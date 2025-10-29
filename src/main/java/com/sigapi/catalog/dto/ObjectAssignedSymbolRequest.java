package com.sigapi.catalog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear y actualizar ObjectAssignedSymbol
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectAssignedSymbolRequest {

    @NotNull(message = "El ID del símbolo no puede ser nulo")
    private Integer idSymbol;

    @NotNull(message = "El ID del objeto no puede ser nulo")
    private Integer idObject;
}

