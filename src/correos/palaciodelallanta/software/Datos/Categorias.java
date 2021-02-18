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
public class Categorias {

    public int id;
    public String nombre;
    public String descripcion;
    
    public Conexion m_Conexion;

    public void setCategoria(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public void setCategoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Categorias() {
        this.m_Conexion = Conexion.getInstancia();
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

     public DefaultTableModel getCategoria(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel categoria = new DefaultTableModel();
        categoria.setColumnIdentifiers(new Object[]{
            "id", "nombre", "descripcion"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT\n"
                + "categorias.id,\n"
                + "categorias.nombre,\n"
                + "categorias.descripcion\n"
                + "FROM categorias\n"
                + "WHERE categorias.id=?";
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
                categoria.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categoria;
    }

    public DefaultTableModel getCategorias() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel categorias = new DefaultTableModel();
        categorias.setColumnIdentifiers(new Object[]{
            "id", "nombre", "descripcion"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT\n"
                + "categorias.id,\n"
                + "categorias.nombre,\n"
                + "categorias.descripcion\n"
                + "FROM categorias order by id asc";

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                categorias.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categorias;
    }

    public void registrar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        PreparedStatement ps = null;
        String query = "INSERT INTO categoriaS (nombre,descripcion) values (?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, descripcion);

            ps.executeUpdate();
            System.out.println("INGRESANDO AL METODO REGISTRAR DEL MODELO CATEGORIA");
            con.close();
        } catch (SQLException e) {
            System.out.println("NO INGRESANDO AL METODO REGISTRAR DEL MODELO");
            System.out.println(e.getMessage());
        }

    }

    public void modificar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        PreparedStatement ps = null;
        String query = "UPDATE categorias SET \n"
                + "nombre = ?,\n"
                + "descripcion = ? \n"
                + "WHERE id = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, this.nombre);
            ps.setString(2, this.descripcion);
            ps.setInt(3, this.id);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {

            System.out.println("ERROR:" + e.getMessage());
        }

    }

    public void eliminar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM categorias WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
