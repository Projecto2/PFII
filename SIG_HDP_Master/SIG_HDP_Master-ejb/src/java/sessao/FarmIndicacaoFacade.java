/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmIndicacao;
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
public class FarmIndicacaoFacade extends AbstractFacade<FarmIndicacao>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmIndicacaoFacade()
   {
      super(FarmIndicacao.class);
   }
   
   public List<FarmIndicacao> findIndicacao(FarmIndicacao indicacao)
   {
      String query = constroiQuery(indicacao);

      TypedQuery<FarmIndicacao> t = em.createQuery(query, FarmIndicacao.class);

      if (indicacao.getDescricao() != null && !(indicacao.getDescricao().isEmpty()))
         t.setParameter("descricao", indicacao.getDescricao() + "%");

      List<FarmIndicacao> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(FarmIndicacao indicacao)
   {
      String query = "SELECT f FROM FarmIndicacao f WHERE f.pkIdIndicacao = f.pkIdIndicacao";

      if (indicacao.getDescricao() != null && !(indicacao.getDescricao().isEmpty()))
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }

   public boolean isIndicacaoTabelaEmpty()
   {
      List<FarmIndicacao> listIndicacoes = this.findAll();
      return (listIndicacoes == null || listIndicacoes.isEmpty());
   }

   public boolean existeRegisto(int pkIdIndicacao)
   {
      FarmIndicacao reg = this.find(pkIdIndicacao);
      return reg != null;
   }
}
