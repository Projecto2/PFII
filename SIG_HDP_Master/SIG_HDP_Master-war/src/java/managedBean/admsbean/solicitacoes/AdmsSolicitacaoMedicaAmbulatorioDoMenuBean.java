/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.admsbean.solicitacoes;

import entidade.AdmsAgendamento;
import entidade.AdmsPaciente;
import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.FinEncargoDevido;
import entidade.FinTipoPagamento;
import entidade.SegHistoricoSessao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import managedBean.admsbean.AdmsDefinicoesParaClassesComAgendamentoBean;
import managedBean.segbean.SegLoginBean;
import sessao.AdmsPacienteFacade;
import sessao.AdmsSubprocessoFacade;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AdmsSolicitacaoMedicaAmbulatorioDoMenuBean extends AdmsSolitacaoNovaEditarGenericoBean implements Serializable
{

    @EJB
    private AdmsPacienteFacade admsPacienteFacade;
    @EJB
    private AdmsSubprocessoFacade admsSubprocessoFacade;

//    @EJB
//    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;
    /**
     * Creates a new instance of AdmsSolicitacaoMedicaInternamentoBean
     */
//    private AdmsServicoSolicitado servicoAnterior;
    private AdmsServicoEfetuado servicoEfetuado;

    private Integer subProcesso;

    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession sessao = request.getSession();

    private boolean pararAtualizacao = false;

    public AdmsSolicitacaoMedicaAmbulatorioDoMenuBean()
    {
    }

    public static AdmsSolicitacaoMedicaAmbulatorioDoMenuBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsSolicitacaoMedicaAmbulatorioDoMenuBean admsSolicitacaoMedicaAmbulatorioDoMenuBean
                = (AdmsSolicitacaoMedicaAmbulatorioDoMenuBean) context.getELContext().
                getELResolver().getValue(FacesContext.getCurrentInstance().
                        getELContext(), null, "admsSolicitacaoMedicaAmbulatorioDoMenuBean");

        return admsSolicitacaoMedicaAmbulatorioDoMenuBean;
    }

    public void create() throws SystemException
    {
        try
        {
            userTransaction.begin();
            solicitacao.setData(new Date());
            definirFuncionarioSolicitante();
            definirAgendamentoParaServicosDeConsulta();//Depois tirar essa linha daqui
            definirAgendamentoParaServicosDeExame();
            definirAgendamentoParaServicosDeInternamento();
            admsSolicitacaoFacade.create(solicitacao);
            gravarAgendamentos();
            userTransaction.commit();
            Mensagem.sucessoMsg("Solicitacoes Adicionadas com sucesso!");

            agendamentos = null;
            agendamentos = new ArrayList<AdmsAgendamento>();
        } catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

    public void definirAgendamentoParaServicosDeConsulta()
    {
        for (AdmsServicoSolicitado servicoSolicitadoLocal : solicitacao.getAdmsServicoSolicitadoList())
        {
            if (servicoSolicitadoLocal.getFkIdServico().getFkIdTipoServico().getDescricaoTipoServico().equalsIgnoreCase("Consulta"))
            {
                definirAgendamentoParaHoje(servicoSolicitadoLocal);
            }
        }
    }    
    
    public void definirAgendamentoParaServicosDeExame()
    {
        for (AdmsServicoSolicitado servicoSolicitadoLocal : solicitacao.getAdmsServicoSolicitadoList())
        {
            if (servicoSolicitadoLocal.getFkIdServico().getFkIdTipoServico().getDescricaoTipoServico().equalsIgnoreCase("Exame"))
            {
                definirAgendamentoParaHoje(servicoSolicitadoLocal);
            }
        }
    }

    public void definirAgendamentoParaServicosDeInternamento()
    {
        for (AdmsServicoSolicitado servicoSolicitadoLocal : solicitacao.getAdmsServicoSolicitadoList())
        {
            if (servicoSolicitadoLocal.getFkIdServico().getFkIdTipoServico().getDescricaoTipoServico().equalsIgnoreCase("Internamento"))
            {
                definirAgendamentoParaHoje(servicoSolicitadoLocal);
            }
        }
    }

    public void definirAgendamentoParaHoje(AdmsServicoSolicitado servicoSolicitado)
    {
        if (agendamentos == null)
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
        try
        {
            userTransaction.begin();
            removerServicoSolicitadoSeuAgendamentoEDescontarValorDoPreco(servicoRemover);
            if (remocaoPersistente)
            {
                admsSolicitacaoFacade.edit(solicitacao);
            } else
            {
                numeroServicosNovosAdicionados--;
            }

            userTransaction.commit();
            numeroServicosNovosAdicionados = 0;
        } catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            } catch (IllegalStateException | SecurityException | SystemException ex)
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
        for (AdmsServicoSolicitado servico : solicitacao.getAdmsServicoSolicitadoList())
        {
            if (servico.getPkIdServicoSolicitado() == null)
            {
                return false;
            } else if (servico.getFkIdEstadoPagamento().getDescricaoEstadoPagamento().equalsIgnoreCase("Pendente"))
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

    public void setProcesso(String processo)
    {
        AdmsPaciente pacienteLocal = admsPacienteFacade.findPacienteByNumeroPaciente(processo);
        if (pacienteLocal != null)
        {
            solicitacao.setFkIdPaciente(pacienteLocal);
        } else
        {
            Mensagem.erroMsg("Processo de Paciente Inexistente");
        }
    }

    public String getProcesso()
    {
        if (solicitacao.getFkIdPaciente() == null)
        {
            solicitacao.setFkIdPaciente(new AdmsPaciente());
        }
        return solicitacao.getFkIdPaciente().getNumeroPaciente();
    }

    public void setSubProcesso(Integer subProcesso)
    {
        if (solicitacao.getFkIdPaciente().getPkIdPaciente() != null)
        {
            if (admsSubprocessoFacade.subProcessoExiste(subProcesso))
            {
                AdmsSolicitacao solicitacaoLocal = admsSolicitacaoFacade.findUltimaSolicitacaoBySubProcesso(subProcesso, solicitacao.getFkIdPaciente().getPkIdPaciente());
                if (solicitacaoLocal != null)
                {
                    solicitacao.setFkIdCentro(solicitacaoLocal.getFkIdCentro());
                    solicitacao.setFkIdResponsavelPaciente(solicitacaoLocal.getFkIdResponsavelPaciente());
                    solicitacao.setFkIdSubprocesso(solicitacaoLocal.getFkIdSubprocesso());
                } else
                {
                    Mensagem.erroMsg("Esta Solicitação Deve Ser Feita Nas Admissões, Pois o SubProcesso Não Possui Antecedentes");
                }
                //            this.subProcesso = subProcesso;

            } else
            {
                Mensagem.erroMsg("Este SubProcesso Não Existe, Insere Um Outro SubProcesso");
            }
        }
    }

    public Integer getSubProcesso()
    {
//        if(subProcesso == null)
//        {
//            subProcesso = new Integer
//        }
        return solicitacao.getFkIdSubprocesso().getNumeroSubprocesso();
    }

    public void gravarSolicitacao() throws SystemException
    {
        create();
        limparDepoisDaGravacao();
//        definirDadosDaSolicitacaoAnterior();
    }

    public void limparDepoisDaGravacao()
    {
        String processo = solicitacao.getFkIdPaciente().getNumeroPaciente();
        Integer subprocessoLocal = solicitacao.getFkIdSubprocesso().getNumeroSubprocesso();

        limparSolicitacao();
        valorTotalDoPreco = 0;
        fatorDeMultiplicacao = 1;
        servicoPesquisa = null;
        servicoSolicitado = null;
//        servicosLista = null;
        getServicoSolicitado();
        limparDeServicoParaBaixo();

        setProcesso(processo);
        setSubProcesso(subprocessoLocal);
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
        FinTipoPagamento tipo = admsTipoPagamentoSubprocessoFacade.findTipoPagamentoBySubprocesso(servicoSolicitadoLocal.getFkIdSolicitacao().getFkIdSubprocesso());
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
