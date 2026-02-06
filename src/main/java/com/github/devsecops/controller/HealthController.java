package com.github.devsecops.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

/**
 * Health check controller for monitoring and DAST scanning.
 */
@RestController
public class HealthController {

    /**
     * Basic health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", Instant.now().toString(),
                "application", "GitHub DevSecOps Demo",
                "version", "1.0.0"
        ));
    }

    /**
     * Readiness probe for Kubernetes deployments.
     */
    @GetMapping("/ready")
    public ResponseEntity<Map<String, String>> ready() {
        return ResponseEntity.ok(Map.of(
                "status", "READY"
        ));
    }

    /**
     * Liveness probe for Kubernetes deployments.
     */
    @GetMapping("/live")
    public ResponseEntity<Map<String, String>> live() {
        return ResponseEntity.ok(Map.of(
                "status", "ALIVE"
        ));
    }
}
