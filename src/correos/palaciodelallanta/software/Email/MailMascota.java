/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Email;

import correos.palaciodelallantas.utils.Cadenas;
import correos.palaciodelallantas.utils.Utils;
import correos.palaciodelallantas.utils.Mensaje;
import correos.palaciodelallanta.software.Negocio.MascotaNegocio;
import correos.palaciodelallanta.procesador.Token;
import correos.palaciodelallanta.procesador.Analex;
import correos.palaciodelallanta.protocolos.ClienteSMTP;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class MailMascota {

    MascotaNegocio mascotaNegocio = new MascotaNegocio();

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
        String raza = Utils.quitarComillas(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String color = Utils.quitarComillas(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int tipo = (int) analex.Preanalisis().getAtributo();
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int cliente_id = (int) analex.Preanalisis().getAtributo();
        if (!validarValoresParametros(nombre, raza, color, tipo, cliente_id)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        mascotaNegocio.registrar(nombre, raza, color, tipo, cliente_id);
        ClienteSMTP.sendMail(destinatario, "REGISTRAR MASCOTA", Cadenas.REGISTRO_SUCCESS + Cadenas.MASCOTA_REGISTER_SUCCESS);
    }

    public void modificar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();

        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        DefaultTableModel veterinario = mascotaNegocio.getMascota(id);
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
                // posicion de la fila del defaultTableModel
                : String.valueOf(veterinario.getValueAt(0, 1));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        String raza = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                : String.valueOf(veterinario.getValueAt(0, 2));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        String color = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                // posicion de la fila del defaultTableModel
                : String.valueOf(veterinario.getValueAt(0, 3));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        int tipo = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                : (int) (veterinario.getValueAt(0, 4));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        int cliente_id = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                : (int) (veterinario.getValueAt(0, 5));
        if (!validarValoresParametros(nombre, raza, color, tipo, cliente_id)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        mascotaNegocio.modificar(id, nombre, raza, color, tipo, cliente_id);
        ClienteSMTP.sendMail(destinatario, "MODIFICAR MASCOTA", Cadenas.MODIFICAR_SUCCESS + Cadenas.MASCOTA_MODIFIED_SUCCESS);

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
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.ELIMINAR_FAILED );
            return;
        }
        mascotaNegocio.eliminar(id);
        ClienteSMTP.sendMail(destinatario, "ELIMINAR MASCOTA", Cadenas.ELIMINAR_SUCCESS + Cadenas.MASCOTA_DELETED_SUCCESS );
    }

    public void listar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();

        String Head[] = {"ID", "NOMBRE", "RAZA", "COLOR", "TIPO(1=PERO , 2 = GATO ,3 = OTROS)", "CLIENTE ID"};
        String Cabecera = "ANIMALHELP - LISTA DE MASCOTAS";
        Mensaje message = Utils.dibujarTablaHtml(mascotaNegocio.getMascotas(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println(Cadenas.SUCCESSFULL_MAIL);
        } else {
            System.out.println(Cadenas.FAILED_MAIL);
        }
    }

    //validaciones
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

    private boolean validarValoresParametros(String nombre, String raza, String color, int tipo, int cliente_id) {
        return (!nombre.isEmpty()
                && !raza.isEmpty()
                && !color.isEmpty()
                && !color.isEmpty()
                && cliente_id > 0
                && 1 <= tipo && tipo <= 3);
    }
}
