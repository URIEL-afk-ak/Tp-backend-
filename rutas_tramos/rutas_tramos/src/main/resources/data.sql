-- Datos de tramos
INSERT INTO tramos (solicitud_id, transportista_id, origen, destino, estado, costo_estimado, duracion_minutos, inicio_estimado, fin_estimado) VALUES
(1, 1, 'Buenos Aires', 'Rosario', 'COMPLETADO', 45000.50, 360, NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days'),
(2, 2, 'Córdoba', 'Mendoza', 'EN_RUTA', 32500.75, 480, NOW(), NOW() + INTERVAL '8 hours'),
(3, 3, 'La Plata', 'Mar del Plata', 'PENDIENTE', 28700.00, 180, NOW() + INTERVAL '2 days', NOW() + INTERVAL '2 days 3 hours');