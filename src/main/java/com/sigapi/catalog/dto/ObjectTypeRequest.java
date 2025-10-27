package com.sigapi.catalog.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para crear o actualizar tipos de objeto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjectTypeRequest {

    @NotBlank(message = "El apiName es obligatorio")
    @Size(max = 50, message = "El apiName no puede exceder 50 caracteres")
    private String apiName;

    @NotBlank(message = "El objectName es obligatorio")
    @Size(max = 60, message = "El objectName no puede exceder 60 caracteres")
    private String objectName;

    @Size(max = 60, message = "El objectOrigName no puede exceder 60 caracteres")
    private String objectOrigName;

    @Min(value = 0, message = "isDefault debe ser 0 o 1")
    @Max(value = 1, message = "isDefault debe ser 0 o 1")
    private Integer isDefault;

    @Min(value = 0, message = "isAssigned debe ser 0 o 1")
    @Max(value = 1, message = "isAssigned debe ser 0 o 1")
    private Integer isAssigned;
}

