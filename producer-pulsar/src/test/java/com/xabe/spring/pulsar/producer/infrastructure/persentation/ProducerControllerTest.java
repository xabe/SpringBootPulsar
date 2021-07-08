package com.xabe.spring.pulsar.producer.infrastructure.persentation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.xabe.spring.pulsar.producer.domain.entity.CarDO;
import com.xabe.spring.pulsar.producer.infrastructure.application.ProducerUseCase;
import com.xabe.spring.pulsar.producer.infrastructure.persentation.mapper.CarDOMapperImpl;
import com.xabe.spring.pulsar.producer.infrastructure.persentation.payload.CarPayload;
import java.time.Clock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProducerControllerTest {

  private ProducerUseCase producerUseCase;

  private Clock clock;

  private ProducerController producerController;

  @BeforeEach
  public void setUp() throws Exception {
    this.producerUseCase = mock(ProducerUseCase.class);
    this.clock = mock(Clock.class);
    this.producerController = new ProducerController(mock(Logger.class), this.clock, new CarDOMapperImpl(), this.producerUseCase);
  }

  @Test
  public void givenACarPayloadWhenInvokeCreateCarThenReturnResponse() throws Exception {
    //Given
    final CarPayload carPayload = CarPayload.builder().id("id").name("name").build();
    final ArgumentCaptor<CarDO> argumentCaptor = ArgumentCaptor.forClass(CarDO.class);

    when(this.clock.millis()).thenReturn(1L);

    //When
    final ResponseEntity result = this.producerController.createCar(carPayload);

    //Then
    assertThat(result, is(notNullValue()));
    assertThat(result.getStatusCode(), is(HttpStatus.OK));
    verify(this.producerUseCase).createCar(argumentCaptor.capture());
    final CarDO value = argumentCaptor.getValue();
    assertThat(value, is(notNullValue()));
    assertThat(value.getId(), is(carPayload.getId()));
    assertThat(value.getName(), is(carPayload.getName()));
    assertThat(value.getSentAt(), is(1L));
  }

}