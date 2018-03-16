/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.adms;

import entidade.FinTipoPagamento;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoEditarPagarBean;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoMedicaAmbulatorioBean;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoMedicaAmbulatorioDoMenuBean;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoMedicaInternamentoBean;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoNovaBean;
import managedBean.admsbean.solicitacoes.AdmsSolicitacaoPacientePesquisarBean;
import managedBean.admsbean.solicitacoes.AdmsSolicitaoPesquisarBean;
import managedBean.finbean.FinPagamentoBean;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class DetetorAtualizacaoPagina implements Serializable
{

    /**
     * Creates a new instance of DetetorAtualizacaoPagina
     */
    public DetetorAtualizacaoPagina()
    {
    }

    int vez = 0;

    private String previousPage = null;

    public void checkAtualizacao(String pagina)
    {
        String msg = "";
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        String id = viewRoot.getViewId();
        if (previousPage == null)
        {
            msg = "First page ever";
            if (pagina.equalsIgnoreCase("solicitacaoEditar"))
            {
                getAdmsSolicitacaoEditarPagarBean().limparSolicitacao();
                getAdmsSolicitacaoEditarPagarBean().inicializaServicoNovos();
                getAdmsSolicitacaoEditarPagarBean().setPararAtualizacao(false);
            }
            if (pagina.equalsIgnoreCase("pesquisaSolicitacoes"))
            {
                limparSolicitacaoJaExistenteBean();
            }

            if (pagina.equalsIgnoreCase("pesquisaSolicitacoesGerais"))
            {
                limparSolicitacoesGeraiseBean();
            }

            if (pagina.equalsIgnoreCase("pagamento"))
            {
                carregarDadosPagamento();
//                getPagamentoBean().setPararAtualizacao(false);
            }

            if (pagina.equalsIgnoreCase("solicitacao"))
            {
                limparSolicitacaoServicoComAgendamentos();
            }

            if (pagina.equalsIgnoreCase("solicitacaoEditar"))
            {
                limparSolicitacaoEditar();
            }

            if (pagina.equalsIgnoreCase("solicitacaoInternamentoMedica"))
            {
                limparSolicitacaoMedicaInternamentoBean();
            }
            

            if (pagina.equalsIgnoreCase("solicitacaoMedicaAmbulatorio"))
            {
                limparSolicitacaoMedicaAmbulatorioBean();
            }
            
            if (pagina.equalsIgnoreCase("solicitacaoMedicaAmbulatorioDoMenu"))
            {
                limparSolicitacaoMedicaAmbulatorioDoMenuBean();
            }

        } else if (previousPage.equals(id))
        {
            msg = "F5 or reload";
            if (pagina.equalsIgnoreCase("solicitacao"))
            {
                limparSolicitacaoServico();
            }

            if (pagina.equalsIgnoreCase("pagamento"))
            {
                limparPagamento();
                getPagamentoBean().setPararAtualizacao(false);
            }

            if (pagina.equalsIgnoreCase("solicitacaoEditar"))
            {
                limparSolicitacaoEditar();
                getAdmsSolicitacaoEditarPagarBean().removerAgendamentosOrfaos();
                getAdmsSolicitacaoEditarPagarBean().setPararAtualizacao(false);
            }

            if (pagina.equalsIgnoreCase("pesquisaSolicitacoes"))
            {
                limparSolicitacaoJaExistenteBean();
            }

            if (pagina.equalsIgnoreCase("pesquisaSolicitacoesGerais"))
            {
                limparSolicitacoesGeraiseBean();
            }

            if (pagina.equalsIgnoreCase("solicitacaoInternamentoMedica"))
            {
                limparSolicitacaoMedicaInternamentoBean();
            }
            
            if (pagina.equalsIgnoreCase("solicitacaoMedicaAmbulatorioDoMenu"))
            {
                limparSolicitacaoMedicaAmbulatorioDoMenuBean();
            }

            if (pagina.equalsIgnoreCase("solicitacaoMedicaAmbulatorio"))
            {
                limparSolicitacaoMedicaAmbulatorioBean();
            } else
            {
                vez = 0;
            }
        } else if (FacesContext.getCurrentInstance().isPostback())
        {
            msg = "It's a postback";
            System.out.println("postback");
        } else
        {
            msg = "It's a navigation";

            if (pagina.equalsIgnoreCase("solicitacao"))
            {
                limparSolicitacaoServicoComAgendamentos();
            }

            if (pagina.equalsIgnoreCase("pagamento"))
            {
                carregarDadosPagamento();
//                getPagamentoBean().setPararAtualizacao(false);
            }

            if (pagina.equalsIgnoreCase("solicitacaoEditar"))
            {
                getAdmsSolicitacaoEditarPagarBean().limparSolicitacao();
                getAdmsSolicitacaoEditarPagarBean().inicializaServicoNovos();
                getAdmsSolicitacaoEditarPagarBean().setPararAtualizacao(false);
            }
            if (pagina.equalsIgnoreCase("pesquisaSolicitacoes"))
            {
                limparSolicitacaoJaExistenteBean();
            }

            if (pagina.equalsIgnoreCase("pesquisaSolicitacoesGerais"))
            {
                limparSolicitacoesGeraiseBean();
            }

//            if(pagina.equalsIgnoreCase("pagamento"))
//            {
//                carregarDadosPagamento();
//            }
            if (pagina.equalsIgnoreCase("solicitacaoEditar"))
            {
                limparSolicitacaoEditar();
            }

            if (pagina.equalsIgnoreCase("solicitacaoInternamentoMedica"))
            {
                limparSolicitacaoMedicaInternamentoBean();
            }

            if (pagina.equalsIgnoreCase("solicitacaoMedicaAmbulatorio"))
            {
                limparSolicitacaoMedicaAmbulatorioBean();
            }
            
            if (pagina.equalsIgnoreCase("solicitacaoMedicaAmbulatorioDoMenu"))
            {
                limparSolicitacaoMedicaAmbulatorioDoMenuBean();
            }

        }

        previousPage = id;
    }

    public void limparSolicitacaoServico()
    {
        AdmsSolicitacaoNovaBean admsSolicitacaoNovaBean = getSolicitacaoBean();
        admsSolicitacaoNovaBean.setServicoPesquisa(null);
        admsSolicitacaoNovaBean.limparDeServicoParaBaixo();
        admsSolicitacaoNovaBean.setPararAtualizacao(false);
    }

    public void limparSolicitacaoServicoComAgendamentos()
    {
        AdmsSolicitacaoNovaBean admsSolicitacaoNovaBean = getSolicitacaoBean();
        admsSolicitacaoNovaBean.setServicoPesquisa(null);
        admsSolicitacaoNovaBean.limparDeServicoParaBaixoComOsAgendamentos();
        admsSolicitacaoNovaBean.setPararAtualizacao(false);
    }

    public void limparPagamento()
    {
        FinPagamentoBean finPagamentoBean = getPagamentoBean();
        finPagamentoBean.limparDados();
//        finPagamentoBean.setTipoPagamento(new FinTipoPagamento());
        if (finPagamentoBean.getPagamento().getFkIdFormaPagamento() != null && finPagamentoBean.getPagamento().getFkIdFormaPagamento().getPkIdFormaPagamento() != null)
        {
            finPagamentoBean.inicializaFormaDePagamento();
            finPagamentoBean.definirFormasParaFalse();
        }
    }

    public void carregarDadosPagamento()
    {
        FinPagamentoBean finPagamentoBean = getPagamentoBean();
        finPagamentoBean.carregarDadosPagamento();
    }

    private void limparSolicitacaoEditar()
    {
        AdmsSolicitacaoEditarPagarBean admsSolicitacaoEditarPagarBean = getAdmsSolicitacaoEditarPagarBean();
        admsSolicitacaoEditarPagarBean.limparDeServicoParaBaixo();
    }

    private void limparSolicitacaoJaExistenteBean()
    {
        AdmsSolicitacaoPacientePesquisarBean admsSolicitacoesBean = getAdmsSolicitacoesBean();
        admsSolicitacoesBean.limparSolicitacoes();
    }

    private void limparSolicitacoesGeraiseBean()
    {
        AdmsSolicitaoPesquisarBean admsSolicitacoesGeraisBean = getAdmsSolicitacoesGeraisBean();
        admsSolicitacoesGeraisBean.limparSolicitacoes();
    }

    private void limparSolicitacaoMedicaInternamentoBean()
    {
        AdmsSolicitacaoMedicaInternamentoBean admsSolicitacaoMedicaBean = getAdmsSolicitacaoMedicaInternamentoBean();
        admsSolicitacaoMedicaBean.limparDeServicoParaBaixo();
    }

    private void limparSolicitacaoMedicaAmbulatorioBean()
    {
        AdmsSolicitacaoMedicaAmbulatorioBean admsSolicitacaoMedicaBean = getAdmsSolicitacaoMedicaAmbulatorioBean();
        admsSolicitacaoMedicaBean.limparDeServicoParaBaixo();
    }

    private void limparSolicitacaoMedicaAmbulatorioDoMenuBean()
    {
        AdmsSolicitacaoMedicaAmbulatorioDoMenuBean admsSolicitacaoMedicaDoMenuBean = getAdmsSolicitacaoMedicaAmbulatorioDoMenuBean();
        admsSolicitacaoMedicaDoMenuBean.limparDeServicoParaBaixo();
    }

    private AdmsSolicitacaoNovaBean getSolicitacaoBean()
    {
        return (AdmsSolicitacaoNovaBean) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance().getELContext(), null, "admsSolicitacaoNovaBean");
    }

    private FinPagamentoBean getPagamentoBean()
    {
        return (FinPagamentoBean) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance().getELContext(), null, "finPagamentoBean");
    }

    private AdmsSolicitacaoEditarPagarBean getAdmsSolicitacaoEditarPagarBean()
    {
        return (AdmsSolicitacaoEditarPagarBean) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance().getELContext(), null, "admsSolicitacaoEditarPagarBean");
    }

    private AdmsSolicitacaoPacientePesquisarBean getAdmsSolicitacoesBean()
    {
        return (AdmsSolicitacaoPacientePesquisarBean) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance().getELContext(), null, "admsSolicitacaoPacientePesquisarBean");
    }

    private AdmsSolicitaoPesquisarBean getAdmsSolicitacoesGeraisBean()
    {
        return (AdmsSolicitaoPesquisarBean) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance().getELContext(), null, "admsSolicitaoPesquisarBean");
    }

    private AdmsSolicitacaoMedicaInternamentoBean getAdmsSolicitacaoMedicaInternamentoBean()
    {
        return (AdmsSolicitacaoMedicaInternamentoBean) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance().getELContext(), null, "admsSolicitacaoMedicaInternamentoBean");
    }

    private AdmsSolicitacaoMedicaAmbulatorioBean getAdmsSolicitacaoMedicaAmbulatorioBean()
    {
        return (AdmsSolicitacaoMedicaAmbulatorioBean) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance().getELContext(), null, "admsSolicitacaoMedicaAmbulatorioBean");
    }

    private AdmsSolicitacaoMedicaAmbulatorioDoMenuBean getAdmsSolicitacaoMedicaAmbulatorioDoMenuBean()
    {
        return (AdmsSolicitacaoMedicaAmbulatorioDoMenuBean) FacesContext.getCurrentInstance()
                .getELContext().getELResolver()
                .getValue(FacesContext.getCurrentInstance().getELContext(), null, "admsSolicitacaoMedicaAmbulatorioDoMenuBean");
    }

}
