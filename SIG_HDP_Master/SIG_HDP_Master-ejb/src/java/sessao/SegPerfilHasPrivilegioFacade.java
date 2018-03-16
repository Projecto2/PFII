/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SegPerfilHasPrivilegio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author adalberto
 */
@Stateless
public class SegPerfilHasPrivilegioFacade extends AbstractFacade<SegPerfilHasPrivilegio>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public SegPerfilHasPrivilegioFacade()
   {
      super(SegPerfilHasPrivilegio.class);
   }
   
   
}
