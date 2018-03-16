/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.agendas;

//import entidade.AdmsAgendamento;
import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoEditarPagarBean;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public class ScheduleEditarBean extends ScheduleGenericoBean implements Serializable
{
//    @EJB
//    private AdmsAgendamentoFacade admsAgendamentoFacade;
    

    /**
     * Creates a new instance of ScheduleBean
     * @return 
     */

    
    public static ScheduleEditarBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        ScheduleEditarBean scheduleEditarBean = 
            (ScheduleEditarBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "scheduleEditarBean");
        
        return scheduleEditarBean;
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
            if (AdmsSolicitacaoEditarPagarBean.getInstanciaBean() != null)
            {
                removerEventoAnterior();
                    evento.setTitle(""+AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome()
                    + ", " + AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getNomeServico()
                    + ", " + AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico());
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
                AdmsSolicitacaoEditarPagarBean.getInstanciaBean().definirAgendamento(event.getStartDate(), event.getEndDate());
                AdmsSolicitacaoEditarPagarBean.getInstanciaBean().naoPermitirAgendamentoMedico();
//                mensagemEroo = "";
            }
        }
        event = new DefaultScheduleEvent();
    }

    
    public void removeEvent(ActionEvent actionEvent) {
        if (AdmsSolicitacaoEditarPagarBean.getInstanciaBean() != null)
        {
            removerEventoAnterior();
            AdmsSolicitacaoEditarPagarBean.getInstanciaBean().removerAgendamentoServicoPorProcurar();
        }
        event = new DefaultScheduleEvent();
        AdmsSolicitacaoEditarPagarBean.getInstanciaBean().inicializarCamposAgendamentoMedico();
    }
    
    @Override
    public void inicializarCamposAgendamentoMedico()
    {
        AdmsSolicitacaoEditarPagarBean.getInstanciaBean().inicializarCamposAgendamentoMedico();
    }
    
    
    @Override
    public String getTituloDoServico()
    {
        if (AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getServicoPorProcurarAgendamento() != null)
        {
            return ""+AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome()
                + ", " + AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getNomeServico()
                + ", " + AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico();
        }
        return "";
    }
    
    
    
    public void trocarEstadoDoAgendamentoMedico()
    {
        if(AdmsSolicitacaoEditarPagarBean.getInstanciaBean().isAgendamentoMedico())
        {
            AdmsSolicitacaoEditarPagarBean.getInstanciaBean().setAgendamentoMedico(false);
        }
        else{
            AdmsSolicitacaoEditarPagarBean.getInstanciaBean().setAgendamentoMedico(true);
        }
    }  

    @Override
    public void naoPermitirAgendamentoMedico()
    {
        AdmsSolicitacaoEditarPagarBean.getInstanciaBean().naoPermitirAgendamentoMedico();
    }

    @Override
    public int getPkIdServicoPorProcurarAgendamento()
    {
        return AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getPkIdServico();
    }

    @Override
    public boolean isCarregarAgenda()
    {
        return AdmsSolicitacaoEditarPagarBean.getInstanciaBean().isCarregarAgenda();
    }

    @Override
    public void setCarregarAgendaFalse()
    {
        AdmsSolicitacaoEditarPagarBean.getInstanciaBean().setCarregarAgenda(false);
    }

    @Override
    public Integer getPkIdMedico()
    {
        return AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getMedico().getPkIdFuncionario();
    }
    
    @Override
    public int getIdServicoProProcurarAgendamento()
    {
        return AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getPkIdServico();
    }

    @Override
    public String getNomeDoMedicoSelecionado()
    {
        if(AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getMedico() == null || AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getMedico().getPkIdFuncionario() == null)
        {
            return "SEM MÉDICO ASSOCIADO";
        }
        RhFuncionario medicoSelecionado = rhFuncionarioFacade.find(AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getMedico().getPkIdFuncionario());
        return ""+medicoSelecionado.getFkIdPessoa().getNome()+" "+medicoSelecionado.getFkIdPessoa().getSobreNome();
    }

    @Override
    public AdmsPaciente getPaciente()
    {
        return AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getSolicitacao().getFkIdPaciente();
    }

    @Override
    public AdmsServicoSolicitado getServicoPorProcurarAgendamento()
    {
        return AdmsSolicitacaoEditarPagarBean.getInstanciaBean().getServicoPorProcurarAgendamento();
    }
}
