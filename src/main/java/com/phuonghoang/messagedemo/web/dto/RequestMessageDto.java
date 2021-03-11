package com.phuonghoang.messagedemo.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Schema(description = "Class representing a message content for used in create/update request.")
public class RequestMessageDto {
  @Schema(description = "The content of a message", example = "Go hang a salami, I'm a lasagna hog")
  @NotNull(message = "content must be provided.")
  private final String content;

  @JsonCreator
  public RequestMessageDto(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RequestMessageDto)) return false;
    RequestMessageDto that = (RequestMessageDto) o;
    return getContent().equals(that.getContent());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getContent());
  }

  public String getContent() {
    return content;
  }
}
