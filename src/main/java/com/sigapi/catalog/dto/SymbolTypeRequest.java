package com.sigapi.catalog.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para crear o actualizar tipos de símbolo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SymbolTypeRequest {

    @NotBlank(message = "El apiName es obligatorio")
    @Size(max = 50, message = "El apiName no puede exceder 50 caracteres")
    private String apiName;

    @NotBlank(message = "El symbolName es obligatorio")
    @Size(max = 60, message = "El symbolName no puede exceder 60 caracteres")
    private String symbolName;

    @NotBlank(message = "El symbolType es obligatorio")
    @Size(max = 40, message = "El symbolType no puede exceder 40 caracteres")
    private String symbolType;

    @Size(max = 60, message = "El symbolOrigName no puede exceder 60 caracteres")
    private String symbolOrigName;

    @Min(value = 0, message = "isDefault debe ser 0 o 1")
    @Max(value = 1, message = "isDefault debe ser 0 o 1")
    private Integer isDefault;

    @Min(value = 0, message = "isAssigned debe ser 0 o 1")
    @Max(value = 1, message = "isAssigned debe ser 0 o 1")
    private Integer isAssigned;

    private String symbolImageBase64;
}


