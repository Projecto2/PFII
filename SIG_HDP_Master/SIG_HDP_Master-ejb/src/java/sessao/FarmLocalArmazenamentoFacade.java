/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmLocalArmazenamento;
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
public class FarmLocalArmazenamentoFacade extends AbstractFacade<FarmLocalArmazenamento>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmLocalArmazenamentoFacade()
   {
      super(FarmLocalArmazenamento.class);
   }
   
   public List<FarmLocalArmazenamento> findLocalArmazenamento (FarmLocalArmazenamento item)
   {      
      String query = constroiQuery(item);

      TypedQuery<FarmLocalArmazenamento> t = em.createQuery(query, FarmLocalArmazenamento.class);
          
      if(item.getFkIdTipoLocalArmazenamento().getPkIdTipoLocalArmazenamento() != null)
         t.setParameter("tipoLocal", item.getFkIdTipoLocalArmazenamento());
     
      if(item.getDescricao() != null  && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%"+item.getDescricao()+"%");
         
      List<FarmLocalArmazenamento> resultado = t.getResultList();

      return resultado;
   }
   
   
   public String constroiQuery (FarmLocalArmazenamento item)
   {
      String query = "SELECT f FROM FarmLocalArmazenamento f WHERE f.pkIdLocalArmazenamento = f.pkIdLocalArmazenamento";
      if(item.getFkIdTipoLocalArmazenamento().getPkIdTipoLocalArmazenamento() != null)
         query += " AND f.fkIdTipoLocalArmazenamento = :tipoLocal";

      if(item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";
      
      query += " ORDER BY f.descricao";

      return query;
   }
   
   public boolean isLocalArmazenamentoTabelaEmpty()
   {
      List<FarmLocalArmazenamento> listLocalArmazenamento = this.findAll();
      return (listLocalArmazenamento== null || listLocalArmazenamento.isEmpty());
   }

   public boolean existeRegisto(int pkIdLocalArmazenamento)
   {
      FarmLocalArmazenamento reg = this.find(pkIdLocalArmazenamento);
      return reg != null;
   }
}
