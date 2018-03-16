/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.FarmEstadoPedido;
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
public class FarmEstadoPedidoFacade extends AbstractFacade<FarmEstadoPedido>
{

   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmEstadoPedidoFacade()
   {
      super(FarmEstadoPedido.class);
   }

   public List<FarmEstadoPedido> findEstadoPedido(FarmEstadoPedido estadoPedido)
   {
      String query = constroiQuery(estadoPedido);

      TypedQuery<FarmEstadoPedido> t = em.createQuery(query, FarmEstadoPedido.class);

      if (estadoPedido.getDescricao() != null && !(estadoPedido.getDescricao().isEmpty()))
         t.setParameter("descricao", estadoPedido.getDescricao() + "%");

      List<FarmEstadoPedido> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(FarmEstadoPedido estadoPedido)
   {
      String query = "SELECT f FROM FarmEstadoPedido f WHERE f.pkIdEstadoPedido = f.pkIdEstadoPedido";

      if (estadoPedido.getDescricao() != null && !(estadoPedido.getDescricao().isEmpty()))
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }

   public boolean isEstadoPedidoTabelaEmpty()
   {
      List<FarmEstadoPedido> listEstadosDePedido = this.findAll();
      return (listEstadosDePedido == null || listEstadosDePedido.isEmpty());
   }

   public boolean existeRegisto(int pkIdEstadoPedido)
   {
      FarmEstadoPedido reg = this.find(pkIdEstadoPedido);
      return reg != null;
   }

}
