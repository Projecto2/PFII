/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmProduto;
import entidade.FarmProdutoHasFarmaco;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmProdutoHasFarmacoFacade extends AbstractFacade<FarmProdutoHasFarmaco>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmProdutoHasFarmacoFacade()
   {
      super(FarmProdutoHasFarmaco.class);
   }
   
   public List<FarmProdutoHasFarmaco> findByIdProduto(FarmProduto item)
   {
      TypedQuery<FarmProdutoHasFarmaco> t = em.
              createQuery("SELECT f FROM FarmProdutoHasFarmaco f WHERE f.fkIdProduto.pkIdProduto = :idProduto ORDER BY f.fkIdFarmaco.descricao",
                      FarmProdutoHasFarmaco.class).setParameter("idProduto", item.getPkIdProduto());
      return t.getResultList();
   }
   
}
