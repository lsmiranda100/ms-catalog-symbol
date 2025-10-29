package com.sigapi.catalog.repository;

import com.sigapi.catalog.entity.ObjectAssignedSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad ObjectAssignedSymbol
 */
@Repository
public interface ObjectAssignedSymbolRepository extends JpaRepository<ObjectAssignedSymbol, Integer> {

    /**
     * Encuentra todas las asignaciones por ID de símbolo
     */
    List<ObjectAssignedSymbol> findByIdSymbol(Integer idSymbol);

    /**
     * Encuentra todas las asignaciones por ID de objeto
     */
    List<ObjectAssignedSymbol> findByIdObject(Integer idObject);

    /**
     * Encuentra una asignación específica por ID de símbolo e ID de objeto
     */
    Optional<ObjectAssignedSymbol> findByIdSymbolAndIdObject(Integer idSymbol, Integer idObject);

    /**
     * Elimina todas las asignaciones por ID de símbolo
     */
    void deleteByIdSymbol(Integer idSymbol);

    /**
     * Elimina todas las asignaciones por ID de objeto
     */
    void deleteByIdObject(Integer idObject);
}

