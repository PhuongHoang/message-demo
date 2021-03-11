package com.phuonghoang.messagedemo.services;

import com.phuonghoang.messagedemo.web.dto.RequestMessageDto;
import com.phuonghoang.messagedemo.web.dto.MessageDto;
import com.phuonghoang.messagedemo.web.dto.PagedResponse;
import com.phuonghoang.messagedemo.services.mappers.MessageMapper;
import com.phuonghoang.messagedemo.database.models.Message;
import com.phuonghoang.messagedemo.database.repositories.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MessageService {
  private final MessageRepository messageRepository;
  private final MessageMapper messageMapper;
  private final PalindromeService palindromeService;

  public MessageService(
      MessageRepository messageRepository,
      MessageMapper messageMapper,
      PalindromeService palindromeService) {
    this.messageRepository = messageRepository;
    this.messageMapper = messageMapper;
    this.palindromeService = palindromeService;
  }

  public MessageDto getMessage(String id) {
    final Message messageBy =
        messageRepository
            .findById(id)
            .orElseThrow(() -> new NoSuchElementException("No message found for Id = " + id));
    return messageMapper.toMessageDto(messageBy);
  }

  public MessageDto createMessage(RequestMessageDto messageDto) {
    Objects.requireNonNull(messageDto, "messageDto cannot be null.");
    final String content = messageDto.getContent();
    Objects.requireNonNull(content, "content cannot be null.");
    final Instant creationTime = Instant.now();
    final Message savedMessage =
        messageRepository.save(
            messageMapper.toMessage(
                new MessageDto(null, content, palindromeService.isPalindrome(content), creationTime, creationTime)));
    return messageMapper.toMessageDto(savedMessage);
  }

  public void deleteMessage(String id) {
    final MessageDto messageDto = getMessage(id);
    messageRepository.deleteById(messageDto.getId());
  }

  public MessageDto updateMessage(String id, RequestMessageDto updatedMessage) {
    Objects.requireNonNull(updatedMessage, "updatedMessage cannot be null.");
    Objects.requireNonNull(updatedMessage.getContent(), "content cannot be null.");
    final MessageDto messageDto = getMessage(id);
    final Message savedMessage =
        messageRepository.save(
            messageMapper.toMessage(
                new MessageDto(
                    messageDto.getId(),
                    updatedMessage.getContent(),
                    palindromeService.isPalindrome(updatedMessage.getContent()), messageDto.getCreatedAt(), Instant.now())));
    return messageMapper.toMessageDto(savedMessage);
  }

  public PagedResponse<MessageDto> retrieveMessages(Pageable pageable) {
    if (pageable.getPageNumber() < 0) {
      throw new IllegalArgumentException("page cannot be negative.");
    }
    if (pageable.getPageSize() < 0) {
      throw new IllegalArgumentException("size cannot be negative.");
    }
    final Page<Message> pagedResults = messageRepository.findAll(pageable);
    return new PagedResponse<>(
        pagedResults.getContent().stream()
            .map(messageMapper::toMessageDto)
            .collect(Collectors.toList()),
        pageable.getPageNumber(),
        pagedResults.getNumberOfElements(),
        pagedResults.getTotalElements());
  }
}
