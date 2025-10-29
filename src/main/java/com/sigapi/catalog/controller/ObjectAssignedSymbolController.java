package com.sigapi.catalog.controller;

import com.sigapi.catalog.dto.ApiResponse;
import com.sigapi.catalog.dto.ObjectAssignedSymbolRequest;
import com.sigapi.catalog.dto.ObjectAssignedSymbolResponse;
import com.sigapi.catalog.service.ObjectAssignedSymbolService;
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
 * Controlador REST para gestionar asignaciones de objetos a símbolos
 */
@RestController
@RequestMapping("/api/v1/object-assigned-symbols")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Object Assigned Symbols", description = "API para gestionar la asignación de objetos a símbolos")
public class ObjectAssignedSymbolController {

    private final ObjectAssignedSymbolService service;

    /**
     * Crear nueva asignación
     */
    @PostMapping
    @Operation(summary = "Crear asignación", description = "Crea una nueva asignación de objeto a símbolo")
    public ResponseEntity<ApiResponse<ObjectAssignedSymbolResponse>> create(
            @Valid @RequestBody ObjectAssignedSymbolRequest request) {
        log.info("POST /api/v1/object-assigned-symbols - Creando asignación");
        ObjectAssignedSymbolResponse response = service.create(request);
        
        ApiResponse<ObjectAssignedSymbolResponse> apiResponse = ApiResponse.<ObjectAssignedSymbolResponse>builder()
                .success(true)
                .message("Asignación creada exitosamente")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Obtener todas las asignaciones
     */
    @GetMapping
    @Operation(summary = "Listar asignaciones", description = "Obtiene todas las asignaciones")
    public ResponseEntity<ApiResponse<List<ObjectAssignedSymbolResponse>>> findAll() {
        log.info("GET /api/v1/object-assigned-symbols - Obteniendo todas las asignaciones");
        List<ObjectAssignedSymbolResponse> responses = service.findAll();
        
        ApiResponse<List<ObjectAssignedSymbolResponse>> apiResponse = 
                ApiResponse.<List<ObjectAssignedSymbolResponse>>builder()
                .success(true)
                .message("Se encontraron " + responses.size() + " asignaciones")
                .data(responses)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Obtener asignación por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene una asignación por su ID")
    public ResponseEntity<ApiResponse<ObjectAssignedSymbolResponse>> findById(@PathVariable Integer id) {
        log.info("GET /api/v1/object-assigned-symbols/{} - Buscando asignación", id);
        ObjectAssignedSymbolResponse response = service.findById(id);
        
        ApiResponse<ObjectAssignedSymbolResponse> apiResponse = ApiResponse.<ObjectAssignedSymbolResponse>builder()
                .success(true)
                .message("Asignación encontrada")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Obtener asignaciones por ID de símbolo
     */
    @GetMapping("/by-symbol/{idSymbol}")
    @Operation(summary = "Buscar por ID de símbolo", description = "Obtiene asignaciones por ID de símbolo")
    public ResponseEntity<ApiResponse<List<ObjectAssignedSymbolResponse>>> findByIdSymbol(
            @PathVariable Integer idSymbol) {
        log.info("GET /api/v1/object-assigned-symbols/by-symbol/{}", idSymbol);
        List<ObjectAssignedSymbolResponse> responses = service.findByIdSymbol(idSymbol);
        
        ApiResponse<List<ObjectAssignedSymbolResponse>> apiResponse = 
                ApiResponse.<List<ObjectAssignedSymbolResponse>>builder()
                .success(true)
                .message("Se encontraron " + responses.size() + " asignaciones")
                .data(responses)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Obtener asignaciones por ID de objeto
     */
    @GetMapping("/by-object/{idObject}")
    @Operation(summary = "Buscar por ID de objeto", description = "Obtiene asignaciones por ID de objeto")
    public ResponseEntity<ApiResponse<List<ObjectAssignedSymbolResponse>>> findByIdObject(
            @PathVariable Integer idObject) {
        log.info("GET /api/v1/object-assigned-symbols/by-object/{}", idObject);
        List<ObjectAssignedSymbolResponse> responses = service.findByIdObject(idObject);
        
        ApiResponse<List<ObjectAssignedSymbolResponse>> apiResponse = 
                ApiResponse.<List<ObjectAssignedSymbolResponse>>builder()
                .success(true)
                .message("Se encontraron " + responses.size() + " asignaciones")
                .data(responses)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Actualizar asignación
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar asignación", description = "Actualiza una asignación existente")
    public ResponseEntity<ApiResponse<ObjectAssignedSymbolResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ObjectAssignedSymbolRequest request) {
        log.info("PUT /api/v1/object-assigned-symbols/{} - Actualizando asignación", id);
        ObjectAssignedSymbolResponse response = service.update(id, request);
        
        ApiResponse<ObjectAssignedSymbolResponse> apiResponse = ApiResponse.<ObjectAssignedSymbolResponse>builder()
                .success(true)
                .message("Asignación actualizada exitosamente")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Eliminar asignación por ID
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar asignación", description = "Elimina una asignación por su ID")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        log.info("DELETE /api/v1/object-assigned-symbols/{} - Eliminando asignación", id);
        service.delete(id);
        
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .message("Asignación eliminada exitosamente")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Eliminar asignaciones por ID de símbolo
     */
    @DeleteMapping("/by-symbol/{idSymbol}")
    @Operation(summary = "Eliminar por símbolo", description = "Elimina todas las asignaciones de un símbolo")
    public ResponseEntity<ApiResponse<Void>> deleteByIdSymbol(@PathVariable Integer idSymbol) {
        log.info("DELETE /api/v1/object-assigned-symbols/by-symbol/{}", idSymbol);
        service.deleteByIdSymbol(idSymbol);
        
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .message("Asignaciones eliminadas exitosamente")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Eliminar asignaciones por ID de objeto
     */
    @DeleteMapping("/by-object/{idObject}")
    @Operation(summary = "Eliminar por objeto", description = "Elimina todas las asignaciones de un objeto")
    public ResponseEntity<ApiResponse<Void>> deleteByIdObject(@PathVariable Integer idObject) {
        log.info("DELETE /api/v1/object-assigned-symbols/by-object/{}", idObject);
        service.deleteByIdObject(idObject);
        
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .message("Asignaciones eliminadas exitosamente")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(apiResponse);
    }
}

