-- Datos de depósitos
INSERT INTO depositos (nombre, direccion, ubicacion_geografica, fecha_creacion, fecha_actualizacion) VALUES
('Depósito Norte', 'Av. del Libertador 1234, CABA', '-34.5763, -58.4083', NOW(), NOW()),
('Depósito Sur', 'Av. San Juan 2345, CABA', '-34.6250, -58.3850', NOW(), NOW()),
('Depósito Oeste', 'Av. Rivadavia 5678, CABA', '-34.6100, -58.4600', NOW(), NOW());

-- Datos de estadías
INSERT INTO estadia_contenedores (contenedor_id, deposito_id, fecha_entrada, fecha_salida, dias_estadia, costo_calculado, fecha_creacion) VALUES
(1, 1, NOW() - INTERVAL '5 days', NULL, 5, 1250.00, NOW()),
(2, 2, NOW() - INTERVAL '3 days', NULL, 3, 750.00, NOW()),
(3, 1, NOW() - INTERVAL '10 days', NOW() - INTERVAL '2 days', 8, 2000.00, NOW());