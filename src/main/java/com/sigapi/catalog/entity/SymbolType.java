package com.sigapi.catalog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidad que representa la tabla sigapisymboltype
 * Catálogo de tipos de símbolos de ARIS
 */
@Entity
@Table(name = "sigapisymboltype", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SymbolType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsymbol")
    private Integer idSymbol;

    @Column(name = "apiname", nullable = false, length = 50, unique = true)
    private String apiName;

    @Column(name = "symbolname", nullable = false, length = 60)
    private String symbolName;

    @Column(name = "symboltype", nullable = false, length = 40)
    private String symbolType;

    @Column(name = "symbolorigname", length = 60)
    private String symbolOrigName;

    @Column(name = "createdate")
    private LocalDate createDate;

    @Column(name = "isdefault")
    @Builder.Default
    private Integer isDefault = 0;

    @Column(name = "isassigned")
    @Builder.Default
    private Integer isAssigned = 0;

    @Column(name = "lastupdate")
    private LocalDate lastUpdate;

    @Column(name = "symbolimage", columnDefinition = "bytea")
    private byte[] symbolImage;

    @PrePersist
    protected void onCreate() {
        if (createDate == null) {
            createDate = LocalDate.now();
        }
        if (isDefault == null) {
            isDefault = 0;
        }
        if (isAssigned == null) {
            isAssigned = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDate.now();
    }
}
