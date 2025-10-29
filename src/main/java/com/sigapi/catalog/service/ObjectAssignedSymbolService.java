package com.sigapi.catalog.service;

import com.sigapi.catalog.dto.ObjectAssignedSymbolRequest;
import com.sigapi.catalog.dto.ObjectAssignedSymbolResponse;
import com.sigapi.catalog.entity.ObjectAssignedSymbol;
import com.sigapi.catalog.exception.ResourceAlreadyExistsException;
import com.sigapi.catalog.exception.ResourceNotFoundException;
import com.sigapi.catalog.mapper.ObjectAssignedSymbolMapper;
import com.sigapi.catalog.repository.ObjectAssignedSymbolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar ObjectAssignedSymbol
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ObjectAssignedSymbolService {

    private final ObjectAssignedSymbolRepository repository;
    private final ObjectAssignedSymbolMapper mapper;

    /**
     * Crea una nueva asignación
     */
    @Transactional
    public ObjectAssignedSymbolResponse create(ObjectAssignedSymbolRequest request) {
        log.info("Creando nueva asignación: idSymbol={}, idObject={}", request.getIdSymbol(), request.getIdObject());
        
        // Verificar si ya existe
        repository.findByIdSymbolAndIdObject(request.getIdSymbol(), request.getIdObject())
                .ifPresent(s -> {
                    throw new ResourceAlreadyExistsException(
                            "Ya existe una asignación para idSymbol: " + request.getIdSymbol() + 
                            " e idObject: " + request.getIdObject());
                });
        
        ObjectAssignedSymbol entity = mapper.toEntity(request);
        ObjectAssignedSymbol saved = repository.save(entity);
        log.info("Asignación creada exitosamente con ID: {}", saved.getIdObjectAssigned());
        
        return mapper.toResponse(saved);
    }

    /**
     * Obtiene todas las asignaciones
     */
    @Transactional(readOnly = true)
    public List<ObjectAssignedSymbolResponse> findAll() {
        log.info("Obteniendo todas las asignaciones");
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una asignación por ID
     */
    @Transactional(readOnly = true)
    public ObjectAssignedSymbolResponse findById(Integer id) {
        log.info("Buscando asignación por ID: {}", id);
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Asignación no encontrada con ID: " + id));
    }

    /**
     * Obtiene asignaciones por ID de símbolo
     */
    @Transactional(readOnly = true)
    public List<ObjectAssignedSymbolResponse> findByIdSymbol(Integer idSymbol) {
        log.info("Buscando asignaciones por idSymbol: {}", idSymbol);
        return repository.findByIdSymbol(idSymbol).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene asignaciones por ID de objeto
     */
    @Transactional(readOnly = true)
    public List<ObjectAssignedSymbolResponse> findByIdObject(Integer idObject) {
        log.info("Buscando asignaciones por idObject: {}", idObject);
        return repository.findByIdObject(idObject).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza una asignación
     */
    @Transactional
    public ObjectAssignedSymbolResponse update(Integer id, ObjectAssignedSymbolRequest request) {
        log.info("Actualizando asignación con ID: {}", id);
        
        ObjectAssignedSymbol entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Asignación no encontrada con ID: " + id));
        
        // Verificar si la nueva combinación ya existe
        if (!entity.getIdSymbol().equals(request.getIdSymbol()) || 
            !entity.getIdObject().equals(request.getIdObject())) {
            repository.findByIdSymbolAndIdObject(request.getIdSymbol(), request.getIdObject())
                    .ifPresent(s -> {
                        throw new ResourceAlreadyExistsException(
                                "Ya existe una asignación para idSymbol: " + request.getIdSymbol() + 
                                " e idObject: " + request.getIdObject());
                    });
        }
        
        mapper.updateEntityFromDto(request, entity);
        ObjectAssignedSymbol updated = repository.save(entity);
        log.info("Asignación actualizada exitosamente");
        
        return mapper.toResponse(updated);
    }

    /**
     * Elimina una asignación por ID
     */
    @Transactional
    public void delete(Integer id) {
        log.info("Eliminando asignación con ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Asignación no encontrada con ID: " + id);
        }
        repository.deleteById(id);
        log.info("Asignación eliminada exitosamente");
    }

    /**
     * Elimina todas las asignaciones de un símbolo
     */
    @Transactional
    public void deleteByIdSymbol(Integer idSymbol) {
        log.info("Eliminando asignaciones por idSymbol: {}", idSymbol);
        repository.deleteByIdSymbol(idSymbol);
        log.info("Asignaciones eliminadas exitosamente");
    }

    /**
     * Elimina todas las asignaciones de un objeto
     */
    @Transactional
    public void deleteByIdObject(Integer idObject) {
        log.info("Eliminando asignaciones por idObject: {}", idObject);
        repository.deleteByIdObject(idObject);
        log.info("Asignaciones eliminadas exitosamente");
    }
}

