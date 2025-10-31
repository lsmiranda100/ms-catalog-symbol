-- Verificar y corregir la secuencia para sigapisymboltype

-- 1. Crear la secuencia si no existe
CREATE SEQUENCE IF NOT EXISTS sigapisymboltype_idsymbol_seq;

-- 2. Inicializar la secuencia (si la tabla está vacía, empieza en 1)
SELECT setval('sigapisymboltype_idsymbol_seq', 
              GREATEST((SELECT COALESCE(MAX(idsymbol), 0) FROM sigapisymboltype), 1), 
              true);

-- 3. Asociar la secuencia con la columna idsymbol
ALTER TABLE sigapisymboltype 
ALTER COLUMN idsymbol SET DEFAULT nextval('sigapisymboltype_idsymbol_seq');

-- 4. Establecer la secuencia como propiedad de la columna
ALTER SEQUENCE sigapisymboltype_idsymbol_seq 
OWNED BY sigapisymboltype.idsymbol;

-- 5. Verificar que funcionó
SELECT 
    c.column_name,
    c.column_default,
    s.last_value as sequence_current_value
FROM information_schema.columns c
LEFT JOIN pg_sequences s ON s.sequencename = 'sigapisymboltype_idsymbol_seq'
WHERE c.table_name = 'sigapisymboltype' 
  AND c.column_name = 'idsymbol';
