package com.xabe.spring.pulsar.consumer.infrastructure.config;

import com.xabe.spring.pulsar.consumer.domain.repository.ConsumerRepository;
import com.xabe.spring.pulsar.consumer.infrastructure.messaging.CarProcessor;
import com.xabe.spring.pulsar.consumer.infrastructure.messaging.dto.CarDTO;
import com.xabe.spring.pulsar.consumer.infrastructure.messaging.mapper.CarDTOMapper;
import java.util.concurrent.TimeUnit;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarConfig {

  @Value("${spring.pulsar.service-url:pulsar://localhost:6650}")
  private String serviceUrl;

  @Value("${spring.pulsar.ioThreads:10}")
  private Integer ioThreads;

  @Value("${spring.pulsar.listenerThreads:10}")
  private Integer listenerThreads;

  @Value("${spring.pulsar.enableTcpNoDelay:false}")
  private boolean enableTcpNoDelay;

  @Value("${spring.pulsar.keepAliveIntervalSec:20}")
  private Integer keepAliveIntervalSec;

  @Value("${spring.pulsar.connectionTimeoutSec:10}")
  private Integer connectionTimeoutSec;

  @Value("${spring.pulsar.operationTimeoutSec:15}")
  private Integer operationTimeoutSec;

  @Value("${spring.pulsar.startingBackoffIntervalMs:100}")
  private Integer startingBackoffIntervalMs;

  @Value("${spring.pulsar.maxBackoffIntervalSec:10}")
  private Integer maxBackoffIntervalSec;

  @Bean(destroyMethod = "close")
  public PulsarClient pulsarClient() throws PulsarClientException {
    return PulsarClient.builder()
        .serviceUrl(this.serviceUrl)
        .ioThreads(this.ioThreads)
        .listenerThreads(this.listenerThreads)
        .enableTcpNoDelay(this.enableTcpNoDelay)
        .keepAliveInterval(this.keepAliveIntervalSec, TimeUnit.SECONDS)
        .connectionTimeout(this.connectionTimeoutSec, TimeUnit.SECONDS)
        .operationTimeout(this.operationTimeoutSec, TimeUnit.SECONDS)
        .startingBackoffInterval(this.startingBackoffIntervalMs, TimeUnit.MILLISECONDS)
        .maxBackoffInterval(this.maxBackoffIntervalSec, TimeUnit.SECONDS)
        .build();
  }

  @Bean(destroyMethod = "stop", initMethod = "start")
  public CarProcessor buildProducer(final Logger logger, final PulsarClient pulsarClient, final ConsumerRepository consumerRepository,
      final CarDTOMapper carDTOMapper) throws PulsarClientException {
    final Consumer<CarDTO> consumer = pulsarClient
        .newConsumer(Schema.JSON(CarDTO.class))
        .consumerName("consumer-car")
        .subscriptionName("subscription-car")
        .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
        .topic("car")
        .subscriptionType(SubscriptionType.Exclusive).subscribe();
    return new CarProcessor(logger, consumer, consumerRepository, carDTOMapper);
  }

}