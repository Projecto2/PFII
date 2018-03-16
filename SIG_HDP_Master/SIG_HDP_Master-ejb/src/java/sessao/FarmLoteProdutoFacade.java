/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.FarmLoteProduto;
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
public class FarmLoteProdutoFacade extends AbstractFacade<FarmLoteProduto>
{

   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmLoteProdutoFacade()
   {
      super(FarmLoteProduto.class);
   }

   public List<FarmLoteProduto> findLoteProduto(FarmLoteProduto item, Date dataValidadeInicio, Date dataValidadeFim)
   {
      String query = constroiQuery4(item, dataValidadeInicio, dataValidadeFim);
      System.out.println("0 findLoteProduto query> "+query);
      TypedQuery<FarmLoteProduto> t = em.createQuery(query, FarmLoteProduto.class);

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         t.setParameter("lote", "%" + item.getNumeroLote() + "%");

      if (item.getFkIdMarca().getPkIdMarca() != null)
         t.setParameter("marca", item.getFkIdMarca());

      if (item.getFkIdProduto().getDescricao() != null && !item.getFkIdProduto().getDescricao().isEmpty())
         t.setParameter("descricaoProduto", "%" + item.getFkIdProduto().getDescricao() + "%");

      if (dataValidadeInicio != null && dataValidadeFim != null)
      {
         t.setParameter("dataValidadeInicio", dataValidadeInicio);
         t.setParameter("dataValidadeFim", dataValidadeFim);
      }

      if (dataValidadeInicio != null && dataValidadeFim == null)
      {
         t.setParameter("dataValidadeInicio", dataValidadeInicio);
      }

      if (dataValidadeInicio == null && dataValidadeFim != null)
      {
         t.setParameter("dataValidadeFim", dataValidadeFim);
      }

      List<FarmLoteProduto> resultado = t.getResultList();

      return resultado;
   }

   public List<FarmLoteProduto> findLotesPorDescricaoProduto(FarmLoteProduto item)
   {
      String query = constroiQuery3(item);

      TypedQuery<FarmLoteProduto> t = em.createQuery(query, FarmLoteProduto.class);

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         t.setParameter("lote", "%" + item.getNumeroLote() + "%");

      if (item.getFkIdProduto().getDescricao() != null && !item.getFkIdProduto().getDescricao().isEmpty())
         t.setParameter("descricaoProduto", "%" + item.getFkIdProduto().getDescricao() + "%");

      List<FarmLoteProduto> resultado = t.getResultList();

      return resultado;
   }

   public List<FarmLoteProduto> findLotePorProduto(FarmLoteProduto item, FarmProduto produto)
   {
      String query = constroiQuery2(item);

      TypedQuery<FarmLoteProduto> t = em.createQuery(query, FarmLoteProduto.class);

      t.setParameter("idProduto", produto.getPkIdProduto());

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         t.setParameter("lote", "%" + item.getNumeroLote() + "%");

      List<FarmLoteProduto> resultado = t.getResultList();

      return resultado;
   }

   public List<FarmLoteProduto> findProduto(FarmLoteProduto item)
   {
      String query = constroiQuery(item);

      TypedQuery<FarmLoteProduto> t = em.createQuery(query, FarmLoteProduto.class);

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         t.setParameter("lote", "%" + item.getNumeroLote() + "%");

      if (item.getFkIdProduto().getDescricao() != null && !item.getFkIdProduto().getDescricao().isEmpty())
         t.setParameter("descricao", "%" + item.getFkIdProduto().getDescricao() + "%");

      if (item.getFkIdProduto().getNomeGenerico() != null && !item.getFkIdProduto().getNomeGenerico().isEmpty())
         t.setParameter("nomeGenerico", "%" + item.getFkIdProduto().getNomeGenerico() + "%");

      if (item.getNomeComercial() != null && !item.getNomeComercial().isEmpty())
         t.setParameter("nomeComercial", "%" + item.getNomeComercial() + "%");

      if (item.getFkIdProduto().getFkIdTipoProduto().getPkIdTipoProduto() != null)
         t.setParameter("tipoProduto", item.getFkIdProduto().getFkIdTipoProduto());

      if (item.getFkIdProduto().getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica() != null)
         t.setParameter("formaFarmaceutica", item.getFkIdProduto().getFkIdFormaFarmaceutica());
//      
      if (item.getFkIdProduto().getFkIdViaAdministracao().getPkIdViaAdministracao() != null)
         t.setParameter("viaAdmin", item.getFkIdProduto().getFkIdViaAdministracao());

      List<FarmLoteProduto> resultado = t.getResultList();

      return resultado;
   }

