package caventory.ventanas;

import caventory.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmProveedores extends javax.swing.JFrame {

    public FrmProveedores() {
        initComponents();
        setLocationRelativeTo(null);
        cargarDatos();
    }

    private void cargarDatos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaProveedores.getModel();
        modelo.setRowCount(0);

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT * FROM proveedores ORDER BY id_proveedor";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                String telefono = resultado.getString("telefono");
                String correo = resultado.getString("correo");
                String direccion = resultado.getString("direccion");

                if (telefono == null) {
                    telefono = "";
                }
                if (correo == null) {
                    correo = "";
                }
                if (direccion == null) {
                    direccion = "";
                }

                modelo.addRow(new Object[]{
                    resultado.getInt("id_proveedor"),
                    resultado.getString("nombre"),
                    telefono,
                    correo,
                    direccion,
                    resultado.getBoolean("activo")
                });
            }

            resultado.close();
            consulta.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los proveedores");
            System.err.println(e.toString());
        }
    }

    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre del proveedor");
            return false;
        }
        if (correo.isEmpty() == false && correo.contains("@") == false) {
            JOptionPane.showMessageDialog(this, "Escribe un correo valido");
            return false;
        }
        if (txtDireccion.getText().trim().length() > 200) {
            JOptionPane.showMessageDialog(this,
                    "La direccion no puede tener mas de 200 caracteres");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        chkActivo.setSelected(true);
        tablaProveedores.clearSelection();
        txtNombre.requestFocus();
    }

    private void seleccionarProveedor() {
        int fila = tablaProveedores.getSelectedRow();
        if (fila < 0) {
            return;
        }
        txtId.setText(tablaProveedores.getValueAt(fila, 0).toString());
        txtNombre.setText(tablaProveedores.getValueAt(fila, 1).toString());
        txtTelefono.setText(tablaProveedores.getValueAt(fila, 2).toString());
        txtCorreo.setText(tablaProveedores.getValueAt(fila, 3).toString());
        txtDireccion.setText(tablaProveedores.getValueAt(fila, 4).toString());
        chkActivo.setSelected((Boolean) tablaProveedores.getValueAt(fila, 5));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblAviso = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDireccion = new javax.swing.JTextArea();
        chkActivo = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Proveedores");
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitulo.setText("Proveedores");

        lblAviso.setText("Modulo del administrador");

        lblId.setText("ID");

        txtId.setEditable(false);

        lblNombre.setText("Nombre");

        lblTelefono.setText("Telefono");

        lblCorreo.setText("Correo");

        lblDireccion.setText("Direccion");

        txtDireccion.setColumns(20);
        txtDireccion.setLineWrap(true);
        txtDireccion.setRows(5);
        txtDireccion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDireccion);

        chkActivo.setSelected(true);
        chkActivo.setText("Proveedor activo");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Telefono", "Correo", "Direccion", "Activo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProveedoresMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaProveedores);

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
                        .addComponent(lblAviso))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblId)
                            .addComponent(txtId)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre)
                            .addComponent(lblTelefono)
                            .addComponent(txtTelefono)
                            .addComponent(lblCorreo)
                            .addComponent(txtCorreo)
                            .addComponent(lblDireccion)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addComponent(chkActivo)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblAviso))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblTelefono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblCorreo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblDireccion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(chkActivo)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnEditar))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminar)
                            .addComponent(btnLimpiar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnCerrar)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos() == false) {
            return;
        }

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "INSERT INTO proveedores(nombre, telefono, correo, direccion, activo) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, txtNombre.getText().trim());
            consulta.setString(2, txtTelefono.getText().trim());
            consulta.setString(3, txtCorreo.getText().trim());
            consulta.setString(4, txtDireccion.getText().trim());
            consulta.setBoolean(5, chkActivo.isSelected());
            consulta.executeUpdate();

            consulta.close();
            conexion.close();
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Proveedor guardado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el proveedor");
            System.err.println(e.toString());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (tablaProveedores.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor");
            return;
        }
        if (validarCampos() == false) {
            return;
        }

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "UPDATE proveedores SET nombre = ?, telefono = ?, correo = ?, "
                    + "direccion = ?, activo = ? WHERE id_proveedor = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, txtNombre.getText().trim());
            consulta.setString(2, txtTelefono.getText().trim());
            consulta.setString(3, txtCorreo.getText().trim());
            consulta.setString(4, txtDireccion.getText().trim());
            consulta.setBoolean(5, chkActivo.isSelected());
            consulta.setInt(6, Integer.parseInt(txtId.getText()));
            consulta.executeUpdate();

            consulta.close();
            conexion.close();
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Proveedor editado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo editar el proveedor");
            System.err.println(e.toString());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (tablaProveedores.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,
                "Deseas eliminar el proveedor seleccionado?", "Eliminar",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            Connection conexion = Conexion.conectar();
            if (conexion == null) {
                return;
            }
            try {
                String sql = "DELETE FROM proveedores WHERE id_proveedor = ?";
                PreparedStatement consulta = conexion.prepareStatement(sql);
                consulta.setInt(1, Integer.parseInt(txtId.getText()));
                consulta.executeUpdate();

                consulta.close();
                conexion.close();
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Proveedor eliminado");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar un proveedor que tenga productos");
                System.err.println(e.toString());
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tablaProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProveedoresMouseClicked
        seleccionarProveedor();
    }//GEN-LAST:event_tablaProveedoresMouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tablaProveedores;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextArea txtDireccion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
