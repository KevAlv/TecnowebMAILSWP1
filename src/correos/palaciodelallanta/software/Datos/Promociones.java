/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Datos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author
 */
public class Promociones {

    private int id;
    private String descripcion;
    private Date fechai;
    private Date fechaf;
    private int descuento;
  

    private Conexion m_Conexion;

    public Promociones() {
        m_Conexion = Conexion.getInstancia();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getfechai() {
        return fechai;
    }

    public void setfechai(Date fechai) {
        this.fechai = fechai;
    }

    public Date getfechaf() {
        return fechaf;
    }

    public void setfechaf(Date fechaf) {
        this.fechaf = fechaf;
    }

    ///METHODS
    public void setPromocion(int id, String descripcion, Date fechai, Date fechaf, int descuento) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechai = fechai;
        this.fechaf = fechaf;
     
    }

    public void setPromocion(String descripcion, Date fechai, Date fechaf, int descuento) {
        this.descripcion = descripcion;
        this.descuento=descuento;
        this.fechai = fechai;
        this.fechaf = fechaf;
    }

    public void registrar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        // Preparo la consulta
        PreparedStatement ps = null;
        String query = "INSERT INTO Promociones \n"
                + "(descripcion,fechai,fechaf,descuento) \n"
                + " values (?,?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, descripcion);
            ps.setDate(2, fechai);
            ps.setDate(3, fechaf);
            ps.setInt(4, descuento);
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
        String query = "UPDATE Promociones SET \n"
                + "descripcion = ?,\n"
                + "fechai = ?, \n"
                + "fechaf = ?, \n"
                + "descuento = ?, \n"
                + "WHERE id = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, descripcion);
            ps.setDate(2, fechai);
            ps.setDate(3, fechaf);
            ps.setInt(4, descuento);
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
            ps = con.prepareStatement("DELETE FROM Promociones WHERE id = ?");
            ps.setInt(1, this.id);
            System.out.println("ENTRO AL METODO ELIMINAR");
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public DefaultTableModel getPromocion(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel Promocion = new DefaultTableModel();
        Promocion.setColumnIdentifiers(new Object[]{
            "id", "descripcion", "fechai", "fechaf", "descuento"
        });
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        // Preparo la consulta
        String sql = "SELECT * FROM Promociones WHERE id=?";
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
                Promocion.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("descripcion"),
                    rs.getString("fechai"),
                    rs.getString("fechaf"),
                    rs.getInt("descuento"),
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Promocion;
    }

    public DefaultTableModel getPromocions() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel Promociones = new DefaultTableModel();
        Promociones.setColumnIdentifiers(new Object[]{
            "id", "descripcion", "fechai", "fechaf", "descuento"
        });
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT * FROM Promociones";
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                Promociones.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("descripcion"),
                    rs.getString("fechai"),
                    rs.getString("fechaf"),
                    rs.getInt("descuento"),
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Promociones;
    }
}

