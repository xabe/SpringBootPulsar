package com.xabe.spring.pulsar.producer.infrastructure.integration.config;

import com.xabe.spring.pulsar.producer.infrastructure.messaging.dto.CarDTO;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PreDestroy;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CarProcessor {

  private final Logger logger;

  private final PulsarClient pulsarClient;

  private final ExecutorService executor;

  private final AtomicBoolean start;

  private final Consumer<CarDTO> consumer;

  private final BlockingQueue<CarDTO> carDTOS;

  public CarProcessor(final Logger logger, final PulsarClient pulsarClient) throws PulsarClientException {
    this.logger = logger;
    this.pulsarClient = pulsarClient;
    this.start = new AtomicBoolean(true);
    this.executor = Executors.newSingleThreadExecutor();
    this.carDTOS = new ArrayBlockingQueue<>(100);
    this.consumer = pulsarClient
        .newConsumer(Schema.JSON(CarDTO.class))
        .consumerName("consumer-car")
        .subscriptionName("subscription-car")
        .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
        .topic("car")
        .subscriptionType(SubscriptionType.Exclusive).subscribe();

    this.executor.submit(() -> {
      while (this.start.get()) {
        logger.info("Polling...");
        Message<CarDTO> receive = null;
        try {
          receive = this.consumer.receive(250, TimeUnit.MILLISECONDS);
          logger.info("Received event key: {} message:{}", receive.getKey(), receive.getValue());
          this.carDTOS.add(receive.getValue());
          this.consumer.acknowledge(receive);
        } catch (final PulsarClientException e) {
          logger.error("Error receive : {}", e.getMessage(), e);
          this.consumer.negativeAcknowledge(receive);
        }
      }
    });
  }

  public CarDTO expectMessagePulsar(final long milliseconds)
      throws InterruptedException {
    final CarDTO message = this.carDTOS.poll(milliseconds, TimeUnit.MILLISECONDS);
    if (Objects.isNull(message)) {
      throw new RuntimeException("An exception happened while polling the queue for carDTO");
    }
    return message;
  }

  @PreDestroy
  public void stop() throws PulsarClientException {
    this.start.set(false);
    this.consumer.close();
    this.executor.shutdown();
  }
}
