-- Tabla solicitudes
CREATE TABLE IF NOT EXISTS solicitudes (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    origen VARCHAR(255) NOT NULL,
    destino VARCHAR(255) NOT NULL,
    peso_kg DOUBLE PRECISION,
    volumen_m3 DOUBLE PRECISION,
    estado VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);