/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.amb;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author aires
 */
public class Mensagem
{
    public static void enviarMensagemErro(String identifier, String msg)
    {
        enviarMensagem(identifier, FacesMessage.SEVERITY_ERROR, msg);
    }

    public static void enviarMensagemInformacao(String identifier, String msg)
    {
        enviarMensagem(identifier, FacesMessage.SEVERITY_INFO, msg);
    }

    public static void enviarMensagemAlerta(String identifier, String msg)
    {
        enviarMensagem(identifier, FacesMessage.SEVERITY_WARN, msg);
    }

    public static void enviarMensagemFatal(String identifier, String msg)
    {
        enviarMensagem(identifier, FacesMessage.SEVERITY_FATAL, msg);
    }

    public static void enviarMensagem(String identifier, FacesMessage.Severity severity, String msg)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage1 = new FacesMessage(severity, msg, null);
        context.addMessage(identifier, facesMessage1);
    }

    // envio de mensagem para a janela
    public static void enviarJanelaMensagemErro(String tituloJanela, String msg)
    {
        enviarMensagem(FacesMessage.SEVERITY_ERROR, tituloJanela, msg);
    }

    public static void enviarJanelaMensagemInformacao(String tituloJanela, String msg)
    {
//System.out.println("0: Mensagem.enviarJanelaMensagemInformacao()\tmsg: " + msg);
        enviarMensagem(FacesMessage.SEVERITY_INFO, tituloJanela, msg);
    }

    public static void enviarJanelaMensagemAlerta(String tituloJanela, String msg)
    {
        enviarMensagem(FacesMessage.SEVERITY_WARN, tituloJanela, msg);
    }

    public static void enviarJanelaMensagemFatal(String tituloJanela, String msg)
    {
        enviarMensagem(FacesMessage.SEVERITY_FATAL, tituloJanela, msg);
    }
    
    public static void enviarMensagem(FacesMessage.Severity severity, String tituloJanela, String msg)
    {
        RequestContext context = RequestContext.getCurrentInstance();
        context.showMessageInDialog(new FacesMessage(severity, tituloJanela, msg));
    }
}