package com.xabe.spring.pulsar.consumer.infrastructure.integration.config;

import com.xabe.spring.pulsar.consumer.infrastructure.messaging.dto.CarDTO;
import javax.annotation.PreDestroy;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CarProducer {

  private final Logger logger;

  private final PulsarClient pulsarClient;

  private final Producer<CarDTO> producer;

  public CarProducer(final Logger logger, final PulsarClient pulsarClient) throws PulsarClientException {
    this.logger = logger;
    this.pulsarClient = pulsarClient;
    this.producer = pulsarClient
        .newProducer(Schema.JSON(CarDTO.class))
        .topic("car")
        .create();
  }

  public void sendCarDTO(final CarDTO carDTO) throws PulsarClientException {
    this.producer.send(carDTO);
    this.logger.info("Send car {}", carDTO);
  }

  @PreDestroy
  public void stop() throws PulsarClientException {
    this.producer.close();
  }

}
