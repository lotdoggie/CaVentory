/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package caventory.ventanas;

import caventory.CaVentory;
import caventory.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author pablo
 */
public class FrmMenuPrincipal extends javax.swing.JFrame {

    public FrmMenuPrincipal() {
        initComponents();
        setLocationRelativeTo(null);
        lblUsuarioActual.setText("Usuario: " + CaVentory.usuarioActual);
        lblRolActual.setText("Rol: " + CaVentory.rolActual);
        lblPermisos.setText("Puedes administrar productos, categorías, movimientos y colaboradores.");
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
                    + "(SELECT COUNT(*) FROM movimientos WHERE fecha::date = CURRENT_DATE) AS movimientos";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                lblResumen.setText("Productos: " + resultado.getInt("productos")
                        + " | Existencia baja: " + resultado.getInt("bajos")
                        + " | Movimientos de hoy: " + resultado.getInt("movimientos"));
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
        btnProductos = new javax.swing.JButton();
        btnMovimientos = new javax.swing.JButton();
        btnCategorias = new javax.swing.JButton();
        btnColaboradores = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnResumen = new javax.swing.JButton();
        lblPermisos = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CaVentory - Administrador");
        setResizable(false);
        setBackground(new java.awt.Color(245, 247, 250));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(31, 78, 121));
        lblTitulo.setText("Panel del administrador");

        lblVersion.setText("CaVentory 26.1d");

        lblUsuarioActual.setText("Usuario:");

        lblRolActual.setText("Rol:");

        lblResumen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResumen.setText("Productos: 0 | Existencia baja: 0 | Movimientos de hoy: 0");

        lblInstruccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblInstruccion.setText("Módulos del sistema");

        btnProductos.setText("Productos");
        btnProductos.setBackground(new java.awt.Color(225, 235, 245));
        btnProductos.setFocusPainted(false);
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnMovimientos.setText("Entradas y salidas");
        btnMovimientos.setBackground(new java.awt.Color(225, 235, 245));
        btnMovimientos.setFocusPainted(false);
        btnMovimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimientosActionPerformed(evt);
            }
        });

        btnCategorias.setText("Categorías");
        btnCategorias.setBackground(new java.awt.Color(225, 235, 245));
        btnCategorias.setFocusPainted(false);
        btnCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriasActionPerformed(evt);
            }
        });

        btnColaboradores.setText("Colaboradores");
        btnColaboradores.setBackground(new java.awt.Color(225, 235, 245));
        btnColaboradores.setFocusPainted(false);
        btnColaboradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColaboradoresActionPerformed(evt);
            }
        });

        btnProveedores.setText("Proveedores");
        btnProveedores.setBackground(new java.awt.Color(225, 235, 245));
        btnProveedores.setFocusPainted(false);
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });

        btnResumen.setText("Resumen de inventario");
        btnResumen.setBackground(new java.awt.Color(47, 111, 163));
        btnResumen.setForeground(new java.awt.Color(255, 255, 255));
        btnResumen.setFocusPainted(false);
        btnResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumenActionPerformed(evt);
            }
        });

        lblPermisos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPermisos.setText("Permisos del usuario");

        btnActualizar.setText("Actualizar datos");
        btnActualizar.setBackground(new java.awt.Color(47, 111, 163));
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
                        .addComponent(lblUsuarioActual, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRolActual, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblResumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblInstruccion)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(btnCategorias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnMovimientos, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(btnColaboradores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnResumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(btnMovimientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCategorias, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(btnColaboradores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(btnResumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        FrmProductos productos = new FrmProductos();
        productos.setVisible(true);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnMovimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovimientosActionPerformed
        FrmMovimientos movimientos = new FrmMovimientos();
        movimientos.setVisible(true);
    }//GEN-LAST:event_btnMovimientosActionPerformed

    private void btnCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriasActionPerformed
        FrmCategorias categorias = new FrmCategorias();
        categorias.setVisible(true);
    }//GEN-LAST:event_btnCategoriasActionPerformed

    private void btnColaboradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColaboradoresActionPerformed
        FrmColaboradores colaboradores = new FrmColaboradores();
        colaboradores.setVisible(true);
    }//GEN-LAST:event_btnColaboradoresActionPerformed

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        FrmProveedores proveedores = new FrmProveedores();
        proveedores.setVisible(true);
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumenActionPerformed
        FrmResumenInventario resumen = new FrmResumenInventario();
        resumen.setVisible(true);
    }//GEN-LAST:event_btnResumenActionPerformed

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
    private javax.swing.JButton btnCategorias;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnColaboradores;
    private javax.swing.JButton btnMovimientos;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnResumen;
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
