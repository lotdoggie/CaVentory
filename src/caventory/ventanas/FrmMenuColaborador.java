package caventory.ventanas;

import caventory.CaVentory;
import javax.swing.JOptionPane;

public class FrmMenuColaborador extends javax.swing.JFrame {

    public FrmMenuColaborador() {
        initComponents();
        setLocationRelativeTo(null);
        lblUsuarioActual.setText("Usuario: " + CaVentory.usuarioActual);
        lblRolActual.setText("Rol: " + CaVentory.rolActual);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        lblUsuarioActual = new javax.swing.JLabel();
        lblRolActual = new javax.swing.JLabel();
        lblInstruccion = new javax.swing.JLabel();
        btnConsultarProductos = new javax.swing.JButton();
        btnMovimientos = new javax.swing.JButton();
        lblPermisos = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CaVentory - Colaborador");
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitulo.setText("Panel del colaborador");

        lblVersion.setText("CaVentory 26.1");

        lblUsuarioActual.setText("Usuario:");

        lblRolActual.setText("Rol:");

        lblInstruccion.setText("Selecciona la actividad que deseas realizar");

        btnConsultarProductos.setText("Consultar inventario");
        btnConsultarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarProductosActionPerformed(evt);
            }
        });

        btnMovimientos.setText("Registrar entrada o salida");
        btnMovimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimientosActionPerformed(evt);
            }
        });

        lblPermisos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPermisos.setText("Puedes consultar existencias y registrar movimientos.");

        btnCerrarSesion.setText("Cerrar sesion");
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
                    .addComponent(lblInstruccion)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConsultarProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(btnMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblPermisos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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
                .addGap(22, 22, 22)
                .addComponent(lblInstruccion)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnConsultarProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(btnMovimientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(lblPermisos)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        CaVentory.idUsuarioActual = 0;
        CaVentory.usuarioActual = "";
        CaVentory.rolActual = "";
        FrmInicioSesion inicio = new FrmInicioSesion();
        inicio.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this,
                "Deseas salir de CaVentory?", "Salir",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnConsultarProductos;
    private javax.swing.JButton btnMovimientos;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel lblInstruccion;
    private javax.swing.JLabel lblPermisos;
    private javax.swing.JLabel lblRolActual;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuarioActual;
    private javax.swing.JLabel lblVersion;
    // End of variables declaration//GEN-END:variables
}
