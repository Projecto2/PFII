/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.solicitacoes;

import entidade.AdmsAgendamento;
import entidade.AdmsAgendamentoMedico;
import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AdmsEstadoPagamento;
import entidade.AdmsPaciente;
import entidade.AdmsResponsavelPaciente;
import entidade.AdmsServico;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.AdmsSubprocesso;
import entidade.AdmsTipoSolicitacaoServico;
import entidade.AdmsCategoriaServico;
import entidade.AdmsTipoPagamentoSubprocesso;
import entidade.FinEncargoDevido;
import entidade.FinPrecoCategoriaServico;
import entidade.FinTipoPagamento;
import entidade.GrlCentroHospitalar;
import entidade.GrlGrauParentesco;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import managedBean.admsbean.AdmsDefinicoesParaClassesComAgendamentoBean;
import sessao.AdmsResponsavelPacienteFacade;
import sessao.AdmsSubprocessoFacade;
import sessao.AdmsTipoPagamentoSubprocessoFacade;
import sessao.FinTipoPagamentoFacade;
import util.Mensagem;
import util.adms.ConstantesAdms;
import util.inter.NotifyView;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsSolicitacaoNovaBean extends AdmsSolitacaoNovaEditarGenericoBean implements Serializable
{
    @EJB
    private AdmsTipoPagamentoSubprocessoFacade admsTipoPagamentoSubprocessoFacade;
    @EJB
    private FinTipoPagamentoFacade finTipoPagamentoFacade;
    @EJB
    private AdmsResponsavelPacienteFacade admsResponsavelPacienteFacade;
    @EJB
    private AdmsSubprocessoFacade admsSubprocessoFacade;
    
    
    
    private boolean pararAtualizacao = false;
    
    private FacesContext context = FacesContext.getCurrentInstance();
    
    private AdmsSolicitacao solicitacaoPesquisa;
    
    private AdmsServico servico;
    
    private ArrayList<AdmsServicoSolicitado> servicos;
    
    List<AdmsServico> servicosLista;
    
    private FinPrecoCategoriaServico valorPreco;
    
    private FinTipoPagamento tipoPagamento;
    
    private AdmsAgendamento novoAgendamento;
    private AdmsAgendamentoMedico novoAgendamentoMedico;
//    
    protected HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    protected HttpSession sessao = request.getSession();
    
//    private String responsavel = "";
    
//    private boolean
    
    
    /**
     * Creates a new instance of SolicitacaoBean
     */
    public AdmsSolicitacaoNovaBean()
    {        
    }
    
    public static AdmsSolicitacaoNovaBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsSolicitacaoNovaBean admsSolicitacaoNovaBean = 
            (AdmsSolicitacaoNovaBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsSolicitacaoNovaBean");
        
        return admsSolicitacaoNovaBean;
    }
//    
    public void inicializaServicoSolicitado()
    {
        servicoSolicitado = null;
        servicoSolicitado = new AdmsServicoSolicitado();
        servicoSolicitado.setFkIdClassificacaoServicoSolicitado(new AdmsClassificacaoServicoSolicitado());
        servicoSolicitado.setFkIdTipoSolicitacao(new AdmsTipoSolicitacaoServico());
    }
    

    public AdmsSolicitacao getSolicitacaoPesquisa()
    {
        if(solicitacaoPesquisa == null)
        {
            solicitacaoPesquisa = new AdmsSolicitacao();
            solicitacaoPesquisa.setFkIdCentro(new GrlCentroHospitalar());  
        }
        return this.solicitacaoPesquisa;
    }

    public void setSolicitacaoPesquisa(AdmsSolicitacao solicitacaoPesquisa)
    {
        this.solicitacaoPesquisa = solicitacaoPesquisa;
    }
    

    public ArrayList<AdmsServicoSolicitado> getServicosLista()
    {
        if(servicos == null)
            servicos = new ArrayList<>();
        return servicos;
    }

    public void setServicosLista(ArrayList<AdmsServicoSolicitado> servicos)
    {
        this.servicos = servicos;
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
        tipoPagamento = finTipoPagamentoFacade.find(tipoPagamento.getPkIdTipoPagamento());

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
    }
    
    public void changeTipoPagamento(ValueChangeEvent e)
   {
//       FinTipoPagamento tipoPagamento = new FinTipoPagamento();
       tipoPagamento = finTipoPagamentoFacade.find(Integer.parseInt(e.getNewValue().toString()));
//       valorTotalDoPreco = valorTotalDoPreco * tipoPagamento.getFatorDeMultiplicacao();
   }
    
    public void carregarPrecosServicos()
    {
        if(servico.getPkIdServico() != null)
        {
            listaPrecos = admsCategoriaServicoFacade.findCategoriasServicoAtivos(this.servico);
                preco = listaPrecos.get(0);
                getValorPreco();
        }
        else
        {
            listaPrecos = null;
            preco = null;
            valorPreco = null;
        }
        
    }

    
    public List<AdmsCategoriaServico> getPrecosServicos(/*String nomeServico*/)
    {
        if(servico.getPkIdServico() != null)
        {
            listaPrecos = admsCategoriaServicoFacade.findCategoriasServicoAtivos(this.servico);
            if(!listaPrecos.isEmpty())
                preco = listaPrecos.get(0);
            else{
                listaPrecos = null;
                preco = null;
                valorPreco = null;
            }
        }
        else
        {
            listaPrecos = null;
            preco = null;
            valorPreco = null;
        }
        return listaPrecos;
    }

    
    
    public void removerServicoSolicitado(AdmsServicoSolicitado servicoRemover)
    {
        removerServicoSolicitadoSeuAgendamentoEDescontarValorDoPreco(servicoRemover);
//        System.out.println("depois de remover o id do pk do tipo e"+servicoSolicitado.getFkIdTipoSolicitacao());
    }
    
    
    
    public void create()
    {
        
        AdmsSubprocesso subprocesso = new AdmsSubprocesso();
        try
        {
            userTransaction.begin();
                solicitacao.setData(new Date());
                
                subprocesso.setDataInicioSubprocesso(solicitacao.getData());
                subprocesso.setDataFimSubprocesso(solicitacao.getData());
                int subprocessoLong = admsSubprocessoFacade.findUltimoNumeroSubprocessoPaciente(solicitacao.getFkIdPaciente());
                subprocessoLong++;
                subprocesso.setNumeroSubprocesso(subprocessoLong);
                subprocesso.setFkIdPaciente(solicitacao.getFkIdPaciente());
                admsSubprocessoFacade.create(subprocesso);
                
                gravarTipoPagamentoSubprocesso(subprocesso);
                
                gravarResponsavel();
                
                definirFuncionarioSolicitante();
                adicionarSolicitacaoAosServicos();
                solicitacao.setFkIdSubprocesso(subprocesso);
                admsSolicitacaoFacade.create(solicitacao);
                gravarAgendamentos();
                gravarAgendamentosMedicos();
                
            userTransaction.commit();
            
            
            
            Mensagem.sucessoMsg("Solicitacao Salva Com Sucesso! O Número do SubProcesso É: "+subprocesso.getNumeroSubprocesso());
            
            solicitacao.setFkIdResponsavelPaciente(new AdmsResponsavelPaciente());
            solicitacao.getFkIdResponsavelPaciente().setFkIdGrauParentesco(new GrlGrauParentesco());
            
            if(agendamentos != null)
                agendamentos.clear();
            if(agendamentosMedicos != null)
                agendamentosMedicos.clear();
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
    
    
    public void gravarTipoPagamentoSubprocesso(AdmsSubprocesso subProcesso)
    {
        AdmsTipoPagamentoSubprocesso tipoPagamentoSubprocesso = new AdmsTipoPagamentoSubprocesso();
        tipoPagamentoSubprocesso.setFkIdSubprocesso(subProcesso);
        tipoPagamentoSubprocesso.setFkIdTipoPagamento(tipoPagamento);
        tipoPagamentoSubprocesso.setData(new Date());
        admsTipoPagamentoSubprocessoFacade.create(tipoPagamentoSubprocesso);
    }
    
    @Override
    public void definirValorASerPagoPeloTipo(FinEncargoDevido encargo, AdmsServicoSolicitado servicoSolicitadoLocal)
    { 
        if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("Preço 1"))
        {
            encargo.setValor(servicoSolicitadoLocal.getFkIdPrecoCategoriaServico().getValor());
        }
        if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("Preço 2"))
        {
            encargo.setValor(servicoSolicitadoLocal.getFkIdPrecoCategoriaServico().getValorPreco2());
        }
        if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("DP"))
        {
            encargo.setValor(servicoSolicitadoLocal.getFkIdPrecoCategoriaServico().getValorPrecoDp());
        }
        if (tipoPagamento.getDescricaoTipoPagamento().equalsIgnoreCase("DP-FS"))
        {
            encargo.setValor(servicoSolicitadoLocal.getFkIdPrecoCategoriaServico().getValorPrecoDpfs());
        }
    }
    
    
    public void gravarResponsavel()
    {
        if(solicitacao.getFkIdResponsavelPaciente().getNomeCompleto().trim().equalsIgnoreCase(""))
        {
            solicitacao.setFkIdResponsavelPaciente(null);
        }
        else
        {
            if(solicitacao.getFkIdResponsavelPaciente().getFkIdGrauParentesco().getPkIdGrauParentesco() == null)
            {
                solicitacao.getFkIdResponsavelPaciente().setFkIdGrauParentesco(null);
            }
            admsResponsavelPacienteFacade.create(solicitacao.getFkIdResponsavelPaciente());
        }
    }
    
    
    public boolean solicitacaoValida()
    {
//        System.out.println("tamanho da lista "+solicitacao.getAdmsServicoSolicitadoList().size());
        if(solicitacao.getAdmsServicoSolicitadoList().isEmpty())
        {
            Mensagem.erroMsg("Pelo Menos Um Servico Deve Ser Adicionado Para Se Gravar a Solicitação!");
            return false;
        }
        if(solicitacao.getFkIdResponsavelPaciente().getNomeCompleto().trim().equalsIgnoreCase(""))
        {
            if(!solicitacao.getFkIdResponsavelPaciente().getTelefone1().trim().equalsIgnoreCase("") 
                || solicitacao.getFkIdResponsavelPaciente().getFkIdGrauParentesco().getPkIdGrauParentesco() != null)
            {
                Mensagem.erroMsg("O Responsável Não Passui Um Nome, Insira o Nome, Ou Remova Todos os Dados do Responsável");
                return false;
            }
        }
        return true;
    }
    
    
    
    public void definirFuncionarioSolicitante()
    {
        SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");
        solicitacao.setFkIdFuncionarioSolicitante(sessaoActual.getFkIdFuncionario());
    }
    
    public void gravarSolicitacao()
    {
        if(solicitacaoValida())
        {
            create();
//            notificarAreasPrestamServicos();
            limparDepoisDaGravacao();
        }
    }
    
