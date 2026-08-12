CREATE TABLE Proveedores (
    ID_proveedor SERIAL PRIMARY KEY,
    Nombre VARCHAR(100) UNIQUE NOT NULL,
    Telefono VARCHAR(30),
    Correo VARCHAR(100),
    Direccion VARCHAR(200),
    Activo BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE Productos
ADD COLUMN ID_proveedor INT;

ALTER TABLE Productos
ALTER COLUMN ID_proveedor SET NOT NULL;

ALTER TABLE Productos
ADD CONSTRAINT productos_id_proveedor_fkey
FOREIGN KEY (ID_proveedor) REFERENCES Proveedores(ID_proveedor);
