package caventory.ventanas;

import caventory.CaVentory;
import caventory.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FrmMenuColaborador extends javax.swing.JFrame {

    public FrmMenuColaborador() {
        initComponents();
        setLocationRelativeTo(null);
        lblUsuarioActual.setText("Usuario: " + CaVentory.usuarioActual);
        lblRolActual.setText("Rol: " + CaVentory.rolActual);
        cargarResumen();
    }

    private void cargarResumen() {
        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            lblResumen.setText("No se pudo cargar el resumen del inventario");
            return;
        }
        try {
            String sql = "SELECT (SELECT COUNT(*) FROM productos) AS productos, "
                    + "(SELECT COUNT(*) FROM productos WHERE existencia <= stock_minimo) AS bajos, "
                    + "(SELECT COUNT(*) FROM movimientos WHERE id_user = ? "
                    + "AND fecha::date = CURRENT_DATE) AS movimientos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, CaVentory.idUsuarioActual);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                lblResumen.setText("Productos: " + resultado.getInt("productos")
                        + " | Existencia baja: " + resultado.getInt("bajos")
                        + " | Mis movimientos de hoy: " + resultado.getInt("movimientos"));
            }

            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            lblResumen.setText("No se pudo cargar el resumen del inventario");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        lblUsuarioActual = new javax.swing.JLabel();
        lblRolActual = new javax.swing.JLabel();
        lblResumen = new javax.swing.JLabel();
        lblInstruccion = new javax.swing.JLabel();
        btnConsultarProductos = new javax.swing.JButton();
        btnMovimientos = new javax.swing.JButton();
        btnMiActividad = new javax.swing.JButton();
        lblPermisos = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CaVentory - Colaborador");
        setResizable(false);
        setBackground(new java.awt.Color(246, 248, 246));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(35, 82, 60));
        lblTitulo.setText("Panel del colaborador");

        lblVersion.setText("CaVentory 26.3");

        lblUsuarioActual.setText("Usuario:");

        lblRolActual.setText("Rol:");

        lblResumen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResumen.setText("Productos: 0 | Existencia baja: 0 | Mis movimientos de hoy: 0");

        lblInstruccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblInstruccion.setText("Acciones disponibles");

        btnConsultarProductos.setText("Consultar inventario");
        btnConsultarProductos.setBackground(new java.awt.Color(47, 107, 79));
        btnConsultarProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarProductos.setFocusPainted(false);
        btnConsultarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarProductosActionPerformed(evt);
            }
        });

        btnMovimientos.setText("Registrar entrada o salida");
        btnMovimientos.setBackground(new java.awt.Color(226, 239, 231));
        btnMovimientos.setFocusPainted(false);
        btnMovimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimientosActionPerformed(evt);
            }
        });

        btnMiActividad.setText("Historial de movimientos");
        btnMiActividad.setBackground(new java.awt.Color(226, 239, 231));
        btnMiActividad.setFocusPainted(false);
        btnMiActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiActividadActionPerformed(evt);
            }
        });

        lblPermisos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPermisos.setText("Puedes consultar existencias y registrar movimientos.");

        btnActualizar.setText("Actualizar datos");
        btnActualizar.setBackground(new java.awt.Color(47, 107, 79));
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblVersion))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblUsuarioActual, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRolActual, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblResumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblInstruccion)
                    .addComponent(btnConsultarProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(btnMiActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblPermisos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblVersion))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuarioActual)
                    .addComponent(lblRolActual))
                .addGap(12, 12, 12)
                .addComponent(lblResumen)
                .addGap(22, 22, 22)
                .addComponent(lblInstruccion)
                .addGap(12, 12, 12)
                .addComponent(btnConsultarProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnMovimientos, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(btnMiActividad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(lblPermisos)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnCerrarSesion)
                    .addComponent(btnSalir))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarProductosActionPerformed
        FrmConsultaProductos consulta = new FrmConsultaProductos();
        consulta.setVisible(true);
    }//GEN-LAST:event_btnConsultarProductosActionPerformed

    private void btnMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovimientosActionPerformed
        FrmMovimientos movimientos = new FrmMovimientos();
        movimientos.setVisible(true);
    }//GEN-LAST:event_btnMovimientosActionPerformed

    private void btnMiActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiActividadActionPerformed
        FrmMiActividad actividad = new FrmMiActividad();
        actividad.setVisible(true);
    }//GEN-LAST:event_btnMiActividadActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarResumen();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        CaVentory.limpiarSesion();
        FrmInicioSesion inicio = new FrmInicioSesion();
        inicio.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Deseas salir de CaVentory?", "Salir",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnConsultarProductos;
    private javax.swing.JButton btnMiActividad;
    private javax.swing.JButton btnMovimientos;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel lblInstruccion;
    private javax.swing.JLabel lblPermisos;
    private javax.swing.JLabel lblRolActual;
    private javax.swing.JLabel lblResumen;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuarioActual;
    private javax.swing.JLabel lblVersion;
    // End of variables declaration//GEN-END:variables
}
