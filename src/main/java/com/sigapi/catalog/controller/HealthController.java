package com.sigapi.catalog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para health checks personalizados
 */
@RestController
@Slf4j
@Tag(name = "Health", description = "Endpoints de health check")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Health Check personalizado", description = "Verifica el estado del microservicio")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        log.debug("GET /health - Health check personalizado");
        
        Map<String, Object> health = new HashMap<>();
        health.put("service", "ms-catalog-symbol");
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("version", "1.0.0");
        health.put("java_version", System.getProperty("java.version"));
        
        Map<String, Object> features = new HashMap<>();
        features.put("crud_operations", true);
        features.put("async_support", true);
        features.put("validation", true);
        features.put("swagger_ui", true);
        
        health.put("features", features);
        
        return ResponseEntity.ok(health);
    }

    @GetMapping("/hello")
    @Operation(summary = "Hello World", description = "Endpoint de saludo básico")
    public ResponseEntity<String> hello() {
        log.debug("GET /hello - Hello endpoint");
        return ResponseEntity.ok("¡Hola desde el Microservicio de Catálogo de Símbolos con Spring Boot 3.3.4 y Java 17!");
    }
}


