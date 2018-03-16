/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.inter;

import entidade.AdmsPaciente;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import util.Constantes;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class NotifyView implements Serializable
{

    /**
     * Creates a new instance of NotifyView
     */
    public NotifyView()
    {
    }

    public static NotifyView getInstanciaBean()
    {
        return (NotifyView) GeradorCodigo.getInstanciaBean("notifyView");
    }

    public void send(AdmsPaciente paciente, String servico)
    {
        if (servico.equals("Internamento Medicina"))
        {
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish(Constantes.CHANNEL, new FacesMessage("Nova Solicitação de Internamento", "Paciente : " + paciente.getNumeroPaciente() + "-" + paciente.getFkIdPessoa().getNome() + " " + paciente.getFkIdPessoa().getNomeDoMeio() + " " + paciente.getFkIdPessoa().getSobreNome()));
        }
    }
}
