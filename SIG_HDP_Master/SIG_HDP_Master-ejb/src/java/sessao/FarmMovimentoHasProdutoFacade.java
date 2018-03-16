/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmMovimento;
import entidade.FarmMovimentoHasProduto;
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
public class FarmMovimentoHasProdutoFacade extends AbstractFacade<FarmMovimentoHasProduto>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmMovimentoHasProdutoFacade()
   {
      super(FarmMovimentoHasProduto.class);
   }
   
   public List<FarmMovimentoHasProduto> findProdutosMovimento(FarmMovimento item)
   {
      System.out.println("findProdutosMovimento");
      String query = constroiQuery(item);

      TypedQuery<FarmMovimentoHasProduto> t = em.createQuery(query, FarmMovimentoHasProduto.class);
      
      if(item.getPkIdMovimento() != null)
         t.setParameter("movimento", item);
      
      List<FarmMovimentoHasProduto> resultado = t.getResultList();

      System.out.println("query: "+query);
      System.out.println("resultado: "+resultado);
      return resultado;
   }
   
   public String constroiQuery(FarmMovimento item)
   {
      String query = "Select f FROM FarmMovimentoHasProduto f WHERE f.pkIdMovimentoHasLoteProduto = f.pkIdMovimentoHasLoteProduto";

      if((item.getPkIdMovimento()!= null))
         query += " AND f.fkIdMovimento = :movimento";
      
      query += " ORDER BY f.fkIdLoteProduto.fkIdProduto.descricao";

      return query;
   }
}
