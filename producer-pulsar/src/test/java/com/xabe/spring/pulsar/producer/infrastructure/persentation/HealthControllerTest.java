package com.xabe.spring.pulsar.producer.infrastructure.persentation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class HealthControllerTest {

  private HealthController healthController;

  @BeforeEach
  public void setUp() throws Exception {
    this.healthController = new HealthController(mock(Logger.class));
  }

  @Test
  public void shouldGetStatus() throws Exception {
    //Given

    //When
    final ResponseEntity result = this.healthController.healthCheck();

    //Then
    assertThat(result, is(notNullValue()));
    assertThat(result.getStatusCode(), is(HttpStatus.OK));
    assertThat(result.getBody(), is(HealthController.OK));
  }

}