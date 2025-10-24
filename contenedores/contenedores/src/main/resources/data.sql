-- Eliminar la tabla si existe y dejar que JPA la cree
DELETE FROM contenedores;

-- Insertar datos de ejemplo
INSERT INTO contenedores (numero_identificacion, tipo, capacidad, ubicacion, estado, cliente_id, fecha_creacion, fecha_actualizacion, observaciones) VALUES
('CONT001', '20ft', 20.0, 'Puerto Buenos Aires', 'PENDIENTE', 1, '2024-01-15 10:00:00', '2024-01-15 10:00:00', 'Contenedor estándar 20 pies'),
('CONT002', '40ft', 40.0, 'Puerto Rosario', 'EN_TRANSITO', 2, '2024-01-16 14:30:00', '2024-01-16 14:30:00', 'Contenedor refrigerado 40 pies'),
('CONT003', '20ft', 20.0, 'Puerto La Plata', 'ENTREGADO', 1, '2024-01-10 09:15:00', '2024-01-12 16:45:00', 'Contenedor con mercadería general'),
('CONT004', '40ft', 40.0, 'Puerto Mar del Plata', 'PENDIENTE', 3, '2024-01-17 11:20:00', '2024-01-17 11:20:00', 'Contenedor para productos químicos'),
('CONT005', '20ft', 20.0, 'Puerto Bahía Blanca', 'MANTENIMIENTO', 2, '2024-01-05 08:00:00', '2024-01-18 13:30:00', 'Requiere reparación de puertas');