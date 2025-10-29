package com.sigapi.catalog.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa la tabla sigapiobjectassignedsymbol
 * Relación entre ObjectType y SymbolType
 */
@Entity
@Table(name = "sigapiobjectassignedsymbol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ObjectAssignedSymbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idobjectassigned")
    private Integer idObjectAssigned;

    @Column(name = "idsymbol", nullable = false)
    private Integer idSymbol;

    @Column(name = "idobject", nullable = false)
    private Integer idObject;
}

