/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhCriterioDeAvaliacao;
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
public class RhCriterioDeAvaliacaoFacade extends AbstractFacade<RhCriterioDeAvaliacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhCriterioDeAvaliacaoFacade ()
    {
        super(RhCriterioDeAvaliacao.class);
    }
    
    /**
     * Pesquisa um critério de avaliação com uma determinada descrição
     * 
     * @param descricao
     * @return
     */
    public List<RhCriterioDeAvaliacao> pesquisaPorDescricao(String descricao)
    {
        TypedQuery<RhCriterioDeAvaliacao> t = this.em.createQuery("SELECT ca FROM RhCriterioDeAvaliacao ca "
                + "WHERE ca.descricao = :descricao", RhCriterioDeAvaliacao.class).setParameter("descricao", descricao);

        List<RhCriterioDeAvaliacao> resultado = t.getResultList();

        return resultado;
    }
    
    /**
     * Retorna uma lista de todos os critérios de avaliação em ordem afabética
     * 
     * @return
     */
    public List<RhCriterioDeAvaliacao> findAllOrdemAlfabetica ()
    {
        TypedQuery<RhCriterioDeAvaliacao> t;
        t = this.em.createQuery("SELECT c FROM RhCriterioDeAvaliacao c ORDER BY c.descricao", RhCriterioDeAvaliacao.class);

        return t.getResultList();
    }
}
