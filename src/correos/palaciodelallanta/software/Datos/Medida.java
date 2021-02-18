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
public class Medida {

    public int idmedida;
    public String ancho;
    public String alto;
	public String diametro;
    
    public Conexion m_Conexion;

    public void setMedida(int id, String ancho, String alto, String diametro) {
        this.idmedida = id;
        this.ancho = ancho;
        this.alto = alto;
	this.diametro = diametro;
    }

    public void setMedida(String ancho, String alto, String diametro) {
        this.ancho = ancho;
        this.alto = alto;
		this.diametro = diametro;
    }

    public Medida() {
        this.m_Conexion = Conexion.getInstancia();
    }

    public int getIdmedida() {
        return idmedida;
    }

    public void setIdmedida(int id) {
        this.idmedida = id;
    }

    public String getancho() {
        return ancho;
    }

    public void setancho(String ancho) {
        this.ancho = ancho;
    }

    public String getalto() {
        return alto;
    }

    public void setalto(String alto) {
        this.alto = alto;
    }
    public String getdiametro() {
        return diametro;
    }

    public void setdiametro(String diametro) {
        this.diametro = diametro;
    }
     public DefaultTableModel getMedida(int idmedida) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel Medida = new DefaultTableModel();
        Medida.setColumnIdentifiers(new Object[]{
            "idmedida", "ancho", "alto","diametro"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT\n"
                + "medidas.idmedida,\n"
                + "medidas.ancho,\n"
                + "medidas.alto\n"
		+ "medidas.diametro\n"
                + "FROM medidas\n"
                + "WHERE medidas.idmedida=?";
        // Los simbolos de interrogacion son para mandar parametros 
        // a la consulta al momento de ejecutalas

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idmedida);
            ResultSet rs = ps.executeQuery();
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                Medida.addRow(new Object[]{
                    rs.getInt("idmedida"),
                    rs.getString("ancho"),
                    rs.getString("alto"),
                    rs.getString("diametro"),
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Medida;
    }

    public DefaultTableModel getMedidas() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel medidas = new DefaultTableModel();
        medidas.setColumnIdentifiers(new Object[]{
            "idmedida", "ancho", "alto","diametro"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT\n"
                + "medidas.idmedida,\n"
                + "medidas.ancho,\n"
                + "medidas.alto\n"
		+ "medidas.diametro\n"
                + "FROM medidas order by idmedida asc";

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                medidas.addRow(new Object[]{
                    rs.getInt("idmedida"),
                    rs.getString("ancho"),
                    rs.getString("alto")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return medidas;
    }

    public void registrar() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        PreparedStatement ps = null;
        String query = "INSERT INTO medidas (ancho,alto,diametro) values (?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, ancho);
            ps.setString(2, alto);
            ps.setString(3, diametro);

            ps.executeUpdate();
            System.out.println("INGRESANDO AL METODO REGISTRAR DEL MODELO Medida");
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
        String query = "UPDATE medidas SET \n"
                + "ancho = ?,\n"
                + "alto = ? \n"
		+ "diametro = ? \n"
                + "WHERE idmedida = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, this.ancho);
            ps.setString(2, this.alto);
            ps.setString(3, this.diametro);
            ps.setInt(4, this.idmedida);
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
            ps = con.prepareStatement("DELETE FROM medidas WHERE idmedida = ?");
            ps.setInt(1, this.idmedida);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
