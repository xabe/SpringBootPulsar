package com.xabe.spring.pulsar.producer.infrastructure.messaging;

import com.xabe.spring.pulsar.producer.domain.entity.CarDO;
import com.xabe.spring.pulsar.producer.domain.repository.ProducerRepository;
import com.xabe.spring.pulsar.producer.infrastructure.messaging.dto.CarDTO;
import com.xabe.spring.pulsar.producer.infrastructure.messaging.mapper.CarDTOMapper;
import lombok.RequiredArgsConstructor;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProducerRepositoryImpl implements ProducerRepository {

  private final Logger logger;

  private final Producer<CarDTO> producer;

  private final CarDTOMapper carDTOMapper;

  @Override
  public void saveCar(final CarDO carDO) {
    try {
      final CarDTO carDTO = this.carDTOMapper.map(carDO);
      this.producer.send(carDTO);
      this.logger.info("Send Command Car {}", carDTO);
    } catch (final PulsarClientException e) {
      this.logger.error("Error send Command Car {}", carDO);
      throw new RuntimeException(e);
    }
  }

}