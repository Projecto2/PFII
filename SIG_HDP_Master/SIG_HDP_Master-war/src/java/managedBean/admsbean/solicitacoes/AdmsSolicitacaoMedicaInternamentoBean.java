/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.solicitacoes;

import entidade.AdmsAgendamento;
import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.AdmsSubprocesso;
import entidade.FinEncargoDevido;
import entidade.FinTipoPagamento;
import entidade.SegHistoricoSessao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import managedBean.admsbean.AdmsDefinicoesParaClassesComAgendamentoBean;
import managedBean.segbean.SegLoginBean;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsSolicitacaoMedicaInternamentoBean extends AdmsSolitacaoNovaEditarGenericoBean implements Serializable
{
//    @EJB
//    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;

    /**
     * Creates a new instance of AdmsSolicitacaoMedicaInternamentoBean
     */
    
//    private AdmsServicoSolicitado servicoAnterior;
    private AdmsServicoEfetuado servicoEfetuado;
    
    private boolean pararAtualizacao = false;
    
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession sessao = request.getSession();
    
    public AdmsSolicitacaoMedicaInternamentoBean()
    {
    }
    
    public static AdmsSolicitacaoMedicaInternamentoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsSolicitacaoMedicaInternamentoBean admsSolicitacaoMedicaBean = 
            (AdmsSolicitacaoMedicaInternamentoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsSolicitacaoMedicaInternamentoBean");
        
        return admsSolicitacaoMedicaBean;
    }
    
    public void create()
    {     
        try
        {
            userTransaction.begin();
                solicitacao.setData(new Date());
                definirFuncionarioSolicitante();
                definirAgendamentoParaServicosDeExame();
                admsSolicitacaoFacade.create(solicitacao);
                gravarAgendamentos();
            userTransaction.commit();
            Mensagem.sucessoMsg("Solicitações Adicionadas Com Sucesso!");
            
            agendamentos = null;
            agendamentos = new ArrayList<AdmsAgendamento>();
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
    
    
    public void definirAgendamentoParaServicosDeExame()
    {
        for(AdmsServicoSolicitado servicoSolicitadoLocal:solicitacao.getAdmsServicoSolicitadoList())
        {
            if(servicoSolicitadoLocal.getFkIdServico().getFkIdTipoServico().getDescricaoTipoServico().equalsIgnoreCase("Exame"))
            {
                definirAgendamentoParaHoje(servicoSolicitadoLocal);
            }
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
    
    
    @Override
    public void gravarAgendamentos()
    {
        if(agendamentos == null || agendamentos.isEmpty()) return;
        for(int i = 0; i < agendamentos.size(); i++)
        {
            admsAgendamentoFacade.create(agendamentos.get(i));
//            admsAgendamentoFacade.create(agendamento);
            System.out.println("servico que vai criar encargo "+agendamentos.get(i).getFkIdServicoSolicitado().getFkIdServico().getNomeServico());
//            criarEncargoParaOServicoAgendado(agendamentos.get(i).getFkIdServicoSolicitado());
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
//        SegHistoricoSessao sessaoActual = (SegHistoricoSessao) sessao.getAttribute("sessaoActual");
        solicitacao.setFkIdFuncionarioSolicitante(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
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
        if(solicitacaoValida())
        {
            create();
            limparDepoisDaGravacao();
            definirDadosDaSolicitacaoAnterior();
        }
    }
    
    
    public boolean solicitacaoValida()
    {
//        System.out.println("tamanho da lista "+solicitacao.getAdmsServicoSolicitadoList().size());
        if(solicitacao.getAdmsServicoSolicitadoList().isEmpty())
        {
            Mensagem.erroMsg("Pelo Menos Um Serviço Deve Ser Adicionado Para Se Gravar a Solicitação!");
            return false;
        }
//        if(solicitacao.getFkIdResponsavelPaciente().getNomeCompleto().trim().equalsIgnoreCase(""))
//        {
//            if(!solicitacao.getFkIdResponsavelPaciente().getTelefone1().trim().equalsIgnoreCase("") 
//                || solicitacao.getFkIdResponsavelPaciente().getFkIdGrauParentesco().getPkIdGrauParentesco() != null)
//            {
//                Mensagem.erroMsg("O Responsável Não Passui Um Nome, Insira o Nome, Ou Remova Todos os Dados do Responsável");
//                return false;
//            }
//        }
        return true;
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
    public void definirValorASerPagoPeloTipo(FinEncargoDevido encargo, AdmsServicoSolicitado servicoSolicitadoLocal)
    {
        FinTipoPagamento tipo =  admsTipoPagamentoSubprocessoFacade.findTipoPagamentoBySubprocesso(servicoSolicitadoLocal.getFkIdSolicitacao().getFkIdSubprocesso());
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
    
    
    public void setPararAtualizacao(boolean pararAtualizacao)
    {
//System.out.println("Parou");
        this.pararAtualizacao = pararAtualizacao;
    }
    
    public boolean isPararAtualizacao()
    {
        return pararAtualizacao;
    }
    
}