   public List<FarmLoteProduto> findProdutoNaoExpirado(FarmLoteProduto item)
   {
      String query = constroiQueryProdutoNaoExpirado(item);

      TypedQuery<FarmLoteProduto> t = em.createQuery(query, FarmLoteProduto.class);

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         t.setParameter("lote", "%" + item.getNumeroLote() + "%");

      if (item.getFkIdProduto().getDescricao() != null && !item.getFkIdProduto().getDescricao().isEmpty())
         t.setParameter("descricao", "%" + item.getFkIdProduto().getDescricao() + "%");

      if (item.getFkIdProduto().getNomeGenerico() != null && !item.getFkIdProduto().getNomeGenerico().isEmpty())
         t.setParameter("nomeGenerico", "%" + item.getFkIdProduto().getNomeGenerico() + "%");

      if (item.getNomeComercial() != null && !item.getNomeComercial().isEmpty())
         t.setParameter("nomeComercial", "%" + item.getNomeComercial() + "%");

      if (item.getFkIdProduto().getFkIdTipoProduto().getPkIdTipoProduto() != null)
         t.setParameter("tipoProduto", item.getFkIdProduto().getFkIdTipoProduto());

      if (item.getFkIdProduto().getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica() != null)
         t.setParameter("formaFarmaceutica", item.getFkIdProduto().getFkIdFormaFarmaceutica());
//      
      if (item.getFkIdProduto().getFkIdViaAdministracao().getPkIdViaAdministracao() != null)
         t.setParameter("viaAdmin", item.getFkIdProduto().getFkIdViaAdministracao());

      t.setParameter("dataHoje", new Date());
      List<FarmLoteProduto> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery4(FarmLoteProduto item, Date dataValidadeInicio, Date dataValidadeFim)
   {
      String query = "SELECT f FROM FarmLoteProduto f WHERE f.pkIdLoteProduto = f.pkIdLoteProduto";

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         query += " AND f.numeroLote LIKE :lote";

      if (item.getFkIdMarca().getPkIdMarca() != null)
         query += " AND f.fkIdMarca = :marca";

      if (item.getFkIdProduto().getDescricao() != null && !item.getFkIdProduto().getDescricao().isEmpty())
         query += " AND f.fkIdProduto.descricao LIKE :descricaoProduto";

      if (dataValidadeInicio != null && dataValidadeFim != null)
         query += " AND f.dataValidade between :dataValidadeInicio and :dataValidadeFim";

      if (dataValidadeInicio != null && dataValidadeFim == null)
         query += " AND f.dataValidade >= :dataValidadeInicio";

      if (dataValidadeInicio == null && dataValidadeFim != null)
         query += " AND f.dataValidade <= :dataValidadeFim";

      query += " ORDER BY f.dataValidade";

      return query;
   }

   public String constroiQuery3(FarmLoteProduto item)
   {
      String query = "SELECT f FROM FarmLoteProduto f WHERE f.fkIdProduto.pkIdProduto = f.fkIdProduto.pkIdProduto";

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         query += " AND f.numeroLote LIKE :lote";

      if (item.getFkIdProduto().getDescricao() != null && !item.getFkIdProduto().getDescricao().isEmpty())
         query += " AND f.fkIdProduto.descricao LIKE :descricaoProduto";

      query += " ORDER BY f.dataValidade";

      return query;
   }

   public String constroiQuery2(FarmLoteProduto item)
   {
      String query = "SELECT f FROM FarmLoteProduto f WHERE f.fkIdProduto.pkIdProduto = :idProduto";

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         query += " AND f.numeroLote LIKE :lote";

      query += " ORDER BY f.dataValidade";

      return query;
   }

   public String constroiQuery(FarmLoteProduto item)
   {
      String query = "SELECT f FROM FarmLoteProduto f WHERE f.pkIdLoteProduto = f.pkIdLoteProduto";

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         query += " AND f.numeroLote LIKE :lote";

      if (item.getFkIdProduto().getDescricao() != null && !item.getFkIdProduto().getDescricao().isEmpty())
         query += " AND f.fkIdProduto.descricao LIKE :descricao";

      if (item.getFkIdProduto().getNomeGenerico() != null && !item.getFkIdProduto().getNomeGenerico().isEmpty())
         query += " AND f.fkIdProduto.nomeGenerico LIKE :nomeGenerico";

      if (item.getNomeComercial() != null && !item.getNomeComercial().isEmpty())
         query += " AND f.fkIdProduto.nomeComercial LIKE :nomeComercial";

      if (item.getFkIdProduto().getFkIdTipoProduto().getPkIdTipoProduto() != null)
         query += " AND f.fkIdProduto.fkIdTipoProduto = :tipoProduto";

      if (item.getFkIdProduto().getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica() != null)
         query += " AND f.fkIdProduto.fkIdFormaFarmaceutica = :formaFarmaceutica";

      if (item.getFkIdProduto().getFkIdViaAdministracao().getPkIdViaAdministracao() != null)
         query += " AND f.fkIdProduto.fkIdViaAdministracao = :viaAdmin";

      query += " ORDER BY f.dataValidade";

      return query;
   }

   public String constroiQueryProdutoNaoExpirado(FarmLoteProduto item)
   {
      String query = "SELECT f FROM FarmLoteProduto f WHERE f.pkIdLoteProduto = f.pkIdLoteProduto";

      if (item.getNumeroLote() != null && !item.getNumeroLote().isEmpty())
         query += " AND f.numeroLote LIKE :lote";

      if (item.getFkIdProduto().getDescricao() != null && !item.getFkIdProduto().getDescricao().isEmpty())
         query += " AND f.fkIdProduto.descricao LIKE :descricao";

      if (item.getFkIdProduto().getNomeGenerico() != null && !item.getFkIdProduto().getNomeGenerico().isEmpty())
         query += " AND f.fkIdProduto.nomeGenerico LIKE :nomeGenerico";

      if (item.getNomeComercial() != null && !item.getNomeComercial().isEmpty())
         query += " AND f.fkIdProduto.nomeComercial LIKE :nomeComercial";

      if (item.getFkIdProduto().getFkIdTipoProduto().getPkIdTipoProduto() != null)
         query += " AND f.fkIdProduto.fkIdTipoProduto = :tipoProduto";

      if (item.getFkIdProduto().getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica() != null)
         query += " AND f.fkIdProduto.fkIdFormaFarmaceutica = :formaFarmaceutica";

      if (item.getFkIdProduto().getFkIdViaAdministracao().getPkIdViaAdministracao() != null)
         query += " AND f.fkIdProduto.fkIdViaAdministracao = :viaAdmin";

      query += " AND f.dataValidade > :dataHoje ORDER BY f.dataValidade";

      return query;
   }
}