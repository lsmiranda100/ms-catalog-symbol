package com.sigapi.catalog.controller;

import com.sigapi.catalog.dto.ApiResponse;
import com.sigapi.catalog.dto.SymbolTypeRequest;
import com.sigapi.catalog.dto.SymbolTypeResponse;
import com.sigapi.catalog.service.SymbolTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar tipos de símbolos
 */
@RestController
@RequestMapping("/api/v1/symbol-types")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Symbol Type", description = "API para gestionar catálogo de tipos de símbolos")
@CrossOrigin(origins = "*")
public class SymbolTypeController {

    private final SymbolTypeService service;

    /**
     * Crea un nuevo tipo de símbolo
     */
    @PostMapping
    @Operation(summary = "Crear tipo de símbolo", description = "Crea un nuevo tipo de símbolo en el catálogo")
    public ResponseEntity<ApiResponse<SymbolTypeResponse>> create(
            @Valid @RequestBody SymbolTypeRequest request) {
        
        log.info("POST /api/v1/symbol-types - Crear tipo de símbolo");
        SymbolTypeResponse response = service.create(request);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Tipo de símbolo creado exitosamente"));
    }

    /**
     * Obtiene un tipo de símbolo por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo de símbolo por ID")
    public ResponseEntity<ApiResponse<SymbolTypeResponse>> getById(
            @Parameter(description = "ID del tipo de símbolo")
            @PathVariable Integer id) {
        
        log.info("GET /api/v1/symbol-types/{}", id);
        SymbolTypeResponse response = service.getById(id);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Tipo de símbolo encontrado"));
    }

    /**
     * Obtiene todos los tipos de símbolo
     */
    @GetMapping
    @Operation(summary = "Obtener todos los tipos de símbolo")
    public ResponseEntity<ApiResponse<List<SymbolTypeResponse>>> getAll() {
        log.info("GET /api/v1/symbol-types");
        List<SymbolTypeResponse> responses = service.getAll();
        
        return ResponseEntity.ok(ApiResponse.success(responses, 
                String.format("Se encontraron %d tipos de símbolo", responses.size())));
    }

    /**
     * Busca tipo de símbolo por apiName
     */
    @GetMapping("/api-name/{apiName}")
    @Operation(summary = "Obtener tipo de símbolo por apiName")
    public ResponseEntity<ApiResponse<SymbolTypeResponse>> getByApiName(
            @Parameter(description = "API Name del tipo de símbolo")
            @PathVariable String apiName) {
        
        log.info("GET /api/v1/symbol-types/api-name/{}", apiName);
        SymbolTypeResponse response = service.getByApiName(apiName);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Tipo de símbolo encontrado"));
    }

    /**
     * Busca tipos de símbolo por nombre
     */
    @GetMapping("/search")
    @Operation(summary = "Buscar tipos de símbolo por nombre")
    public ResponseEntity<ApiResponse<List<SymbolTypeResponse>>> searchByName(
            @Parameter(description = "Nombre del tipo de símbolo (búsqueda parcial)")
            @RequestParam String name) {
        
        log.info("GET /api/v1/symbol-types/search?name={}", name);
        List<SymbolTypeResponse> responses = service.searchByName(name);
        
        return ResponseEntity.ok(ApiResponse.success(responses, 
                String.format("Se encontraron %d tipos de símbolo", responses.size())));
    }

    /**
     * Busca tipos de símbolo por tipo
     */
    @GetMapping("/by-type/{symbolType}")
    @Operation(summary = "Obtener tipos de símbolo por tipo")
    public ResponseEntity<ApiResponse<List<SymbolTypeResponse>>> getByType(
            @Parameter(description = "Tipo de símbolo")
            @PathVariable String symbolType) {
        
        log.info("GET /api/v1/symbol-types/by-type/{}", symbolType);
        List<SymbolTypeResponse> responses = service.getByType(symbolType);
        
        return ResponseEntity.ok(ApiResponse.success(responses, 
                String.format("Se encontraron %d tipos de símbolo", responses.size())));
    }

    /**
     * Obtiene tipos de símbolo por defecto o asignados
     */
    @GetMapping("/default-or-assigned")
    @Operation(summary = "Obtener tipos de símbolo por defecto o asignados")
    public ResponseEntity<ApiResponse<List<SymbolTypeResponse>>> getAllDefaultOrAssigned() {
        log.info("GET /api/v1/symbol-types/default-or-assigned");
        List<SymbolTypeResponse> responses = service.getAllDefaultOrAssigned();
        
        return ResponseEntity.ok(ApiResponse.success(responses, 
                String.format("Se encontraron %d tipos de símbolo", responses.size())));
    }

    /**
     * Actualiza un tipo de símbolo
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tipo de símbolo")
    public ResponseEntity<ApiResponse<SymbolTypeResponse>> update(
            @Parameter(description = "ID del tipo de símbolo")
            @PathVariable Integer id,
            @Valid @RequestBody SymbolTypeRequest request) {
        
        log.info("PUT /api/v1/symbol-types/{}", id);
        SymbolTypeResponse response = service.update(id, request);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Tipo de símbolo actualizado exitosamente"));
    }

    /**
     * Elimina un tipo de símbolo
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tipo de símbolo")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID del tipo de símbolo")
            @PathVariable Integer id) {
        
        log.info("DELETE /api/v1/symbol-types/{}", id);
        service.delete(id);
        
        return ResponseEntity.ok(ApiResponse.success(null, "Tipo de símbolo eliminado exitosamente"));
    }
}


