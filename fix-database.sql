-- Script para corregir la tabla sigapisymboltype en PostgreSQL
-- Ejecutar con: psql -h localhost -U postgres -d aris71adm -f fix-database.sql

-- Eliminar tabla existente si hay problemas
DROP TABLE IF EXISTS public.sigapisymboltype CASCADE;
DROP TABLE IF EXISTS aris71adm.sigapisymboltype CASCADE;

-- Crear secuencia para el ID
CREATE SEQUENCE IF NOT EXISTS aris71adm.sigapisymboltype_idsymbol_seq;

-- Crear tabla en el esquema correcto
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

-- Asociar la secuencia con la columna
ALTER SEQUENCE aris71adm.sigapisymboltype_idsymbol_seq OWNED BY aris71adm.sigapisymboltype.idsymbol;

-- Comentarios en la tabla
COMMENT ON TABLE aris71adm.sigapisymboltype IS 'Catálogo de tipos de símbolos de ARIS';
COMMENT ON COLUMN aris71adm.sigapisymboltype.idsymbol IS 'ID único del símbolo';
COMMENT ON COLUMN aris71adm.sigapisymboltype.apiname IS 'Nombre único del símbolo en la API';
COMMENT ON COLUMN aris71adm.sigapisymboltype.symbolname IS 'Nombre del símbolo';
COMMENT ON COLUMN aris71adm.sigapisymboltype.symboltype IS 'Tipo de símbolo';
COMMENT ON COLUMN aris71adm.sigapisymboltype.symbolimage IS 'Imagen del símbolo en formato BYTEA';

-- Insertar datos de ejemplo
INSERT INTO aris71adm.sigapisymboltype (apiname, symbolname, symboltype, symbolorigname, isdefault, isassigned, createdate)
VALUES 
    ('SYM_FUNC_DEFAULT', 'Función (rectángulo)', 'FUNCTION', 'Function (rectangle)', 1, 1, CURRENT_DATE),
    ('SYM_EVENT_START', 'Evento inicio', 'EVENT', 'Start event', 1, 1, CURRENT_DATE),
    ('SYM_DECISION', 'Decisión (rombo)', 'DECISION', 'Decision (diamond)', 1, 0, CURRENT_DATE);

-- Verificar
SELECT COUNT(*) as total_records FROM aris71adm.sigapisymboltype;

SELECT 'Tabla creada exitosamente en el esquema aris71adm' AS status;

