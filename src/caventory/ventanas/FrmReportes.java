package caventory.ventanas;

import caventory.CaVentory;
import caventory.Conexion;
import caventory.GeneradorPDF;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmReportes extends javax.swing.JFrame {

    private String tituloActual = "Resumen general";

    public FrmReportes() {
        initComponents();
        setLocationRelativeTo(null);
        lblUsuario.setText("Usuario: " + CaVentory.usuarioActual);
        cargarDatos();
    }

    private boolean cargarDatos() {
        String reporte = cmbReporte.getSelectedItem().toString();
        String periodo = cmbPeriodo.getSelectedItem().toString();
        String[] columnas;
        String sql;

        cmbPeriodo.setEnabled(reporte.equals("Movimientos"));

        if (reporte.equals("Resumen general")) {
            columnas = new String[]{"Concepto", "Total"};
            sql = "SELECT concepto, total FROM ("
                    + "SELECT 1 AS orden, 'Productos registrados' AS concepto, COUNT(*)::numeric AS total FROM productos "
                    + "UNION ALL SELECT 2, 'Unidades en inventario', COALESCE(SUM(existencia), 0)::numeric FROM productos "
                    + "UNION ALL SELECT 3, 'Valor del inventario', COALESCE(SUM(precio * existencia), 0)::numeric FROM productos "
                    + "UNION ALL SELECT 4, 'Productos con existencia baja', COUNT(*)::numeric FROM productos WHERE existencia <= stock_minimo "
                    + "UNION ALL SELECT 5, 'Movimientos registrados', COUNT(*)::numeric FROM movimientos "
                    + "UNION ALL SELECT 6, 'Categorías registradas', COUNT(*)::numeric FROM categorias "
                    + "UNION ALL SELECT 7, 'Proveedores activos', COUNT(*)::numeric FROM proveedores WHERE activo = TRUE "
                    + "UNION ALL SELECT 8, 'Colaboradores activos', COUNT(*)::numeric FROM usuarios WHERE activo = TRUE"
                    + ") datos ORDER BY orden";
        } else if (reporte.equals("Inventario completo")) {
            columnas = new String[]{"Código", "Producto", "Categoría", "Proveedor",
                "Precio", "Existencia", "Mínimo", "Valor"};
            sql = "SELECT p.codigo, p.nombre AS producto, c.nombre AS categoria, "
                    + "pr.nombre AS proveedor, p.precio, p.existencia, p.stock_minimo, "
                    + "ROUND(p.precio * p.existencia, 2) AS valor "
                    + "FROM productos p INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
                    + "INNER JOIN proveedores pr ON p.id_proveedor = pr.id_proveedor "
                    + "ORDER BY p.nombre";
        } else if (reporte.equals("Existencia baja")) {
            columnas = new String[]{"Código", "Producto", "Existencia",
                "Mínimo", "Faltante", "Estado"};
            sql = "SELECT codigo, nombre AS producto, existencia, stock_minimo, "
                    + "GREATEST(stock_minimo - existencia, 0) AS faltante, "
                    + "CASE WHEN existencia = 0 THEN 'Agotado' ELSE 'Bajo' END AS estado "
                    + "FROM productos WHERE existencia <= stock_minimo ORDER BY nombre";
        } else if (reporte.equals("Movimientos")) {
            columnas = new String[]{"Fecha", "Producto", "Tipo",
                "Cantidad", "Usuario", "Observación"};
            String filtro = "";
            if (periodo.equals("Hoy")) {
                filtro = "WHERE m.fecha::date = CURRENT_DATE ";
            } else if (periodo.equals("Este mes")) {
                filtro = "WHERE DATE_TRUNC('month', m.fecha) = DATE_TRUNC('month', CURRENT_DATE) ";
            }
            sql = "SELECT TO_CHAR(m.fecha, 'DD/MM/YYYY HH24:MI') AS fecha, "
                    + "p.nombre AS producto, m.tipo, m.cantidad, u.usuario, "
                    + "COALESCE(m.observacion, '') AS observacion "
                    + "FROM movimientos m INNER JOIN productos p ON m.id_producto = p.id_producto "
                    + "INNER JOIN usuarios u ON m.id_user = u.id_user "
                    + filtro + "ORDER BY m.fecha DESC";
        } else if (reporte.equals("Categorías")) {
            columnas = new String[]{"Categoría", "Descripción", "Productos"};
            sql = "SELECT c.nombre AS categoria, COALESCE(c.descripcion, '') AS descripcion, "
                    + "COUNT(p.id_producto) AS productos FROM categorias c "
                    + "LEFT JOIN productos p ON c.id_categoria = p.id_categoria "
                    + "GROUP BY c.id_categoria, c.nombre, c.descripcion ORDER BY c.nombre";
        } else if (reporte.equals("Proveedores")) {
            columnas = new String[]{"Proveedor", "Teléfono", "Correo", "Estado", "Productos"};
            sql = "SELECT pr.nombre AS proveedor, COALESCE(pr.telefono, '') AS telefono, "
                    + "COALESCE(pr.correo, '') AS correo, "
                    + "CASE WHEN pr.activo THEN 'Activo' ELSE 'Inactivo' END AS estado, "
                    + "COUNT(p.id_producto) AS productos FROM proveedores pr "
                    + "LEFT JOIN productos p ON pr.id_proveedor = p.id_proveedor "
                    + "GROUP BY pr.id_proveedor, pr.nombre, pr.telefono, pr.correo, pr.activo "
                    + "ORDER BY pr.nombre";
        } else {
            columnas = new String[]{"Nombre", "Usuario", "Rol", "Estado", "Movimientos"};
            sql = "SELECT u.nombre, u.usuario, u.rol, "
                    + "CASE WHEN u.activo THEN 'Activo' ELSE 'Inactivo' END AS estado, "
                    + "COUNT(m.id_movimiento) AS movimientos FROM usuarios u "
                    + "LEFT JOIN movimientos m ON u.id_user = m.id_user "
                    + "GROUP BY u.id_user, u.nombre, u.usuario, u.rol, u.activo "
                    + "ORDER BY u.nombre";
        }

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(columnas);
        tablaReporte.setModel(modelo);
        tablaReporte.setDefaultEditor(Object.class, null);

        Connection conexion = Conexion.conectar();
        if (conexion == null) {
            lblResumen.setText("No se pudo cargar el reporte");
            return false;
        }

        try {
            PreparedStatement consulta = conexion.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            int total = 0;
            int unidades = 0;
            int entradas = 0;
            int salidas = 0;
            int activos = 0;
            double valor = 0;

            while (resultado.next()) {
                Object[] fila = new Object[columnas.length];
                for (int i = 0; i < columnas.length; i++) {
                    fila[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(fila);
                total++;

                if (reporte.equals("Inventario completo")) {
                    unidades += resultado.getInt("existencia");
                    valor += resultado.getDouble("valor");
                } else if (reporte.equals("Movimientos")) {
                    if (resultado.getString("tipo").equals("Entrada")) {
                        entradas += resultado.getInt("cantidad");
                    } else {
                        salidas += resultado.getInt("cantidad");
                    }
                } else if ((reporte.equals("Proveedores")
                        || reporte.equals("Colaboradores"))
                        && resultado.getString("estado").equals("Activo")) {
                    activos++;
                }
            }

            if (reporte.equals("Resumen general")) {
                lblResumen.setText("Indicadores generales del sistema");
            } else if (reporte.equals("Inventario completo")) {
                lblResumen.setText("Productos: " + total + " | Unidades: "
                        + unidades + " | Valor: $" + String.format("%.2f", valor));
            } else if (reporte.equals("Existencia baja")) {
                lblResumen.setText("Productos que necesitan revisión: " + total);
            } else if (reporte.equals("Movimientos")) {
                lblResumen.setText("Movimientos: " + total + " | Entradas: "
                        + entradas + " | Salidas: " + salidas);
            } else if (reporte.equals("Proveedores")
                    || reporte.equals("Colaboradores")) {
                lblResumen.setText("Registros: " + total + " | Activos: " + activos);
            } else {
                lblResumen.setText("Categorías registradas: " + total);
            }

            tituloActual = reporte;
            if (reporte.equals("Movimientos")) {
                tituloActual += " - " + periodo;
            }

            resultado.close();
            consulta.close();
            Conexion.cerrar(conexion);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar el reporte");
            System.err.println(e.toString());
            Conexion.cerrar(conexion);
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblInstruccion = new javax.swing.JLabel();
        lblReporte = new javax.swing.JLabel();
        cmbReporte = new javax.swing.JComboBox<>();
        lblPeriodo = new javax.swing.JLabel();
        cmbPeriodo = new javax.swing.JComboBox<>();
        btnVer = new javax.swing.JButton();
        lblAviso = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaReporte = new javax.swing.JTable();
        lblResumen = new javax.swing.JLabel();
        btnGuardarPDF = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CaVentory - Reportes");
        setResizable(false);
        setBackground(new java.awt.Color(246, 248, 246));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(35, 82, 60));
        lblTitulo.setText("Reportes y análisis");

        lblUsuario.setText("Usuario:");

        lblInstruccion.setText("Selecciona la información que deseas consultar o guardar");

        lblReporte.setText("Reporte");

        cmbReporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Resumen general", "Inventario completo", "Existencia baja", "Movimientos", "Categorías", "Proveedores", "Colaboradores" }));

        lblPeriodo.setText("Período");

        cmbPeriodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Hoy", "Este mes" }));

        btnVer.setBackground(new java.awt.Color(226, 239, 231));
        btnVer.setFocusPainted(false);
        btnVer.setText("Ver datos");
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });

        lblAviso.setForeground(new java.awt.Color(90, 90, 90));
        lblAviso.setText("El período solamente se utiliza en el reporte de movimientos");

        tablaReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Concepto", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaReporte.setRowHeight(24);
        tablaReporte.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tablaReporte);

        lblResumen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResumen.setText("Indicadores generales del sistema");

        btnGuardarPDF.setBackground(new java.awt.Color(47, 107, 79));
        btnGuardarPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarPDF.setFocusPainted(false);
        btnGuardarPDF.setText("Guardar PDF");
        btnGuardarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPDFActionPerformed(evt);
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
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblInstruccion)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblReporte)
                            .addComponent(cmbReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPeriodo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(btnVer, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblAviso)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblResumen)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(lblUsuario))
                .addGap(10, 10, 10)
                .addComponent(lblInstruccion)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReporte)
                    .addComponent(lblPeriodo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVer))
                .addGap(8, 8, 8)
                .addComponent(lblAviso)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblResumen)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarPDF)
                    .addComponent(btnCerrar))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        cargarDatos();
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnGuardarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPDFActionPerformed
        if (!cargarDatos()) {
            return;
        }

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte");
        selector.setSelectedFile(new File(tituloActual.replace(" ", "_") + ".pdf"));
        if (selector.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
            archivo = new File(archivo.getAbsolutePath() + ".pdf");
        }

        if (archivo.exists()) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "El archivo ya existe. ¿Desea reemplazarlo?",
                    "Guardar reporte", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            GeneradorPDF.guardar(tablaReporte, tituloActual,
                    lblResumen.getText(), archivo);
            JOptionPane.showMessageDialog(this,
                    "Reporte guardado en:\n" + archivo.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el reporte");
            System.err.println(e.toString());
        }
    }//GEN-LAST:event_btnGuardarPDFActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardarPDF;
    private javax.swing.JButton btnVer;
    private javax.swing.JComboBox<String> cmbPeriodo;
    private javax.swing.JComboBox<String> cmbReporte;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblInstruccion;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JLabel lblReporte;
    private javax.swing.JLabel lblResumen;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tablaReporte;
    // End of variables declaration//GEN-END:variables
}
