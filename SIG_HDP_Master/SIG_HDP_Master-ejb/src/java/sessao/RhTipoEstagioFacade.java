/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhTipoEstagio;
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
public class RhTipoEstagioFacade extends AbstractFacade<RhTipoEstagio>
{
     @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager ()
     {
          return em;
     }

     public RhTipoEstagioFacade ()
     {
          super(RhTipoEstagio.class);
     }
     
    @Override
    public List<RhTipoEstagio> findAll()
    {
          Query t = em.createQuery("SELECT t FROM RhTipoEstagio t ORDER BY t.descricao", RhTipoEstagio.class);

          return t.getResultList();
    }
    
}
