package caventory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    public static Connection conectar() {
        Properties datos = new Properties();

        try {
            String rutaArchivo = System.getProperty("conexion.archivo",
                    "conexion.properties");
            FileInputStream archivo = new FileInputStream(rutaArchivo);
            datos.load(archivo);
            archivo.close();

            String servidor = datos.getProperty("servidor");
            String puerto = datos.getProperty("puerto");
            String baseDatos = datos.getProperty("base_datos");
            String usuario = datos.getProperty("usuario");
            String password = datos.getProperty("password");
            String url = "jdbc:postgresql://" + servidor + ":" + puerto
                    + "/" + baseDatos;

            return DriverManager.getConnection(url, usuario, password);
        } catch (IOException e) {
            System.err.println("No se encontró el archivo conexion.properties");
        } catch (SQLException e) {
            System.err.println("No se pudo conectar con la base de datos");
            System.err.println(e.toString());
        }
        return null;
    }

    public static void cerrar(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("No se pudo cerrar la conexión");
            }
        }
    }
}
