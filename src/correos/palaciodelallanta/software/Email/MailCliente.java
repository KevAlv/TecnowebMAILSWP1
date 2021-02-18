/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Email;

import correos.palaciodelallantas.utils.Utils;
import correos.palaciodelallantas.utils.Mensaje;
import correos.palaciodelallanta.software.Negocio.ClienteNegocio;
import correos.palaciodelallanta.procesador.Token;
import correos.palaciodelallanta.procesador.Analex;
import correos.palaciodelallanta.protocolos.ClienteSMTP;
import correos.palaciodelallantas.utils.Cadenas;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class MailCliente {

    ClienteNegocio clienteNegocio = new ClienteNegocio();

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
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String apellidos = Utils.quitarComillas(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int ci = Integer.parseInt(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String celular = Utils.quitarComillas(analex.Preanalisis().getToStr());
        if (!validarValoresParametros(nombre, apellidos, ci, celular)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        clienteNegocio.registrar(nombre, apellidos, ci, celular);
        ClienteSMTP.sendMail(destinatario, "REGISTRAR CLIENTE", Cadenas.REGISTRO_SUCCESS + Cadenas.CLIENTE_REGISTER_SUCCESS);
    }

    public void modificar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        DefaultTableModel veterinario = clienteNegocio.getCliente(id);
        // verifica si existe al id
        if (veterinario.getRowCount() == 0) {
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
                : String.valueOf(veterinario.getValueAt(0, 1));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        String apellido = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                : String.valueOf(veterinario.getValueAt(0, 2));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        int ci = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                // posicion de la fila del defaultTableModel
                : Integer.parseInt(String.valueOf(veterinario.getValueAt(0, 3)));
        
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        String celular = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                : String.valueOf(veterinario.getValueAt(0, 4));
        if (!validarValoresParametros(nombre, apellido, ci, celular)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        clienteNegocio.modificar(id, nombre, apellido, ci, celular);
        ClienteSMTP.sendMail(destinatario, "MODIFICAR CLIENTE", Cadenas.MODIFICAR_SUCCESS+ Cadenas.CLIENTE_MODIFIED_SUCCESS);

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
        if (id < 1) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.ELIMINAR_FAILED);
            return ;
        }
        clienteNegocio.eliminar(id);
        ClienteSMTP.sendMail(destinatario, "ELIMINAR CLIENTE", Cadenas.ELIMINAR_SUCCESS + Cadenas.CLIENTE_DELETED_SUCCESS);
    }

    public void listar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();

        String Head[] = {"ID", "NOMBRE", "APELLIDO", "CI", "CELULAR"};
        String Cabecera = "ANIMALHELP - LISTA DE CLIENTES";
        Mensaje message = Utils.dibujarTablaHtml(clienteNegocio.getClientes(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println(Cadenas.SUCCESSFULL_MAIL);
        } else {
            System.out.println(Cadenas.FAILED_MAIL);
        }
    }

   
    private boolean validarValoresParametros(String nombre, String apellidos, int ci, String celular) {
        return (!nombre.isEmpty()
                && !apellidos.isEmpty()
                && !celular.isEmpty()
                && ci > 0);

    }

    private boolean esValidoParametros(Token token, String destinatario, int tipo) {
        //TIPO =1 REGISTRAR ; 2 MODIFICAR
        switch (tipo) {
            //REGISTRAR
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
            //MODIFICAR
            case 2:
                if (!(token.getNombre() == Token.STRING) && !(token.getNombre() == Token.GB)) {
                    ClienteSMTP.sendMail(destinatario, "ERROR DE PARAMETROS", Cadenas.MODIFICAR_FAILED);
                    return false;
                }
                break;
            case 3:
                if (!(token.getNombre() == Token.STRING) && !(token.getNombre() == Token.GB)) {
                    ClienteSMTP.sendMail(destinatario, "ERROR DE PARAMETROS", Cadenas.MODIFICAR_FAILED);
                    return false;
                }
                break;
            //ELIMINAR
            case 4:
                if (!(token.getNombre() == Token.NUM)) {
                    ClienteSMTP.sendMail(destinatario, "ERROR DE PARAMETROS", Cadenas.ELIMINAR_FAILED);
                    return false;
                }
        }
        return true;
    }
}
