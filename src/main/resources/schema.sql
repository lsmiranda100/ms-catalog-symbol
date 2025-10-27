-- Script SQL para la tabla sigapisymboltype en la base de datos aris71adm
-- La tabla ya existe en la base de datos, este script es solo de referencia

-- Tabla: sigapisymboltype
-- Catálogo de tipos de símbolos
CREATE TABLE IF NOT EXISTS aris71adm.sigapisymboltype (
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

COMMENT ON TABLE aris71adm.sigapisymboltype IS 'Catálogo de tipos de símbolos de ARIS';
COMMENT ON COLUMN aris71adm.sigapisymboltype.idsymbol IS 'ID único del tipo de símbolo';
COMMENT ON COLUMN aris71adm.sigapisymboltype.apiname IS 'Nombre técnico de API (único)';
COMMENT ON COLUMN aris71adm.sigapisymboltype.symbolname IS 'Nombre del símbolo';
COMMENT ON COLUMN aris71adm.sigapisymboltype.symboltype IS 'Clasificación del símbolo (FUNCTION, EVENT, etc.)';
COMMENT ON COLUMN aris71adm.sigapisymboltype.symbolorigname IS 'Nombre original del símbolo (inglés)';
COMMENT ON COLUMN aris71adm.sigapisymboltype.symbolimage IS 'Imagen del símbolo en formato binario';

-- Índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_symboltype_apiname ON aris71adm.sigapisymboltype(apiname);
CREATE INDEX IF NOT EXISTS idx_symboltype_symboltype ON aris71adm.sigapisymboltype(symboltype);
CREATE INDEX IF NOT EXISTS idx_symboltype_isdefault ON aris71adm.sigapisymboltype(isdefault);
CREATE INDEX IF NOT EXISTS idx_symboltype_isassigned ON aris71adm.sigapisymboltype(isassigned);
