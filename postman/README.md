# 📮 Colecciones de Postman - MS Catalog Symbol

Este directorio contiene las colecciones de Postman para probar todos los endpoints del microservicio.

## 📁 Colecciones Disponibles

### 1. SymbolType_Collection.postman_collection.json
**Descripción**: Colección completa para el controlador de tipos de símbolos  
**Endpoints**: 10 requests  
**Incluye**:
- ✅ Crear tipo de símbolo
- ✅ Crear símbolo con imagen
- ✅ Obtener todos los símbolos
- ✅ Obtener por ID
- ✅ Obtener por apiName
- ✅ Buscar por nombre
- ✅ Filtrar por tipo
- ✅ Obtener por defecto o asignados
- ✅ Actualizar símbolo
- ✅ Eliminar símbolo

### 2. Health_Collection.postman_collection.json
**Descripción**: Colección para health checks y monitoreo  
**Endpoints**: 7 requests  
**Incluye**:
- ✅ Custom Health Check
- ✅ Hello World
- ✅ Actuator Health
- ✅ Actuator Info
- ✅ Actuator Metrics
- ✅ Swagger UI
- ✅ OpenAPI Docs

### 3. Complete_Collection.postman_collection.json
**Descripción**: Colección completa con todos los endpoints organizados  
**Endpoints**: 17 requests  
**Carpetas**:
- Symbol Types (10 requests)
- Health & Monitoring (7 requests)

## 🚀 Cómo Importar en Postman

### Opción 1: Importar desde archivo

1. Abre Postman
2. Click en **"Import"** (esquina superior izquierda)
3. Selecciona **"File"**
4. Navega a la carpeta `postman/`
5. Selecciona uno o todos los archivos `.json`
6. Click en **"Import"**

### Opción 2: Drag & Drop

1. Abre Postman
2. Arrastra los archivos `.json` directamente a la ventana de Postman

## ⚙️ Configuración de Variables

Las colecciones incluyen variables predefinidas que puedes modificar:

### Variables de Colección

| Variable | Valor por Defecto | Descripción |
|----------|-------------------|-------------|
| `baseUrl` | `http://localhost:8080` | URL base del microservicio |
| `symbolId` | `1` | ID del símbolo para operaciones |

### Modificar Variables

**En Postman**:
1. Click derecho en la colección
2. Selecciona **"Edit"**
3. Ve a la pestaña **"Variables"**
4. Modifica los valores según tu entorno

## 🔄 Crear un Entorno (Environment)

Para trabajar con múltiples ambientes (dev, prod, etc.):

1. Click en **"Environments"** (menú izquierdo)
2. Click en **"+"** para crear nuevo entorno
3. Agrega las variables:

```
Variable        | Initial Value                | Current Value
----------------|------------------------------|------------------
baseUrl         | http://localhost:8080        | http://localhost:8080
symbolId        | 1                            | 1
```

4. Guarda el entorno
5. Selecciónalo en el dropdown superior derecho

### Ejemplo de Entornos

**Desarrollo (DEV)**:
```
baseUrl = http://localhost:8080
```

**Producción (PROD)**:
```
baseUrl = https://api.sigapi.com
```

## 🧪 Tests Automatizados

Las colecciones incluyen tests automáticos que validan las respuestas:

### SymbolType Collection Tests

```javascript
// Test 1: Validar código de estado
pm.test("Status code is 201", function () {
    pm.response.to.have.status(201);
});

// Test 2: Validar respuesta exitosa
pm.test("Response has success true", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.success).to.eql(true);
});

// Test 3: Guardar ID para siguientes requests
pm.test("Response has idSymbol", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.data.idSymbol).to.exist;
    pm.environment.set("symbolId", jsonData.data.idSymbol);
});
```

### Ver Resultados de Tests

Después de ejecutar un request:
1. Ve a la pestaña **"Test Results"**
2. Verás los tests que pasaron (✓) o fallaron (✗)

