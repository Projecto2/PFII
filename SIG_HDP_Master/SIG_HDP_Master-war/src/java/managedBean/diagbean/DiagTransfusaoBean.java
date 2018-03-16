/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsSolicitacao;
import entidade.DiagRespostaRequisicaoComponenteSanguineo;
import entidade.DiagRespostaRequisicaoHasComponenteHasTeste;
import entidade.DiagTransfusao;
import entidade.InterCamaInternamento;
import entidade.InterRegistoInternamento;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
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
import managedBean.admsbean.paciente.AdmsPacienteNovoBean;
import managedBean.admsbean.servicosSolicitadosBean.AdmsAgendamentoBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import managedBean.segbean.SegLoginBean;
import sessao.DiagRespostaRequisicaoComponenteSanguineoFacade;
import sessao.DiagRespostaRequisicaoHasComponenteHasTesteFacade;
import sessao.DiagTransfusaoFacade;
import util.Mensagem;
import util.diag.Defs;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTransfusaoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagTransfusaoFacade diagTransfusaoFacade;
    @EJB
    private DiagRespostaRequisicaoComponenteSanguineoFacade diagRespostaRequisicaoComponenteSanguineoFacade;

    @EJB
    private DiagRespostaRequisicaoHasComponenteHasTesteFacade diagRespostaRequisicaoHasComponenteHasTesteFacade;

    private DiagTransfusao diagTransfusao, diagTransfusaoPesquisar, diagTransfusaoVisualizar;

    private SegLoginBean segLoginBean = new SegLoginBean();

    private boolean pesquisar;
    List<DiagTransfusao> itens;
    List<DiagRespostaRequisicaoHasComponenteHasTeste> listaBolsas;

    private int numeroRegistos = 10;

    private Date dataInicioPesquisaResultado, dataFimPesquisaResultado;

    /**
     * Creates a new instance of TransfusaoBean
     */
    public DiagTransfusaoBean()
    {

    }

    public static DiagTransfusaoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTransfusaoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTransfusaoBean");
    }

    public static DiagTransfusao getInstancia()
    {
        DiagTransfusao diagTransfusao = new DiagTransfusao();
        diagTransfusao.setFkIdRespostaRequisicaoComponenteSanguineo(DiagRespostaRequisicaoComponenteSanguineoBean.getInstancia());
        diagTransfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().setFkIdRegistoInternamento(new InterRegistoInternamento());
        diagTransfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().setFkIdCamaInternamento(new InterCamaInternamento());
        diagTransfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().setFkIdServicoSolicitado(AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado());
        diagTransfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().setFkIdSolicitacao(new AdmsSolicitacao());
        diagTransfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().setFkIdPaciente(AdmsPacienteNovoBean.getInstanciaBean().getInstanciaPaciente());
        diagTransfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdNacionalidade().setPkIdPais(Defs.NACIONALIDADE_DEFAULT_ID);
        diagTransfusao.getFkIdRespostaRequisicaoComponenteSanguineo().getFkIdRequisicaoComponenteSanguineo().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getFkIdSexo().setPkIdSexo(Defs.SEXO_DEFAULT_ID);
        diagTransfusao.setFkRealizadoPor(RhFuncionarioNovoBean.getInstancia());

        return diagTransfusao;
    }

    public DiagTransfusao getDiagTransfusaoPesquisar()
    {
        if (diagTransfusaoPesquisar == null)
        {
            diagTransfusaoPesquisar = getInstancia();
        }
        return diagTransfusaoPesquisar;
    }

    public void setDiagTransfusaoPesquisar(DiagTransfusao diagTransfusaoPesquisar)
    {
        this.diagTransfusaoPesquisar = diagTransfusaoPesquisar;
    }

    public DiagTransfusao getDiagTransfusaoVisualizar()
    {
        if (diagTransfusaoVisualizar == null)
        {
            diagTransfusaoVisualizar = getInstancia();
        }
        return diagTransfusaoVisualizar;
    }

    public void setDiagTransfusaoVisualizar(DiagTransfusao diagTransfusaoVisualizar)
    {
        this.diagTransfusaoVisualizar = diagTransfusaoVisualizar;

        //Pesquisar diag_resposta_requisicao_has_componente_has_teste com a chave da resposta: fk_id_resposta_requisicao_has_componente
        listaBolsas = diagRespostaRequisicaoHasComponenteHasTesteFacade.findBolsas(diagTransfusaoVisualizar.getFkIdRespostaRequisicaoComponenteSanguineo().getPkIdRespostaRequisicaoComponenteSanguineo());
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagTransfusao> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagTransfusao> itens)
    {
        this.itens = itens;
    }

    public List<DiagRespostaRequisicaoHasComponenteHasTeste> getListaBolsas()
    {
        if (listaBolsas == null)
        {
            listaBolsas = new ArrayList<>();
        }
        return listaBolsas;
    }

    public void setListaBolsas(List<DiagRespostaRequisicaoHasComponenteHasTeste> listaBolsas)
    {
        this.listaBolsas = listaBolsas;
    }

    public int getNumeroRegistos()
    {
        return numeroRegistos;
    }

    public void setNumeroRegistos(int numeroRegistos)
    {
        this.numeroRegistos = numeroRegistos;
    }

    public Date getDataInicioPesquisaResultado()
    {
        return dataInicioPesquisaResultado;
    }

    public void setDataInicioPesquisaResultado(Date dataInicioPesquisaResultado)
    {
        this.dataInicioPesquisaResultado = dataInicioPesquisaResultado;
    }

    public Date getDataFimPesquisaResultado()
    {
        return dataFimPesquisaResultado;
    }

    public void setDataFimPesquisaResultado(Date dataFimPesquisaResultado)
    {
        this.dataFimPesquisaResultado = dataFimPesquisaResultado;
    }

    public List<DiagRespostaRequisicaoComponenteSanguineo> findSolicitacoesAprovadas()
    {
        return diagRespostaRequisicaoComponenteSanguineoFacade.findSolicitacoesAprovadas();
    }

    public List<DiagTransfusao> pesquisarTransfusoes()
    {
        itens = diagTransfusaoFacade.findTransfusao(diagTransfusaoPesquisar, dataInicioPesquisaResultado, dataFimPesquisaResultado, numeroRegistos);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }

        return null;
    }

    public void create(DiagRespostaRequisicaoComponenteSanguineo diagRespostaRequisicaoComponenteSanguineo)
    {
        GregorianCalendar data = new GregorianCalendar();

        try
        {
            userTransaction.begin();

            diagTransfusao = new DiagTransfusao();
            diagTransfusao.setDataTransfusao(new Date(data.getTimeInMillis()));
            diagTransfusao.setFkIdRespostaRequisicaoComponenteSanguineo(diagRespostaRequisicaoComponenteSanguineo);

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession sessao = request.getSession();
            SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");

            diagTransfusao.setFkRealizadoPor(sessaoActual.getFkIdFuncionario());

            diagTransfusaoFacade.create(diagTransfusao);

            userTransaction.commit();
            Mensagem.sucessoMsg("Transfusão registada com sucesso!");

            diagTransfusao = new DiagTransfusao();
        }
        catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
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

    public String limparPesquisa()
    {
        pesquisar = false;
        itens = new ArrayList<>();
        listaBolsas = new ArrayList<>();
        diagTransfusao = diagTransfusaoPesquisar = diagTransfusaoVisualizar = null;

        dataInicioPesquisaResultado = dataFimPesquisaResultado = null;

        return "historicoTransfusoes.xhtml?faces-redirect=true";
    }
}
