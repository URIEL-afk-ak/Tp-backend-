-- Tabla facturas
CREATE TABLE IF NOT EXISTS facturas (
    id BIGSERIAL PRIMARY KEY,
    solicitud_id BIGINT,
    monto DOUBLE PRECISION,
    pagado BOOLEAN DEFAULT false,
    created_at TIMESTAMP
);