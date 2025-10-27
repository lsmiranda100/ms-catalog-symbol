package com.sigapi.catalog.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * DTO de respuesta para tipos de símbolo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SymbolTypeResponse {

    private Integer idSymbol;
    private String apiName;
    private String symbolName;
    private String symbolType;
    private String symbolOrigName;
    private LocalDate createDate;
    private Integer isDefault;
    private Integer isAssigned;
    private LocalDate lastUpdate;
    private String symbolImageBase64;
}
