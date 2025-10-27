package com.sigapi.catalog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Clase principal del microservicio de catálogo de símbolos
 * 
 * Microservicio CRUD para gestionar símbolos asignados a objetos de la API
 * Desarrollado con Spring Boot 3.3.4 y Java 17
 * 
 * Características principales:
 * - Operaciones CRUD completas
 * - Validación de datos con Jakarta Validation
 * - Manejo global de excepciones
 * - Documentación con OpenAPI/Swagger
 * - Soporte para operaciones asíncronas
 * - Implementación de principios SOLID
 * - Uso de características avanzadas de Java 17
 * 
 * @author SIGAPI Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableAsync
@Slf4j
@OpenAPIDefinition(
        info = @Info(
                title = "MS Catalog Symbol API",
                version = "1.0.0",
                description = "Microservicio para gestionar catálogo de símbolos asignados a objetos. " +
                             "Proporciona operaciones CRUD completas con validaciones, manejo de excepciones " +
                             "y soporte para operaciones asíncronas.",
                contact = @Contact(
                        name = "SIGAPI Team",
                        email = "support@sigapi.com"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor de desarrollo"
                ),
                @Server(
                        url = "https://api.sigapi.com",
                        description = "Servidor de producción"
                )
        }
)
public class MsCatalogSymbolApplication {

    public static void main(String[] args) {
        log.info("╔════════════════════════════════════════════════════════════════╗");
        log.info("║   Iniciando MS Catalog Symbol                                  ║");
        log.info("║   Spring Boot 3.3.4 | Java 17                                  ║");
        log.info("╚════════════════════════════════════════════════════════════════╝");
        
        SpringApplication.run(MsCatalogSymbolApplication.class, args);
        
        log.info("╔════════════════════════════════════════════════════════════════╗");
        log.info("║   MS Catalog Symbol iniciado exitosamente                      ║");
        log.info("║   Swagger UI: http://localhost:8080/swagger-ui.html            ║");
        log.info("║   API Docs:   http://localhost:8080/api-docs                   ║");
        log.info("║   Health:     http://localhost:8080/health                     ║");
        log.info("║   Actuator:   http://localhost:8080/actuator/health            ║");
        log.info("╚════════════════════════════════════════════════════════════════╝");
    }
}


