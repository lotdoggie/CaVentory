package caventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection conectar() {
        String url = "jdbc:postgresql://localhost:5432/CaVentoryDB";
        String usuario = "postgres";
        String password = "0922";
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion correcta con CaVentoryDB");
        } catch (SQLException e) {
            System.err.println("No se pudo conectar con CaVentoryDB");
            System.err.println(e.toString());
        }

        return conexion;
    }
}
