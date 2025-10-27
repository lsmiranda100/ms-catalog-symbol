# ☕ Características Avanzadas de Java 17 Implementadas

Este documento detalla las características avanzadas de Java 17 y buenas prácticas implementadas en el microservicio.

## 📚 Tabla de Contenidos

1. [Records y Pattern Matching](#records-y-pattern-matching)
2. [Programación Concurrente](#programación-concurrente)
3. [Streams y Functional Programming](#streams-y-functional-programming)
4. [Text Blocks](#text-blocks)
5. [Principios SOLID](#principios-solid)
6. [Patrones de Diseño](#patrones-de-diseño)

---

## 🎯 Records y Pattern Matching

### Uso de Records (Java 14+)

Los DTOs utilizan Lombok para generar código similar a Records:

```java
@Data
@Builder
public class ObjectAssignedSymbolResponse {
    private Long id;
    private String objectId;
    private String symbolCode;
    // ... más campos
}
```

**Ventajas:**
- Inmutabilidad (con `@Value` en lugar de `@Data`)
- Menos boilerplate
- Generación automática de equals(), hashCode(), toString()

### Pattern Matching (Java 17)

Aunque no se usa explícitamente en este proyecto, Java 17 permite:

```java
// Ejemplo de pattern matching con instanceof
public String processObject(Object obj) {
    if (obj instanceof String s) {
        return s.toUpperCase();
    } else if (obj instanceof Integer i) {
        return "Number: " + i;
    }
    return "Unknown";
}
```

---

## ⚡ Programación Concurrente

### CompletableFuture (Java 8+, mejorado en 17)

Implementado en `ObjectAssignedSymbolService.java`:

```java
@Transactional(readOnly = true)
public CompletableFuture<List<ObjectAssignedSymbolResponse>> getActiveByObjectTypeAsync(String objectType) {
    log.debug("Buscando símbolos activos de forma asíncrona para objectType: {}", objectType);
    
    return CompletableFuture.supplyAsync(() -> {
        List<ObjectAssignedSymbol> entities = repository.findActiveByObjectType(objectType);
        return mapper.toResponseList(entities);
    });
}
```

**Características:**
- Ejecución asíncrona no bloqueante
- Composición de operaciones
- Manejo de excepciones en contextos asíncronos

### Ejemplo de Uso Avanzado de CompletableFuture

```java
// Composición de múltiples operaciones asíncronas
public CompletableFuture<CombinedResult> getCombinedData(String objectId) {
    CompletableFuture<List<Symbol>> symbolsFuture = 
        CompletableFuture.supplyAsync(() -> getSymbols(objectId));
    
    CompletableFuture<ObjectDetails> detailsFuture = 
        CompletableFuture.supplyAsync(() -> getObjectDetails(objectId));
    
    return symbolsFuture.thenCombine(detailsFuture, (symbols, details) -> 
        new CombinedResult(symbols, details)
    );
}
```

---

## 🌊 Streams y Functional Programming

### Uso de Streams en el Servicio

Implementado en `ObjectAssignedSymbolService.java`:

```java
@Transactional(readOnly = true)
public List<ObjectAssignedSymbolResponse> getActiveByObjectId(String objectId) {
    return repository.findByObjectIdAndIsActive(objectId, true)
            .stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
}
```

### Operaciones Avanzadas con Streams

```java
// Filtrado, mapeo y agrupación
public Map<String, List<ObjectAssignedSymbolResponse>> groupByObjectType() {
    return repository.findAll()
            .stream()
            .filter(ObjectAssignedSymbol::getIsActive)
            .map(mapper::toResponse)
            .collect(Collectors.groupingBy(
                ObjectAssignedSymbolResponse::getObjectType
            ));
}

// Ordenamiento y limitación
public List<ObjectAssignedSymbolResponse> getTopPrioritySymbols(int limit) {
    return repository.findAll()
            .stream()
            .sorted(Comparator.comparing(ObjectAssignedSymbol::getPriority).reversed())
            .limit(limit)
            .map(mapper::toResponse)
            .toList(); // Java 16+ - nuevo método
}

// Reducción para cálculos
public int getTotalPriority(String objectId) {
    return repository.findByObjectId(objectId)
            .stream()
            .mapToInt(ObjectAssignedSymbol::getPriority)
            .sum();
}
```

---

## 📝 Text Blocks (Java 13+)

Aunque no se usa extensivamente en este proyecto, Text Blocks simplifican strings multilínea:

```java
// Sin Text Blocks (tradicional)
String sql = "SELECT * FROM sigapiobjectassignedsymbol " +
             "WHERE object_id = ? " +
             "AND is_active = true " +
             "ORDER BY priority DESC";

// Con Text Blocks (Java 13+)
String sql = """
    SELECT * FROM sigapiobjectassignedsymbol
    WHERE object_id = ?
    AND is_active = true
    ORDER BY priority DESC
    """;

// Ejemplo en JSON
String json = """
    {
        "objectId": "USER-001",
        "symbolCode": "PREMIUM",
        "isActive": true
    }
    """;
```

---

## 🏗️ Principios SOLID

### Single Responsibility Principle (SRP)

Cada clase tiene una única responsabilidad:

```java
// Controller: Solo maneja HTTP requests/responses
@RestController
public class ObjectAssignedSymbolController {
    // Delega la lógica al servicio
}

// Service: Solo lógica de negocio
@Service
public class ObjectAssignedSymbolService {
    // No maneja HTTP, solo lógica
}

// Repository: Solo acceso a datos
@Repository
public interface ObjectAssignedSymbolRepository extends JpaRepository {
    // Solo consultas a BD
}
```

### Open/Closed Principle (OCP)

Las clases están abiertas para extensión pero cerradas para modificación:

```java
// Exception handling extensible
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(...) {
        // Manejo específico
    }
    
    // Fácil agregar nuevos handlers sin modificar existentes
    @ExceptionHandler(NewCustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleNewException(...) {
        // Nuevo manejo
    }
}
```

### Liskov Substitution Principle (LSP)

Las subclases pueden sustituir a sus clases base:

```java
// Excepciones personalizadas extienden RuntimeException
public class ResourceNotFoundException extends RuntimeException {
    // Puede usarse donde se espera RuntimeException
}

public class DuplicateResourceException extends RuntimeException {
    // Puede usarse donde se espera RuntimeException
}
```

### Interface Segregation Principle (ISP)

Interfaces específicas en lugar de generales:

```java
// JpaRepository proporciona solo métodos necesarios
public interface ObjectAssignedSymbolRepository extends JpaRepository<ObjectAssignedSymbol, Long> {
    // Métodos específicos del dominio
    List<ObjectAssignedSymbol> findByObjectId(String objectId);
    List<ObjectAssignedSymbol> findByObjectType(String objectType);
}
```

### Dependency Inversion Principle (DIP)

Depender de abstracciones, no de implementaciones concretas:

```java
@Service
@RequiredArgsConstructor
public class ObjectAssignedSymbolService {
    
    // Depende de la interfaz Repository, no de implementación concreta
    private final ObjectAssignedSymbolRepository repository;
    
    // Depende de abstracción Mapper, no de implementación
    private final ObjectAssignedSymbolMapper mapper;
}
```

---

## 🎨 Patrones de Diseño

### 1. Builder Pattern (con Lombok)

```java
@Builder
public class ObjectAssignedSymbol {
    private Long id;
    private String objectId;
    // ...
}

// Uso
ObjectAssignedSymbol symbol = ObjectAssignedSymbol.builder()
    .objectId("USER-001")
    .symbolCode("PREMIUM")
    .isActive(true)
    .build();
```

### 2. Repository Pattern

```java
// Abstrae el acceso a datos
@Repository
public interface ObjectAssignedSymbolRepository extends JpaRepository<ObjectAssignedSymbol, Long> {
    List<ObjectAssignedSymbol> findByObjectId(String objectId);
}

// Uso en servicio
@Service
public class ObjectAssignedSymbolService {
    private final ObjectAssignedSymbolRepository repository;
    
    public List<ObjectAssignedSymbol> getByObjectId(String objectId) {
        return repository.findByObjectId(objectId);
    }
}
```

### 3. DTO Pattern

```java
// Separa la capa de presentación de la capa de dominio
public class ObjectAssignedSymbolRequest {
    // Campos para entrada
}

public class ObjectAssignedSymbolResponse {
    // Campos para salida
}

// Entity separada
@Entity
public class ObjectAssignedSymbol {
    // Modelo de dominio
}
```

### 4. Mapper Pattern

```java
@Component
public class ObjectAssignedSymbolMapper {
    
    public ObjectAssignedSymbol toEntity(ObjectAssignedSymbolRequest request) {
        // Conversión Request -> Entity
    }
    
    public ObjectAssignedSymbolResponse toResponse(ObjectAssignedSymbol entity) {
        // Conversión Entity -> Response
    }
}
```

### 5. Exception Handler Pattern

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(...) {
        // Manejo centralizado
    }
}
```

---

## 🔧 Buenas Prácticas Implementadas

### 1. Inmutabilidad

```java
// Usar final para campos no modificables
@Service
@RequiredArgsConstructor
public class ObjectAssignedSymbolService {
    private final ObjectAssignedSymbolRepository repository; // final = inmutable
}
```

### 2. Null Safety

```java
// Usar Optional para evitar NullPointerException
public ObjectAssignedSymbolResponse getById(Long id) {
    ObjectAssignedSymbol entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Símbolo", "id", id));
    return mapper.toResponse(entity);
}
```

### 3. Method References

```java
// Usar referencias a métodos en lugar de lambdas cuando sea posible
.map(mapper::toResponse)  // En lugar de .map(e -> mapper.toResponse(e))
```

### 4. Logging Estructurado

```java
@Slf4j
public class ObjectAssignedSymbolService {
    
    public void create(ObjectAssignedSymbolRequest request) {
        log.info("Creando símbolo asignado para objectId: {} con symbolCode: {}", 
                request.getObjectId(), request.getSymbolCode());
    }
}
```

### 5. Validación con Jakarta Validation

```java
@NotBlank(message = "El objectId es obligatorio")
private String objectId;

@Min(value = 0, message = "La prioridad no puede ser negativa")
@Max(value = 100, message = "La prioridad no puede exceder 100")
private Integer priority;
```

### 6. Transacciones

```java
@Transactional  // Para operaciones de escritura
public ObjectAssignedSymbolResponse create(...) { }

@Transactional(readOnly = true)  // Para operaciones de solo lectura
public List<ObjectAssignedSymbolResponse> getAll() { }
```

---

## 📊 Optimización de Rendimiento

### 1. Lazy Loading

```java
// JPA lazy loading por defecto en colecciones
@OneToMany(fetch = FetchType.LAZY)
private List<RelatedEntity> relatedEntities;
```

### 2. Índices de Base de Datos

```sql
-- En schema.sql
CREATE INDEX idx_object_id ON sigapiobjectassignedsymbol(object_id);
CREATE INDEX idx_symbol_code ON sigapiobjectassignedsymbol(symbol_code);
```

### 3. Query Methods Optimizados

```java
// Spring Data JPA genera queries optimizadas
List<ObjectAssignedSymbol> findByObjectIdAndIsActive(String objectId, Boolean isActive);
```

---

## 🧪 Testing con JUnit 5

### Características Modernas de Testing

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("ObjectAssignedSymbolService - Pruebas Unitarias")
class ObjectAssignedSymbolServiceTest {
    
    @Test
    @DisplayName("Crear símbolo asignado - Exitoso")
    void create_Success() {
        // Arrange
        when(repository.save(any())).thenReturn(entity);
        
        // Act
        var result = service.create(request);
        
        // Assert
        assertThat(result).isNotNull();
    }
}
```

**Características:**
- `@DisplayName` para descripciones legibles
- Uso de `var` (Java 10+) para inferencia de tipos
- AssertJ para assertions fluidas

---

## 📈 Métricas y Monitoreo

### Spring Boot Actuator

```properties
# Exponer endpoints de métricas
management.endpoints.web.exposure.include=health,info,metrics
```

Endpoints disponibles:
- `/actuator/health` - Estado de salud
- `/actuator/metrics` - Métricas de rendimiento
- `/actuator/info` - Información de la aplicación

---

## 🎓 Recursos para Aprender Más

1. **Java 17 Features**: https://openjdk.org/projects/jdk/17/
2. **Spring Boot 3**: https://spring.io/projects/spring-boot
3. **SOLID Principles**: https://www.baeldung.com/solid-principles
4. **Java Concurrency**: https://docs.oracle.com/javase/tutorial/essential/concurrency/

---

**Este microservicio demuestra el uso profesional de Java 17 con Spring Boot, 
implementando las mejores prácticas y patrones de diseño modernos.**


