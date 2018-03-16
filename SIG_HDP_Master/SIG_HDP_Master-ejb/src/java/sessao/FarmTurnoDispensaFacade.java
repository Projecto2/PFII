/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

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
public class FarmTurnoDispensaFacade extends AbstractFacade<FarmTurnoDispensa>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmTurnoDispensaFacade()
   {
      super(FarmTurnoDispensa.class);
   }
   
   public FarmTurnoDispensa findUltimoTurnoAberto(FarmTurnoDispensa item)
   {
      String query = constroiQuery(item);

      TypedQuery<FarmTurnoDispensa> t = em.createQuery(query, FarmTurnoDispensa.class);
                  
      List<FarmTurnoDispensa> resultado = t.getResultList();
      
      if(resultado.isEmpty())
         return null;
      
      return resultado.get(0);
   }
   
   public List<FarmTurnoDispensa> findTurnosFechadosOrderByIdDesc()
   {

      TypedQuery<FarmTurnoDispensa> t = em.createQuery("SELECT f FROM FarmTurnoDispensa f ORDER BY f.pkIdTurnoDispensa DESC", FarmTurnoDispensa.class);
                  
      List<FarmTurnoDispensa> resultado = t.getResultList();
      
      return resultado;
   }
   
   public String constroiQuery(FarmTurnoDispensa item)
   {
      String query = "Select f FROM FarmTurnoDispensa f WHERE f.pkIdTurnoDispensa = f.pkIdTurnoDispensa";
     
      query += " ORDER BY f.pkIdTurnoDispensa DESC";

      return query;
   }
}
