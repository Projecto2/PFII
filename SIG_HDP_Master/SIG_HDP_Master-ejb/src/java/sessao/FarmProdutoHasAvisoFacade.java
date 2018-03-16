/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmProduto;
import entidade.FarmProdutoHasAviso;
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
public class FarmProdutoHasAvisoFacade extends AbstractFacade<FarmProdutoHasAviso>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public List<FarmProdutoHasAviso> findByIdProduto(FarmProduto item)
   {
       System.out.println("List<FarmProdutoHasAviso> findByIdProduto(FarmProduto item) "+item);   
      TypedQuery<FarmProdutoHasAviso> t = em.
              createQuery("SELECT f FROM FarmProdutoHasAviso f WHERE f.fkIdProduto = :idProduto ORDER BY f.fkIdAviso.descricao",
                      FarmProdutoHasAviso.class).setParameter("idProduto", item);
      
      System.out.println("avisos..."+t.getResultList());
      return t.getResultList();
   }
   
   public FarmProdutoHasAvisoFacade()
   {
      super(FarmProdutoHasAviso.class);
   }
   
}
