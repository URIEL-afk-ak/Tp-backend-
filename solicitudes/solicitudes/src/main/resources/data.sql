-- Datos de solicitudes
INSERT INTO solicitudes (cliente_id, origen, destino, peso_kg, volumen_m3, estado, created_at, updated_at) VALUES
(1, 'Buenos Aires', 'Rosario', 15000.0, 60.5, 'COMPLETADA', NOW() - INTERVAL '7 days', NOW() - INTERVAL '5 days'),
(2, 'Córdoba', 'Mendoza', 12000.0, 45.2, 'EN_TRANSITO', NOW() - INTERVAL '3 days', NOW()),
(3, 'La Plata', 'Mar del Plata', 8000.0, 35.8, 'PENDIENTE', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day'),
(1, 'Rosario', 'Buenos Aires', 18000.0, 70.3, 'ACEPTADA', NOW(), NOW());