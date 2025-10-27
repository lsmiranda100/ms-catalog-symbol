package com.sigapi.catalog.exception;

/**
 * Excepción lanzada cuando se intenta crear un recurso duplicado
 */
public class DuplicateResourceException extends RuntimeException {
    
    public DuplicateResourceException(String message) {
        super(message);
    }
    
    public DuplicateResourceException(String resource, String field, Object value) {
        super(String.format("%s ya existe con %s: '%s'", resource, field, value));
    }
}


