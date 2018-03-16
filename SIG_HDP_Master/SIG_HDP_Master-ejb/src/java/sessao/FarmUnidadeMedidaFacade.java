/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.FarmUnidadeMedida;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmUnidadeMedidaFacade extends AbstractFacade<FarmUnidadeMedida>
{

   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmUnidadeMedidaFacade()
   {
      super(FarmUnidadeMedida.class);
   }

   public List<FarmUnidadeMedida> findUnidadeMedida(FarmUnidadeMedida item)
   {
      String query = constroiQuery(item);

      TypedQuery<FarmUnidadeMedida> t = em.createQuery(query, FarmUnidadeMedida.class);
      
      if (item.getAbreviatura()!= null && !(item.getAbreviatura().isEmpty()))
         t.setParameter("abreviatura", item.getAbreviatura() + "%");

      if (item.getDescricao() != null && !(item.getDescricao().isEmpty()))
         t.setParameter("descricao", item.getDescricao() + "%");

      if (item.getFkIdTipoUnidadeMedida().getPkIdTipoUnidadeMedida() != null)
         t.setParameter("tipo", item.getFkIdTipoUnidadeMedida());
      
      List<FarmUnidadeMedida> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(FarmUnidadeMedida item)
   {
      String query = "SELECT f FROM FarmUnidadeMedida f WHERE f.pkIdUnidadeMedida = f.pkIdUnidadeMedida";

      if (item.getDescricao() != null && !(item.getDescricao().isEmpty()))
         query += " AND f.descricao LIKE :descricao";

      if (item.getAbreviatura() != null && !(item.getAbreviatura().isEmpty()))
         query += " AND f.abreviatura LIKE :abreviatura";

      if (item.getFkIdTipoUnidadeMedida().getPkIdTipoUnidadeMedida() != null)
         query += " AND f.fkIdTipoUnidadeMedida =:tipo";

      query += " ORDER BY f.descricao";

      return query;
   }
   
   public boolean isUnidadeMedidaTabelaEmpty()
   {
      List<FarmUnidadeMedida> listUnidadesMedida = this.findAll();
      return (listUnidadesMedida == null || listUnidadesMedida.isEmpty());
   }

   public boolean existeRegisto(int pkIdUnidadeMedida)
   {
      FarmUnidadeMedida reg = this.find(pkIdUnidadeMedida);
      return reg != null;
   }
   
   public List<FarmUnidadeMedida> lerUnidadesInternamento()
   {
        Query qrs = em.createQuery("SELECT o FROM FarmUnidadeMedida o WHERE o.abreviatura = :unidade1 OR o.abreviatura = :unidade2 OR o.abreviatura = :unidade3 OR o.abreviatura = :unidade4");
         qrs.setParameter("unidade1", "cl").setParameter("unidade2", "ml").setParameter("unidade3", "g").setParameter("unidade4", "mg");
        
         return qrs.getResultList();
   }
}