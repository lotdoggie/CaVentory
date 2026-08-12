package caventory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;
import javax.swing.JTable;

public class GeneradorPDF {

    private static final int FILAS_POR_PAGINA = 20;

    public static void guardar(JTable tabla, String titulo,
            String resumen, File archivo) throws IOException {
        int paginas = Math.max(1,
                (tabla.getRowCount() + FILAS_POR_PAGINA - 1)
                / FILAS_POR_PAGINA);
        int fuenteNormal = 3 + paginas * 2;
        int fuenteNegrita = fuenteNormal + 1;
        int totalObjetos = fuenteNegrita;
        int[] posiciones = new int[totalObjetos + 1];
        ByteArrayOutputStream pdf = new ByteArrayOutputStream();

        escribir(pdf, "%PDF-1.4\n");
        posiciones[1] = pdf.size();
        objeto(pdf, 1, "<< /Type /Catalog /Pages 2 0 R >>");

        String paginasPDF = "";
        for (int i = 0; i < paginas; i++) {
            paginasPDF += (3 + i * 2) + " 0 R ";
        }
        posiciones[2] = pdf.size();
        objeto(pdf, 2, "<< /Type /Pages /Kids ["
                + paginasPDF + "] /Count " + paginas + " >>");

        for (int i = 0; i < paginas; i++) {
            int numeroPagina = 3 + i * 2;
            int numeroContenido = numeroPagina + 1;
            byte[] contenido = pagina(tabla, titulo, resumen,
                    i, paginas).getBytes(StandardCharsets.ISO_8859_1);

            posiciones[numeroPagina] = pdf.size();
            objeto(pdf, numeroPagina,
                    "<< /Type /Page /Parent 2 0 R "
                    + "/MediaBox [0 0 842 595] "
                    + "/Resources << /Font << /F1 "
                    + fuenteNormal + " 0 R /F2 "
                    + fuenteNegrita + " 0 R >> >> "
                    + "/Contents " + numeroContenido + " 0 R >>");

            posiciones[numeroContenido] = pdf.size();
            escribir(pdf, numeroContenido + " 0 obj\n<< /Length "
                    + contenido.length + " >>\nstream\n");
            pdf.write(contenido);
            escribir(pdf, "\nendstream\nendobj\n");
        }

        posiciones[fuenteNormal] = pdf.size();
        objeto(pdf, fuenteNormal, "<< /Type /Font /Subtype /Type1 "
                + "/BaseFont /Helvetica /Encoding /WinAnsiEncoding >>");
        posiciones[fuenteNegrita] = pdf.size();
        objeto(pdf, fuenteNegrita, "<< /Type /Font /Subtype /Type1 "
                + "/BaseFont /Helvetica-Bold /Encoding /WinAnsiEncoding >>");

        int inicio = pdf.size();
        escribir(pdf, "xref\n0 " + (totalObjetos + 1) + "\n");
        escribir(pdf, "0000000000 65535 f \n");
        for (int i = 1; i <= totalObjetos; i++) {
            escribir(pdf, String.format(Locale.US,
                    "%010d 00000 n \n", posiciones[i]));
        }
        escribir(pdf, "trailer\n<< /Size " + (totalObjetos + 1)
                + " /Root 1 0 R >>\nstartxref\n"
                + inicio + "\n%%EOF");
        Files.write(archivo.toPath(), pdf.toByteArray());
    }

    private static String pagina(JTable tabla, String titulo,
            String resumen, int pagina, int paginas) {
        StringBuilder contenido = new StringBuilder();
        contenido.append("q 0.12 0.31 0.47 rg 0 525 842 70 re f Q\n");
        texto(contenido, "F2", 24, "1 1 1", 30, 558, "CaVentory");
        texto(contenido, "F1", 12, "0.85 0.92 0.98", 30, 536,
                cortar(titulo, 90));

        contenido.append("q 0.93 0.96 0.99 rg 30 480 782 28 re f Q\n");
        texto(contenido, "F2", 9, "0.12 0.24 0.36", 40, 490,
                cortar(resumen, 125));

        fila(contenido, tabla, -1, 452, true);
        int primeraFila = pagina * FILAS_POR_PAGINA;
        int ultimaFila = Math.min(primeraFila + FILAS_POR_PAGINA,
                tabla.getRowCount());
        int y = 434;
        for (int i = primeraFila; i < ultimaFila; i++) {
            fila(contenido, tabla, i, y, false);
            y -= 18;
        }

        if (tabla.getRowCount() == 0) {
            texto(contenido, "F1", 10, "0.35 0.40 0.45", 40, 420,
                    "No hay registros para mostrar.");
        }

        texto(contenido, "F1", 8, "0.40 0.45 0.50", 30, 28,
                "Reporte generado por CaVentory");
        texto(contenido, "F1", 8, "0.40 0.45 0.50", 740, 28,
                "Página " + (pagina + 1) + " de " + paginas);
        return contenido.toString();
    }

    private static void fila(StringBuilder contenido, JTable tabla,
            int fila, int y, boolean encabezado) {
        int columnas = tabla.getColumnCount();
        int ancho = 782 / columnas;
        int x = 30;

        for (int i = 0; i < columnas; i++) {
            int anchoCelda = i == columnas - 1 ? 812 - x : ancho;
            if (encabezado) {
                contenido.append("q 0.18 0.43 0.64 rg ")
                        .append(x).append(" ").append(y).append(" ")
                        .append(anchoCelda).append(" 22 re f Q\n");
            } else if (fila % 2 == 0) {
                contenido.append("q 0.96 0.97 0.99 rg ")
                        .append(x).append(" ").append(y).append(" ")
                        .append(anchoCelda).append(" 18 re f Q\n");
            }

            contenido.append("q 0.72 0.78 0.84 RG 0.5 w ")
                    .append(x).append(" ").append(y).append(" ")
                    .append(anchoCelda).append(" ")
                    .append(encabezado ? 22 : 18).append(" re S Q\n");

            String valor;
            if (encabezado) {
                valor = tabla.getColumnName(i);
            } else {
                Object dato = tabla.getValueAt(fila, i);
                valor = dato == null ? "" : dato.toString();
            }
            texto(contenido, encabezado ? "F2" : "F1", 7,
                    encabezado ? "1 1 1" : "0.16 0.20 0.24",
                    x + 4, y + (encabezado ? 8 : 6),
                    cortar(valor, Math.max(4, (anchoCelda - 8) / 4)));
            x += anchoCelda;
        }
    }

    private static void texto(StringBuilder contenido, String fuente,
            int tamano, String color, int x, int y, String valor) {
        contenido.append("BT /").append(fuente).append(" ")
                .append(tamano).append(" Tf ").append(color).append(" rg ")
                .append(x).append(" ").append(y).append(" Td (")
                .append(escapar(valor)).append(") Tj ET\n");
    }

    private static String cortar(String texto, int maximo) {
        texto = texto.replace("\n", " ").replace("\r", " ");
        if (texto.length() <= maximo) {
            return texto;
        }
        return texto.substring(0, Math.max(1, maximo - 3)) + "...";
    }

    private static String escapar(String texto) {
        return texto.replace("\\", "\\\\")
                .replace("(", "\\(").replace(")", "\\)");
    }

    private static void objeto(ByteArrayOutputStream pdf,
            int numero, String contenido) throws IOException {
        escribir(pdf, numero + " 0 obj\n" + contenido + "\nendobj\n");
    }

    private static void escribir(ByteArrayOutputStream pdf,
            String texto) throws IOException {
        pdf.write(texto.getBytes(StandardCharsets.ISO_8859_1));
    }
}
