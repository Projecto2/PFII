/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.solicitacoes;

import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.FinEncargoDevido;
import entidade.FinTipoPagamento;
import entidade.SegHistoricoSessao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsSolicitacaoMedicaAmbulatorioBean extends AdmsSolitacaoNovaEditarGenericoBean implements Serializable
{
//    @EJB
//    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;

    /**
     * Creates a new instance of AdmsSolicitacaoMedicaInternamentoBean
     */
    
//    private AdmsServicoSolicitado servicoAnterior;
    private AdmsServicoEfetuado servicoEfetuado;
    
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession sessao = request.getSession();
    
    public AdmsSolicitacaoMedicaAmbulatorioBean()
    {
    }
    
    public static AdmsSolicitacaoMedicaAmbulatorioBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsSolicitacaoMedicaAmbulatorioBean admsSolicitacaoMedicaAmbulatorioBean = 
            (AdmsSolicitacaoMedicaAmbulatorioBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsSolicitacaoMedicaAmbulatorioBean");
        
        return admsSolicitacaoMedicaAmbulatorioBean;
    }
    
    public void create()
    {     
        try
        {
            userTransaction.begin();
                solicitacao.setData(new Date());
                definirFuncionarioSolicitante();
                admsSolicitacaoFacade.create(solicitacao);
            userTransaction.commit();
            Mensagem.sucessoMsg("Solicitacoes Adicionadas com sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
//    public void adicionarSolicitacoes() throws Exception
//    {
//        for(AdmsServicoSolicitado servicoSolicitadoLocal:solicitacao.getAdmsServicoSolicitadoList())
//        {
//            if(servicoSolicitadoLocal.getPkIdServicoSolicitado() == null)
//            {
////                System.out.println("Gravou");
////                    admsServicoSolicitadoFacade.create(servicoSolicitadoLocal);
////                    
//                servicoSolicitadoLocal.setFkIdSolicitacao(solicitacao);
//            }
//        }
//    }
    
    
    public void definirFuncionarioSolicitante()
    {
        SegHistoricoSessao sessaoActual = (SegHistoricoSessao) sessao.getAttribute("sessaoActual");
        solicitacao.setFkIdFuncionarioSolicitante(sessaoActual.getFkIdConta().getFkIdFuncionario());
    }
    

    
    public String removerServicoSolicitado(AdmsServicoSolicitado servicoRemover)
    {
        try{
            userTransaction.begin();
                removerServicoSolicitadoSeuAgendamentoEDescontarValorDoPreco(servicoRemover);
                if(remocaoPersistente)
                {
                    admsSolicitacaoFacade.edit(solicitacao);
                }
                else numeroServicosNovosAdicionados--;
                
            userTransaction.commit();
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
        return "";
    }
    
    
    public boolean renderizaGravar()
    {
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

    public AdmsServicoEfetuado getServicoEfetuado()
    {
        servicoEfetuado = new AdmsServicoEfetuado();
        return servicoEfetuado;
    }

    public void setServicoEfetuado(AdmsServicoEfetuado servicoEfetuado)
    {
        System.out.println("set servico efetuado chamado");
        solicitacao = null;
        getSolicitacao();
        this.servicoEfetuado = servicoEfetuado;
        
        definirDadosDaSolicitacaoAnterior();
        
    }
    
    
    public void definirDadosDaSolicitacaoAnterior()
    {
        solicitacao.setFkIdCentro(servicoEfetuado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdCentro());
        solicitacao.setFkIdPaciente(servicoEfetuado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente());
        solicitacao.setFkIdResponsavelPaciente(servicoEfetuado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdResponsavelPaciente());
        solicitacao.setFkIdServicoEfetuado(servicoEfetuado);
        solicitacao.setFkIdSubprocesso(servicoEfetuado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso());
    }
    
    public void gravarSolicitacao()
    {
        create();
        limparDepoisDaGravacao();
        definirDadosDaSolicitacaoAnterior();
    }
    
    
    public void limparDepoisDaGravacao()
    {
        limparSolicitacao();
        valorTotalDoPreco = 0;
        fatorDeMultiplicacao = 1;
        servicoPesquisa = null;
        servicoSolicitado = null;
//        servicosLista = null;
        getServicoSolicitado();
        limparDeServicoParaBaixo();
    }
    
    @Override
    public void limparSolicitacao()
    {
        solicitacao = null;
        solicitacao = new AdmsSolicitacao();
        solicitacao.setAdmsServicoSolicitadoList(new ArrayList<AdmsServicoSolicitado>());
    }

    @Override
    public void definirValorASerPagoPeloTipo(FinEncargoDevido encargo, AdmsServicoSolicitado servicoSolicitado)
    {
        FinTipoPagamento tipo = admsTipoPagamentoSubprocessoFacade.findTipoPagamentoBySubprocesso(servicoSolicitado.getFkIdSolicitacao().getFkIdSubprocesso());
//        encargo.setValor(servicoSolicitadoLocal.getFkIdPrecoCategoriaServico().getValor() * tipo.getFatorDeMultiplicacao());

        if (tipo.getDescricaoTipoPagamento().equalsIgnoreCase("Preço 1"))
        {
            encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValor());
        }
        if (tipo.getDescricaoTipoPagamento().equalsIgnoreCase("Preço 2"))
        {
            encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValorPreco2());
        }
        if (tipo.getDescricaoTipoPagamento().equalsIgnoreCase("DP"))
        {
            encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValorPrecoDp());
        }
        if (tipo.getDescricaoTipoPagamento().equalsIgnoreCase("DP-FS"))
        {
            encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValorPrecoDpfs());
        }
    }
    
}
