package com.github.devsecops.service;

import com.github.devsecops.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service layer for message operations.
 * Uses in-memory storage for demo purposes.
 */
@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    
    private final Map<Long, Message> messages = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public MessageService() {
        // Add some sample data
        createMessage(new Message(null, "Welcome", "Welcome to GitHub DevSecOps Demo!", "System"));
        createMessage(new Message(null, "Security First", "This app demonstrates secure coding practices.", "System"));
    }

    /**
     * Get all messages.
     */
    public List<Message> getAllMessages() {
        logger.info("Fetching all messages, count: {}", messages.size());
        return new ArrayList<>(messages.values());
    }

    /**
     * Get message by ID.
     */
    public Optional<Message> getMessageById(Long id) {
        if (id == null) {
            logger.warn("Attempted to fetch message with null ID");
            return Optional.empty();
        }
        logger.info("Fetching message with ID: {}", sanitizeLogInput(String.valueOf(id)));
        return Optional.ofNullable(messages.get(id));
    }

    /**
     * Create a new message.
     */
    public Message createMessage(Message message) {
        Long id = idGenerator.getAndIncrement();
        message.setId(id);
        message.setCreatedAt(Instant.now());
        message.setUpdatedAt(Instant.now());
        
        messages.put(id, message);
        logger.info("Created message with ID: {}", id);
        
        return message;
    }

    /**
     * Delete a message by ID.
     */
    public void deleteMessage(Long id) {
        if (id != null && messages.containsKey(id)) {
            messages.remove(id);
            logger.info("Deleted message with ID: {}", sanitizeLogInput(String.valueOf(id)));
        } else {
            logger.warn("Attempted to delete non-existent message with ID: {}", 
                    sanitizeLogInput(String.valueOf(id)));
        }
    }

    /**
     * Sanitize input for logging to prevent log injection attacks.
     * This is a security best practice detected by CodeQL.
     */
    private String sanitizeLogInput(String input) {
        if (input == null) {
            return "null";
        }
        return input.replaceAll("[\\r\\n]", "_");
    }
}
