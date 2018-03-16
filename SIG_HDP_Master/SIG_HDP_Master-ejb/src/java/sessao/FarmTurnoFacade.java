/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmTurno;
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
public class FarmTurnoFacade extends AbstractFacade<FarmTurno>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmTurnoFacade()
   {
      super(FarmTurno.class);
   }
   
   public List<FarmTurno> findTurno (FarmTurno item)
   {      
      String query = constroiQuery(item);

      TypedQuery<FarmTurno> t = em.createQuery(query, FarmTurno.class);
            
      if(item.getPkIdTurno() != null )
         t.setParameter("id", item.getPkIdTurno());
      
      if (item.getDescricao() != null || !item.getDescricao().isEmpty())
         t.setParameter("descricao", item.getDescricao());
         
      List<FarmTurno> resultado = t.getResultList();

      return resultado;
   }
   
   public String constroiQuery (FarmTurno item)
   {
      String query = "SELECT f FROM FarmTurno f WHERE f.pkIdTurno = f.pkIdTurno";

      if (item.getPkIdTurno()!= null)
           query += " AND f.pkIdTurno = :id";

      if (item.getDescricao() != null || !item.getDescricao().isEmpty())
           query += " AND f.descricao = :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }
    
    public boolean isTurnoTabelaEmpty()
   {
      List<FarmTurno> listTurnos = this.findAll();
      return (listTurnos == null || listTurnos.isEmpty());
   }

   public boolean existeRegisto(int pkIdTurno)
   {
      System.out.println("Verificando de existe FarmTurno com id "+pkIdTurno);
      FarmTurno reg = this.find(pkIdTurno);
      System.out.println("Encontrou turno "+reg);
      return reg != null;
   }
}
