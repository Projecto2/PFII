/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.solicitacoes;

import entidade.AdmsAgendamento;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.FinEncargoDevido;
import entidade.FinTipoPagamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import managedBean.admsbean.AdmsDefinicoesParaClassesComAgendamentoBean;
import sessao.AdmsTipoPagamentoSubprocessoFacade;
//import util.Constantes;
import util.Mensagem;
import util.adms.ConstantesAdms;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsSolicitacaoEditarPagarBean extends AdmsSolitacaoNovaEditarGenericoBean implements Serializable
{    
    @EJB
    private AdmsTipoPagamentoSubprocessoFacade admsTipoPagamentoSubprocessoFacade;
    
    private String paginaAnterior;
    
    private AdmsServicoSolicitado  admsServicoSolicitadoEliminar;
    
    private boolean pararAtualizacao = false;
    
    private FinTipoPagamento tipoPagamento;

    /**
     * Creates a new instance of AdmsSolicitacaoEditarPagarBean
     */
    
    public AdmsSolicitacaoEditarPagarBean()
    {
    }
    
    public static AdmsSolicitacaoEditarPagarBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsSolicitacaoEditarPagarBean admsSolicitacaoEditarPagarBean = 
            (AdmsSolicitacaoEditarPagarBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsSolicitacaoEditarPagarBean");
        
        return admsSolicitacaoEditarPagarBean;
    }
    
    
    public void create()
    {
        try
        {
            userTransaction.begin();
                adicionarSolicitacoes();
                gravarAgendamentos();
                gravarAgendamentosMedicos();
                admsSolicitacaoFacade.edit(solicitacao);
                agendamentos.clear();
                agendamentosMedicos.clear();
            userTransaction.commit();
            Mensagem.sucessoMsg("Edição Efetuada Com Sucesso!");
            numeroServicosNovosAdicionados = 0;
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
    
    @Override
    public void definirValorASerPagoPeloTipo(FinEncargoDevido encargo, AdmsServicoSolicitado servicoSolicitado)
    {
        if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("Preço 1"))
        {
            encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValor());
        }
        if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("Preço 2"))
        {
            encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValorPreco2());
        }
        if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("DP"))
        {
            encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValorPrecoDp());
        }
        if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("DP-FS"))
        {
            encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValorPrecoDpfs());
        }
    }
    
    
    
    public void adicionarSolicitacoes() throws Exception
    {
        for(AdmsServicoSolicitado servicoSolicitadoLocal:solicitacao.getAdmsServicoSolicitadoList())
        {
            if(servicoSolicitadoLocal.getPkIdServicoSolicitado() == null)
            {
                admsServicoSolicitadoFacade.create(servicoSolicitadoLocal);
            }
        }
    }

    
    
    public void removerServicoSolicitado(AdmsServicoSolicitado servicoRemover)
    {
        try{
                removerServicoSolicitadoSeuAgendamentoEDescontarValorDoPreco(servicoRemover);
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
    

    public void eliminarServicoSolicitado()
    {
        long pkIdSolicitacaoLocal = admsServicoSolicitadoEliminar.getFkIdSolicitacao().getPkIdSolicitacao();
        AdmsSolicitacao solicitacaoLocal;
        try{
            userTransaction.begin();
                removerServicoSolicitadoSeuAgendamentoEDescontarValorDoPreco(admsServicoSolicitadoEliminar);
            userTransaction.commit();
            
            solicitacaoLocal = admsSolicitacaoFacade.find(pkIdSolicitacaoLocal);
            userTransaction.begin();
                if(remocaoPersistente)
                {
                    System.out.println("remocao persistente sim");
                    if(solicitacaoLocal.getAdmsServicoSolicitadoList().isEmpty())
                    {
                        admsSolicitacaoFacade.remove(solicitacaoLocal);
                        System.out.println("removeu a solicitacao");
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
    
    
    public boolean renderizaGravar()
    {
        for(int i = 0; i < agendamentos.size(); i++)
        {
            if(agendamentos.get(i).getPkIdAgendamento() == null)
            {
                return false;
            }
        }
        return numeroServicosNovosAdicionados == 0;
    }

    
    public void inicializaServicoNovos()
    {
        numeroServicosNovosAdicionados = 0;
    }
    
    
    public boolean podePagar()
    {
        for(AdmsServicoSolicitado servico: solicitacao.getAdmsServicoSolicitadoList())
        {
            if(servico.getPkIdServicoSolicitado() == null)
            {
                return false;
            }
            else if(servico.getFkIdEstadoPagamento().getDescricaoEstadoPagamento().equalsIgnoreCase("Pendente"))
            {
                return true;
            }
        }
        return false;
    }
    
    
    public double getFatorDeMultiplicacao()
    {
        return fatorDeMultiplicacao;
    }

    public void setFatorDeMultiplicacao(double fatorDeMultiplicacao)
    {
        this.fatorDeMultiplicacao = fatorDeMultiplicacao;
    }
    
    public double getValorTotalDoPreco()
    {
        valorTotalDoPreco = 0;
        for(AdmsServicoSolicitado servico: solicitacao.getAdmsServicoSolicitadoList())
        {
            if(servico.getFkIdEstadoPagamento().getDescricaoEstadoPagamento().equalsIgnoreCase("Pendente"))
            {
                valorTotalDoPreco += servico.getFkIdPrecoCategoriaServico().getValor();
            }
        }
        return valorTotalDoPreco;
    }

    public void setValorTotalDoPreco(double valorTotalDoPreco)
    {
        this.valorTotalDoPreco = valorTotalDoPreco;
    }
        
    public double getValorTotalApagar()
    {
        System.out.println("" + tipoPagamento);
        System.out.println("" + tipoPagamento.getFatorDeMultiplicacao());
        System.out.println("" + valorTotalDoPreco);
//        tipoPagamento = finTipoPagamentoFacade.find(tipoPagamento.getPkIdTipoPagamento());

        valorTotalDoPreco = 0;
        
        if (solicitacao != null)
        {
            if (solicitacao.getAdmsServicoSolicitadoList() != null && !solicitacao.getAdmsServicoSolicitadoList().isEmpty())
            {
                
                if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("Preço 1"))
                {
                    for (int i = 0; i < solicitacao.getAdmsServicoSolicitadoList().size(); i++)
                    {
                        valorTotalDoPreco += solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdPrecoCategoriaServico().getValor();
                    }
                }
                if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("Preço 2"))
                {
                    for (int i = 0; i < solicitacao.getAdmsServicoSolicitadoList().size(); i++)
                    {
                        valorTotalDoPreco += solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdPrecoCategoriaServico().getValorPreco2();
                    }
                }
                if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("DP"))
                {
                    for (int i = 0; i < solicitacao.getAdmsServicoSolicitadoList().size(); i++)
                    {
                        valorTotalDoPreco += solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdPrecoCategoriaServico().getValorPrecoDp();
                    }
                }
                if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("DP-FS"))
                {
                    for (int i = 0; i < solicitacao.getAdmsServicoSolicitadoList().size(); i++)
                    {
                        valorTotalDoPreco += solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdPrecoCategoriaServico().getValorPrecoDpfs();
                    }
                }
            }
        }
        
        System.out.println(""+tipoPagamento);
        System.out.println(""+tipoPagamento.getFatorDeMultiplicacao());
        System.out.println(""+valorTotalDoPreco);
        return valorTotalDoPreco/* * tipoPagamento.getFatorDeMultiplicacao()*/;
//        return valorTotalDoPreco * tipoPagamento.getFatorDeMultiplicacao();
    }
    
    public void recarregarSolicitacao()
    {
        long id = solicitacao.getPkIdSolicitacao();
        
        solicitacao.getAdmsServicoSolicitadoList().clear();
        
        solicitacao = null;
        
        solicitacao = new AdmsSolicitacao();
        
        solicitacao = admsSolicitacaoFacade.find(id);
        
        for(int i = 0; i < solicitacao.getAdmsServicoSolicitadoList().size(); i ++)
            System.out.println(""+solicitacao.getAdmsServicoSolicitadoList().get(i).getPkIdServicoSolicitado()+" servicos solicitados "+solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdServico().getNomeServico());
        
        numeroServicosNovosAdicionados = 0;
    }
    
    
// 8888888888888888888888888888888888888888888888888888888888888 Metodo ainda de teste, deve ser bem analizado
    public void removerAgendamentosOrfaos()
    {
        List<Integer> posicoes = new ArrayList<>();
        for(int j = 0; j < agendamentos.size(); j++)
        {
            if(agendamentos.get(j).getFkIdServicoSolicitado().getPkIdServicoSolicitado() == null)
            {
                removerAgendamentoMedico(agendamentos.get(j));
                posicoes.add(j);
            }
        }
        for(int i = 0; i < posicoes.size(); i++)
        {
//System.out.println("Removeu");
            agendamentos.remove(posicoes.get(i).intValue() - i);
        }
    }
    
    
    public void definirAgendamentoParaHoje(AdmsServicoSolicitado servicoSolicitado)
    {
        if(agendamentos == null)
        {
            agendamentos = new ArrayList<>();
        }
        
        AdmsAgendamento agendamentoLocal = new AdmsAgendamento();
        agendamentoLocal.setDataHoraInicio(AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().get15MinutosDepois(new Date()));
        agendamentoLocal.setDataHoraCheckIn(new Date());
        agendamentoLocal.setFkIdEstadoAgendamento(admsEstadoAgendamentoFacade.getEstadoAgendamentoChegou());
//        agendamento.setHoraAgendada(data);
        agendamentoLocal.setFkIdServicoSolicitado(servicoSolicitado);
        
        agendamentos.add(agendamentoLocal);
    }
    
    
    public void checkIn(AdmsServicoSolicitado servicoSolicitado)
    {
        mudarEstado(servicoSolicitado, ConstantesAdms.CHEGOU);
    }
    
    
    public void cancelar(AdmsServicoSolicitado servicoSolicitado)
    {
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

    public boolean isPararAtualizacao()
    {
        return pararAtualizacao;
    }

    public void setPararAtualizacao(boolean pararAtualizacao)
    {
//System.out.println("Parou");
        this.pararAtualizacao = pararAtualizacao;
    }

    public String getPaginaAnterior()
    {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior)
    {
        this.paginaAnterior = paginaAnterior;
    }
    
    public void definirSolicitacaoEPaginaAnterior(AdmsSolicitacao solicitacaoLocal, String paginaAnterior)
    {
//        AdmsSolicitacao solicitacaoLocal = admsSolicitacaoFacade.find(id);
//        setSolicitacao(solicitacao);
        setSolicitacao(solicitacaoLocal);
        this.paginaAnterior = paginaAnterior;
    }
    

    public void definirServicoSolicitadoParaRemover(AdmsServicoSolicitado servicoRemover)
    {
        admsServicoSolicitadoEliminar = servicoRemover;
    }
    
    
    public FinTipoPagamento getTipoPagamentoSubprocesso()
    {
        return admsTipoPagamentoSubprocessoFacade.findTipoPagamentoBySubprocesso(solicitacao.getFkIdSubprocesso());
    }

    public FinTipoPagamento getTipoPagamento()
    {
        if(tipoPagamento == null)
        {
            tipoPagamento = getTipoPagamentoSubprocesso();
        }
        return tipoPagamento;
    }

    public void setTipoPagamento(FinTipoPagamento tipoPagamento)
    {
        this.tipoPagamento = tipoPagamento;
    }

    
    
}
