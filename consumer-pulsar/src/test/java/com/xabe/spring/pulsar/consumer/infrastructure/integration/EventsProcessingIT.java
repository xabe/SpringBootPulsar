package com.xabe.spring.pulsar.consumer.infrastructure.integration;

import com.xabe.spring.pulsar.consumer.App;
import com.xabe.spring.pulsar.consumer.infrastructure.integration.config.CarProducer;
import com.xabe.spring.pulsar.consumer.infrastructure.messaging.dto.CarDTO;
import com.xabe.spring.pulsar.consumer.infrastructure.presentation.payload.CarPayload;
import java.util.concurrent.TimeUnit;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
  public CarProducer carProducer;

  @LocalServerPort
  protected int serverPort;

  @Test
  public void shouldCreatedCar() throws Exception {
    this.carProducer.sendCarDTO(CarDTO.builder().id("id").name("name").sentAt(1L).build());

    Awaitility.await().pollDelay(DELAY_MS, TimeUnit.MILLISECONDS).pollInterval(POLL_INTERVAL_MS, TimeUnit.MILLISECONDS)
        .atMost(TIMEOUT_MS, TimeUnit.MILLISECONDS).until(() -> {

      final HttpResponse<CarPayload[]> response = Unirest.get(String.format("http://localhost:%d/consumer", this.serverPort))
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).asObject(CarPayload[].class);

      return response != null && (response.getStatus() >= 200 || response.getStatus() < 300) && response.getBody().length >= 1;
    });
  }
}
