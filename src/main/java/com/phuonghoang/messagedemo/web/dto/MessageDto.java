package com.phuonghoang.messagedemo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Schema(description = "Class representing a message tracked by the application.")
public class MessageDto {
  @Schema(
      description = "Unique identifier of a message. No two messages can have the same id.",
      example = "3972e8aa-1ea6-4d47-9490-a4f78f772713")
  @NotNull(message = "id must be provided.")
  private final String id;

  @Schema(description = "The content of a message", example = "Go hang a salami, I'm a lasagna hog")
  @NotNull(message = "content must be provided.")
  private final String content;

  @Schema(
      description = "A flag indicating if the content is a palindrome or not.",
      example = "true (if it is a palindrome)")
  private final boolean isPalindrome;

  @Schema(
      description = "The time that the message is created.",
      example = "2018-08-22T10:00:00Z")
  private final Instant createdAt;

  @Schema(
      description = "The last time that the message is created.",
      example = "2018-08-22T10:00:00Z")
  private final Instant updatedAt;

  public MessageDto(
      String id, String content, boolean isPalindrome, Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.content = content;
    this.isPalindrome = isPalindrome;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public boolean isPalindrome() {
    return isPalindrome;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MessageDto)) return false;
    MessageDto that = (MessageDto) o;
    return isPalindrome() == that.isPalindrome()
        && getId().equals(that.getId())
        && getContent().equals(that.getContent())
        && getCreatedAt().equals(that.getCreatedAt())
        && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getContent(), isPalindrome(), getCreatedAt(), getUpdatedAt());
  }
}
