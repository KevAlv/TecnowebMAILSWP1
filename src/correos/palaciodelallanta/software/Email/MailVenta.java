/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software.Email;

import correos.palaciodelallantas.utils.Cadenas;
import correos.palaciodelallantas.utils.Utils;
import correos.palaciodelallantas.utils.Mensaje;
import correos.palaciodelallanta.software.Negocio.VentaNegocio;
import correos.palaciodelallanta.software.Datos.DetalleVenta;
import correos.palaciodelallanta.procesador.Analex;
import correos.palaciodelallanta.protocolos.ClienteSMTP;
import correos.palaciodelallanta.procesador.Token;
import java.sql.Date;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */
public class MailVenta {

    VentaNegocio ventaNegocio = new VentaNegocio();

    public void registrar(Analex analex, String destinatario) throws Exception {
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        analex.Avanzar();
        // Atributos
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        String nit = Utils.quitarComillas(analex.Preanalisis().getToStr());
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 1)) {
            return;
        }
        Date fecha = Utils.convertirFechas(Utils.quitarComillas(analex.Preanalisis().getToStr()));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int cliente_id = (int) analex.Preanalisis().getAtributo();
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
            return;
        }
        int veterinario_id = (int) analex.Preanalisis().getAtributo();
        if (!validarValoresParametros(nit, cliente_id, veterinario_id)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }

        analex.Avanzar();
        analex.Avanzar();
        token = analex.Preanalisis();
        LinkedList<DetalleVenta> lista = new LinkedList<>();
        while (!isEOF(token)) {
            analex.Avanzar();
            if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
                return;
            }
            int producto_id = (int) analex.Preanalisis().getAtributo();
            analex.Avanzar();
            analex.Avanzar();
            analex.Avanzar();
            if (!esValidoParametros(analex.Preanalisis(), destinatario, 0)) {
                return;
            }
            int cantidad = (int) analex.Preanalisis().getAtributo();
            analex.Avanzar();
            analex.Avanzar();
            lista.add(new DetalleVenta(producto_id, cantidad));
            token = analex.Preanalisis();
        }
        ventaNegocio.registrar(nit, fecha, cliente_id, veterinario_id, lista);
        ClienteSMTP.sendMail(destinatario, "REGISTRAR VENTA", Cadenas.REGISTRO_SUCCESS+Cadenas.VENTA_REGISTER_SUCCESS);

    }

    public void modificar(Analex analex, String destinatario) throws Exception {
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        DefaultTableModel venta = ventaNegocio.getVenta(id);
        if (venta.getRowCount() == 0) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_ID, Cadenas.NO_EXISTS_ID);
            return;
        }
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        System.out.println("NIT");
        String nit = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                : String.valueOf(venta.getValueAt(0, 1));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 2)) {
            return;
        }
        System.out.println("date");
        Date fecha = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.convertirFechas(Utils.quitarComillas(analex.Preanalisis().getToStr()))
                : ((Date) venta.getValueAt(0, 2));
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        System.out.println("cliente ");
        int cliente_id = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                : (int) (venta.getValueAt(0, 3));

        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 3)) {
            return;
        }
        System.out.println("veterinario ");
        int veterinario_id = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                : (int) (venta.getValueAt(0, 4));
        if (!validarValoresParametros(nit, cliente_id, veterinario_id)) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.LONGITUD_FAILED);
            return;
        }
        System.out.println("todo");
        ventaNegocio.modificar(id, nit, fecha, cliente_id, veterinario_id);
        ClienteSMTP.sendMail(destinatario, "MODIFICAR VENTA", Cadenas.MODIFICAR_SUCCESS+Cadenas.VENTA_MODIFIED_SUCCESS);
    }

    public void eliminar(Analex analex, String destinatario) throws Exception {
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        analex.Avanzar();
        //VALIDAR SI EL TOKEN ES NUM
        if (!esValidoParametros(analex.Preanalisis(), destinatario, 4)) {
            return;
        }
        int id = (int) analex.Preanalisis().getAtributo();
        //valido si el num > 0 (no existe id < 1)

        if (id < 1) {
            ClienteSMTP.sendMail(destinatario, Cadenas.ERROR_PARAM, Cadenas.ELIMINAR_FAILED);
            return;
        }
        ventaNegocio.eliminar(id);
        ClienteSMTP.sendMail(destinatario, "ELIMINAR VENTA", Cadenas.ELIMINAR_SUCCESS +Cadenas.VENTA_DELETED_SUCCESS);
    }

    public void listar(Analex analex, String destinatario) throws Exception {
        analex.Avanzar();
        Token token = analex.Preanalisis();
        String Head[] = {"ID", "NIT", "FECHA ", "TOTAL(BS) ", "CLIENTE ID", "VETERINARIO ID "};
        String Cabecera = "VETERINARIA ANIMALHELP - LISTA DE VENTAS";
        Mensaje message = Utils.dibujarTablaHtml(ventaNegocio.getVentas(), Head, Cabecera);
        message.setCorreo(destinatario);
        if (message.enviarCorreo()) {
            System.out.println(Cadenas.SUCCESSFULL_MAIL);
        } else {
            System.out.println(Cadenas.FAILED_MAIL);
        }
    }

    private boolean isEOF(Token token) {
        return token.getNombre() == Token.FIN ? true : false; // pregunta si el token que veo es eof
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

    private boolean validarValoresParametros(String nit, int cliente_id, int veterinario_id) {
        return (!nit.isEmpty()
                && cliente_id > 0 && veterinario_id > 0);
    }
}
