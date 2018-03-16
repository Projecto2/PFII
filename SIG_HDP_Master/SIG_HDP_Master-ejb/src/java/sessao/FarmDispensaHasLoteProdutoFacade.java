/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmDispensa;
import entidade.FarmDispensaHasLoteProduto;
import entidade.FarmTurnoDispensa;
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
public class FarmDispensaHasLoteProdutoFacade extends AbstractFacade<FarmDispensaHasLoteProduto>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmDispensaHasLoteProdutoFacade()
   {
      super(FarmDispensaHasLoteProduto.class);
   }
   
   public List<FarmDispensaHasLoteProduto> findItensDispensadosNoTurno(FarmTurnoDispensa item)
   {
      String query = constroiQueryItensDispensadosNoTurno(item);
      TypedQuery<FarmDispensaHasLoteProduto> t = em.createQuery(query, FarmDispensaHasLoteProduto.class);
            
      if(item.getPkIdTurnoDispensa()!= null)
         t.setParameter("turno", item);
      
      List<FarmDispensaHasLoteProduto> resultado = t.getResultList();
      
      return resultado;
   }
   
   public List<FarmDispensaHasLoteProduto> findItensDispensa(FarmDispensa item)
   {
      String query = constroiQuery(item);

      TypedQuery<FarmDispensaHasLoteProduto> t = em.createQuery(query, FarmDispensaHasLoteProduto.class);
      
      if(item.getPkIdDispensa()!= null)
         t.setParameter("dispensa", item);
            
      List<FarmDispensaHasLoteProduto> resultado = t.getResultList();
      
      return resultado;
   }
   
   public String constroiQuery(FarmDispensa item)
   {
      String query = "Select f FROM FarmDispensaHasLoteProduto f WHERE f.pkIdDispensaHasLoteProduto = f.pkIdDispensaHasLoteProduto";
      
      if(item.getPkIdDispensa()!= null)
         query += " AND f.fkIdDispensa = :dispensa";
            
      return query;
   }
   
   public String constroiQueryGroupByLote(FarmTurnoDispensa item)
   {
      String query = "Select f.fkIdLoteProduto, SUM(f.quantidade) FROM FarmDispensaHasLoteProduto f WHERE f.pkIdDispensaHasLoteProduto = f.pkIdDispensaHasLoteProduto";
      
      if(item.getPkIdTurnoDispensa()!= null)
         query += " AND f.fkIdDispensa.fkIdTurnoDispensa = :turno";
            
      query += " GROUP BY f.fkIdLoteProduto";
      return query;
   }
   
   public String constroiQueryItensDispensadosNoTurno(FarmTurnoDispensa item)
   {
      String query = "SELECT f FROM FarmDispensaHasLoteProduto f WHERE f.pkIdDispensaHasLoteProduto = f.pkIdDispensaHasLoteProduto";
            
      if(item.getPkIdTurnoDispensa() != null)
         query += " AND f.fkIdDispensa.fkIdTurnoDispensa = :turno";
      
      query += " ORDER BY f.fkIdDispensa.dataHora";
      return query;
   }
}
