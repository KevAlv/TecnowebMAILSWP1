/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class Atencion {

    //atributos
    private int id;
    private String fecha;
    private String descripcion;
    private String diagnostico;
    //foreign
    private int cliente_id;
    private int veterinario_id;
    private int mascota_id;

    //BD
    private Conexion m_Conexion;

    public Atencion() {
        m_Conexion = Conexion.getInstancia();
    }
    //Setters y Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getVeterinario_id() {
        return veterinario_id;
    }

    public void setVeterinario_id(int veterinario_id) {
        this.veterinario_id = veterinario_id;
    }

    public int getMascota_id() {
        return mascota_id;
    }

    public void setMascota_id(int mascota_id) {
        this.mascota_id = mascota_id;
    }

    ///METHODS
    public void setAtencion(int id, String fecha, String descripcion, String diagnostico, int cliente_id, int veterinario_id, int mascota_id) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.cliente_id = cliente_id;
        this.veterinario_id = veterinario_id;
        this.mascota_id = mascota_id;
    }

    public void setAtencion(String fecha, String descripcion, String diagnostico, int cliente_id, int veterinario_id, int mascota_id) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.cliente_id = cliente_id;
        this.veterinario_id = veterinario_id;
        this.mascota_id = mascota_id;
    }

    public void registrar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        // Preparo la consulta
        PreparedStatement ps = null;
        String query = "INSERT INTO atenciones \n"
                + "(fecha,descripcion,diagnostico,cliente_id,veterinario_id,mascota_id) \n"
                + " values (?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, fecha);
            ps.setString(2, descripcion);
            ps.setString(3, diagnostico);
            ps.setInt(4, cliente_id);
            ps.setInt(5, veterinario_id);
            ps.setInt(6, mascota_id);
            ps.executeUpdate();
            System.out.println("Registrado!!");
            con.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    // JOIN 
    /*
    
     SELECT * FROM productos INNER JOIN categorias 
     ON productos.categoria_id = categorias.id;
    
     */

    public void modificar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        PreparedStatement ps = null;
        String query = "UPDATE atenciones SET \n"
                + "fecha = ?,\n"
                + "descripcion = ?, \n"
                + "diagnostico = ?, \n"
                + "cliente_id = ?, \n"
                + "veterinario_id = ?, \n"
                + "mascota_id = ? \n"
                + "WHERE id = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, fecha);
            ps.setString(2, descripcion);
            ps.setString(3, diagnostico);
            ps.setInt(4, cliente_id);
            ps.setInt(5, veterinario_id);
            ps.setInt(6, mascota_id);
            ps.setInt(7, id);
            ps.executeUpdate();
            System.out.println("Modificado!!");
            con.close();
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    public void eliminar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM atenciones WHERE id = ?");
            ps.setInt(1, this.id);
            System.out.println("ELIMINADO");
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public DefaultTableModel getAtencion(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel atencion = new DefaultTableModel();
        atencion.setColumnIdentifiers(new Object[]{
            "id", "fecha", "descripcion", "diagnostico", "cliente_id",
            "veterinario_id", "mascota_id"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        // Preparo la consulta
         String sql = "SELECT * FROM atenciones WHERE id=?";
        // Los simbolos de interrogacion son para mandar parametros 
        // a la consulta al momento de ejecutalas

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                atencion.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("fecha"),
                    rs.getString("descripcion"),
                    rs.getString("diagnostico"),
                    rs.getInt("cliente_id"),
                    rs.getInt("veterinario_id"),
                    rs.getInt("mascota_id"),});
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return atencion;
    }

    public DefaultTableModel getAtenciones() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel atenciones = new DefaultTableModel();
        atenciones.setColumnIdentifiers(new Object[]{
            "id", "fecha", "descripcion", "diagnostico", "cliente_id", "veterinario_id", "mascota_id"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        // Preparo la consulta
        String sql = "SELECT\n"
                + "id,\n"
                + "fecha,\n"
                + "descripcion,\n"
                + "diagnostico,\n"
                + "cliente_id,\n"
                + "veterinario_id,\n"
                + "mascota_id\n"
                + "FROM atenciones";

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                atenciones.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("fecha"),
                    rs.getString("descripcion"),
                    rs.getString("diagnostico"),
                    rs.getInt("cliente_id"),
                    rs.getInt("veterinario_id"),
                    rs.getInt("mascota_id"),});
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return atenciones;
    }
}
