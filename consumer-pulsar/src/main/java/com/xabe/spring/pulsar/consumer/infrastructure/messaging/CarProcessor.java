package com.xabe.spring.pulsar.consumer.infrastructure.messaging;

import com.xabe.spring.pulsar.consumer.domain.repository.ConsumerRepository;
import com.xabe.spring.pulsar.consumer.infrastructure.messaging.dto.CarDTO;
import com.xabe.spring.pulsar.consumer.infrastructure.messaging.mapper.CarDTOMapper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;

public class CarProcessor {

  private final Logger logger;

  private final ExecutorService executor;

  private final AtomicBoolean status;

  private final Consumer<CarDTO> consumer;

  private final ConsumerRepository consumerRepository;

  private final CarDTOMapper carDTOMapper;

  public CarProcessor(final Logger logger, final Consumer<CarDTO> consumer, final ConsumerRepository consumerRepository,
      final CarDTOMapper carDTOMapper) {
    this.logger = logger;
    this.consumer = consumer;
    this.carDTOMapper = carDTOMapper;
    this.consumerRepository = consumerRepository;
    this.status = new AtomicBoolean(false);
    this.executor = Executors.newSingleThreadExecutor();
  }

  private void consumer() {
    while (this.status.get()) {
      this.logger.info("Polling...");
      Message<CarDTO> receive = null;
      try {
        receive = this.consumer.receive(250, TimeUnit.MILLISECONDS);
        this.logger.info("Received event key: {} message:{}", receive.getKey(), receive.getValue());
        this.consumerRepository.addCar(this.carDTOMapper.map(receive.getValue()));
        this.consumer.acknowledge(receive);
      } catch (final PulsarClientException e) {
        this.logger.error("Error receive : {}", e.getMessage(), e);
        this.consumer.negativeAcknowledge(receive);
      }
    }
  }

  public void start() {
    this.status.set(true);
    this.executor.submit(this::consumer);
  }

  public void stop() throws PulsarClientException {
    this.status.set(false);
    this.consumer.close();
    this.executor.shutdown();
  }

}
