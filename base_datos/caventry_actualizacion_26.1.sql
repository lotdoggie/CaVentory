ALTER TABLE Usuarios
ADD CONSTRAINT usuarios_rol_valido
CHECK (Rol IN ('Administrador', 'Colaborador'));

ALTER TABLE Categorias
ADD CONSTRAINT categorias_nombre_unico
UNIQUE (Nombre);

ALTER TABLE Productos
ADD CONSTRAINT productos_precio_valido
CHECK (Precio >= 0);

ALTER TABLE Productos
ADD CONSTRAINT productos_existencia_valida
CHECK (Existencia >= 0);

ALTER TABLE Productos
ADD CONSTRAINT productos_stock_minimo_valido
CHECK (Stock_minimo >= 0);

ALTER TABLE Movimientos
ADD CONSTRAINT movimientos_tipo_valido
CHECK (Tipo IN ('Entrada', 'Salida'));

ALTER TABLE Movimientos
ADD CONSTRAINT movimientos_cantidad_valida
CHECK (Cantidad > 0);
