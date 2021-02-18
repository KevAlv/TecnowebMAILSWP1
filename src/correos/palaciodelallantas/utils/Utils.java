/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallantas.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class Utils {

    public static Date convertirFechas(String fecha) {
        // Formato de fecha a ingresar dd-MM-yyyy
        Date fechaNueva = null;
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            java.util.Date fechaJava = formato.parse(fecha);
            fechaNueva = new Date(fechaJava.getTime());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return fechaNueva;
    }

    public static String getDestinatario(String contenido) {
        String destinatario = "";
        // Dividir en lineas
        String[] lines = contenido.split("\n");
        //System.out.println("Linea 2 : "+lines[2]);
        if (lines[2].contains("Return-Path:")) {
            int inicio = lines[2].indexOf("<");
            int fin = lines[2].indexOf(">");
            String s = lines[2].substring(inicio + 1, fin);
            //System.out.println("Linea 2 : "+s);
            if (s.contains(".com")) {
                return s;
            }
            return s;
        }
        int index = -1;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > 5
                    && lines[i].substring(0, 5).toUpperCase().equals("FROM:")) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            // Quitar la palabra 'From: '
            destinatario = lines[index].substring(6);
            lines = destinatario.split(" ");
            if (lines.length == 1) { // Correo del Server
                destinatario = lines[0];
            } else { // Desde otro Servidor de Correo
                destinatario = lines[lines.length - 1];
                destinatario = destinatario.split("<")[1].split(">")[0];
            }
        }
        return destinatario;
    }

    public static String getSubjectOrden(String contenido) {
        String orden = "";
        // Dividir en lineas
        String[] lines = contenido.split("\n");
        int index = -1;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > 9
                    && lines[i].substring(0, 9).toUpperCase().equals("SUBJECT: ")) {
                index = i;
                break;
            }
            if (lines[i].length() > 14
                    && lines[i].substring(0, 14).toUpperCase().equals("SUBJECT: FWD: ")) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            // Quitar la palabra 'Subject: '
            orden = lines[index].substring(9) + lines[index + 1].substring(0) + lines[index + 2].substring(0) + lines[index + 3].substring(0);
            //System.out.println("Mensaje : "+orden);
            int i = orden.indexOf("Thread-Topic");
            if (i == -1) {
                i = orden.indexOf("To");
            }
            if (i == -1) {
                i = orden.indexOf("MIME-Version:");
            }
            if (i != -1) {
                orden = orden.substring(0, i);
            }
        }
        return orden;
    }

    public static Mensaje dibujarMenuAyuda() {
        //System.out.println("|-------------------------------------------------------------");
        String Cabecera = "MENÚ DE AYUDA- ANIMAL HELP";
        LinkedList<String> Head = new LinkedList<>(Arrays.asList(
                "CASO DE USO",
                "DETALLES Y USO",
                "EJEMPLO"
        ));
        LinkedList<String> CasosDeUso = new LinkedList<>(Arrays.asList(
                //VETERINARIA ANIMAL HELP

                //CU1 Gestionar Cliente
                "REGISTRARCLIENTE",
                "MODIFICARCLIENTE",
                "OBTENERCLIENTES",
                "ELIMINARCLIENTE",
                //CU2 Gestionar Mascota
                "REGISTRARMASCOTA",
                "MODIFICARMASCOTA",
                "OBTENERMASCOTAS",
                "ELIMINARMASCOTA",
                ///CU3 Gestionar Veterinario
                "REGISTRARVETERINARIO",
                "OBTENERVETERINARIO",
                "MODIFICARVETERINARIO",
                "ELIMINARVETERINARIO",
                ///CU4 Gestionar Categoria
                "REGISTRARCATEGORIA",
                "OBTENERCATEGORIA",
                "MODIFICARCATEGORIA",
                "ELIMINARCATEGORIA",
                ///CU5 Gestionar Producto
                "REGISTRARPRODUCTO",
                "MODIFICARPRODUCTO",
                "OBTENERPRODUCTOS",
                "ELIMINARPRODUCTO",
                //CU6 Gestionar Venta
                "REGISTRARVENTA",
                "MODIFICARVENTA",
                "OBTENERVENTAS",
                "ELIMINARVENTA",
                //CU7 Gestionar atencion
                "REGISTRARATENCION",
                "MODIFICARATENCION",
                "OBTENERATENCIONES",
                "ELIMINARATENCION",
                //CU8 REPORTES
                "VENTASMENSUALES",
                "TOPTRESCLIENTESCOMPRAS",
                "TOPTRESMASCOTASMASATENDIDAS",
                "TOPTRESPRODUCTOSMASVENDIDOS",
                "VENTASTOTALDEHOY",
                "TORTAPORCENTAJEPORANIMAL"
        ));
        LinkedList<String> Detalles = new LinkedList<>(Arrays.asList(
                //veterinaria animal help

                //CU1 Gestionar Cliente
                "REGISTRARCLIENTE[\"Nombre\"][\"Apellidos\"][Cedula Identidad][\"Celular\"]",
                "MODIFICARCLIENTE[ID Cliente][\"Nombre\"][\"Apellidos\"][Cedula Identidad][\"Celular\"]" + Cadenas.GUION_BAJO,
                "OBTENERCLIENTES",
                "ELIMINARCLIENTE[ID Cliente]",
                //CU2 Gestionar Mascota
                "REGISTRARMASCOTA[\"Nombre\"][\"Raza\"][\"Color\"][Tipo de mascota (1, 2 ,3 | 1=Perro 2 = Gato | 3 = otros (cualquier otro tipo de mascota )] [id del amo (cliente)]",
                "MODIFICARMASCOTA[id de mascota][\"Nombre\"][\"Raza\"][\"Color\"][Tipo de mascota (1, 2 ,3 | 1=Perro 2 = Gato  | 3 = otros (cualquier otro tipo de mascota ))] [id del amo (cliente)]" + Cadenas.GUION_BAJO,
                "OBTENERMASCOTAS",
                "ELIMINARMASCOTA[id de la mascota]",
                ///CU3 Gestionar Veterinario
                "REGISTRARVETERINARIO[\"Nombre\"][\"Apellidos\"][Cedula Identidad][\"Celular\"][\"Direcciopn\"]",
                "OBTENERVETERINARIOS",
                "MODIFICARVETERINARIO[id de veterinario][\"Nombre\"][\"Descripcion\"]" + Cadenas.GUION_BAJO,
                "ELIMINARVETERINARIO[id veterinario]",
                ///CU4 Gestionar Categoria
                "REGISTRARCATEGORIA[\"Nombre\"][\"Descripcion\"]",
                "OBTENERCATEGORIAS",
                "MODIFICARCATEGORIA[\"ID categoria\"][\"Nombre Categoria\"][\"Descripcion\"]" + Cadenas.GUION_BAJO,
                "ELIMINARCATEGORIA[\"ID categoria\"]",
                ///CU5 Gestionar Producto
                "REGISTRARPRODUCTO[\"Nombre\"][Precio][ID  de la Categoria]",
                "OBTENERPRODUCTOS",
                "MODIFICARPRODUCTO[ID Producto][\"Nombre\"][Precio][ID Categoria]" + Cadenas.GUION_BAJO,
                "ELIMINARPRODUCTO[ID Producto] ",
                //CU6 Gestionar Venta
                "REGISTRARVENTA[\"NIT\"][\"Fecha(dia-mes-año)\"][id cliente][id veterinario](despues del \"id veterinario\" vienen los detalles de la compra siempre en pares [id producto][cantidad][id producto][cantidad].....etc. importante nunca repetir el id del producto porque el mismo producto no puede estar detallado 2 veces en la misma venta) ej. asi: [2][1][1][5][3][4]",
                "MODIFICARVENTA[ID Venta][\"NIT\"][\"Fecha(dia-mes-año\"][id cliente][id veterinario]" + Cadenas.GUION_BAJO,
                "OBTENERVENTAS",
                "ELIMINARVENTA[15]",
                //CU7 Gestionar Atencion
                "REGISTRARATENCION[\"Fecha\"][\"Descripcion\"][\"Diagnostico\"][id cliente][id veterinario][id mascota]",
                "MODIFICARATENCION[ID atencion[[\"03-08-2020\"][\"Descripcion\"][\"Diagnostico medico\"][id cliente][id veterinario][id mascota]" + Cadenas.GUION_BAJO,
                "OBTENERATENCIONES",
                "ELIMINARATENCION[ID de la atención])",
                //C8 REPORTES
                "VENTASMENSUALES(Ventas totales por mes)",
                "TOP3CLIENTESCOMPRAS (Los 3 clientes que mas compras realizaron)",
                "TOP3MASCOTASATENDIDAS(Las mascotas que mas veces fueron atendidas)",
                "TOP3PRODUCTOSVENDIDOS(Los 3 productos mas vendidos)",
                "VENTASTOTALDEHOY (Todas las ventas realizadas hoy)",
                "TORTAPORCENTAJEANIMAL(TORTA GRAFICA CON % DE ANIMALES ATENDIDOS POR TIPO (PERROS Y GATOS))"
        ));
        LinkedList<String> Ejemplos = new LinkedList<>(Arrays.asList(
                //Veterinaria
                //CU1 Gestionar Cliente
                "REGISTRARCLIENTE[\"Jhasmany\"][\"Campos Aguilar\"][1555][\"+59175575745\"]",
                "MODIFICARCLIENTE[7][_][_][_][\"+59178036436\"]<br>",
                "OBTENERCLIENTES",
                "ELIMINARCLIENTE[7]",
                ///CU2 Gestionar Mascota
                "REGISTRARMASCOTA[\"Sony\"][\"chihuahua\"][\"Negro\"][1][2]",
                "MODIFICARMASCOTA[4][\"Sonic\"][_][_][_][_]",
                "OBTENERMASCOTAS",
                "ELIMINARMASCOTA[4]",
                //CU3 Gestionar Veterinario
                "REGISTRARVETERINARIO[\"Luciano\"][\"Aguierre Balboa\"][12530][\"+591700365436\"][\"Calle Guabira#03\"]",
                "OBTENERVETERINARIOS",
                "MODIFICARVETERINARIO[1][_][_][853651][\"+591 75575746\"][\"Calle Santa Cruz#1113\"]",
                "ELIMINARVETERINARIO[2]",
                ///CU4 Gestionar Categoria
                "REGISTRARCATEGORIA[\"Limpieza\"][\"Productos de aseo para las mascotas\"]",
                "OBTENERCATEGORIAS",
                "MODIFICARCATEGORIA[11][_][\" Limpieza para mascotas\"]",
                "ELIMINARCATEGORIA[10]",
                ///CU5 Gestionar Porducto
                "REGISTRARPRODUCTO[\"Golfo\"][15][11]",
                "OBTENERPRODUCTOS",
                "MODIFICARPRODUCTO[5][\"Golfo ++\"][_][_]",
                "ELIMINARPRODUCTO[5]",
                //CU6 Gestionar Venta
                "REGISTRARVENTA[\"21461\"][\"02-08-2020\"][1][1][2][1][1][5][3][4]",
                "MODIFICARVENTA[8][_][\"10-02-2019\"][_][_]",
                "OBTENERVENTAS",
                "ELIMINARVENTA[10]",
                //CU7 Gestionar Atencion
                "REGISTRARATENCION[\"02-08-2020\"][\"el animal tenia moquillo\"][\"darle vitaminas C\"][1][1][3]",
                "MODIFICARATENCION[28][_][_][\"darle vitaminas c y b\"][_][_][_]",
                "OBTENERATENCIONES",
                "ELIMINARATENCION[28]",
                //CU8 REPORTES
                "VENTASMENSUALES",
                "TOPTRESCLIENTESCOMPRAS",
                "TOPTRESMASCOTASATENDIDAS",
                "TOPTRESPRODUCTOSVENDIDOS",
                "VENTASTOTALDEHOY",
                "TORTAPORCENTAJEANIMAL"
        ));
        String data = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "h1 {\n"
                + "    color: black;\n"
                + "}\n"
                + "h2,img {\n"
                + "color: red;\n"
                + "    display: inline-block;\n"
                + "}\n"
                + "img{"
                + "    float: right;\n"
                + "    vertical-align: top;\n"
                + "    margin-bottom: 0.45em;"
                + "}\n"
                + "table {\n"
                + "    border-collapse: collapse;\n"
                //+ "    border: green 5px solid;"
                + "    width: 100%;\n"
                + "}\n"
                + "\n"
                + "th, td {\n"
                + "    border: 2px solid #53ADA9;"
                + "    text-align: center;\n"
                + "    padding: 10px;\n"
                + "}\n"
                + "h1{\n"
                + "     text-align: center;"
                + "}\n"
                + "\n"
                + "tr:nth-child(even){background-color: #075C6D }\n"
                + "\n"
                + "th {\n"
                + "    background-color: #399393;\n"
                + "    color: white;\n"
                + "}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>" + Cabecera + "</h1> \n"
                + "<h2>NOTA: IMPORTANTE LEER LA LISTA DE INSTRUCCIONES Y RECOMENDACIONES</h2> \n"
                + "<ul>\n"
                + "<li><strong>En los MODIFICAR usar \"_\" para mantener los valores anteriores de la fila, es decir los campos que no se quieren modificar.</strong></li>\n"
                + "<li><strong>REGISTRAR VENTA: desdes del \"[id veterinario]\" agregar 2 campos por lo menos si o si (en pares) [id producto][cantidad] se puede agregar N productos pero siempre producto y cantidad como se hace en un carrito de compra.</strong></li>\n"
                + "<li><strong>Prohibido usar caracteres especiales</strong></li>\n"
                + "<li><strong>Reemplazar la letra \"ñ\"  por \"nh\"</strong></li>\n"
                + "<li><strong>Utilizar solo la lista comandos disponibles para poder realizar alguna acción</strong></li>\n"
                + "</ul>\n"
                + "<table class=\"w3-table-all\">\n";
        data = data + "<tr>\n";
        for (int i = 0; i < Head.size(); i++) {
            data = data + "<th>" + Head.get(i) + "</th>\n";
        }
        data = data + "</tr>\n";
        // Agregando Content
        for (int i = 0; i < CasosDeUso.size(); i++) {
            data = data + "<tr>";
            data = data + "<td><strong>" + CasosDeUso.get(i) + "</strong></td>";
            data = data + "<td><strong>" + Detalles.get(i) + "</strong></td>";
            data = data + "<td>" + Ejemplos.get(i) + "</td>";
            data = data + "</tr>\n";
        }
        data += "</table>\n"
                + "</body>\n"
                + "</html>\n";
        Mensaje mensaje = new Mensaje(Cabecera, data);
        return mensaje;
    }

    public static String quitarComillas(String texto) {
        int len = texto.length() - 1;
        return texto.substring(1, len);
    }

    public static Mensaje dibujarTablaHtml(DefaultTableModel tabla, String[] Head, String Cabecera) {
        ArrayList<String> headers = new ArrayList<>();
        ArrayList<List<String>> rowList = new ArrayList<>();
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            headers.add(tabla.getColumnName(i));
        }
        String data = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "h1 {\n"
                + "    color: black;\n"
                + "}\n"
                + "h2,img {\n"
                + "    display: inline-block;\n"
                + "}\n"
                + "img{"
                + "    float: right;\n"
                + "    vertical-align: top;\n"
                + "    margin-bottom: 0.45em;"
                + "}\n"
                + "table {\n"
                + "    border-collapse: collapse;\n"
                + "    width: 100%;\n"
                + "}\n"
                + "\n"
                + "th, td {\n"
                + "    border: 1px solid #53ADA9;"
                + "    text-align: center;\n"
                + "    padding: 10px;\n"
                + "}\n"
                + "\n"
                + "tr:nth-child(even){background-color: #f2f2f2}\n"
                + "\n"
                + "th {\n"
                + "    background-color: #399393;\n"
                + "    color: white;\n"
                + "}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1 align=\"center\">" + Cabecera + "</h1> \n"
                + "<table class=\"w3-table-all\">\n"
                + "<tr>\n";
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            data = data + "<th>" + Head[i] + "</th>\n";
        }
        data = data + "</tr>\n";
        // Agregando Content
        for (int i = 0; i < tabla.getRowCount(); i++) {
            ArrayList<String> row = new ArrayList<>();
            data = data + "<tr>";
            for (int j = 0; j < tabla.getColumnCount(); j++) {
                row.add(String.valueOf(tabla.getValueAt(i, j)));
                data = data + "<td><strong>" + row.get(j) + "</strong></td>";
            }
            data = data + "</tr>\n";
            rowList.add(row.subList(0, row.size()));
            //System.out.println("+-------------------------------------------------------------");
        }
        data += "</table>\n"
                + "</body>\n"
                + "</html>\n";

        if (rowList.size() < 1) {
            data = "Tabla Vacia";
        }
        Mensaje mensaje = new Mensaje(Cabecera, data);
        return mensaje;
    }

}
