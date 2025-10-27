# MS Catalog Symbol - Microservicio CRUD

Microservicio desarrollado con **Spring Boot 3.3.4** y **Java 17** para gestionar el catálogo de **tipos de símbolos** (`sigapisymboltype`) de la base de datos ARIS (**aris71adm**).

## 📋 Características

- ✅ **CRUD completo** para tipos de símbolos
- ✅ **Base de datos real** ARIS (aris71adm)
- ✅ **Gestión de imágenes** (BYTEA ↔ Base64)
- ✅ **Validación de datos** con Jakarta Validation
- ✅ **Manejo global de excepciones**
- ✅ **Documentación automática** con OpenAPI/Swagger
- ✅ **Consultas personalizadas** con JPA
- ✅ **Health checks** personalizados
- ✅ **Principios SOLID** y buenas prácticas Java 17

## 🚀 Tecnologías

- **Java**: 17 (LTS)
- **Spring Boot**: 3.3.4
- **Spring Data JPA**: Persistencia de datos
- **PostgreSQL**: Base de datos (aris71adm)
- **Lombok**: Reducción de código boilerplate
- **SpringDoc OpenAPI**: Documentación API (Swagger)
- **Maven**: Gestión de dependencias

## 🗄️ Tabla: sigapisymboltype

| Campo | Tipo | Descripción |
|-------|------|-------------|
| idsymbol | INTEGER | ID único (PK, auto-incremental) |
| apiname | VARCHAR(50) | Nombre de API (único) |
| symbolname | VARCHAR(60) | Nombre del símbolo |
| symboltype | VARCHAR(40) | Tipo de símbolo (FUNCTION, EVENT, etc.) |
| symbolorigname | VARCHAR(60) | Nombre original (inglés) |
| createdate | DATE | Fecha de creación |
| isdefault | INTEGER | ¿Es por defecto? (0/1) |
| isassigned | INTEGER | ¿Está asignado? (0/1) |
| lastupdate | DATE | Última actualización |
| symbolimage | BYTEA | Imagen del símbolo (binario) |

### Tipos de Símbolos Comunes
- `FUNCTION` - Símbolos de función
- `EVENT` - Símbolos de evento
- `DECISION` - Símbolos de decisión
- `ORGANIZATIONAL` - Símbolos organizacionales
- `DATA` - Símbolos de datos
- `PROCESS` - Símbolos de proceso

## ⚙️ Configuración

### Base de Datos PostgreSQL (aris71adm)

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/aris71adm
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

**Importante**: El microservicio se conecta a la base de datos **aris71adm** existente. No crea la tabla automáticamente (ddl-auto=none).

## 🔧 Instalación y Ejecución

### Paso 1: Compilar el proyecto

```bash
mvn clean install
```

### Paso 2: Ejecutar aplicación

```bash
# Conectar a aris71adm
mvn spring-boot:run

# O usar el script
./run.sh prod
```

La aplicación se iniciará en: **http://localhost:8080**

## 📚 Documentación de la API

### Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON
```
http://localhost:8080/api-docs
```

## 🔌 Endpoints

**Base URL**: `/api/v1/symbol-types`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/` | Crear tipo de símbolo |
| GET | `/{id}` | Obtener por ID |
| GET | `/` | Obtener todos |
| GET | `/api-name/{apiName}` | Obtener por apiName |
| GET | `/search?name={name}` | Buscar por nombre |
| GET | `/by-type/{symbolType}` | Filtrar por tipo |
| GET | `/default-or-assigned` | Obtener por defecto o asignados |
| PUT | `/{id}` | Actualizar |
| DELETE | `/{id}` | Eliminar |

## 📝 Ejemplos de Uso

### 1. Crear un tipo de símbolo

```bash
curl -X POST http://localhost:8080/api/v1/symbol-types \
  -H "Content-Type: application/json" \
  -d '{
    "apiName": "SYM_FUNC_DEFAULT",
    "symbolName": "Función (rectángulo)",
    "symbolType": "FUNCTION",
    "symbolOrigName": "Function (rectangle)",
    "isDefault": 1,
    "isAssigned": 1
  }'
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Tipo de símbolo creado exitosamente",
  "data": {
    "idSymbol": 1,
    "apiName": "SYM_FUNC_DEFAULT",
    "symbolName": "Función (rectángulo)",
    "symbolType": "FUNCTION",
    "symbolOrigName": "Function (rectangle)",
    "createDate": "2025-10-27",
    "isDefault": 1,
    "isAssigned": 1,
    "lastUpdate": null,
    "symbolImageBase64": null
  },
  "timestamp": "2025-10-27T14:30:00"
}
```

### 2. Crear símbolo con imagen

```bash
curl -X POST http://localhost:8080/api/v1/symbol-types \
  -H "Content-Type: application/json" \
  -d '{
    "apiName": "SYM_CUSTOM",
    "symbolName": "Símbolo personalizado",
    "symbolType": "FUNCTION",
    "isDefault": 0,
    "isAssigned": 0,
    "symbolImageBase64": "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="
  }'
