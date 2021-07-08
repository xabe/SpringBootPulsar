package com.xabe.spring.pulsar.producer.infrastructure.persentation.payload;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class CarPayload implements Serializable {

  @NotNull
  private String id;

  @NotNull
  private String name;

  private Long sentAt;

}
