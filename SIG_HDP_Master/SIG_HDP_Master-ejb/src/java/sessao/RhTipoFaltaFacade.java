/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhTipoFalta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhTipoFaltaFacade extends AbstractFacade<RhTipoFalta>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhTipoFaltaFacade ()
    {
        super(RhTipoFalta.class);
    }
    
    @Override
    public List<RhTipoFalta> findAll()
    {
          Query t = em.createQuery("SELECT t FROM RhTipoFalta t ORDER BY t.descricao", RhTipoFalta.class);

          return t.getResultList();
    }
    
}
