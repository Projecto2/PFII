/*
 * To change this license header, choose License Headers in Project Propertie f.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhFuncao;
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
public class RhFuncaoFacade extends AbstractFacade<RhFuncao>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhFuncaoFacade ()
    {
        super(RhFuncao.class);
    }

    @Override
    public List<RhFuncao> findAll()
    {
          Query t = em.createQuery("SELECT f FROM RhFuncao f ORDER BY f.descricao", RhFuncao.class);

          return t.getResultList();
    }
}
