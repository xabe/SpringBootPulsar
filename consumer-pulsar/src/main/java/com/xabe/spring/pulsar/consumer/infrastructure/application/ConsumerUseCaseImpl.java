package com.xabe.spring.pulsar.consumer.infrastructure.application;

import com.xabe.spring.pulsar.consumer.domain.entity.CarDO;
import com.xabe.spring.pulsar.consumer.domain.repository.ConsumerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsumerUseCaseImpl implements ConsumerUseCase {

  private final ConsumerRepository consumerRepository;

  @Override
  public List<CarDO> getCars() {
    return this.consumerRepository.getCarDOS();
  }
}
