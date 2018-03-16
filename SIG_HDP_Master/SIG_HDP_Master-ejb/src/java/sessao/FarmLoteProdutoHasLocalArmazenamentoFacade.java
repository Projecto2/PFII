/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmProduto;
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
public class FarmLoteProdutoHasLocalArmazenamentoFacade extends AbstractFacade<FarmLoteProdutoHasLocalArmazenamento>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmLoteProdutoHasLocalArmazenamentoFacade()
   {
      super(FarmLoteProdutoHasLocalArmazenamento.class);
   }
   
   public List<FarmLoteProduto> findLoteExistentesNoStock()
   {
      TypedQuery<FarmLoteProduto> t = em.createQuery("SELECT DISTINCT f.fkIdLoteProduto FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.quantidadeStock > 0 ORDER BY f.fkIdLoteProduto.dataValidade ASC ", FarmLoteProduto.class);

      List<FarmLoteProduto> resultado = t.getResultList();

      return resultado;
   }
     /*
   RECEBE: local e o produto
   RETORNA: todos os registos onde o produto e o local forem os mesmos
   */

   /**
    *
    * @param local
    * @param produto
    * @return
    */
   
   public List<FarmLoteProdutoHasLocalArmazenamento> findProdutosNoLocal(FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = constroiQueryFindProdutoNoLocal(local, produto);

      TypedQuery<FarmLoteProdutoHasLocalArmazenamento> t = em.createQuery(query, FarmLoteProdutoHasLocalArmazenamento.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(produto.getPkIdProduto() != null)
         t.setParameter("produto", produto);
      
      List<FarmLoteProdutoHasLocalArmazenamento> resultado = t.getResultList();
      
      return resultado;
   }
   
   /*
   RECEBE: local e descricao do produto
   RETORNA: todos os registos onde a descricao do produto possui os caracteres recebidos
   */
   public List<FarmLoteProdutoHasLocalArmazenamento> findProdutosNoLocal(FarmLocalArmazenamento local, String descricao)
   {
      String query = constroiQuery(local, descricao);

      TypedQuery<FarmLoteProdutoHasLocalArmazenamento> t = em.createQuery(query, FarmLoteProdutoHasLocalArmazenamento.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(descricao != null && !descricao.isEmpty())
         t.setParameter("descricao", "%" +descricao+ "%");
      
      List<FarmLoteProdutoHasLocalArmazenamento> resultado = t.getResultList();
      
      return resultado;
   }
      
   /*
   RECEBE: local e lote do produto
   RETORNA: o registo para este lote no local
   */   
   public FarmLoteProdutoHasLocalArmazenamento findLoteProdutoNoLocal(FarmLocalArmazenamento local, FarmLoteProduto lote)
   {
      String query = constroiQuery(local, lote);

      TypedQuery<FarmLoteProdutoHasLocalArmazenamento> t = em.createQuery(query, FarmLoteProdutoHasLocalArmazenamento.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(lote.getPkIdLoteProduto() != null)
         t.setParameter("lote", lote);
      
      List<FarmLoteProdutoHasLocalArmazenamento> resultado = t.getResultList();
      
      if( resultado.size() > 0)
         return resultado.get(0);
      
      return null;
   }
   
   
   /*
   RECEBE: local e lote do produto
   RETORNA: todos os registos para este lote no local com quantidade superior a ZERO
   */
   public List<FarmLoteProdutoHasLocalArmazenamento> findLoteProdutoNoLocalComQtdPositiva(FarmLocalArmazenamento local, FarmLoteProduto lote)
   {
      String query = constroiQueryComQtdPositiva(local, lote);

      TypedQuery<FarmLoteProdutoHasLocalArmazenamento> t = em.createQuery(query, FarmLoteProdutoHasLocalArmazenamento.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(lote.getPkIdLoteProduto() != null)
         t.setParameter("lote", lote);
      
      List<FarmLoteProdutoHasLocalArmazenamento> resultado = t.getResultList();
      return resultado;
   }
   
   /*
   RECEBE: local e lote do produto
   RETORNA: todos os registos para este lote no local com quantidade superior a indefinida
   */
   public List<FarmLoteProdutoHasLocalArmazenamento> findLoteProdutoNoLocalAux(FarmLocalArmazenamento local, FarmLoteProduto lote)
   {
      String query = constroiQuery(local, lote);

      TypedQuery<FarmLoteProdutoHasLocalArmazenamento> t = em.createQuery(query, FarmLoteProdutoHasLocalArmazenamento.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(lote.getPkIdLoteProduto() != null)
         t.setParameter("lote", lote);
      
      List<FarmLoteProdutoHasLocalArmazenamento> resultado = t.getResultList();
      return resultado;
   }
   
   /*
   RECEBE: local e produto
   RETORNA: apenas um registo com a referencia do produto com quantidade indefinida
   */
   public FarmLoteProdutoHasLocalArmazenamento findReferenciaProdutoNoLocal(FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = constroiQuery_findReferenciaProdutoNoLocal(local, produto);
      
      TypedQuery<FarmLoteProdutoHasLocalArmazenamento> t = em.createQuery(query, FarmLoteProdutoHasLocalArmazenamento.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(produto.getPkIdProduto() != null)
         t.setParameter("produto", produto);
      
      List<FarmLoteProdutoHasLocalArmazenamento> resultado = t.getResultList();

      if( resultado.size() > 0)
         return resultado.get(0);
      
      return null;
   }
   
   /*
   RECEBE: local e produto
   RETORNA: todos os registos com a referencia do produto com quantidade indefinida
   */
   public List<FarmLoteProdutoHasLocalArmazenamento> findProdutoNoLocal(FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = constroiQueryComQuantIndefinida(local, produto);

      TypedQuery<FarmLoteProdutoHasLocalArmazenamento> t = em.createQuery(query, FarmLoteProdutoHasLocalArmazenamento.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(produto.getPkIdProduto() != null)
         t.setParameter("produto", produto);
      
      List<FarmLoteProdutoHasLocalArmazenamento> resultado = t.getResultList();

      return resultado;
   }
   
   /*
   RECEBE: local e produto
   RETORNA: todos os registos com a referencia do produto com quantidade superior a ZERO
   */
   public List<FarmLoteProdutoHasLocalArmazenamento> findProdutosDisponiveisNoLocal(FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = constroiQuery(local, produto);

      TypedQuery<FarmLoteProdutoHasLocalArmazenamento> t = em.createQuery(query, FarmLoteProdutoHasLocalArmazenamento.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(produto.getPkIdProduto() != null)
         t.setParameter("produto", produto);
      
      List<FarmLoteProdutoHasLocalArmazenamento> resultado = t.getResultList();

      return resultado;
   }
      
   /**
    * @param produto
    * @return Todas as referencias do produto em todos os locais
    */
   public List<FarmLoteProdutoHasLocalArmazenamento> findProdutoEmTodosOsLocais(FarmProduto produto)
   {
      String query = constroiQueryFindProdutoEmTodosOsLocais(produto);
      TypedQuery<FarmLoteProdutoHasLocalArmazenamento> t = em.createQuery(query, FarmLoteProdutoHasLocalArmazenamento.class);
            
      if(produto.getPkIdProduto() != null)
         t.setParameter("produto", produto);
      
      List<FarmLoteProdutoHasLocalArmazenamento> resultado = t.getResultList();
      
      return resultado;
   }
   
   public String constroiQueryComQuantIndefinida (FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = "Select f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.pkIdLoteProdutoHasLocalArmazenamento = f.pkIdLoteProdutoHasLocalArmazenamento";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalArmazenamento = :local";
      
      if(produto.getPkIdProduto() != null)
         query += " AND f.fkIdLoteProduto.fkIdProduto = :produto";
            
      query += " ORDER BY f.fkIdLoteProduto.dataValidade ASC";

      return query;
   }

   public String constroiQuery (FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = "Select f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.pkIdLoteProdutoHasLocalArmazenamento = f.pkIdLoteProdutoHasLocalArmazenamento";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalArmazenamento = :local";
      
      if(produto.getPkIdProduto() != null)
         query += " AND f.fkIdLoteProduto.fkIdProduto = :produto";
      
      query += " AND f.quantidadeStock > 0";
      
      query += " ORDER BY f.fkIdLoteProduto.dataValidade ASC";

      return query;
   }

   public String constroiQueryFindProdutoNoLocal (FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = "Select f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.pkIdLoteProdutoHasLocalArmazenamento = f.pkIdLoteProdutoHasLocalArmazenamento";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalArmazenamento = :local";
      
      if(produto.getPkIdProduto() != null)
         query += " AND f.fkIdLoteProduto.fkIdProduto = :produto";
      
      query += " AND f.quantidadeStock > 0";
      
      query += " ORDER BY f.fkIdLoteProduto.dataValidade ASC";

      return query;
   }

   public String constroiQueryFindProdutoEmTodosOsLocais (FarmProduto produto)
   {
      String query = "Select f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.pkIdLoteProdutoHasLocalArmazenamento = f.pkIdLoteProdutoHasLocalArmazenamento";

      if(produto.getPkIdProduto() != null)
         query += " AND f.fkIdLoteProduto.fkIdProduto = :produto";
      
      query += " ORDER BY f.fkIdLoteProduto.dataValidade ASC";

      return query;
   }
   
   /**
    *
    * @param local
    * @param lote
    * @return
    */
   public String constroiQuery (FarmLocalArmazenamento local, FarmLoteProduto lote)
   {
      String query = "Select f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.pkIdLoteProdutoHasLocalArmazenamento = f.pkIdLoteProdutoHasLocalArmazenamento";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalArmazenamento = :local";
      
      if(lote.getPkIdLoteProduto() != null)
         query += " AND f.fkIdLoteProduto = :lote";
            
      return query;
   }
   
   /**
    *
    * @param local
    * @param lote
    * @return
    */
   public String constroiQueryComQtdPositiva (FarmLocalArmazenamento local, FarmLoteProduto lote)
   {
      String query = "Select f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.pkIdLoteProdutoHasLocalArmazenamento = f.pkIdLoteProdutoHasLocalArmazenamento";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalArmazenamento = :local";
      
      if(lote.getPkIdLoteProduto() != null)
         query += " AND f.fkIdLoteProduto = :lote";
      
         
      query += " AND f.quantidadeStock > 0 ORDER BY f.fkIdLocalArmazenamento.abreviatura";
      return query;
   }
   
   public String constroiQuery_findReferenciaProdutoNoLocal (FarmLocalArmazenamento local, FarmProduto produto)
   {
      String query = "Select f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.pkIdLoteProdutoHasLocalArmazenamento = f.pkIdLoteProdutoHasLocalArmazenamento";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalArmazenamento = :local";
      
      if(produto.getPkIdProduto() != null)
         query += " AND f.fkIdLoteProduto.fkIdProduto = :produto";
            
      query += " ORDER BY f.fkIdLoteProduto.fkIdProduto.descricao";

      return query;
   }
   
   public String constroiQuery (FarmLocalArmazenamento local, String descricao)
   {
      String query = "Select f FROM FarmLoteProdutoHasLocalArmazenamento f WHERE f.pkIdLoteProdutoHasLocalArmazenamento = f.pkIdLoteProdutoHasLocalArmazenamento";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " AND f.fkIdLocalArmazenamento = :local";
                  
      if(descricao != null && !descricao.isEmpty())
         query += " AND f.fkIdLoteProduto.fkIdProduto.descricao LIKE :descricao";
      
      query += " ORDER BY f.fkIdLoteProduto.fkIdProduto.descricao";

      return query;
   }
}
