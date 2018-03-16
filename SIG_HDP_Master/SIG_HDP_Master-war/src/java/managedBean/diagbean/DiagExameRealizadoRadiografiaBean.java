/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
import entidade.DiagCategoriaExame;
import entidade.DiagExame;
import entidade.DiagExameRealizado;
import entidade.RhFuncionario;
import entidade.SegConta;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.admsbean.AdmsServicoEfetuadoBean;
import managedBean.admsbean.servicosSolicitadosBean.AdmsAgendamentoBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import managedBean.segbean.SegLoginBean;
import sessao.AdmsClassificacaoServicoSolicitadoFacade;
import sessao.AdmsServicoEfetuadoFacade;
import sessao.DiagCategoriaExameFacade;
import sessao.DiagExameFacade;
import sessao.DiagExameRealizadoFacade;
import sessao.RhFuncionarioFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagExameRealizadoRadiografiaBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AdmsClassificacaoServicoSolicitadoFacade admsClassificacaoServicoSolicitadoFacade;

    @EJB
    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;

    @EJB
    private DiagCategoriaExameFacade diagCategoriaExameFacade;

    @EJB
    private DiagExameFacade diagExameFacade;

    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;

    @EJB
    private DiagExameRealizadoFacade diagExameRealizadoFacade;

    private DiagExame diagExame;
    private DiagExameRealizado diagExameRealizado, diagExameRealizadoPesquisar, diagExameRealizadoVisualizar;
    private AdmsServicoSolicitado admsServicoSolicitado, admsServicoSolicitadoVisualizar;

    private AdmsServicoEfetuado admsServicoEfetuado;

    private List<DiagExame> listaExames;

    private Date dataInicioPesquisa, dataFimPesquisa;

    private boolean pesquisar;

    private SegLoginBean segLoginBean = new SegLoginBean();

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    public static DiagExameRealizadoRadiografiaBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagExameRealizadoRadiografiaBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagExameRealizadoRadiografiaBean");
    }

    public static DiagExameRealizado getInstancia()
    {
        DiagExameRealizado diagExameRealizado = new DiagExameRealizado();
        diagExameRealizado.setFkIdServicoSolicitado(AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado());
        diagExameRealizado.setFkIdExame(DiagExameBean.getInstancia());
        diagExameRealizado.setFkIdAmostra(DiagAmostraBean.getInstancia());
        diagExameRealizado.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return diagExameRealizado;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public Date getDataInicioPesquisa()
    {
        return dataInicioPesquisa;
    }

    public void setDataInicioPesquisa(Date dataInicioPesquisa)
    {
        this.dataInicioPesquisa = dataInicioPesquisa;
    }

    public Date getDataFimPesquisa()
    {
        return dataFimPesquisa;
    }

    public void setDataFimPesquisa(Date dataFimPesquisa)
    {
        this.dataFimPesquisa = dataFimPesquisa;
    }

    public DiagExameRealizado getDiagExameRealizadoPesquisar()
    {
        if (diagExameRealizadoPesquisar == null)
        {
            diagExameRealizadoPesquisar = getInstancia();
        }
        return diagExameRealizadoPesquisar;
    }

    public void setDiagExameRealizadoPesquisar(DiagExameRealizado exameRealizado)
    {
        this.diagExameRealizadoPesquisar = exameRealizado;
    }

    public List<DiagExame> getListaExames()
    {
        return listaExames;
    }

    public void setListaExames(List<DiagExame> listaExames)
    {
        this.listaExames = listaExames;
    }

    public DiagExameRealizado getDiagExameRealizado()
    {
        if (diagExameRealizado == null)
        {
            diagExameRealizado = getInstancia();
        }
        return diagExameRealizado;
    }

    public void setDiagExameRealizado(DiagExameRealizado diagExameRealizado)
    {
        this.diagExameRealizado = diagExameRealizado;
    }

    public DiagExameRealizado getDiagExameRealizadoVisualizar()
    {
        return diagExameRealizadoVisualizar;
    }

    public void setDiagExameRealizadoVisualizar(DiagExameRealizado diagExameRealizadoVisualizar)
    {
        this.diagExameRealizadoVisualizar = diagExameRealizadoVisualizar;
    }

    public void selecionarExameRealizadoVisualizar(DiagExameRealizado exameRealizadoVisualizar)
    {
        this.diagExameRealizadoVisualizar = exameRealizadoVisualizar;
    }

    public AdmsServicoSolicitado getAdmsServicoSolicitado()
    {
        if (admsServicoSolicitado == null)
        {
            admsServicoSolicitado = AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado();
        }
        return admsServicoSolicitado;
    }

    public void setAdmsServicoSolicitado(AdmsServicoSolicitado admsServicoSolicitado)
    {
        this.admsServicoSolicitado = admsServicoSolicitado;
    }

    public AdmsServicoSolicitado getAdmsServicoSolicitadoVisualizar()
    {
        if (admsServicoSolicitadoVisualizar == null)
        {
            admsServicoSolicitadoVisualizar = AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado();
        }
        return admsServicoSolicitadoVisualizar;
    }

    public void setAdmsServicoSolicitadoVisualizar(AdmsServicoSolicitado admsServicoSolicitadoVisualizar)
    {
        this.admsServicoSolicitadoVisualizar = admsServicoSolicitadoVisualizar;
    }

    public DiagExame getDiagExame()
    {
        if (diagExame == null)
        {
            diagExame = new DiagExame();
        }
        return diagExame;
    }

    public void setDiagExame(DiagExame diagExame)
    {
        this.diagExame = diagExame;
    }

    public boolean isTemMensagemPendente()
    {
        return temMensagemPendente;
    }

    public void setTemMensagemPendente(boolean temMensagemPendente)
    {
        this.temMensagemPendente = temMensagemPendente;
    }

    public String getMensagemPendente()
    {
        return mensagemPendente;
    }

    public void setMensagemPendente(String mensagemPendente)
    {
        this.mensagemPendente = mensagemPendente;
    }

    public String getTipoMensagemPendente()
    {
        return tipoMensagemPendente;
    }

    public void setTipoMensagemPendente(String tipoMensagemPendente)
    {
        this.tipoMensagemPendente = tipoMensagemPendente;
    }

    public List<DiagCategoriaExame> findAllCategoriaExames()
    {
        return diagCategoriaExameFacade.findAll();
    }

    public List<AdmsClassificacaoServicoSolicitado> findAllClassificacaoServicoSolicitado()
    {
        return admsClassificacaoServicoSolicitadoFacade.findAll();
    }

    public List<RhFuncionario> findAllFuncionarios()
    {
        return rhFuncionarioFacade.findAll();
    }

    public void carregarListaExamesPorCategoria()
    {
        listaExames = diagExameFacade.findExamesByCategoria(diagExameRealizadoPesquisar.getFkIdExame().getFkIdCategoriaExame());
    }

    public void selecionarExameRadiografiaRealizar(AdmsServicoSolicitado servicoSolicitadoAux)
    {
        this.admsServicoSolicitado = servicoSolicitadoAux;
    }

    public void getMensagem()
    {
        if (tipoMensagemPendente == "Sucesso")
        {
            Mensagem.sucessoMsg(mensagemPendente);

            temMensagemPendente = false;
        }
        else
        {
            Mensagem.erroMsg(mensagemPendente);

            temMensagemPendente = false;
        }
    }

    public String create()
    {
        GregorianCalendar data = new GregorianCalendar();

        try
        {
            userTransaction.begin();

            diagExame = new DiagExame();
            diagExame = diagExameFacade.findExamePorNomeServicoSolicitado(admsServicoSolicitado);

            diagExameRealizado.setFkIdExame(diagExame);
            diagExameRealizado.setData(new Date(data.getTimeInMillis()));

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession sessao = request.getSession();
            SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");
            
            diagExameRealizado.setFkIdFuncionario(sessaoActual.getFkIdFuncionario());

            diagExameRealizado.setFkIdServicoSolicitado(admsServicoSolicitado);

            if (diagExameRealizado.getFkIdAmostra().getPkIdAmostra() == null)
            {
                diagExameRealizado.setFkIdAmostra(null);
            }

            diagExameRealizadoFacade.create(diagExameRealizado);

            userTransaction.commit();

            admsServicoEfetuado = new AdmsServicoEfetuado();
            admsServicoEfetuado.setCodigoTabelaBusca(BigInteger.valueOf(diagExameRealizado.getPkIdExameRealizado().intValue()));
            admsServicoEfetuado.setDescricaoTabelaBusca("Exame");
            admsServicoEfetuado.setDataEfetuada(new Date());
            admsServicoEfetuado.setFkIdServicoSolicitado(admsServicoSolicitado);

            admsServicoEfetuadoFacade.create(admsServicoEfetuado);

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Exame realizado com sucesso!";

            diagExame = DiagExameBean.getInstancia();
            diagExameRealizado = DiagExameRealizadoBean.getInstancia();
            admsServicoSolicitado = AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado();
            admsServicoEfetuado = AdmsServicoEfetuadoBean.getInstancia();
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }

        return limpar();
    }

    public List<DiagExameRealizado> pesquisarExamesRealizados()
    {
        if (pesquisar)
        {
            List<DiagExameRealizado> pp;
            pp = diagExameRealizadoFacade.findPesquisaExameRealizado(diagExameRealizadoPesquisar, dataInicioPesquisa, dataFimPesquisa);
            if (pp.isEmpty() || pp == null)
            {
                Mensagem.erroMsg("Nenhum registo encontrado para esta pesquisa");
            }

            return pp;
        }

        return null;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagExameRealizadoPesquisar = getInstancia();

        return "historicoExames.xhtml?faces-redirect=true";
    }

    public String limpar()
    {
        diagExameRealizado = DiagExameRealizadoBean.getInstancia();

        return "pedidosExames.xhtml?faces-redirect=true";
    }
}
