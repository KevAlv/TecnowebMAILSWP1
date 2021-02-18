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
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 
 */

public class Venta {

    private int id;
    private String nit;
    private Date fecha;
    private int total; // total de la venta 
    private int cliente_id;
    private int users_id;

    private Conexion m_Conexion;

    public Venta() {
        m_Conexion = Conexion.getInstancia();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setVenta(int id, String nit, Date fecha, int cliente_id, int users_id) {
        this.id = id;
        this.nit = nit;
        this.fecha = fecha;
        this.cliente_id = cliente_id;
        this.users_id = users_id;
        total = 0;
    }

    public void setVenta(String nit, Date fecha, int cliente_id, int users_id) {
        this.fecha = fecha;
        this.nit = nit;
        this.cliente_id = cliente_id;
        this.users_id = users_id;
        total = 0;
    }

    public int registrar() {
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "INSERT INTO ventas (\n"
                + "nit,fecha,total,cliente_id,users_id)\n"
                + "VALUES(?,?,?,?,?)";
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // El segundo parametro de usa cuando se tienen tablas que generan llaves primarias
            // es bueno cuando nuestra bd tiene las primarias autoincrementables
            ps.setString(1, this.nit);
            ps.setDate(2, this.fecha);
            ps.setInt(3, this.total);
            ps.setInt(4, this.cliente_id);
            ps.setInt(5, this.users_id);
            int rows = ps.executeUpdate();
            // Cierro Conexion
            this.m_Conexion.cerrarConexion();
            // Obtengo el id generado pra devolverlo
            if (rows != 0) {
                ResultSet generateKeys = ps.getGeneratedKeys();
                if (generateKeys.next()) {
                    id = generateKeys.getInt(1);
                    return id;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1; // NO REGISTRO

    }

    public DefaultTableModel getVenta(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel venta = new DefaultTableModel();
        venta.setColumnIdentifiers(new Object[]{
            "ID", "NIT", "FECHA", "CLIENTE_ID", "VETERINARIO_ID"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT * FROM ventas WHERE id=?";
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
                venta.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nit"),
                    rs.getDate("fecha"),
                    rs.getInt("cliente_id"),
                    rs.getInt("users_id")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return venta;
    }

    public DefaultTableModel getVentas() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel clientes = new DefaultTableModel();
        clientes.setColumnIdentifiers(new Object[]{
            "id", "nit", "fecha", "total", "cliente_id", "users_id"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT * FROM ventas";
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                clientes.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nit"),
                    rs.getDate("fecha"),
                    rs.getInt("total"),
                    rs.getInt("cliente_id"),
                    rs.getInt("users_id"),});
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clientes;
    }

    public void actualizarTotal(int total) {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        PreparedStatement ps = null;
        String query = "UPDATE ventas SET \n"
                + "total = ? \n"
                + "WHERE id = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, total);
            ps.setInt(2, id);
            ps.executeUpdate();
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
            ps = con.prepareStatement("DELETE FROM ventas WHERE id = ?");
            ps.setInt(1, this.id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void modificar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        PreparedStatement ps = null;
        String query = "UPDATE ventas SET \n"
                + "nit = ?,\n"
                + "fecha = ?, \n"
                + "cliente_id = ?, \n"
                + "users_id = ? \n"
                + "WHERE id = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nit);
            ps.setDate(2, fecha);
            ps.setInt(3, cliente_id);
            ps.setInt(4, users_id);
            ps.setInt(5, id);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
