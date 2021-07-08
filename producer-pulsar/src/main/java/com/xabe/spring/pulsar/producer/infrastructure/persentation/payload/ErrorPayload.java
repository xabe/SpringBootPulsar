package com.xabe.spring.pulsar.producer.infrastructure.persentation.payload;

import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import org.springframework.validation.FieldError;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class ErrorPayload {

  @Builder.Default
  private final List<FieldValidationErrorPayload> fieldErrors = Collections.emptyList();

  @Value
  @EqualsAndHashCode
  @ToString
  @NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
  @AllArgsConstructor
  public static class FieldValidationErrorPayload {

    private final String field;

    private final String message;

    public static FieldValidationErrorPayload builder(final FieldError fieldError) {
      return new FieldValidationErrorPayload(fieldError.getField(), fieldError.getDefaultMessage());
    }

  }
}
