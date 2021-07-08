package com.xabe.spring.pulsar.producer.infrastructure.application;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.xabe.spring.pulsar.producer.domain.entity.CarDO;
import com.xabe.spring.pulsar.producer.domain.repository.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class ProducerUseCaseImplTest {

  private ProducerRepository producerRepository;

  private ProducerUseCase producerUseCase;

  @BeforeEach
  public void setUp() throws Exception {
    this.producerRepository = mock(ProducerRepository.class);
    this.producerUseCase = new ProducerUseCaseImpl(mock(Logger.class), this.producerRepository);
  }

  @Test
  public void shouldCreateCar() throws Exception {
    //Given
    final CarDO carDO = CarDO.builder().build();

    //When
    this.producerUseCase.createCar(carDO);
    //Then
    verify(this.producerRepository).saveCar(eq(carDO));
  }

}