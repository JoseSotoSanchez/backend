INSERT INTO Reparacion (nombre, descripcion, tipo_combustible, precio_gasolina, precio_diesel, precio_hibrido, precio_electrico)
VALUES
('Reparaciones del Sistema de Frenos', 'Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.', 'Gasolina', 120000, 120000, 180000, 220000),
('Servicio del Sistema de Refrigeración', 'Reparación o reemplazo de radiadores, bombas de agua, termostatos y mangueras, así como la solución de problemas de sobrecalentamiento.', 'Gasolina', 130000, 130000, 190000, 230000),
('Reparaciones del Motor', 'Desde reparaciones menores como el reemplazo de bujías y cables, hasta reparaciones mayores como la reconstrucción del motor o la reparación de la junta de la culata.', 'Gasolina', 350000, 450000, 700000, 800000),
('Reparaciones de la Transmisión', 'Incluyen la reparación o reemplazo de componentes de la transmisión manual o automática, cambios de líquido y solución de problemas de cambios de marcha.', 'Gasolina', 210000, 210000, 300000, 300000),
('Reparación del Sistema Eléctrico', 'Solución de problemas y reparación de alternadores, arrancadores, baterías y sistemas de cableado, así como la reparación de componentes eléctricos como faros, intermitentes y sistemas de entretenimiento.', 'Gasolina', 150000, 150000, 200000, 250000),
('Reparaciones del Sistema de Escape', 'Incluye el reemplazo del silenciador, tubos de escape, catalizador y la solución de problemas relacionados con las emisiones.', 'Gasolina', 100000, 120000, 450000, 0),
('Reparación de Neumáticos y Ruedas', 'Reparación de pinchazos, reemplazo de neumáticos, alineación y balanceo de ruedas.', 'Gasolina', 100000, 100000, 100000, 100000),
('Reparaciones de la Suspensión y la Dirección', 'Reemplazo de amortiguadores, brazos de control, rótulas y reparación del sistema de dirección asistida.', 'Gasolina', 180000, 180000, 210000, 250000),
('Reparación del Sistema de Aire Acondicionado y Calefacción', 'Incluye la recarga de refrigerante, reparación o reemplazo del compresor, y solución de problemas del sistema de calefacción.', 'Gasolina', 150000, 150000, 180000, 180000),
('Reparaciones del Sistema de Combustible', 'Limpieza o reemplazo de inyectores de combustible, reparación o reemplazo de la bomba de combustible y solución de problemas de suministro de combustible.', 'Gasolina', 130000, 140000, 220000, 0),
('Reparación y Reemplazo del Parabrisas y Cristales', 'Reparación de pequeñas grietas en el parabrisas o reemplazo completo de parabrisas y ventanas dañadas.', 'Gasolina', 80000, 80000, 80000, 80000);

INSERT INTO descuentos_reparaciones (num_reparaciones, desc_gasolina, desc_diesel, desc_hibrido, desc_electrico)
VALUES
    (1, 5, 7, 10, 8),
    (2, 5, 7, 10, 8),
    (3, 10, 12, 15, 13),
    (4, 10, 12, 15, 13),
    (5, 10, 12, 15, 13),
    (6, 15, 17, 20, 18),
    (7, 15, 17, 20, 18),
    (8, 15, 17, 20, 18),
    (9, 15, 17, 20, 18),
    (10, 20, 22, 25, 23);

INSERT INTO bonos(marca_vehiculo, cantidad, monto)
VALUES
    ('Toyota', 5, 70000),
    ('Ford', 2, 50000),
    ('Hyundai', 1, 30000),
    ('Honda', 7, 40000);