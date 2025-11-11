-- Datos de solicitudes (compatible con H2)
INSERT INTO solicitudes (cliente_id, origen, destino, peso_kg, volumen_m3, estado, created_at, updated_at) VALUES
(1, 'Buenos Aires', 'Rosario', 15000.0, 60.5, 'COMPLETADA', DATEADD('DAY', -7, NOW()), DATEADD('DAY', -5, NOW())),
(2, 'Córdoba', 'Mendoza', 12000.0, 45.2, 'EN_TRANSITO', DATEADD('DAY', -3, NOW()), NOW()),
(3, 'La Plata', 'Mar del Plata', 8000.0, 35.8, 'PENDIENTE', DATEADD('DAY', -1, NOW()), DATEADD('DAY', -1, NOW())),
(1, 'Rosario', 'Buenos Aires', 18000.0, 70.3, 'ACEPTADA', NOW(), NOW());
