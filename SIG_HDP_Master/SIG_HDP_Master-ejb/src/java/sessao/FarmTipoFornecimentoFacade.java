/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmTipoFornecimento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmTipoFornecimentoFacade extends AbstractFacade<FarmTipoFornecimento>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmTipoFornecimentoFacade()
   {
      super(FarmTipoFornecimento.class);
   }
   
   public boolean isTipoFornecimentoTabelaEmpty()
   {
      List<FarmTipoFornecimento> listTiposFornecimento = this.findAll();
      return (listTiposFornecimento == null || listTiposFornecimento.isEmpty());
   }

   public boolean existeRegisto(int pkIdTipoFornecimento)
   {
      FarmTipoFornecimento reg = this.find(pkIdTipoFornecimento);
      return reg != null;
   }
   
}
