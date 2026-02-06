package com.github.devsecops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application entry point for GitHub DevSecOps Demo.
 * 
 * This application demonstrates GitHub's comprehensive DevSecOps capabilities:
 * - SAST with CodeQL
 * - SCA with Dependency-Check and Trivy
 * - DAST with OWASP ZAP
 * - Container scanning with Trivy
 * - Artifact management with GitHub Packages
 * - Container registry with GHCR
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
