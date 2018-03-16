/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.admsbean.agendas;

//import entidade.AdmsAgendamento;
//import entidade.AdmsDiaHoraDeAtendimentoDoMedico;
import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.RhFuncionario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoNovaBean;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public class ScheduleBean extends ScheduleGenericoBean implements Serializable
{
//    @EJB
//    private AdmsAgendamentoFacade admsAgendamentoFacade;
    

    /**
     * Creates a new instance of ScheduleBean
     */
    
    AdmsSolicitacaoNovaBean beanNovaSolicitacao;
    

    public static ScheduleBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        ScheduleBean scheduleBean = 
            (ScheduleBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "scheduleBean");
        
        return scheduleBean;
    }
    
    @Override
    public int getPkIdServicoPorProcurarAgendamento()
    {
        return AdmsSolicitacaoNovaBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getPkIdServico();
    }
    
    
    @Override
    public boolean isCarregarAgenda()
    {
        return AdmsSolicitacaoNovaBean.getInstanciaBean().isCarregarAgenda();
    }
    
    
    @Override
    public void setCarregarAgendaFalse()
    {
        AdmsSolicitacaoNovaBean.getInstanciaBean().setCarregarAgenda(false);
    }
     
 
     
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
        {
            DefaultScheduleEvent evento = new DefaultScheduleEvent();
            if (AdmsSolicitacaoNovaBean.getInstanciaBean() != null)
            {
                removerEventoAnterior();
                    evento.setTitle(""+AdmsSolicitacaoNovaBean.getInstanciaBean().getSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome()
                    + ", " + AdmsSolicitacaoNovaBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getNomeServico()
                    + ", " + AdmsSolicitacaoNovaBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico());
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
                AdmsSolicitacaoNovaBean.getInstanciaBean().definirAgendamento(event.getStartDate(), event.getEndDate());
                AdmsSolicitacaoNovaBean.getInstanciaBean().naoPermitirAgendamentoMedico();
//                mensagemEroo = "";
            }
        }
        event = new DefaultScheduleEvent();
    }
    
    
    
    public void removeEvent(ActionEvent actionEvent) {
        if (AdmsSolicitacaoNovaBean.getInstanciaBean() != null)
        {
            removerEventoAnterior();
            AdmsSolicitacaoNovaBean.getInstanciaBean().removerAgendamentoServicoPorProcurar();
        }
        event = new DefaultScheduleEvent();
        AdmsSolicitacaoNovaBean.getInstanciaBean().inicializarCamposAgendamentoMedico();
    }
    
    @Override
    public void naoPermitirAgendamentoMedico()
    {
        AdmsSolicitacaoNovaBean.getInstanciaBean().naoPermitirAgendamentoMedico();
    }
    
    @Override
    public void inicializarCamposAgendamentoMedico()
    {
        AdmsSolicitacaoNovaBean.getInstanciaBean().inicializarCamposAgendamentoMedico();
    }
    
    @Override
    public String getTituloDoServico()
    {
        if (AdmsSolicitacaoNovaBean.getInstanciaBean().getServicoPorProcurarAgendamento() != null)
        {
            return ""+AdmsSolicitacaoNovaBean.getInstanciaBean().getSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome()
                + ", " + AdmsSolicitacaoNovaBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getNomeServico()
                + ", " + AdmsSolicitacaoNovaBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico();
        }
        return "";
    }

    
    public void trocarEstadoDoAgendamentoMedico()
    {
        if(AdmsSolicitacaoNovaBean.getInstanciaBean().isAgendamentoMedico())
        {
            AdmsSolicitacaoNovaBean.getInstanciaBean().setAgendamentoMedico(false);
        }
        else AdmsSolicitacaoNovaBean.getInstanciaBean().setAgendamentoMedico(true);
    }

    @Override
    public Integer getPkIdMedico()
    {
        if(AdmsSolicitacaoNovaBean.getInstanciaBean().getMedico() == null || AdmsSolicitacaoNovaBean.getInstanciaBean().getMedico().getPkIdFuncionario() == null)
        {
            return null;
        }
        return AdmsSolicitacaoNovaBean.getInstanciaBean().getMedico().getPkIdFuncionario();
    }

    @Override
    public int getIdServicoProProcurarAgendamento()
    {
        return AdmsSolicitacaoNovaBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getPkIdServico();
    }

    @Override
    public String getNomeDoMedicoSelecionado()
    {
        if(AdmsSolicitacaoNovaBean.getInstanciaBean().getMedico() == null || AdmsSolicitacaoNovaBean.getInstanciaBean().getMedico().getPkIdFuncionario() == null)
        {
            return "SEM MÉDICO ASSOCIADO";
        }
        RhFuncionario medicoSelecionado = rhFuncionarioFacade.find(AdmsSolicitacaoNovaBean.getInstanciaBean().getMedico().getPkIdFuncionario());
        return ""+medicoSelecionado.getFkIdPessoa().getNome()+" "+medicoSelecionado.getFkIdPessoa().getSobreNome();
    }

    @Override
    public AdmsPaciente getPaciente()
    {
        return AdmsSolicitacaoNovaBean.getInstanciaBean().getSolicitacao().getFkIdPaciente();
    }

    @Override
    public AdmsServicoSolicitado getServicoPorProcurarAgendamento()
    {
        return AdmsSolicitacaoNovaBean.getInstanciaBean().getServicoPorProcurarAgendamento();
    }
    
    
    
    
}
