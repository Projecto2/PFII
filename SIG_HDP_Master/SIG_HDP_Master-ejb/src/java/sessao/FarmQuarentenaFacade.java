/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmQuarentena;
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
public class FarmQuarentenaFacade extends AbstractFacade<FarmQuarentena>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmQuarentenaFacade()
   {
      super(FarmQuarentena.class);
   }

   public List<FarmQuarentena> findQuarentena (FarmQuarentena item, Date dataInicio, Date dataFim)
   {      
      String query = constroiQuery(item, dataInicio, dataFim);

      TypedQuery<FarmQuarentena> t = em.createQuery(query, FarmQuarentena.class);
                  
      if(item.getFkIdLocalOrigem().getPkIdLocalArmazenamento()!= null)
            t.setParameter("localOrigem", item.getFkIdLocalOrigem());
     
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
      
      List<FarmQuarentena> resultado = t.getResultList();

      return resultado;
   }
   
   
   public String constroiQuery (FarmQuarentena item, Date dataInicio, Date dataFim)
   {
      String query = "SELECT f FROM FarmQuarentena f WHERE f.pkIdQuarentena = f.pkIdQuarentena";
      
      if(item.getFkIdLocalOrigem().getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalOrigem = :localOrigem";
     
      if(dataInicio != null && dataFim != null)
      {
         query += " AND f.dataCadastro >= :dataInicio and f.dataCadastro <= :dataFim";
      }
      
      if(dataInicio != null && dataFim == null)
      {
         query += " AND f.dataCadastro >= :dataInicio";
      }
      
      if(dataInicio == null && dataFim != null)
      {
         query += " AND f.dataCadastro <= :dataFim";
      }
      
      query += " ORDER BY f.dataCadastro DESC";

      return query;
   }
}
