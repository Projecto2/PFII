/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmProduto;
import entidade.FarmProdutoHasOutroComponente;
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
public class FarmProdutoHasOutroComponenteFacade extends AbstractFacade<FarmProdutoHasOutroComponente>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmProdutoHasOutroComponenteFacade()
   {
      super(FarmProdutoHasOutroComponente.class);
   }
      
   public List<FarmProdutoHasOutroComponente> findByIdProduto(FarmProduto item)
   {
      TypedQuery<FarmProdutoHasOutroComponente> t = em.
              createQuery("SELECT f FROM FarmProdutoHasOutroComponente f WHERE f.fkIdProduto.pkIdProduto = :idProduto ORDER BY f.fkIdOutroComponente.descricaoComponente",
                      FarmProdutoHasOutroComponente.class).setParameter("idProduto", item.getPkIdProduto());
      return t.getResultList();
   }
   
}
