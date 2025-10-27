package com.sigapi.catalog.repository;

import com.sigapi.catalog.entity.ObjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de ObjectType
 */
@Repository
public interface ObjectTypeRepository extends JpaRepository<ObjectType, Integer> {

    /**
     * Busca un tipo de objeto por su nombre API
     */
    Optional<ObjectType> findByApiName(String apiName);

    /**
     * Busca tipos de objeto por nombre (parcial, case-insensitive)
     */
    List<ObjectType> findByObjectNameContainingIgnoreCase(String objectName);

    /**
     * Busca tipos de objeto que sean por defecto o estén asignados
     */
    @Query("SELECT o FROM ObjectType o WHERE o.isDefault = 1 OR o.isAssigned = 1")
    List<ObjectType> findByIsDefaultOrIsAssigned();

    /**
     * Verifica si existe un tipo de objeto con el apiName dado
     */
    boolean existsByApiName(String apiName);
}

