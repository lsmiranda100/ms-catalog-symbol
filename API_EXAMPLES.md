# 📚 Ejemplos de Uso de la API - MS Catalog Symbol

Este documento contiene ejemplos completos de cómo usar todos los endpoints del microservicio.

## 📝 Tabla de Contenidos

1. [Operaciones CRUD Básicas](#operaciones-crud-básicas)
2. [Consultas Especializadas](#consultas-especializadas)
3. [Operaciones Asíncronas](#operaciones-asíncronas)
4. [Soft Delete](#soft-delete)
5. [Ejemplos con diferentes herramientas](#ejemplos-con-diferentes-herramientas)

---

## 🔧 Operaciones CRUD Básicas

### 1. Crear un Símbolo Asignado (CREATE)

**cURL:**
```bash
curl -X POST http://localhost:8080/api/v1/object-assigned-symbols \
  -H "Content-Type: application/json" \
  -d '{
    "objectId": "USER-001",
    "objectType": "USER",
    "symbolCode": "PREMIUM",
    "symbolName": "Usuario Premium",
    "description": "Usuario con acceso premium",
    "isActive": true,
    "priority": 10,
    "metadata": "{\"level\": \"gold\", \"expiry\": \"2025-12-31\"}",
    "createdBy": "admin"
  }'
```

**JavaScript (fetch):**
```javascript
fetch('http://localhost:8080/api/v1/object-assigned-symbols', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    objectId: 'USER-001',
    objectType: 'USER',
    symbolCode: 'PREMIUM',
    symbolName: 'Usuario Premium',
    description: 'Usuario con acceso premium',
    isActive: true,
    priority: 10,
    metadata: JSON.stringify({ level: 'gold', expiry: '2025-12-31' }),
    createdBy: 'admin'
  })
})
.then(response => response.json())
.then(data => console.log(data));
```

**Python:**
```python
import requests

url = "http://localhost:8080/api/v1/object-assigned-symbols"
payload = {
    "objectId": "USER-001",
    "objectType": "USER",
    "symbolCode": "PREMIUM",
    "symbolName": "Usuario Premium",
    "description": "Usuario con acceso premium",
    "isActive": True,
    "priority": 10,
    "metadata": '{"level": "gold", "expiry": "2025-12-31"}',
    "createdBy": "admin"
}

response = requests.post(url, json=payload)
print(response.json())
```

**Respuesta Exitosa (201 Created):**
```json
{
  "success": true,
  "message": "Símbolo asignado creado exitosamente",
  "data": {
    "id": 1,
    "objectId": "USER-001",
    "objectType": "USER",
    "symbolCode": "PREMIUM",
    "symbolName": "Usuario Premium",
    "description": "Usuario con acceso premium",
    "isActive": true,
    "priority": 10,
    "metadata": "{\"level\": \"gold\", \"expiry\": \"2025-12-31\"}",
    "createdAt": "2025-10-27T10:30:00",
    "updatedAt": null,
    "createdBy": "admin",
    "updatedBy": null
  },
  "timestamp": "2025-10-27T10:30:00.123"
}
```

---

### 2. Obtener Todos los Símbolos (READ ALL)

**cURL:**
```bash
curl http://localhost:8080/api/v1/object-assigned-symbols
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Se encontraron 5 símbolos asignados",
  "data": [
    {
      "id": 1,
      "objectId": "USER-001",
      "objectType": "USER",
      "symbolCode": "PREMIUM",
      "symbolName": "Usuario Premium",
      "isActive": true,
      "priority": 10,
      ...
    },
    ...
  ],
  "timestamp": "2025-10-27T10:35:00"
}
```

---

### 3. Obtener un Símbolo por ID (READ ONE)

**cURL:**
```bash
curl http://localhost:8080/api/v1/object-assigned-symbols/1
```

**Respuesta Exitosa (200 OK):**
```json
{
  "success": true,
  "message": "Símbolo asignado encontrado",
  "data": {
    "id": 1,
    "objectId": "USER-001",
    ...
  },
  "timestamp": "2025-10-27T10:40:00"
}
```

**Respuesta Error (404 Not Found):**
```json
{
  "success": false,
  "message": "Símbolo asignado no encontrado con id: '99'",
  "data": null,
  "timestamp": "2025-10-27T10:40:00"
}
```

---

### 4. Actualizar un Símbolo (UPDATE)

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/v1/object-assigned-symbols/1 \
  -H "Content-Type: application/json" \
  -d '{
    "objectId": "USER-001",
    "objectType": "USER",
    "symbolCode": "PREMIUM",
    "symbolName": "Usuario Premium Plus",
    "description": "Usuario con acceso premium mejorado",
    "isActive": true,
    "priority": 15,
    "metadata": "{\"level\": \"platinum\", \"features\": [\"priority_support\"]}",
    "updatedBy": "admin"
  }'
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Símbolo asignado actualizado exitosamente",
  "data": {
    "id": 1,
    "symbolName": "Usuario Premium Plus",
    "priority": 15,
    "updatedAt": "2025-10-27T11:00:00",
    "updatedBy": "admin",
    ...
  },
  "timestamp": "2025-10-27T11:00:00"
}
```

---

### 5. Eliminar un Símbolo (DELETE)

**cURL:**
```bash
curl -X DELETE http://localhost:8080/api/v1/object-assigned-symbols/1
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Símbolo asignado eliminado exitosamente",
  "data": null,
  "timestamp": "2025-10-27T11:10:00"
}
```

---

## 🔍 Consultas Especializadas

### 6. Buscar Símbolos por ObjectId

**cURL:**
```bash
curl http://localhost:8080/api/v1/object-assigned-symbols/by-object/USER-001
```

**Ejemplo de uso:** Obtener todos los símbolos asignados a un usuario específico.

---

### 7. Buscar Símbolos por Tipo de Objeto

**cURL:**
```bash
curl http://localhost:8080/api/v1/object-assigned-symbols/by-type/USER
```

**Ejemplo de uso:** Obtener todos los símbolos asignados a cualquier usuario.

---

### 8. Buscar Símbolos Activos por ObjectId

**cURL:**
```bash
curl http://localhost:8080/api/v1/object-assigned-symbols/active/USER-001
```

**Ejemplo de uso:** Filtrar solo símbolos activos (isActive=true) de un objeto específico.

---

### 9. Buscar Símbolos Ordenados por Prioridad

**cURL:**
```bash
curl http://localhost:8080/api/v1/object-assigned-symbols/by-priority/USER-001
```

**Respuesta (ordenados de mayor a menor prioridad):**
```json
{
  "success": true,
  "message": "Símbolos obtenidos ordenados por prioridad",
  "data": [
    {
      "id": 2,
      "objectId": "USER-001",
      "symbolCode": "VERIFIED",
      "priority": 15,
      ...
    },
    {
      "id": 1,
      "objectId": "USER-001",
      "symbolCode": "PREMIUM",
      "priority": 10,
      ...
    }
  ],
  "timestamp": "2025-10-27T11:20:00"
}
```

---

## ⚡ Operaciones Asíncronas

### 10. Buscar Símbolos Activos de Forma Asíncrona (Java 17 CompletableFuture)

**cURL:**
```bash
curl http://localhost:8080/api/v1/object-assigned-symbols/active-async/USER
```

**Descripción:** 
Este endpoint demuestra el uso de programación concurrente con `CompletableFuture` de Java 17. 
La operación se ejecuta de forma asíncrona, permitiendo mejor rendimiento en operaciones costosas.

**JavaScript (con async/await):**
```javascript
async function getActiveSymbolsAsync() {
  try {
    const response = await fetch(
      'http://localhost:8080/api/v1/object-assigned-symbols/active-async/USER'
    );
    const data = await response.json();
    console.log('Símbolos activos:', data);
  } catch (error) {
    console.error('Error:', error);
  }
}

getActiveSymbolsAsync();
```

---

## 🗑️ Soft Delete

### 11. Desactivar un Símbolo (Soft Delete)

**cURL:**
```bash
curl -X PATCH http://localhost:8080/api/v1/object-assigned-symbols/1/deactivate
```

**Descripción:** 
En lugar de eliminar permanentemente el registro, este endpoint lo marca como inactivo (isActive=false).
Esto permite mantener el historial y posibilitar la reactivación futura.

**Respuesta:**
```json
{
  "success": true,
  "message": "Símbolo asignado desactivado exitosamente",
  "data": {
    "id": 1,
    "objectId": "USER-001",
    "symbolCode": "PREMIUM",
    "isActive": false,
    "updatedAt": "2025-10-27T11:30:00",
    ...
  },
  "timestamp": "2025-10-27T11:30:00"
}
```

---

## 🛠️ Ejemplos con Diferentes Herramientas

### HTTPie

```bash
# Crear
http POST localhost:8080/api/v1/object-assigned-symbols \
  objectId="USER-002" \
  objectType="USER" \
  symbolCode="VERIFIED" \
  symbolName="Usuario Verificado" \
  isActive:=true \
  priority:=5 \
  createdBy="system"

# Obtener todos
http GET localhost:8080/api/v1/object-assigned-symbols

# Actualizar
http PUT localhost:8080/api/v1/object-assigned-symbols/1 \
  objectId="USER-001" \
  objectType="USER" \
  symbolCode="PREMIUM" \
  symbolName="Usuario Premium VIP" \
  isActive:=true \
  priority:=20
```

---

### Postman Collection JSON

```json
{
  "info": {
    "name": "MS Catalog Symbol API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Create Symbol",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"objectId\": \"USER-001\",\n  \"objectType\": \"USER\",\n  \"symbolCode\": \"PREMIUM\",\n  \"symbolName\": \"Usuario Premium\",\n  \"isActive\": true,\n  \"priority\": 10\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/v1/object-assigned-symbols",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "v1", "object-assigned-symbols"]
        }
      }
    }
  ]
}
```

---

## 🎯 Casos de Uso Prácticos

### Caso 1: Gestionar Badges de Usuario

```bash
# Asignar badge "Early Adopter"
curl -X POST http://localhost:8080/api/v1/object-assigned-symbols \
  -H "Content-Type: application/json" \
  -d '{
    "objectId": "USER-123",
    "objectType": "USER",
    "symbolCode": "EARLY_ADOPTER",
    "symbolName": "Early Adopter",
    "description": "Usuario registrado en el primer mes",
    "isActive": true,
    "priority": 5,
    "metadata": "{\"registered_date\": \"2024-01-15\", \"badge_color\": \"gold\"}",
    "createdBy": "system"
  }'

