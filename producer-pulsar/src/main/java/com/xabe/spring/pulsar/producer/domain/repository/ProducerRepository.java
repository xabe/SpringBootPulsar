package com.xabe.spring.pulsar.producer.domain.repository;

import com.xabe.spring.pulsar.producer.domain.entity.CarDO;

public interface ProducerRepository {

  void saveCar(CarDO carDO);

}
