-- Datos de tramos (compatible con H2)
INSERT INTO tramos (solicitud_id, transportista_id, origen, destino, estado, costo_estimado, duracion_minutos, inicio_estimado, fin_estimado) VALUES
(1, 1, 'Buenos Aires', 'Rosario', 'COMPLETADO', 45000.50, 360, DATEADD('DAY', -5, NOW()), DATEADD('DAY', -4, NOW())),
(2, 2, 'Córdoba', 'Mendoza', 'EN_RUTA', 32500.75, 480, NOW(), DATEADD('HOUR', 8, NOW())),
(3, 3, 'La Plata', 'Mar del Plata', 'PENDIENTE', 28700.00, 180, DATEADD('DAY', 2, NOW()), DATEADD('HOUR', 51, NOW()));
