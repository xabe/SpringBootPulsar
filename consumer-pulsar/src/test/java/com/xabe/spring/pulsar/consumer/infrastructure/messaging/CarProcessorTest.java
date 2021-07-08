package com.xabe.spring.pulsar.consumer.infrastructure.messaging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.xabe.spring.pulsar.consumer.domain.entity.CarDO;
import com.xabe.spring.pulsar.consumer.domain.repository.ConsumerRepository;
import com.xabe.spring.pulsar.consumer.infrastructure.messaging.dto.CarDTO;
import com.xabe.spring.pulsar.consumer.infrastructure.messaging.mapper.CarDTOMapperImpl;
import java.util.concurrent.TimeUnit;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;

class CarProcessorTest {

  private Logger logger;

  private Consumer<CarDTO> consumer;

  private ConsumerRepository consumerRepository;

  private CarProcessor carProcessor;

  @BeforeEach
  public void setUp() throws Exception {
    this.logger = mock(Logger.class);
    this.consumer = mock(Consumer.class);
    this.consumerRepository = mock(ConsumerRepository.class);
    this.carProcessor = new CarProcessor(this.logger, this.consumer, this.consumerRepository, new CarDTOMapperImpl());
  }

  @Test
  public void shouldAddCar() throws Exception {
    //Given
    final ArgumentCaptor<CarDO> argumentCaptor = ArgumentCaptor.forClass(CarDO.class);

    //When
    final MessageFake messageFake = new MessageFake();
    when(this.consumer.receive(anyInt(), any())).then(invocation -> {
      TimeUnit.MILLISECONDS.sleep(250);
      return messageFake;
    });
    this.carProcessor.start();

    //Then
    verify(this.consumerRepository, timeout(300)).addCar(argumentCaptor.capture());
    final CarDO result = argumentCaptor.getValue();
    assertThat(result, is(notNullValue()));
    assertThat(result.getId(), is("id"));
    assertThat(result.getName(), is("name"));
    assertThat(result.getSentAt(), is(1L));
    verify(this.consumer).acknowledge(eq(messageFake));
  }

  @Test
  public void notShouldAddCar() throws Exception {
    //Given
    final MessageFake messageFake = null;

    //When
    when(this.consumer.receive(anyInt(), any())).then(invocation -> {
      TimeUnit.MILLISECONDS.sleep(250);
      throw new PulsarClientException("error");
    });
    this.carProcessor.start();

    //Then
    verify(this.consumerRepository, never()).addCar(any());
    verify(this.consumer, timeout(300)).negativeAcknowledge(eq(messageFake));
  }
}