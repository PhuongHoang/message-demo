package com.phuonghoang.messagedemo.services.mappers;

import com.phuonghoang.messagedemo.database.models.Message;
import com.phuonghoang.messagedemo.web.dto.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.BDDAssertions.then;

class MessageMapperTest {
  private MessageMapper messageMapper;

  @BeforeEach
  void setup() {
    messageMapper = new MessageMapper();
  }

  @Test
  void toMessageDto_null() {
    then(messageMapper.toMessageDto(null)).isNull();
  }

  @Test
  void toMessageDto_nonNull() {
    final Instant nowInTest =
        Instant.now(Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC));
    final Message message = new Message("testId", "test content", false, nowInTest, nowInTest);
    then(messageMapper.toMessageDto(message))
        .usingRecursiveComparison()
        .isEqualTo(new MessageDto("testId", "test content", false, nowInTest, nowInTest));
  }

  @Test
  void toMessage_null() {
    then(messageMapper.toMessage(null)).isNull();
  }

  @Test
  void toMessage_nonNull() {
    final Instant nowInTest =
        Instant.now(Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC));
    final MessageDto messageDto =
        new MessageDto("testId", "test content", false, nowInTest, nowInTest);
    final Message message = new Message("testId", "test content", false, nowInTest, nowInTest);
    then(messageMapper.toMessage(messageDto)).usingRecursiveComparison().isEqualTo(message);
  }
}
