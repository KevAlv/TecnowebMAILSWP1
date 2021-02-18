/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Email;

import correos.palaciodelallantas.utils.Cadenas;
import correos.palaciodelallantas.utils.Utils;
import correos.palaciodelallantas.utils.Mensaje;
import correos.palaciodelallanta.software.Negocio.AtencionNegocio;
import correos.palaciodelallanta.procesador.Token;
import correos.palaciodelallanta.procesador.Analex;
import correos.palaciodelallanta.protocolos.ClienteSMTP;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class MailAtencion {

    AtencionNegocio atencionNegocio = new AtencionNegocio();

    public void registrar(Analex analex, String destinatario) throws Exception {
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Sino, ejecutar el comando
        analex.Avanzar();
        // Atributos
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String fecha = Utils.quitarComillas(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String descripcion = Utils.quitarComillas(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String diagnostico = Utils.quitarComillas(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int cliente_id = Integer.parseInt(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int veterinario_id = Integer.parseInt(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int mascota_id = Integer.parseInt(analex.Preanalisis().getToStr());
        if (!validarValoresParametros(fecha, descripcion, diagnostico, cliente_id, veterinario_id, mascota_id)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        atencionNegocio.registrar(fecha, descripcion, diagnostico, cliente_id, veterinario_id, mascota_id);
        ClienteSMTP.sendMail(destinatario, "REGISTRAR ATENCION", Cadenas.REGISTRO_SUCCESS + Cadenas.ATENCION_REGISTER_SUCCESS);
    }

    public void modificar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Sino, ejecutar el comando
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        DefaultTableModel atencion = atencionNegocio.getAtencion(id);
        // verifica si existe al id
        if (atencion.getRowCount() == 0) {
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
        String fecha = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                // posicion de la fila del defaultTableModel
                : String.valueOf(atencion.getValueAt(0, 1));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        String descripcion = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                : String.valueOf(atencion.getValueAt(0, 2));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        String diagnostico = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                // posicion de la fila del defaultTableModel
                : String.valueOf(atencion.getValueAt(0, 3));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        int cliente_id = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                : Integer.parseInt(String.valueOf(atencion.getValueAt(0, 4)));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        int veterinario_id = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                : Integer.parseInt(String.valueOf(atencion.getValueAt(0, 5)));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        int mascota_id = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                : Integer.parseInt(String.valueOf(atencion.getValueAt(0, 6)));
        if (!validarValoresParametros(fecha, descripcion, diagnostico, cliente_id, veterinario_id, mascota_id)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        atencionNegocio.modificar(id, fecha, descripcion, diagnostico, cliente_id, veterinario_id, mascota_id);
        ClienteSMTP.sendMail(destinatario, "MODIFICAR ATENCION", Cadenas.MODIFICAR_SUCCESS + Cadenas.ATENCION_MODIFIED_SUCCESS);

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
        //valido si el num > 0 (no existe id < 1)
        if (id < 1) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.ELIMINAR_FAILED);
            return;
        }
        atencionNegocio.eliminar(id);
        ClienteSMTP.sendMail(destinatario, "ELIMINAR ATENCION", Cadenas.ELIMINAR_SUCCESS + Cadenas.ATENCION_DELETED_SUCCESS);
    }

    public void listar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();
        String Head[] = {"ID", "FECHA", "DESCRIPCION", "DIAGNOSTICO", "CIENTE ID", "VETERINARIO ID", "MASCOTA ID"};
        String Cabecera = "ANIMALHELP - LISTA DE ATENCIONES";
        Mensaje message = Utils.dibujarTablaHtml(atencionNegocio.getAtenciones(), Head, Cabecera);
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

    private boolean validarValoresParametros(String fecha, String descripcion, String diagnostico, int cliente_id, int veterinario_id, int mascota_id) {
        return (!fecha.isEmpty()
                && !descripcion.isEmpty()
                && !diagnostico.isEmpty()
                && cliente_id > 0 && veterinario_id > 0 && mascota_id > 0);
    }

}
