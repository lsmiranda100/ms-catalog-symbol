# 🔧 Instrucciones para Corregir la Base de Datos

## Problema Identificado

El microservicio tenía un problema de configuración donde:
1. ❌ La entidad apuntaba al schema `public` en lugar de `aris71adm`
2. ❌ La tabla no tenía la secuencia configurada correctamente para auto-generar IDs
3. ❌ El tipo BYTEA no estaba correctamente mapeado

## ✅ Soluciones Aplicadas

### 1. Corrección en el Código
- **Archivo:** `src/main/java/com/sigapi/catalog/entity/SymbolType.java`
- **Cambio:** Schema de `"public"` a `"aris71adm"`
- **Línea 13:** `@Table(name = "sigapisymboltype", schema = "aris71adm")`
- **Línea 53:** `@Column(name = "symbolimage", columnDefinition = "bytea")`

### 2. Script SQL de Corrección
Se creó el archivo `fix-database.sql` para:
- Eliminar tablas problemáticas existentes
- Crear la secuencia para auto-generación de IDs
- Crear la tabla con el esquema correcto en `aris71adm`
- Configurar el tipo BYTEA correctamente
- Insertar datos de ejemplo

## 📋 Pasos para Aplicar la Corrección

### Opción A: Ejecutar el Script SQL (RECOMENDADO)

```bash
# 1. Asegúrate de tener psql instalado
which psql

# 2. Ejecuta el script de corrección
psql -h localhost -U postgres -d aris71adm -f fix-database.sql

# 3. Verifica que la tabla fue creada correctamente
psql -h localhost -U postgres -d aris71adm -c "\d aris71adm.sigapisymboltype"
```

### Opción B: Dejar que Hibernate cree la tabla automáticamente

1. **Verifica que `application.properties` tenga:**
   ```properties
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.default_schema=aris71adm
   ```

2. **Reinicia el microservicio:**
   ```bash
   # Detén el servicio actual
   pkill -f ms-catalog-symbol
   
   # Recompila y ejecuta
   mvn clean package -DskipTests
   java -jar target/ms-catalog-symbol-1.0.0.jar
   ```

### Opción C: Manual con SQL

```sql
-- Conéctate a la base de datos
psql -h localhost -U postgres -d aris71adm

-- Ejecuta las siguientes instrucciones:

-- 1. Eliminar tabla problemática
DROP TABLE IF EXISTS public.sigapisymboltype CASCADE;
DROP TABLE IF EXISTS aris71adm.sigapisymboltype CASCADE;

-- 2. Crear secuencia
CREATE SEQUENCE aris71adm.sigapisymboltype_idsymbol_seq;

-- 3. Crear tabla
CREATE TABLE aris71adm.sigapisymboltype (
    idsymbol INTEGER NOT NULL DEFAULT nextval('aris71adm.sigapisymboltype_idsymbol_seq'::regclass),
    apiname VARCHAR(50) NOT NULL UNIQUE,
    symbolname VARCHAR(60) NOT NULL,
    symboltype VARCHAR(40) NOT NULL,
    symbolorigname VARCHAR(60),
    createdate DATE,
    isdefault INTEGER DEFAULT 0,
    isassigned INTEGER DEFAULT 0,
    lastupdate DATE,
    symbolimage BYTEA,
    CONSTRAINT pk_sigapisymboltype PRIMARY KEY (idsymbol)
);

-- 4. Asociar secuencia
ALTER SEQUENCE aris71adm.sigapisymboltype_idsymbol_seq 
OWNED BY aris71adm.sigapisymboltype.idsymbol;

-- 5. Insertar datos de prueba
INSERT INTO aris71adm.sigapisymboltype 
(apiname, symbolname, symboltype, symbolorigname, isdefault, isassigned, createdate)
VALUES 
('SYM_FUNC_DEFAULT', 'Función (rectángulo)', 'FUNCTION', 'Function (rectangle)', 1, 1, CURRENT_DATE);
```

## 🧪 Verificación Post-Corrección

### 1. Verificar la tabla en PostgreSQL

```bash
psql -h localhost -U postgres -d aris71adm -c "SELECT * FROM aris71adm.sigapisymboltype;"
```

### 2. Probar el endpoint POST

```bash
curl -X POST http://localhost:8080/api/v1/symbol-types \
  -H "Content-Type: application/json" \
  -d '{
    "apiName": "TEST_VERIFICATION",
    "symbolName": "Test Verificación",
    "symbolType": "TEST",
    "isDefault": 1,
    "isAssigned": 0
  }'
```

**Respuesta esperada:**
```json
{
  "success": true,
  "message": "SymbolType creado exitosamente",
  "data": {
    "idSymbol": 1,
    "apiName": "TEST_VERIFICATION",
    "symbolName": "Test Verificación",
    ...
  },
  "timestamp": "2025-10-27T..."
}
```

### 3. Probar el endpoint GET

```bash
curl http://localhost:8080/api/v1/symbol-types
```

### 4. Usar las Colecciones de Postman

Las colecciones en `postman/` están actualizadas y listas para usar:
- `SymbolType_Collection.postman_collection.json` - Endpoints CRUD completos
- `Health_Collection.postman_collection.json` - Health checks
- `Complete_Collection.postman_collection.json` - Colección completa

## ⚠️ Notas Importantes

1. **Schema correcto:** Siempre usar `aris71adm`, no `public`
2. **Auto-incremento:** La secuencia maneja automáticamente el ID
3. **BYTEA:** Las imágenes deben enviarse como Base64 en el JSON
4. **Lombok:** Asegúrate de que tu IDE tenga el plugin de Lombok instalado

## 🔍 Troubleshooting

### Error: "relation does not exist"
- Ejecuta el script `fix-database.sql`
- Verifica que `spring.jpa.properties.hibernate.default_schema=aris71adm`

### Error: "null value in column idsymbol"
- La secuencia no está configurada
- Ejecuta: `ALTER TABLE aris71adm.sigapisymboltype ALTER COLUMN idsymbol SET DEFAULT nextval('aris71adm.sigapisymboltype_idsymbol_seq'::regclass);`

### Error: "column symbolimage is of type bytea but expression is of type oid"
- ✅ Ya corregido en la última versión
- La entidad usa `columnDefinition = "bytea"`

## 📚 Referencias

- [PostgreSQL SERIAL Types](https://www.postgresql.org/docs/current/datatype-numeric.html#DATATYPE-SERIAL)
- [PostgreSQL BYTEA](https://www.postgresql.org/docs/current/datatype-binary.html)
- [Hibernate Schema Generation](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#schema-generation)

