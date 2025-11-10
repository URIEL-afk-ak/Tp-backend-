-- Tabla contenedores
CREATE TABLE IF NOT EXISTS contenedores (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    numero_identificacion VARCHAR(255) UNIQUE NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    capacidad DOUBLE PRECISION NOT NULL,
    ubicacion VARCHAR(255) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP,
    fecha_entrega TIMESTAMP,
    observaciones VARCHAR(500)
);