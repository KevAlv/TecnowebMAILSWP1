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
public class Reporte {

    private Conexion m_Conexion;

    public Reporte() {
        m_Conexion = Conexion.getInstancia();
    }

    public DefaultTableModel ventasMensuales() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel ventas = new DefaultTableModel();
        ventas.setColumnIdentifiers(new Object[]{
            "AÑO", "MES", "PRECIOTOTALVENTA", "CANTIDADVENTA", "PROMEDIOVENTA"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT CAST (extract (year from fecha) as INTEGER) as anho,\n"
                + "to_char(fecha, 'Month') as mes,\n"
                + "CAST (sum(total ) AS INTEGER),\n"
                + "CAST (count(total ) AS INTEGER),\n"
                + "CAST (avg(Cast(total as float)) as INTEGER)"
                + "from ventas\n"
                + "group by 1,2\n"
                + "order by anho DESC";
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                ventas.addRow(new Object[]{
                    rs.getInt("anho"),
                    rs.getString("mes"),
                    rs.getInt("sum"),
                    rs.getInt("count"),
                    rs.getInt("avg"),});
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ventas;
    }

    public DefaultTableModel top3ClientesCompras() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel clientes = new DefaultTableModel();
        clientes.setColumnIdentifiers(new Object[]{
            "NOMBRE", "APELLIDOS", "CI", "CELULAR", "CANTIDAD-COMPRAS"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT nombre, apellido, ci, celular, count (*) compras\n"
                + "FROM clientes\n"
                + "INNER JOIN ventas ON ventas.cliente_id = clientes.id\n"
                + "group by nombre, apellido, ci, celular\n"
                + "order by compras DESC\n"
                + "LIMIT 3";
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
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("ci"),
                    rs.getString("celular"),
                    rs.getInt("compras"),});
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clientes;
    }

    public DefaultTableModel top3MascotasAtendidas() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel mascotas = new DefaultTableModel();
        mascotas.setColumnIdentifiers(new Object[]{
            "ID", "NOMBRE", "RAZA", "DUEÑO", "APELLIDO", "CANTIDAD"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        // Preparo la consulta
        String sql = "SELECT mascotas.id, mascotas.nombre, raza, clientes.nombre as dueño, clientes.apellido as apellido, count (*) cantidad\n"
                + "FROM mascotas\n"
                + "INNER JOIN atenciones ON atenciones.mascota_id = mascotas.id\n"
                + "INNER JOIN clientes ON clientes.id = mascotas.cliente_id\n"
                + "group by mascotas.id ,mascotas.nombre, raza, dueño,apellido\n"
                + "order by cantidad DESC\n"
                + "LIMIT 3";
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
                    rs.getString("dueño"),
                    rs.getString("apellido"),
                    rs.getInt("cantidad"),});
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mascotas;
    }

    public DefaultTableModel top3ProductosVendidos() {
        DefaultTableModel productos = new DefaultTableModel();
        productos.setColumnIdentifiers(new Object[]{
            "ID", "NOMBRE", "PRECIO", "CATEGORIA", "CANTIDAD"
        });
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        String sql = "SELECT productos.id,productos.nombre,precio,categorias.nombre as categoria, count (*) cantidad\n"
                + "FROM productos\n"
                + "INNER JOIN detalles_ventas ON detalles_ventas.producto_id = productos.id\n"
                + "INNER JOIN categorias ON categorias.id = productos.categoria_id\n"
                + "group by productos.id,productos.nombre,precio, categoria\n"
                + "order by cantidad DESC\n"
                + "LIMIT 3";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                productos.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("precio"),
                    rs.getString("categoria"),
                    rs.getInt("cantidad"),});
            }
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return productos;
    }

    public DefaultTableModel ventasTotalDeHoy() {
        DefaultTableModel ventas = new DefaultTableModel();
        ventas.setColumnIdentifiers(new Object[]{
            "ID", "NIT", "FECHA", "TOTAL", "CLIENTE_ID", "VETERINARIO_ID"
        });

        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        String sql = "select * from ventas where fecha=current_date";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                ventas.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nit"),
                    rs.getDate("fecha"),
                    rs.getInt("total"),
                    rs.getInt("cliente_id"),
                    rs.getInt("veterinario_id")
                });
            }
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ventas;
    }

    public double getPorcentajePerros() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        String sql = "select CAST ( ((select count(*) from mascotas where tipo = 1) * 100) / \n"
                + "(select count(*) from mascotas)as  DOUBLE PRECISION) as total";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                return rs.getDouble("total");
            }
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    public double getPorcentajeGatos() {
      this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        String sql = "select CAST ( ((select count(*) from mascotas where tipo = 2) * 100) / \n"
                + "(select count(*) from mascotas)as  DOUBLE PRECISION) as total";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                return rs.getDouble("total");
            }
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
  public double getPorcentajeOtros() {
      this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        String sql = "select CAST ( ((select count(*) from mascotas where tipo = 3) * 100) / \n"
                + "(select count(*) from mascotas)as  DOUBLE PRECISION) as total";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                return rs.getDouble("total");
            }
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

}
