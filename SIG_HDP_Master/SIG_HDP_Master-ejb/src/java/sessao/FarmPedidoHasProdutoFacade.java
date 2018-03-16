/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmPedido;
import entidade.FarmPedidoHasProduto;
import entidade.FarmProduto;
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
public class FarmPedidoHasProdutoFacade extends AbstractFacade<FarmPedidoHasProduto>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmPedidoHasProdutoFacade()
   {
      super(FarmPedidoHasProduto.class);
   }
   
   public List<FarmPedidoHasProduto> findProdutoPedidoAux(FarmPedido pedido, FarmProduto produto)
   {
      String query = constroiQuery(pedido, produto);

      TypedQuery<FarmPedidoHasProduto> t = em.createQuery(query, FarmPedidoHasProduto.class);
      
      if(produto.getPkIdProduto() != null)
         t.setParameter("pedido", pedido);
      
      if(produto.getPkIdProduto() != null)
         t.setParameter("produto", produto);
      
      List<FarmPedidoHasProduto> resultado = t.getResultList();

      return resultado;
   }
   
   public List<FarmPedidoHasProduto> findProdutoPedido(FarmPedido item)
   {
      String query = constroiQuery(item);

      TypedQuery<FarmPedidoHasProduto> t = em.createQuery(query, FarmPedidoHasProduto.class);
      
      if(item.getPkIdPedido() != null)
         t.setParameter("pedido", item);
      
      List<FarmPedidoHasProduto> resultado = t.getResultList();

      return resultado;
   }
      
   public String constroiQuery (FarmPedido item)
   {
      String query = "Select f FROM FarmPedidoHasProduto f WHERE f.pkIdPedidoHasProduto = f.pkIdPedidoHasProduto";

      if((item.getPkIdPedido() != null))
         query += " AND f.fkIdPedido = :pedido";
      
      query += " ORDER BY f.fkIdProduto.descricao";

      return query;
   }
   
   /**
    *
    * @param pedido
    * @param produto
    * @return
    */
   public String constroiQuery (FarmPedido pedido, FarmProduto produto)
   {
      String query = "Select f FROM FarmPedidoHasProduto f WHERE f.pkIdPedidoHasProduto = f.pkIdPedidoHasProduto";

      if((pedido.getPkIdPedido() != null))
         query += " AND f.fkIdPedido = :pedido";
      
      if((produto.getPkIdProduto() != null))
         query += " AND f.fkIdProduto = :produto";
            
      query += " ORDER BY f.fkIdProduto.descricao";

      return query;
   }
}
