/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmTipoUnidadeMedida;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmTipoUnidadeMedidaFacade extends AbstractFacade<FarmTipoUnidadeMedida>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmTipoUnidadeMedidaFacade()
   {
      super(FarmTipoUnidadeMedida.class);
   }
   
   public boolean isTipoUnidadeMedidaTabelaEmpty()
   {
      List<FarmTipoUnidadeMedida> listTipoUnidadeMedidas = this.findAll();
      return (listTipoUnidadeMedidas == null || listTipoUnidadeMedidas.isEmpty());
   }

   public boolean existeRegisto(int pkIdTipoUnidadeMedida)
   {
      FarmTipoUnidadeMedida reg = this.find(pkIdTipoUnidadeMedida);
      return reg != null;
   }
}
