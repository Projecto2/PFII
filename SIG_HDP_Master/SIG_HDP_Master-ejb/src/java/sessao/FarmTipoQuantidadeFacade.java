/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmTipoQuantidade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmTipoQuantidadeFacade extends AbstractFacade<FarmTipoQuantidade>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmTipoQuantidadeFacade()
   {
      super(FarmTipoQuantidade.class);
   }
   
   public boolean isTipoQuantidadeTabelaEmpty()
   {
      List<FarmTipoQuantidade> listTiposQuantidade = this.findAll();
      return (listTiposQuantidade == null || listTiposQuantidade.isEmpty());
   }

   public boolean existeRegisto(int pkIdTipoQuantidade)
   {
      FarmTipoQuantidade reg = this.find(pkIdTipoQuantidade);
      return reg != null;
   }
}
