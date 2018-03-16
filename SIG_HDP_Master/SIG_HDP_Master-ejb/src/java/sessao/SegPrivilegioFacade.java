/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SegPrivilegio;
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
public class SegPrivilegioFacade extends AbstractFacade<SegPrivilegio>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public SegPrivilegioFacade()
   {
      super(SegPrivilegio.class);
   }
 
   public List<SegPrivilegio> findPrivilegio(SegPrivilegio item)
   {
      String query = constroiQuery(item);

      TypedQuery<SegPrivilegio> t = em.createQuery(query, SegPrivilegio.class);

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%" + item.getDescricao() + "%");

      List<SegPrivilegio> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(SegPrivilegio item)
   {
      String query = "SELECT f FROM SegPrivilegio f WHERE f.pkIdPrivilegio = f.pkIdPrivilegio";

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }
}
