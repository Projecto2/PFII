/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.rhbean.avaliacaodedesempenho;

import entidade.RhCriterioDeAvaliacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessao.RhClassificacaoDoCriterioFacade;
import sessao.RhCriterioDeAvaliacaoFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhCriterioDeAvaliacaoListarBean implements Serializable
{

//    @Resource
//    private UserTransaction userTransaction;
//
    @EJB
    private RhClassificacaoDoCriterioFacade classificacaoDoCriterioFacade;
    @EJB
    private RhCriterioDeAvaliacaoFacade criterioDeAvaliacaoFacade;
//
//    /**
//     * Entidade criterioDeAvaliacao
//     */
//    private RhCriterioDeAvaliacao criterioDeAvaliacao, criterioDeAvaliacaoVisualizar;
    private List<RhCriterioDeAvaliacao> criterioDeAvaliacaoList;
    
    public RhCriterioDeAvaliacaoListarBean ()
    {
    }

//    public static RhCriterioDeAvaliacao getInstancia ()
//    {
//        RhCriterioDeAvaliacao criterio = new RhCriterioDeAvaliacao();
//
//        criterio.setRhClassificacaoDoCriterioList(new ArrayList<RhClassificacaoDoCriterio>());
//
//        return criterio;
//    }
//
//    public RhCriterioDeAvaliacao getCriterioDeAvaliacao ()
//    {
//        if (criterioDeAvaliacao == null)
//        {
//            criterioDeAvaliacao = getInstancia();
//            criterioDeAvaliacao.setRhClassificacaoDoCriterioList(new ArrayList<RhClassificacaoDoCriterio>());
//        }
//        return criterioDeAvaliacao;
//    }
//
//    public void setCriterioDeAvaliacao (RhCriterioDeAvaliacao criterioDeAvaliacao)
//    {
//
//        if (criterioDeAvaliacao != null)
//        {
//            criterioDeAvaliacao.setRhClassificacaoDoCriterioList(classificacaoDoCriterioFacade.pesquisaPorCriterioDeAvaliacao(criterioDeAvaliacao.getPkIdCriterioDeAvaliacao()));
//        }
//
//        this.criterioDeAvaliacao = criterioDeAvaliacao;
//    }
//
//    public void setCriterioDeAvaliacaoVisualizar (RhCriterioDeAvaliacao criterioDeAvaliacaoVisualizar)
//    {
//        this.criterioDeAvaliacaoVisualizar = criterioDeAvaliacaoVisualizar;
//    }
//
    public List<RhCriterioDeAvaliacao> getCriterioDeAvaliacaoList ()
    {
        criterioDeAvaliacaoList = findAll();
        return criterioDeAvaliacaoList;
    }
    
    public List<RhCriterioDeAvaliacao> findAll ()
    {
        List<RhCriterioDeAvaliacao> criteriosDeAvaliacaoList = new ArrayList<>();

        for (RhCriterioDeAvaliacao criterio : criterioDeAvaliacaoFacade.findAll())
        {
            criterio.setRhClassificacaoDoCriterioList(classificacaoDoCriterioFacade.pesquisaPorCriterioDeAvaliacao(criterio.getPkIdCriterioDeAvaliacao()));
            criteriosDeAvaliacaoList.add(criterio);
        }

        return criteriosDeAvaliacaoList;
    }
    
}
