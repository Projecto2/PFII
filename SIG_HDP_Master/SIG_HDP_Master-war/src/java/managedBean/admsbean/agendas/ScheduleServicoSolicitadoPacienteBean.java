/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.agendas;

import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import managedBean.admsbean.servicosSolicitadosBean.AdmsAgendamentoPacienteBean;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoNovaBean;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public class ScheduleServicoSolicitadoPacienteBean extends ScheduleGenericoBean implements Serializable
{
//    @EJB
//    private AdmsAgendamentoFacade admsAgendamentoFacade;
    

    /**
     * Creates a new instance of ScheduleBean
     */
    
    AdmsSolicitacaoNovaBean beanNovaSolicitacao;
    
    public static ScheduleServicoSolicitadoPacienteBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        ScheduleServicoSolicitadoPacienteBean scheduleServicoSolicitadoPacienteBean = 
            (ScheduleServicoSolicitadoPacienteBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "scheduleServicoSolicitadoPacienteBean");
        
        return scheduleServicoSolicitadoPacienteBean;
    }
     
 
    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
    
    
     
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
        {
            DefaultScheduleEvent evento = new DefaultScheduleEvent();
            if (AdmsAgendamentoPacienteBean.getInstanciaBean() != null)
            {
                removerEventoAnterior();
                    evento.setTitle(""+AdmsAgendamentoPacienteBean.getInstanciaBean().getPaciente().getFkIdPessoa().getNome()
                    + ", " + AdmsAgendamentoPacienteBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getNomeServico()
                    + ", " + AdmsAgendamentoPacienteBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico());
                    evento.setStartDate(event.getStartDate());
                    evento.setEndDate(event.getEndDate());
                    evento.setEditable(false);
                    evento.setDescription("null");
                    evento.setStyleClass("emp1");
            }
            if(validarEvento(evento))
            {
                if(evento.getEndDate() == null) evento.setEndDate(evento.getStartDate());
                eventModel.addEvent(evento);
                AdmsAgendamentoPacienteBean.getInstanciaBean().definirAgendamento(event.getStartDate(), event.getEndDate());
                AdmsAgendamentoPacienteBean.getInstanciaBean().naoPermitirAgendamentoMedico();
//                mensagemEroo = "";
            }
            else{
//                mensagemEroo = "Horário Escolhido Não Válido, Escolha Outro";
            }
        }
        event = new DefaultScheduleEvent();
    }
    
    
    
    
    public void removeEvent(ActionEvent actionEvent) {
        if (getAdmsServicoSolicitadoPacienteBean() != null)
        {
            removerEventoAnterior();
            AdmsAgendamentoPacienteBean.getInstanciaBean().removerAgendamentoServicoPorProcurar();
        }
        event = new DefaultScheduleEvent();
        AdmsAgendamentoPacienteBean.getInstanciaBean().inicializarCamposAgendamentoMedico();
    }
        
    
    @Override
    public String getTituloDoServico()
    {
        if (AdmsAgendamentoPacienteBean.getInstanciaBean().getServicoPorProcurarAgendamento() != null)
        {
            return ""+AdmsAgendamentoPacienteBean.getInstanciaBean().getPaciente().getFkIdPessoa().getNome()
                + ", " + AdmsAgendamentoPacienteBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getNomeServico()
                + ", " + AdmsAgendamentoPacienteBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico();
        }
        return "";
    }
    
    
    public AdmsAgendamentoPacienteBean getAdmsServicoSolicitadoPacienteBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsAgendamentoPacienteBean admsServicoSolicitadoPacienteBean = (AdmsAgendamentoPacienteBean) context.getELContext().getELResolver()
            .getValue(context.getELContext(), null, "admsServicoSolicitadoPacienteBean");
        
        return admsServicoSolicitadoPacienteBean;
    }
    
    
    public void trocarEstadoDoAgendamentoMedico()
    {
        if(AdmsAgendamentoPacienteBean.getInstanciaBean().isAgendamentoMedico())
        {
            AdmsAgendamentoPacienteBean.getInstanciaBean().setAgendamentoMedico(false);
        }
        else{
            AdmsAgendamentoPacienteBean.getInstanciaBean().setAgendamentoMedico(true);
        }
    }  
    
    

    @Override
    public void naoPermitirAgendamentoMedico()
    {
        AdmsAgendamentoPacienteBean.getInstanciaBean().naoPermitirAgendamentoMedico();
    }

    @Override
    public void inicializarCamposAgendamentoMedico()
    {
        AdmsAgendamentoPacienteBean.getInstanciaBean().inicializarCamposAgendamentoMedico();
    }

    @Override
    public int getPkIdServicoPorProcurarAgendamento()
    {
        return AdmsAgendamentoPacienteBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getPkIdServico();
    }

    @Override
    public boolean isCarregarAgenda()
    {
        return AdmsAgendamentoPacienteBean.getInstanciaBean().isCarregarAgenda();
    }

    @Override
    public void setCarregarAgendaFalse()
    {
        AdmsAgendamentoPacienteBean.getInstanciaBean().setCarregarAgenda(false);
    }

    @Override
    public Integer getPkIdMedico()
    {
        return AdmsAgendamentoPacienteBean.getInstanciaBean().getMedico().getPkIdFuncionario();
    }
    
    @Override
    public int getIdServicoProProcurarAgendamento()
    {
        return AdmsAgendamentoPacienteBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getPkIdServico();
    }

    @Override
    public String getNomeDoMedicoSelecionado()
    {
        if(AdmsAgendamentoPacienteBean.getInstanciaBean().getMedico() == null || AdmsAgendamentoPacienteBean.getInstanciaBean().getMedico().getPkIdFuncionario() == null)
        {
            return "SEM MÉDICO ASSOCIADO";
        }
        RhFuncionario medicoSelecionado = rhFuncionarioFacade.find(AdmsAgendamentoPacienteBean.getInstanciaBean().getMedico().getPkIdFuncionario());
        return ""+medicoSelecionado.getFkIdPessoa().getNome()+" "+medicoSelecionado.getFkIdPessoa().getSobreNome();
    }

    @Override
    public AdmsPaciente getPaciente()
    {
        return AdmsAgendamentoPacienteBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdSolicitacao().getFkIdPaciente();
    }

    @Override
    public AdmsServicoSolicitado getServicoPorProcurarAgendamento()
    {
        return AdmsAgendamentoPacienteBean.getInstanciaBean().getServicoPorProcurarAgendamento();
    }
    
}
