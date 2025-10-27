-- Datos de ejemplo para la tabla sigapisymboltype
-- Estos datos se pueden cargar opcionalmente para pruebas

-- Insertar tipos de símbolos de ejemplo
INSERT INTO aris71adm.sigapisymboltype (apiname, symbolname, symboltype, symbolorigname, createdate, isdefault, isassigned)
VALUES 
    ('SYM_FUNC_DEFAULT', 'Función (rectángulo)', 'FUNCTION', 'Function (rectangle)', CURRENT_DATE, 1, 1),
    ('SYM_FUNC_ROUNDED', 'Función (redondeada)', 'FUNCTION', 'Function (rounded)', CURRENT_DATE, 1, 0),
    ('SYM_EVENT_START', 'Evento inicio', 'EVENT', 'Start event', CURRENT_DATE, 1, 1),
    ('SYM_EVENT_END', 'Evento fin', 'EVENT', 'End event', CURRENT_DATE, 1, 1),
    ('SYM_EVENT_INTERMEDIATE', 'Evento intermedio', 'EVENT', 'Intermediate event', CURRENT_DATE, 1, 0),
    ('SYM_DECISION', 'Decisión (rombo)', 'DECISION', 'Decision (diamond)', CURRENT_DATE, 1, 1),
    ('SYM_ORG_UNIT', 'Unidad organizativa', 'ORGANIZATIONAL', 'Organizational unit', CURRENT_DATE, 1, 0),
    ('SYM_DATA_OBJECT', 'Objeto de datos', 'DATA', 'Data object', CURRENT_DATE, 1, 0),
    ('SYM_DOCUMENT', 'Documento', 'DATA', 'Document', CURRENT_DATE, 1, 0),
    ('SYM_PROCESS', 'Proceso', 'PROCESS', 'Process', CURRENT_DATE, 1, 1)
ON CONFLICT (apiname) DO NOTHING;
