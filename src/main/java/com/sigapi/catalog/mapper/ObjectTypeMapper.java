package com.sigapi.catalog.mapper;

import com.sigapi.catalog.dto.ObjectTypeRequest;
import com.sigapi.catalog.dto.ObjectTypeResponse;
import com.sigapi.catalog.entity.ObjectType;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Entity y DTOs de ObjectType
 */
@Component
public class ObjectTypeMapper {

    /**
     * Convierte un ObjectTypeRequest a una entidad ObjectType
     */
    public ObjectType toEntity(ObjectTypeRequest request) {
        return ObjectType.builder()
                .apiName(request.getApiName())
                .objectName(request.getObjectName())
                .objectOrigName(request.getObjectOrigName())
                .isDefault(request.getIsDefault() != null ? request.getIsDefault() : 0)
                .isAssigned(request.getIsAssigned() != null ? request.getIsAssigned() : 0)
                .build();
    }

    /**
     * Convierte una entidad ObjectType a ObjectTypeResponse
     */
    public ObjectTypeResponse toResponse(ObjectType entity) {
        return ObjectTypeResponse.builder()
                .idObject(entity.getIdObject())
                .apiName(entity.getApiName())
                .objectName(entity.getObjectName())
                .objectOrigName(entity.getObjectOrigName())
                .createDate(entity.getCreateDate())
                .isDefault(entity.getIsDefault())
                .isAssigned(entity.getIsAssigned())
                .lastUpdate(entity.getLastUpdate())
                .build();
    }

    /**
     * Actualiza una entidad existente con los datos del request
     */
    public void updateEntity(ObjectType entity, ObjectTypeRequest request) {
        entity.setApiName(request.getApiName());
        entity.setObjectName(request.getObjectName());
        entity.setObjectOrigName(request.getObjectOrigName());
        
        if (request.getIsDefault() != null) {
            entity.setIsDefault(request.getIsDefault());
        }
        if (request.getIsAssigned() != null) {
            entity.setIsAssigned(request.getIsAssigned());
        }
    }
}

