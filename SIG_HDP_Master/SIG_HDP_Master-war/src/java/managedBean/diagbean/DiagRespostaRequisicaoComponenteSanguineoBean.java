/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsPaciente;
import entidade.DiagBolsaSangue;
import entidade.DiagGrupoSanguineo;
import entidade.DiagRequisicaoComponenteSanguineo;
import entidade.DiagRequisicaoComponenteSanguineoHasComponente;
import entidade.DiagRespostaRequisicaoComponenteSanguineo;
import entidade.DiagRespostaRequisicaoComponenteSanguineoHasComponente;
import entidade.DiagRespostaRequisicaoHasComponenteHasTeste;
import entidade.DiagResultadoExameCandidato;
import entidade.DiagResultadoTesteCompatibilidade;
import entidade.DiagTesteCompatibilidade;
import entidade.DiagTipagemDador;
import entidade.DiagTipagemDoente;
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
import managedBean.rhbean.funcionario.RhFuncionarioEditarBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import managedBean.segbean.SegLoginBean;
import org.primefaces.context.RequestContext;
import sessao.DiagBolsaSangueFacade;
import sessao.DiagRequisicaoComponenteSanguineoFacade;
import sessao.DiagRespostaRequisicaoComponenteSanguineoFacade;
import sessao.DiagRespostaRequisicaoComponenteSanguineoHasComponenteFacade;
import sessao.DiagRespostaRequisicaoHasComponenteHasTesteFacade;
import sessao.DiagResultadoExameCandidatoFacade;
import sessao.DiagResultadoTesteCompatibilidadeFacade;
import sessao.DiagTesteCompatibilidadeFacade;
import sessao.DiagTipagemDadorFacade;
import sessao.DiagTipagemDoenteFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagRespostaRequisicaoComponenteSanguineoBean implements Serializable
{

    @EJB
    private DiagRespostaRequisicaoHasComponenteHasTesteFacade diagRespostaRequisicaoHasComponenteHasTesteFacade;
    @EJB
    private DiagRespostaRequisicaoComponenteSanguineoHasComponenteFacade diagRespostaRequisicaoComponenteSanguineoHasComponenteFacade;

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagResultadoTesteCompatibilidadeFacade diagResultadoTesteCompatibilidadeFacade;

    @EJB
    private DiagTipagemDadorFacade diagTipagemDadorFacade;

    @EJB
    private DiagResultadoExameCandidatoFacade diagResultadoExameCandidatoFacade;

    @EJB
    private DiagTipagemDoenteFacade diagTipagemDoenteFacade;

    @EJB
    private DiagBolsaSangueFacade diagBolsaSangueFacade;
    @EJB
    private DiagTesteCompatibilidadeFacade diagTesteCompatibilidadeFacade;
    @EJB
    private DiagRequisicaoComponenteSanguineoFacade diagRequisicaoComponenteSanguineoFacade;
    @EJB
    private DiagRespostaRequisicaoComponenteSanguineoFacade diagRespostaRequisicaoComponenteSanguineoFacade;

    private DiagRespostaRequisicaoComponenteSanguineo diagRespostaRequisicaoComponenteSanguineo = getInstancia(), diagRespostaRequisicaoComponenteSanguineoVisualizar = getInstancia();
    private DiagRequisicaoComponenteSanguineo diagRequisicaoComponenteSanguineo;

    private DiagResultadoExameCandidato diagPesquisaAntiCorposIrregulares = new DiagResultadoExameCandidato(2);

    private DiagTipagemDoente diagTipagemDoenteVisualizar;

    private DiagTipagemDador diagTipagemDadorVisualizar;

    private DiagRequisicaoComponenteSanguineoHasComponente componenteAdicionar, componenteVisualizarBolsas;

    private List<DiagRequisicaoComponenteSanguineoHasComponente> listaComponentesRequisitados;

    private DiagBolsaSangue diagBolsaSanguePesquisar, diagBolsaSangueSeleccionada;

    private boolean pesquisarBolsasCompativeis;

    private Float quantidadeSangueAdicionarNaLista = new Float(0F);

    private List<DiagRespostaRequisicaoHasComponenteHasTeste> listaBolsasDaResposta = new ArrayList<>();

    private DiagGrupoSanguineo diagGrupoSanguineoPesquisar;

    private Date dataInicioCadastro, dataFimCadastro, dataInicioExpiracao, dataFimExpiracao;

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    private DiagAdicionarTesteCompatibilidadeBean diagAdicionarTesteCompatibilidadeBean = DiagAdicionarTesteCompatibilidadeBean.getInstanciaBean();

    private DiagResultadoTesteCompatibilidade diagResultadoTesteCompatibilidade;
    private DiagTesteCompatibilidade diagTesteCompatibilidade;

    private DiagResultadoExameCandidato provaSalina = new DiagResultadoExameCandidato(), testeCoombs = new DiagResultadoExameCandidato();

    List<DiagBolsaSangue> itensListaBolsasCompativeis;

    private SegLoginBean segLoginBean = new SegLoginBean();

    List<DiagRespostaRequisicaoHasComponenteHasTeste> listaBolsas;

    private int numeroRegistos = 10;

    public static DiagRespostaRequisicaoComponenteSanguineoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagRespostaRequisicaoComponenteSanguineoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagRespostaRequisicaoComponenteSanguineoBean");
    }

    public static DiagRespostaRequisicaoComponenteSanguineo getInstancia()
    {
        DiagRespostaRequisicaoComponenteSanguineo diagRespostaRequisicaoComponenteSanguineo = new DiagRespostaRequisicaoComponenteSanguineo();
        diagRespostaRequisicaoComponenteSanguineo.setFkIdRequisicaoComponenteSanguineo(DiagRequisicaoComponenteSanguineoBean.getInstancia());
        diagRespostaRequisicaoComponenteSanguineo.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return diagRespostaRequisicaoComponenteSanguineo;
    }

    public int getNumeroRegistos()
    {
        return numeroRegistos;
    }

    public void setNumeroRegistos(int numeroRegistos)
    {
        this.numeroRegistos = numeroRegistos;
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

    public List<DiagBolsaSangue> getItensListaBolsasCompativeis()
    {
        return itensListaBolsasCompativeis;
    }

    public void setItensListaBolsasCompativeis(List<DiagBolsaSangue> itensListaBolsasCompativeis)
    {
        this.itensListaBolsasCompativeis = itensListaBolsasCompativeis;
    }

    public DiagResultadoExameCandidato getDiagPesquisaAntiCorposIrregulares()
    {
        if (diagPesquisaAntiCorposIrregulares == null)
        {
            diagPesquisaAntiCorposIrregulares = new DiagResultadoExameCandidato(2);
        }
        return diagPesquisaAntiCorposIrregulares;
    }

    public void setDiagPesquisaAntiCorposIrregulares(DiagResultadoExameCandidato diagPesquisaAntiCorposIrregulares)
    {
        this.diagPesquisaAntiCorposIrregulares = diagPesquisaAntiCorposIrregulares;
    }

    public DiagResultadoExameCandidato getProvaSalina()
    {
        if (provaSalina == null)
        {
            provaSalina = new DiagResultadoExameCandidato();
        }
        return provaSalina;
    }

    public void setProvaSalina(DiagResultadoExameCandidato provaSalina)
    {
        this.provaSalina = provaSalina;
    }

    public DiagResultadoExameCandidato getTesteCoombs()
    {
        if (testeCoombs == null)
        {
            testeCoombs = new DiagResultadoExameCandidato();
        }
        return testeCoombs;
    }

    public void setTesteCoombs(DiagResultadoExameCandidato testeCoombs)
    {
        this.testeCoombs = testeCoombs;
    }

    public DiagTesteCompatibilidade getDiagTesteCompatibilidade()
    {
        if (diagTesteCompatibilidade == null)
        {
            diagTesteCompatibilidade = DiagTesteCompatibilidadeBean.getInstancia();
        }
        return diagTesteCompatibilidade;
    }

    public void setDiagTesteCompatibilidade(DiagTesteCompatibilidade diagTesteCompatibilidade)
    {
        this.diagTesteCompatibilidade = diagTesteCompatibilidade;
    }

    public List<DiagResultadoExameCandidato> getListaResultadosExamesTesteCompatibilidade()
    {
        return diagResultadoExameCandidatoFacade.findAll();
    }

    public DiagTipagemDador getDiagTipagemDadorVisualizar()
    {
        if (diagTipagemDadorVisualizar == null)
        {
            diagTipagemDadorVisualizar = DiagTipagemDadorBean.getInstancia();
        }
        return diagTipagemDadorVisualizar;
    }

    public void setDiagTipagemDadorVisualizar(DiagTipagemDador diagTipagemDadorVisualizar)
    {
        this.diagTipagemDadorVisualizar = diagTipagemDadorVisualizar;
    }

    public DiagGrupoSanguineo getDiagGrupoSanguineoPesquisar()
    {
        if (diagGrupoSanguineoPesquisar == null)
        {
            diagGrupoSanguineoPesquisar = new DiagGrupoSanguineo();
        }
        return diagGrupoSanguineoPesquisar;
    }

    public void setDiagGrupoSanguineoPesquisar(DiagGrupoSanguineo diagGrupoSanguineoPesquisar)
    {
        this.diagGrupoSanguineoPesquisar = diagGrupoSanguineoPesquisar;
    }

    public Date getDataInicioCadastro()
    {
        return dataInicioCadastro;
    }

    public void setDataInicioCadastro(Date dataInicioCadastro)
    {
        this.dataInicioCadastro = dataInicioCadastro;
    }

    public Date getDataFimCadastro()
    {
        return dataFimCadastro;
    }

    public void setDataFimCadastro(Date dataFimCadastro)
    {
        this.dataFimCadastro = dataFimCadastro;
    }

    public Date getDataInicioExpiracao()
    {
        return dataInicioExpiracao;
    }

    public void setDataInicioExpiracao(Date dataInicioExpiracao)
    {
        this.dataInicioExpiracao = dataInicioExpiracao;
    }

    public Date getDataFimExpiracao()
    {
        return dataFimExpiracao;
    }

    public void setDataFimExpiracao(Date dataFimExpiracao)
    {
        this.dataFimExpiracao = dataFimExpiracao;
    }

    public DiagBolsaSangue getDiagBolsaSangueSeleccionada()
    {
        if (diagBolsaSangueSeleccionada == null)
        {
            diagBolsaSangueSeleccionada = DiagBolsaSangueBean.getInstancia();
        }
        return diagBolsaSangueSeleccionada;
    }

    public void setDiagBolsaSangueSeleccionada(DiagBolsaSangue diagBolsaSangueSeleccionada)
    {
        this.diagBolsaSangueSeleccionada = diagBolsaSangueSeleccionada;
    }

    public DiagBolsaSangue getDiagBolsaSanguePesquisar()
    {
        if (diagBolsaSanguePesquisar == null)
        {
            diagBolsaSanguePesquisar = DiagBolsaSangueBean.getInstancia();
        }
        return diagBolsaSanguePesquisar;
    }

    public void setDiagBolsaSanguePesquisar(DiagBolsaSangue diagBolsaSanguePesquisar)
    {
        this.diagBolsaSanguePesquisar = diagBolsaSanguePesquisar;
    }

    public Float getQuantidadeSangueAdicionarNaLista()
    {
        return quantidadeSangueAdicionarNaLista;
    }

    public void setQuantidadeSangueAdicionarNaLista(Float quantidadeSangueAdicionarNaLista)
    {
        this.quantidadeSangueAdicionarNaLista = quantidadeSangueAdicionarNaLista;
    }

//    public List<DiagBolsaSelecionadaRespostaTransfusao> getListaBolsasSelecionadasTransfusao()
//    {
//        return listaBolsasSelecionadasTransfusao;
//    }
//
//    public void setListaBolsasSelecionadasTransfusao(List<DiagBolsaSelecionadaRespostaTransfusao> listaBolsasSelecionadasTransfusao)
//    {
//        this.listaBolsasSelecionadasTransfusao = listaBolsasSelecionadasTransfusao;
//    }
    public DiagRespostaRequisicaoComponenteSanguineo getDiagRespostaRequisicaoComponenteSanguineo()
    {
        if (diagRespostaRequisicaoComponenteSanguineo == null)
        {
            diagRespostaRequisicaoComponenteSanguineo = DiagRespostaRequisicaoComponenteSanguineoBean.getInstancia();
        }
        return diagRespostaRequisicaoComponenteSanguineo;
    }

    public void setDiagRespostaRequisicaoComponenteSanguineo(DiagRespostaRequisicaoComponenteSanguineo diagRespostaRequisicaoComponenteSanguineo)
    {
        this.diagRespostaRequisicaoComponenteSanguineo = diagRespostaRequisicaoComponenteSanguineo;
    }

    public DiagRespostaRequisicaoComponenteSanguineo getDiagRespostaRequisicaoComponenteSanguineoVisualizar()
    {
        if (diagRespostaRequisicaoComponenteSanguineoVisualizar == null)
        {
            diagRespostaRequisicaoComponenteSanguineoVisualizar = DiagRespostaRequisicaoComponenteSanguineoBean.getInstancia();
        }
        return diagRespostaRequisicaoComponenteSanguineoVisualizar;
    }

    public void setDiagRespostaRequisicaoComponenteSanguineoVisualizar(DiagRespostaRequisicaoComponenteSanguineo diagRespostaRequisicaoComponenteSanguineoVisualizar)
    {
        this.diagRespostaRequisicaoComponenteSanguineoVisualizar = diagRespostaRequisicaoComponenteSanguineoVisualizar;

        if (diagRespostaRequisicaoComponenteSanguineoVisualizar != null)
        {
            //Pesquisar diag_resposta_requisicao_has_componente_has_teste com a chave da resposta: fk_id_resposta_requisicao_has_componente
            listaBolsas = diagRespostaRequisicaoHasComponenteHasTesteFacade.findBolsas(diagRespostaRequisicaoComponenteSanguineoVisualizar.getPkIdRespostaRequisicaoComponenteSanguineo());
            System.out.println("listaBolsas" + listaBolsas.size());
        }
    }

    public DiagRequisicaoComponenteSanguineo getDiagRequisicaoComponenteSanguineo()
    {
        if (diagRequisicaoComponenteSanguineo == null)
        {
            diagRequisicaoComponenteSanguineo = DiagRequisicaoComponenteSanguineoBean.getInstancia();
        }
        return diagRequisicaoComponenteSanguineo;
    }

    public void setDiagRequisicaoComponenteSanguineo(DiagRequisicaoComponenteSanguineo diagRequisicaoComponenteSanguineo)
    {
        this.diagRequisicaoComponenteSanguineo = diagRequisicaoComponenteSanguineo;
    }

    public DiagRequisicaoComponenteSanguineoHasComponente getComponenteAdicionar()
    {
        if (componenteAdicionar == null)
        {
            componenteAdicionar = new DiagRequisicaoComponenteSanguineoHasComponente();
            componenteAdicionar.setFkIdComponente(DiagComponenteSanguineoBean.getInstancia());
            componenteAdicionar.setFkIdRequisicaoComponenteSanguineo(DiagRequisicaoComponenteSanguineoBean.getInstancia());
        }
        return componenteAdicionar;
    }

    public void setComponenteAdicionar(DiagRequisicaoComponenteSanguineoHasComponente componenteAdicionar)
    {
        this.componenteAdicionar = componenteAdicionar;

        pesquisarBolsasCompativeis = false;

        if (componenteAdicionar == null)
        {
            quantidadeSangueAdicionarNaLista = 0F;
        }
    }

    public void selecionarComponenteAdicionar(DiagRequisicaoComponenteSanguineoHasComponente componenteAdicionar)
    {
        //Se já foi adicionada a quantidade requisitada, apresentar uma dialog com a mensagem a dizer que já não dá
        if (quantidadeComponenteRequisitadoSatisfeita(componenteAdicionar))
        {
            this.componenteAdicionar = componenteAdicionar;

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dialogMensagemErro').show()");

//            temMensagemPendente = true;
//
//            tipoMensagemPendente = "Erro";
//
//            mensagemPendente = "Já não é possivel adicionar mais bolsas!";
//            pesquisarBolsasCompativeis = false;
//
//            candidatoDadorPesquisar = null;
//
//            if (componenteAdicionar == null)
//            {
//                quantidadeSangueAdicionarNaLista = 0F;
//            }
        }
        else
        {
            this.componenteAdicionar = componenteAdicionar;

            pesquisarBolsasCompativeis = false;

            if (componenteAdicionar == null)
            {
                quantidadeSangueAdicionarNaLista = 0F;
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dialogPesquisar').show()");
        }
    }

    public DiagRequisicaoComponenteSanguineoHasComponente getComponenteVisualizarBolsas()
    {
        if (componenteVisualizarBolsas == null)
        {
            componenteVisualizarBolsas = new DiagRequisicaoComponenteSanguineoHasComponente();
            componenteVisualizarBolsas.setFkIdComponente(DiagComponenteSanguineoBean.getInstancia());
            componenteVisualizarBolsas.setFkIdRequisicaoComponenteSanguineo(DiagRequisicaoComponenteSanguineoBean.getInstancia());
        }
        return componenteVisualizarBolsas;
    }

    public void setComponenteVisualizarBolsas(DiagRequisicaoComponenteSanguineoHasComponente componenteVisualizarBolsas)
    {
        this.componenteVisualizarBolsas = componenteVisualizarBolsas;
    }

    public DiagTipagemDoente getDiagTipagemDoenteVisualizar()
    {
        if (diagTipagemDoenteVisualizar == null)
        {
            diagTipagemDoenteVisualizar = DiagTipagemDoenteBean.getInstancia();
        }
        return diagTipagemDoenteVisualizar;
    }

    public void setDiagTipagemDoenteVisualizar(DiagTipagemDoente diagTipagemDoenteVisualizar)
    {
        this.diagTipagemDoenteVisualizar = diagTipagemDoenteVisualizar;
    }

    public List<DiagRequisicaoComponenteSanguineoHasComponente> getListaComponentesRequisitados()
    {
        if (listaComponentesRequisitados == null)
        {
            listaComponentesRequisitados = new ArrayList<>();
        }
        return listaComponentesRequisitados;
    }

    public void setListaComponentesRequisitados(List<DiagRequisicaoComponenteSanguineoHasComponente> listaComponentesRequisitados)
    {
        this.listaComponentesRequisitados = listaComponentesRequisitados;
    }

    public boolean isPesquisarBolsasCompativeis()
    {
        return pesquisarBolsasCompativeis;
    }

    public void setPesquisarBolsasCompativeis(boolean pesquisarBolsasCompativeis)
    {
        this.pesquisarBolsasCompativeis = pesquisarBolsasCompativeis;
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

    public boolean testeCompatibilidadeJaEfectuado(AdmsPaciente paciente, DiagBolsaSangue bolsaSangue, DiagRequisicaoComponenteSanguineo requisicao)
    {
        return !diagTesteCompatibilidadeFacade.findTesteCompatibilidadeByPacienteAndBolsaAndSolicitacao(paciente, bolsaSangue, requisicao).isEmpty();
    }

    public String registarTesteCompatibilidade()
    {

        GregorianCalendar data = new GregorianCalendar();

        try
        {
            userTransaction.begin();

            diagTesteCompatibilidade = DiagTesteCompatibilidadeBean.getInstancia();

            diagTesteCompatibilidade.setData(new Date(data.getTimeInMillis()));
            diagTesteCompatibilidade.setFkIdPaciente(diagRequisicaoComponenteSanguineo.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente());
            diagTesteCompatibilidade.setFkIdBolsaSangue(diagBolsaSangueSeleccionada);
            diagTesteCompatibilidade.setFkIdRequisicaoComponente(diagRequisicaoComponenteSanguineo);

            diagResultadoTesteCompatibilidade = DiagResultadoTesteCompatibilidadeBean.getInstancia();

            System.out.println("diagTesteCompatibilidade.toString() " + diagTesteCompatibilidade.toString());
            System.out.println("provaSalina.getPkIdResultadoExameCandidato() " + provaSalina.getPkIdResultadoExameCandidato());
            System.out.println("testeCoombs.getPkIdResultadoExameCandidato() " + testeCoombs.getPkIdResultadoExameCandidato());

            if (provaSalina.getPkIdResultadoExameCandidato() == 2 && testeCoombs.getPkIdResultadoExameCandidato() == 2)
            {
                diagResultadoTesteCompatibilidade = diagResultadoTesteCompatibilidadeFacade.find(1);
            }
            else
            {
                diagResultadoTesteCompatibilidade = diagResultadoTesteCompatibilidadeFacade.find(2);
            }

            diagTesteCompatibilidade.setTesteCoombs(testeCoombs);
            diagTesteCompatibilidade.setProvaSoroFisiologico(provaSalina);
            diagTesteCompatibilidade.setFkIdResultadoTesteCompatibilidade(diagResultadoTesteCompatibilidade);

            diagTesteCompatibilidadeFacade.create(diagTesteCompatibilidade);

            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Teste de compatibilidade salvo com sucesso!";

            Mensagem.sucessoMsg("Teste de compatibilidade salvo com sucesso!");

            //Se é compatível abrir dialog adicionar e se não apresentar apenas a msg
            if (diagTesteCompatibilidade.getFkIdResultadoTesteCompatibilidade().getPkIdResultadoTesteCompatibilidade() == 1)
            {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogAdicionar').show()");
            }
            else
            {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("dialogTesteCompatibilidade.hide()");

                return "aprovarSolicitacaoComponenteSanguineo.xhtml?faces-redirect=true";
            }

            provaSalina = new DiagResultadoExameCandidato();

            testeCoombs = new DiagResultadoExameCandidato();

            diagTesteCompatibilidade = null;
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

        return null;
    }

    public List<DiagBolsaSangue> pesquisarBolsasCompativeisComPaciente()
    {
        itensListaBolsasCompativeis = diagBolsaSangueFacade.findBolsasSangue(diagBolsaSanguePesquisar, diagGrupoSanguineoPesquisar, dataInicioCadastro, dataFimCadastro, dataInicioExpiracao, dataFimExpiracao, numeroRegistos);

        if (itensListaBolsasCompativeis.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itensListaBolsasCompativeis.size() + " registo(s) retornado(s).");
        }

        return null;
    }

    public List<DiagBolsaSangue> gerarListaBolsasCompativeis(List<DiagBolsaSangue> bolsasRetornadas)
    {
        List<DiagBolsaSangue> listaCompativel = new ArrayList<>();

        for (DiagBolsaSangue bolsaSangueAux : bolsasRetornadas)
        {
            DiagTipagemDador tipagemDadorAux = diagTipagemDadorFacade.findTipagemDador(bolsaSangueAux.getFkIdCandidatoDador());

            if (diagAdicionarTesteCompatibilidadeBean.eCompativel(diagTipagemDoenteVisualizar, tipagemDadorAux))
            {
                listaCompativel.add(bolsaSangueAux);
            }
        }

        return listaCompativel;
    }

    public String redirecionarAprovarRequisicao(DiagRequisicaoComponenteSanguineo requisicaoComponenteSanguineoAux)
    {

//        if (diagTesteCompatibilidadeFacade.findTesteCompatibilidadeByPaciente(requisicaoComponenteSanguineoAux.getFkIdPaciente()).isEmpty())
//        {
//            Mensagem.erroMsg("Erro: Ainda não foi efectuado nenhum teste de compatibilidade para este paciente!");
//
//            System.out.println("Erro: Ainda não foi efectuado nenhum teste de compatibilidade para este paciente!");
//
//            return null;
//        }
//        else
//        {
        diagRequisicaoComponenteSanguineo = requisicaoComponenteSanguineoAux;

        diagTipagemDoenteVisualizar = diagTipagemDoenteFacade.findTipagemDoente(diagRequisicaoComponenteSanguineo.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente());

        listaComponentesRequisitados = diagRequisicaoComponenteSanguineoFacade.findComponentesBySolicitacao(diagRequisicaoComponenteSanguineo);

        return "aprovarSolicitacaoComponenteSanguineo.xhtml?faces-redirect=true";
//        }

    }

    public void seleccionarBolsaParaTeste(DiagBolsaSangue bolsaSangueAux)
    {
        this.diagBolsaSangueSeleccionada = bolsaSangueAux;

        diagTipagemDadorVisualizar = diagTipagemDadorFacade.findTipagemDador(diagBolsaSangueSeleccionada.getFkIdCandidatoDador());

        if (testeCompatibilidadeJaEfectuado(diagRequisicaoComponenteSanguineo.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente(), diagBolsaSangueSeleccionada, diagRequisicaoComponenteSanguineo))
        {
            if (diagTesteCompatibilidadeFacade.findTesteCompatibilidadeByPacienteAndBolsaAndSolicitacao(diagRequisicaoComponenteSanguineo.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente(), diagBolsaSangueSeleccionada, diagRequisicaoComponenteSanguineo).get(0).getFkIdResultadoTesteCompatibilidade().getPkIdResultadoTesteCompatibilidade() == 1)
            {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogAdicionar').show()");
            }
            else
            {
                Mensagem.erroMsg("Erro: Esta bolsa não é compatível.");
            }
        }
        else
        {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dialogTesteCompatibilidade').show()");
        }

//        return null;
    }

    public String adicionarBolsaDadorNaLista(DiagBolsaSangue bolsaSangueAux)
    {
        if (quantidadeSangueAdicionarNaLista == 0)
        {
            pesquisarBolsasCompativeis = false;
            diagBolsaSanguePesquisar = null;

            Mensagem.erroMsg("Erro: Não se pode adicionar uma quantidade nula.");

            return null;
        }

        //Se a bolsa já se encontra na listaBolsasDaResposta, acrescentar a quantidade
//        {
        if (bolsaJaNaLista(bolsaSangueAux))
        {
            //Se a bolsa já está na lista e ser somada a quantidade a adicionar irá ultrapassar a quantidade requisitada                        
            if (quantidadeComponenteSuperiorRequisitadoSatisfeita(componenteAdicionar, quantidadeSangueAdicionarNaLista))
            {
//                    temMensagemPendente = true;
//                    tipoMensagemPendente = "Erro";
//                    mensagemPendente = "Erro: Se forem adicionados " + quantidadeSangueAdicionarNaLista + " irá ultrapassar a quantidade requisitada para o componente (" + componenteAdicionar.getQuantidadeComponente() + ").";

                Mensagem.erroMsg("Erro: Se forem adicionados " + quantidadeSangueAdicionarNaLista + "ml irá ultrapassar a quantidade requisitada para o componente (" + componenteAdicionar.getQuantidadeComponente() + " ml).");

                return null;
            }
            else
            {
                getBolsaJaNaLista(bolsaSangueAux).setQuantidade(getBolsaJaNaLista(bolsaSangueAux).getQuantidade() + quantidadeSangueAdicionarNaLista);

                //Escrever as alterações nas bolsas já selecionadas na BD
                DiagBolsaSangue bolsaSanguePersistir = diagBolsaSangueFacade.find(bolsaSangueAux.getPkIdBolsaSangue());
                bolsaSanguePersistir.setQuantidadeColhida(bolsaSanguePersistir.getQuantidadeColhida() - quantidadeSangueAdicionarNaLista);
                diagBolsaSangueFacade.edit(bolsaSanguePersistir);

                temMensagemPendente = true;
                tipoMensagemPendente = "Sucesso";
                mensagemPendente = "Bolsa " + bolsaSangueAux.getCodigoColheita() + " selecionada para o componente " + componenteAdicionar.getFkIdComponente().getDescricaoComponenteSanguineo() + ".";
            }
        }
        else
        {
            //Se a bolsa não se encontra na lista mas se for adicionada a quantidade irá ultrapassar a quantidade requisitada
            if (quantidadeSangueAdicionarNaLista > componenteAdicionar.getQuantidadeComponente())
            {
//                    temMensagemPendente = true;
//                    tipoMensagemPendente = "Erro";
//                    mensagemPendente = "Erro: Se forem adicionados " + quantidadeSangueAdicionarNaLista + " irá ultrapassar a quantidade requisitada para o componente (" + componenteAdicionar.getQuantidadeComponente() + ").";

                Mensagem.erroMsg("Erro: Se forem adicionados " + quantidadeSangueAdicionarNaLista + " ml irá ultrapassar a quantidade requisitada para o componente (" + componenteAdicionar.getQuantidadeComponente() + " ml).");

                return null;
            }
            else
            {

                //Adicionar na lista
                DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionada = new DiagRespostaRequisicaoHasComponenteHasTeste();
                bolsaSelecionada.setFkIdRespostaRequisicaoHasComponente(new DiagRespostaRequisicaoComponenteSanguineoHasComponente());
                bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().setFkIdComponente(componenteAdicionar.getFkIdComponente());
                bolsaSelecionada.setFkIdTesteCompatibilidade(new DiagTesteCompatibilidade());
                bolsaSelecionada.getFkIdTesteCompatibilidade().setFkIdBolsaSangue(bolsaSangueAux);
                bolsaSelecionada.setQuantidade(quantidadeSangueAdicionarNaLista);

                listaBolsasDaResposta.add(bolsaSelecionada);

                //Escrever as alterações nas bolsas já selecionadas na BD
                DiagBolsaSangue bolsaSanguePersistir = diagBolsaSangueFacade.find(bolsaSangueAux.getPkIdBolsaSangue());
                bolsaSanguePersistir.setQuantidadeColhida(bolsaSanguePersistir.getQuantidadeColhida() - quantidadeSangueAdicionarNaLista);
                diagBolsaSangueFacade.edit(bolsaSanguePersistir);

                temMensagemPendente = true;
                tipoMensagemPendente = "Sucesso";
                mensagemPendente = "Bolsa " + bolsaSangueAux.getCodigoColheita() + " selecionada para o componente " + componenteAdicionar.getFkIdComponente().getDescricaoComponenteSanguineo() + ".";
            }
        }
//        }

        quantidadeSangueAdicionarNaLista = 0F;
        this.componenteAdicionar = null;
        pesquisarBolsasCompativeis = false;
        itensListaBolsasCompativeis = new ArrayList<>();

//        candidatoDadorPesquisar = null;
        return "aprovarSolicitacaoComponenteSanguineo.xhtml?faces-redirect=true";
    }

    public DiagRespostaRequisicaoHasComponenteHasTeste getBolsaJaNaLista(DiagBolsaSangue bolsaSangueAUx)
    {
        for (DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionada : listaBolsasDaResposta)
        {
            if (bolsaSelecionada.getFkIdTesteCompatibilidade().getFkIdBolsaSangue().getPkIdBolsaSangue() == bolsaSangueAUx.getPkIdBolsaSangue() && bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo().equals(componenteAdicionar.getFkIdComponente().getDescricaoComponenteSanguineo()))
            {
                return bolsaSelecionada;
            }
        }

        return null;
    }

    public boolean bolsaJaNaLista(DiagBolsaSangue bolsaSangueAUx)
    {
        for (DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionada : listaBolsasDaResposta)
        {

            if (bolsaSelecionada.getFkIdTesteCompatibilidade().getFkIdBolsaSangue().getPkIdBolsaSangue() == bolsaSangueAUx.getPkIdBolsaSangue() && bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo().equals(componenteAdicionar.getFkIdComponente().getDescricaoComponenteSanguineo()))
            {
                return true;
            }
        }
        return false;
    }

    public float quantidadeSelecionadaParaBolsa(DiagRespostaRequisicaoHasComponenteHasTeste bolsaSangueAUx)
    {
        if (bolsaSangueAUx.getFkIdRespostaRequisicaoHasComponente().getDiagRespostaRequisicaoHasComponenteHasTesteList() == null)
        {
            return 0;
        }

        float quantidadeTotal = 0F;

        for (int i = 0; i < bolsaSangueAUx.getFkIdRespostaRequisicaoHasComponente().getDiagRespostaRequisicaoHasComponenteHasTesteList().size(); i++)
        {
            DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionada = bolsaSangueAUx.getFkIdRespostaRequisicaoHasComponente().getDiagRespostaRequisicaoHasComponenteHasTesteList().get(i);

            if (bolsaSelecionada.getFkIdTesteCompatibilidade().getFkIdBolsaSangue().getPkIdBolsaSangue() == bolsaSangueAUx.getFkIdTesteCompatibilidade().getFkIdBolsaSangue().getPkIdBolsaSangue() && bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo().equals(bolsaSangueAUx.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo()))
            {
                quantidadeTotal += bolsaSelecionada.getQuantidade();
                System.out.println("quantidadeTotal " + quantidadeTotal);
            }
        }

        return quantidadeTotal;
//        float quantidadeEmUtilizacaoTotal = 0F;
//
//        for (int i = 0; i < listaBolsasDaResposta.size(); i++)
//        {
//            DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionada = listaBolsasDaResposta.get(i);
//
//            if (bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo().equals(bolsaSangueAUx.getFkIdComponente().getDescricaoComponenteSanguineo()))
//            {
//                quantidadeEmUtilizacaoTotal += bolsaSelecionada.getQuantidade();
//            }
//        }
//
//        return quantidadeEmUtilizacaoTotal;
    }

    //Funciona
    public boolean quantidadeComponenteRequisitadoSatisfeita(DiagRequisicaoComponenteSanguineoHasComponente bolsaSangueAUx)
    {
        if (bolsaSangueAUx == null)
        {
            return false;
        }

        float quantidadeEmUtilizacaoTotal = 0F;

        for (int i = 0; i < listaBolsasDaResposta.size(); i++)
        {
            DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionada = listaBolsasDaResposta.get(i);

            if (bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo().equals(bolsaSangueAUx.getFkIdComponente().getDescricaoComponenteSanguineo()))
            {
                quantidadeEmUtilizacaoTotal += bolsaSelecionada.getQuantidade();
            }
        }

        return quantidadeEmUtilizacaoTotal == bolsaSangueAUx.getQuantidadeComponente();
    }

    public boolean quantidadeComponenteSuperiorRequisitadoSatisfeita(DiagRequisicaoComponenteSanguineoHasComponente bolsaSangueAUx, float quantidadeAdicional)
    {
        if (bolsaSangueAUx == null)
        {
            return false;
        }

        float quantidadeEmUtilizacaoTotal = 0F;

        for (int i = 0; i < listaBolsasDaResposta.size(); i++)
        {
            DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionada = listaBolsasDaResposta.get(i);

            if (bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo().equals(bolsaSangueAUx.getFkIdComponente().getDescricaoComponenteSanguineo()))
            {
                quantidadeEmUtilizacaoTotal += bolsaSelecionada.getQuantidade();
            }
        }

        return (quantidadeEmUtilizacaoTotal + quantidadeAdicional) > bolsaSangueAUx.getQuantidadeComponente();
    }

    public boolean todosComponentesRequisitadosSatisfeitos()
    {
        if (listaBolsasDaResposta.isEmpty())
        {
            return false;
        }

//        float quantidadeEmUtilizacaoTotal = 0F;
        for (int j = 0; j < listaComponentesRequisitados.size(); j++)
        {
            DiagRequisicaoComponenteSanguineoHasComponente bolsaSangueAUx = listaComponentesRequisitados.get(j);

            System.out.println("bolsaSangueAUx.getFkIdComponente().getDescricaoComponenteSanguineo() " + bolsaSangueAUx.getFkIdComponente().getDescricaoComponenteSanguineo());

            for (int i = 0; i < listaBolsasDaResposta.size(); i++)
            {
//                DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionada = listaBolsasDaResposta.get(i);

                if (!quantidadeComponenteRequisitadoSatisfeita(bolsaSangueAUx))
                {
                    return false;
                }

//                System.out.println("bolsaSelecionada.getFkIdTesteCompatibilidade().getFkIdBolsaSangue().getCodigoColheita()" + bolsaSelecionada.getFkIdTesteCompatibilidade().getFkIdBolsaSangue().getCodigoColheita());
//                quantidadeEmUtilizacaoTotal += quantidadeSelecionadaParaBolsa(bolsaSelecionada);
//
//                if (!(bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo().equals(bolsaSangueAUx.getFkIdComponente().getDescricaoComponenteSanguineo())
//                        && quantidadeEmUtilizacaoTotal == bolsaSangueAUx.getQuantidadeComponente()))
//                {
//                    System.out.println("bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo() " + bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo());
//                    System.out.println("quantidadeEmUtilizacaoTotal " + bolsaSangueAUx.getQuantidadeComponente());
//
//                    return false;
//                }
            }
        }
        return true;
    }

    public boolean existemBolsasSelecionadas()
    {
        return !listaBolsasDaResposta.isEmpty();
    }
//
//    public Float somaQuantidadesBolsasSelecionadas()
//    {
//        Float somaQuantidadesBolsasSelecionadas = new Float(0F);
//
//        for (DiagBolsaSelecionadaRespostaTransfusao bolsaJaSelecionada : gerarSublistaBolsaSelecionada(componenteAdicionar))
//        {
//            somaQuantidadesBolsasSelecionadas += bolsaJaSelecionada.getQuantidadeEmUtilizacaoBolsa();
//        }
//
//        return somaQuantidadesBolsasSelecionadas;
//    }
//

    public List<DiagRespostaRequisicaoHasComponenteHasTeste> gerarSublistaBolsaSelecionada(DiagRequisicaoComponenteSanguineoHasComponente componenteAux)
    {
        List<DiagRespostaRequisicaoHasComponenteHasTeste> subListaBolsas = new ArrayList<>();

        for (DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionada : listaBolsasDaResposta)
        {
            if (componenteAux.getFkIdComponente().getPkIdComponenteSanguineo() == bolsaSelecionada.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getPkIdComponenteSanguineo())
            {
                subListaBolsas.add(bolsaSelecionada);
            }
        }

        return subListaBolsas;
    }

    public String aprovarRequisicao()
    {
        try
        {
            //Testar
            if (!todosComponentesRequisitadosSatisfeitos())
            {
                Mensagem.erroMsg("Erro: Ainda não foram selecionadas bolsas para todos os componentes requisitados.");

                return null;
            }

            GregorianCalendar data = new GregorianCalendar();

            userTransaction.begin();

//            diagRespostaRequisicaoComponenteSanguineo = getInstancia();
            diagRespostaRequisicaoComponenteSanguineo.setDataResposta(new Date(data.getTimeInMillis()));
            diagRespostaRequisicaoComponenteSanguineo.setFkIdRequisicaoComponenteSanguineo(diagRequisicaoComponenteSanguineo);

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpSession sessao = request.getSession();
            SegConta sessaoActual = (SegConta) sessao.getAttribute("sessaoActual");

            diagRespostaRequisicaoComponenteSanguineo.setFkIdFuncionario(sessaoActual.getFkIdFuncionario());

            //Pesquisa Anticorpos
            diagRespostaRequisicaoComponenteSanguineo.setResultadoPesquisaAnticorposIrregulares(diagPesquisaAntiCorposIrregulares);
            //Observaçoes
            //Adicionar ao form
            diagRespostaRequisicaoComponenteSanguineoFacade.create(diagRespostaRequisicaoComponenteSanguineo);

            //Adicionar um diag_resposta_requisicao_componente_sanguineo_has_componente para cada componente na lista de requisição e adicionar na bd
            //com a chave da resposta e do tal componente
            for (DiagRequisicaoComponenteSanguineoHasComponente diagRequisicaoComponenteSanguineoHasComponente : listaComponentesRequisitados)
            {
                DiagRespostaRequisicaoComponenteSanguineoHasComponente diagRespostaRequisicaoComponenteSanguineoHasComponente = new DiagRespostaRequisicaoComponenteSanguineoHasComponente();
                diagRespostaRequisicaoComponenteSanguineoHasComponente.setFkIdComponente(diagRequisicaoComponenteSanguineoHasComponente.getFkIdComponente());
                diagRespostaRequisicaoComponenteSanguineoHasComponente.setFkIdRespostaRequisicaoComponente(diagRespostaRequisicaoComponenteSanguineo);

                diagRespostaRequisicaoComponenteSanguineoHasComponenteFacade.create(diagRespostaRequisicaoComponenteSanguineoHasComponente);
                //Adicionar as listas de bolsas utilizadas
                for (DiagRespostaRequisicaoHasComponenteHasTeste diagRespostaRequisicaoHasComponenteHasTeste : listaBolsasDaResposta)
                {
                    if (diagRespostaRequisicaoHasComponenteHasTeste.getFkIdRespostaRequisicaoHasComponente().getFkIdComponente().getDescricaoComponenteSanguineo().equals(diagRespostaRequisicaoComponenteSanguineoHasComponente.getFkIdComponente().getDescricaoComponenteSanguineo()))
                    {
                        diagRespostaRequisicaoHasComponenteHasTeste.setFkIdTesteCompatibilidade(diagTesteCompatibilidadeFacade.findTesteCompatibilidadeByPacienteAndBolsaAndSolicitacao(diagRequisicaoComponenteSanguineo.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente(), diagRespostaRequisicaoHasComponenteHasTeste.getFkIdTesteCompatibilidade().getFkIdBolsaSangue(), diagRequisicaoComponenteSanguineo).get(0));
                        diagRespostaRequisicaoHasComponenteHasTeste.setFkIdRespostaRequisicaoHasComponente(diagRespostaRequisicaoComponenteSanguineoHasComponente);
                        diagRespostaRequisicaoHasComponenteHasTesteFacade.create(diagRespostaRequisicaoHasComponenteHasTeste);
                    }
                }
            }

            userTransaction.commit();

            diagPesquisaAntiCorposIrregulares = new DiagResultadoExameCandidato(2);
            diagRespostaRequisicaoComponenteSanguineo = getInstancia();

            DiagRequisicaoComponenteSanguineoBean diagRequisicaoComponenteSanguineoBean = DiagRequisicaoComponenteSanguineoBean.getInstanciaBean();

            diagRequisicaoComponenteSanguineoBean.setTemMensagemPendente(true);
            diagRequisicaoComponenteSanguineoBean.setTipoMensagemPendente("Sucesso");
            diagRequisicaoComponenteSanguineoBean.setMensagemPendente("Solicitação de transfusão aprovada com sucesso!");
        }
        catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = ex.toString();
            }
            catch (IllegalStateException | SecurityException | SystemException ex1)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }
        return "solicitacoesComponentesSanguineos.xhtml?faces-redirect=true";
    }

    private void reverterAlteracoesBolsasSelecionadas()
    {
        for (int i = 0; i < listaBolsasDaResposta.size(); i++)
        {
            DiagRespostaRequisicaoHasComponenteHasTeste bolsaSelecionadaRespostaTransfusao = listaBolsasDaResposta.get(i);

            DiagBolsaSangue bolsaSangueReverter = diagBolsaSangueFacade.find(bolsaSelecionadaRespostaTransfusao.getFkIdTesteCompatibilidade().getFkIdBolsaSangue().getPkIdBolsaSangue());

            System.out.println("bolsaSangueReverter.getQuantidadeColhida() " + bolsaSangueReverter.getQuantidadeColhida());
            System.out.println("bolsaSelecionadaRespostaTransfusao.getQuantidade() " + bolsaSelecionadaRespostaTransfusao.getQuantidade());

            bolsaSangueReverter.setQuantidadeColhida(bolsaSangueReverter.getQuantidadeColhida() + bolsaSelecionadaRespostaTransfusao.getQuantidade());
            diagBolsaSangueFacade.edit(bolsaSangueReverter);

        }
    }

    public void voltarParaDialogPesquisarDadores()
    {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogPesquisar').show()");
//        context.execute("dialogAdicionar.hide()");
    }

    public String voltarParaPesquisaSolicitacoes()
    {
        reverterAlteracoesBolsasSelecionadas();

        diagPesquisaAntiCorposIrregulares = null;
        diagRespostaRequisicaoComponenteSanguineo = null;

        listaComponentesRequisitados = null;

        listaBolsasDaResposta = new ArrayList<>();
        return "solicitacoesComponentesSanguineos.xhtml?faces-redirect=true";
    }

    public String limparPesquisaBolsasCompativeis()
    {
        pesquisarBolsasCompativeis = false;
        itensListaBolsasCompativeis = new ArrayList<>();

        diagBolsaSanguePesquisar = null;

        return null;
    }
}
