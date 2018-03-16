/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmPedido;
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
public class FarmPedidoFacade extends AbstractFacade<FarmPedido>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmPedidoFacade()
   {
      super(FarmPedido.class);
   }
   
   public List<FarmPedido> findPedido (FarmPedido item, Date dataInicio, Date dataFim)
   {
      String query = constroiQuery(item, dataInicio, dataFim);
      
      TypedQuery<FarmPedido> t = em.createQuery(query, FarmPedido.class);
      
      if(item.getFkIdEstadoPedido().getPkIdEstadoPedido() != null)
         t.setParameter("estado", item.getFkIdEstadoPedido());
      
      if(item.getFkLocalOrigemPedido().getPkIdLocalArmazenamento() != null)
         t.setParameter("origem", item.getFkLocalOrigemPedido());
      
      if(item.getFkLocalDestinoPedido().getPkIdLocalArmazenamento() != null)
         t.setParameter("destino", item.getFkLocalDestinoPedido());
      
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
      
      List<FarmPedido> resultado = t.getResultList();

      return resultado;
   }
      
   public String constroiQuery (FarmPedido item, Date dataInicio, Date dataFim)
   {
      String query = "Select f FROM FarmPedido f WHERE f.pkIdPedido = f.pkIdPedido";

      if(item.getFkIdEstadoPedido().getPkIdEstadoPedido() != null)
         query += " AND f.fkIdEstadoPedido = :estado";
      
      if(item.getFkLocalOrigemPedido().getPkIdLocalArmazenamento() != null)
         query += " AND f.fkLocalOrigemPedido = :origem";
      
      if(item.getFkLocalDestinoPedido().getPkIdLocalArmazenamento() != null)
         query += " AND f.fkLocalDestinoPedido = :destino";
      
      if(dataInicio != null && dataFim != null)
      {
         query += " AND f.dataHoraPedido between :dataInicio and :dataFim";
      }
      
      if(dataInicio != null && dataFim == null)
      {
         query += " AND f.dataHoraPedido >= :dataInicio";
      }
      
      if(dataInicio == null && dataFim != null)
      {
         query += " AND f.dataHoraPedido <= :dataFim";
      }
      
      query += " ORDER BY f.dataHoraPedido DESC";

      return query;
   }
}