# Obtener todos los badges del usuario
curl http://localhost:8080/api/v1/object-assigned-symbols/by-priority/USER-123
```

### Caso 2: Etiquetar Productos

```bash
# Marcar producto como "Destacado"
curl -X POST http://localhost:8080/api/v1/object-assigned-symbols \
  -H "Content-Type: application/json" \
  -d '{
    "objectId": "PROD-456",
    "objectType": "PRODUCT",
    "symbolCode": "FEATURED",
    "symbolName": "Producto Destacado",
    "description": "Producto destacado en página principal",
    "isActive": true,
    "priority": 20,
    "metadata": "{\"campaign\": \"black_friday_2024\", \"discount\": 25}",
    "createdBy": "marketing_team"
  }'

# Obtener todos los productos destacados
curl http://localhost:8080/api/v1/object-assigned-symbols/active-async/PRODUCT
```

### Caso 3: Gestionar Estados de Órdenes

```bash
# Marcar orden como urgente
curl -X POST http://localhost:8080/api/v1/object-assigned-symbols \
  -H "Content-Type: application/json" \
  -d '{
    "objectId": "ORDER-789",
    "objectType": "ORDER",
    "symbolCode": "URGENT",
    "symbolName": "Entrega Urgente",
    "description": "Orden con entrega urgente solicitada",
    "isActive": true,
    "priority": 30,
    "metadata": "{\"requested_by\": \"customer\", \"delivery_date\": \"2025-10-28\"}",
    "createdBy": "order_service"
  }'

