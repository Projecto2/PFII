/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagCaracterTransfusao;
import entidade.DiagComponenteSanguineo;
import entidade.DiagRequisicaoComponenteSanguineo;
import entidade.DiagRequisicaoComponenteSanguineoHasComponente;
import entidade.DiagRespostasQuestoesRequisicaoComponentes;
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
import managedBean.interbean.InterRegistoInternamentoBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import managedBean.segbean.SegLoginBean;
import sessao.DiagCaracterTransfusaoFacade;
import sessao.DiagComponenteSanguineoFacade;
import sessao.DiagRequisicaoComponenteSanguineoFacade;
import sessao.DiagRequisicaoComponenteSanguineoHasComponenteFacade;
import sessao.DiagRespostasQuestoesRequisicaoComponentesFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagRequisicaoComponenteSanguineoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagRequisicaoComponenteSanguineoHasComponenteFacade diagRequisicaoComponenteSanguineoHasComponenteFacade;
    @EJB
    private DiagCaracterTransfusaoFacade diagCaracterTransfusaoFacade;
    @EJB
    private DiagRespostasQuestoesRequisicaoComponentesFacade diagRespostasQuestoesRequisicaoComponentesFacade;
    @EJB
    private DiagComponenteSanguineoFacade diagComponenteSanguineoFacade;
    @EJB
    private DiagRequisicaoComponenteSanguineoFacade diagRequisicaoComponenteSanguineoFacade;

    private InterRegistoInternamento interRegistoInternamento;
    private DiagRequisicaoComponenteSanguineo diagRequisicaoComponenteSanguineo, diagRequisicaoComponenteSanguineoVisualizar;
    private DiagCaracterTransfusao diagCaracterTransfusao;
    private DiagRespostasQuestoesRequisicaoComponentes gravidezAnterior, transfusaoAnterior, historiaReaccao, sistomatologiaHemorragica;

    private List<DiagRequisicaoComponenteSanguineoHasComponente> listaComponentesSanguineosSelecionados, listaComponentesSanguineosVisualizar;

    private List<DiagComponenteSanguineo> listaComponenteSanguineosPesquisados;

    private DiagComponenteSanguineo diagComponenteSanguineoPesquisa, diagComponenteSanguineoAdicionarNaLista;

    private boolean renderDataProgramada = false;

    private SegLoginBean segLoginBean = new SegLoginBean();

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    public static DiagRequisicaoComponenteSanguineoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagRequisicaoComponenteSanguineoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagRequisicaoComponenteSanguineoBean");
    }

    public static DiagRequisicaoComponenteSanguineo getInstancia()
    {
        DiagRequisicaoComponenteSanguineo diagRequisicaoComponenteSanguineo = new DiagRequisicaoComponenteSanguineo();
        diagRequisicaoComponenteSanguineo.setFkIdCaracterTransfusao(DiagCaracterTransfusaoBean.getInstancia());
        diagRequisicaoComponenteSanguineo.setFkIdMedico(RhFuncionarioNovoBean.getInstancia());
        diagRequisicaoComponenteSanguineo.setFkIdRegistoInternamento(InterRegistoInternamentoBean.getInstanciaBean().getInstancia());

        return diagRequisicaoComponenteSanguineo;
    }

    public InterRegistoInternamento getInterRegistoInternamento()
    {
        if (interRegistoInternamento == null)
        {
            interRegistoInternamento = InterRegistoInternamentoBean.getInstanciaBean().getInstancia();
        }
        return interRegistoInternamento;
    }

    public void setInterRegistoInternamento(InterRegistoInternamento interRegistoInternamento)
    {
        this.interRegistoInternamento = interRegistoInternamento;
    }

    public DiagRequisicaoComponenteSanguineo getDiagRequisicaoComponenteSanguineo()
    {
        if (diagRequisicaoComponenteSanguineo == null)
        {
            diagRequisicaoComponenteSanguineo = getInstancia();
        }
        return diagRequisicaoComponenteSanguineo;
    }

    public void setDiagRequisicaoComponenteSanguineo(DiagRequisicaoComponenteSanguineo diagRequisicaoComponenteSanguineo)
    {
        this.diagRequisicaoComponenteSanguineo = diagRequisicaoComponenteSanguineo;
    }

    public DiagRequisicaoComponenteSanguineo getDiagRequisicaoComponenteSanguineoVisualizar()
    {
        if (diagRequisicaoComponenteSanguineoVisualizar == null)
        {
            diagRequisicaoComponenteSanguineoVisualizar = getInstancia();
        }
        return diagRequisicaoComponenteSanguineoVisualizar;
    }

    public void setDiagRequisicaoComponenteSanguineoVisualizar(DiagRequisicaoComponenteSanguineo diagRequisicaoComponenteSanguineoVisualizar)
    {
        this.diagRequisicaoComponenteSanguineoVisualizar = diagRequisicaoComponenteSanguineoVisualizar;

        listaComponentesSanguineosVisualizar = diagRequisicaoComponenteSanguineoHasComponenteFacade.findComponentesByRequisicao(diagRequisicaoComponenteSanguineoVisualizar);
    }

    public DiagCaracterTransfusao getDiagCaracterTransfusao()
    {
        if (diagCaracterTransfusao == null)
        {
            diagCaracterTransfusao = DiagCaracterTransfusaoBean.getInstancia();
        }
        return diagCaracterTransfusao;
    }

    public void setDiagCaracterTransfusao(DiagCaracterTransfusao diagCaracterTransfusao)
    {
        this.diagCaracterTransfusao = diagCaracterTransfusao;
    }

    public DiagRespostasQuestoesRequisicaoComponentes getGravidezAnterior()
    {
        if (gravidezAnterior == null)
        {
            gravidezAnterior = DiagRespostasQuestoesRequisicaoComponentesBean.getInstancia();
        }
        return gravidezAnterior;
    }

    public void setGravidezAnterior(DiagRespostasQuestoesRequisicaoComponentes gravidezAnterior)
    {
        this.gravidezAnterior = gravidezAnterior;
    }

    public DiagRespostasQuestoesRequisicaoComponentes getTransfusaoAnterior()
    {
        if (transfusaoAnterior == null)
        {
            transfusaoAnterior = DiagRespostasQuestoesRequisicaoComponentesBean.getInstancia();
        }
        return transfusaoAnterior;
    }

    public void setTransfusaoAnterior(DiagRespostasQuestoesRequisicaoComponentes transfusaoAnterior)
    {
        this.transfusaoAnterior = transfusaoAnterior;
    }

    public DiagRespostasQuestoesRequisicaoComponentes getHistoriaReaccao()
    {
        if (historiaReaccao == null)
        {
            historiaReaccao = DiagRespostasQuestoesRequisicaoComponentesBean.getInstancia();
        }
        return historiaReaccao;
    }

    public void setHistoriaReaccao(DiagRespostasQuestoesRequisicaoComponentes historiaReaccao)
    {
        this.historiaReaccao = historiaReaccao;
    }

    public DiagRespostasQuestoesRequisicaoComponentes getSistomatologiaHemorragica()
    {
        if (sistomatologiaHemorragica == null)
        {
            sistomatologiaHemorragica = DiagRespostasQuestoesRequisicaoComponentesBean.getInstancia();
        }
        return sistomatologiaHemorragica;
    }

    public void setSistomatologiaHemorragica(DiagRespostasQuestoesRequisicaoComponentes sistomatologiaHemorragica)
    {
        this.sistomatologiaHemorragica = sistomatologiaHemorragica;
    }

    public DiagComponenteSanguineo getDiagComponenteSanguineoPesquisa()
    {
        if (diagComponenteSanguineoPesquisa == null)
        {
            diagComponenteSanguineoPesquisa = DiagComponenteSanguineoBean.getInstancia();
        }
        return diagComponenteSanguineoPesquisa;
    }

    public void setDiagComponenteSanguineoPesquisa(DiagComponenteSanguineo diagComponenteSanguineoPesquisa)
    {
        this.diagComponenteSanguineoPesquisa = diagComponenteSanguineoPesquisa;
    }

    public DiagComponenteSanguineo getDiagComponenteSanguineoAdicionarNaLista()
    {
        if (diagComponenteSanguineoAdicionarNaLista == null)
        {
            diagComponenteSanguineoAdicionarNaLista = DiagComponenteSanguineoBean.getInstancia();
        }
        return diagComponenteSanguineoAdicionarNaLista;
    }

    public void setDiagComponenteSanguineoAdicionarNaLista(DiagComponenteSanguineo diagComponenteSanguineoAdicionarNaLista)
    {
        this.diagComponenteSanguineoAdicionarNaLista = diagComponenteSanguineoAdicionarNaLista;
    }

    public void setListaComponenteSanguineosPesquisados(List<DiagComponenteSanguineo> listaComponenteSanguineosPesquisados)
    {
        this.listaComponenteSanguineosPesquisados = listaComponenteSanguineosPesquisados;
    }

    public boolean isRenderDataProgramada()
    {
        return renderDataProgramada;
    }

    public void setRenderDataProgramada(boolean renderDataProgramada)
    {
        this.renderDataProgramada = renderDataProgramada;
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

    public List<DiagComponenteSanguineo> getListaComponenteSanguineosPesquisados()
    {
        return listaComponenteSanguineosPesquisados;
    }

    public List<DiagRequisicaoComponenteSanguineoHasComponente> getListaComponentesSanguineosSelecionados()
    {
        if (listaComponentesSanguineosSelecionados == null)
        {
            listaComponentesSanguineosSelecionados = new ArrayList<>();
        }
        return listaComponentesSanguineosSelecionados;
    }

    public List<DiagRequisicaoComponenteSanguineoHasComponente> getListaComponentesSanguineosVisualizar()
    {
        if (listaComponentesSanguineosVisualizar == null)
        {
            listaComponentesSanguineosVisualizar = new ArrayList<>();
        }
        return listaComponentesSanguineosVisualizar;
    }

    public List<DiagRespostasQuestoesRequisicaoComponentes> getListaRespostasQuestoesRequisicaoComponenteses()
    {
        return diagRespostasQuestoesRequisicaoComponentesFacade.findAll();
    }

    public List<DiagCaracterTransfusao> getListaCaracterTransfusoes()
    {
        return diagCaracterTransfusaoFacade.findAll();
    }

    public List<DiagComponenteSanguineo> getListaTiposComponenteSanguineo()
    {
        return diagComponenteSanguineoFacade.findAll();
    }

    public List<DiagRequisicaoComponenteSanguineo> getSolicitacoes()
    {
        return diagRequisicaoComponenteSanguineoFacade.findSolicitacoes();
    }

    public List<DiagRequisicaoComponenteSanguineo> findAll()
    {
        return diagRequisicaoComponenteSanguineoFacade.findAll();
    }

    public String selecionarPacienteRequisicaoComponenteSanguineo()
    {

        return "/faces/interVisao/homeInter.xhtml?faces-redirect=true";
    }

    public void pesquisarComponentesSanguineos()
    {
        setListaComponenteSanguineosPesquisados(diagComponenteSanguineoFacade.findPesquisa(getDiagComponenteSanguineoPesquisa()));

        if (getListaComponenteSanguineosPesquisados().isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getListaComponenteSanguineosPesquisados().size() + " registo(s) retornado(s).");
        }

        eliminarDuplicados();
    }

    public void eliminarDuplicados()
    {
        for (DiagRequisicaoComponenteSanguineoHasComponente requisicao_has_componente_aux : getListaComponentesSanguineosSelecionados())
        {
            if (getListaComponenteSanguineosPesquisados().contains(requisicao_has_componente_aux.getFkIdComponente()))
            {
                getListaComponenteSanguineosPesquisados().remove(requisicao_has_componente_aux.getFkIdComponente());
            }
        }
    }

    public void adicionarComponente()
    {
        DiagRequisicaoComponenteSanguineoHasComponente itemAux = new DiagRequisicaoComponenteSanguineoHasComponente();
        itemAux.setFkIdRequisicaoComponenteSanguineo(diagRequisicaoComponenteSanguineo);
        itemAux.setFkIdComponente(getDiagComponenteSanguineoAdicionarNaLista());
        itemAux.setQuantidadeComponente(1);
        listaComponentesSanguineosSelecionados.add(itemAux);

        pesquisarComponentesSanguineos();
    }

    public void removerComponente()
    {
        DiagRequisicaoComponenteSanguineoHasComponente itemAux = new DiagRequisicaoComponenteSanguineoHasComponente();
        itemAux.setFkIdRequisicaoComponenteSanguineo(diagRequisicaoComponenteSanguineo);
        itemAux.setFkIdComponente(getDiagComponenteSanguineoAdicionarNaLista());
        itemAux.setQuantidadeComponente(1);
        listaComponentesSanguineosSelecionados.remove(itemAux);

        pesquisarComponentesSanguineos();
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

            diagRequisicaoComponenteSanguineo.setFkIdRegistoInternamento(interRegistoInternamento);

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession sessao = request.getSession();
            SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");
                        
            diagRequisicaoComponenteSanguineo.setFkIdMedico(sessaoActual.getFkIdFuncionario());

            diagRequisicaoComponenteSanguineo.setGravidezAnterior(gravidezAnterior);
            diagRequisicaoComponenteSanguineo.setHistoriaReaccaoTransfusional(historiaReaccao);
            diagRequisicaoComponenteSanguineo.setSistomatologiaHemorragica(sistomatologiaHemorragica);
            diagRequisicaoComponenteSanguineo.setTransfusaoAnterior(transfusaoAnterior);

            diagRequisicaoComponenteSanguineo.setFkIdCaracterTransfusao(diagCaracterTransfusao);

            diagRequisicaoComponenteSanguineo.setData(new Date(data.getTimeInMillis()));

            diagRequisicaoComponenteSanguineoFacade.create(diagRequisicaoComponenteSanguineo);

            userTransaction.commit();

            adicionarComponentesDaRequisicao(diagRequisicaoComponenteSanguineo);

//            temMensagemPendente = true;
//
//            tipoMensagemPendente = "Sucesso";
//
//            mensagemPendente = "Requisição de componente sanguíneo registada com sucesso!";
            Mensagem.sucessoMsg("Requisição de componente sanguíneo registada com sucesso!");

            diagRequisicaoComponenteSanguineo = new DiagRequisicaoComponenteSanguineo();

            gravidezAnterior = DiagRespostasQuestoesRequisicaoComponentesBean.getInstancia();
            historiaReaccao = DiagRespostasQuestoesRequisicaoComponentesBean.getInstancia();
            sistomatologiaHemorragica = DiagRespostasQuestoesRequisicaoComponentesBean.getInstancia();
            transfusaoAnterior = DiagRespostasQuestoesRequisicaoComponentesBean.getInstancia();

            diagCaracterTransfusao = DiagCaracterTransfusaoBean.getInstancia();

            listaComponentesSanguineosSelecionados = new ArrayList<>();
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

        return "/faces/interVisao/homeInter.xhtml?faces-redirect=true";
    }

    private void adicionarComponentesDaRequisicao(DiagRequisicaoComponenteSanguineo requisicaoAux)
    {
        DiagRequisicaoComponenteSanguineoHasComponente requisicaoHasComponente;

        for (DiagRequisicaoComponenteSanguineoHasComponente componenteAux : listaComponentesSanguineosSelecionados)
        {
            requisicaoHasComponente = new DiagRequisicaoComponenteSanguineoHasComponente();

            requisicaoHasComponente.setFkIdComponente(componenteAux.getFkIdComponente());
            requisicaoHasComponente.setFkIdRequisicaoComponenteSanguineo(requisicaoAux);

            requisicaoHasComponente.setQuantidadeComponente(componenteAux.getQuantidadeComponente());

            diagRequisicaoComponenteSanguineoHasComponenteFacade.create(requisicaoHasComponente);
        }
    }

    public void apresentarDataProgramada()
    {
        DiagCaracterTransfusao caracterTransfusaoAux = diagCaracterTransfusaoFacade.find(diagCaracterTransfusao.getPkIdCaracterTransfusao());
        renderDataProgramada = caracterTransfusaoAux.getDescricao().equals("Programada");
    }
}
