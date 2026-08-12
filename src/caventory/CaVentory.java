/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package caventory;

/**
 *
 * @author pablo
 */
public class CaVentory {

    public static String usuarioActual = "";
    public static String rolActual = "";
    public static int idUsuarioActual;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FrmInicioSesion inicio = new FrmInicioSesion();
        inicio.setVisible(true);
    }
    
}
