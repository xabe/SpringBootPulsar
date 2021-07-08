package com.xabe.spring.pulsar.consumer.domain.repository;

import com.xabe.spring.pulsar.consumer.domain.entity.CarDO;
import java.util.List;

public interface ConsumerRepository {

  List<CarDO> getCarDOS();

  void addCar(final CarDO carDO);
}