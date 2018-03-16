/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.FarmOutroComponente;
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
public class FarmOutroComponenteFacade extends AbstractFacade<FarmOutroComponente>
{

   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmOutroComponenteFacade()
   {
      super(FarmOutroComponente.class);
   }

   public List<FarmOutroComponente> findOutroComponente(FarmOutroComponente efeitoSecundario)
   {
      String query = constroiQuery(efeitoSecundario);

      TypedQuery<FarmOutroComponente> t = em.createQuery(query, FarmOutroComponente.class);

      if (efeitoSecundario.getDescricaoComponente() != null && !(efeitoSecundario.getDescricaoComponente().isEmpty()))
         t.setParameter("descricaoComponente", efeitoSecundario.getDescricaoComponente() + "%");

      List<FarmOutroComponente> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(FarmOutroComponente efeitoSecundario)
   {
      String query = "SELECT f FROM FarmOutroComponente f WHERE f.pkIdComponente = f.pkIdComponente";

      if (efeitoSecundario.getDescricaoComponente() != null && !(efeitoSecundario.getDescricaoComponente().isEmpty()))
         query += " AND f.descricaoComponente LIKE :descricaoComponente";

      query += " ORDER BY f.descricaoComponente";

      return query;
   }
   
   public boolean isOutroComponenteTabelaEmpty()
   {
      List<FarmOutroComponente> listViasAdministracao = this.findAll();
      return (listViasAdministracao == null || listViasAdministracao.isEmpty());
   }

   public boolean existeRegisto(int pkIdOutroComponente)
   {
      FarmOutroComponente reg = this.find(pkIdOutroComponente);
      return reg != null;
   }
}
