package com.xabe.spring.pulsar.consumer.infrastructure.messaging;

import com.xabe.spring.pulsar.consumer.infrastructure.messaging.dto.CarDTO;
import java.util.Map;
import java.util.Optional;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.common.api.EncryptionContext;

public class MessageFake
    implements org.apache.pulsar.client.api.Message<com.xabe.spring.pulsar.consumer.infrastructure.messaging.dto.CarDTO> {

  @Override
  public Map<String, String> getProperties() {
    return null;
  }

  @Override
  public boolean hasProperty(final String s) {
    return false;
  }

  @Override
  public String getProperty(final String s) {
    return null;
  }

  @Override
  public byte[] getData() {
    return new byte[0];
  }

  @Override
  public CarDTO getValue() {
    return CarDTO.builder().id("id").name("name").sentAt(1L).build();
  }

  @Override
  public MessageId getMessageId() {
    return null;
  }

  @Override
  public long getPublishTime() {
    return 0;
  }

  @Override
  public long getEventTime() {
    return 0;
  }

  @Override
  public long getSequenceId() {
    return 0;
  }

  @Override
  public String getProducerName() {
    return null;
  }

  @Override
  public boolean hasKey() {
    return false;
  }

  @Override
  public String getKey() {
    return "key";
  }

  @Override
  public boolean hasBase64EncodedKey() {
    return false;
  }

  @Override
  public byte[] getKeyBytes() {
    return new byte[0];
  }

  @Override
  public boolean hasOrderingKey() {
    return false;
  }

  @Override
  public byte[] getOrderingKey() {
    return new byte[0];
  }

  @Override
  public String getTopicName() {
    return null;
  }

  @Override
  public Optional<EncryptionContext> getEncryptionCtx() {
    return Optional.empty();
  }

  @Override
  public int getRedeliveryCount() {
    return 0;
  }

  @Override
  public byte[] getSchemaVersion() {
    return new byte[0];
  }

  @Override
  public boolean isReplicated() {
    return false;
  }

  @Override
  public String getReplicatedFrom() {
    return null;
  }
}
