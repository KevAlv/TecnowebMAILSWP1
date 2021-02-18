/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Negocio;

import correos.palaciodelallanta.software.Datos.Producto;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class ProductoNegocio {

    private Producto producto;

    public ProductoNegocio() {
        this.producto = new Producto();
    }

    public DefaultTableModel getProducto(int id) {
        return producto.getProducto(id);
    }

    public DefaultTableModel getProductos() {
        return producto.getProductos();
    }

    public void registrar(String nombre, int precio, int categoria_id) {
        producto.setProducto(nombre, precio, categoria_id);
        producto.registrar();
    }

    public void modificar(int id, String nombre, int precio, int categoria_id) {
        producto.setProducto(id, nombre, precio, categoria_id);
        producto.modificar();
    }

    public void eliminar(int id) {
        producto.setId(id);
        producto.eliminar();
    }
}
