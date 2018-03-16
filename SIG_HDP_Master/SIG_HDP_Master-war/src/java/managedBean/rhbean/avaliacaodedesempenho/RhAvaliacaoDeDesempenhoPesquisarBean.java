/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.avaliacaodedesempenho;

import entidade.GrlPessoa;
import entidade.RhAvaliacaoDeDesempenho;
import entidade.RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartModel;
import sessao.RhAvaliacaoDeDesempenhoFacade;
import sessao.RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoFacade;
import sessao.RhClassificacaoDoCriterioFacade;
import sessao.RhCriterioDeAvaliacaoFacade;
import sessao.RhFuncionarioFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhAvaliacaoDeDesempenhoPesquisarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhAvaliacaoDeDesempenhoFacade avaliacaoDeDesempenhoFacade;
    @EJB
    private RhCriterioDeAvaliacaoFacade criterioDeAvaliacaoFacade;
    @EJB
    private RhClassificacaoDoCriterioFacade classificacaoDoCriterioFacade;
    @EJB
    private RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoFacade avaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;

    /**
     * Entidades
     */
    private RhAvaliacaoDeDesempenho avaliacaoDeDesempenhoVisualizar, avaliacaoDeDesempenhoRemover;
    private List<RhAvaliacaoDeDesempenho> avaliacoesDeDesempenhoPesquisadasList;
    private Integer anoPesquisa;

    /**
     * Creates a new instance of RhAvaliacaoDeDesempenhoPesquisarBean
     */
    public RhAvaliacaoDeDesempenhoPesquisarBean ()
    {
    }

    public RhAvaliacaoDeDesempenho getAvaliacaoDeDesempenhoVisualizar ()
    {
        if (avaliacaoDeDesempenhoVisualizar == null)
        {
            avaliacaoDeDesempenhoVisualizar = RhAvaliacaoDeDesempenhoNovaBean.getInstancia();
        }

        return avaliacaoDeDesempenhoVisualizar;
    }

    public void setAvaliacaoDeDesempenhoVisualizar (RhAvaliacaoDeDesempenho avaliacaoDeDesempenhoVisualizar)
    {
        this.avaliacaoDeDesempenhoVisualizar = avaliacaoDeDesempenhoVisualizar;
    }

    public RhAvaliacaoDeDesempenho getAvaliacaoDeDesempenhoRemover ()
    {
        return avaliacaoDeDesempenhoRemover;
    }

    public void setAvaliacaoDeDesempenhoRemover (RhAvaliacaoDeDesempenho avaliacaoDeDesempenhoRemover)
    {
        this.avaliacaoDeDesempenhoRemover = avaliacaoDeDesempenhoRemover;
    }

    public List<RhAvaliacaoDeDesempenho> getAvaliacoesDeDesempenhoPesquisadasList ()
    {
        if (avaliacoesDeDesempenhoPesquisadasList == null)
        {
            avaliacoesDeDesempenhoPesquisadasList = new ArrayList<>();
        }
        return avaliacoesDeDesempenhoPesquisadasList;
    }

    public Integer getAnoPesquisa ()
    {
        return anoPesquisa;
    }

    public void setAnoPesquisa (Integer anoPesquisa)
    {
        this.anoPesquisa = anoPesquisa;
    }

    public String limparPesquisa ()
    {
        avaliacaoDeDesempenhoVisualizar = null;
        avaliacaoDeDesempenhoRemover = null;
        avaliacoesDeDesempenhoPesquisadasList = null;
        anoPesquisa = null;

        return "avaliacaoDeDesempenhoHomeRh.xhtml?faces-redirect=true";
    }

    public ChartModel getModeloGraficoDeBarras ()
    {
        BarChartModel modelo = new BarChartModel();

        BarChartSeries funcionariosSerie = new BarChartSeries();
        funcionariosSerie.setLabel("Funcionários");

        if (avaliacoesDeDesempenhoPesquisadasList != null && !avaliacoesDeDesempenhoPesquisadasList.isEmpty())
        {
            for (RhAvaliacaoDeDesempenho avl : avaliacoesDeDesempenhoPesquisadasList)
            {
                GrlPessoa p = avl.getFkIdFuncionario().getFkIdPessoa();
                String nomeCompleto = p.getNome() + " " + p.getNomeDoMeio() + " " + p.getSobreNome();

                funcionariosSerie.set(nomeCompleto, avl.getClassificacao());
            }
        }

        modelo.addSeries(funcionariosSerie);
        modelo.setTitle("Avaliações de Desempenho " + anoPesquisa);
        Axis yAxis = modelo.getAxis(AxisType.Y);
        yAxis.setLabel("Classificações");
        yAxis.setMin(0);
        yAxis.setMax(17);

        Axis xAxis = modelo.getAxis(AxisType.X);
        xAxis.setTickAngle(-60);

        return modelo;
    }

    public String corDaClassificacaoGeral ()
    {
        Double valor = getAvaliacaoDeDesempenhoVisualizar().getClassificacao();

        if (valor < 7.5)
        {
            return "red";
        }
        else if (valor < 12.5)
        {
            return "orange";
        }
        else if (valor < 15.5)
        {
            return "blue";
        }
        else if (valor <= 17)
        {
            return "green";
        }
        else
        {
            return null;
        }
    }

    public void pesquisar ()
    {
        avaliacoesDeDesempenhoPesquisadasList = new ArrayList<>();

        for (RhAvaliacaoDeDesempenho av : avaliacaoDeDesempenhoFacade.pesquisarPorAno(anoPesquisa))
        {
            //Pegando as classificações obtidas nos critérios
            av.setRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList(avaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoFacade.pesquisarPorAvaliacao(av.getPkIdAvaliacaoDeDesempenho()));
            avaliacoesDeDesempenhoPesquisadasList.add(av);
        }

        if (avaliacoesDeDesempenhoPesquisadasList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + avaliacoesDeDesempenhoPesquisadasList.size() + ")");
        }
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            for (RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao classificNoCriterio : avaliacaoDeDesempenhoRemover.getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList())
            {
                avaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoFacade.remove(classificNoCriterio);
            }

            avaliacaoDeDesempenhoRemover.setRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList(null);
            avaliacaoDeDesempenhoFacade.remove(avaliacaoDeDesempenhoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Avaliação de desempenho removida com sucesso!");
            pesquisar();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Esta avaliação de desempenho possui registro de actividades, impossível remover !");
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return null;
    }

}
