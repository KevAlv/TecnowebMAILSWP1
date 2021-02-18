/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Negocio;

import correos.palaciodelallanta.software.Datos.Mascota;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class MascotaNegocio {

    private Mascota mascota;

    public MascotaNegocio() {
        this.mascota = new Mascota();
    }

    public DefaultTableModel getMascota(int id) {
        return mascota.getMascota(id);
    }

    public DefaultTableModel getMascotas() {
        return mascota.getMascotas();
    }

    public void registrar(String nombre,String raza, String color,int tipo,int cliente_id) {
        mascota.setMascota(nombre, raza, color,  tipo ,cliente_id);
        mascota.registrar();
    }

    public void modificar(int id, String nombre,String raza, String color,int tipo, int cliente_id) {
        mascota.setMascota(id, nombre, raza, color, tipo, cliente_id);
        mascota.modificar();
    }

    public void eliminar(int id) {
        mascota.setId(id);
        mascota.eliminar();
    }
}
