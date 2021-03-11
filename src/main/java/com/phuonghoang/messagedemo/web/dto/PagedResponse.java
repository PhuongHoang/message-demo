package com.phuonghoang.messagedemo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Class representing a paged response.")
public class PagedResponse<T> {
  @Schema(description = "The page index, starting from zero", example = "0")
  private final long page;

  @Schema(description = "The size of a page", example = "10")
  private final long size;

  @Schema(
      description = "The total number of items tracked by the application",
      example = "1000")
  private final long count;

  @Schema(description = "The content of the response")
  private final List<T> contents;

  public PagedResponse(List<T> contents, long page, long size, long count) {
    this.page = page;
    this.size = size;
    this.contents = contents;
    this.count = count;
  }

  public long getPage() {
    return page;
  }

  public long getSize() {
    return size;
  }

  public List<T> getContents() {
    return contents;
  }

  public long getCount() {
    return count;
  }
}
