# CONTEXT.md

## Lenguaje y versión
- **Lenguaje:** Java 17  
- **Versión:** 17  
- **Tema principal:** Advanced Java Concepts  

---

## Introducción al lenguaje y características avanzadas
### Características principales de Java 17
Java 17 introduce características como *pattern matching*, *sealed classes*, y mejoras de rendimiento en el garbage collector (ZGC). También se enfoca en la estabilidad de la API estándar y nuevas capacidades en la JVM.

### Manejo de versiones y compatibilidad hacia atrás
Java 17 es una versión **LTS (Long-Term Support)**, lo que significa que recibirá soporte extendido y actualizaciones de seguridad durante un largo período, facilitando la transición desde versiones anteriores.

---

## Buenas prácticas en Java
### Principios SOLID en Java
Los principios SOLID son fundamentales para escribir código mantenible y extensible. Se aplican con énfasis en la separación de responsabilidades, la inversión de dependencias y el diseño orientado a objetos.

### Uso adecuado de Streams
Java 17 mejora el rendimiento de las operaciones con *Streams*. Es importante evitar su uso innecesario cuando un bucle tradicional sea más eficiente.

### Inyección de dependencias (DI) en Java
La inyección de dependencias permite lograr un diseño desacoplado. Frameworks como **Spring** y **Jakarta EE** facilitan esta práctica.

---

## Patrones de diseño
### Patrón Singleton
Asegura que una clase tenga solo una instancia y proporciona un punto de acceso global. Puede implementarse de forma *thread-safe* usando inicialización perezosa (*lazy initialization*).

### Patrón Factory
Permite instanciar objetos sin especificar la clase exacta. Es útil para crear objetos de diferentes tipos bajo condiciones dinámicas.

---

## Manejo de excepciones avanzadas
### Excepciones personalizadas
Se crean cuando las excepciones estándar no son suficientes. Se pueden extender de `Exception` (chequeadas) o `RuntimeException` (no chequeadas).

### Excepciones en bloques concurrentes
En entornos multihilo, deben manejarse con cuidado. Herramientas como `CompletableFuture` o `ExecutorService` ayudan a capturar y gestionar excepciones sin perder control del flujo.

---

## Optimización y rendimiento
### Uso eficiente de memoria
Utilizar herramientas como **JVisualVM** o el **ZGC** (Z Garbage Collector). También es clave elegir tipos de datos adecuados y liberar recursos no utilizados.

### Evitar creación excesiva de objetos
La creación innecesaria de objetos impacta en el GC. Se recomienda usar *object pooling* o patrones como **Flyweight**.

---

## Uso avanzado de APIs y librerías
### Java NIO (New I/O)
Proporciona APIs más eficientes que las I/O tradicionales, usando **buffers** y **canales** para manejar grandes volúmenes de datos.

### APIs de concurrencia
Java 17 incluye utilidades como `ExecutorService`, `CountDownLatch` y `CyclicBarrier` que facilitan la programación concurrente de alto rendimiento.

---

## Casos de uso y ejemplos prácticos
### Implementación de un servidor HTTP simple
Puedes crear un servidor HTTP básico utilizando las APIs de red y I/O de Java. Útil para aprender sobre sockets y concurrencia.

### Implementación del patrón Reactor
Ideal para manejar I/O no bloqueante en aplicaciones de alto rendimiento, utilizando `java.nio.channels`.

---

## Spring Boot - Fundamentos y Avanzado
### ¿Qué es Spring Boot?
Framework basado en Spring que permite crear aplicaciones autónomas listas para producción con mínima configuración. Ideal para RESTful APIs y microservicios.

### Creación de un proyecto con Spring Boot
Puedes usar el [Spring Initializr](https://start.spring.io/) para generar la estructura básica. Luego, ejecuta la app con `SpringApplication.run()`.

### Inyección de dependencias con Spring Boot
Spring Boot aprovecha el sistema de inyección de dependencias de Spring. Se utilizan anotaciones como `@Autowired`, `@Component`, `@Service` y `@Repository`.

### Configuración avanzada
Mediante `application.properties` o `application.yml`. También soporta perfiles con `@Profile` para distintos entornos (dev, prod, etc.).

### Manejo de base de datos con Spring Data JPA
Spring Data JPA permite definir repositorios (`JpaRepository`) para CRUD sin necesidad de SQL explícito.

### Creación de microservicios
Spring Boot junto a **Spring Cloud** permite construir microservicios que se comunican vía REST. Incluye herramientas como **Spring Cloud Config** y **Eureka**.

### Pruebas unitarias e integración
Spring Boot soporta pruebas con `@SpringBootTest`, `@DataJpaTest` y `@WebMvcTest`, tanto unitarias como integradas.

---

## Endpoints del Microservicio Cursor

### Health Check
- **URL**: `GET /cursor/healtcheck`
- **Descripción**: Endpoint personalizado para verificar el estado del microservicio
- **Respuesta**: JSON con información del servicio, estado del sistema y características avanzadas

### Health Check Actuator (Spring Boot)
- **URL**: `GET /actuator/health`
- **Descripción**: Health check estándar de Spring Boot Actuator
- **Respuesta**: JSON con estado de salud del servicio

### Hello World
- **URL**: `GET /hello`
- **Descripción**: Endpoint de saludo básico
- **Respuesta**: "Hola desde Cursor con Spring Boot!"

### Advanced Java Features
- **URL**: `GET /cursor/advanced/streams`
- **Descripción**: Demuestra uso eficiente de Streams de Java 17
- **Respuesta**: JSON con ejemplos de filtrado, mapeo y reducción

- **URL**: `GET /cursor/advanced/pattern-matching?type=email`
- **Descripción**: Demuestra Pattern Matching de Java 17
- **Respuesta**: JSON con resultado del pattern matching

- **URL**: `POST /cursor/advanced/notify`
- **Descripción**: Demuestra patrón Factory para notificaciones
- **Parámetros**: type, message, recipient
- **Respuesta**: JSON con estado del envío

- **URL**: `GET /cursor/advanced/singleton`
- **Descripción**: Demuestra patrón Singleton thread-safe
- **Respuesta**: JSON con información de la instancia

- **URL**: `GET /cursor/advanced/concurrent`
- **Descripción**: Demuestra programación concurrente con CompletableFuture
- **Respuesta**: JSON con resultados de tareas asíncronas

- **URL**: `GET /cursor/advanced/memory`
- **Descripción**: Demuestra optimización de memoria y GC
- **Respuesta**: JSON con métricas de memoria

---

## Configuración

- **Puerto**: 8080
- **Actuator**: Habilitado con endpoints de health e info
- **Java Version**: 17
- **Spring Boot Version**: 3.3.4