//    public void gravarEPagar()
//    {
//        create();
//        FinPagamentoBean.getInstanciaBean().setSolicitacao(solicitacao);
//        limparDepoisDaGravacao();
//    }
    
    public void adicionarSolicitacaoAosServicos() throws Exception
    {
        AdmsEstadoPagamento estadoPagamento = getEstadoPagamentoPendente();
        for(AdmsServicoSolicitado servicoSolicitadoLoca:solicitacao.getAdmsServicoSolicitadoList())
        {
            servicoSolicitadoLoca.setFkIdSolicitacao(solicitacao);
            servicoSolicitadoLoca.setFkIdEstadoPagamento(estadoPagamento);
        }
    }

    
    @Override
    public void gravarAgendamentos()
    {
        if(agendamentos == null || agendamentos.isEmpty()) return;
        for(int i = 0; i < agendamentos.size(); i++)
        {
            admsAgendamentoFacade.create(agendamentos.get(i));
//            admsAgendamentoFacade.create(agendamento);
//            System.out.println("servico que vai criar encargo "+agendamentos.get(i).getFkIdServicoSolicitado().getFkIdServico().getNomeServico());
//            criarEncargoParaOServicoAgendado(agendamentos.get(i).getFkIdServicoSolicitado());
            
        }
    }
    
    
    
    @Override
    public void gravarAgendamentosMedicos()
    {
        //int posicao;
        if(agendamentosMedicos == null || agendamentosMedicos.isEmpty()) return;
        for(int i = 0; i < agendamentosMedicos.size(); i++)
        {
            admsAgendamentoMedicoFacade.create(agendamentosMedicos.get(i));
        }
    }
    
    
    
    public AdmsEstadoPagamento getEstadoPagamentoPendente()
    {
        return admsEstadoPagamentoFacade.findPagamentoPendente();
    }
    
    public void definirPacienteSolicitacao(AdmsPaciente paciente)
    {
        limparDados();
//        getServico();
        getServicoSolicitado();
        inicializaServicoSolicitado();
        getServicosLista();
        getSolicitacao().setFkIdPaciente(paciente);
        valorTotalDoPreco = 0;
        fatorDeMultiplicacao = 1;
        tipoPagamento = null;
    }
    
    
    public void limparDados()
    {
        solicitacao = null;
        servico = null;
        servicos = null;
        servicoSolicitado = null;
        valorTotalDoPreco = 0;
        fatorDeMultiplicacao = 1;
        tipoPagamento = null;
    }
    

    public void setAgendamentos(ArrayList<AdmsAgendamento> agendamentos)
    {
        this.agendamentos = agendamentos;
    }

    
    public void setDadosDoAgendamento(AdmsServicoSolicitado admsServicoSolicitado)
    {
        if(agendamentos == null || agendamentos.isEmpty())
        {
            novoAgendamento = null;
            novoAgendamentoMedico = null;
            return;
        }
        int posicao = getPosicaoAgendamento(admsServicoSolicitado);
        if(posicao >= 0)
        {
            if(novoAgendamento == null) getNovoAgendamento();
            novoAgendamento.setDataHoraInicio(agendamentos.get(posicao).getDataHoraInicio());
            novoAgendamento.setDataHoraFim(agendamentos.get(posicao).getDataHoraFim());
            novoAgendamento.setFkIdServicoSolicitado(agendamentos.get(posicao).getFkIdServicoSolicitado());
            novoAgendamento.setFkIdEstadoAgendamento(agendamentos.get(posicao).getFkIdEstadoAgendamento());

            if(!(agendamentosMedicos == null || agendamentosMedicos.isEmpty()))
            {
                int posicaoMedico = getPosicaoAgendamentoMedico(novoAgendamento);
                if(posicaoMedico >= 0)
                {
                    if(novoAgendamentoMedico == null) getNovoAgendamentoMedico();
                    novoAgendamentoMedico.setFkIdMedico(agendamentosMedicos.get(posicaoMedico).getFkIdMedico());
                    novoAgendamentoMedico.setFkIdAgendamento(agendamentosMedicos.get(posicaoMedico).getFkIdAgendamento());
                }
                else{
                    novoAgendamentoMedico = null;
                }
            } else novoAgendamentoMedico = null;
        }
        else{
            novoAgendamento = null;
            novoAgendamentoMedico = null;
        }
    }
    

    public AdmsAgendamentoMedico getNovoAgendamentoMedico()
    {
        if(novoAgendamentoMedico == null)
        {
            novoAgendamentoMedico = new AdmsAgendamentoMedico();
        }
        return novoAgendamentoMedico;
    }
    
    

    public void setNovoAgendamentoMedico(AdmsAgendamentoMedico novoAgendamentoMedico)
    {
        this.novoAgendamentoMedico = novoAgendamentoMedico;
    }
    
    

    public AdmsAgendamento getNovoAgendamento()
    {
        if(novoAgendamento == null)
        {
            novoAgendamento = new AdmsAgendamento();
        }
        return novoAgendamento;
    }

    public void setNovoAgendamento(AdmsAgendamento novoAgendamento)
    {
        this.novoAgendamento = novoAgendamento;
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
    
    
    public boolean servicoJaPossuiAgendamento(AdmsServicoSolicitado servicoProcura)
    {
        if(agendamentos == null) return false;
        int posicaoAgendamento = getPosicaoAgendamento(servicoProcura);
        return posicaoAgendamento != -1;
    }
    
    
    public void marcarCheckInParaEsteServico(AdmsServicoSolicitado servicoProcura)
    {
        
        int posicaoAgendamento = getPosicaoAgendamento(servicoProcura);
        if(posicaoAgendamento == -1) return;
        agendamentos.get(posicaoAgendamento).setFkIdEstadoAgendamento(admsEstadoAgendamentoFacade.getEstadoAgendamentoChegou()); 
        agendamentos.get(posicaoAgendamento).setDataHoraCheckIn(new Date());
    }
    
    public boolean podeFazerCheckIn(AdmsServicoSolicitado servicoProcura)
    {
        if(agendamentos == null) return false;
        int posicaoAgendamento = getPosicaoAgendamento(servicoProcura);
        if(posicaoAgendamento == -1) return false;
        return AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().podeFazerCheckInNovaSolicitacao(agendamentos.get(posicaoAgendamento));
    }
    
    
    
    
    public void limparDepoisDaGravacao()
    {
        limparSolicitacaoManterPaciente();
        valorTotalDoPreco = 0;
        fatorDeMultiplicacao = 1;
        preco = new AdmsCategoriaServico();
        valorPreco = new FinPrecoCategoriaServico();
        servicoPesquisa = null;
        servicoSolicitado = null;
        novoAgendamento = null;
        novoAgendamentoMedico = null;
        servicosLista = null;
        getServicoSolicitado();
        limparDeServicoParaBaixo();
    }
    
    
    public void limparSolicitacaoManterPaciente()
    {
        AdmsPaciente paciente;
        paciente = solicitacao.getFkIdPaciente();
        solicitacao = null;
        getSolicitacao();
        solicitacao.setFkIdPaciente(paciente); 
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
    
    
    
    public String getCorAgendamentoNovaSolicitacao()
    {
        if(novoAgendamento != null)
        {
            if(novoAgendamento.getFkIdEstadoAgendamento() == null) return ConstantesAdms.PRETO;
            if(novoAgendamento.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equalsIgnoreCase("Chegou"))
                return ConstantesAdms.VERDE;
            else return ConstantesAdms.LARANJA;
        }
        return ConstantesAdms.PRETO;
    }
    
    
    public boolean temResponsavel()
    {
        return !solicitacao.getFkIdResponsavelPaciente().getNomeCompleto().trim().equalsIgnoreCase("");
    }
    
    
    public boolean podeAgendar()
    {
        return novoAgendamento != null;
    }
    
    
    public void notificarAreasPrestamServicos()
    {
        for (int i = 0; i < solicitacao.getAdmsServicoSolicitadoList().size(); i++)
            NotifyView.getInstanciaBean().send(solicitacao.getFkIdPaciente(), solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdServico().getNomeServico());
    }

    public FinTipoPagamento getTipoPagamento()
    {
        if(tipoPagamento == null)
        {
            List<FinTipoPagamento> lista = finTipoPagamentoFacade.findTipoPagamento(new FinTipoPagamento(0, "Preço 1"));
            tipoPagamento = new FinTipoPagamento();
//            tipoPagamento.setPkIdTipoPagamento(lista.get(0).getPkIdTipoPagamento());
//            tipoPagamento.setFatorDeMultiplicacao(lista.get(0).getFatorDeMultiplicacao());
            tipoPagamento = lista.get(0);
            
        }
        return tipoPagamento;
    }

    public void setTipoPagamento(FinTipoPagamento tipoPagamento)
    {
        this.tipoPagamento = tipoPagamento;
    }
    
    
}
