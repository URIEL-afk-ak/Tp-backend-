-- Esquema de la base de datos para clientes
-- Esta tabla se crea automáticamente por JPA, pero incluimos el script para referencia

CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP NOT NULL
);
