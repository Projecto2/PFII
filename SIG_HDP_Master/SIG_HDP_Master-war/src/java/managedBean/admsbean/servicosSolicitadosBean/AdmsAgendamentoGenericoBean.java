/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.servicosSolicitadosBean;

import entidade.AdmsAgendamento;
import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AdmsEstadoAgendamento;
import entidade.AdmsEstadoPagamento;
import entidade.AdmsServico;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.AdmsTipoPagamentoSubprocesso;
import entidade.AdmsTipoSolicitacaoServico;
import entidade.FinEncargoDevido;
import entidade.FinPrecoCategoriaServico;
import entidade.FinTipoPagamento;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.transaction.SystemException;
import managedBean.admsbean.AdmsDefinicoesParaClassesComAgendamentoBean;
//import javax.faces.bean.SessionScoped;
import managedBean.admsbean.AdmsDefsGenericaSolicitacaoBean;
import sessao.AdmsTipoPagamentoSubprocessoFacade;
import sessao.FinTipoPagamentoFacade;
import util.Mensagem;
import util.adms.ConstantesAdms;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsAgendamentoGenericoBean extends AdmsDefsGenericaSolicitacaoBean implements Serializable
{
    @EJB
    private AdmsTipoPagamentoSubprocessoFacade admsTipoPagamentoSubprocessoFacade;

    protected AdmsServicoSolicitado servicoSolicitadoPesquisar, admsServicoSolicitadoEliminar;
    
    protected AdmsEstadoAgendamento estadoAgendamento;
    
    protected List<AdmsServicoSolicitado> listaServicosSolicitados;
    
    protected Date dataInicial, dataFinal, dataAgendadaInicial, dataAgendadaFinal;
    
    protected RhFuncionario funcionario;
    
    protected String estadoConfirmDialog;
    
//    protected Integer quantidadeRegistos = 10;
    
    public AdmsAgendamentoGenericoBean()
    {
    }
    
    public AdmsServicoSolicitado getServicoSolicitadoPesquisar()
    {
        if(servicoSolicitadoPesquisar == null)
        {
            servicoSolicitadoPesquisar = getInstanciaServicoSolicitado();
        }
        return servicoSolicitadoPesquisar;
    }
    
    
    public void setServicoSolicitadoPesquisar(AdmsServicoSolicitado servicoSolicitado)
    {
        this.servicoSolicitadoPesquisar = servicoSolicitado;
    }
    
    public AdmsServicoSolicitado getInstanciaServicoSolicitado()
    {
        AdmsServicoSolicitado servicoSolicitadoLocal = new AdmsServicoSolicitado();

        servicoSolicitadoLocal.setFkIdClassificacaoServicoSolicitado(new AdmsClassificacaoServicoSolicitado());
        servicoSolicitadoLocal.setFkIdEstadoPagamento(new AdmsEstadoPagamento());
        servicoSolicitadoLocal.setFkIdServico(new AdmsServico());
        servicoSolicitadoLocal.setFkIdTipoSolicitacao(new AdmsTipoSolicitacaoServico());
        //servicoSolicitado.setFkIdSolicitacao(solicitacao);
        servicoSolicitadoLocal.setFkIdPrecoCategoriaServico(new FinPrecoCategoriaServico());

        return servicoSolicitadoLocal;
    }

    public AdmsEstadoAgendamento getEstadoAgendamento()
    {
        if(estadoAgendamento == null)
        {
            estadoAgendamento = new AdmsEstadoAgendamento();
        }
        return estadoAgendamento;
    }

    public void setEstadoAgendamento(AdmsEstadoAgendamento estadoAgendamento)
    {
        this.estadoAgendamento = estadoAgendamento;
    }
    
    public Date getDataInicial()
    {
        if(dataInicial == null)
        {
            dataInicial = new Date();
        }
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial)
    {
        this.dataInicial = dataInicial;
    }
    
    
    public Date getDataFinal()
    {
        if(dataFinal == null)
        {
            dataFinal = AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().getEndOfDay(new Date());
        }
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal)
    {
        if(dataFinal != null)
        {
            dataFinal = AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().getEndOfDay(dataFinal);
            this.dataFinal = dataFinal;
        }
    }
    

    public RhFuncionario getFuncionario()
    {
        if(funcionario == null)
        {
            funcionario = new RhFuncionario();
        }
        return funcionario;
    }

    public void setFuncionario(RhFuncionario funcionario)
    {
        this.funcionario = funcionario;
    }
    
    
    public List<AdmsServicoSolicitado> getListaServicosSolicitados()
    {
        return listaServicosSolicitados;
    }
    
    public boolean validarDadosAPesquisar()
    {
        if(dataInicial != null && dataFinal != null)
        {
            if(dataInicial.compareTo(dataFinal) > 0)
            {
                Mensagem.erroMsg("A Data Inicial Nao Pode Ser Superior A Data Final");
                return false;
            }
        }
        if((dataAgendadaInicial != null || funcionario.getPkIdFuncionario()!= null) && (estadoAgendamento.getPkIdEstadoAgendamento() != null && estadoAgendamento.getPkIdEstadoAgendamento() == -1))
        {
            Mensagem.erroMsg("Com Este Estado de Agendamento Não Se Pode Pesquisar Pela Data Agendada, ou Pelo Médico Agendado!");
            return false;
        }
        return true;
    }
    
    

     public void limparSolicitacoes()
     {
         listaServicosSolicitados = null;
     }
     
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
   public void changeServico(ValueChangeEvent e)
   {
       servicoSolicitadoPesquisar.getFkIdServico().setPkIdServico((Integer)e.getNewValue());
   }
   
   public void changeTipoSolicitacao(ValueChangeEvent e)
   {
       servicoSolicitadoPesquisar.getFkIdTipoSolicitacao().setPkIdTipoSolicitacao((Integer)e.getNewValue());
   }

   public void changeClassificacao(ValueChangeEvent e)
   {
       servicoSolicitadoPesquisar.getFkIdClassificacaoServicoSolicitado().setPkIdClassificacaoServicoSolicitado((Integer)e.getNewValue());
   }

   public void changeEstadoPagamento(ValueChangeEvent e)
   {
       servicoSolicitadoPesquisar.getFkIdEstadoPagamento().setPkIdEstadoPagamento((Integer)e.getNewValue());
   }
   
   public void changeEstadoAgendamento(ValueChangeEvent e)
   {
       estadoAgendamento.setPkIdEstadoAgendamento((Integer)e.getNewValue());
   }

   public void changeMedico(ValueChangeEvent e)
   {
       funcionario.setPkIdFuncionario((Integer)e.getNewValue());
   }
   
    

    @Override
    public boolean podeRemover(AdmsServicoSolicitado servicoRemover)
    {
        return !foiEfetuado(servicoRemover) && !temPagamento(servicoRemover);
    }
    
    public boolean renderizaRemover(AdmsServicoSolicitado servicoRemover)
    {
        return podeRemover(servicoRemover);
    }
    
    
    public void removerServicoSolicitado()
    {
        long pkIdSolicitacaoLocal = admsServicoSolicitadoEliminar.getFkIdSolicitacao().getPkIdSolicitacao();
        AdmsSolicitacao solicitacaoLocal;
        try{
            userTransaction.begin();
                listaServicosSolicitados.remove(pegarPosicaoServicoSolicitado(admsServicoSolicitadoEliminar));  
                removerServicoSolicitadoESeuAgendamentoPersistenteMente(admsServicoSolicitadoEliminar);
            userTransaction.commit();
            
            solicitacaoLocal = admsSolicitacaoFacade.find(pkIdSolicitacaoLocal);
            userTransaction.begin();

            if(remocaoPersistente)
            {
                if(solicitacaoLocal.getAdmsServicoSolicitadoList().isEmpty())
                {
                    admsSolicitacaoFacade.remove(solicitacaoLocal);
                }
            }
            else numeroServicosNovosAdicionados--;
            userTransaction.commit();
            
            Mensagem.warnMsg("Serviço Removido Com Sucesso");
            admsServicoSolicitadoEliminar = null;
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
 
    
    
// 8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    public int pegarPosicaoServicoSolicitado(AdmsServicoSolicitado servicoSolicitado)
    {
        for(int i = 0; i <= listaServicosSolicitados.size(); i++)
        {
            if(listaServicosSolicitados.get(i).getPkIdServicoSolicitado() == servicoSolicitado.getPkIdServicoSolicitado())
            {
                return i;
            }
        }
        return -1;
    }
    
    
    public void checkIn(AdmsServicoSolicitado servicoSolicitado)
    {
        mudarEstado(servicoSolicitado, ConstantesAdms.CHEGOU);
    }
    
    public void cancelar(AdmsServicoSolicitado servicoSolicitado)
    {
        System.out.println("Servico para Remover "+servicoSolicitado);
        mudarEstado(servicoSolicitado, ConstantesAdms.CANCELADOADMS);
    }
    
    public void naoApareceu(AdmsServicoSolicitado servicoSolicitado)
    {
        mudarEstado(servicoSolicitado, ConstantesAdms.NAO_APARECEU);
    }
    
    
    public void mudarEstado(AdmsServicoSolicitado servicoSolicitado, String novoEstado)
    {
        try{
            userTransaction.begin();
                AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().mudarEstado(servicoSolicitado, novoEstado, agendamentos);
                if(novoEstado.equalsIgnoreCase(ConstantesAdms.CANCELADOADMS))
                    removerAgendamentoDoPorCancelamento(servicoSolicitado);
                else if(novoEstado.equalsIgnoreCase(ConstantesAdms.CHEGOU))//    8888888888888888888888888888888888888888888888888888888888888888888
                {
//                    int p = getPosicaoAgendamento(servicoSolicitado);
//                    agendamentos.get(p).setDataHoraCheckIn(new Date());
                    AdmsAgendamento agendamentoCheckIn = admsAgendamentoFacade.findAgendamentoByServicoSolicitado(servicoSolicitado);
                    agendamentoCheckIn.setDataHoraCheckIn(new Date());
                    admsAgendamentoFacade.edit(agendamentoCheckIn);
                }
            userTransaction.commit();
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
    
    
    public boolean abilitaGravar()
    {
        if(agendamentos == null)
        {
            getAgendamentos();
            getAgendamentosMedicos();
        }
        
        for(int i = 0; i < agendamentos.size(); i++)
        {
            if(agendamentos.get(i).getPkIdAgendamento() == null)
            {
                estadoConfirmDialog = "PF('cd').show()";
                return false;
            }
        }
        if(numeroServicosNovosAdicionados == 0)
        {
            estadoConfirmDialog = "PF('cd').hide()";
            return true;
        }
        estadoConfirmDialog = "PF('cd').show()";
        return false;
    }
    
    
    
    public void create()
    {
        try
        {
            userTransaction.begin();
                gravarAgendamentos();
                gravarAgendamentosMedicos();
                agendamentos.clear();
                agendamentosMedicos.clear();
            userTransaction.commit();
            Mensagem.sucessoMsg("Solicitacoes Adicionadas com sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

    

    public Date getMomentoActual()
    {
        return new Date();
    }
    
    public void limparResultadosLista()
    {
        if(listaServicosSolicitados != null && (!listaServicosSolicitados.isEmpty()))
        {
            if(!possuiAgendamentosNaoGravados())
            {
                limpar();
            }
        }
    }
    
    public void limpar()
    {
        agendamentos.clear();
        agendamentosMedicos.clear();
        listaServicosSolicitados.clear();
    }
    
    public String getAssaoDoConfirmDialog()
    {
        return estadoConfirmDialog;
    }
    
    
    public void limparComBotao()
    {
        if(listaServicosSolicitados != null && (!listaServicosSolicitados.isEmpty()))
        {
            limpar();
        } 
    }

    public Date getDataAgendadaInicial()
    {
        return dataAgendadaInicial;
    }

    public void setDataAgendadaInicial(Date dataAgendadaInicial)
    {
        this.dataAgendadaInicial = dataAgendadaInicial;
    }
    
    public Date getDataAgendadaFinal()
    {
        return dataAgendadaFinal;
    }

    public void setDataAgendadaFinal(Date dataAgendadaFinal)
    {
        if(dataAgendadaFinal != null)
        {
            dataAgendadaFinal = AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().getEndOfDay(dataAgendadaFinal);
            this.dataAgendadaFinal = dataAgendadaFinal;
        }
    }
    
    
    public void definirServicoSolicitadoParaRemover(AdmsServicoSolicitado servicoRemover)
    {
        admsServicoSolicitadoEliminar = servicoRemover;
    }
    
    public Date getMomentoAtual()
    {
        return new Date();
    }

    @Override
    public void definirValorASerPagoPeloTipo(FinEncargoDevido encargo, AdmsServicoSolicitado servicoSolicitado)
    {
        FinTipoPagamento tipoPagamento = admsTipoPagamentoSubprocessoFacade.findTipoPagamentoBySubprocesso(servicoSolicitado.getFkIdSolicitacao().getFkIdSubprocesso());
        
        encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValor() * tipoPagamento.getFatorDeMultiplicacao());
    }
    
}
