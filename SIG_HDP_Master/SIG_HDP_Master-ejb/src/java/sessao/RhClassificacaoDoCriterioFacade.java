/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhClassificacaoDoCriterio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhClassificacaoDoCriterioFacade extends AbstractFacade<RhClassificacaoDoCriterio>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhClassificacaoDoCriterioFacade ()
    {
        super(RhClassificacaoDoCriterio.class);
    }

    /**
     * Procura as classificações de um critério de avaliação
     * 
     * @param idCriterioDeAvaliacao
     * @return
     */
    public List<RhClassificacaoDoCriterio> pesquisaPorCriterioDeAvaliacao (Integer idCriterioDeAvaliacao)
    {
        TypedQuery<RhClassificacaoDoCriterio> t;
        t = em.createQuery("SELECT c FROM RhClassificacaoDoCriterio c "
                           + "WHERE c.fkIdCriterioDeAvaliacao.pkIdCriterioDeAvaliacao = :idCriterioDeAvaliacao "
                           + "ORDER BY c.classificacao DESC", RhClassificacaoDoCriterio.class)
                .setParameter("idCriterioDeAvaliacao", idCriterioDeAvaliacao);

        List<RhClassificacaoDoCriterio> resultado = t.getResultList();

        return resultado;
    }

}
