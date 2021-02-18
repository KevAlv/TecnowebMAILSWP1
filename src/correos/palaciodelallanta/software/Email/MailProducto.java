/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Email;

import correos.palaciodelallantas.utils.Cadenas;
import correos.palaciodelallantas.utils.Utils;
import correos.palaciodelallantas.utils.Mensaje;
import correos.palaciodelallanta.software.Negocio.ProductoNegocio;
import correos.palaciodelallanta.procesador.Token;
import correos.palaciodelallanta.procesador.Analex;
import correos.palaciodelallanta.protocolos.ClienteSMTP;
import javax.swing.table.DefaultTableModel;

/**
 * @author Jorge Luis Urquiza
 */
public class MailProducto {

    ProductoNegocio productoNegocio = new ProductoNegocio();

    public void registrar(Analex analex, String destinatario) throws Exception {
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();

        analex.Avanzar();
        // Atributos
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String nombre = Utils.quitarComillas(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int precio = Integer.parseInt(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int categoria_id = Integer.parseInt(analex.Preanalisis().getToStr());
        if (!validarValoresParametros(nombre, precio, categoria_id)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        productoNegocio.registrar(nombre, precio, categoria_id);
        ClienteSMTP.sendMail(destinatario, "REGISTRAR PRODUCTO", Cadenas.REGISTRO_SUCCESS + Cadenas.PRODUCTO_REGISTER_SUCCESS);
    }

    public void modificar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();

        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        DefaultTableModel producto = productoNegocio.getProducto(id);
        // verifica si existe al id
        if (producto.getRowCount() == 0) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_ID, Cadenas.NO_EXISTS_ID);
            return;
        }
        // Revisar los GuionBajo
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        String nombre = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                // posicion de la fila del defaultTableModel
                : String.valueOf(producto.getValueAt(0, 1));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        int precio = (analex.Preanalisis().getNombre() != Token.GB)
                ? Integer.parseInt(analex.Preanalisis().getToStr())
                : (int) (producto.getValueAt(0, 2));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        int categoria_id = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                // posicion de la fila del defaultTableModel
                : (int) producto.getValueAt(0, 3);

        if (!validarValoresParametros(nombre, precio, categoria_id)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        productoNegocio.modificar(id, nombre, precio, categoria_id);
        ClienteSMTP.sendMail(destinatario, "MODIFICAR PRODUCTO", Cadenas.MODIFICAR_SUCCESS + Cadenas.PRODUCTO_MODIFIED_SUCCESS);

    }

    public void eliminar(Analex analex, String destinatario) throws Exception {
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();

        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 4)) {
            return;
        }
        //valido si el num > 0 (no existe id < 0)
        if (id < 1) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.ELIMINAR_FAILED);
            return ;
        }
        productoNegocio.eliminar(id);
        ClienteSMTP.sendMail(destinatario, "ELIMINAR PRODUCTO", Cadenas.ELIMINAR_SUCCESS + Cadenas.PRODUCTO_DELETED_SUCCESS);
    }

    public void listar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();
        String Head[] = {"ID", "NOMBRE", "PRECIO (BS.)", "CATEGORIA ID"};
        String Cabecera = "VETERINARIA ANIMALHELP - LISTA DE PRODUCTOS";
        Mensaje message = Utils.dibujarTablaHtml(productoNegocio.getProductos(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println(Cadenas.SUCCESSFULL_MAIL);
        } else {
            System.out.println(Cadenas.FAILED_MAIL);
        }
    }

    private boolean esValidoParametros(Token token, String destinatario, int tipo) {
        //TIPO =1 REGISTRAR ; 2 MODIFICAR
        switch (tipo) {
            case 0:
                if (!(token.getNombre() == Token.NUM)) {
                    ClienteSMTP.sendMail(destinatario, "ERROR DE PARAMETROS", Cadenas.REGISTRO_FAILED);
                    return false;
                }
                break;
            case 1:
                if (!(token.getNombre() == Token.STRING)) {
                    ClienteSMTP.sendMail(destinatario, "ERROR DE PARAMETROS", Cadenas.REGISTRO_FAILED);
                    return false;
                }
                break;
            case 2:
                if (!(token.getNombre() == Token.STRING) && !(token.getNombre() == Token.GB)) {
                    ClienteSMTP.sendMail(destinatario, "ERROR DE PARAMETROS", Cadenas.MODIFICAR_FAILED);
                    return false;
                }
                break;

            case 3:
                if (!(token.getNombre() == Token.NUM) && !(token.getNombre() == Token.GB)) {
                    ClienteSMTP.sendMail(destinatario, "ERROR DE PARAMETROS", Cadenas.MODIFICAR_FAILED);
                    return false;
                }
                break;
            case 4:
                if (!(token.getNombre() == Token.NUM)) {
                    ClienteSMTP.sendMail(destinatario, "ERROR DE PARAMETROS", Cadenas.ELIMINAR_FAILED);
                    return false;
                }
        }
        return true;

    }

    private boolean validarValoresParametros(String nombre, int precio, int categoria_id) {
        return (!nombre.isEmpty()
                && precio > 0
                && categoria_id > 0);

    }
}
