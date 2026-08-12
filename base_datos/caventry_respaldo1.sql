CREATE TABLE Usuarios (
    ID_user SERIAL PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Usuario VARCHAR(50) UNIQUE NOT NULL,
    Contrasena VARCHAR(100) NOT NULL,
    Rol VARCHAR(20) NOT NULL CHECK (Rol IN ('Administrador', 'Colaborador')),
    Activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE Categorias (
    ID_categoria SERIAL PRIMARY KEY,
    Nombre VARCHAR(100) UNIQUE NOT NULL,
    Descripcion VARCHAR(200)
);

CREATE TABLE Proveedores (
    ID_proveedor SERIAL PRIMARY KEY,
    Nombre VARCHAR(100) UNIQUE NOT NULL,
    Telefono VARCHAR(30),
    Correo VARCHAR(100),
    Direccion VARCHAR(200),
    Activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE Productos (
    ID_producto SERIAL PRIMARY KEY,
    Codigo VARCHAR(30) UNIQUE NOT NULL,
    Nombre VARCHAR(100) NOT NULL,
    ID_categoria INT NOT NULL,
    ID_proveedor INT NOT NULL,
    Precio NUMERIC(10,2) NOT NULL CHECK (Precio >= 0),
    Existencia INT NOT NULL CHECK (Existencia >= 0),
    Stock_minimo INT NOT NULL CHECK (Stock_minimo >= 0),
    FOREIGN KEY (ID_categoria) REFERENCES Categorias(ID_categoria),
    FOREIGN KEY (ID_proveedor) REFERENCES Proveedores(ID_proveedor)
);

CREATE TABLE Movimientos (
    ID_movimiento SERIAL PRIMARY KEY,
    ID_producto INT NOT NULL,
    ID_user INT NOT NULL,
    Tipo VARCHAR(20) NOT NULL CHECK (Tipo IN ('Entrada', 'Salida')),
    Cantidad INT NOT NULL CHECK (Cantidad > 0),
    Fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Observacion VARCHAR(200),
    FOREIGN KEY (ID_producto) REFERENCES Productos(ID_producto),
    FOREIGN KEY (ID_user) REFERENCES Usuarios(ID_user)
);

INSERT INTO Usuarios (Nombre, Usuario, Contrasena, Rol, Activo)
VALUES ('Administrador', 'admin', 'admin123', 'Administrador', TRUE);

INSERT INTO Usuarios (Nombre, Usuario, Contrasena, Rol, Activo)
VALUES ('Trabajador', 'trabajador', '1234', 'Colaborador', TRUE);
