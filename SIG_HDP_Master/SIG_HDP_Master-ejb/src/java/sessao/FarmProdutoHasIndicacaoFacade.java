/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmProduto;
import entidade.FarmProdutoHasIndicacao;
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
public class FarmProdutoHasIndicacaoFacade extends AbstractFacade<FarmProdutoHasIndicacao>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public List<FarmProdutoHasIndicacao> findByIdProduto(FarmProduto item)
   {
      TypedQuery<FarmProdutoHasIndicacao> t = em.
              createQuery("SELECT f FROM FarmProdutoHasIndicacao f WHERE f.fkIdProduto = :idProduto ORDER BY f.fkIdIndicacao.descricao",
                      FarmProdutoHasIndicacao.class).setParameter("idProduto", item);
      return t.getResultList();
   }
   
   public FarmProdutoHasIndicacaoFacade()
   {
      super(FarmProdutoHasIndicacao.class);
   }
   
}
