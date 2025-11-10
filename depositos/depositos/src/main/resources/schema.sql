-- Tabla depositos
CREATE TABLE IF NOT EXISTS depositos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    ubicacion_geografica VARCHAR(255),
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP
);

-- Tabla estadia_contenedores
CREATE TABLE IF NOT EXISTS estadia_contenedores (
    id BIGSERIAL PRIMARY KEY,
    contenedor_id BIGINT NOT NULL,
    deposito_id BIGINT NOT NULL,
    fecha_entrada TIMESTAMP NOT NULL,
    fecha_salida TIMESTAMP,
    dias_estadia INTEGER,
    costo_calculado DOUBLE PRECISION,
    fecha_creacion TIMESTAMP NOT NULL
);