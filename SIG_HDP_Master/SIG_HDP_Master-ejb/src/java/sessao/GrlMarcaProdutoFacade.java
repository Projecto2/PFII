/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlMarcaProduto;
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
public class GrlMarcaProdutoFacade extends AbstractFacade<GrlMarcaProduto>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public GrlMarcaProdutoFacade()
   {
      super(GrlMarcaProduto.class);
   }
   
   public List<GrlMarcaProduto> findMarcaProduto(GrlMarcaProduto item)
   {
      String query = constroiQuery(item);

      TypedQuery<GrlMarcaProduto> t = em.createQuery(query, GrlMarcaProduto.class);

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%" + item.getDescricao() + "%");

      List<GrlMarcaProduto> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(GrlMarcaProduto item)
   {
      String query = "SELECT f FROM GrlMarcaProduto f WHERE f.pkIdMarca = f.pkIdMarca";

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }
   
   public boolean isMarcaProdutoTabelaEmpty()
   {
      List<GrlMarcaProduto> listMarcas = this.findAll();
      return (listMarcas == null || listMarcas.isEmpty());
   }

   public boolean existeRegisto(int pkIdMarcaProduto)
   {
      GrlMarcaProduto reg = this.find(pkIdMarcaProduto);
      return reg != null;
   }
}


