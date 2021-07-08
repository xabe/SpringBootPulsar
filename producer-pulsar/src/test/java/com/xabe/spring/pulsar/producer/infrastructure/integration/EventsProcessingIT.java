package com.xabe.spring.pulsar.producer.infrastructure.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.xabe.spring.pulsar.producer.App;
import com.xabe.spring.pulsar.producer.infrastructure.integration.config.CarProcessor;
import com.xabe.spring.pulsar.producer.infrastructure.messaging.dto.CarDTO;
import com.xabe.spring.pulsar.producer.infrastructure.persentation.payload.CarPayload;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EventsProcessingIT {

  public static final int TIMEOUT_MS = 5000;

  public static final int DELAY_MS = 1500;

  public static final int POLL_INTERVAL_MS = 500;

  @Autowired
  public CarProcessor carProcessor;

  @LocalServerPort
  protected int serverPort;

  @Test
  public void shouldCreatedCar() throws Exception {
    final CarPayload carPayload = CarPayload.builder().id("id").name("mazda 3").sentAt(Instant.now().toEpochMilli()).build();

    final HttpResponse<JsonNode> response = Unirest.post(String.format("http://localhost:%d/api/producer/car", this.serverPort))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).body(carPayload).asJson();

    assertThat(response, is(notNullValue()));
    assertThat(response.getStatus(), is(200));

    Awaitility.await().pollDelay(DELAY_MS, TimeUnit.MILLISECONDS).pollInterval(POLL_INTERVAL_MS, TimeUnit.MILLISECONDS)
        .atMost(TIMEOUT_MS, TimeUnit.MILLISECONDS).until(() -> {
      final CarDTO result = this.carProcessor.expectMessagePulsar(500);
      assertThat(result, is(notNullValue()));
      assertThat(result.getId(), is(carPayload.getId()));
      assertThat(result.getName(), is(carPayload.getName()));
      assertThat(result.getSentAt(), is(notNullValue()));
      return true;
    });
  }
}
