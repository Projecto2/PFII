/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmMovimento;
import java.util.Date;
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
public class FarmMovimentoFacade extends AbstractFacade<FarmMovimento>
{
   private static final int CANCELADO = 2;
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmMovimentoFacade()
   {
      super(FarmMovimento.class);
   }
   
      public List<FarmMovimento> findMovimento (FarmMovimento item, Date dataInicio, Date dataFim)
   {
      String query = constroiQuery(item, dataInicio, dataFim);

      System.out.println("metodo findMovimento: ");
      System.out.println("metodo origem: "+item.getFkIdPedido().getFkLocalOrigemPedido().getPkIdLocalArmazenamento());
      System.out.println("metodo destino: "+item.getFkIdPedido().getFkLocalDestinoPedido().getPkIdLocalArmazenamento());
      System.out.println("metodo data inicio: "+dataInicio);
      System.out.println("metodo data inicio: "+dataFim);
      System.out.println("metodo fkIdPedido: "+item.getFkIdPedido());
      
      TypedQuery<FarmMovimento> t = em.createQuery(query, FarmMovimento.class);
      
      if(item.getFkIdPedido().getFkLocalOrigemPedido().getPkIdLocalArmazenamento() != null)
         t.setParameter("origem", item.getFkIdPedido().getFkLocalOrigemPedido());
      
      if(item.getFkIdPedido().getFkLocalDestinoPedido().getPkIdLocalArmazenamento() != null)
         t.setParameter("destino", item.getFkIdPedido().getFkLocalDestinoPedido());
      
      if(item.getFkIdPedido().getPkIdPedido()!= null)
         t.setParameter("pedido", item.getFkIdPedido());
      
      if(dataInicio != null && dataFim != null)
      {
         t.setParameter("dataInicio", dataInicio);
         t.setParameter("dataFim", dataFim);
      }
      
      if(dataInicio != null && dataFim == null)
      {
         t.setParameter("dataInicio", dataInicio);
      }
      
      if(dataInicio == null && dataFim != null)
      {
         t.setParameter("dataFim", dataFim);
      }
      List<FarmMovimento> resultado = t.getResultList();

      return resultado;
   }
      
   public String constroiQuery (FarmMovimento item, Date dataInicio, Date dataFim)
   {
      String query = "Select f FROM FarmMovimento f WHERE f.pkIdMovimento = f.pkIdMovimento";
            
      if(item.getFkIdPedido().getFkLocalOrigemPedido().getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdPedido.fkLocalOrigemPedido = :origem";
      
      if(item.getFkIdPedido().getFkLocalDestinoPedido().getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdPedido.fkLocalDestinoPedido = :destino";
      
      if(item.getFkIdPedido().getPkIdPedido() != null)
         query += " AND f.fkIdPedido = :pedido";
      
      if(dataInicio != null && dataFim != null)
      {
         query += " AND f.dataMovimento >= :dataInicio and f.dataMovimento <= :dataFim";
      }
      
      if(dataInicio != null && dataFim == null)
      {
         query += " AND f.dataMovimento >= :dataInicio";
      }
      
      if(dataInicio == null && dataFim != null)
      {
         query += " AND f.dataMovimento <= :dataFim";
      }
      
      query += " AND f.fkIdPedido.fkIdEstadoPedido.pkIdEstadoPedido <> " + CANCELADO + " ORDER BY f.dataMovimento DESC";

      return query;
   }
   
}
