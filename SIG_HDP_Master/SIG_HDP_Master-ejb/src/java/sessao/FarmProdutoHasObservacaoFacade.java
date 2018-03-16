/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmProduto;
import entidade.FarmProdutoHasObservacao;
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
public class FarmProdutoHasObservacaoFacade extends AbstractFacade<FarmProdutoHasObservacao>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }
   
   public List<FarmProdutoHasObservacao> findByIdProduto(FarmProduto item)
   {
      TypedQuery<FarmProdutoHasObservacao> t = em.
              createQuery("SELECT f FROM FarmProdutoHasObservacao f WHERE f.fkIdProduto = :idProduto ORDER BY f.fkIdObservacao.descricao",
                      FarmProdutoHasObservacao.class).setParameter("idProduto", item);
      return t.getResultList();
   }

   public FarmProdutoHasObservacaoFacade()
   {
      super(FarmProdutoHasObservacao.class);
   }
   
}
