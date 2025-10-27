package com.sigapi.catalog.service;

import com.sigapi.catalog.dto.ObjectTypeRequest;
import com.sigapi.catalog.dto.ObjectTypeResponse;
import com.sigapi.catalog.entity.ObjectType;
import com.sigapi.catalog.exception.DuplicateResourceException;
import com.sigapi.catalog.exception.ResourceNotFoundException;
import com.sigapi.catalog.mapper.ObjectTypeMapper;
import com.sigapi.catalog.repository.ObjectTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la lógica de negocio de ObjectType
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ObjectTypeService {

    private final ObjectTypeRepository repository;
    private final ObjectTypeMapper mapper;

    /**
     * Crea un nuevo tipo de objeto
     */
    @Transactional
    public ObjectTypeResponse create(ObjectTypeRequest request) {
        log.info("Creando nuevo ObjectType con apiName: {}", request.getApiName());
        
        if (repository.existsByApiName(request.getApiName())) {
            throw new DuplicateResourceException(
                    "Ya existe un ObjectType con apiName: '" + request.getApiName() + "'");
        }
        
        ObjectType entity = mapper.toEntity(request);
        ObjectType saved = repository.save(entity);
        
        log.info("ObjectType creado exitosamente con ID: {}", saved.getIdObject());
        return mapper.toResponse(saved);
    }

    /**
     * Obtiene todos los tipos de objeto
     */
    @Transactional(readOnly = true)
    public List<ObjectTypeResponse> findAll() {
        log.info("Obteniendo todos los ObjectTypes");
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un tipo de objeto por ID
     */
    @Transactional(readOnly = true)
    public ObjectTypeResponse findById(Integer id) {
        log.info("Buscando ObjectType con ID: {}", id);
        ObjectType entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ObjectType no encontrado con id: '" + id + "'"));
        return mapper.toResponse(entity);
    }

    /**
     * Obtiene un tipo de objeto por apiName
     */
    @Transactional(readOnly = true)
    public ObjectTypeResponse findByApiName(String apiName) {
        log.info("Buscando ObjectType con apiName: {}", apiName);
        ObjectType entity = repository.findByApiName(apiName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ObjectType no encontrado con apiName: '" + apiName + "'"));
        return mapper.toResponse(entity);
    }

    /**
     * Busca tipos de objeto por nombre (parcial)
     */
    @Transactional(readOnly = true)
    public List<ObjectTypeResponse> searchByObjectName(String name) {
        log.info("Buscando ObjectTypes con nombre que contenga: {}", name);
        return repository.findByObjectNameContainingIgnoreCase(name).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene tipos de objeto por defecto o asignados
     */
    @Transactional(readOnly = true)
    public List<ObjectTypeResponse> findDefaultOrAssigned() {
        log.info("Buscando ObjectTypes por defecto o asignados");
        return repository.findByIsDefaultOrIsAssigned().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un tipo de objeto existente
     */
    @Transactional
    public ObjectTypeResponse update(Integer id, ObjectTypeRequest request) {
        log.info("Actualizando ObjectType con ID: {}", id);
        
        ObjectType entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ObjectType no encontrado con id: '" + id + "'"));
        
        // Validar que el apiName no esté duplicado (si cambió)
        if (!entity.getApiName().equals(request.getApiName()) && 
            repository.existsByApiName(request.getApiName())) {
            throw new DuplicateResourceException(
                    "Ya existe un ObjectType con apiName: '" + request.getApiName() + "'");
        }
        
        mapper.updateEntity(entity, request);
        ObjectType updated = repository.save(entity);
        
        log.info("ObjectType actualizado exitosamente con ID: {}", id);
        return mapper.toResponse(updated);
    }

    /**
     * Elimina un tipo de objeto
     */
    @Transactional
    public void delete(Integer id) {
        log.info("Eliminando ObjectType con ID: {}", id);
        
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "ObjectType no encontrado con id: '" + id + "'");
        }
        
        repository.deleteById(id);
        log.info("ObjectType eliminado exitosamente con ID: {}", id);
    }
}

