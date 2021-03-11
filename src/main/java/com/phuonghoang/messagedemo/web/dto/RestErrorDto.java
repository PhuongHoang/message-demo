package com.phuonghoang.messagedemo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

@Schema(description = "Class representing an error response.")
public class RestErrorDto {
    private final String type;
    private final String title;
    private final HttpStatus status;
    private final String detail;
    private final String instance;

    public RestErrorDto(String type, String title, HttpStatus status, String detail, String instance) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }

    @Schema(
            description = "The type of the error",
            example = "error/internal-error")
    public String getType() {
        return type;
    }

    @Schema(
            description = "The title of this error response",
            example = "Internal error")
    public String getTitle() {
        return title;
    }

    @Schema(
            description = "The HTTP status code",
            example = "400")
    public int getStatus() {
        return status.value();
    }

    @Schema(
            description = "A detailed reason of the error",
            example = "Cannot find message with ID 12345")
    public String getDetail() {
        return detail;
    }

    @Schema(
            description = "The path where the error occurs",
            example = "/v1/messages/3972e8aa-1ea6-4d47-9490-a4f78f772713")
    public String getInstance() {
        return instance;
    }
}
