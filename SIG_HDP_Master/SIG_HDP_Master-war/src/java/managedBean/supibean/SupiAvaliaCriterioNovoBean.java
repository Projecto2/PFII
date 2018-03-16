/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.RhEstagiario;
import entidade.SupiAreaAvaliacao;
import entidade.SupiAvaliaCriterios;
import entidade.SupiAvaliacaoDesempenho;
import entidade.SupiCriterioAvaliacao;
import entidade.SupiPontuacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.RhEscolaUniversidadeFacade;
import sessao.RhEstagiarioFacade;
import sessao.SupiAreaAvaliacaoFacade;
import sessao.SupiAvaliaCriteriosFacade;
import sessao.SupiAvaliacaoDesempenhoFacade;
import sessao.SupiCriterioAvaliacaoFacade;
import sessao.SupiItensAvaliacaoFacade;
import sessao.SupiPontuacaoFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.supi.SupiCriterioAuxiliar;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiAvaliaCriterioNovoBean implements Serializable
{

    @EJB
    private SupiPontuacaoFacade supiPontuacaoFacade;

    @EJB
    private SupiAreaAvaliacaoFacade supiAreaAvaliacaoFacade;

    @EJB
    private RhEscolaUniversidadeFacade rhEscolaUniversidadeFacade;
    @EJB
    private SupiItensAvaliacaoFacade supiItensAvaliacaoFacade;
    @EJB
    private RhEstagiarioFacade rhEstagiarioFacade;
    @EJB
    private SupiCriterioAvaliacaoFacade supiCriterioAvaliacaoFacade;
    @EJB
    private SupiAvaliacaoDesempenhoFacade supiAvaliacaoDesempenhoFacade;
    @EJB
    private SupiAvaliaCriteriosFacade supiAvaliaCriteriosFacade;

    private RhEstagiario estagiario, pesquisaEstagiario, visualizarEstagiario;
    private SupiAvaliacaoDesempenho avaliacaoDesempenho, pesquisaAvaliacaoDesempenho, visualizarDesempenho;
    private List<SupiAvaliacaoDesempenho> listAvaliacaoDesempenho;
    private List<SupiCriterioAvaliacao> listaCriterios;
    private List<SupiAreaAvaliacao> listaSupiAreaAvaliacao;
    private List<SupiAvaliaCriterios> listaSupiAvaliaCriterios;
    private SupiAvaliaCriterios supiAvaliaCriterios;
    private SupiAreaAvaliacao areaAvaliacao;
    private SupiPontuacao supiPontuacao;
    private List<SupiPontuacao> listaSupiPontuacao;
    private String[] numeros;
    private String observacao;
    private String observacaoInstituicao;

    private List<SupiCriterioAuxiliar> listaCriterioPontuacao;
    private int pontuacao;

    Mensagem mensagem;

    /**
     * Creates a new instance of SupiAvaliaCriterioNovoBean
     */
    public SupiAvaliaCriterioNovoBean()
    {
    }

    public static SupiAvaliaCriterioNovoBean getInstanciaBean()
    {
        return (SupiAvaliaCriterioNovoBean) GeradorCodigo.getInstanciaBean("supiAvaliaCriterioNovoBean");
    }

    public static SupiAvaliaCriterios getInstancia()
    {
        SupiAvaliaCriterios avaliaCriterios = new SupiAvaliaCriterios();
        avaliaCriterios.setFkIdAvaliacaoDesempenho(SupiAvaliacaoNovaBean.getInstancia());
        avaliaCriterios.setFkIdCriterioAvaliacao(new SupiCriterioAvaliacao());
        avaliaCriterios.setFkIdPontuacao(new SupiPontuacao());

        return avaliaCriterios;
    }

    public int getPontuacao()
    {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao)
    {
        this.pontuacao = pontuacao;
    }

    public List<SupiCriterioAuxiliar> getListaCriterioPontuacao()
    {
        return listaCriterioPontuacao;
    }

    public void setListaCriterioPontuacao(List<SupiCriterioAuxiliar> listaCriterioPontuacao)
    {
        this.listaCriterioPontuacao = listaCriterioPontuacao;
    }

    public RhEstagiario getEstagiario()
    {
        return estagiario;
    }

    public void setEstagiario(RhEstagiario estagiario)
    {
        this.estagiario = estagiario;
    }

    public RhEstagiario getPesquisaEstagiario()
    {
        return pesquisaEstagiario;
    }

    public void setPesquisaEstagiario(RhEstagiario pesquisaEstagiario)
    {
        this.pesquisaEstagiario = pesquisaEstagiario;
    }

    public SupiAvaliacaoDesempenho getAvaliacaoDesempenho()
    {
        return avaliacaoDesempenho;
    }

    public void setAvaliacaoDesempenho(SupiAvaliacaoDesempenho avaliacaoDesempenho)
    {
        this.avaliacaoDesempenho = avaliacaoDesempenho;
    }

    public SupiAvaliacaoDesempenho getPesquisaAvaliacaoDesempenho()
    {
        return pesquisaAvaliacaoDesempenho;
    }

    public void setPesquisaAvaliacaoDesempenho(SupiAvaliacaoDesempenho pesquisaAvaliacaoDesempenho)
    {
        this.pesquisaAvaliacaoDesempenho = pesquisaAvaliacaoDesempenho;
    }

    public List<SupiAvaliacaoDesempenho> getListAvaliacaoDesempenho()
    {
        return listAvaliacaoDesempenho;
    }

    public void setListAvaliacaoDesempenho(List<SupiAvaliacaoDesempenho> listAvaliacaoDesempenho)
    {
        this.listAvaliacaoDesempenho = listAvaliacaoDesempenho;
    }

    public List<SupiCriterioAvaliacao> getListaCriterios()
    {
        return listaCriterios;
    }

    public void setListaCriterios(List<SupiCriterioAvaliacao> listaCriterios)
    {
        this.listaCriterios = listaCriterios;
    }

    public List<SupiAreaAvaliacao> getListaSupiAreaAvaliacao()
    {
        return listaSupiAreaAvaliacao;
    }

    public void setListaSupiAreaAvaliacao(List<SupiAreaAvaliacao> listaSupiAreaAvaliacao)
    {
        this.listaSupiAreaAvaliacao = listaSupiAreaAvaliacao;
    }

    public SupiAvaliaCriterios getSupiAvaliaCriterios()
    {
        if (supiAvaliaCriterios == null)
        {
            supiAvaliaCriterios = getInstancia();
        }
        return supiAvaliaCriterios;
    }

    public void setSupiAvaliaCriterios(SupiAvaliaCriterios supiAvaliaCriterios)
    {
        this.supiAvaliaCriterios = supiAvaliaCriterios;
    }

    public SupiAreaAvaliacao getAreaAvaliacao()
    {
        return areaAvaliacao;
    }

    public void setAreaAvaliacao(SupiAreaAvaliacao areaAvaliacao)
    {
        this.areaAvaliacao = areaAvaliacao;
    }

    public List<SupiAvaliaCriterios> getListaAvaliaCriterios()
    {
        listaSupiAvaliaCriterios = supiAvaliaCriteriosFacade.findAll();
        return listaSupiAvaliaCriterios;
    }

    public List<SupiPontuacao> getListaPontuacao()
    {
        listaSupiPontuacao = supiPontuacaoFacade.findAll();
        return listaSupiPontuacao;
    }

    public void atualizarPontuacao(int pkCriterio)
    {

        SupiCriterioAuxiliar pontCriterio = new SupiCriterioAuxiliar();

        if (listaCriterioPontuacao == null)
        {
            listaCriterioPontuacao = new ArrayList();
        }

        if (supiAvaliaCriterios.getFkIdPontuacao().getPkIdPontuacao() != null)
        {
            pontCriterio.setPkCriterio(pkCriterio);
            pontCriterio.setPontuacao(supiPontuacaoFacade.find(supiAvaliaCriterios.getFkIdPontuacao().getPkIdPontuacao()).getValor());

            if (listaCriterioPontuacao.size() > 0)
            {
                if (verificarCriterio(listaCriterioPontuacao, pkCriterio))
                {
                    listaCriterioPontuacao.set(posicacoCriterio(listaCriterioPontuacao, pkCriterio), pontCriterio);
                } else
                {
                    listaCriterioPontuacao.add(pontCriterio);
                }
            } else
            {
                listaCriterioPontuacao.add(pontCriterio);
            }

        }

//        supiAvaliaCriterios.setFkIdPontuacao(new SupiPontuacao());
//        
//         System.out.println("Pts: "+pontuacao);
//        System.out.println("TsDf: "+supiAvaliaCriterios.getFkIdPontuacao());
    }

    public boolean verificarCriterio(List<SupiCriterioAuxiliar> listaAux, int criterio)
    {
        for (int i = 0; i < listaAux.size(); i++)
        {
            if (listaAux.get(i).getPkCriterio() == criterio)
            {
                return true;
            }
        }
        return false;
    }

    public int posicacoCriterio(List<SupiCriterioAuxiliar> listaAux, int criterio)
    {
        for (int i = 0; i < listaAux.size(); i++)
        {
            if (listaAux.get(i).getPkCriterio() == criterio)
            {
                return i;
            }
        }
        return -1;
    }

    public int getPontuacaoTotal()
    {
        int pontos = 0;
        
        if (listaCriterioPontuacao == null || listaCriterioPontuacao.isEmpty())
        {
           return pontos;
        } 
        else
        {
            for (int i = 0; i < listaCriterioPontuacao.size(); i++)
            {
                pontos += listaCriterioPontuacao.get(i).getPontuacao();
            }
        }
        return pontos;
    }
}
