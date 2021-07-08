package com.xabe.spring.pulsar.consumer.infrastructure.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

import com.xabe.spring.pulsar.consumer.domain.entity.CarDO;
import com.xabe.spring.pulsar.consumer.domain.repository.ConsumerRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class InMemoryConsumerRepositoryTest {

  private Logger logger;

  private ConsumerRepository consumerRepository;

  @BeforeEach
  public void setUp() throws Exception {
    this.logger = mock(Logger.class);
    this.consumerRepository = new InMemoryConsumerRepository(this.logger);
  }

  @Test
  public void shouldGetAllCars() throws Exception {
    //Given
    this.consumerRepository.addCar(CarDO.builder().build());

    //When
    final List<CarDO> result = this.consumerRepository.getCarDOS();

    //Then
    assertThat(result, is(notNullValue()));
    assertThat(result, is(hasSize(1)));
  }

}