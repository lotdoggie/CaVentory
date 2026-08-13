# CaVentory

CaVentory es una aplicación de escritorio para llevar el control de un inventario. Permite administrar productos, categorías, proveedores, colaboradores y movimientos de entrada o salida.

Fue desarrollada en Java con ventanas `JFrame Form` de NetBeans y utiliza PostgreSQL como base de datos.

## Versión

Versión 26.3.

## Funciones principales

- Inicio de sesión.
- Menús diferentes para administradores y colaboradores.
- Registro de productos, categorías y proveedores.
- Administración de colaboradores.
- Entradas, salidas y ajustes de inventario.
- Consulta de existencias.
- Avisos de existencia baja.
- Historial de movimientos.
- Resumen general.
- Reportes guardados en PDF.

## Requisitos

- Windows 10 u 11.
- JDK 25 o una versión compatible.
- Apache NetBeans 29 o una versión compatible.
- PostgreSQL y pgAdmin 4.
- Controlador JDBC de PostgreSQL.

Enlaces oficiales:

- [JDK](https://www.oracle.com/java/technologies/downloads/)
- [Apache NetBeans](https://netbeans.apache.org/front/main/download/)
- [PostgreSQL](https://www.postgresql.org/download/windows/)
- [Controlador JDBC](https://jdbc.postgresql.org/download/)

## Instalación

### 1. Descargar el proyecto

1. Descarga `Source code (zip)` desde la versión de GitHub.
2. Haz clic derecho sobre el archivo y selecciona **Extraer todo**.
3. Abre la carpeta extraída.

No abras el proyecto desde el interior del ZIP.

### 2. Crear la base de datos

1. Abre pgAdmin 4.
2. Conéctate a PostgreSQL.
3. Haz clic derecho sobre **Databases**.
4. Selecciona **Create > Database**.
5. Escribe `CaVentoryDB` como nombre.
6. Selecciona la base de datos y abre **Query Tool**.
7. Abre `base_datos/caventry_respaldo1.sql`.
8. Ejecuta todo el archivo.

El respaldo crea las tablas y los usuarios iniciales. Debe ejecutarse una sola vez sobre una base de datos vacía.

### 3. Configurar la conexión

1. Haz una copia de `conexion.properties.example`.
2. Cambia el nombre de la copia a `conexion.properties`.
3. Abre el archivo con el Bloc de notas.
4. Completa los datos de PostgreSQL:

```properties
servidor=localhost
puerto=5432
base_datos=CaVentoryDB
usuario=postgres
password=TU_CONTRASEÑA
```

`conexion.properties` contiene datos privados y no se sube a GitHub. Cada computadora debe tener su propio archivo.

### 4. Abrir el proyecto en NetBeans

1. Abre Apache NetBeans.
2. Selecciona **File > Open Project**.
3. Busca la carpeta que contiene `build.xml` y `nbproject`.
4. Selecciona CaVentory y presiona **Open Project**.

### 5. Agregar el controlador de PostgreSQL

El proyecto utiliza una librería llamada `PostgreSQLDriver`.

Si NetBeans muestra una referencia faltante:

1. Descarga el archivo JAR del controlador JDBC.
2. Abre **Tools > Libraries** en NetBeans.
3. Crea una librería llamada `PostgreSQLDriver`.
4. Agrega el archivo JAR descargado.
5. Vuelve a cargar el proyecto.

### 6. Ejecutar

1. Haz clic derecho sobre el proyecto.
2. Selecciona **Clean and Build**.
3. Selecciona **Run Project** o presiona `F6`.
4. Debe aparecer la ventana de inicio de sesión.

## Usuarios iniciales

| Rol | Usuario | Contraseña |
| --- | --- | --- |
| Administrador | `admin` | `admin123` |
| Colaborador | `trabajador` | `1234` |

Estas cuentas son para entrar a CaVentory. No son los datos de PostgreSQL escritos en `conexion.properties`.

## Uso recomendado

1. Inicia sesión como administrador.
2. Registra una categoría.
3. Registra un proveedor.
4. Registra los productos.
5. Crea colaboradores si son necesarios.
6. Utiliza entradas y salidas para modificar las existencias.
7. Revisa el resumen y los productos con existencia baja.
8. Guarda reportes en PDF cuando necesites compartir información.

## Permisos

### Administrador

Puede administrar productos, categorías, proveedores y colaboradores. También puede registrar movimientos, realizar ajustes, consultar el resumen y generar reportes.

### Colaborador

Puede consultar el inventario, registrar entradas y salidas y revisar su historial. No puede abrir las ventanas administrativas.

## Problemas comunes

### No hay conexión con la base de datos

- Comprueba que PostgreSQL esté iniciado.
- Revisa los datos de `conexion.properties`.
- Confirma que la base de datos se llame `CaVentoryDB`.
- Verifica que ejecutaste `caventry_respaldo1.sql`.

### El archivo termina como `.txt`

Debe llamarse exactamente `conexion.properties`. Activa las extensiones de nombre de archivo en el Explorador de Windows y elimina `.txt`.

### NetBeans muestra una referencia faltante

Comprueba que exista la librería `PostgreSQLDriver` y que contenga el controlador JDBC.

### El proyecto no aparece en NetBeans

Selecciona la carpeta que contiene `build.xml` y `nbproject`, no el ZIP ni una carpeta superior.

## Manual completo

El manual de usuario e instalación se encuentra en:

`documentacion/Manual_de_usuario_CaVentory_26.3.pdf`

## Repositorio

[https://github.com/lotdoggie/CaVentory](https://github.com/lotdoggie/CaVentory)
