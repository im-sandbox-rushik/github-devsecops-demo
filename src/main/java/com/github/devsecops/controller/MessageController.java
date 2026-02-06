package com.github.devsecops.controller;

import com.github.devsecops.model.Message;
import com.github.devsecops.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for message operations.
 * Demonstrates secure coding practices for CodeQL scanning.
 */
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Get all messages.
     */
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    /**
     * Get message by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new message.
     */
    @PostMapping
    public ResponseEntity<Message> createMessage(@Valid @RequestBody Message message) {
        Message created = messageService.createMessage(message);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Delete a message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
