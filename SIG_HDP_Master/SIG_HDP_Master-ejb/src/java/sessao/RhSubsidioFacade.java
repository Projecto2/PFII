/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhSubsidio;
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
public class RhSubsidioFacade extends AbstractFacade<RhSubsidio>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhSubsidioFacade ()
    {
        super(RhSubsidio.class);
    }
    
    @Override
    public List<RhSubsidio> findAll()
    {
          Query t = em.createQuery("SELECT s FROM RhSubsidio s ORDER BY s.descricaoSubsidio", RhSubsidio.class);

          return t.getResultList();
    }
}
