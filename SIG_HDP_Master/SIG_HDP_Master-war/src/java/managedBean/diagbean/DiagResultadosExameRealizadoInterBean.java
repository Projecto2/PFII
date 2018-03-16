/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AdmsEstadoPagamento;
import entidade.AdmsPaciente;
import entidade.AdmsServico;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.DiagCategoriaExame;
import entidade.DiagExame;
import entidade.DiagExameRealizado;
import entidade.DiagTipoAmostra;
import entidade.GrlPessoa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.admsbean.servicosSolicitadosBean.AdmsAgendamentoBean;
import static managedBean.diagbean.DiagResultadosExameRealizadoBean.getInstancia;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.DiagCategoriaExameFacade;
import sessao.DiagExameFacade;
import sessao.DiagExameRealizadoFacade;
import sessao.DiagTipoAmostraFacade;
import util.Mensagem;
import util.diag.Defs;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagResultadosExameRealizadoInterBean implements Serializable
{
    @EJB
    private DiagCategoriaExameFacade diagCategoriaExameFacade;

    @EJB
    private DiagTipoAmostraFacade diagTipoAmostraFacade;
    @EJB
    private DiagExameFacade diagExameFacade;
    @EJB
    private DiagExameRealizadoFacade diagExameRealizadoFacade;

    private DiagExameRealizado diagExameRealizadoPesquisar, diagExameRealizadoVisualizar;
    private Date dataInicioPesquisa, dataFimPesquisa;

    List<DiagExameRealizado> itens;
    private List<DiagExame> listaExames;
    
    private int numeroRegistos = 10;

    /**
     * Creates a new instance of DiagResultadosExameRealizadoInterBean
     */
    public DiagResultadosExameRealizadoInterBean()
    {
    }

    public static DiagResultadosExameRealizadoInterBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagResultadosExameRealizadoInterBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagResultadosExameRealizadoInterBean");
    }

    public static DiagExameRealizado getInstancia()
    {
        AdmsServicoSolicitado admsServicoSolicitado = AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado();
        admsServicoSolicitado.setFkIdClassificacaoServicoSolicitado(new AdmsClassificacaoServicoSolicitado());
        admsServicoSolicitado.setFkIdEstadoPagamento(new AdmsEstadoPagamento());
        admsServicoSolicitado.setFkIdRecepcionista(RhFuncionarioNovoBean.getInstancia());
        admsServicoSolicitado.setFkIdServico(new AdmsServico());

        AdmsPaciente admsPaciente = new AdmsPaciente();
        //Remover Linha a Seguir
//        admsPaciente.setPkIdPaciente(1L);
        admsPaciente.setFkIdPessoa(new GrlPessoa());

        AdmsSolicitacao admsSolicitacao = new AdmsSolicitacao();
        admsSolicitacao.setFkIdPaciente(admsPaciente);

        admsServicoSolicitado.setFkIdSolicitacao(admsSolicitacao);

        DiagExameRealizado diagExameRealizado = new DiagExameRealizado();
        diagExameRealizado.setFkIdServicoSolicitado(admsServicoSolicitado);

        DiagExame exameAux = DiagExameBean.getInstancia();
        exameAux.setPkIdExame(Defs.EXAME_LABORATORIO_DEFAULT_ID);
        diagExameRealizado.setFkIdExame(exameAux);
        diagExameRealizado.setFkIdAmostra(DiagAmostraBean.getInstancia());
        diagExameRealizado.getFkIdAmostra().getFkIdTipoAmostra().setPkIdTipoAmostra(Defs.TIPO_AMOSTRA_DEFAULT_ID);
        diagExameRealizado.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return diagExameRealizado;
    }

    public int getNumeroRegistos()
    {
        return numeroRegistos;
    }

    public void setNumeroRegistos(int numeroRegistos)
    {
        this.numeroRegistos = numeroRegistos;
    }
    
    public List<DiagExameRealizado> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagExameRealizado> itens)
    {
        this.itens = itens;
    }

    public List<DiagExame> getListaExames()
    {
        return listaExames;
    }

    public void setListaExames(List<DiagExame> listaExames)
    {
        this.listaExames = listaExames;
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

    public List<DiagCategoriaExame> findAllCategoria()
    {
        return diagCategoriaExameFacade.findAll();
    }
    
    public void carregarListaExamesPorCategoria()
    {
        listaExames = diagExameFacade.findExamesByCategoria(diagExameRealizadoPesquisar.getFkIdExame().getFkIdCategoriaExame());
    }

    public List<DiagTipoAmostra> findAllTiposAmostra()
    {
        return diagTipoAmostraFacade.findAll();
    }

    public Date getMomentoActual()
    {
        return new Date();
    }

    public void pesquisarResultadosExamesRealizados()
    {
        itens = diagExameRealizadoFacade.findPesquisaResultadosExamesInter(diagExameRealizadoPesquisar, dataInicioPesquisa, dataFimPesquisa, numeroRegistos);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
    }

    public String limparPesquisa()
    {
        diagExameRealizadoPesquisar = null;
        itens = new ArrayList<>();

        dataInicioPesquisa = dataFimPesquisa = null;

        return "resultadosExamesInter.xhtml?faces-redirect=true";
    }
    
    public void setPaciente(AdmsPaciente adms)
    {
//        diagExameRealizadoPesquisar.getFkIdServicoSolicitado().getFkIdSolicitacao().setFkIdPaciente(adms);
    }
}
