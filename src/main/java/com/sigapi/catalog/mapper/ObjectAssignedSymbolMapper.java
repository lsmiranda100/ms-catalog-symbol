package com.sigapi.catalog.mapper;

import com.sigapi.catalog.dto.ObjectAssignedSymbolRequest;
import com.sigapi.catalog.dto.ObjectAssignedSymbolResponse;
import com.sigapi.catalog.entity.ObjectAssignedSymbol;
import org.springframework.stereotype.Component;

/**
 * Mapper para ObjectAssignedSymbol
 */
@Component
public class ObjectAssignedSymbolMapper {

    /**
     * Convierte Request a Entity
     */
    public ObjectAssignedSymbol toEntity(ObjectAssignedSymbolRequest request) {
        if (request == null) {
            return null;
        }
        
        return ObjectAssignedSymbol.builder()
                .idSymbol(request.getIdSymbol())
                .idObject(request.getIdObject())
                .build();
    }

    /**
     * Convierte Entity a Response
     */
    public ObjectAssignedSymbolResponse toResponse(ObjectAssignedSymbol entity) {
        if (entity == null) {
            return null;
        }
        
        return ObjectAssignedSymbolResponse.builder()
                .idObjectAssigned(entity.getIdObjectAssigned())
                .idSymbol(entity.getIdSymbol())
                .idObject(entity.getIdObject())
                .build();
    }

    /**
     * Actualiza Entity desde Request
     */
    public void updateEntityFromDto(ObjectAssignedSymbolRequest request, ObjectAssignedSymbol entity) {
        if (request == null || entity == null) {
            return;
        }
        
        if (request.getIdSymbol() != null) {
            entity.setIdSymbol(request.getIdSymbol());
        }
        if (request.getIdObject() != null) {
            entity.setIdObject(request.getIdObject());
        }
    }
}

