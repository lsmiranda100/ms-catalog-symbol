package com.sigapi.catalog.mapper;

import com.sigapi.catalog.dto.SymbolTypeRequest;
import com.sigapi.catalog.dto.SymbolTypeResponse;
import com.sigapi.catalog.entity.SymbolType;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre entidades y DTOs de SymbolType
 */
@Component
public class SymbolTypeMapper {

    public SymbolType toEntity(SymbolTypeRequest request) {
        SymbolType.SymbolTypeBuilder builder = SymbolType.builder()
                .apiName(request.getApiName())
                .symbolName(request.getSymbolName())
                .symbolType(request.getSymbolType())
                .symbolOrigName(request.getSymbolOrigName())
                .isDefault(request.getIsDefault() != null ? request.getIsDefault() : 0)
                .isAssigned(request.getIsAssigned() != null ? request.getIsAssigned() : 0);

        // Convertir imagen Base64 a bytes si existe
        if (request.getSymbolImageBase64() != null && !request.getSymbolImageBase64().isEmpty()) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(request.getSymbolImageBase64());
                builder.symbolImage(imageBytes);
            } catch (IllegalArgumentException e) {
                // Si hay error en decodificación, ignorar la imagen
            }
        }

        return builder.build();
    }

    public SymbolTypeResponse toResponse(SymbolType entity) {
        SymbolTypeResponse.SymbolTypeResponseBuilder builder = SymbolTypeResponse.builder()
                .idSymbol(entity.getIdSymbol())
                .apiName(entity.getApiName())
                .symbolName(entity.getSymbolName())
                .symbolType(entity.getSymbolType())
                .symbolOrigName(entity.getSymbolOrigName())
                .createDate(entity.getCreateDate())
                .isDefault(entity.getIsDefault())
                .isAssigned(entity.getIsAssigned())
                .lastUpdate(entity.getLastUpdate());

        // Convertir imagen a Base64 si existe
        if (entity.getSymbolImage() != null && entity.getSymbolImage().length > 0) {
            String base64Image = Base64.getEncoder().encodeToString(entity.getSymbolImage());
            builder.symbolImageBase64(base64Image);
        }

        return builder.build();
    }

    public List<SymbolTypeResponse> toResponseList(List<SymbolType> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(SymbolType entity, SymbolTypeRequest request) {
        entity.setApiName(request.getApiName());
        entity.setSymbolName(request.getSymbolName());
        entity.setSymbolType(request.getSymbolType());
        entity.setSymbolOrigName(request.getSymbolOrigName());
        
        if (request.getIsDefault() != null) {
            entity.setIsDefault(request.getIsDefault());
        }
        if (request.getIsAssigned() != null) {
            entity.setIsAssigned(request.getIsAssigned());
        }

        // Actualizar imagen si se proporciona
        if (request.getSymbolImageBase64() != null && !request.getSymbolImageBase64().isEmpty()) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(request.getSymbolImageBase64());
                entity.setSymbolImage(imageBytes);
            } catch (IllegalArgumentException e) {
                // Si hay error en decodificación, mantener imagen existente
            }
        }
    }
}
