/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SegHistoricoSessao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elisangela
 */
@Stateless
public class SegHistoricoSessaoFacade extends AbstractFacade<SegHistoricoSessao>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public SegHistoricoSessaoFacade()
   {
      super(SegHistoricoSessao.class);
   }
   
}
