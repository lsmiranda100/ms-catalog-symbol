-- Verificar y corregir la secuencia para sigapiobjecttype

-- 1. Ver la estructura actual de la tabla
\d sigapiobjecttype

-- 2. Verificar si existe una secuencia
SELECT sequence_name FROM information_schema.sequences 
WHERE sequence_schema = 'public' 
AND sequence_name LIKE '%objecttype%';

-- 3. Crear la secuencia si no existe
CREATE SEQUENCE IF NOT EXISTS sigapiobjecttype_idobject_seq;

-- 4. Obtener el valor máximo actual de idobject
SELECT COALESCE(MAX(idobject), 0) + 1 as next_val FROM sigapiobjecttype;

-- 5. Ajustar la secuencia al valor correcto (ejecutar después de ver el resultado del paso 4)
-- Reemplaza 1 con el valor que obtuviste en el paso 4
SELECT setval('sigapiobjecttype_idobject_seq', (SELECT COALESCE(MAX(idobject), 0) FROM sigapiobjecttype), true);

-- 6. Asociar la secuencia con la columna
ALTER TABLE sigapiobjecttype 
ALTER COLUMN idobject SET DEFAULT nextval('sigapiobjecttype_idobject_seq');

-- 7. Establecer la secuencia como propiedad de la columna
ALTER SEQUENCE sigapiobjecttype_idobject_seq OWNED BY sigapiobjecttype.idobject;

-- 8. Verificar que funcionó
\d sigapiobjecttype
