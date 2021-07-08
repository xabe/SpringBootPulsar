package com.xabe.spring.pulsar.consumer.infrastructure.presentation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.xabe.spring.pulsar.consumer.domain.entity.CarDO;
import com.xabe.spring.pulsar.consumer.infrastructure.application.ConsumerUseCase;
import com.xabe.spring.pulsar.consumer.infrastructure.presentation.mapper.CarDOMapperImpl;
import com.xabe.spring.pulsar.consumer.infrastructure.presentation.payload.CarPayload;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ConsumerControllerTest {

  private Logger logger;

  private ConsumerUseCase consumerUseCase;

  private ConsumerController consumerController;

  @BeforeEach
  public void setUp() throws Exception {
    this.logger = mock(Logger.class);
    this.consumerUseCase = mock(ConsumerUseCase.class);
    this.consumerController = new ConsumerController(this.logger, new CarDOMapperImpl(), this.consumerUseCase);
  }

  @Test
  public void shouldGetAllCars() throws Exception {
    //Given
    when(this.consumerUseCase.getCars()).thenReturn(List.of(CarDO.builder().id("id").name("name").sentAt(1L).build()));

    //When
    final ResponseEntity<List<CarPayload>> result = this.consumerController.getCars();

    //Then
    assertThat(result, is(notNullValue()));
    assertThat(result.getStatusCode(), is(HttpStatus.OK));
    assertThat(result.getBody(), is(hasSize(1)));
    assertThat(result.getBody().get(0).getId(), is("id"));
    assertThat(result.getBody().get(0).getName(), is("name"));
    assertThat(result.getBody().get(0).getSentAt(), is(1L));
  }

}