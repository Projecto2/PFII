/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmNotificacao;
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
public class FarmNotificacaoFacade extends AbstractFacade<FarmNotificacao>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmNotificacaoFacade()
   {
      super(FarmNotificacao.class);
   }
   
   public List<FarmNotificacao> findNotificacaoOrderByDataASC ()
   {      
      String query = "SELECT f FROM FarmNotificacao f WHERE f.pkIdNotificacao = f.pkIdNotificacao";
      
      query += " ORDER BY f.dataCadastro DESC";
      
      TypedQuery<FarmNotificacao> t = em.createQuery(query, FarmNotificacao.class);
      
      List<FarmNotificacao> resultado = t.getResultList();

      return resultado;
   }
   
   public List<FarmNotificacao> findNotificacaoOrderByTipo()
   {      
      String query = "SELECT f FROM FarmNotificacao f WHERE f.pkIdNotificacao = f.pkIdNotificacao";
      
      query += " ORDER BY f.fkIdTipoNotificacao";
      
      TypedQuery<FarmNotificacao> t = em.createQuery(query, FarmNotificacao.class);
      
      List<FarmNotificacao> resultado = t.getResultList();

      return resultado;
   }
   
   public List<FarmNotificacao> findNotificacao (FarmNotificacao item)
   {      
      String query = constroiQuery(item);

      TypedQuery<FarmNotificacao> t = em.createQuery(query, FarmNotificacao.class);
      
      
      if (item.getTitulo() != null && !item.getTitulo().isEmpty())
         t.setParameter("titulo", item.getTitulo());
      
      if(item.getFkIdLoteProduto().getPkIdLoteProduto() != null)
         t.setParameter("lote", item.getFkIdLoteProduto());
      
      if(item.getFkIdProduto().getPkIdProduto() != null)
         t.setParameter("produto", item.getFkIdProduto());
      
      List<FarmNotificacao> resultado = t.getResultList();

      return resultado;
   }
   
    public String constroiQuery (FarmNotificacao item)
   {
      String query = "SELECT f FROM FarmNotificacao f WHERE f.pkIdNotificacao = f.pkIdNotificacao";

      if (item.getTitulo() != null && !item.getTitulo().isEmpty())
           query += " AND f.titulo = :titulo";
      
      if(item.getFkIdLoteProduto().getPkIdLoteProduto() != null)
           query += " AND f.fkIdLoteProduto = :lote";
      
      if(item.getFkIdProduto().getPkIdProduto() != null)
           query += " AND f.fkIdProduto = :produto";
      
      query += " ORDER BY f.dataCadastro DESC";
      return query;
   }
   
}
