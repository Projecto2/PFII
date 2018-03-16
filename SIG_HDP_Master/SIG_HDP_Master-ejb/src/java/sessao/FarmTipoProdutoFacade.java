/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmTipoProduto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmTipoProdutoFacade extends AbstractFacade<FarmTipoProduto>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmTipoProdutoFacade()
   {
      super(FarmTipoProduto.class);
   }
   
   public boolean isTipoProdutoTabelaEmpty()
   {
      List<FarmTipoProduto> listTiposProduto = this.findAll();
      return (listTiposProduto == null || listTiposProduto.isEmpty());
   }

   public boolean existeRegisto(int pkIdTipoProduto)
   {
      FarmTipoProduto reg = this.find(pkIdTipoProduto);
      return reg != null;
   }
}
