package com.phuonghoang.messagedemo.web.controllers;

import com.phuonghoang.messagedemo.web.dto.RequestMessageDto;
import com.phuonghoang.messagedemo.web.dto.MessageDto;
import com.phuonghoang.messagedemo.web.dto.PagedResponse;
import com.phuonghoang.messagedemo.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/messages")
@Tag(
    name = "Message CRUD operations",
    description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Messages.")
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Retrieves a message specified by ID")
  public MessageDto getMessage(@PathVariable String id) {
    return messageService.getMessage(id);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Creates a new message")
  public MessageDto createMessage(@Valid @RequestBody RequestMessageDto messageDto) {
    return messageService.createMessage(messageDto);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deletes a message specified by ID")
  public void deleteMessage(@Valid @PathVariable String id) {
    messageService.deleteMessage(id);
  }

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Updates an existing message specified by ID")
  public MessageDto updateMessage(@PathVariable String id, @Valid @RequestBody RequestMessageDto messageDto) {
    return messageService.updateMessage(id, messageDto);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "List all existing messages")
  @PageableAsQueryParam
  public PagedResponse<MessageDto> listMessages(@Valid @Parameter(hidden = true) Pageable pageable) {
    return messageService.retrieveMessages(pageable);
  }
}
