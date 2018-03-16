/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmAviso;
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
public class FarmAvisoFacade extends AbstractFacade<FarmAviso>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmAvisoFacade()
   {
      super(FarmAviso.class);
   }
   
   
   public List<FarmAviso> findAviso(FarmAviso aviso)
   {
      String query = constroiQuery(aviso);

      TypedQuery<FarmAviso> t = em.createQuery(query, FarmAviso.class);

      if (aviso.getDescricao() != null && !(aviso.getDescricao().isEmpty()))
         t.setParameter("descricao", aviso.getDescricao() + "%");

      List<FarmAviso> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(FarmAviso aviso)
   {
      String query = "SELECT f FROM FarmAviso f WHERE f.pkIdAviso = f.pkIdAviso";

      if (aviso.getDescricao() != null && !(aviso.getDescricao().isEmpty()))
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }
   public boolean isAvisoTabelaEmpty()
   {
      List<FarmAviso> listAvisos = this.findAll();
      return (listAvisos == null || listAvisos.isEmpty());
   }

   public boolean existeRegisto(int pkIdAviso)
   {
      FarmAviso reg = this.find(pkIdAviso);
      return reg != null;
   }
}
