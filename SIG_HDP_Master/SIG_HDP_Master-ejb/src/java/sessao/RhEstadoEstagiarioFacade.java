/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhEstadoEstagiario;
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
public class RhEstadoEstagiarioFacade extends AbstractFacade<RhEstadoEstagiario>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager ()
   {
      return em;
   }

   public RhEstadoEstagiarioFacade ()
   {
      super(RhEstadoEstagiario.class);
   }
   
    @Override
    public List<RhEstadoEstagiario> findAll()
    {
          Query t = em.createQuery("SELECT e FROM RhEstadoEstagiario e ORDER BY e.descricao", RhEstadoEstagiario.class);

          return t.getResultList();
    }
    
    /**
     * Pesquisa um estado de estagiário com uma determinada descrição
     * 
     * @param descricao
     * @return
     */
    public List<RhEstadoEstagiario> pesquisaPorDescricao(String descricao)
    {
        TypedQuery<RhEstadoEstagiario> t = this.em.createQuery("SELECT ee FROM RhEstadoEstagiario ee "
                + "WHERE ee.descricao = :descricao", RhEstadoEstagiario.class).setParameter("descricao", descricao);

        List<RhEstadoEstagiario> resultado = t.getResultList();

        return resultado;
    }
    
}
