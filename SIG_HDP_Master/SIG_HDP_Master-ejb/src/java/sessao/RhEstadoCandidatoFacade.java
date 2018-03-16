/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhEstadoCandidato;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhEstadoCandidatoFacade extends AbstractFacade<RhEstadoCandidato>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhEstadoCandidatoFacade ()
    {
        super(RhEstadoCandidato.class);
    }
    
    @Override
    public List<RhEstadoCandidato> findAll()
    {
          Query t = em.createQuery("SELECT e FROM RhEstadoCandidato e ORDER BY e.descricao", RhEstadoCandidato.class);

          return t.getResultList();
    }
    
    /**
     * Pesquisa um estado de candidato com uma determinada descrição
     * 
     * @param descricao
     * @return
     */
    public List<RhEstadoCandidato> pesquisaPorDescricao(String descricao)
    {
        TypedQuery<RhEstadoCandidato> t = this.em.createQuery("SELECT ec FROM RhEstadoCandidato ec "
                + "WHERE ec.descricao = :descricao", RhEstadoCandidato.class).setParameter("descricao", descricao);

        List<RhEstadoCandidato> resultado = t.getResultList();

        return resultado;
    }
}
