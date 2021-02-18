/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Negocio;

import correos.palaciodelallanta.software.Datos.Cliente;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class ClienteNegocio {

    //Datos

    private Cliente cliente;

    public ClienteNegocio() {
        this.cliente = new Cliente();
    }

    public DefaultTableModel getCliente(int id) {
        return cliente.getCliente(id);
    }

    public DefaultTableModel getClientes() {
        return cliente.getClientes();
    }

    public void registrar(String nombre, String apellidos, int ci, String celular) {
        cliente.setCliente(nombre, apellidos, ci, celular); 
        cliente.registrar();
    }

    public void modificar(int id, String nombre, String apellidos, int ci, String celular) {
        cliente.setCliente(id, nombre, apellidos, ci, celular);
        cliente.modificar();
    }

    public void eliminar(int id) {
        cliente.setId(id);
        cliente.eliminar();
    }

}
