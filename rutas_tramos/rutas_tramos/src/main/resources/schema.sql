-- Tabla tramos
CREATE TABLE IF NOT EXISTS tramos (
    id BIGSERIAL PRIMARY KEY,
    solicitud_id BIGINT NOT NULL,
    transportista_id BIGINT,
    origen VARCHAR(255) NOT NULL,
    destino VARCHAR(255) NOT NULL,
    estado VARCHAR(50),
    costo_estimado DOUBLE PRECISION,
    duracion_minutos BIGINT,
    inicio_estimado TIMESTAMP,
    fin_estimado TIMESTAMP
);