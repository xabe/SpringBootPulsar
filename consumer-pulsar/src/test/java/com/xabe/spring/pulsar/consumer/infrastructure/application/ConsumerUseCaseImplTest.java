package com.xabe.spring.pulsar.consumer.infrastructure.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.xabe.spring.pulsar.consumer.domain.entity.CarDO;
import com.xabe.spring.pulsar.consumer.domain.repository.ConsumerRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsumerUseCaseImplTest {

  private ConsumerRepository consumerRepository;

  private ConsumerUseCase consumerUseCase;

  @BeforeEach
  public void setUp() throws Exception {
    this.consumerRepository = mock(ConsumerRepository.class);
    this.consumerUseCase = new ConsumerUseCaseImpl(this.consumerRepository);
  }

  @Test
  public void shouldGetAllCars() throws Exception {
    //Given
    when(this.consumerRepository.getCarDOS()).thenReturn(List.of(CarDO.builder().build()));

    //When
    final List<CarDO> result = this.consumerUseCase.getCars();

    //Then
    assertThat(result, is(notNullValue()));
    assertThat(result, is(hasSize(1)));
  }

}