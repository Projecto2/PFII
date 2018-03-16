/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.servicosSolicitadosBean;

import entidade.AdmsPaciente;
import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
//import managedBean.admsbean.AdmsDefinicoesParaClassesComAgendamentoBean;
import util.Mensagem;
import util.adms.ConstantesAdms;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsAgendamentoPacienteBean extends AdmsAgendamentoGenericoBean implements Serializable
{

    private AdmsPaciente paciente;
//    private boolean pesquisar = false;
    /**
     * Creates a new instance of AdmsSolicitacoesBean
     */
    public AdmsAgendamentoPacienteBean()
    {
    }
    
    public static AdmsAgendamentoPacienteBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsAgendamentoPacienteBean admsServicoSolicitadoPacienteBean = 
            (AdmsAgendamentoPacienteBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsAgendamentoPacienteBean");
        
        return admsServicoSolicitadoPacienteBean;
    }


    
    public void setPaciente(AdmsPaciente paciente)
    {
        servicoSolicitadoPesquisar = null;
        
        dataInicial = null;
        dataFinal = null;
        
        
        listaServicosSolicitados = null;
        listaServicosSolicitados = new ArrayList<>();
        this.paciente = paciente;
    }
    
    public AdmsPaciente getPaciente()
    {
        if(paciente == null)
        {
            paciente = new AdmsPaciente();
        }
        return paciente;
    }


    public void pesquisar()
    {
        if(agendamentos == null)
        {
            getAgendamentos();
            getAgendamentosMedicos();
        }
        agendamentos.clear();
        agendamentosMedicos.clear();
        
        if (listaServicosSolicitados == null)
        {
            listaServicosSolicitados = new ArrayList<>();
        }
        if (validarDadosAPesquisar())
        {
            //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
            if(estadoAgendamento.getPkIdEstadoAgendamento() != null && estadoAgendamento.getPkIdEstadoAgendamento() != ConstantesAdms.NAO_APARECEU_COD)
                estadoAgendamento = admsEstadoAgendamentoFacade.find(estadoAgendamento.getPkIdEstadoAgendamento());
            //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
            
            listaServicosSolicitados = admsServicoSolicitadoFacade.findServicoSolicitado
           (servicoSolicitadoPesquisar, estadoAgendamento, funcionario, dataInicial, dataFinal, dataAgendadaInicial, dataAgendadaFinal, paciente, 100);
            if(listaServicosSolicitados == null || listaServicosSolicitados.isEmpty())
                Mensagem.warnMsg("Nenhum Serviço Solicitado Encontrado");
            else Mensagem.sucessoMsg("Tabela Atualizada. "+listaServicosSolicitados.size()+" registos!");
        }
    }
}
