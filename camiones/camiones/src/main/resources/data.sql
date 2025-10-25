-- Mantener solo datos para tablas existentes (transportistas, camiones)

DELETE FROM camiones;
DELETE FROM transportistas;

-- Insertar datos de ejemplo para transportistas
INSERT INTO transportistas (nombre, codigo, contacto, telefono, email, activo) VALUES
('Transportes del Norte S.A.', 'TRANS_NORTE', 'Juan Pérez', '+54 11 1234-5678', 'juan@transnorte.com', true),
('Logística Sur SRL', 'LOG_SUR', 'María García', '+54 11 2345-6789', 'maria@logsur.com', true),
('Carga Express', 'CARGA_EXP', 'Carlos López', '+54 11 3456-7890', 'carlos@cargaexp.com', true);

-- Insertar datos de ejemplo para camiones
INSERT INTO camiones (transportista_id, patente, capacidad_peso_kg, capacidad_volumen_m3, costo_base_km, disponible) VALUES
(1, 'ABC123', 25000.0, 80.0, 1500.0, true),
(1, 'DEF456', 18000.0, 60.0, 1200.0, true),
(2, 'GHI789', 30000.0, 100.0, 2000.0, false),
(2, 'JKL012', 22000.0, 75.0, 1600.0, true),
(3, 'MNO345', 15000.0, 50.0, 1000.0, true);