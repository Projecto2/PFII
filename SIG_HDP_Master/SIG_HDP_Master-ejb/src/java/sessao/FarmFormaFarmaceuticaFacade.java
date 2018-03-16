/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmFormaFarmaceutica;
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
public class FarmFormaFarmaceuticaFacade extends AbstractFacade<FarmFormaFarmaceutica>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmFormaFarmaceuticaFacade()
   {
      super(FarmFormaFarmaceutica.class);
   }
   
   public List<FarmFormaFarmaceutica> findFormaFarmaceutica(FarmFormaFarmaceutica item)
   {
      String query = constroiQuery(item);

      TypedQuery<FarmFormaFarmaceutica> t = em.createQuery(query, FarmFormaFarmaceutica.class);

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%" + item.getDescricao() + "%");

      List<FarmFormaFarmaceutica> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(FarmFormaFarmaceutica item)
   {
      String query = "SELECT f FROM FarmFormaFarmaceutica f WHERE f.pkIdFormaFarmaceutica = f.pkIdFormaFarmaceutica";

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }
   
   public boolean isFormaFarmaceuticaTabelaEmpty()
   {
      List<FarmFormaFarmaceutica> listFormaFarmaceuticas = this.findAll();
      return (listFormaFarmaceuticas == null || listFormaFarmaceuticas.isEmpty());
   }

   public boolean existeRegisto(int pkIdFormaFarmaceutica)
   {
      FarmFormaFarmaceutica reg = this.find(pkIdFormaFarmaceutica);
      return reg != null;
   }
}
