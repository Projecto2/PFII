/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmProduto;
import entidade.FarmProdutoHasContraIndicacao;
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
public class FarmProdutoHasContraIndicacaoFacade extends AbstractFacade<FarmProdutoHasContraIndicacao>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public List<FarmProdutoHasContraIndicacao> findByIdProduto(FarmProduto item)
   {
      TypedQuery<FarmProdutoHasContraIndicacao> t = em.
              createQuery("SELECT f FROM FarmProdutoHasContraIndicacao f WHERE f.fkIdProduto = :idProduto ORDER BY f.fkIdContraIndicacao.descricao",
                      FarmProdutoHasContraIndicacao.class).setParameter("idProduto", item);
      return t.getResultList();
   }
   
   public FarmProdutoHasContraIndicacaoFacade()
   {
      super(FarmProdutoHasContraIndicacao.class);
   }
   
}
