/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmObservacao;
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
public class FarmObservacaoFacade extends AbstractFacade<FarmObservacao>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmObservacaoFacade()
   {
      super(FarmObservacao.class);
   }     

   public List<FarmObservacao> findObservacao(FarmObservacao observacao)
   {
      String query = constroiQuery(observacao);

      TypedQuery<FarmObservacao> t = em.createQuery(query, FarmObservacao.class);

      if (observacao.getDescricao() != null && !(observacao.getDescricao().isEmpty()))
         t.setParameter("descricao", observacao.getDescricao() + "%");

      List<FarmObservacao> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(FarmObservacao observacao)
   {
      String query = "SELECT f FROM FarmObservacao f WHERE f.pkIdObservacao = f.pkIdObservacao";

      if (observacao.getDescricao() != null && !(observacao.getDescricao().isEmpty()))
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }

   public boolean isObservacaoTabelaEmpty()
   {
      List<FarmObservacao> listObservacoes = this.findAll();
      return (listObservacoes == null || listObservacoes.isEmpty());
   }

   public boolean existeRegisto(int pkIdObservacao)
   {
      FarmObservacao reg = this.find(pkIdObservacao);
      return reg != null;
   }
}
