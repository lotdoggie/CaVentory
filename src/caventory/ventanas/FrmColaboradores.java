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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pablo
 */
public class FrmColaboradores extends javax.swing.JFrame {

    public FrmColaboradores() {
        initComponents();
        setLocationRelativeTo(null);
        cargarDatos();
    }

    private String obtenerContrasena() {
        return new String(txtContrasena.getPassword());
    }

    private void cargarDatos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaColaboradores.getModel();
        modelo.setRowCount(0);

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT id_user, nombre, usuario, rol, activo "
                    + "FROM usuarios ORDER BY id_user";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                modelo.addRow(new Object[]{
                    resultado.getInt("id_user"),
                    resultado.getString("nombre"),
                    resultado.getString("usuario"),
                    resultado.getString("rol"),
                    resultado.getBoolean("activo")
                });
            }

            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los colaboradores");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contrasena = obtenerContrasena();

        if (nombre.isEmpty() || usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre y el usuario");
            return false;
        }
        if (nombre.length() > 100) {
            JOptionPane.showMessageDialog(this,
                    "El nombre no puede tener más de 100 caracteres");
            return false;
        }
        if (usuario.length() > 50 || usuario.contains(" ")) {
            JOptionPane.showMessageDialog(this,
                    "El usuario no puede tener espacios ni más de 50 caracteres");
            return false;
        }
        if (contrasena.length() > 100) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña no puede tener más de 100 caracteres");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
        cmbRol.setSelectedIndex(0);
        chkActivo.setSelected(true);
        tablaColaboradores.clearSelection();
        txtNombre.requestFocus();
    }

    private void seleccionarColaborador() {
        int fila = tablaColaboradores.getSelectedRow();
        if (fila < 0) {
            return;
        }
        txtId.setText(tablaColaboradores.getValueAt(fila, 0).toString());
        txtNombre.setText(tablaColaboradores.getValueAt(fila, 1).toString());
        txtUsuario.setText(tablaColaboradores.getValueAt(fila, 2).toString());
        txtContrasena.setText("");
        cmbRol.setSelectedItem(tablaColaboradores.getValueAt(fila, 3).toString());
        chkActivo.setSelected((Boolean) tablaColaboradores.getValueAt(fila, 4));
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
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        lblRol = new javax.swing.JLabel();
        cmbRol = new javax.swing.JComboBox<>();
        chkActivo = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaColaboradores = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Colaboradores");
        setResizable(false);
        setBackground(new java.awt.Color(246, 248, 246));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(35, 82, 60));
        lblTitulo.setText("Colaboradores");

        lblAviso.setForeground(new java.awt.Color(90, 90, 90));
        lblAviso.setText("Administración de usuarios");

        lblId.setText("ID");

        txtId.setEditable(false);

        lblNombre.setText("Nombre completo");

        lblUsuario.setText("Usuario");

        lblContrasena.setText("Contraseña");

        lblRol.setText("Rol");

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Colaborador", "Administrador" }));

        chkActivo.setSelected(true);
        chkActivo.setText("Usuario activo");

        btnGuardar.setText("Guardar");
        btnGuardar.setBackground(new java.awt.Color(47, 107, 79));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFocusPainted(false);
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
        btnEliminar.setBackground(new java.awt.Color(176, 64, 64));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setFocusPainted(false);
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

        lblBuscar.setText("Buscar por nombre o usuario");

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(47, 107, 79));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnTodos.setText("Todos");
        btnTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodosActionPerformed(evt);
            }
        });

        tablaColaboradores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Usuario", "Rol", "Activo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaColaboradores.setRowHeight(24);
        tablaColaboradores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaColaboradores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaColaboradoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaColaboradores);

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
                            .addComponent(lblUsuario)
                            .addComponent(txtUsuario)
                            .addComponent(lblContrasena)
                            .addComponent(txtContrasena)
                            .addComponent(lblRol)
                            .addComponent(cmbRol, 0, 280, Short.MAX_VALUE)
                            .addComponent(chkActivo)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBuscar)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblContrasena)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblRol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
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
                        .addComponent(lblBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar)
                            .addComponent(btnTodos))
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        String contrasena = obtenerContrasena();
        if (contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe una contraseña");
            return;
        }

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "INSERT INTO usuarios(nombre, usuario, contrasena, rol, activo) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, txtNombre.getText().trim());
            consulta.setString(2, txtUsuario.getText().trim());
            consulta.setString(3, contrasena);
            consulta.setString(4, cmbRol.getSelectedItem().toString());
            consulta.setBoolean(5, chkActivo.isSelected());
            consulta.executeUpdate();

            consulta.close();
            Conexion.cerrar(conexion);
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Colaborador guardado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el colaborador");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = tablaColaboradores.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un colaborador");
            return;
        }
        if (validarCampos() == false) {
            return;
        }
        String contrasena = obtenerContrasena();

        int idUsuario = Integer.parseInt(txtId.getText());
        if (idUsuario == CaVentory.idUsuarioActual
                && (chkActivo.isSelected() == false
                || cmbRol.getSelectedItem().toString().equals("Administrador") == false)) {
            JOptionPane.showMessageDialog(this,
                    "No puedes desactivar ni cambiar el rol de tu propio usuario");
            return;
        }

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql;
            if (contrasena.isEmpty()) {
                sql = "UPDATE usuarios SET nombre = ?, usuario = ?, rol = ?, activo = ? "
                        + "WHERE id_user = ?";
            } else {
                sql = "UPDATE usuarios SET nombre = ?, usuario = ?, contrasena = ?, "
                        + "rol = ?, activo = ? WHERE id_user = ?";
            }
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, txtNombre.getText().trim());
            consulta.setString(2, txtUsuario.getText().trim());
            if (contrasena.isEmpty()) {
                consulta.setString(3, cmbRol.getSelectedItem().toString());
                consulta.setBoolean(4, chkActivo.isSelected());
                consulta.setInt(5, idUsuario);
            } else {
                consulta.setString(3, contrasena);
                consulta.setString(4, cmbRol.getSelectedItem().toString());
                consulta.setBoolean(5, chkActivo.isSelected());
                consulta.setInt(6, idUsuario);
            }
            consulta.executeUpdate();

            consulta.close();
            Conexion.cerrar(conexion);
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Colaborador editado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo editar el colaborador");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tablaColaboradores.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un colaborador");
            return;
        }
        if (Integer.parseInt(txtId.getText()) == CaVentory.idUsuarioActual) {
            JOptionPane.showMessageDialog(this, "No puedes eliminar tu propio usuario");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Deseas eliminar el colaborador seleccionado?", "Eliminar",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            Connection conexion = Conexion.conectar();
            if (conexion == null) {
                return;
            }
            try {
                String sql = "DELETE FROM usuarios WHERE id_user = ?";
                PreparedStatement consulta = conexion.prepareStatement(sql);
                consulta.setInt(1, Integer.parseInt(txtId.getText()));
                consulta.executeUpdate();

                consulta.close();
                Conexion.cerrar(conexion);
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Colaborador eliminado");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el colaborador");
                System.err.println(e.toString());
                Conexion.cerrar(conexion);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void buscarColaborador() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un nombre o usuario");
            return;
        }

        for (int fila = 0; fila < tablaColaboradores.getRowCount(); fila++) {
            String nombre = tablaColaboradores.getValueAt(fila, 1).toString().toLowerCase();
            String usuario = tablaColaboradores.getValueAt(fila, 2).toString().toLowerCase();
            if (nombre.contains(texto) || usuario.contains(texto)) {
                tablaColaboradores.setRowSelectionInterval(fila, fila);
                seleccionarColaborador();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No se encontró el colaborador");
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarColaborador();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        buscarColaborador();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodosActionPerformed
        txtBuscar.setText("");
        cargarDatos();
        limpiarCampos();
    }//GEN-LAST:event_btnTodosActionPerformed

    private void tablaColaboradoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaColaboradoresMouseClicked
        seleccionarColaborador();
    }//GEN-LAST:event_tablaColaboradoresMouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnTodos;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tablaColaboradores;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
