/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmProduto;
import entidade.FarmProdutoHasEfeitoSecundario;
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
public class FarmProdutoHasEfeitoSecundarioFacade extends AbstractFacade<FarmProdutoHasEfeitoSecundario>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmProdutoHasEfeitoSecundarioFacade()
   {
      super(FarmProdutoHasEfeitoSecundario.class);
   }
   
   public List<FarmProdutoHasEfeitoSecundario> findByIdProduto(FarmProduto item)
   {
      TypedQuery<FarmProdutoHasEfeitoSecundario> t = em.
              createQuery("SELECT f FROM FarmProdutoHasEfeitoSecundario f WHERE f.fkIdProduto.pkIdProduto = :idProduto ORDER BY f.fkIdEfeitoSecundario.descricaoEfeitoSecundario",
                      FarmProdutoHasEfeitoSecundario.class).setParameter("idProduto", item.getPkIdProduto());
      return t.getResultList();
   }
}
