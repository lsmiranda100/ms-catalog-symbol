package com.sigapi.catalog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidad que representa la tabla sigapiobjecttype
 * Catálogo de tipos de objetos de ARIS
 */
@Entity
@Table(name = "sigapiobjecttype")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ObjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idobject")
    private Integer idObject;

    @Column(name = "apiname", nullable = false, length = 50, unique = true)
    private String apiName;

    @Column(name = "objectname", nullable = false, length = 60)
    private String objectName;

    @Column(name = "objectorigname", length = 60)
    private String objectOrigName;

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

