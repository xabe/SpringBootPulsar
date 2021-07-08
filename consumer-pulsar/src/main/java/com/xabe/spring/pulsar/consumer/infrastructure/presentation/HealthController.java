package com.xabe.spring.pulsar.consumer.infrastructure.presentation;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

  public static final String OK = "OK";

  private final Logger logger;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity healthCheck() {
    this.logger.info("health");
    return ResponseEntity.ok(OK);
  }

}
