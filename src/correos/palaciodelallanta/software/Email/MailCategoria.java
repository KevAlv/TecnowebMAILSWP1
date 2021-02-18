/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Email;

import correos.palaciodelallanta.software.Negocio.CategoriaNegocio;
import correos.palaciodelallanta.protocolos.ClienteSMTP;
import correos.palaciodelallanta.procesador.Analex;
import correos.palaciodelallanta.procesador.Token;
import correos.palaciodelallantas.utils.Cadenas;
import correos.palaciodelallantas.utils.Mensaje;
import correos.palaciodelallantas.utils.Utils;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class MailCategoria {

    private CategoriaNegocio categoriaNegocio;

    public MailCategoria() {
        this.categoriaNegocio = new CategoriaNegocio();
    }

    public void registrar(Analex analex, String destinatario) throws Exception {
        // Obtengo el Siguiente token
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String nombre = Utils.quitarComillas(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        //se espera un STRING
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String descripcion = Utils.quitarComillas(analex.Preanalisis().getToStr());
        if (!validarValoresParametros(nombre, descripcion)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        categoriaNegocio.registrar(nombre, descripcion);
        ClienteSMTP.sendMail(destinatario, "REGISTRAR CATEGORIA", Cadenas.REGISTRO_SUCCESS+Cadenas.CATEGORIA_REGISTER_SUCCESS);

    }

    public void modificar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        DefaultTableModel categoria = categoriaNegocio.getCategoria(id);
        // verifica si existe al id
        if (categoria.getRowCount() == 0) {
            ClienteSMTP.sendMail(destinatario, Cadenas.NO_EXISTS_ID, Cadenas.NO_EXISTS_ID);
            return;
        }
        // Revisar los GuionBajo
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        //tipo=> registrar = 1 , modificar = 2 , eliminar = 2
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        String nombre = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                // posicion de la fila del defaultTableModel
                : String.valueOf(categoria.getValueAt(0, 1));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        //tipo=> registrar = 1 , modificar = 2 , eliminar = 2
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        String descripcion = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                : String.valueOf(categoria.getValueAt(0, 2));
        if (!validarValoresParametros(nombre, descripcion)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        categoriaNegocio.modificar(id, nombre, descripcion);
        ClienteSMTP.sendMail(destinatario, "MODIFICAR CATEGORIA", Cadenas.MODIFICAR_SUCCESS + Cadenas.CATEGORIA_MODIFIED_SUCCESS);
    }

    public void eliminar(Analex analex, String destinatario) throws Exception {
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        //validar si el TOKEN ES NUM
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        //valido si el num > 0 (no existe id < 1)
        if (id < 1) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.ELIMINAR_FAILED);
            return;
        }
        categoriaNegocio.eliminar(id);
        
        ClienteSMTP.sendMail(destinatario, "ELIMINAR CATEGORIA", Cadenas.ELIMINAR_SUCCESS+ Cadenas.CATEGORIA_DELETED_SUCCESS);
    }

    public void listar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda

        String Head[] = {"ID", "NOMBRE", "DESCRIPCION"};
        String Cabecera = "ANIMALHELP - LISTA DE CATEGORIAS";
        // Mensaje message = Utils.dibujarTabla2(usuarioNegocio.obtenerUsuarios(), Head, Cabecera);
        Mensaje message = Utils.dibujarTablaHtml(categoriaNegocio.getCategorias(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println("Envio Correo");
        } else {
            System.out.println("No envio Correo");
        }
    }

    private boolean validarValoresParametros(String nombre, String descripcion) {
        return (!nombre.isEmpty()
                && !descripcion.isEmpty());
    }

    private boolean esValidoParametros(Token token, String destinatario, int tipo) {

        switch (tipo) {
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
                if (!(token.getNombre() == Token.NUM)) {
                    ClienteSMTP.sendMail(destinatario, "ERROR DE PARAMETROS", Cadenas.ELIMINAR_FAILED);
                    return false;
                }
        }
        return true;

    }
}
