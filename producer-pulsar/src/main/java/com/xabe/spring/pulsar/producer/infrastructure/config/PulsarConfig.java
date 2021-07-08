package com.xabe.spring.pulsar.producer.infrastructure.config;

import com.xabe.spring.pulsar.producer.infrastructure.messaging.dto.CarDTO;
import java.util.concurrent.TimeUnit;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
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

  @Bean(destroyMethod = "close")
  public Producer<CarDTO> buildProducer(final PulsarClient pulsarClient) throws PulsarClientException {
    return pulsarClient.newProducer(Schema.JSON(CarDTO.class))
        .topic("car")
        .create();
  }

}