package com.xabe.spring.pulsar.producer.infrastructure.messaging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.xabe.spring.pulsar.producer.domain.entity.CarDO;
import com.xabe.spring.pulsar.producer.domain.repository.ProducerRepository;
import com.xabe.spring.pulsar.producer.infrastructure.messaging.dto.CarDTO;
import com.xabe.spring.pulsar.producer.infrastructure.messaging.mapper.CarDTOMapperImpl;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;

class ProducerRepositoryImplTest {

  private Producer<CarDTO> producer;

  private ProducerRepository producerRepository;

  @BeforeEach
  public void setUp() throws Exception {
    this.producer = mock(Producer.class);
    this.producerRepository = new ProducerRepositoryImpl(mock(Logger.class), this.producer, new CarDTOMapperImpl());
  }

  @Test
  public void shouldSendCarDO() throws Exception {
    //Given
    final CarDO carDO = CarDO.builder().id("id").name("name").sentAt(1L).build();
    final ArgumentCaptor<CarDTO> argumentCaptor = ArgumentCaptor.forClass(CarDTO.class);
    //When
    this.producerRepository.saveCar(carDO);

    //Then
    verify(this.producer).send(argumentCaptor.capture());
    final CarDTO value = argumentCaptor.getValue();
    assertThat(value, is(notNullValue()));
    assertThat(value.getId(), is(carDO.getId()));
    assertThat(value.getName(), is(carDO.getName()));
    assertThat(value.getSentAt(), is(carDO.getSentAt()));
  }

  @Test
  public void shouldSendException() throws Exception {
    //Given
    final CarDO carDO = CarDO.builder().id("id").name("name").sentAt(1L).build();
    //When

    doThrow(PulsarClientException.class).when(this.producer).send(any());

    Assertions.assertThrows(RuntimeException.class, () -> this.producerRepository.saveCar(carDO));
  }


}