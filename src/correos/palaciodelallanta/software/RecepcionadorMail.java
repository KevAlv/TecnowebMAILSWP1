/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correos.palaciodelallanta.software;

import correos.palaciodelallantas.utils.Utils;
import correos.palaciodelallantas.utils.Mensaje;
import correos.palaciodelallanta.procesador.Parser;
import correos.palaciodelallanta.procesador.Token;
import correos.palaciodelallanta.procesador.Cinta;
import correos.palaciodelallanta.procesador.Analex;
import correos.palaciodelallanta.software.Email.MailVenta;
import correos.palaciodelallanta.software.Email.MailReporte;
import correos.palaciodelallanta.software.Email.MailMascota;
import correos.palaciodelallanta.software.Email.MailProducto;
import correos.palaciodelallanta.software.Email.MailCliente;
import correos.palaciodelallanta.software.Email.MailCategoria;
import correos.palaciodelallanta.software.Email.MailVeterinario;
import correos.palaciodelallanta.software.Email.MailAtencion;
import correos.palaciodelallanta.protocolos.ClienteSMTP;
import java.sql.Date;
import javax.mail.MessagingException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge Luis Urquiza
 */

public class RecepcionadorMail {

    MailCategoria mailCategoria = new MailCategoria();
    MailVeterinario mailVeterinario = new MailVeterinario();
    MailProducto mailProducto = new MailProducto();
    MailMascota mailMascota = new MailMascota();
    MailCliente mailCliente = new MailCliente();
    MailVenta mailVenta = new MailVenta();
    MailAtencion mailAtencion = new MailAtencion();
    MailReporte mailReporte = new MailReporte();

    public void procesarMensaje(String Message) throws MessagingException, Exception {
        // Setteando Variables
        String destinatario = Utils.getDestinatario(Message); //obtener destinario
        String content = Utils.getSubjectOrden(Message); // obtener subject
        System.out.println("Contenido : \t" + content);
        System.out.println("Destinatario : \t" + destinatario);
        Cinta cinta = new Cinta(content);
        Analex analex = new Analex(cinta);
        Parser parser = new Parser(analex);
        // Verificar Orden
        parser.Expresion();
        if (parser.errorFlag) {
            // Enviar Correo de Error
            ClienteSMTP.sendMail(destinatario, "ERROR DE COMANDO ",
                    "El comando o caracteres que usted ha introducido es incorrecto,"
                    + "para ver la lista de comandos disponibles y ejemplos utilice el comando HELP"
            );
            return;
        }

        // Si todo va bien, procesar el Comando
        analex.Init();
        Token token = analex.Preanalisis();
        if (token.getNombre() == Token.HELP) { // EL USUARIO HA PEDIDO LA LISTA DE COMANDOS
            // Mostrar HTML con ayuda
            Mensaje message = Utils.dibujarMenuAyuda(); //MimeBodyMail
            message.setCorreo(destinatario);
            if (message.enviarCorreo()) {
                System.out.println("Envio Correo de Respuesta Exitoso");
            } else {
                System.out.println("No se ha enviado el correo, reintentar de nuevo");
            }
            return;
        }
        //FUNC = COMANDO DE LOS CASOS DE USO
        // Sino es HELP, es una funcionalidad
        switch ((int) token.getAtributo()) {
            // CU1 
            case Token.REGISTRARCATEGORIA:
                mailCategoria.registrar(analex, destinatario);
                break;
            case Token.OBTENERCATEGORIAS:
                mailCategoria.listar(analex, destinatario);
                break;
            case Token.MODIFICARCATEGORIA:
                mailCategoria.modificar(analex, destinatario);
                break;
            case Token.ELIMINARCATEGORIA:
                mailCategoria.eliminar(analex, destinatario);
                break;

            //CU2
            case Token.REGISTRARVETERINARIO:
                mailVeterinario.registrar(analex, destinatario);
                break;
            case Token.MODIFICARVETERINARIO:
                mailVeterinario.modificar(analex, destinatario);
                break;
            case Token.OBTENERVETERINARIOS:
                mailVeterinario.listar(analex, destinatario);
                break;
            case Token.ELIMINARVETERINARIO:
                mailVeterinario.eliminar(analex, destinatario);
                break;
                
            // CU3

            case Token.REGISTRARPRODUCTO:
                mailProducto.registrar(analex, destinatario);
                break;
            case Token.MODIFICARPRODUCTO:
                mailProducto.modificar(analex, destinatario);
                break;
            case Token.OBTENERPRODUCTOS:
                mailProducto.listar(analex, destinatario);
                break;
            case Token.ELIMINARPRODUCTO:
                mailProducto.eliminar(analex, destinatario);
                break;

            //CU4
            case Token.REGISTRARCLIENTE:
                mailCliente.registrar(analex, destinatario);
                break;
            case Token.MODIFICARCLIENTE:
                mailCliente.modificar(analex, destinatario);
                break;
            case Token.OBTENERCLIENTES:
                mailCliente.listar(analex, destinatario);
                break;
            case Token.ELIMINARCLIENTE:
                mailCliente.eliminar(analex, destinatario);
                break;

            //CU5
            case Token.REGISTRARMASCOTA:
                mailMascota.registrar(analex, destinatario);
                break;
            case Token.MODIFICARMASCOTA:
                mailMascota.modificar(analex, destinatario);
                break;
            case Token.OBTENERMASCOTAS:
                mailMascota.listar(analex, destinatario);
                break;
            case Token.ELIMINARMASCOTA:
                mailMascota.eliminar(analex, destinatario);
                break;

            //CU6
            case Token.REGISTRARVENTA:
                mailVenta.registrar(analex, destinatario);
                break;
            case Token.MODIFICARVENTA:
                mailVenta.modificar(analex, destinatario);
                break;
            case Token.OBTENERVENTAS:
                mailVenta.listar(analex, destinatario);
                break;
            case Token.ELIMINARVENTA:
                mailVenta.eliminar(analex, destinatario);
                break;

            //CU7
            case Token.REGISTRARATENCION:
                mailAtencion.registrar(analex, destinatario);
                break;
            case Token.MODIFICARATENCION:
                mailAtencion.modificar(analex, destinatario);
                break;
            case Token.OBTENERATENCIONES:
                mailAtencion.listar(analex, destinatario);
                break;
            case Token.ELIMINARATENCION:
                mailAtencion.eliminar(analex, destinatario);
                break;

            //CU8 REPORTES
            case Token.VENTASMENSUALES:
                mailReporte.ventasMensuales(analex, destinatario);
                break;
            case Token.TOPTRESCLIENTESCOMPRAS:
                mailReporte.top3ClientesCompras(analex, destinatario);
                break;
            case Token.TOPTRESMASCOTASATENDIDAS:
                mailReporte.top3MascotasAtendidas(analex, destinatario);
                break;
            case Token.TOPTRESPRODUCTOSVENDIDOS:
                mailReporte.top3ProductosVendidos(analex, destinatario);
                break;
            case Token.VENTASTOTALDEHOY:
                mailReporte.ventasTotalDeHoy(analex, destinatario);
                break;
            case Token.TORTAPORCENTAJEANIMAL:
                mailReporte.tortaPorcentajeAnimal(analex, destinatario);
                break;
        }
    }
}
