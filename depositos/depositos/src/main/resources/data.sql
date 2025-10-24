-- Datos iniciales para depósitos
INSERT INTO depositos (nombre, direccion, ubicacion_geografica, fecha_creacion) VALUES
('Depósito Central', 'Av. Corrientes 1234', 'Lat: -34.6037, Lon: -58.3816', NOW()),
('Depósito Norte', 'Ruta 9 Km 45', 'Lat: -34.4732, Lon: -58.5173', NOW()),
('Depósito Sur', 'Av. San Juan 5678', 'Lat: -34.9215, Lon: -57.9545', NOW());

-- Datos iniciales para estadías de contenedores
INSERT INTO estadia_contenedores (contenedor_id, deposito_id, fecha_entrada, fecha_salida, dias_estadia, costo_calculado, fecha_creacion) VALUES
(1, 1, '2024-01-15 10:00:00', '2024-01-20 14:30:00', 5, 500.0, NOW()),
(2, 1, '2024-01-18 09:15:00', NULL, NULL, NULL, NOW()),
(3, 2, '2024-01-20 11:00:00', NULL, NULL, NULL, NOW());