/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmFornecimento;
import java.util.Date;
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
public class FarmFornecimentoFacade extends AbstractFacade<FarmFornecimento>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmFornecimentoFacade()
   {
      super(FarmFornecimento.class);
   }
   
   public List<FarmFornecimento> findFornecimento (FarmFornecimento item, Date dataInicio, Date dataFim)
   {      
      String query = constroiQuery(item, dataInicio, dataFim);

      TypedQuery<FarmFornecimento> t = em.createQuery(query, FarmFornecimento.class);
                  
      if(item.getFkIdTipoFornecimento().getPkIdTipoFornecimento() != null)
         t.setParameter("tipoFornecimento", item.getFkIdTipoFornecimento());
     
      if(item.getFkIdFornecedor().getPkIdFornecedor() != null)
         t.setParameter("fornecedor", item.getFkIdFornecedor());
         
      if(dataInicio != null && dataFim != null)
      {
         t.setParameter("dataInicio", dataInicio);
         t.setParameter("dataFim", dataFim);
      }
      
      if(dataInicio != null && dataFim == null)
      {
         t.setParameter("dataInicio", dataInicio);
      }
      
      if(dataInicio == null && dataFim != null)
      {
         t.setParameter("dataFim", dataFim);
      }
      
      List<FarmFornecimento> resultado = t.getResultList();

      return resultado;
   }
   
   
   public String constroiQuery (FarmFornecimento item, Date dataInicio, Date dataFim)
   {
      String query = "SELECT f FROM FarmFornecimento f WHERE f.pkIdFornecimento = f.pkIdFornecimento";
      
      if(item.getFkIdTipoFornecimento().getPkIdTipoFornecimento() != null)
         query += " AND f.fkIdTipoFornecimento = :tipoFornecimento";
     
      if(item.getFkIdFornecedor().getPkIdFornecedor() != null)
         query += " AND f.fkIdFornecedor = :fornecedor";
      
      if(dataInicio != null && dataFim != null)
      {
         query += " AND f.dataFornecimento >= :dataInicio and f.dataFornecimento <= :dataFim";
      }
      
      if(dataInicio != null && dataFim == null)
      {
         query += " AND f.dataFornecimento >= :dataInicio";
      }
      
      if(dataInicio == null && dataFim != null)
      {
         query += " AND f.dataFornecimento <= :dataFim";
      }
      
      query += " ORDER BY f.dataFornecimento DESC";

      return query;
   }
}
