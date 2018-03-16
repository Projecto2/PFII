/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SegPerfil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author adalberto
 */
@Stateless
public class SegPerfilFacade extends AbstractFacade<SegPerfil>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public SegPerfilFacade()
   {
      super(SegPerfil.class);
   }
   
   public List<SegPerfil> findPerfil(SegPerfil item)
   {
      String query = constroiQuery(item);

      TypedQuery<SegPerfil> t = em.createQuery(query, SegPerfil.class);

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%" + item.getDescricao() + "%");

      List<SegPerfil> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(SegPerfil item)
   {
      String query = "SELECT f FROM SegPerfil f WHERE f.pkIdPerfil = f.pkIdPerfil";

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }
}
