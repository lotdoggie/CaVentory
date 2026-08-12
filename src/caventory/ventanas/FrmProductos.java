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
public class FrmProductos extends javax.swing.JFrame {

    public FrmProductos() {
        initComponents();
        setLocationRelativeTo(null);
        lblRol.setText("Rol: " + CaVentory.rolActual);
        cargarCategorias();
        cargarProveedores();
        cargarDatos();
    }

    private void cargarProveedores() {
        cmbProveedor.removeAllItems();
        cmbProveedor.addItem("Seleccione");

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT nombre FROM proveedores WHERE activo = true ORDER BY nombre";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                cmbProveedor.addItem(resultado.getString("nombre"));
            }

            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los proveedores");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    private void cargarCategorias() {
        cmbCategoria.removeAllItems();
        cmbCategoria.addItem("Seleccione");

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT nombre FROM categorias ORDER BY nombre";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                cmbCategoria.addItem(resultado.getString("nombre"));
            }

            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar las categorías");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    private void cargarDatos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0);

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            String sql = "SELECT p.id_producto, p.codigo, p.nombre, c.nombre AS categoria, "
                    + "pr.nombre AS proveedor, p.precio, p.existencia, p.stock_minimo "
                    + "FROM productos p INNER JOIN categorias c "
                    + "ON p.id_categoria = c.id_categoria INNER JOIN proveedores pr "
                    + "ON p.id_proveedor = pr.id_proveedor ORDER BY p.id_producto";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            int productosBajos = 0;

            while (resultado.next()) {
                int existencia = resultado.getInt("existencia");
                int minimo = resultado.getInt("stock_minimo");
                modelo.addRow(new Object[]{
                    resultado.getInt("id_producto"),
                    resultado.getString("codigo"),
                    resultado.getString("nombre"),
                    resultado.getString("categoria"),
                    resultado.getString("proveedor"),
                    resultado.getDouble("precio"),
                    existencia,
                    minimo
                });
                if (existencia <= minimo) {
                    productosBajos++;
                }
            }

            lblRol.setText("Rol: " + CaVentory.rolActual
                    + " | Productos con existencia baja: " + productosBajos);

            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los productos");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()
                || txtPrecio.getText().trim().isEmpty() || txtExistencia.getText().trim().isEmpty()
                || txtMinimo.getText().trim().isEmpty()
                || cmbCategoria.getSelectedIndex() == 0
                || cmbProveedor.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Completa todos los datos del producto");
            return false;
        }

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            int existencia = Integer.parseInt(txtExistencia.getText());
            int minimo = Integer.parseInt(txtMinimo.getText());

            if (precio < 0 || existencia < 0 || minimo < 0) {
                JOptionPane.showMessageDialog(this,
                        "Precio, existencia y mínimo no pueden ser negativos");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio, existencia y mínimo deben ser números");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        cmbCategoria.setSelectedIndex(0);
        cmbProveedor.setSelectedIndex(0);
        txtPrecio.setText("");
        txtExistencia.setText("");
        txtMinimo.setText("");
        tablaProductos.clearSelection();
        txtExistencia.setEditable(true);
        txtCodigo.requestFocus();
    }

    private void seleccionarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            return;
        }

        txtId.setText(tablaProductos.getValueAt(fila, 0).toString());
        txtCodigo.setText(tablaProductos.getValueAt(fila, 1).toString());
        txtNombre.setText(tablaProductos.getValueAt(fila, 2).toString());
        cmbCategoria.setSelectedItem(tablaProductos.getValueAt(fila, 3).toString());
        cmbProveedor.setSelectedItem(tablaProductos.getValueAt(fila, 4).toString());
        txtPrecio.setText(tablaProductos.getValueAt(fila, 5).toString());
        txtExistencia.setText(tablaProductos.getValueAt(fila, 6).toString());
        txtMinimo.setText(tablaProductos.getValueAt(fila, 7).toString());
        txtExistencia.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCategoria = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<>();
        lblProveedor = new javax.swing.JLabel();
        cmbProveedor = new javax.swing.JComboBox<>();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblExistencia = new javax.swing.JLabel();
        txtExistencia = new javax.swing.JTextField();
        lblMinimo = new javax.swing.JLabel();
        txtMinimo = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Productos");
        setResizable(false);
        setBackground(new java.awt.Color(245, 247, 250));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(31, 78, 121));
        lblTitulo.setText("Productos");

        lblRol.setText("Rol:");

        lblId.setText("ID");

        txtId.setEditable(false);

        lblCodigo.setText("Código");

        lblNombre.setText("Nombre");

        lblCategoria.setText("Categoría");

        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));

        lblProveedor.setText("Proveedor");

        cmbProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));

        lblPrecio.setText("Precio");

        lblExistencia.setText("Existencia");

        lblMinimo.setText("Stock mínimo");

        btnGuardar.setText("Guardar");
        btnGuardar.setBackground(new java.awt.Color(47, 111, 163));
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

        lblBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBuscar.setText("Buscar por código o nombre");

        btnBuscar.setText("Buscar");
        btnBuscar.setBackground(new java.awt.Color(47, 111, 163));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setFocusPainted(false);
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

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Nombre", "Categoría", "Proveedor", "Precio", "Existencia", "Mínimo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductos.setRowHeight(24);
        tablaProductos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

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
                        .addComponent(lblRol))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblId)
                            .addComponent(txtId)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre)
                            .addComponent(lblCategoria)
                            .addComponent(cmbCategoria, 0, 260, Short.MAX_VALUE)
                            .addComponent(lblProveedor)
                            .addComponent(cmbProveedor, 0, 260, Short.MAX_VALUE)
                            .addComponent(lblPrecio)
                            .addComponent(txtPrecio)
                            .addComponent(lblExistencia)
                            .addComponent(txtExistencia)
                            .addComponent(lblMinimo)
                            .addComponent(txtMinimo)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBuscar)
                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btnTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblRol))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblCategoria)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblProveedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblPrecio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblExistencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblMinimo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            int existencia = Integer.parseInt(txtExistencia.getText());

            String sql = "INSERT INTO productos(codigo, nombre, id_categoria, id_proveedor, "
                    + "precio, existencia, stock_minimo) VALUES (?, ?, "
                    + "(SELECT id_categoria FROM categorias WHERE nombre = ?), "
                    + "(SELECT id_proveedor FROM proveedores WHERE nombre = ?), ?, ?, ?)";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, txtCodigo.getText().trim());
            consulta.setString(2, txtNombre.getText().trim());
            consulta.setString(3, cmbCategoria.getSelectedItem().toString());
            consulta.setString(4, cmbProveedor.getSelectedItem().toString());
            consulta.setDouble(5, Double.parseDouble(txtPrecio.getText()));
            consulta.setInt(6, existencia);
            consulta.setInt(7, Integer.parseInt(txtMinimo.getText()));
            consulta.executeUpdate();
            consulta.close();

            if (existencia > 0) {
                String sqlBuscar = "SELECT id_producto FROM productos WHERE codigo = ?";
                PreparedStatement buscar = conexion.prepareStatement(sqlBuscar);
                buscar.setString(1, txtCodigo.getText().trim());
                ResultSet resultado = buscar.executeQuery();
                resultado.next();
                int idProducto = resultado.getInt("id_producto");
                resultado.close();
                buscar.close();

                String sqlMovimiento = "INSERT INTO movimientos(id_producto, id_user, tipo, "
                        + "cantidad, observacion) VALUES (?, ?, 'Entrada', ?, ?)";
                PreparedStatement movimiento = conexion.prepareStatement(sqlMovimiento);
                movimiento.setInt(1, idProducto);
                movimiento.setInt(2, CaVentory.idUsuarioActual);
                movimiento.setInt(3, existencia);
                movimiento.setString(4, "Existencia inicial");
                movimiento.executeUpdate();
                movimiento.close();
            }

            Conexion.cerrar(conexion);
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto guardado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el producto");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla");
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
            String sql = "UPDATE productos SET codigo = ?, nombre = ?, id_categoria = "
                    + "(SELECT id_categoria FROM categorias WHERE nombre = ?), id_proveedor = "
                    + "(SELECT id_proveedor FROM proveedores WHERE nombre = ?), precio = ?, "
                    + "stock_minimo = ? WHERE id_producto = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, txtCodigo.getText().trim());
            consulta.setString(2, txtNombre.getText().trim());
            consulta.setString(3, cmbCategoria.getSelectedItem().toString());
            consulta.setString(4, cmbProveedor.getSelectedItem().toString());
            consulta.setDouble(5, Double.parseDouble(txtPrecio.getText()));
            consulta.setInt(6, Integer.parseInt(txtMinimo.getText()));
            consulta.setInt(7, Integer.parseInt(txtId.getText()));
            consulta.executeUpdate();

            consulta.close();
            Conexion.cerrar(conexion);
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto editado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo editar el producto");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Deseas eliminar el producto seleccionado?", "Eliminar",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            Connection conexion = Conexion.conectar();
            if (conexion == null) {
                return;
            }
            try {
                String sql = "DELETE FROM productos WHERE id_producto = ?";
                PreparedStatement consulta = conexion.prepareStatement(sql);
                consulta.setInt(1, Integer.parseInt(txtId.getText()));
                consulta.executeUpdate();

                consulta.close();
                Conexion.cerrar(conexion);
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Producto eliminado");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar un producto que tenga movimientos");
                System.err.println(e.toString());
                Conexion.cerrar(conexion);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un código o nombre");
            return;
        }

        for (int fila = 0; fila < tablaProductos.getRowCount(); fila++) {
            String codigo = tablaProductos.getValueAt(fila, 1).toString().toLowerCase();
            String nombre = tablaProductos.getValueAt(fila, 2).toString().toLowerCase();
            if (codigo.contains(texto) || nombre.contains(texto)) {
                tablaProductos.setRowSelectionInterval(fila, fila);
                seleccionarProducto();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No se encontró el producto");
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodosActionPerformed
        txtBuscar.setText("");
        cargarDatos();
        limpiarCampos();
    }//GEN-LAST:event_btnTodosActionPerformed

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked
        seleccionarProducto();
    }//GEN-LAST:event_tablaProductosMouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnTodos;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbProveedor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblExistencia;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblMinimo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtExistencia;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMinimo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
