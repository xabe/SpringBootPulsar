package com.xabe.spring.pulsar.producer.infrastructure.application;

import com.xabe.spring.pulsar.producer.domain.entity.CarDO;

public interface ProducerUseCase {

  void createCar(CarDO carDO);

}
