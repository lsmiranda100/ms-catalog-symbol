package com.sigapi.catalog.controller;

import com.sigapi.catalog.dto.ApiResponse;
import com.sigapi.catalog.dto.ObjectTypeRequest;
import com.sigapi.catalog.dto.ObjectTypeResponse;
import com.sigapi.catalog.service.ObjectTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para gestionar tipos de objetos (ObjectType)
 */
@RestController
@RequestMapping("/api/v1/object-types")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Object Types", description = "API para gestionar tipos de objetos de ARIS")
public class ObjectTypeController {

    private final ObjectTypeService service;

    /**
     * Crea un nuevo tipo de objeto
     */
    @PostMapping
    @Operation(summary = "Crear tipo de objeto", description = "Crea un nuevo tipo de objeto en el catálogo")
    public ResponseEntity<ApiResponse<ObjectTypeResponse>> create(@Valid @RequestBody ObjectTypeRequest request) {
        log.info("POST /api/v1/object-types - Creando nuevo ObjectType");
        ObjectTypeResponse response = service.create(request);
        
        ApiResponse<ObjectTypeResponse> apiResponse = ApiResponse.<ObjectTypeResponse>builder()
                .success(true)
                .message("ObjectType creado exitosamente")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Obtiene todos los tipos de objeto
     */
    @GetMapping
    @Operation(summary = "Listar todos los tipos de objeto", description = "Obtiene la lista completa de tipos de objeto")
    public ResponseEntity<ApiResponse<List<ObjectTypeResponse>>> findAll() {
        log.info("GET /api/v1/object-types - Obteniendo todos los ObjectTypes");
        List<ObjectTypeResponse> responses = service.findAll();
        
        ApiResponse<List<ObjectTypeResponse>> apiResponse = ApiResponse.<List<ObjectTypeResponse>>builder()
                .success(true)
                .message("Se encontraron " + responses.size() + " tipos de objeto")
                .data(responses)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Obtiene un tipo de objeto por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene un tipo de objeto específico por su ID")
    public ResponseEntity<ApiResponse<ObjectTypeResponse>> findById(@PathVariable Integer id) {
        log.info("GET /api/v1/object-types/{} - Buscando ObjectType por ID", id);
        ObjectTypeResponse response = service.findById(id);
        
        ApiResponse<ObjectTypeResponse> apiResponse = ApiResponse.<ObjectTypeResponse>builder()
                .success(true)
                .message("ObjectType encontrado")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Obtiene un tipo de objeto por apiName
     */
    @GetMapping("/api-name/{apiName}")
    @Operation(summary = "Buscar por nombre API", description = "Obtiene un tipo de objeto por su nombre API único")
    public ResponseEntity<ApiResponse<ObjectTypeResponse>> findByApiName(@PathVariable String apiName) {
        log.info("GET /api/v1/object-types/api-name/{} - Buscando ObjectType por apiName", apiName);
        ObjectTypeResponse response = service.findByApiName(apiName);
        
        ApiResponse<ObjectTypeResponse> apiResponse = ApiResponse.<ObjectTypeResponse>builder()
                .success(true)
                .message("ObjectType encontrado")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Busca tipos de objeto por nombre (parcial)
     */
    @GetMapping("/search")
    @Operation(summary = "Buscar por nombre", description = "Busca tipos de objeto cuyo nombre contenga el texto especificado")
    public ResponseEntity<ApiResponse<List<ObjectTypeResponse>>> searchByName(@RequestParam String name) {
        log.info("GET /api/v1/object-types/search?name={} - Buscando ObjectTypes", name);
        List<ObjectTypeResponse> responses = service.searchByObjectName(name);
        
        ApiResponse<List<ObjectTypeResponse>> apiResponse = ApiResponse.<List<ObjectTypeResponse>>builder()
                .success(true)
                .message("Se encontraron " + responses.size() + " resultados")
                .data(responses)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Obtiene tipos de objeto por defecto o asignados
     */
    @GetMapping("/default-or-assigned")
    @Operation(summary = "Buscar por defecto o asignados", description = "Obtiene tipos de objeto que sean por defecto o estén asignados")
    public ResponseEntity<ApiResponse<List<ObjectTypeResponse>>> findDefaultOrAssigned() {
        log.info("GET /api/v1/object-types/default-or-assigned - Buscando ObjectTypes por defecto o asignados");
        List<ObjectTypeResponse> responses = service.findDefaultOrAssigned();
        
        ApiResponse<List<ObjectTypeResponse>> apiResponse = ApiResponse.<List<ObjectTypeResponse>>builder()
                .success(true)
                .message("Se encontraron " + responses.size() + " tipos de objeto")
                .data(responses)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Actualiza un tipo de objeto existente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tipo de objeto", description = "Actualiza un tipo de objeto existente")
    public ResponseEntity<ApiResponse<ObjectTypeResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ObjectTypeRequest request) {
        log.info("PUT /api/v1/object-types/{} - Actualizando ObjectType", id);
        ObjectTypeResponse response = service.update(id, request);
        
        ApiResponse<ObjectTypeResponse> apiResponse = ApiResponse.<ObjectTypeResponse>builder()
                .success(true)
                .message("ObjectType actualizado exitosamente")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Elimina un tipo de objeto
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tipo de objeto", description = "Elimina un tipo de objeto del catálogo")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        log.info("DELETE /api/v1/object-types/{} - Eliminando ObjectType", id);
        service.delete(id);
        
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .message("ObjectType eliminado exitosamente")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }
}

