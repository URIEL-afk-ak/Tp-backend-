-- Tabla transportistas
CREATE TABLE IF NOT EXISTS transportistas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    codigo VARCHAR(255) UNIQUE NOT NULL,
    contacto VARCHAR(255) NOT NULL,
    telefono VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT true
);

-- Tabla camiones
CREATE TABLE IF NOT EXISTS camiones (
    id BIGSERIAL PRIMARY KEY,
    transportista_id BIGINT NOT NULL,
    patente VARCHAR(255) UNIQUE NOT NULL,
    capacidad_peso_kg DOUBLE PRECISION NOT NULL,
    capacidad_volumen_m3 DOUBLE PRECISION NOT NULL,
    costo_base_km DOUBLE PRECISION NOT NULL,
    disponible BOOLEAN DEFAULT true,
    FOREIGN KEY (transportista_id) REFERENCES transportistas(id)
);