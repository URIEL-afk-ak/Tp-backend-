-- Datos de contenedores
INSERT INTO contenedores (cliente_id, numero_identificacion, tipo, capacidad, ubicacion, estado, fecha_creacion, fecha_actualizacion) VALUES
(1, 'CONT-001-2024', 'DRY', 33.2, 'Puerto Buenos Aires', 'PENDIENTE', NOW(), NOW()),
(1, 'CONT-002-2024', 'REEFER', 28.5, 'Depósito Central', 'EN_TRANSITO', NOW(), NOW()),
(2, 'CONT-003-2024', 'OPEN_TOP', 30.0, 'Puerto Rosario', 'ENTREGADO', NOW(), NOW()),
(3, 'CONT-004-2024', 'FLAT_RACK', 25.8, 'Planta Industrial', 'MANTENIMIENTO', NOW(), NOW());