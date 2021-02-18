/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Email;

import correos.palaciodelallanta.software.Negocio.ReporteNegocio;
import com.idrsolutions.image.pdf.PdfEncoder;
import correos.palaciodelallanta.procesador.Analex;
import correos.palaciodelallantas.utils.Cadenas;
import correos.palaciodelallantas.utils.Mensaje;
import correos.palaciodelallantas.utils.Utils;
import java.io.File;
import java.util.LinkedList;
import javax.mail.MessagingException;
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
public class MailReporte {

    ReporteNegocio reporteNegocio = new ReporteNegocio();

    // reporte total de ventas por mes
    public void ventasMensuales(Analex analex, String destinatario) throws MessagingException {
        String Head[] = {"AÑO", "MES", "PRECIOTOTALVENTA", "CANTIDADVENTA", "PROMEDIOVENTA"};
        String Cabecera = "REPORTE - VENTAS TOTALES POR MES ";
        Mensaje message = Utils.dibujarTablaHtml(reporteNegocio.ventasMensuales(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println(Cadenas.SUCCESSFULL_MAIL);
        } else {
            System.out.println(Cadenas.FAILED_MAIL);
        }
    }

    // los clientes que mas compras han realizado
    public void top3ClientesCompras(Analex analex, String destinatario) throws MessagingException {
        String Head[] = {"NOMBRE", "APELLIDOS", "CI", "CELULAR", "CANTIDAD-COMPRAS"};
        String Cabecera = "REPORTE - TOP 3 CLIENTES QUE MAS COMPRAS REALIZARON";
        Mensaje message = Utils.dibujarTablaHtml(reporteNegocio.top3ClientesCompras(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println(Cadenas.SUCCESSFULL_MAIL);
        } else {
            System.out.println(Cadenas.FAILED_MAIL);
        }
    }

    // LAS 3 MASCOTAS QUE RECIBIERON ATENCION
    public void top3MascotasAtendidas(Analex analex, String destinatario) throws MessagingException {
        String Head[] = {"ID", "NOMBRE", "RAZA", "NOMBRE (DUEÑO)", "APELLIDOS", "CANTIDAD"};
        String Cabecera = "REPORTE - TOP 3 MASCOTAS MAS VECES ATENTIDAS";
        Mensaje message = Utils.dibujarTablaHtml(reporteNegocio.top3MascotasAtendidas(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println(Cadenas.SUCCESSFULL_MAIL);
        } else {
            System.out.println(Cadenas.FAILED_MAIL);
        }
    }

    //los productos que mas vendieron
    public void top3ProductosVendidos(Analex analex, String destinatario) throws MessagingException {
        String Head[] = {"ID", "NOMBRE", "PRECIO", "CATEGORIA", "CANTIDAD"};
        String Cabecera = "REPORTE - TOP 3 PRODUCTOS MAS VENDIDOS";
        Mensaje message = Utils.dibujarTablaHtml(reporteNegocio.top3ProductosVendidos(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println(Cadenas.SUCCESSFULL_MAIL);
        } else {
            System.out.println(Cadenas.FAILED_MAIL);
        }
    }

    //ventas total del dia de hoy
    public void ventasTotalDeHoy(Analex analex, String destinatario) throws MessagingException {
        String Head[] = {"ID", "NIT", "FECHA", "TOTAL(Bs.)", "CIENTE ID", "VETERINARIO ID"};
        String Cabecera = "REPORTE - VENTAS DEL DIA DE HOY";
        Mensaje message = Utils.dibujarTablaHtml(reporteNegocio.ventasTotalDeHoy(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println(Cadenas.SUCCESSFULL_MAIL);
        } else {
            System.out.println(Cadenas.FAILED_MAIL);
        }
    }

    public void tortaPorcentajeAnimal(Analex analex, String destinatario) throws MessagingException {
        reporteNegocio.tortaPorcentajeAnimal();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mensaje message = new Mensaje();
        message.setCorreo(destinatario);
        message.setSubject("REPORTE TORTA PORCENTAJE ANIMALES");
        message.setCorreo(destinatario);
        if (message.enviarCorreoAdjunto()) {
            System.out.println("ENVIADO");
        } else {
            System.out.println("NO ENVIADO");
        }

    }

   
}
