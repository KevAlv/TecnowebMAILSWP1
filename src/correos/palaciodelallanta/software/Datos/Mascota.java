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
public class Mascota {

    private int id;
    private String nombre;
    private String raza;
    private String color;
    private int tipo;
    private int cliente_id;

    private Conexion m_Conexion;

    public Mascota() {
        m_Conexion = Conexion.getInstancia();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    //Setters and Getters
    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    ///METHODS
    public void setMascota(int id, String nombre, String raza, String color, int tipo, int cliente_id) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.color = color;
        this.cliente_id = cliente_id;
    }

    public void setMascota(String nombre, String raza, String color, int tipo, int cliente_id) {
        this.nombre = nombre;
        this.tipo=tipo;
        this.raza = raza;
        this.color = color;
        this.cliente_id = cliente_id;
    }

    public void registrar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        // Preparo la consulta
        PreparedStatement ps = null;
        String query = "INSERT INTO mascotas \n"
                + "(nombre,raza,color,tipo,cliente_id) \n"
                + " values (?,?,?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, raza);
            ps.setString(3, color);
            ps.setInt(4, tipo);
            ps.setInt(5, cliente_id);
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
        String query = "UPDATE mascotas SET \n"
                + "nombre = ?,\n"
                + "raza = ?, \n"
                + "color = ?, \n"
                + "tipo = ?, \n"
                + "cliente_id = ? \n"
                + "WHERE id = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, raza);
            ps.setString(3, color);
            ps.setInt(4, tipo);
            ps.setInt(5, cliente_id);
            ps.setInt(6, id);
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
            ps = con.prepareStatement("DELETE FROM mascotas WHERE id = ?");
            ps.setInt(1, this.id);
            System.out.println("ENTRO AL METODO ELIMINAR");
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public DefaultTableModel getMascota(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel mascota = new DefaultTableModel();
        mascota.setColumnIdentifiers(new Object[]{
            "id", "nombre", "raza", "color", "tipo", "cliente_id"
        });
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        // Preparo la consulta
        String sql = "SELECT * FROM mascotas WHERE id=?";
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
                mascota.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("raza"),
                    rs.getString("color"),
                    rs.getInt("tipo"),
                    rs.getInt("cliente_id")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mascota;
    }

    public DefaultTableModel getMascotas() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel mascotas = new DefaultTableModel();
        mascotas.setColumnIdentifiers(new Object[]{
            "id", "nombre", "raza", "color", "tipo", "cliente_id"
        });
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT * FROM mascotas";
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                mascotas.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("raza"),
                    rs.getString("color"),
                    rs.getInt("tipo"),
                    rs.getInt("cliente_id")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mascotas;
    }
}
