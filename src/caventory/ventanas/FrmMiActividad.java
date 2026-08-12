package caventory.ventanas;

import caventory.CaVentory;
import caventory.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmMiActividad extends javax.swing.JFrame {

    private boolean soloHoy = false;

    public FrmMiActividad() {
        initComponents();
        setLocationRelativeTo(null);
        lblUsuario.setText("Usuario: " + CaVentory.usuarioActual);
        cargarDatos();
    }

    private void cargarDatos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaActividad.getModel();
        modelo.setRowCount(0);

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String filtro = "";
            if (soloHoy) {
                filtro = "AND m.fecha::date = CURRENT_DATE ";
            }

            String sql = "SELECT m.id_movimiento, p.codigo, p.nombre AS producto, "
                    + "m.tipo, m.cantidad, m.fecha, m.observacion "
                    + "FROM movimientos m INNER JOIN productos p "
                    + "ON m.id_producto = p.id_producto WHERE m.id_user = ? "
                    + filtro
                    + "ORDER BY m.fecha DESC, m.id_movimiento DESC LIMIT 50";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, CaVentory.idUsuarioActual);
            ResultSet resultado = consulta.executeQuery();

            int total = 0;
            int entradas = 0;
            int salidas = 0;

            while (resultado.next()) {
                total++;
                if (resultado.getString("tipo").equals("Entrada")) {
                    entradas++;
                } else {
                    salidas++;
                }
                modelo.addRow(new Object[]{
                    resultado.getInt("id_movimiento"),
                    resultado.getString("codigo"),
                    resultado.getString("producto"),
                    resultado.getString("tipo"),
                    resultado.getInt("cantidad"),
                    resultado.getTimestamp("fecha"),
                    resultado.getString("observacion")
                });
            }

            lblResumen.setText("Movimientos mostrados: " + total
                    + " | Entradas: " + entradas + " | Salidas: " + salidas);

            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar tu actividad");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblAviso = new javax.swing.JLabel();
        btnHoy = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaActividad = new javax.swing.JTable();
        lblResumen = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Historial de movimientos");
        setResizable(false);
        setBackground(new java.awt.Color(245, 247, 250));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(31, 78, 121));
        lblTitulo.setText("Historial de movimientos");

        lblUsuario.setText("Usuario:");

        lblAviso.setText("Consulta los movimientos registrados por tu usuario");

        btnHoy.setText("Hoy");
        btnHoy.setBackground(new java.awt.Color(225, 235, 245));
        btnHoy.setFocusPainted(false);
        btnHoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoyActionPerformed(evt);
            }
        });

        btnTodos.setText("Todos");
        btnTodos.setBackground(new java.awt.Color(225, 235, 245));
        btnTodos.setFocusPainted(false);
        btnTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodosActionPerformed(evt);
            }
        });

        tablaActividad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Producto", "Tipo", "Cantidad", "Fecha", "Observación"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaActividad.setRowHeight(24);
        tablaActividad.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tablaActividad);

        lblResumen.setText("Movimientos mostrados: 0 | Entradas: 0 | Salidas: 0");

        btnActualizar.setText("Actualizar");
        btnActualizar.setBackground(new java.awt.Color(47, 111, 163));
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblAviso)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblResumen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblUsuario))
                .addGap(12, 12, 12)
                .addComponent(lblAviso)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHoy)
                    .addComponent(btnTodos)
                    .addComponent(btnActualizar))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResumen)
                    .addComponent(btnCerrar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarDatos();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoyActionPerformed
        soloHoy = true;
        cargarDatos();
    }//GEN-LAST:event_btnHoyActionPerformed

    private void btnTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodosActionPerformed
        soloHoy = false;
        cargarDatos();
    }//GEN-LAST:event_btnTodosActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnHoy;
    private javax.swing.JButton btnTodos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblResumen;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tablaActividad;
    // End of variables declaration//GEN-END:variables
}
