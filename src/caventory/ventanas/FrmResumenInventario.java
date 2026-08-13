package caventory.ventanas;

import caventory.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmResumenInventario extends javax.swing.JFrame {

    public FrmResumenInventario() {
        initComponents();
        setLocationRelativeTo(null);
        cargarResumen();
    }

    private void cargarResumen() {
        DefaultTableModel modelo = (DefaultTableModel) tablaExistenciaBaja.getModel();
        modelo.setRowCount(0);

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            return;
        }
        try {
            int productos = 0;
            int unidades = 0;
            int productosBajos = 0;
            double valor = 0;

            String sqlProductos = "SELECT precio, existencia, stock_minimo FROM productos";
            PreparedStatement consultaProductos = conexion.prepareStatement(sqlProductos);
            ResultSet resultadoProductos = consultaProductos.executeQuery();

            while (resultadoProductos.next()) {
                int existencia = resultadoProductos.getInt("existencia");
                int minimo = resultadoProductos.getInt("stock_minimo");

                productos++;
                unidades = unidades + existencia;
                valor = valor + resultadoProductos.getDouble("precio") * existencia;

                if (existencia <= minimo) {
                    productosBajos++;
                }
            }
            resultadoProductos.close();
            consultaProductos.close();

            lblProductos.setText("Productos registrados: " + productos);
            lblUnidades.setText("Unidades en inventario: " + unidades);
            lblValor.setText("Valor aproximado: $" + String.format("%.2f", valor));
            lblBajos.setText("Productos con existencia baja: " + productosBajos);

            int categorias = 0;
            int proveedores = 0;
            int colaboradores = 0;

            String sqlCategorias = "SELECT COUNT(*) AS total FROM categorias";
            PreparedStatement consultaCategorias = conexion.prepareStatement(sqlCategorias);
            ResultSet resultadoCategorias = consultaCategorias.executeQuery();
            if (resultadoCategorias.next()) {
                categorias = resultadoCategorias.getInt("total");
            }
            resultadoCategorias.close();
            consultaCategorias.close();

            String sqlProveedores = "SELECT COUNT(*) AS total FROM proveedores WHERE activo = true";
            PreparedStatement consultaProveedores = conexion.prepareStatement(sqlProveedores);
            ResultSet resultadoProveedores = consultaProveedores.executeQuery();
            if (resultadoProveedores.next()) {
                proveedores = resultadoProveedores.getInt("total");
            }
            resultadoProveedores.close();
            consultaProveedores.close();

            String sqlColaboradores = "SELECT COUNT(*) AS total FROM usuarios "
                    + "WHERE rol = 'Colaborador' AND activo = true";
            PreparedStatement consultaColaboradores = conexion.prepareStatement(sqlColaboradores);
            ResultSet resultadoColaboradores = consultaColaboradores.executeQuery();
            if (resultadoColaboradores.next()) {
                colaboradores = resultadoColaboradores.getInt("total");
            }
            resultadoColaboradores.close();
            consultaColaboradores.close();

            lblCatalogos.setText("Categorías: " + categorias
                    + " | Proveedores activos: " + proveedores
                    + " | Colaboradores activos: " + colaboradores);

            String sqlBajos = "SELECT p.codigo, p.nombre, c.nombre AS categoria, "
                    + "pr.nombre AS proveedor, p.existencia, p.stock_minimo "
                    + "FROM productos p INNER JOIN categorias c "
                    + "ON p.id_categoria = c.id_categoria INNER JOIN proveedores pr "
                    + "ON p.id_proveedor = pr.id_proveedor "
                    + "WHERE p.existencia <= p.stock_minimo ORDER BY p.existencia";
            PreparedStatement consultaBajos = conexion.prepareStatement(sqlBajos);
            ResultSet bajos = consultaBajos.executeQuery();

            while (bajos.next()) {
                modelo.addRow(new Object[]{
                    bajos.getString("codigo"),
                    bajos.getString("nombre"),
                    bajos.getString("categoria"),
                    bajos.getString("proveedor"),
                    bajos.getInt("existencia"),
                    bajos.getInt("stock_minimo")
                });
            }

            bajos.close();
            consultaBajos.close();
            Conexion.cerrar(conexion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el resumen");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblAviso = new javax.swing.JLabel();
        lblProductos = new javax.swing.JLabel();
        lblUnidades = new javax.swing.JLabel();
        lblValor = new javax.swing.JLabel();
        lblBajos = new javax.swing.JLabel();
        lblCatalogos = new javax.swing.JLabel();
        lblDetalle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaExistenciaBaja = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Resumen de inventario");
        setResizable(false);
        setBackground(new java.awt.Color(246, 248, 246));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(35, 82, 60));
        lblTitulo.setText("Resumen de inventario");

        lblAviso.setText("Información general del sistema");

        lblProductos.setText("Productos registrados: 0");

        lblUnidades.setText("Unidades en inventario: 0");

        lblValor.setText("Valor aproximado: $0.00");

        lblBajos.setText("Productos con existencia baja: 0");
        lblBajos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBajos.setForeground(new java.awt.Color(210, 140, 40));

        lblCatalogos.setText("Categorías: 0 | Proveedores activos: 0 | Colaboradores activos: 0");

        lblDetalle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDetalle.setText("Productos que necesitan revisión o reabastecimiento");

        tablaExistenciaBaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Producto", "Categoría", "Proveedor", "Existencia", "Mínimo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaExistenciaBaja.setRowHeight(24);
        tablaExistenciaBaja.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tablaExistenciaBaja);

        btnActualizar.setText("Actualizar");
        btnActualizar.setBackground(new java.awt.Color(47, 107, 79));
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
                        .addComponent(lblAviso))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProductos)
                            .addComponent(lblUnidades))
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblValor)
                            .addComponent(lblBajos)))
                    .addComponent(lblCatalogos)
                    .addComponent(lblDetalle)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductos)
                    .addComponent(lblValor))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUnidades)
                    .addComponent(lblBajos))
                .addGap(15, 15, 15)
                .addComponent(lblCatalogos)
                .addGap(22, 22, 22)
                .addComponent(lblDetalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnCerrar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarResumen();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblBajos;
    private javax.swing.JLabel lblCatalogos;
    private javax.swing.JLabel lblDetalle;
    private javax.swing.JLabel lblProductos;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUnidades;
    private javax.swing.JLabel lblValor;
    private javax.swing.JTable tablaExistenciaBaja;
    // End of variables declaration//GEN-END:variables
}
