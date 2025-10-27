package com.sigapi.catalog.repository;

import com.sigapi.catalog.entity.SymbolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para la entidad SymbolType
 */
@Repository
public interface SymbolTypeRepository extends JpaRepository<SymbolType, Integer> {

    /**
     * Busca tipo de símbolo por nombre de API
     */
    Optional<SymbolType> findByApiName(String apiName);

    /**
     * Busca tipos de símbolo por nombre
     */
    List<SymbolType> findBySymbolNameContainingIgnoreCase(String symbolName);

    /**
     * Busca tipos de símbolo por tipo
     */
    List<SymbolType> findBySymbolType(String symbolType);

    /**
     * Busca tipos de símbolo por estado default
     */
    List<SymbolType> findByIsDefault(Integer isDefault);

    /**
     * Busca tipos de símbolo asignados
     */
    List<SymbolType> findByIsAssigned(Integer isAssigned);

    /**
     * Verifica si existe un tipo de símbolo con el apiName
     */
    boolean existsByApiName(String apiName);

    /**
     * Obtiene todos los tipos de símbolo por defecto y asignados
     */
    @Query("SELECT s FROM SymbolType s WHERE s.isDefault = 1 OR s.isAssigned = 1")
    List<SymbolType> findAllDefaultOrAssigned();
}


