/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsPaciente;
import entidade.DiagGrupoSanguineo;
import entidade.DiagSolicitacaoTipagemDoente;
import entidade.DiagTipagemDoente;
import entidade.GrlCentroHospitalar;
import entidade.RhFuncionario;
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
import managedBean.grlbean.GrlCentroHospitalarBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import managedBean.segbean.SegLoginBean;
import sessao.AdmsPacienteFacade;
import sessao.DiagGrupoSanguineoFacade;
import sessao.DiagSolicitacaoTipagemDoenteFacade;
import sessao.DiagTipagemDoenteFacade;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlPessoaFacade;
import sessao.RhFuncionarioFacade;
import util.Mensagem;
import util.diag.Defs;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipagemDoenteBean implements Serializable
{

    @EJB
    private GrlPessoaFacade grlPessoaFacade;

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlCentroHospitalarFacade grlCentroHospitalarFacade;
    @EJB
    private DiagTipagemDoenteFacade diagTipagemDoenteFacade;
    @EJB
    private AdmsPacienteFacade admsPacienteFacade;
    @EJB
    private DiagGrupoSanguineoFacade diagGrupoSanguineoFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    private DiagSolicitacaoTipagemDoenteFacade diagSolicitacaoTipagemDoenteFacade;

    private GrlCentroHospitalar grlCentroHospitalar;
    private DiagTipagemDoente diagTipagemDoente, diagTipagemDoenteVisualizar, diagTipagemDoentePesquisa;
    private DiagSolicitacaoTipagemDoente diagSolicitacaoTipagemDoente, diagSolicitacaoTipagemDoenteVisualizar;

    private DiagGrupoSanguineo diagGrupoSanguineo;

    private Date dataInicioPesquisaResultado, dataFimPesquisaResultado;

    private boolean pesquisarTipagemDoente;

    private SegLoginBean segLoginBean = new SegLoginBean();

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    List<DiagTipagemDoente> itens;

    private int numeroRegistos = 10;
    
    public static DiagTipagemDoenteBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTipagemDoenteBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTipagemDoenteBean");
    }

    public static DiagTipagemDoente getInstancia()
    {
        DiagTipagemDoente diagTipagemDoente = new DiagTipagemDoente();
        diagTipagemDoente.setFkIdPaciente(AdmsPacienteNovoBean.getInstanciaBean().getInstanciaPaciente());
        diagTipagemDoente.getFkIdPaciente().getFkIdPessoa().getFkIdNacionalidade().setPkIdPais(Defs.NACIONALIDADE_DEFAULT_ID);
        diagTipagemDoente.getFkIdPaciente().getFkIdPessoa().getFkIdSexo().setPkIdSexo(Defs.SEXO_DEFAULT_ID);
        diagTipagemDoente.setFkIdSolicitacaoTipagemDoente(DiagSolicitacaoTipagemDoenteBean.getInstancia());
        diagTipagemDoente.setFkRealizadoPor(RhFuncionarioNovoBean.getInstancia());
        diagTipagemDoente.setConclusao(DiagGrupoSanguineoBean.getInstancia());

        return diagTipagemDoente;
    }

    public int getNumeroRegistos()
    {
        return numeroRegistos;
    }

    public void setNumeroRegistos(int numeroRegistos)
    {
        this.numeroRegistos = numeroRegistos;
    }

    public List<DiagTipagemDoente> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagTipagemDoente> itens)
    {
        this.itens = itens;
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

    public GrlCentroHospitalar getGrlCentroHospitalar()
    {
        if (grlCentroHospitalar == null)
        {
            grlCentroHospitalar = GrlCentroHospitalarBean.getInstancia();
        }
        return grlCentroHospitalar;
    }

    public void setGrlCentroHospitalar(GrlCentroHospitalar grlCentroHospitalar)
    {
        this.grlCentroHospitalar = grlCentroHospitalar;
    }

    public DiagTipagemDoente getDiagTipagemDoente()
    {
        if (diagTipagemDoente == null)
        {
            diagTipagemDoente = getInstancia();
        }
        return diagTipagemDoente;
    }

    public void setDiagTipagemDoente(DiagTipagemDoente diagTipagemDoente)
    {
        this.diagTipagemDoente = diagTipagemDoente;
    }

    public DiagSolicitacaoTipagemDoente getDiagSolicitacaoTipagemDoenteVisualizar()
    {
        if (diagSolicitacaoTipagemDoenteVisualizar == null)
        {
            diagSolicitacaoTipagemDoenteVisualizar = DiagSolicitacaoTipagemDoenteBean.getInstancia();
        }
        return diagSolicitacaoTipagemDoenteVisualizar;
    }

    public void setDiagSolicitacaoTipagemDoenteVisualizar(DiagSolicitacaoTipagemDoente diagSolicitacaoTipagemDoenteVisualizar)
    {
        this.diagSolicitacaoTipagemDoenteVisualizar = diagSolicitacaoTipagemDoenteVisualizar;
    }

    public DiagTipagemDoente getDiagTipagemDoenteVisualizar()
    {
        if (diagTipagemDoenteVisualizar == null)
        {
            diagTipagemDoenteVisualizar = getInstancia();
        }
        return diagTipagemDoenteVisualizar;
    }

    public void setDiagTipagemDoenteVisualizar(DiagTipagemDoente diagTipagemDoenteVisualizar)
    {
        this.diagTipagemDoenteVisualizar = diagTipagemDoenteVisualizar;
    }

    public DiagTipagemDoente getDiagTipagemDoentePesquisa()
    {
        if (diagTipagemDoentePesquisa == null)
        {
            diagTipagemDoentePesquisa = getInstancia();
        }
        return diagTipagemDoentePesquisa;
    }

    public void setDiagTipagemDoentePesquisa(DiagTipagemDoente diagTipagemDoentePesquisa)
    {
        this.diagTipagemDoentePesquisa = diagTipagemDoentePesquisa;
    }

    public DiagSolicitacaoTipagemDoente getDiagSolicitacaoTipagemDoente()
    {
        if (diagSolicitacaoTipagemDoente == null)
        {
            diagSolicitacaoTipagemDoente = DiagSolicitacaoTipagemDoenteBean.getInstancia();
        }
        return diagSolicitacaoTipagemDoente;
    }

    public void setDiagSolicitacaoTipagemDoente(DiagSolicitacaoTipagemDoente diagSolicitacaoTipagemDoente)
    {
        this.diagSolicitacaoTipagemDoente = diagSolicitacaoTipagemDoente;
    }

    public DiagGrupoSanguineo getDiagGrupoSanguineo()
    {
        if (diagGrupoSanguineo == null)
        {
            diagGrupoSanguineo = DiagGrupoSanguineoBean.getInstancia();
        }
        return diagGrupoSanguineo;
    }

    public void setDiagGrupoSanguineo(DiagGrupoSanguineo diagGrupoSanguineo)
    {
        this.diagGrupoSanguineo = diagGrupoSanguineo;
    }

    public boolean getPesquisarTipagemDoente()
    {
        return pesquisarTipagemDoente;
    }

    public void setPesquisarTipagemDoente(boolean pesquisarTipagemDoente)
    {
        this.pesquisarTipagemDoente = pesquisarTipagemDoente;
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

    public List<DiagGrupoSanguineo> getListaDiagGrupoSanguineo()
    {
        return diagGrupoSanguineoFacade.findAll();
    }

    public List<GrlCentroHospitalar> getListaGrlCentrosHospitalares()
    {
        return grlCentroHospitalarFacade.findAll();
    }

    public List<RhFuncionario> getListaFuncionarios()
    {
        return rhFuncionarioFacade.findAll();
    }

    public List<AdmsPaciente> getListaPacientes()
    {
        return admsPacienteFacade.findAll();
    }

    public List<DiagTipagemDoente> getListaDiagTipagemDoentes()
    {
        return diagTipagemDoenteFacade.findAll();
    }

    public boolean pacienteJaTipado(AdmsPaciente paciente)
    {
        return diagTipagemDoenteFacade.findTipagemDoente(paciente) != null;
    }

    public String selecionarSolicitacaoTipagemDoente(DiagSolicitacaoTipagemDoente solicitacaoTipagemDoenteAux)
    {
        diagSolicitacaoTipagemDoente = solicitacaoTipagemDoenteAux;

        return "registarTipagemDoente.xhtml?faces-redirect=true";
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

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession sessao = request.getSession();
            SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");
                                    
            diagTipagemDoente.setFkRealizadoPor(sessaoActual.getFkIdFuncionario());
            
            diagTipagemDoente.setFkIdSolicitacaoTipagemDoente(diagSolicitacaoTipagemDoente);

            diagTipagemDoente.setFkIdPaciente(diagSolicitacaoTipagemDoente.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente());

            diagTipagemDoente.setConclusao(diagGrupoSanguineo);

            diagTipagemDoente.setDataTipagem(new Date(data.getTimeInMillis()));

            diagTipagemDoenteFacade.create(diagTipagemDoente);

            diagTipagemDoente.getFkIdPaciente().getFkIdPessoa().setFkIdGrupoSanguineo(diagGrupoSanguineo);

            grlPessoaFacade.edit(diagTipagemDoente.getFkIdPaciente().getFkIdPessoa());

            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Tipagem sanguínea registada com sucesso!";
        }
        catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
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

        return "pedidosTipagemDoente.xhtml?faces-redirect=true";
    }

    public List<DiagTipagemDoente> pesquisarTipagensDoentes()
    {
        itens = diagTipagemDoenteFacade.findTipagem(diagTipagemDoentePesquisa, dataInicioPesquisaResultado, dataFimPesquisaResultado, numeroRegistos);

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

    public List<DiagSolicitacaoTipagemDoente> getSolicitacoesTipagensDoentes()
    {
        return diagSolicitacaoTipagemDoenteFacade.findTipagensSolicitadas();
    }

    public String limparPesquisa()
    {
        pesquisarTipagemDoente = false;
        itens = new ArrayList<>();
        diagTipagemDoente = diagTipagemDoentePesquisa = diagTipagemDoenteVisualizar = null;

        dataInicioPesquisaResultado = dataFimPesquisaResultado = null;

        return "tipagemDoente.xhtml?faces-redirect=true";
    }

    public String voltarParaSolicitacoes()
    {
        diagSolicitacaoTipagemDoente = null;

        return "pedidosTipagemDoente.xhtml?faces-redirect=true";
    }
}
