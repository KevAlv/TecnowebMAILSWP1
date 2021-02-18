/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Negocio;

import correos.palaciodelallanta.software.Datos.DetalleVenta;
import correos.palaciodelallanta.software.Datos.Venta;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class VentaNegocio {

    private Venta venta;
    private DetalleVenta detalle;

    public VentaNegocio() {
        this.venta = new Venta();
        this.detalle = new DetalleVenta();
    }

    public void registrar(String nit, Date fecha, int cliente_id, int veterinario_id, LinkedList<DetalleVenta> lista) {
        venta.setVenta(nit, fecha, cliente_id, veterinario_id);
        int total = 0;
        int venta_id = venta.registrar();
        for (DetalleVenta detalle : lista) {
            detalle.setIDVenta(venta_id);
            detalle.registrar();
            total += detalle.getPrecioProductoxCantidad();
            System.out.println("registrando detalles");
        }
        venta.actualizarTotal(total);

    }

    public DefaultTableModel getVenta(int id) {
        return venta.getVenta(id);
    }

    public DefaultTableModel getVentas() {
        return venta.getVentas();
    }

    public void modificar(int id, String nit, Date fecha, int cliente_id, int veterinario_id) {
        //id de la categoria a modificar
        venta.setVenta(id, nit, fecha, cliente_id, veterinario_id);
        venta.modificar();
    }

    public void eliminar(int id) {
        venta.setId(id); // elimina la venta
        venta.eliminar();

    }

}
