package com.phuonghoang.messagedemo.services;

import com.phuonghoang.messagedemo.database.models.Message;
import com.phuonghoang.messagedemo.database.repositories.MessageRepository;
import com.phuonghoang.messagedemo.services.mappers.MessageMapper;
import com.phuonghoang.messagedemo.web.dto.RequestMessageDto;
import com.phuonghoang.messagedemo.web.dto.MessageDto;
import com.phuonghoang.messagedemo.web.dto.PagedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
  private final MessageMapper messageMapper = new MessageMapper();
  private final PalindromeService palindromeService = new PalindromeService();
  @Mock private MessageRepository messageRepository;
  private MessageService messageService;

  @BeforeEach
  void setup() {
    messageService = new MessageService(messageRepository, messageMapper, palindromeService);
  }

  @Test
  void getMessage_nonExistingId() {
    when(messageRepository.findById(anyString())).thenReturn(Optional.empty());

    final Throwable exception = catchThrowable(() -> messageService.getMessage("testId"));
    then(exception).isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void getMessage_existingId() {
    final Instant nowInTest = Instant.now(Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC));
    final Message message = new Message("testId", "Test content", false, nowInTest, nowInTest);
    when(messageRepository.findById(anyString())).thenReturn(Optional.of(message));

    then(messageService.getMessage("testId"))
        .usingRecursiveComparison()
        .isEqualTo(new MessageDto("testId", "Test content", false, nowInTest, nowInTest));
  }

  @Test
  void createMessage_null() {
    final Throwable exception = catchThrowable(() -> messageService.createMessage(null));
    then(exception).isInstanceOf(NullPointerException.class);
  }

  @Test
  void createMessage_nullContent() {
    final Throwable exception =
        catchThrowable(() -> messageService.createMessage(new RequestMessageDto(null)));
    then(exception).isInstanceOf(NullPointerException.class);
  }

  @Test
  void createMessage_validContent() {
    final Instant nowInTest = Instant.now(Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC));
    when(messageRepository.save(any(Message.class)))
        .thenReturn(new Message("testId", "Go hang a salami, I'm a lasagna hog", true, nowInTest, nowInTest));

    final MessageDto messageDto =
        messageService.createMessage(new RequestMessageDto("Go hang a salami, I'm a lasagna hog"));

    then(messageDto)
        .usingRecursiveComparison()
        .isEqualTo(new MessageDto("testId", "Go hang a salami, I'm a lasagna hog", true, nowInTest, nowInTest));
  }

  @Test
  void updateMessage_nonExistingId() {
    when(messageRepository.findById(anyString())).thenReturn(Optional.empty());

    final Throwable exception =
        catchThrowable(
            () ->
                messageService.updateMessage(
                    "testId", new RequestMessageDto("something")));
    then(exception).isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void updateMessage_nullContent() {
    final Throwable exception =
        catchThrowable(
            () -> messageService.updateMessage("testId", new RequestMessageDto(null)));
    then(exception).isInstanceOf(NullPointerException.class);
  }

  @Test
  void updateMessage_nullMessage() {
    final Throwable exception = catchThrowable(() -> messageService.updateMessage("testId", null));
    then(exception).isInstanceOf(NullPointerException.class);
  }

  @Test
  void retrieveMessages_negativePageIndex() {
    final Throwable exception =
        catchThrowable(() -> messageService.retrieveMessages(PageRequest.of(-1, 10)));
    then(exception).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void retrieveMessages_negativePageSize() {
    final Throwable exception =
        catchThrowable(() -> messageService.retrieveMessages(PageRequest.of(0, -10)));
    then(exception).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void retrieveMessages_returnsResult() {
    final Instant nowInTest = Instant.now(Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC));
    final PageImpl<Message> pagedMessages = new PageImpl<>(List.of(
            new Message("testId", "Go hang a salami, I'm a lasagna hog", true, nowInTest, nowInTest),
            new Message("testId", "Test content", false, nowInTest, nowInTest)), PageRequest.of(0, 10), 2);
    when(messageRepository.findAll(PageRequest.of(0, 10))).thenReturn(pagedMessages);

    final PagedResponse<MessageDto> messageDtoPagedResponse =
        messageService.retrieveMessages(PageRequest.of(0, 10));
    then(messageDtoPagedResponse)
        .usingRecursiveComparison()
        .isEqualTo(
            new PagedResponse<>(
                List.of(
                    new MessageDto("testId", "Go hang a salami, I'm a lasagna hog", true, nowInTest, nowInTest),
                    new MessageDto("testId", "Test content", false, nowInTest, nowInTest)),
                0,
                2,
                2));
  }
}
