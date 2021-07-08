package com.xabe.spring.pulsar.consumer.infrastructure.persistence;

import com.xabe.spring.pulsar.consumer.domain.entity.CarDO;
import com.xabe.spring.pulsar.consumer.domain.repository.ConsumerRepository;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InMemoryConsumerRepository implements ConsumerRepository {

  private final Logger logger;

  private final LinkedList<CarDO> carDOS = new LinkedList<>();

  @Override
  public List<CarDO> getCarDOS() {
    this.logger.info("Get cars size {}", this.carDOS.size());
    return Collections.unmodifiableList(this.carDOS);
  }

  @Override
  public void addCar(final CarDO carDO) {
    this.carDOS.add(carDO);
  }
}
