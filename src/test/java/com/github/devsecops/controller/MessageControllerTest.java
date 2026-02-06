package com.github.devsecops.controller;

import com.github.devsecops.model.Message;
import com.github.devsecops.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for MessageController.
 */
@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    private Message testMessage;

    @BeforeEach
    void setUp() {
        testMessage = new Message(1L, "Test Title", "Test Content", "TestAuthor");
    }

    @Test
    void getAllMessages_ShouldReturnList() throws Exception {
        when(messageService.getAllMessages()).thenReturn(Arrays.asList(testMessage));

        mockMvc.perform(get("/api/v1/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Title"));
    }

    @Test
    void getMessageById_WhenExists_ShouldReturnMessage() throws Exception {
        when(messageService.getMessageById(1L)).thenReturn(Optional.of(testMessage));

        mockMvc.perform(get("/api/v1/messages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"));
    }

    @Test
    void getMessageById_WhenNotExists_ShouldReturn404() throws Exception {
        when(messageService.getMessageById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/messages/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createMessage_WithValidData_ShouldReturn201() throws Exception {
        when(messageService.createMessage(any(Message.class))).thenReturn(testMessage);

        mockMvc.perform(post("/api/v1/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Title\",\"content\":\"Test Content\",\"author\":\"TestAuthor\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Title"));
    }

    @Test
    void deleteMessage_ShouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/messages/1"))
                .andExpect(status().isNoContent());
    }
}
