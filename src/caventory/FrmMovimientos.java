/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package caventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pablo
 */
public class FrmMovimientos extends javax.swing.JFrame {

    public FrmMovimientos() {
        initComponents();
        setLocationRelativeTo(null);
        lblUsuario.setText("Usuario: " + CaVentory.usuarioActual);
        cargarProductos();
        cargarDatos();
    }

    private void cargarProductos() {
        cmbProducto.removeAllItems();
        cmbProducto.addItem("Seleccione");

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT codigo, nombre FROM productos ORDER BY nombre";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                cmbProducto.addItem(resultado.getString("codigo") + " - "
                        + resultado.getString("nombre"));
            }

            resultado.close();
            consulta.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los productos");
            System.err.println(e.toString());
        }
    }

    private void cargarDatos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaMovimientos.getModel();
        modelo.setRowCount(0);

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT m.id_movimiento, p.nombre AS producto, m.tipo, "
                    + "m.cantidad, m.fecha, u.usuario, m.observacion "
                    + "FROM movimientos m INNER JOIN productos p "
                    + "ON m.id_producto = p.id_producto INNER JOIN usuarios u "
                    + "ON m.id_user = u.id_user ORDER BY m.id_movimiento";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                modelo.addRow(new Object[]{
                    resultado.getInt("id_movimiento"),
                    resultado.getString("producto"),
                    resultado.getString("tipo"),
                    resultado.getInt("cantidad"),
                    resultado.getTimestamp("fecha"),
                    resultado.getString("usuario"),
                    resultado.getString("observacion")
                });
            }

            resultado.close();
            consulta.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los movimientos");
            System.err.println(e.toString());
        }
    }

    private void limpiarCampos() {
        cmbProducto.setSelectedIndex(0);
        cmbTipo.setSelectedIndex(0);
        txtCantidad.setText("");
        txtObservacion.setText("");
        cmbProducto.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblProducto = new javax.swing.JLabel();
        cmbProducto = new javax.swing.JComboBox<>();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        lblObservacion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        btnRegistrar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        lblHistorial = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaMovimientos = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Entradas y salidas");
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitulo.setText("Entradas y salidas");

        lblUsuario.setText("Usuario:");

        lblProducto.setText("Producto");

        cmbProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));

        lblTipo.setText("Tipo de movimiento");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Entrada", "Salida" }));

        lblCantidad.setText("Cantidad");

        lblObservacion.setText("Observacion");

        txtObservacion.setColumns(20);
        txtObservacion.setLineWrap(true);
        txtObservacion.setRows(5);
        txtObservacion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtObservacion);

        btnRegistrar.setText("Registrar movimiento");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        lblHistorial.setText("Historial temporal de movimientos");

        tablaMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Producto", "Tipo", "Cantidad", "Fecha", "Usuario", "Observacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaMovimientos);

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
                        .addComponent(lblUsuario))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblProducto)
                            .addComponent(cmbProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTipo)
                            .addComponent(cmbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCantidad)
                            .addComponent(txtCantidad)
                            .addComponent(lblObservacion)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHistorial)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblUsuario))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblCantidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblObservacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegistrar)
                            .addComponent(btnLimpiar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHistorial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnCerrar)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        if (cmbProducto.getSelectedIndex() == 0 || cmbTipo.getSelectedIndex() == 0
                || txtCantidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona el producto, tipo y cantidad");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un numero entero");
            return;
        }

        String producto = cmbProducto.getSelectedItem().toString();
        String codigo = producto.substring(0, producto.indexOf(" - "));
        String tipo = cmbTipo.getSelectedItem().toString();

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sqlProducto = "SELECT id_producto, existencia FROM productos "
                    + "WHERE codigo = ?";
            PreparedStatement buscarProducto = conexion.prepareStatement(sqlProducto);
            buscarProducto.setString(1, codigo);
            ResultSet resultado = buscarProducto.executeQuery();
            resultado.next();

            int idProducto = resultado.getInt("id_producto");
            int existencia = resultado.getInt("existencia");
            int nuevaExistencia;

            if (tipo.equals("Entrada")) {
                nuevaExistencia = existencia + cantidad;
            } else {
                if (cantidad > existencia) {
                    JOptionPane.showMessageDialog(this, "No hay suficiente existencia");
                    resultado.close();
                    buscarProducto.close();
                    conexion.close();
                    return;
                }
                nuevaExistencia = existencia - cantidad;
            }

            resultado.close();
            buscarProducto.close();

            String sqlMovimiento = "INSERT INTO movimientos(id_producto, id_user, tipo, "
                    + "cantidad, observacion) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement guardarMovimiento = conexion.prepareStatement(sqlMovimiento);
            guardarMovimiento.setInt(1, idProducto);
            guardarMovimiento.setInt(2, CaVentory.idUsuarioActual);
            guardarMovimiento.setString(3, tipo);
            guardarMovimiento.setInt(4, cantidad);
            guardarMovimiento.setString(5, txtObservacion.getText());
            guardarMovimiento.executeUpdate();

            String sqlExistencia = "UPDATE productos SET existencia = ? WHERE id_producto = ?";
            PreparedStatement actualizarExistencia = conexion.prepareStatement(sqlExistencia);
            actualizarExistencia.setInt(1, nuevaExistencia);
            actualizarExistencia.setInt(2, idProducto);
            actualizarExistencia.executeUpdate();

            guardarMovimiento.close();
            actualizarExistencia.close();
            conexion.close();
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Movimiento registrado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el movimiento");
            System.err.println(e.toString());
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbProducto;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblHistorial;
    private javax.swing.JLabel lblObservacion;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tablaMovimientos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables
}
