# 🔧 Guía de Solución de Problemas

## Error: "relation aris71adm.sigapisymboltype does not exist"

### Causa
La tabla `sigapisymboltype` no existe en la base de datos.

### Solución 1: Dejar que Hibernate cree la tabla (Recomendado para desarrollo)

La configuración ya está ajustada para crear la tabla automáticamente:

```properties
spring.jpa.hibernate.ddl-auto=update
```

**Pasos:**
1. Detén el microservicio si está corriendo
2. Reinicia el microservicio:
   ```bash
   mvn spring-boot:run
   ```
3. Hibernate creará la tabla automáticamente al iniciar

### Solución 2: Crear la tabla manualmente

Si prefieres crear la tabla manualmente en PostgreSQL:

```sql
-- Conectar a la base de datos
psql -U postgres -d aris71adm

-- Crear la tabla
CREATE TABLE aris71adm.sigapisymboltype (
    idsymbol SERIAL PRIMARY KEY,
    apiname VARCHAR(50) NOT NULL UNIQUE,
    symbolname VARCHAR(60) NOT NULL,
    symboltype VARCHAR(40) NOT NULL,
    symbolorigname VARCHAR(60),
    createdate DATE,
    isdefault INTEGER DEFAULT 0,
    isassigned INTEGER DEFAULT 0,
    lastupdate DATE,
    symbolimage BYTEA
);

-- Crear índices
CREATE INDEX idx_symboltype_apiname ON aris71adm.sigapisymboltype(apiname);
CREATE INDEX idx_symboltype_symboltype ON aris71adm.sigapisymboltype(symboltype);
```

Luego cambia la configuración a:
```properties
spring.jpa.hibernate.ddl-auto=none
```

### Solución 3: Ejecutar el script schema.sql

```bash
# Desde el directorio del proyecto
psql -U postgres -d aris71adm -f src/main/resources/schema.sql
```

---

## Error: Puerto 8080 ocupado

### Causa
Otro proceso está usando el puerto 8080.

### Solución

**Opción 1: Detener el proceso**
```bash
# Encontrar el proceso
lsof -i :8080

# Detener el proceso (reemplaza PID con el número del proceso)
kill PID
```

**Opción 2: Cambiar el puerto**
```bash
# Ejecutar en otro puerto
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

O edita `application.properties`:
```properties
server.port=8081
```

---

## Error: Conexión rechazada a PostgreSQL

### Causa
PostgreSQL no está corriendo o las credenciales son incorrectas.

### Solución

**Verificar que PostgreSQL esté corriendo:**
```bash
# macOS
brew services list | grep postgresql

# Linux
sudo systemctl status postgresql
```

**Iniciar PostgreSQL:**
```bash
# macOS
brew services start postgresql

# Linux
sudo systemctl start postgresql
```

**Verificar credenciales en `application.properties`:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/aris71adm
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**Crear la base de datos si no existe:**
```sql
CREATE DATABASE aris71adm;
```

---

## Error: Authentication failed for user "postgres"

### Causa
La contraseña de PostgreSQL es incorrecta.

### Solución

**Opción 1: Cambiar la contraseña en application.properties**
```properties
spring.datasource.password=tu_password_correcta
```

**Opción 2: Resetear la contraseña de PostgreSQL**
```bash
# Conectar como superusuario
sudo -u postgres psql

# Cambiar la contraseña
ALTER USER postgres PASSWORD 'postgres';
```

---

## Error: Esquema "aris71adm" no existe

### Causa
El esquema no ha sido creado en la base de datos.

### Solución

```sql
-- Conectar a PostgreSQL
psql -U postgres -d aris71adm

-- Crear el esquema
CREATE SCHEMA IF NOT EXISTS aris71adm;

-- Dar permisos
GRANT ALL ON SCHEMA aris71adm TO postgres;
```

O puedes eliminar el esquema de la entidad:

```java
@Table(name = "sigapisymboltype")  // Sin schema
```

Y remover de application.properties:
```properties
# Comentar o eliminar esta línea
# spring.jpa.properties.hibernate.default_schema=aris71adm
```

---

## Error: Maven no encontrado

### Causa
Maven no está instalado o no está en el PATH.

### Solución

**macOS:**
```bash
brew install maven
```

**Linux:**
```bash
sudo apt install maven  # Ubuntu/Debian
sudo yum install maven  # CentOS/RHEL
```

**Verificar instalación:**
```bash
mvn --version
```

---

## Error: Java 17 no encontrado

### Causa
Java 17 no está instalado.

### Solución

**macOS:**
```bash
brew install openjdk@17

# Configurar JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

**Linux:**
```bash
sudo apt install openjdk-17-jdk  # Ubuntu/Debian
```

**Verificar:**
```bash
java -version
```

---

## Error 404 en los endpoints

### Causa
El microservicio no está corriendo o la URL es incorrecta.

### Verificación

```bash
# Verificar que el servicio esté corriendo
lsof -i :8080

# Probar el health check
curl http://localhost:8080/actuator/health

# Probar endpoint principal
curl http://localhost:8080/api/v1/symbol-types
```

### Solución

1. Asegúrate de que el microservicio esté corriendo:
   ```bash
   mvn spring-boot:run
   ```

2. Verifica los logs en la consola para errores de inicio

3. Verifica la URL correcta:
   - Base: `http://localhost:8080`
   - API: `/api/v1/symbol-types`

---

## Ver logs detallados

Para ver más información sobre los errores:

```properties
# application.properties
logging.level.com.sigapi=DEBUG
logging.level.org.springframework=DEBUG
logging.level.org.hibernate=DEBUG
```

---

## Limpiar y recompilar

Si hay problemas de compilación:

```bash
# Limpiar
mvn clean

# Compilar
mvn compile

# Empaquetar
mvn package

# Ejecutar
mvn spring-boot:run
```

---

## Docker Issues

### Error: Cannot connect to the Docker daemon

```bash
# Iniciar Docker
# macOS
open -a Docker

# Linux
sudo systemctl start docker
```

### Error: Port already allocated

```bash
# Detener contenedores
docker-compose down

# Ver contenedores corriendo
docker ps

# Detener contenedor específico
docker stop CONTAINER_ID
```

---

## Postman Issues

### Variables no funcionan

1. Verifica que las variables estén definidas en:
   - Variables de colección
   - Variables de entorno

2. Usa `{{baseUrl}}` en lugar de `http://localhost:8080`

### Tests fallan

1. Verifica que el microservicio esté corriendo
2. Revisa la respuesta en la pestaña "Body"
3. Verifica los tests en la pestaña "Tests"

---

## Contacto

Si el problema persiste:
1. Revisa los logs completos del microservicio
2. Verifica la conexión a PostgreSQL
3. Consulta la documentación en README.md

**Email de soporte**: support@sigapi.com


