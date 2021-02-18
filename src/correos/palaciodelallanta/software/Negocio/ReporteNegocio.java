/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Negocio;

import com.idrsolutions.image.pdf.PdfEncoder;
import correos.palaciodelallanta.software.Datos.Reporte;
import java.io.File;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class ReporteNegocio {

    private Reporte reporte;

    public ReporteNegocio() {
        this.reporte = new Reporte();
    }

    // reporte total de ventas por mes
    public DefaultTableModel ventasMensuales() {
        return reporte.ventasMensuales();
    }

    // los clientes que mas compras han realizado
    public DefaultTableModel top3ClientesCompras() {
        return reporte.top3ClientesCompras();
    }

    // LAS 3 MASCOTAS QUE RECIBIERON ATENCION
    public DefaultTableModel top3MascotasAtendidas() {
        return reporte.top3MascotasAtendidas();
    }

    //los productos que mas vendieron
    public DefaultTableModel top3ProductosVendidos() {
        return reporte.top3ProductosVendidos();
    }

    //ventas total del dia de hoy
    public DefaultTableModel ventasTotalDeHoy() {
        return reporte.ventasTotalDeHoy();
    }

    public void tortaPorcentajeAnimal() {
        double perros = reporte.getPorcentajePerros();
        double gatos = reporte.getPorcentajeGatos();
        double otros = reporte.getPorcentajeOtros();
        LinkedList<Double> lista = new LinkedList<>();
        lista.add(perros);
        lista.add(gatos);
        lista.add(otros);
        guardar(lista);
    }

    private void guardar(LinkedList<Double> lista) {
        double perros = lista.get(0);
        double gatos = lista.get(1);
        double otros = lista.get(2);
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("PERROS: " + perros + "%", new Double(perros));
        dataset.setValue("GATOS: " + gatos + "%", new Double(gatos));
        dataset.setValue("OTROS: " + otros + "%", new Double(otros));
        JFreeChart chart = ChartFactory.createPieChart3D(// char t
                "Grafico circular 3D - Porcentaje de tipos de animales atendidos ",// title                                                                     
                dataset, // data
                true, // include legend
                true, false);
        try {
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file = new File("chart.jpg");
            // ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
            ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
            File pdfFile = new File("reporte.pdf");
            pdfFile.createNewFile();
            //write the image to the pdf
            PdfEncoder encoder = new PdfEncoder();
            encoder.write(file, pdfFile);

        } catch (Exception e) {
            System.out.println("ALGO SALIO MAL INTENTE DE NUEVO");
        }

    }

}
