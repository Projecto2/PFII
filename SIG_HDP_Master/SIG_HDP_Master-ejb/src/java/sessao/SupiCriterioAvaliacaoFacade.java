/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiCriterioAvaliacao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author helga
 */
@Stateless
public class SupiCriterioAvaliacaoFacade extends AbstractFacade<SupiCriterioAvaliacao> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiCriterioAvaliacaoFacade() {
        super(SupiCriterioAvaliacao.class);
    }
    
     public List<SupiCriterioAvaliacao> findCriterio(SupiCriterioAvaliacao criterio)
    {
        String query = constroiQuery(criterio);

        TypedQuery<SupiCriterioAvaliacao> t = em.createQuery(query, SupiCriterioAvaliacao.class);

        if (criterio.getDescricaoCriterio() != null && !criterio.getDescricaoCriterio().trim().isEmpty())
        {
            t.setParameter("descricaoCriterio", criterio.getDescricaoCriterio() + "%");
        }

//        if (criterio.getFkIdPontuacao().getPkIdPontuacao() != null)
//        {
//            t.setParameter("pontuacao", criterio.getFkIdPontuacao().getPkIdPontuacao());
//        }

        List<SupiCriterioAvaliacao> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(SupiCriterioAvaliacao criterio)
    {
        String query = "SELECT e FROM SupiCriterioAvaliacao e WHERE e.pkIdCriterioAvaliacao = e.pkIdCriterioAvaliacao";
        
        if (criterio.getDescricaoCriterio() != null && !criterio.getDescricaoCriterio().trim().isEmpty())
        {
            query += " AND e.descricaoCriterio LIKE :descricaoCriterio";
        }

//        if (criterio.getFkIdPontuacao().getPkIdPontuacao() != null)
//        {
//            query += " AND e.fkIdPontuacao.pkIdPontuacao = :pontuacao";
//        }

        query += " ORDER BY e.descricaoCriterio";

        return query;
    }
    
}
