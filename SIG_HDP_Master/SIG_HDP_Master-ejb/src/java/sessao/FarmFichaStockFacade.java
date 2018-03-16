/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmFichaStock;
import entidade.FarmLocalArmazenamento;
import entidade.FarmProduto;
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
public class FarmFichaStockFacade extends AbstractFacade<FarmFichaStock>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmFichaStockFacade()
   {
      super(FarmFichaStock.class);
   }
   
   public List<FarmFichaStock> findFichaStock(FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = constroiQuery(local, produto);

      TypedQuery<FarmFichaStock> t = em.createQuery(query, FarmFichaStock.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(produto.getPkIdProduto() != null)
         t.setParameter("produto", produto);
      
      List<FarmFichaStock> resultado = t.getResultList();

      return resultado;
   }
   
   public List<FarmFichaStock> findFichaStockComIntervaloDeData(FarmLocalArmazenamento local, FarmProduto produto, Date dataInicio, Date dataFim)
   {
      String query = constroiQueryComIntervaloDeData(local, produto, dataInicio, dataFim);

      TypedQuery<FarmFichaStock> t = em.createQuery(query, FarmFichaStock.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(produto.getPkIdProduto() != null)
         t.setParameter("produto", produto);
        
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
      
      List<FarmFichaStock> resultado = t.getResultList();

      return resultado;
   }
      
   public String constroiQuery (FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = "Select f FROM FarmFichaStock f WHERE f.pkIdFichaStock = f.pkIdFichaStock";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalArmazenamento = :local";
      
      if(produto.getPkIdProduto() != null)
         query += " AND f.fkIdLoteProduto.fkIdProduto = :produto";
            
      query += " ORDER BY f.dataMovimento ASC";

      return query;
   }
   
   public String constroiQueryComIntervaloDeData (FarmLocalArmazenamento local, FarmProduto produto, Date dataInicio, Date dataFim )
   {
      String query = "Select f FROM FarmFichaStock f WHERE f.pkIdFichaStock = f.pkIdFichaStock";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalArmazenamento = :local";
      
      if(produto.getPkIdProduto() != null)
         query += " AND f.fkIdLoteProduto.fkIdProduto = :produto";
             
      if(dataInicio != null && dataFim != null)
      {
         query += " AND f.dataMovimento >= :dataInicio and f.dataMovimento <= :dataFim";
      }
      
      if(dataInicio != null && dataFim == null)
      {
         query += " AND f.dataMovimento >= :dataInicio";
      }
      
      if(dataInicio == null && dataFim != null)
      {
         query += " AND f.dataMovimento <= :dataFim";
      }
      query += " ORDER BY f.dataMovimento ASC";

      return query;
   }
}
