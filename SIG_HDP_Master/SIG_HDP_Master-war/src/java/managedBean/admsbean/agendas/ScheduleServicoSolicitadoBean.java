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
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import managedBean.admsbean.servicosSolicitadosBean.AdmsAgendamentoBean;
import org.primefaces.model.DefaultScheduleEvent;
import sessao.AdmsAgendamentoFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public class ScheduleServicoSolicitadoBean extends ScheduleGenericoBean implements Serializable
{
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    

    /**
     * Creates a new instance of ScheduleBean
     * @return 
     */
    
    public static ScheduleServicoSolicitadoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        ScheduleServicoSolicitadoBean scheduleServicoSolicitadoBean = 
            (ScheduleServicoSolicitadoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "scheduleServicoSolicitadoBean");
        
        return scheduleServicoSolicitadoBean;
    }

    
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
        {
            DefaultScheduleEvent evento = new DefaultScheduleEvent();
            if (AdmsAgendamentoBean.getInstanciaBean() != null)
            {
                removerEventoAnterior();
                    evento.setTitle(""+AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdSolicitacao()
                        .getFkIdPaciente().getFkIdPessoa().getNome()
                    + ", " + AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getNomeServico()
                    + ", " + AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico());
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
                AdmsAgendamentoBean.getInstanciaBean().definirAgendamento(event.getStartDate(), event.getEndDate());
                AdmsAgendamentoBean.getInstanciaBean().naoPermitirAgendamentoMedico();
//                mensagemEroo = "";
            }
        }
        event = new DefaultScheduleEvent();
    }
    
    
    public void removeEvent(ActionEvent actionEvent) {
        if (AdmsAgendamentoBean.getInstanciaBean() != null)
        {
            removerEventoAnterior();
            AdmsAgendamentoBean.getInstanciaBean().removerAgendamentoServicoPorProcurar();
        }
        event = new DefaultScheduleEvent();
        AdmsAgendamentoBean.getInstanciaBean().inicializarCamposAgendamentoMedico();
    }
    
      
    @Override
    public String getTituloDoServico()
    {
        if (AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento() != null)
        {
            return "" + AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getNomeServico()
                + ", " + AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdTipoSolicitacao().getDescricaoTipoSolicitacaoServico();
        }
        return "";
    }

    
    
    public void trocarEstadoDoAgendamentoMedico()
    {
        if(AdmsAgendamentoBean.getInstanciaBean().isAgendamentoMedico())
        {
            AdmsAgendamentoBean.getInstanciaBean().setAgendamentoMedico(false);
        }
        else{
            AdmsAgendamentoBean.getInstanciaBean().setAgendamentoMedico(true);
        }
    }  

    @Override
    public void naoPermitirAgendamentoMedico()
    {
        AdmsAgendamentoBean.getInstanciaBean().naoPermitirAgendamentoMedico();
    }

    @Override
    public void inicializarCamposAgendamentoMedico()
    {
        AdmsAgendamentoBean.getInstanciaBean().inicializarCamposAgendamentoMedico();
    }

    @Override
    public int getPkIdServicoPorProcurarAgendamento()
    {
        return AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getPkIdServico();
    }

    @Override
    public boolean isCarregarAgenda()
    {
        return AdmsAgendamentoBean.getInstanciaBean().isCarregarAgenda();
    }

    @Override
    public void setCarregarAgendaFalse()
    {
        AdmsAgendamentoBean.getInstanciaBean().setCarregarAgenda(false);
    }

    @Override
    public Integer getPkIdMedico()
    {
        return AdmsAgendamentoBean.getInstanciaBean().getMedico().getPkIdFuncionario();
    }
    
    @Override
    public int getIdServicoProProcurarAgendamento()
    {
        return AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdServico().getPkIdServico();
    }

    @Override
    public String getNomeDoMedicoSelecionado()
    {
        if(AdmsAgendamentoBean.getInstanciaBean().getMedico() == null || AdmsAgendamentoBean.getInstanciaBean().getMedico().getPkIdFuncionario() == null)
        {
            return "SEM MÉDICO ASSOCIADO";
        }
        RhFuncionario medicoSelecionado = rhFuncionarioFacade.find(AdmsAgendamentoBean.getInstanciaBean().getMedico().getPkIdFuncionario());
        return ""+medicoSelecionado.getFkIdPessoa().getNome()+" "+medicoSelecionado.getFkIdPessoa().getSobreNome();
    }

    @Override
    public AdmsPaciente getPaciente()
    {
        return AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento().getFkIdSolicitacao().getFkIdPaciente();
    }

    @Override
    public AdmsServicoSolicitado getServicoPorProcurarAgendamento()
    {
        return AdmsAgendamentoBean.getInstanciaBean().getServicoPorProcurarAgendamento();
    }
    
}