```

### 3. Obtener todos los símbolos

```bash
curl http://localhost:8080/api/v1/symbol-types
```

### 4. Buscar símbolos por tipo

```bash
curl http://localhost:8080/api/v1/symbol-types/by-type/FUNCTION
```

### 5. Buscar por nombre

```bash
curl "http://localhost:8080/api/v1/symbol-types/search?name=función"
```

### 6. Obtener símbolo por apiName

```bash
curl http://localhost:8080/api/v1/symbol-types/api-name/SYM_FUNC_DEFAULT
```

### 7. Obtener símbolos por defecto o asignados

```bash
curl http://localhost:8080/api/v1/symbol-types/default-or-assigned
```

### 8. Actualizar un símbolo

```bash
curl -X PUT http://localhost:8080/api/v1/symbol-types/1 \
  -H "Content-Type: application/json" \
  -d '{
    "apiName": "SYM_FUNC_DEFAULT",
    "symbolName": "Función actualizada",
    "symbolType": "FUNCTION",
    "isDefault": 1,
    "isAssigned": 1
  }'
```

### 9. Eliminar un símbolo

```bash
curl -X DELETE http://localhost:8080/api/v1/symbol-types/1
```

## 🖼️ Gestión de Imágenes

Las imágenes de símbolos se almacenan en formato **BYTEA** (binario) en PostgreSQL y se exponen como **Base64** en la API REST.

### Subir imagen

```bash
# Convertir imagen a Base64
base64 -i simbolo.png | tr -d '\n' > imagen_base64.txt

# Crear símbolo con imagen
curl -X POST http://localhost:8080/api/v1/symbol-types \
  -H "Content-Type: application/json" \
  -d "{
    \"apiName\": \"SYM_WITH_IMAGE\",
    \"symbolName\": \"Símbolo con imagen\",
    \"symbolType\": \"FUNCTION\",
    \"isDefault\": 0,
    \"isAssigned\": 0,
    \"symbolImageBase64\": \"$(cat imagen_base64.txt)\"
  }"
```

### Descargar imagen

```bash
# Obtener símbolo con imagen
curl http://localhost:8080/api/v1/symbol-types/api-name/SYM_WITH_IMAGE | jq -r '.data.symbolImageBase64' > imagen_descargada.txt

# Decodificar Base64 a imagen
base64 -d imagen_descargada.txt > simbolo_descargado.png
```

## 🐳 Docker

### Ejecutar con Docker Compose

```bash
docker-compose up -d
```

Esto levanta:
- PostgreSQL en puerto 5432 (base de datos aris71adm)
- Microservicio en puerto 8080

### Detener servicios

```bash
docker-compose down
```

## 🧪 Pruebas

### Ejecutar pruebas unitarias

```bash
mvn test
```

## 📊 Health Checks

### Health Check Personalizado

```bash
curl http://localhost:8080/health
```

**Respuesta:**
```json
{
  "service": "ms-catalog-symbol",
  "status": "UP",
  "timestamp": "2025-10-27T14:30:00",
  "version": "1.0.0",
  "java_version": "17.0.2",
  "features": {
    "crud_operations": true,
    "image_support": true,
    "validation": true,
    "swagger_ui": true
  }
}
```

### Actuator Health Check

```bash
curl http://localhost:8080/actuator/health
```

## 🔍 Consultas Avanzadas

### Listar todos los tipos de símbolo

```sql
SELECT * FROM aris71adm.sigapisymboltype ORDER BY symbolname;
```

### Símbolos por tipo

```sql
SELECT symboltype, COUNT(*) as total 
FROM aris71adm.sigapisymboltype 
GROUP BY symboltype 
ORDER BY total DESC;
```

### Símbolos con imagen

```sql
SELECT apiname, symbolname 
FROM aris71adm.sigapisymboltype 
WHERE symbolimage IS NOT NULL;
```

### Símbolos por defecto

```sql
SELECT * FROM aris71adm.sigapisymboltype WHERE isdefault = 1;
```

## 🛠️ Solución de Problemas

### Error de conexión a aris71adm

Verifica que la base de datos aris71adm exista:

```sql
-- En PostgreSQL
\c aris71adm
\dt aris71adm.sigapi*
```

### La tabla no existe

Este microservicio NO crea la tabla automáticamente. La tabla debe existir en la base de datos aris71adm.

### Puerto 8080 en uso

Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

### Error al decodificar imagen Base64

Asegúrate de que la cadena Base64 sea válida:
```bash
echo "tu_cadena_base64" | base64 -d > test.png
```

## 📖 Documentación Adicional

- **[QUICKSTART.md](QUICKSTART.md)** - Guía de inicio rápido
- **[API_EXAMPLES.md](API_EXAMPLES.md)** - Ejemplos detallados de API
- **[JAVA17_FEATURES.md](JAVA17_FEATURES.md)** - Características de Java 17

## 👥 Equipo

**SIGAPI Team**
- Email: support@sigapi.com

## 📄 Licencia

Este proyecto es privado y propietario de SIGAPI.

---

**Versión**: 1.0.0  
**Tabla**: `sigapisymboltype` (aris71adm)  
**Fecha**: Octubre 2025  
**Desarrollado con** ❤️ **usando Spring Boot 3.3.4 y Java 17**
