package com.sigapi.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuestas de ObjectAssignedSymbol
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectAssignedSymbolResponse {

    private Integer idObjectAssigned;
    private Integer idSymbol;
    private Integer idObject;
}

