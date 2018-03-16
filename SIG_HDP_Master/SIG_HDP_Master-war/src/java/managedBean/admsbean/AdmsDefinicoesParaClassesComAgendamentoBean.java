/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsAgendamento;
import entidade.AdmsEstadoPagamento;
import entidade.AdmsServicoSolicitado;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.UserTransaction;
import managedBean.finbean.FinPagamentoBean;
//import managedBean.finbean.FinPagamentoBean;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsEstadoAgendamentoFacade;
import sessao.AdmsEstadoPagamentoFacade;
import util.Constantes;
import util.Mensagem;
import util.adms.ConstantesAdms;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsDefinicoesParaClassesComAgendamentoBean implements Serializable
{
    @EJB
    private AdmsEstadoAgendamentoFacade admsEstadoAgendamentoFacade;
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    @EJB
    private AdmsEstadoPagamentoFacade admsEstadoPagamentoFacade;
    
    @Resource
    protected UserTransaction userTransaction;
    

    private String corAgendamento;
    /**
     * Creates a new instance of AdmsDefinicoesParaClassesComAgendamentoBean
     */
    public AdmsDefinicoesParaClassesComAgendamentoBean()
    {
    }    
    
    public static AdmsDefinicoesParaClassesComAgendamentoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsDefinicoesParaClassesComAgendamentoBean admsDefinicoesParaClassesComAgendamentoBean = 
            (AdmsDefinicoesParaClassesComAgendamentoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsDefinicoesParaClassesComAgendamentoBean");
        
        return admsDefinicoesParaClassesComAgendamentoBean;
    }
    
    public Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    
    public Date getInicioDoDia(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 01);
        calendar.set(Calendar.MILLISECOND, 00);
//        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        return calendar.getTime();
    }
    
    public Date get15MinutosDepois(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 15);
        return calendar.getTime();
    }
    
   
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void definirCorEstadoAgendamento(String estado)
    {
        if(estado.equalsIgnoreCase(""))
            corAgendamento = ConstantesAdms.PRETO;
        else if(estado.equalsIgnoreCase("Chegou"))
            corAgendamento = ConstantesAdms.VERDE;
        else if(estado.equalsIgnoreCase("Agendado"))
            corAgendamento = ConstantesAdms.LARANJA;
        else if(estado.equalsIgnoreCase("Cancelado"))
            corAgendamento = ConstantesAdms.VERMELHO;
        else if(estado.equalsIgnoreCase("Não Apareceu"))
            corAgendamento = ConstantesAdms.CINZA;
        else if(estado.equalsIgnoreCase("Efetuado"))
            corAgendamento = ConstantesAdms.AZUL;
    }
    

    
    public boolean podeAgendar(AdmsAgendamento agendamento)
    {
        if(agendamento == null)
            return true;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Cancelado"))
            return true;
        return agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Não Apareceu");
    }
    
    public boolean podeFazerCheckIn(AdmsAgendamento agendamento)
    {
        
        if(agendamento == null || agendamento.getPkIdAgendamento() == null) return false;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Cancelado"))
            return false;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Não Apareceu"))
            return false;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Efetuado"))
            return false;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Chegou"))
            return false;
        
        Date data = new Date();
        Date dataAgendada = new Date();
        
        dataAgendada = agendamento.getDataHoraInicio();
        
        if( (dataAgendada.compareTo(data)) > 0 )
        {
            if( getDiferencaDeHoras(dataAgendada, data) > 6)
            {
                return false;
            }
        }
        else {
            if( getDiferencaDeHoras(data, dataAgendada) > 10)
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    public boolean podeFazerCheckInNovaSolicitacao(AdmsAgendamento agendamento)
    {
        
        if(agendamento == null) return false;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Cancelado"))
            return false;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Não Apareceu"))
            return false;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Efetuado"))
            return false;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Chegou"))
            return false;
        
        Date data = new Date();
        Date dataAgendada = new Date();
        
        dataAgendada = agendamento.getDataHoraInicio();
        
        if( (dataAgendada.compareTo(data)) > 0 )
        {
            if( getDiferencaDeHoras(dataAgendada, data) > 6)
                return false;
        }
        else {
            if( getDiferencaDeHoras(data, dataAgendada) > 10)
                return false;
        }
        
        return true;
    }
    
    
    public boolean podeFazerCheckInSemAgendamentoGravado(AdmsAgendamento agendamento)
    {
//System.out.println("entrou");
        if(agendamento == null) return false;
//System.out.println("o agendamento e nulo");
        if(agendamento.getFkIdEstadoAgendamento() == null || agendamento.getFkIdEstadoAgendamento().getPkIdEstadoAgendamento() == null) return false;
        
//System.out.println("apresentou false no agendamento");
        
        Date data = new Date();
        Date dataAgendada = new Date();
        
//        dataAgendada = getDataCompleta(agendamento);
        
        dataAgendada = agendamento.getDataHoraInicio();
        
        if( (dataAgendada.compareTo(data)) > 0 )
        {
//System.out.println("numa 1");
            if( getDiferencaDeHoras(dataAgendada, data) > 6)
            {
//System.out.println("maior q seis horas");
                return false;
            }
        }
        else {
            if( getDiferencaDeHoras(data, dataAgendada) > 10)
            {
//System.out.println("maior q dez horas");
                return false;
            }
        }
        
        return true;
    }
    
//    public Date getDataCompleta(AdmsAgendamento agendamento){
//        Calendar dataAgendadaCalendar = Calendar.getInstance();
//        Calendar horaAgendadaCalendar = Calendar.getInstance();
//        Calendar dataCompletaAgendada = Calendar.getInstance();
//        
//        dataAgendadaCalendar.setTime(agendamento.getDataAgendada());
//        horaAgendadaCalendar.setTime(agendamento.getHoraAgendada());
//        
//        dataCompletaAgendada.set(dataAgendadaCalendar.get(dataAgendadaCalendar.YEAR), 
//            dataAgendadaCalendar.get(dataAgendadaCalendar.MONTH), 
//            dataAgendadaCalendar.get(dataAgendadaCalendar.DATE), 
//            horaAgendadaCalendar.get(horaAgendadaCalendar.HOUR_OF_DAY), 
//            horaAgendadaCalendar.get(horaAgendadaCalendar.MINUTE));
//        
//        return dataCompletaAgendada.getTime();
//    }
    
    
    public boolean podeCancelar(AdmsAgendamento agendamento)
    {
        if(agendamento == null)
            return false;
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Cancelado"))
            return false;
        return !agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Efetuado");
    }
    
    public boolean podeMarcarAusencia(AdmsAgendamento agendamento)
    {
        if(agendamento == null || agendamento.getPkIdAgendamento() == null)
            return false;
        
        Date data = new Date();
        Date dataAgendada = new Date();
        
//        dataAgendada = getDataCompleta(agendamento);
        
        dataAgendada = agendamento.getDataHoraInicio();
        
        if(agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Agendado")
            && (dataAgendada.compareTo(data)) < 0)
        {
            if(getDiferencaDeHoras(data, dataAgendada) >= 2)
                return true;
        }
        
        return false;
    }
    
    public boolean podeReAgendar(AdmsAgendamento agendamento)
    {
        if(agendamento == null)
            return false;
        return !agendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Efetuado");
    }
    
    
    public long getDiferencaDeHoras(Date dataAgendada, Date dataAtual)
    {
        long segundos = (dataAgendada.getTime() - dataAtual.getTime()) / 1000;
        long horas = segundos / 3600;
        return horas;
    }
    
    
    public void mudarEstado(AdmsServicoSolicitado servicoSolicitado, String novoEstado, List<AdmsAgendamento> agendamentos)
    {
        String msg = "";
        int sucesso = 0;
        AdmsAgendamento agendamento = new AdmsAgendamento();
        for(int i = 0; i < agendamentos.size(); i++)
        {
            if(agendamentos.get(i).getFkIdServicoSolicitado().getPkIdServicoSolicitado() == servicoSolicitado.getPkIdServicoSolicitado())
            {
                agendamento = admsAgendamentoFacade.findUltimoAgendamentoServico(agendamentos.get(i).getFkIdServicoSolicitado());
//                        agendamento = agendamentos.get(i);
                break;
            }
        }
        if(novoEstado.equalsIgnoreCase(ConstantesAdms.CHEGOU))
        {
            if(podeFazerCheckIn(agendamento))
            {
                agendamento.setFkIdEstadoAgendamento(admsEstadoAgendamentoFacade.getEstadoAgendamentoChegou());
                msg = "Chek-In Efetuado Com Sucesso";
                sucesso = 1;
            } else 
            {
                msg = "Chek-In Não Pode Ser Efetuado, Estado Do Serviço Foi Alterado!";
            }
        }
        if(novoEstado.equalsIgnoreCase(ConstantesAdms.CANCELADOADMS))
        {
            if(podeCancelar(agendamento))
            {
                agendamento.setFkIdEstadoAgendamento(admsEstadoAgendamentoFacade.getEstadoAgendamentoCancelado());
                msg = "Serviço Cancelado Com Sucesso";
                sucesso = 1;
            } else 
            {
                msg = "Cancelamento Não Pode Ser Efetuado, Estado Do Serviço Foi Alterado!";
            }
        }
        if(novoEstado.equalsIgnoreCase(ConstantesAdms.NAO_APARECEU))
        {
            if(podeMarcarAusencia(agendamento))
            {
                agendamento.setFkIdEstadoAgendamento(admsEstadoAgendamentoFacade.getEstadoAgendamentoNaoApareceu());
                msg = "Estado Do Serviço Alterado Com Sucesso";
                sucesso = 1;
            } else
            {
                msg = "Marcação Da Ausência Não Pode Ser Efetuado, Estado Do Serviço Foi Alterado!";
            }
        }
        if(!(agendamento == null) && !(agendamento.getPkIdAgendamento() == null))
        {
            admsAgendamentoFacade.edit(agendamento);
        } else sucesso = 2;
        if(sucesso == 1) Mensagem.sucessoMsg(msg);
        else if(sucesso == 0) Mensagem.warnMsg(msg);
}
    
    
    public boolean verificarSeAgendamnetosSaoIguais(AdmsAgendamento agendamento1, AdmsAgendamento agendamento2)
    {
        if(agendamento1.getPkIdAgendamento() != agendamento2.getPkIdAgendamento())
            return false;
        if(agendamento1.getDataHoraInicio() != agendamento2.getDataHoraInicio())
            return false;
//        if(agendamento1.getDataAgendada() != agendamento2.getDataAgendada())
//            return false;
//        if(agendamento1.getHoraAgendada() != agendamento2.getHoraAgendada())
//            return false;
        if(agendamento1.getFkIdEstadoAgendamento() != agendamento2.getFkIdEstadoAgendamento())
            return false;
        return true;
    }
    
    
    public boolean podePagar(AdmsServicoSolicitado servicoPagar)
    {
        AdmsEstadoPagamento estadoPagamento = admsEstadoPagamentoFacade.findEstadoPagamentoPago();
        return estadoPagamento.getPkIdEstadoPagamento() == servicoPagar.getFkIdEstadoPagamento().getPkIdEstadoPagamento();
    }
    
    
//    public String servicoPagar(AdmsServicoSolicitado servicoPagar)
//    {
//        FinPagamentoBean.getInstanciaBean().definirPagamentoDeUmServicoSolicitado(servicoPagar);
//        return "/faces/admsVisao/pagamento/pagamentoAdms.xhtml?faces-redirect=true";
//    }

    
//    public FinPagamentoBean getFinPagamentoBean()
//    {
//        FacesContext context = FacesContext.getCurrentInstance();
//        FinPagamentoBean finPagamentoBean = (FinPagamentoBean) context.getELContext().getELResolver()
//            .getValue(context.getELContext(), null, "finPagamentoBean");
//        
//        return finPagamentoBean;
//    }

    public String getCorAgendamento()
    {
        return corAgendamento;
    }

    public void setCorAgendamento(String corAgendamento)
    {
        this.corAgendamento = corAgendamento;
    }
    
    public Date getMomentoActual()
    {
        return new Date();
    }
    
}
