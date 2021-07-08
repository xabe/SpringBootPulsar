package com.xabe.spring.pulsar.producer.infrastructure.persentation;

import com.xabe.spring.pulsar.producer.domain.entity.CarDO;
import com.xabe.spring.pulsar.producer.infrastructure.application.ProducerUseCase;
import com.xabe.spring.pulsar.producer.infrastructure.persentation.mapper.CarDOMapper;
import com.xabe.spring.pulsar.producer.infrastructure.persentation.payload.CarPayload;
import java.time.Clock;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/producer/car")
@RequiredArgsConstructor
public class ProducerController {

  private final Logger logger;

  private final Clock clock;

  private final CarDOMapper carDOMapper;

  private final ProducerUseCase producerUseCase;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createCar(@Valid @RequestBody final CarPayload carPayload) {
    final CarDO carDO = this.carDOMapper.map(carPayload);
    this.producerUseCase.createCar(carDO.withSentAt(this.clock.millis()));
    this.logger.info("Create carPayload {}", carPayload);
    return ResponseEntity.ok().build();
  }

}
