/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmEstado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmEstadoFacade extends AbstractFacade<FarmEstado>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmEstadoFacade()
   {
      super(FarmEstado.class);
   }
   
   public boolean isEstadoTabelaEmpty()
   {
      List<FarmEstado> listEstados = this.findAll();
      return (listEstados == null || listEstados.isEmpty());
   }

   public boolean existeRegisto(int pkIdEstado)
   {
      FarmEstado reg = this.find(pkIdEstado);
      return reg != null;
   }
}
