package caventory.ventanas;

import caventory.CaVentory;
import caventory.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmAjustes extends javax.swing.JFrame {

    public FrmAjustes() {
        initComponents();
        setLocationRelativeTo(null);
        lblUsuario.setText("Usuario: " + CaVentory.usuarioActual);
        cargarProductos();
        cargarAjustes();
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
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar los productos");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    private String obtenerCodigo() {
        if (cmbProducto.getSelectedIndex() <= 0) {
            return "";
        }
        String producto = cmbProducto.getSelectedItem().toString();
        return producto.substring(0, producto.indexOf(" - "));
    }

    private void cargarExistencia() {
        String codigo = obtenerCodigo();
        if (codigo.isEmpty()) {
            lblExistencia.setText("Existencia actual: 0");
            return;
        }

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT existencia FROM productos WHERE codigo = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, codigo);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                lblExistencia.setText("Existencia actual: "
                        + resultado.getInt("existencia"));
            }

            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo consultar la existencia");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    private void cargarAjustes() {
        DefaultTableModel modelo = (DefaultTableModel) tablaAjustes.getModel();
        modelo.setRowCount(0);

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT m.fecha, p.nombre AS producto, m.tipo, "
                    + "m.cantidad, u.usuario, m.observacion FROM movimientos m "
                    + "INNER JOIN productos p ON m.id_producto = p.id_producto "
                    + "INNER JOIN usuarios u ON m.id_user = u.id_user "
                    + "WHERE m.observacion LIKE 'Ajuste de inventario%' "
                    + "ORDER BY m.fecha DESC, m.id_movimiento DESC";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                modelo.addRow(new Object[]{
                    resultado.getTimestamp("fecha"),
                    resultado.getString("producto"),
                    resultado.getString("tipo"),
                    resultado.getInt("cantidad"),
                    resultado.getString("usuario"),
                    resultado.getString("observacion")
                });
            }

            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar los ajustes");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    private void limpiarCampos() {
        cmbProducto.setSelectedIndex(0);
        txtNuevaExistencia.setText("");
        cmbMotivo.setSelectedIndex(0);
        txtDetalle.setText("");
        lblExistencia.setText("Existencia actual: 0");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblProducto = new javax.swing.JLabel();
        cmbProducto = new javax.swing.JComboBox<>();
        lblExistencia = new javax.swing.JLabel();
        lblNuevaExistencia = new javax.swing.JLabel();
        txtNuevaExistencia = new javax.swing.JTextField();
        lblMotivo = new javax.swing.JLabel();
        cmbMotivo = new javax.swing.JComboBox<>();
        lblDetalle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDetalle = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        lblHistorial = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaAjustes = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Ajustes de inventario");
        setResizable(false);
        setBackground(new java.awt.Color(246, 248, 246));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(35, 82, 60));
        lblTitulo.setText("Ajustes de inventario");

        lblUsuario.setText("Usuario:");

        lblProducto.setText("Producto");

        cmbProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));
        cmbProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProductoActionPerformed(evt);
            }
        });

        lblExistencia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblExistencia.setForeground(new java.awt.Color(35, 82, 60));
        lblExistencia.setText("Existencia actual: 0");

        lblNuevaExistencia.setText("Nueva existencia");

        lblMotivo.setText("Motivo");

        cmbMotivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Conteo físico", "Producto dañado", "Producto perdido", "Error de captura", "Devolución", "Otro" }));

        lblDetalle.setText("Detalle adicional");

        txtDetalle.setColumns(20);
        txtDetalle.setLineWrap(true);
        txtDetalle.setRows(5);
        txtDetalle.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDetalle);

        btnGuardar.setBackground(new java.awt.Color(47, 107, 79));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setText("Guardar ajuste");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        lblHistorial.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHistorial.setText("Historial de ajustes");

        tablaAjustes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Producto", "Tipo", "Cantidad", "Usuario", "Observación"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAjustes.setRowHeight(24);
        tablaAjustes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tablaAjustes);

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
                            .addComponent(cmbProducto, 0, 280, Short.MAX_VALUE)
                            .addComponent(lblExistencia)
                            .addComponent(lblNuevaExistencia)
                            .addComponent(txtNuevaExistencia)
                            .addComponent(lblMotivo)
                            .addComponent(cmbMotivo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDetalle)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHistorial)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(lblExistencia)
                        .addGap(15, 15, 15)
                        .addComponent(lblNuevaExistencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNuevaExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblMotivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblDetalle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnLimpiar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHistorial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnCerrar)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProductoActionPerformed
        cargarExistencia();
    }//GEN-LAST:event_cmbProductoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (cmbProducto.getSelectedIndex() <= 0
                || cmbMotivo.getSelectedIndex() <= 0
                || txtNuevaExistencia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona el producto, escribe la existencia y el motivo");
            return;
        }

        int nuevaExistencia;
        try {
            nuevaExistencia = Integer.parseInt(txtNuevaExistencia.getText().trim());
            if (nuevaExistencia < 0) {
                JOptionPane.showMessageDialog(this,
                        "La existencia no puede ser negativa");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "La existencia debe ser un número entero");
            return;
        }

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sqlProducto = "SELECT id_producto, existencia FROM productos "
                    + "WHERE codigo = ?";
            PreparedStatement buscar = conexion.prepareStatement(sqlProducto);
            buscar.setString(1, obtenerCodigo());
            ResultSet resultado = buscar.executeQuery();

            if (resultado.next() == false) {
                resultado.close();
                buscar.close();
                Conexion.cerrar(conexion);
                JOptionPane.showMessageDialog(this, "El producto ya no existe");
                cargarProductos();
                return;
            }

            int idProducto = resultado.getInt("id_producto");
            int existenciaActual = resultado.getInt("existencia");
            resultado.close();
            buscar.close();

            if (nuevaExistencia == existenciaActual) {
                Conexion.cerrar(conexion);
                JOptionPane.showMessageDialog(this,
                        "La existencia nueva es igual a la actual");
                return;
            }

            String tipo = nuevaExistencia > existenciaActual ? "Entrada" : "Salida";
            int diferencia = Math.abs(nuevaExistencia - existenciaActual);
            String observacion = "Ajuste de inventario - "
                    + cmbMotivo.getSelectedItem().toString()
                    + ". Antes: " + existenciaActual + ", después: " + nuevaExistencia;
            String detalle = txtDetalle.getText().trim();
            if (detalle.isEmpty() == false) {
                observacion += ". " + detalle;
            }
            if (observacion.length() > 200) {
                Conexion.cerrar(conexion);
                JOptionPane.showMessageDialog(this,
                        "El detalle del ajuste es demasiado largo");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(this,
                    "La existencia cambiará de " + existenciaActual + " a "
                    + nuevaExistencia + ". ¿Deseas guardar el ajuste?",
                    "Confirmar ajuste", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                Conexion.cerrar(conexion);
                return;
            }

            conexion.setAutoCommit(false);

            String sqlActualizar = "UPDATE productos SET existencia = ? "
                    + "WHERE id_producto = ?";
            PreparedStatement actualizar = conexion.prepareStatement(sqlActualizar);
            actualizar.setInt(1, nuevaExistencia);
            actualizar.setInt(2, idProducto);
            actualizar.executeUpdate();

            String sqlMovimiento = "INSERT INTO movimientos(id_producto, id_user, "
                    + "tipo, cantidad, observacion) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement movimiento = conexion.prepareStatement(sqlMovimiento);
            movimiento.setInt(1, idProducto);
            movimiento.setInt(2, CaVentory.idUsuarioActual);
            movimiento.setString(3, tipo);
            movimiento.setInt(4, diferencia);
            movimiento.setString(5, observacion);
            movimiento.executeUpdate();

            actualizar.close();
            movimiento.close();
            conexion.commit();
            Conexion.cerrar(conexion);

            cargarAjustes();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Ajuste guardado");
        } catch (SQLException e) {
            try {
                if (conexion.getAutoCommit() == false) {
                    conexion.rollback();
                }
            } catch (SQLException error) {
                System.err.println(error.toString());
            }
            JOptionPane.showMessageDialog(this, "No se pudo guardar el ajuste");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbMotivo;
    private javax.swing.JComboBox<String> cmbProducto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDetalle;
    private javax.swing.JLabel lblExistencia;
    private javax.swing.JLabel lblHistorial;
    private javax.swing.JLabel lblMotivo;
    private javax.swing.JLabel lblNuevaExistencia;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tablaAjustes;
    private javax.swing.JTextArea txtDetalle;
    private javax.swing.JTextField txtNuevaExistencia;
    // End of variables declaration//GEN-END:variables
}
