package com.xabe.spring.pulsar.producer.infrastructure.application;

import com.xabe.spring.pulsar.producer.domain.entity.CarDO;
import com.xabe.spring.pulsar.producer.domain.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerUseCaseImpl implements ProducerUseCase {

  private final Logger logger;

  private final ProducerRepository producerRepository;

  @Override
  public void createCar(final CarDO carDO) {
    this.producerRepository.saveCar(carDO);
    this.logger.info("Created carDO {}", carDO);
  }

}
