-- Script para configurar la secuencia de sigapisymboltype

-- 1. Crear la secuencia
CREATE SEQUENCE IF NOT EXISTS sigapisymboltype_idsymbol_seq;

-- 2. Ajustar la secuencia al siguiente valor disponible
SELECT setval('sigapisymboltype_idsymbol_seq', 
    CASE 
        WHEN (SELECT MAX(idsymbol) FROM sigapisymboltype) IS NULL THEN 1
        ELSE (SELECT MAX(idsymbol) FROM sigapisymboltype)
    END, 
    (SELECT MAX(idsymbol) FROM sigapisymboltype) IS NOT NULL);

-- 3. Asociar la secuencia con la columna
ALTER TABLE sigapisymboltype 
ALTER COLUMN idsymbol SET DEFAULT nextval('sigapisymboltype_idsymbol_seq');

-- 4. Establecer la secuencia como propiedad de la columna
ALTER SEQUENCE sigapisymboltype_idsymbol_seq 
OWNED BY sigapisymboltype.idsymbol;
