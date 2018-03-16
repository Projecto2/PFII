/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmDispensa;
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
public class FarmDispensaFacade extends AbstractFacade<FarmDispensa>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmDispensaFacade()
   {
      super(FarmDispensa.class);
   }
   
   public List<FarmDispensa> findDispensa(FarmDispensa item, Date dataInicio, Date dataFim)
   {
      String query = constroiQuery(item, dataInicio, dataFim);

      TypedQuery<FarmDispensa> t = em.createQuery(query, FarmDispensa.class);
      
      if(item.getFkIdTurnoDispensa().getPkIdTurnoDispensa() != null)
         t.setParameter("turnoDispensa", item.getFkIdTurnoDispensa());
            
      if(item.getFkIdTurnoDispensa().getFkIdTurno().getPkIdTurno() != null)
         t.setParameter("turno", item.getFkIdTurnoDispensa().getFkIdTurno());
      
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
      
      List<FarmDispensa> resultado = t.getResultList();
      
      return resultado;
   }
   
   public String constroiQuery(FarmDispensa item, Date dataInicio, Date dataFim)
   {
      String query = "Select f FROM FarmDispensa f WHERE f.pkIdDispensa = f.pkIdDispensa";
      
      if(item.getFkIdTurnoDispensa().getPkIdTurnoDispensa() != null)
         query += " AND f.fkIdTurnoDispensa = :turnoDispensa";
      
      if(item.getFkIdTurnoDispensa().getFkIdTurno().getPkIdTurno() != null)
         query += " AND f.fkIdTurnoDispensa.fkIdTurno = :turno";
            
      if(dataInicio != null && dataFim != null)
         query += " AND f.dataHora between :dataInicio and :dataFim";
      
      if(dataInicio != null && dataFim == null)
         query += " AND f.dataHora >= :dataInicio";
            
      if(dataInicio == null && dataFim != null)
         query += " AND f.dataHora <= :dataFim";

      query += " ORDER BY f.dataHora DESC";

      return query;
   }
}