## 🏃 Ejecutar la Colección Completa

### Opción 1: Collection Runner

1. Click derecho en la colección
2. Selecciona **"Run collection"**
3. Selecciona los requests a ejecutar
4. Click en **"Run [Nombre Colección]"**
5. Observa los resultados

### Opción 2: Newman (CLI)

Instala Newman:
```bash
npm install -g newman
```

Ejecuta la colección:
```bash
# Ejecutar colección completa
newman run Complete_Collection.postman_collection.json

# Con entorno
newman run Complete_Collection.postman_collection.json \
  --environment dev-environment.json

# Generar reporte HTML
newman run Complete_Collection.postman_collection.json \
  --reporters html \
  --reporter-html-export report.html
```

## 📊 Orden Recomendado de Ejecución

### Para Pruebas Iniciales:

1. **Health Check** → Verificar que el servicio esté activo
2. **Get All Symbol Types** → Ver símbolos existentes
3. **Create Symbol Type** → Crear un nuevo símbolo
4. **Get by ID** → Verificar el símbolo creado
5. **Update Symbol Type** → Actualizar el símbolo
6. **Delete Symbol Type** → Eliminar el símbolo

### Para Búsquedas:

1. **Create Symbol Type** → Crear datos de prueba
2. **Search by Name** → Buscar por nombre
3. **Get by Type** → Filtrar por tipo
4. **Get by API Name** → Buscar por apiName
5. **Get Default or Assigned** → Filtrar por estado

## 🎯 Ejemplos de Datos de Prueba

### Símbolos de Función

```json
{
  "apiName": "SYM_FUNC_DEFAULT",
  "symbolName": "Función (rectángulo)",
  "symbolType": "FUNCTION",
  "symbolOrigName": "Function (rectangle)",
  "isDefault": 1,
  "isAssigned": 1
}
```

### Símbolos de Evento

```json
{
  "apiName": "SYM_EVENT_START",
  "symbolName": "Evento inicio",
  "symbolType": "EVENT",
  "symbolOrigName": "Start event",
  "isDefault": 1,
  "isAssigned": 1
}
```

### Símbolos de Decisión

```json
{
  "apiName": "SYM_DECISION",
  "symbolName": "Decisión (rombo)",
  "symbolType": "DECISION",
  "symbolOrigName": "Decision (diamond)",
  "isDefault": 1,
  "isAssigned": 0
}
```

### Símbolo con Imagen

```json
{
  "apiName": "SYM_CUSTOM",
  "symbolName": "Símbolo personalizado",
  "symbolType": "FUNCTION",
  "isDefault": 0,
  "isAssigned": 0,
  "symbolImageBase64": "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="
}
```

## 🔍 Solución de Problemas

### Error: Could not get response

**Causa**: El microservicio no está ejecutándose  
**Solución**:
```bash
cd /path/to/ms-catalog-symbol
mvn spring-boot:run
```

### Error: 404 Not Found

**Causa**: URL incorrecta o endpoint no existe  
**Solución**: Verifica que la variable `baseUrl` sea correcta

### Error: 401/403 Unauthorized

**Causa**: Falta autenticación (si está habilitada)  
**Solución**: Agrega headers de autenticación si es necesario

### Error: Connection refused

**Causa**: Puerto incorrecto o servicio no accesible  
**Solución**: Verifica que el servicio esté en el puerto 8080

## 📖 Recursos Adicionales

- [Documentación Oficial de Postman](https://learning.postman.com/)
- [Newman CLI Documentation](https://learning.postman.com/docs/running-collections/using-newman-cli/)
- [Swagger UI del Microservicio](http://localhost:8080/swagger-ui.html)

## 🤝 Contribuir

Para agregar nuevos requests a las colecciones:

1. Crea el request en Postman
2. Agrega tests apropiados
3. Exporta la colección actualizada
4. Reemplaza el archivo `.json` en esta carpeta

---

**Última actualización**: Octubre 2025  
**Versión**: 1.0.0


