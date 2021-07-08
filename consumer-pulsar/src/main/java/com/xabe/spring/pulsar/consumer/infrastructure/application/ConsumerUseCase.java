package com.xabe.spring.pulsar.consumer.infrastructure.application;

import com.xabe.spring.pulsar.consumer.domain.entity.CarDO;
import java.util.List;

public interface ConsumerUseCase {

  List<CarDO> getCars();

}
