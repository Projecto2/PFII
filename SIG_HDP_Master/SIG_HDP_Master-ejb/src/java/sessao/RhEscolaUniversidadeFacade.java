/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhEscolaUniversidade;
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
public class RhEscolaUniversidadeFacade extends AbstractFacade<RhEscolaUniversidade>
{
     @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager ()
     {
          return em;
     }

     public RhEscolaUniversidadeFacade ()
     {
          super(RhEscolaUniversidade.class);
     }
     
    @Override
    public List<RhEscolaUniversidade> findAll()
    {
          Query t = em.createQuery("SELECT e FROM RhEscolaUniversidade e ORDER BY e.descricao", RhEscolaUniversidade.class);

          return t.getResultList();
    }
    
}
