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

/**
 *
 * @author Jorge Luis Urquiza
 */
public class DetalleVenta {

    private int producto_id;
    private int venta_id;
    private int cantidad;

    private Conexion m_conexion;

    public void setDetalleVenta(int producto_id, int cantidad) {
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.venta_id = 0;
    }

    public DetalleVenta() {
        m_conexion = Conexion.getInstancia();
    }

    public DetalleVenta(int producto_id, int cantidad) {
        m_conexion = Conexion.getInstancia();
        this.producto_id = producto_id;
        this.cantidad = cantidad;
    }

    public void setIDVenta(int venta_id) {
        this.venta_id = venta_id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public int getVenta_id() {
        return venta_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void registrar() {
        m_conexion = Conexion.getInstancia();
        this.m_conexion.abrirConexion();
        Connection con = this.m_conexion.getConexion();

        // Preparo la consulta
        PreparedStatement ps = null;
        String query = "INSERT INTO detalles_ventas (venta_id,producto_id,cantidad) values (?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, this.venta_id);
            ps.setInt(2, this.producto_id);
            ps.setInt(3, cantidad);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarDetalleVenta(int id) {

    }

    public int getPrecioProductoxCantidad() {
        // Abro y obtengo la conexion
        this.m_conexion.abrirConexion();
        Connection con = this.m_conexion.getConexion();
        // Preparo la consulta
        String sql = "SELECT precio FROM productos WHERE id=?";
        String price = "1";
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, this.producto_id);
            ResultSet rs = ps.executeQuery();
            // Cierro la conexion
            this.m_conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                price = rs.getString("precio");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Integer.parseInt(price) * this.cantidad;
    }

}
