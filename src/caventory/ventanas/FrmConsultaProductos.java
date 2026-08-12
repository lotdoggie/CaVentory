package caventory.ventanas;

import caventory.CaVentory;
import caventory.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmConsultaProductos extends javax.swing.JFrame {

    public FrmConsultaProductos() {
        initComponents();
        setLocationRelativeTo(null);
        lblUsuario.setText("Usuario: " + CaVentory.usuarioActual);
        cargarDatos();
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
                    + "ON p.id_proveedor = pr.id_proveedor ORDER BY p.nombre";

            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            int total = 0;
            int productosBajos = 0;

            while (resultado.next()) {
                int existencia = resultado.getInt("existencia");
                int minimo = resultado.getInt("stock_minimo");
                String estado = "Disponible";

                if (existencia <= minimo) {
                    estado = "Existencia baja";
                    productosBajos++;
                }

                modelo.addRow(new Object[]{
                    resultado.getInt("id_producto"),
                    resultado.getString("codigo"),
                    resultado.getString("nombre"),
                    resultado.getString("categoria"),
                    resultado.getString("proveedor"),
                    resultado.getDouble("precio"),
                    existencia,
                    minimo,
                    estado
                });
                total++;
            }

            lblResumen.setText("Productos mostrados: " + total
                    + " | Con existencia baja: " + productosBajos);
            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo consultar el inventario");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        lblResumen = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Consulta de inventario");
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitulo.setText("Consulta de inventario");

        lblUsuario.setText("Usuario:");

        lblBuscar.setText("Buscar por código o nombre");

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

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Nombre", "Categoría", "Proveedor", "Precio", "Existencia", "Mínimo", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

        lblResumen.setText("Productos mostrados: 0 | Con existencia baja: 0");

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBuscar)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(18, 18, 18)
                .addComponent(lblBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnTodos))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResumen)
                    .addComponent(btnCerrar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "No se encontró el producto");
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodosActionPerformed
        txtBuscar.setText("");
        tablaProductos.clearSelection();
        cargarDatos();
    }//GEN-LAST:event_btnTodosActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnTodos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblResumen;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
