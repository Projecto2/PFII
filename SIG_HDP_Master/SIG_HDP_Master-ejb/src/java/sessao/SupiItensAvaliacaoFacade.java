/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiItensAvaliacao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author helga
 */
@Stateless
public class SupiItensAvaliacaoFacade extends AbstractFacade<SupiItensAvaliacao> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiItensAvaliacaoFacade() {
        super(SupiItensAvaliacao.class);
    }
    
    public List<SupiItensAvaliacao> findListaItensCriterio(Integer idAvaliacao)
    {
        Query query;
        query = em.createQuery("SELECT i FROM SupiItensAvaliacao i WHERE i.fkIdAvaliacao.pkIdAvaliacaoDesempenho = :idAvaliacao");

        query.setParameter("idAvaliacao", idAvaliacao);

        List<SupiItensAvaliacao> results = (List<SupiItensAvaliacao>) query.getResultList();

        System.out.println("results:"+results);
        if (results.isEmpty())
        {
            return null;
        } else
        {
            return results;
        }

    }
    
}
