-- Datos de transportistas
INSERT INTO transportistas (nombre, codigo, contacto, telefono, email, activo) VALUES
('Transportes del Norte SA', 'TN001', 'Juan Pérez', '+541134567890', 'juan@transportesdelnorte.com', true),
('Logística Sur SRL', 'LS002', 'María García', '+541187654321', 'maria@logisticasur.com', true),
('Carga Express', 'CE003', 'Carlos López', '+541123456789', 'carlos@cargaexpress.com', true);

-- Datos de camiones
INSERT INTO camiones (transportista_id, patente, capacidad_peso_kg, capacidad_volumen_m3, costo_base_km, disponible) VALUES
(1, 'ABC123', 20000.0, 80.0, 150.50, true),
(1, 'DEF456', 15000.0, 60.0, 120.75, true),
(2, 'GHI789', 25000.0, 100.0, 180.25, true),
(3, 'JKL012', 18000.0, 70.0, 135.00, false);