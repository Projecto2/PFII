/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.agendas;

//import entidade.AdmsAgendamento;
//import entidade.AdmsDiaHoraDeAtendimentoDoMedico;
import entidade.AdmsAgendamento;
import entidade.AdmsAgendamentoMedico;
import entidade.AdmsDiaHoraDeAtendimentoDoMedico;
import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import managedBean.admsbean.AdmsDefinicoesParaClassesComAgendamentoBean;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsAgendamentoMedicoFacade;
import sessao.AdmsDiaHoraDeAtendimentoDoMedicoFacade;
import sessao.RhFuncionarioFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public abstract class ScheduleGenericoBean implements Serializable
{    
    @EJB
    private AdmsDiaHoraDeAtendimentoDoMedicoFacade admsDiaHoraDeAtendimentoDoMedicoFacade;
    @EJB
    private AdmsAgendamentoMedicoFacade admsAgendamentoMedicoFacade;
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    @EJB
    protected RhFuncionarioFacade rhFuncionarioFacade;
    
    
    
    
    protected ScheduleModel eventModel;
    
    protected AdmsServicoSolicitado servicoPorProcurar;
    
    protected int especialidade;
 
    protected ScheduleEvent event = new DefaultScheduleEvent();
    
    protected boolean podeEditar = true, podeGravar = true, podeRemover = false;
    
    protected List<RhFuncionario> funcionariosLista;
    
    protected String mensagemEroo = "", mensagemAviso = "", horarioAtendimento = "", limitePacientes = "", jaMarcados = "", nomeDoMedico = "";
    
    protected List<AdmsDiaHoraDeAtendimentoDoMedico> horariosMedico;
    
    protected List<AdmsAgendamento> agendamentosQueChocam;
    
    protected List<AdmsAgendamentoMedico> agendamentosMedicosQueChocam;
    
 
    /**
     * Creates a new instance of ScheduleGenericoBean
     */
    public ScheduleGenericoBean()
    {
    }
    
    
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
    }

     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.MONTH, calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 0);
         
        return calendar.getTime();
    }
     
 
    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
    
    
    public void onEventSelect(SelectEvent selectEvent) {
        podeEditar = false;
        podeGravar = false;
        event = (ScheduleEvent) selectEvent.getObject();
        podeRemover = event.getDescription().equalsIgnoreCase("null");
//        naoPermitirAgendamentoMedico();
        inicializarStringsDadosAgendamento();
    }
    
    
    public abstract void naoPermitirAgendamentoMedico();
    
    
    public void onDateSelect(SelectEvent selectEvent) {
        podeRemover = false;
        definirSePodeEditarEGravar(selectEvent);
        event = new DefaultScheduleEvent(""+getTituloDoServico(), (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        apresentarDadosDesteDia(event.getStartDate());
    }
    
    public abstract String getTituloDoServico();
    
    public abstract void inicializarCamposAgendamentoMedico();
    
    public void apresentarDadosDesteDia(Date data)
    {
        inicializarStringsDadosAgendamento();
        String diaDaSemana = getDiaDaSemanaDaData(data);
        
        carregarNumeroDeAgendamentosJaMarcadosParaEsteDia(data);
        
        nomeDoMedico = getNomeDoMedicoSelecionado();

        if(horariosMedico != null && !horariosMedico.isEmpty())
        {
            
            for(int i = 0; i < horariosMedico.size(); i++)
            {
                if(horariosMedico.get(i).getFkIdDiaDaSemana().getCodigoDiaSemana().equalsIgnoreCase(diaDaSemana))
                {
                    Calendar dataHorarioInicioMedico = Calendar.getInstance(), dataHorarioFimMedico = Calendar.getInstance();
                    dataHorarioInicioMedico.setTime(horariosMedico.get(i).getHoraInicioTrabalho());
                    dataHorarioFimMedico.setTime(horariosMedico.get(i).getHoraFimTrabalho());
                    
//                    mensagemAviso = ""+dataHorarioInicioMedico.get(Calendar.HOUR_OF_DAY)+":"+dataHorarioInicioMedico.get(Calendar.MINUTE)+" às "
//                        + ""+dataHorarioFimMedico.get(Calendar.HOUR_OF_DAY)+":"+dataHorarioInicioMedico.get(Calendar.MINUTE);
                    
                    horarioAtendimento = ""+dataHorarioInicioMedico.get(Calendar.HOUR_OF_DAY)+":"+dataHorarioInicioMedico.get(Calendar.MINUTE)+" às "
                        + ""+dataHorarioFimMedico.get(Calendar.HOUR_OF_DAY)+":"+dataHorarioInicioMedico.get(Calendar.MINUTE);
                    
                    if(horariosMedico.get(i).getNumeroMaximoPaciente() != null)
                        limitePacientes = ""+horariosMedico.get(i).getNumeroMaximoPaciente();
                    
//                    jaMarcados = ""+numeroMarcacoesJaFeitas;
                   
//                    mensagemAviso +=" Limite de Marcações "+horariosMedico.get(i).getNumeroMaximoPaciente()+", Já Marcados "+numeroMarcacoesJaFeitas;                    
                    return;
                }
            }
            mensagemAviso = "Atenção o Médico Não Possui Horário de Antendimento Neste Dia";
        }
        
    }
    
    
    public void carregarNumeroDeAgendamentosJaMarcadosParaEsteDia(Date data)
    {
        int numeroMarcacoesJaFeitas = admsAgendamentoFacade.findNumeroAgendamentosMedicoServicoSolicitado
            (getIdServicoProProcurarAgendamento(), 
            getPkIdMedico(),
            AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().getInicioDoDia(data), 
            AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().getEndOfDay(data));
        
        if(numeroMarcacoesJaFeitas != 0)
        {
//                mensagemAviso += ", Já Marcados "+numeroMarcacoesJaFeitas;
            jaMarcados = ""+numeroMarcacoesJaFeitas;
        }
    }
    
    public void inicializarStringsDadosAgendamento()
    {
        horarioAtendimento = "";
        limitePacientes = "";
        jaMarcados = "";
        mensagemAviso = "";
        nomeDoMedico = "";
    }
    
    public abstract String getNomeDoMedicoSelecionado();
    
    public abstract int getIdServicoProProcurarAgendamento();
    
    
    public String getDiaDaSemanaDaData(Date data)
    {
        String diaDaSemana = "";
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(data);
        
        int dia = dataCalendar.get(Calendar.DAY_OF_WEEK);
        
        if(dia == Calendar.MONDAY)
            diaDaSemana = "SEG";
        if(dia == Calendar.TUESDAY)
            diaDaSemana = "TER";
        if(dia == Calendar.WEDNESDAY)
            diaDaSemana = "QUA";
        if(dia == Calendar.THURSDAY)
            diaDaSemana = "QUI";
        if(dia == Calendar.FRIDAY)
            diaDaSemana = "SEX";
        if(dia == Calendar.SATURDAY)
            diaDaSemana = "SAB";
        if(dia == Calendar.SUNDAY)
            diaDaSemana = "DOM";
        
        return diaDaSemana;
    }
    
    
    
    public void adicionarEventosDesteTipoDeServico()
    {
        String medicoString = "";
        List<AdmsAgendamento> agendamentos = admsAgendamentoFacade.findAgendamentosComEsseServico(getPkIdServicoPorProcurarAgendamento(), new Date());
        
        if(agendamentos == null)
        {
            return;
        }
        for(int i = 0; i < agendamentos.size(); i++)
        {
            Date dataFimAgendamento = agendamentos.get(i).getDataHoraFim();
            if(dataFimAgendamento == null) 
                dataFimAgendamento = agendamentos.get(i).getDataHoraInicio();
            
            
            AdmsAgendamentoMedico agendamentoMedico = admsAgendamentoMedicoFacade.getAgendamentoMedico(agendamentos.get(i));
            
            if(agendamentoMedico != null)
                medicoString = ", (MEDICO: "+agendamentoMedico.getFkIdMedico().getCodigoFuncionario()+" "+agendamentoMedico.getFkIdMedico().getFkIdPessoa().getNome()+" "+agendamentoMedico.getFkIdMedico().getFkIdPessoa().getSobreNome()+")";
            
            DefaultScheduleEvent evento = new DefaultScheduleEvent(
                ""+agendamentos.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente()+", "
                    + ""+agendamentos.get(i).getFkIdServicoSolicitado().getFkIdServico().getNomeServico()+""+medicoString, 
                /*getDataCompleta(agendamentos.get(i))*/agendamentos.get(i).getDataHoraInicio(), dataFimAgendamento/*getDataCompleta(agendamentos.get(i))*/);
            evento.setEditable(false);
            evento.setDescription(""+agendamentos.get(i).getFkIdServicoSolicitado().getPkIdServicoSolicitado());
            if(agendamentos.get(i).getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Agendado"))
            {
                evento.setStyleClass("emp3");
            }
            if(agendamentos.get(i).getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Chegou"))
            {
                evento.setStyleClass("emp2");
            }
            if(agendamentos.get(i).getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Efetuado"))
            {
                evento.setStyleClass("emp4");
            }
            eventModel.addEvent(evento);
            medicoString = "";
        }
        
    }
    
    
    public abstract int getPkIdServicoPorProcurarAgendamento();
    
    
    public ScheduleModel getEventModel() {
        if(isCarregarAgenda())
        {
            eventModel.clear();
            adicionarEventosDesteTipoDeServico();
//            AdmsSolicitacaoNovaBean.getInstanciaBean().setCarregarAgenda(false);
            setCarregarAgendaFalse();
            mensagemEroo = "";
        }
        return eventModel;
    }
    
    public abstract boolean isCarregarAgenda();
    
    public abstract void setCarregarAgendaFalse();
    
    
    public boolean isPodeEditar()
    {
        return podeEditar;
    }

    public void setPodeEditar(boolean podeEditar)
    {
        this.podeEditar = podeEditar;
    }

    public boolean isPodeRemover()
    {
        return podeRemover;
    }

    public void setPodeRemover(boolean podeRemover)
    {
        this.podeRemover = podeRemover;
    }
    
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
    
    
//    public boolean validarEvento(DefaultScheduleEvent evento)
//    {
//        Date data = new Date();
//        return evento.getEndDate().compareTo(data) >= 0;
//    }
    
    public boolean validarEvento(DefaultScheduleEvent evento)
    {
        mensagemEroo = "";
        
        
        
        if(servicoJaPossuiAgendamento())
        {
            mensagemEroo = "Este Serviço Já Possui Agendamento, Faça Uma Atualização Da Página ou Da Pesquisa";
            return false;
        }
        Date data = new Date();
        if( !(evento.getStartDate().compareTo(data) >= 0) ) 
        {
            mensagemEroo = "Horário Escolhido Não Válido, Escolha Outro";
            return false;
        }
        
//            if(evento.getEndDate() == null)
//            {
//                mensagemEroo = "";
//                return true;
//            }
        if(evento.getEndDate() != null)
        { 
            if( !(evento.getEndDate().compareTo(evento.getStartDate()) > 0) )
            {
                mensagemEroo = "Data de Fim Não Pode Ser Igual ou Exceder a Data de Inicio da Marcação";
                return false;
            }
        }
//        else{
//            mensagemEroo = "Horário Escolhido Não Válido, Escolha Outro";
//            return false;
//        }
        
        
        if(dataDeAgendamentoChocaComOutroAgendamento(evento)) return false;
        
        if(dataDeAgendamentoChocaComOutroAgendamentoMedico(evento)) return false;
        
        return true;
    }
    
    
    
    
    
    public boolean servicoJaPossuiAgendamento()
    {
        AdmsServicoSolicitado servicoSolicitadoLocal = getServicoPorProcurarAgendamento();
        if(servicoSolicitadoLocal.getPkIdServicoSolicitado() == null) return false;
        AdmsAgendamento agendamentoDoServico = admsAgendamentoFacade.findAgendamentoByServicoSolicitado(servicoSolicitadoLocal);
        if(agendamentoDoServico == null) return  false;
        if(agendamentoDoServico.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Não Apareceu"))
            return false;
        return true;
    }
    
    
    public boolean dataDeAgendamentoChocaComOutroAgendamento(DefaultScheduleEvent evento)
    {
        if(evento.getEndDate() != null)
        {
            agendamentosQueChocam = admsAgendamentoFacade.findAgendamentosQueChocamComEstasDatas(getPaciente(), evento.getStartDate(), evento.getEndDate());
        }
        if(agendamentosQueChocam != null)
        {
            System.out.println("lista dos que chocam "+agendamentosQueChocam);
            mensagemEroo = "O Horário Deste Agendamento Choca Com Outros Agendamentos Já Agendados Para Este Paciente";
            return true;
        }
        return false;
    }
    
    
        
    public boolean dataDeAgendamentoChocaComOutroAgendamentoMedico(DefaultScheduleEvent evento)
    {
        if(getPkIdMedico() == null) return false;
        if(evento.getEndDate() != null)
        {
            agendamentosMedicosQueChocam = admsAgendamentoMedicoFacade.findAgendamentosMedicosQueChocamComEstasDatas(getPkIdMedico(), evento.getStartDate(), evento.getEndDate());
        }
        if(agendamentosMedicosQueChocam != null)
        {
            System.out.println("lista dos que chocam "+agendamentosMedicosQueChocam);
            mensagemEroo = "O Horário Deste Agendamento Choca Com Outros Agendamentos Já Existentes Para Este Médico";
            return true;
        }
        return false;
    }
    
    
    public abstract AdmsPaciente getPaciente();
    
    
    
    
    
    public void removerEventoAnterior()
    {
        int posicaoRemover = -1;
        for(int i = 0; i < eventModel.getEvents().size(); i++)
        {
            if(eventModel.getEvents().get(i).getDescription().equalsIgnoreCase("null"))
            {
                posicaoRemover = i;
                break;
            }
        }
        
        if(posicaoRemover != -1)
            eventModel.getEvents().remove(posicaoRemover);
    }
    
    
    public void definirSePodeEditarEGravar(SelectEvent selectEvent)
    {
        Date dataEscolhida = (Date) selectEvent.getObject();
        Date agora = new Date();
        if((!verificarSeEMesmoDia(dataEscolhida, agora)) && (dataEscolhida.compareTo(new Date()) < 0))
        {
            podeGravar = false;
            podeEditar = false;
        }
        else
        {
            podeEditar = true;
            podeGravar = true;
        }
    }

    public boolean isPodeGravar()
    {
        return podeGravar;
    }

    public void setPodeGravar(boolean podeGravar)
    {
        this.podeGravar = podeGravar;
    }
    
    public boolean verificarSeEMesmoDia(Date data1, Date data2)
    {
        Calendar t1 = Calendar.getInstance();
        Calendar t2 = Calendar.getInstance();
        
        t1.setTime(data1);
        t2.setTime(data2);
        
        return ( t1.get(Calendar.YEAR) == (t2.get(Calendar.YEAR)) ) && ( t1.get(Calendar.MONTH) == (t2.get(Calendar.MONTH)) ) && 
            ( t1.get(Calendar.DATE) == (t2.get(Calendar.DATE)) );
    }

    
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public AdmsServicoSolicitado getServicoPorProcurar()
    {
        return servicoPorProcurar;
    }

    public void setServicoPorProcurar(AdmsServicoSolicitado servicoPorProcurar)
    {
        this.servicoPorProcurar = servicoPorProcurar;
    }
    

    public int getEspecialidade()
    {
        return especialidade;
    }

    public void setEspecialidade(int especialidade)
    {
        this.especialidade = especialidade;
    }
    
    public void changeEspecialidade(ValueChangeEvent e)
    {
        this.especialidade = ((Integer) e.getNewValue());
        carregarMedicos(especialidade);
    }
    
    public void carregarMedicos(int especialidade)
    {
        if(funcionariosLista == null)
        {
            funcionariosLista = new ArrayList<>();
        } else funcionariosLista.clear();
        funcionariosLista.addAll(rhFuncionarioFacade.findMedicosAtivosEspecialidade(especialidade));
    }

    public List<RhFuncionario> getFuncionariosLista()
    {
        if(funcionariosLista == null)
        {
            funcionariosLista = new ArrayList<>();
            carregarMedicos(0);
        }
        return funcionariosLista;
    }

    public void setFuncionariosLista(List<RhFuncionario> funcionariosLista)
    {
        this.funcionariosLista = funcionariosLista;
    }


    public String getMensagemEroo()
    {
        return mensagemEroo;
    }

    public void setMensagemEroo(String mensagemEroo)
    {
        this.mensagemEroo = mensagemEroo;
    }
    
    public Date getMomentoAtual()
    {
        return new Date();
    }

    public List<AdmsDiaHoraDeAtendimentoDoMedico> getHorariosMedico()
    {
        return horariosMedico;
    }

    public void setHorariosMedico(List<AdmsDiaHoraDeAtendimentoDoMedico> horariosMedico)
    {
        this.horariosMedico = horariosMedico;
    }
    
    
    public Date dataHoraInicioEvento()
    {
        return event.getStartDate();
    }
    

    public Date dataHoraFimEvento()
    {
        if(event.getStartDate() == null) return null;
        return AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().getEndOfDay(event.getStartDate());
    }

    public String getMensagemAviso()
    {
        return mensagemAviso;
    }

    public void setMensagemAviso(String mensagemAviso)
    {
        this.mensagemAviso = mensagemAviso;
    }
    
    
    public List<AdmsDiaHoraDeAtendimentoDoMedico> getListaDosHorariosMedico()
    {
        horariosMedico = null;
        if(getPkIdMedico() != null)
        {
            horariosMedico = admsDiaHoraDeAtendimentoDoMedicoFacade.findHorariosByMedico(getPkIdMedico());
        }
        return horariosMedico;
    }

    
    
//    public void carregarHorariosDoMedico(ValueChangeEvent e)
//    {
//        Integer idMedico = (Integer)e.getNewValue();
//        horariosMedico = null;
//        if(idMedico != null)
//        {
//            horariosMedico = rhFuncionarioFacade.find(idMedico).getAdmsDiaHoraDeAtendimentoDoMedicoList();
//        }
////        return horariosMedico;
//    }
    
    
    public abstract Integer getPkIdMedico();
    
    
    public String getLimiteDePacientes(Integer numeroMaximoPacientes)
    {
        if(numeroMaximoPacientes == null || numeroMaximoPacientes == 0) return"";
        return "Limite de Pacientes:     "+numeroMaximoPacientes;
    }

    public String getHorarioAtendimento()
    {
        return horarioAtendimento;
    }

    public void setHorarioAtendimento(String horarioAtendimento)
    {
        this.horarioAtendimento = horarioAtendimento;
    }

    public String getLimitePacientes()
    {
        return limitePacientes;
    }

    public void setLimitePacientes(String limitePacientes)
    {
        this.limitePacientes = limitePacientes;
    }

    public String getJaMarcados()
    {
        return jaMarcados;
    }

    public void setJaMarcados(String jaMarcados)
    {
        this.jaMarcados = jaMarcados;
    }

    public String getNomeDoMedico()
    {
        return nomeDoMedico;
    }

    public void setNomeDoMedico(String nomeDoMedico)
    {
        this.nomeDoMedico = nomeDoMedico;
    }
    
    
    public abstract AdmsServicoSolicitado getServicoPorProcurarAgendamento();
    
    
    
    
}
