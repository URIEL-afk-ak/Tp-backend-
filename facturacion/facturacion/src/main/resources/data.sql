-- Datos de facturas
INSERT INTO facturas (solicitud_id, monto, pagado, created_at) VALUES
(1, 45000.50, true, DATEADD('DAY', -5, NOW())),
(2, 32500.75, false, DATEADD('DAY', -3, NOW())),
(3, 28700.00, true, DATEADD('DAY', -1, NOW()));