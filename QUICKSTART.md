# 🚀 Guía de Inicio Rápido - MS Catalog Symbol

Esta guía te ayudará a tener el microservicio ejecutándose en **menos de 5 minutos**.

## 📋 Prerrequisitos

- ✅ Java 17 instalado
- ✅ Maven 3.6+ instalado
- ✅ (Opcional) Docker y Docker Compose

## ⚡ Opción 1: Inicio Rápido con H2 (Base de datos en memoria)

### Paso 1: Compilar el proyecto

```bash
mvn clean install
```

### Paso 2: Ejecutar con perfil de desarrollo

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Paso 3: Probar el servicio

Abre tu navegador en: **http://localhost:8080/swagger-ui.html**

¡Listo! El microservicio está ejecutándose con datos de ejemplo precargados.

---

## 🐳 Opción 2: Inicio con Docker Compose (PostgreSQL)

### Paso único: Levantar todos los servicios

```bash
docker-compose up -d
```

Esto levantará:
- ✅ PostgreSQL en el puerto 5432
- ✅ Microservicio en el puerto 8080

**Accede a Swagger UI**: http://localhost:8080/swagger-ui.html

Para detener los servicios:
```bash
docker-compose down
```

---

## 🔍 Endpoints Rápidos de Prueba

### 1. Health Check
```bash
curl http://localhost:8080/health
```

### 2. Ver todos los símbolos
```bash
curl http://localhost:8080/api/v1/object-assigned-symbols
```

### 3. Crear un nuevo símbolo
```bash
curl -X POST http://localhost:8080/api/v1/object-assigned-symbols \
  -H "Content-Type: application/json" \
  -d '{
    "objectId": "USER-100",
    "objectType": "USER",
    "symbolCode": "VIP",
    "symbolName": "Usuario VIP",
    "description": "Usuario VIP con acceso completo",
    "isActive": true,
    "priority": 20,
    "createdBy": "admin"
  }'
```

### 4. Buscar símbolos por objectId
```bash
curl http://localhost:8080/api/v1/object-assigned-symbols/by-object/USER-001
```

---

## 📚 Documentación Completa

Para más información, consulta el archivo **[README.md](README.md)** completo.

---

## 🛠️ Solución Rápida de Problemas

### Puerto 8080 ocupado
```bash
# Linux/Mac
lsof -ti:8080 | xargs kill -9

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Cambiar el puerto
Edita `application.properties`:
```properties
server.port=8081
```

### Ver logs
```bash
# Si usas Maven
mvn spring-boot:run

# Si usas Docker
docker-compose logs -f app
```

---

## 🎯 Próximos Pasos

1. ✅ Explora la **[documentación Swagger](http://localhost:8080/swagger-ui.html)**
2. ✅ Prueba todos los endpoints CRUD
3. ✅ Revisa los **datos de ejemplo** en `/src/main/resources/data.sql`
4. ✅ Lee la **documentación completa** en `README.md`

---

**¿Preguntas?** Consulta el README.md o contacta al equipo SIGAPI.


