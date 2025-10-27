package com.sigapi.catalog.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * DTO de respuesta para tipos de objeto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjectTypeResponse {

    private Integer idObject;
    private String apiName;
    private String objectName;
    private String objectOrigName;
    private LocalDate createDate;
    private Integer isDefault;
    private Integer isAssigned;
    private LocalDate lastUpdate;
}