# Desactivar cuando ya no sea urgente
curl -X PATCH http://localhost:8080/api/v1/object-assigned-symbols/5/deactivate
```

---

## 🔐 Manejo de Errores

### Error 400 - Validación

```bash
# Request con datos inválidos
curl -X POST http://localhost:8080/api/v1/object-assigned-symbols \
  -H "Content-Type: application/json" \
  -d '{
    "objectId": "",
    "objectType": "USER"
  }'
```

**Respuesta:**
```json
{
  "success": false,
  "message": "Error de validación",
  "data": {
    "objectId": "El objectId es obligatorio",
    "symbolCode": "El symbolCode es obligatorio",
    "symbolName": "El symbolName es obligatorio",
    "isActive": "El estado isActive es obligatorio"
  },
  "timestamp": "2025-10-27T12:00:00"
}
```

### Error 404 - No Encontrado

**Respuesta:**
```json
{
  "success": false,
  "message": "Símbolo asignado no encontrado con id: '999'",
  "data": null,
  "timestamp": "2025-10-27T12:00:00"
}
```

### Error 409 - Conflicto (Duplicado)

**Respuesta:**
```json
{
  "success": false,
  "message": "Símbolo asignado ya existe con objectId y symbolCode: 'USER-001 - PREMIUM'",
  "data": null,
  "timestamp": "2025-10-27T12:00:00"
}
```

---

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
  "timestamp": "2025-10-27T12:00:00",
  "version": "1.0.0",
  "java_version": "17.0.2",
  "features": {
    "crud_operations": true,
    "async_support": true,
    "validation": true,
    "swagger_ui": true
  }
}
```

### Actuator Health Check

```bash
curl http://localhost:8080/actuator/health
```

---

¿Tienes más preguntas? Consulta la [documentación completa en Swagger UI](http://localhost:8080/swagger-ui.html).


