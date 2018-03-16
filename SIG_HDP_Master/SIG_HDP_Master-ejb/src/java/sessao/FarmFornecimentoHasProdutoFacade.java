/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmFornecimento;
import entidade.FarmFornecimentoHasProduto;
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
public class FarmFornecimentoHasProdutoFacade extends AbstractFacade<FarmFornecimentoHasProduto>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmFornecimentoHasProdutoFacade()
   {
      super(FarmFornecimentoHasProduto.class);
   }
   
   public List<FarmFornecimentoHasProduto> findItensFornecimento (FarmFornecimento item)
   {      
      String query = constroiQuery(item);

      TypedQuery<FarmFornecimentoHasProduto> t = em.createQuery(query, FarmFornecimentoHasProduto.class);
                  
      if(item.getPkIdFornecimento() != null)
         t.setParameter("fornecimento", item);
     
      List<FarmFornecimentoHasProduto> resultado = t.getResultList();

      return resultado;
   }
   
   
   public String constroiQuery (FarmFornecimento item)
   {
      String query = "SELECT f FROM FarmFornecimentoHasProduto f WHERE f.pkIdFornecimentoHasProduto = f.pkIdFornecimentoHasProduto";
      
      if(item.getPkIdFornecimento() != null)
         query += " AND f.fkIdFornecimento = :fornecimento";
           
      query += " ORDER BY f.fkIdLoteProduto.fkIdProduto.descricao DESC";

      return query;
   }
}
