package com.phuonghoang.messagedemo.services.mappers;

import com.phuonghoang.messagedemo.database.models.Message;
import com.phuonghoang.messagedemo.web.dto.MessageDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MessageMapper {
  public MessageDto toMessageDto(Message message) {
    if (Objects.isNull(message)) {
      return null;
    }
    return new MessageDto(
        message.getId(),
        message.getContent(),
        message.isPalindrome(),
        message.getCreatedAt(),
        message.getUpdatedAt());
  }

  public Message toMessage(MessageDto messageDto) {
    if (Objects.isNull(messageDto)) {
      return null;
    }
    return new Message(
        messageDto.getId(),
        messageDto.getContent(),
        messageDto.isPalindrome(),
        messageDto.getCreatedAt(),
        messageDto.getUpdatedAt());
  }
}
