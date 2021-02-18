/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Negocio;

import correos.palaciodelallanta.software.Datos.Atencion;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class AtencionNegocio {

    private Atencion atencion;

    public AtencionNegocio() {
        this.atencion = new Atencion();
    }
    
    public DefaultTableModel getAtencion(int id) {
        return atencion.getAtencion(id);
    }

    public DefaultTableModel getAtenciones() {
        return atencion.getAtenciones();
    }

    public void registrar(String fecha, String descripcion, String diagnostico, int cliente_id, int veterinario_id, int mascota_id) {
        atencion.setAtencion(fecha, descripcion, diagnostico, cliente_id, veterinario_id, mascota_id);
        atencion.registrar();
    }

    public void modificar(int id, String fecha, String descripcion, String diagnostico, int cliente_id, int veterinario_id, int mascota_id) {
        atencion.setAtencion(id, fecha, descripcion, diagnostico, cliente_id, veterinario_id, mascota_id);
        atencion.modificar();
    }

    public void eliminar(int id) {
        atencion.setId(id);
        atencion.eliminar();
    }
}
