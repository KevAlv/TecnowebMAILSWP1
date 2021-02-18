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
 * 
 */
public class Administrativo {

    private int id;
    private String nombre;
    private String apellido;
    private int ci;
    private String celular;
    private String direccion;

    public Conexion m_Conexion;

    public Administrativo() {
        this.m_Conexion = Conexion.getInstancia();
    }

    public void setAdminitrativo(String nombre, String apellidos, int ci, String celular, String direccion) {
        this.nombre = nombre;
        this.apellido = apellidos;
        this.ci = ci;
        this.celular = celular;
        this.direccion = direccion;
    }

    public void setAdminitrativo(int id, String nombre, String apellidos, int ci, String celular, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellidos;
        this.ci = ci;
        this.celular = celular;
        this.direccion = direccion;
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

    public String getApellidos() {
        return apellido;
    }

    public void setApellidos(String apellidos) {
        this.apellido = apellidos;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void registrar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        PreparedStatement ps = null;
        String query = "INSERT INTO users \n"
                + "(nombre,apellido,ci,celular,direccion) \n"
                + " values (?,?,?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, ci);
            ps.setString(4, celular);
            ps.setString(5, direccion);
            ps.executeUpdate();
            System.out.println("Registrado!!");
            con.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    public void modificar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        PreparedStatement ps = null;
        String query = "UPDATE users SET \n"
                + "nombre = ?,\n"
                + "apellido = ?, \n"
                + "ci = ?, \n"
                + "celular = ?,\n"
                + "direccion = ? \n"
                + "WHERE id = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, ci);
            ps.setString(4, celular);
            ps.setString(5, direccion);
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
            ps = con.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public DefaultTableModel getAdminitrativo(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel veterinario = new DefaultTableModel();
        veterinario.setColumnIdentifiers(new Object[]{
            "id", "nombre", "apellidos", "ci", "celular", "direccion"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT * FROM users WHERE id=?";
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
                veterinario.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("ci"),
                    rs.getString("celular"),
                    rs.getString("direccion"),});
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return veterinario;
    }

    public DefaultTableModel getAdminitrativos() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel users = new DefaultTableModel();
        users.setColumnIdentifiers(new Object[]{
            "id", "nombre", "apellido", "ci", "celular", "direccion"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT * FROM users";
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                users.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("ci"),
                    rs.getString("celular"),
                    rs.getString("direccion")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }
}
