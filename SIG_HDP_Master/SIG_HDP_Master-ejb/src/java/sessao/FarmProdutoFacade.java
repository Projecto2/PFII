/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmLocalArmazenamento;
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
public class FarmProdutoFacade extends AbstractFacade<FarmProduto>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmProdutoFacade()
   {
      super(FarmProduto.class);
   }
   
   public List<FarmProduto> findProduto (FarmProduto item)
   {
      System.out.println("find produto com os seguintes dados: ");
      System.out.println("getDescricao: "+item.getDescricao());
      System.out.println("getDosagem: "+item.getDosagem());
      System.out.println("getNomeGenerico: "+item.getNomeGenerico());
      System.out.println("getAbreviatura: "+item.getFkIdUnidadeMedida().getAbreviatura());
      System.out.println("getPkIdUnidadeMedida: "+item.getFkIdUnidadeMedida().getPkIdUnidadeMedida());
      System.out.println("getFkIdFormaFarmaceutica desc: "+item.getFkIdFormaFarmaceutica().getDescricao());
      System.out.println("getPkIdFormaFarmaceutica: "+item.getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica());
      
      
      String query = constroiQuery(item);
      System.out.println("query: "+query);
      TypedQuery<FarmProduto> t = em.createQuery(query, FarmProduto.class);
      
      if(item.getDescricao() != null && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%"+item.getDescricao()+"%");
      
      if(item.getNomeGenerico() != null && !item.getNomeGenerico().isEmpty())
         t.setParameter("nomeGenerico", "%"+item.getNomeGenerico()+"%");
      
      if (item.getFkIdTipoProduto().getPkIdTipoProduto() != null)
         t.setParameter("tipoProduto", item.getFkIdTipoProduto());
      
      if (item.getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica() != null)
         t.setParameter("formaFarmaceutica", item.getFkIdFormaFarmaceutica());
      
      if (item.getFkIdViaAdministracao().getPkIdViaAdministracao() != null)
         t.setParameter("viaAdmin", item.getFkIdViaAdministracao());
      
      if (item.getFkIdCategoriaMedicamento().getPkIdCategoriaMedicamento() != null)
         t.setParameter("categoria", item.getFkIdCategoriaMedicamento());
      
      if (item.getFkIdUnidadeMedida().getPkIdUnidadeMedida() != null)
         t.setParameter("unidadeMedida", item.getFkIdUnidadeMedida());
      
      List<FarmProduto> resultado = t.getResultList();
      System.out.println("antes do return : "+resultado);
      return resultado;
   }
      
   public List<FarmProduto> findProdutosComRegistoNoStock (FarmProduto item, FarmLocalArmazenamento local)
   {
      String query = constroiQueryProdutosComRegistoNoStock(item, local);

      TypedQuery<FarmProduto> t = em.createQuery(query, FarmProduto.class);
      
      if(local.getPkIdLocalArmazenamento() != null)
         t.setParameter("local", local);
      
      if(item.getDescricao() != null && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%"+item.getDescricao()+"%");
      
      if(item.getNomeGenerico() != null && !item.getNomeGenerico().isEmpty())
         t.setParameter("nomeGenerico", "%"+item.getNomeGenerico()+"%");
      
      if (item.getFkIdTipoProduto().getPkIdTipoProduto() != null)
         t.setParameter("tipoProduto", item.getFkIdTipoProduto());
      
      if (item.getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica() != null)
         t.setParameter("formaFarmaceutica", item.getFkIdFormaFarmaceutica());
      
      if (item.getFkIdViaAdministracao().getPkIdViaAdministracao() != null)
         t.setParameter("viaAdmin", item.getFkIdViaAdministracao());
      
      if (item.getFkIdCategoriaMedicamento().getPkIdCategoriaMedicamento() != null)
         t.setParameter("categoria", item.getFkIdCategoriaMedicamento());
      
      if (item.getFkIdUnidadeMedida().getPkIdUnidadeMedida() != null)
         t.setParameter("unidadeMedida", item.getFkIdUnidadeMedida());
      
      List<FarmProduto> resultado = t.getResultList();
      return resultado;
   }
   
   public String constroiQuery (FarmProduto item)
   {
      String query = "SELECT f FROM FarmProduto f WHERE f.pkIdProduto = f.pkIdProduto";

      if(item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";
      
      if(item.getNomeGenerico() != null && !item.getNomeGenerico().isEmpty())
         query += " AND f.nomeGenerico LIKE :nomeGenerico";
      
      if (item.getFkIdTipoProduto().getPkIdTipoProduto() != null)
         query += " AND f.fkIdTipoProduto = :tipoProduto";
      
      if (item.getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica() != null)
         query += " AND f.fkIdFormaFarmaceutica = :formaFarmaceutica";
      
      if (item.getFkIdViaAdministracao().getPkIdViaAdministracao() != null)
           query += " AND f.fkIdViaAdministracao = :viaAdmin";
      
      if (item.getFkIdCategoriaMedicamento().getPkIdCategoriaMedicamento()!= null)
           query += " AND f.fkIdCategoriaMedicamento = :categoria";
      
      if (item.getFkIdUnidadeMedida().getPkIdUnidadeMedida() != null)
         query += " AND f.fkIdUnidadeMedida = :unidadeMedida";

      query += " ORDER BY f.descricao";

      return query;
   }
   
   public String constroiQueryProdutosComRegistoNoStock (FarmProduto item, FarmLocalArmazenamento local)
   {
      String query = "SELECT f FROM FarmProduto f WHERE f.pkIdProduto IN ( SELECT p.fkIdLoteProduto.fkIdProduto.pkIdProduto FROM FarmLoteProdutoHasLocalArmazenamento p";

      if(local.getPkIdLocalArmazenamento() != null)
         query += " WHERE p.fkIdLocalArmazenamento = :local)";
      else
         query += " )";

      if(item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";
      
      if(item.getNomeGenerico() != null && !item.getNomeGenerico().isEmpty())
         query += " AND f.nomeGenerico LIKE :nomeGenerico";
      
      if (item.getFkIdTipoProduto().getPkIdTipoProduto() != null)
         query += " AND f.fkIdTipoProduto = :tipoProduto";
      
      if (item.getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica() != null)
         query += " AND f.fkIdFormaFarmaceutica = :formaFarmaceutica";
      
      if (item.getFkIdViaAdministracao().getPkIdViaAdministracao() != null)
           query += " AND f.fkIdViaAdministracao = :viaAdmin";
      
      if (item.getFkIdCategoriaMedicamento().getPkIdCategoriaMedicamento()!= null)
           query += " AND f.fkIdCategoriaMedicamento = :categoria";
      
      if (item.getFkIdUnidadeMedida().getPkIdUnidadeMedida() != null)
         query += " AND f.fkIdUnidadeMedida = :unidadeMedida";

      query += " ORDER BY f.descricao";

      return query;
   }
}
