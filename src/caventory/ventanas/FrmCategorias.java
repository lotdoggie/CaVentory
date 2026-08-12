/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package caventory.ventanas;

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
public class FrmCategorias extends javax.swing.JFrame {

    public FrmCategorias() {
        initComponents();
        setLocationRelativeTo(null);
        cargarDatos();
    }

    private void cargarDatos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaCategorias.getModel();
        modelo.setRowCount(0);

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT * FROM categorias ORDER BY id_categoria";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                String descripcion = resultado.getString("descripcion");
                if (descripcion == null) {
                    descripcion = "";
                }

                modelo.addRow(new Object[]{
                    resultado.getInt("id_categoria"),
                    resultado.getString("nombre"),
                    descripcion
                });
            }

            resultado.close();
            consulta.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar las categorias");
            System.err.println(e.toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        tablaCategorias.clearSelection();
        txtNombre.requestFocus();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()
                || txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre y la descripcion");
            return false;
        }
        if (txtDescripcion.getText().trim().length() > 200) {
            JOptionPane.showMessageDialog(this,
                    "La descripcion no puede tener mas de 200 caracteres");
            return false;
        }
        return true;
    }

    private void seleccionarCategoria() {
        int fila = tablaCategorias.getSelectedRow();
        if (fila < 0) {
            return;
        }
        txtId.setText(tablaCategorias.getValueAt(fila, 0).toString());
        txtNombre.setText(tablaCategorias.getValueAt(fila, 1).toString());
        txtDescripcion.setText(tablaCategorias.getValueAt(fila, 2).toString());
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
        lblDescripcion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCategorias = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Categorias");
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitulo.setText("Categorias");

        lblAviso.setText("Modulo del administrador");

        lblId.setText("ID");

        txtId.setEditable(false);

        lblNombre.setText("Nombre");

        lblDescripcion.setText("Descripcion");

        txtDescripcion.setColumns(20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDescripcion);

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

        tablaCategorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Descripcion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCategorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCategoriasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaCategorias);

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
                            .addComponent(lblDescripcion)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(15, 15, 15)
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnEditar))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminar)
                            .addComponent(btnLimpiar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            String sql = "INSERT INTO categorias(nombre, descripcion) VALUES (?, ?)";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, txtNombre.getText().trim());
            consulta.setString(2, txtDescripcion.getText().trim());
            consulta.executeUpdate();

            consulta.close();
            conexion.close();
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Categoria guardada");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la categoria");
            System.err.println(e.toString());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = tablaCategorias.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una categoria");
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
            String sql = "UPDATE categorias SET nombre = ?, descripcion = ? "
                    + "WHERE id_categoria = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, txtNombre.getText().trim());
            consulta.setString(2, txtDescripcion.getText().trim());
            consulta.setInt(3, Integer.parseInt(txtId.getText()));
            consulta.executeUpdate();

            consulta.close();
            conexion.close();
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Categoria editada");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo editar la categoria");
            System.err.println(e.toString());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tablaCategorias.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una categoria");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,
                "Deseas eliminar la categoria seleccionada?", "Eliminar",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            Connection conexion = Conexion.conectar();
            if (conexion == null) {
                return;
            }
            try {
                String sql = "DELETE FROM categorias WHERE id_categoria = ?";
                PreparedStatement consulta = conexion.prepareStatement(sql);
                consulta.setInt(1, Integer.parseInt(txtId.getText()));
                consulta.executeUpdate();

                consulta.close();
                conexion.close();
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Categoria eliminada");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar una categoria que tenga productos");
                System.err.println(e.toString());
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tablaCategoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCategoriasMouseClicked
        seleccionarCategoria();
    }//GEN-LAST:event_tablaCategoriasMouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tablaCategorias;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
