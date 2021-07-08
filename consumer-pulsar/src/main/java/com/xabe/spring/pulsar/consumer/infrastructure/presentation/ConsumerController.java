package com.xabe.spring.pulsar.consumer.infrastructure.presentation;

import com.xabe.spring.pulsar.consumer.infrastructure.application.ConsumerUseCase;
import com.xabe.spring.pulsar.consumer.infrastructure.presentation.mapper.CarDOMapper;
import com.xabe.spring.pulsar.consumer.infrastructure.presentation.payload.CarPayload;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class ConsumerController {

  private final Logger logger;

  private final CarDOMapper carDOMapper;

  private final ConsumerUseCase consumerUseCase;

  @GetMapping
  public ResponseEntity<List<CarPayload>> getCars() {
    this.logger.info("get all events consumer");
    return ResponseEntity.ok(this.consumerUseCase.getCars().stream().map(this.carDOMapper::map).collect(Collectors.toList()));
  }

}
