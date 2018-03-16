/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.FarmFarmaco;
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
public class FarmFarmacoFacade extends AbstractFacade<FarmFarmaco>
{

   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmFarmacoFacade()
   {
      super(FarmFarmaco.class);
   }

   public List<FarmFarmaco> findFarmaco(FarmFarmaco farmaco)
   {
      String query = constroiQuery(farmaco);

      TypedQuery<FarmFarmaco> t = em.createQuery(query, FarmFarmaco.class);

      if (farmaco.getDescricao() != null && !(farmaco.getDescricao().isEmpty()))
         t.setParameter("descricao", farmaco.getDescricao() + "%");

      List<FarmFarmaco> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(FarmFarmaco farmaco)
   {
      String query = "SELECT f FROM FarmFarmaco f WHERE f.pkIdFarmaco = f.pkIdFarmaco";

      if (farmaco.getDescricao() != null && !(farmaco.getDescricao().isEmpty()))
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }

   public boolean isFarmacoTabelaEmpty()
   {
      List<FarmFarmaco> listFarmacos = this.findAll();
      return (listFarmacos == null || listFarmacos.isEmpty());
   }

   public boolean existeRegisto(int pkIdFarmaco)
   {
      FarmFarmaco reg = this.find(pkIdFarmaco);
      return reg != null;
   }
}
