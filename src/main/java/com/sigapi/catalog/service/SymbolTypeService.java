package com.sigapi.catalog.service;

import com.sigapi.catalog.dto.SymbolTypeRequest;
import com.sigapi.catalog.dto.SymbolTypeResponse;
import com.sigapi.catalog.entity.SymbolType;
import com.sigapi.catalog.exception.DuplicateResourceException;
import com.sigapi.catalog.exception.ResourceNotFoundException;
import com.sigapi.catalog.mapper.SymbolTypeMapper;
import com.sigapi.catalog.repository.SymbolTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestionar tipos de símbolos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SymbolTypeService {

    private final SymbolTypeRepository repository;
    private final SymbolTypeMapper mapper;

    /**
     * Crea un nuevo tipo de símbolo
     */
    public SymbolTypeResponse create(SymbolTypeRequest request) {
        log.info("Creando tipo de símbolo: {}", request.getApiName());

        if (repository.existsByApiName(request.getApiName())) {
            throw new DuplicateResourceException("SymbolType", "apiName", request.getApiName());
        }

        SymbolType entity = mapper.toEntity(request);
        SymbolType saved = repository.save(entity);
        
        log.info("Tipo de símbolo creado exitosamente con ID: {}", saved.getIdSymbol());
        return mapper.toResponse(saved);
    }

    /**
     * Obtiene un tipo de símbolo por ID
     */
    @Transactional(readOnly = true)
    public SymbolTypeResponse getById(Integer id) {
        SymbolType entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SymbolType", "id", id));
        return mapper.toResponse(entity);
    }

    /**
     * Obtiene todos los tipos de símbolo
     */
    @Transactional(readOnly = true)
    public List<SymbolTypeResponse> getAll() {
        List<SymbolType> entities = repository.findAll();
        return mapper.toResponseList(entities);
    }

    /**
     * Busca tipo de símbolo por apiName
     */
    @Transactional(readOnly = true)
    public SymbolTypeResponse getByApiName(String apiName) {
        SymbolType entity = repository.findByApiName(apiName)
                .orElseThrow(() -> new ResourceNotFoundException("SymbolType", "apiName", apiName));
        return mapper.toResponse(entity);
    }

    /**
     * Busca tipos de símbolo por nombre
     */
    @Transactional(readOnly = true)
    public List<SymbolTypeResponse> searchByName(String symbolName) {
        List<SymbolType> entities = repository.findBySymbolNameContainingIgnoreCase(symbolName);
        return mapper.toResponseList(entities);
    }

    /**
     * Busca tipos de símbolo por tipo
     */
    @Transactional(readOnly = true)
    public List<SymbolTypeResponse> getByType(String symbolType) {
        List<SymbolType> entities = repository.findBySymbolType(symbolType);
        return mapper.toResponseList(entities);
    }

    /**
     * Obtiene tipos de símbolo por defecto o asignados
     */
    @Transactional(readOnly = true)
    public List<SymbolTypeResponse> getAllDefaultOrAssigned() {
        List<SymbolType> entities = repository.findAllDefaultOrAssigned();
        return mapper.toResponseList(entities);
    }

    /**
     * Actualiza un tipo de símbolo
     */
    public SymbolTypeResponse update(Integer id, SymbolTypeRequest request) {
        log.info("Actualizando tipo de símbolo con ID: {}", id);

        SymbolType entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SymbolType", "id", id));

        // Validar apiName único si cambió
        if (!entity.getApiName().equals(request.getApiName()) && 
            repository.existsByApiName(request.getApiName())) {
            throw new DuplicateResourceException("SymbolType", "apiName", request.getApiName());
        }

        mapper.updateEntityFromRequest(entity, request);
        SymbolType updated = repository.save(entity);
        
        log.info("Tipo de símbolo actualizado exitosamente con ID: {}", id);
        return mapper.toResponse(updated);
    }

    /**
     * Elimina un tipo de símbolo
     */
    public void delete(Integer id) {
        log.info("Eliminando tipo de símbolo con ID: {}", id);
        
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("SymbolType", "id", id);
        }
        
        repository.deleteById(id);
        log.info("Tipo de símbolo eliminado exitosamente con ID: {}", id);
    }
}


