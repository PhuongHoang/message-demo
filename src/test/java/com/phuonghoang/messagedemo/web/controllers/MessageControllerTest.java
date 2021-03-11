package com.phuonghoang.messagedemo.web.controllers;

import com.phuonghoang.messagedemo.services.MessageService;
import com.phuonghoang.messagedemo.web.dto.RequestMessageDto;
import com.phuonghoang.messagedemo.web.dto.MessageDto;
import com.phuonghoang.messagedemo.web.dto.PagedResponse;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    void getMessage() throws Exception {
        final Instant nowInTest = Instant.now(Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC));
        final MessageDto messageDto = new MessageDto("testId", "content", false, nowInTest, nowInTest);
        when(messageService.getMessage("testId")).thenReturn(messageDto);
        final MvcResult mvcResult = mockMvc.perform(get("/v1/messages/testId")).andExpect(status().isOk()).andReturn();

        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String expectedJson = """
                                    {
                                        "id": "testId",
                                        "content": "content",
                                        "palindrome": false,
                                        "createdAt":"2018-08-22T10:00:00Z",
                                        "updatedAt":"2018-08-22T10:00:00Z"
                                    }
                                    """;
        JSONAssert.assertEquals("The actual JSON does not match the expected JSON", expectedJson, actualResponseBody, JSONCompareMode.STRICT);
    }

    @Test
    void getMessage_noElementFound() throws Exception {
        when(messageService.getMessage("testId")).thenThrow(new NoSuchElementException("No message found for Id = testId"));
        final MvcResult mvcResult = mockMvc.perform(get("/v1/messages/testId")).andExpect(status().isNotFound()).andReturn();

        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String expectedJson = """
                                    {
                                        "type": "error/not-found",
                                        "title": "No element found",
                                        "status": 404,
                                        "detail": "No message found for Id = testId",
                                        "instance": "/v1/messages/testId"
                                    }
                                    """;
        JSONAssert.assertEquals("The actual JSON does not match the expected JSON", expectedJson, actualResponseBody, JSONCompareMode.STRICT);
    }

    @Test
    void retrieveMessages_emptyList() throws Exception {
        when(messageService.retrieveMessages(any(Pageable.class))).thenReturn(new PagedResponse<>(List.of(), 0, 0, 0));
        final MvcResult mvcResult = mockMvc.perform(get("/v1/messages?page=0&size=10")).andExpect(status().isOk()).andReturn();

        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String expectedJson = """
                                    {
                                        "page": 0,
                                        "size": 0,
                                        "count": 0,
                                        "contents": []
                                    }
                                    """;
        JSONAssert.assertEquals("The actual JSON does not match the expected JSON", expectedJson, actualResponseBody, JSONCompareMode.STRICT);
    }

    @Test
    void retrieveMessages_singleItem() throws Exception {
        final Instant nowInTest = Instant.now(Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC));
        when(messageService.retrieveMessages(any(Pageable.class))).thenReturn(new PagedResponse<>(List.of(new MessageDto("testId", "test cont", false, nowInTest, nowInTest)), 0, 1, 1));
        final MvcResult mvcResult = mockMvc.perform(get("/v1/messages?page=0&size=10")).andExpect(status().isOk()).andReturn();

        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String expectedJson = """
                                    {
                                        "page": 0,
                                        "size": 1,
                                        "count": 1,
                                        "contents": [
                                            {
                                                "id": "testId",
                                                "content": "test cont",
                                                "palindrome": false,
                                                "createdAt":"2018-08-22T10:00:00Z",
                                                "updatedAt":"2018-08-22T10:00:00Z"
                                            }
                                        ]
                                    }
                                    """;
        JSONAssert.assertEquals("The actual JSON does not match the expected JSON", expectedJson, actualResponseBody, JSONCompareMode.STRICT);
    }

    @Test
    void deleteMessage() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/v1/messages/testId")).andExpect(status().isOk()).andReturn();

        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        then(actualResponseBody).isEmpty();
    }

    @Test
    void deleteMessage_noElementFound() throws Exception {
        doThrow(new NoSuchElementException("No message found for Id = testId")).when(messageService).deleteMessage("testId");
        final MvcResult mvcResult = mockMvc.perform(delete("/v1/messages/testId")).andExpect(status().isNotFound()).andReturn();

        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String expectedJson = """
                                    {
                                        "type": "error/not-found",
                                        "title": "No element found",
                                        "status": 404,
                                        "detail": "No message found for Id = testId",
                                        "instance": "/v1/messages/testId"
                                    }
                                    """;
        JSONAssert.assertEquals("The actual JSON does not match the expected JSON", expectedJson, actualResponseBody, JSONCompareMode.STRICT);
    }

    @Test
    void putMessage_noElementFound() throws Exception {
        when(messageService.updateMessage("testId", new RequestMessageDto("content"))).thenThrow(new NoSuchElementException("No message found for Id = testId"));
        final String requestBody = """
                                   {
                                        "id": "testId",
                                        "content": "content",
                                        "palindrome": false
                                    }
                                   """;
        final MvcResult mvcResult = mockMvc.perform(put("/v1/messages/testId").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isNotFound()).andReturn();

        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String expectedJson = """
                                    {
                                        "type": "error/not-found",
                                        "title": "No element found",
                                        "status": 404,
                                        "detail": "No message found for Id = testId",
                                        "instance": "/v1/messages/testId"
                                    }
                                    """;
        JSONAssert.assertEquals("The actual JSON does not match the expected JSON", expectedJson, actualResponseBody, JSONCompareMode.STRICT);
    }

    @Test
    void putMessage() throws Exception {
        final Instant nowInTest = Instant.now(Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC));
        final MessageDto messageDto = new MessageDto("testId", "content", false, nowInTest, nowInTest);
        when(messageService.updateMessage("testId", new RequestMessageDto("content"))).thenReturn(messageDto);

        final String requestBody = """
                                   {
                                        "content": "content"
                                    }
                                   """;
        final MvcResult mvcResult = mockMvc.perform(put("/v1/messages/testId").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isOk()).andReturn();

        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String expectedJson = """
                                    {
                                        "id": "testId",
                                        "content": "content",
                                        "palindrome": false,
                                        "createdAt":"2018-08-22T10:00:00Z",
                                        "updatedAt":"2018-08-22T10:00:00Z"
                                    }
                                    """;
        JSONAssert.assertEquals("The actual JSON does not match the expected JSON", expectedJson, actualResponseBody, JSONCompareMode.STRICT);
    }

    @Test
    void createMessage() throws Exception {
        final Instant nowInTest = Instant.now(Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC));
        final RequestMessageDto messageDto = new RequestMessageDto("content");
        when(messageService.createMessage(messageDto)).thenReturn(new MessageDto("testId", "content", false, nowInTest, nowInTest));

        final String requestBody = """
                                   {
                                        "content": "content"
                                    }
                                   """;
        final MvcResult mvcResult = mockMvc.perform(post("/v1/messages").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isOk()).andReturn();

        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String expectedJson = """
                                    {
                                        "id": "testId",
                                        "content": "content",
                                        "palindrome": false,
                                        "createdAt":"2018-08-22T10:00:00Z",
                                        "updatedAt":"2018-08-22T10:00:00Z"
                                    }
                                    """;
        JSONAssert.assertEquals("The actual JSON does not match the expected JSON", expectedJson, actualResponseBody, JSONCompareMode.STRICT);
    }
}