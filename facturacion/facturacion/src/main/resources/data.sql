-- Datos de facturas
INSERT INTO facturas (solicitud_id, monto, pagado, created_at) VALUES
(1, 45000.50, true, NOW() - INTERVAL '5 days'),
(2, 32500.75, false, NOW() - INTERVAL '3 days'),
(3, 28700.00, true, NOW() - INTERVAL '1 day');