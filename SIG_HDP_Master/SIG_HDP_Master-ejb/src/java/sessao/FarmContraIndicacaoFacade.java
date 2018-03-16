/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmContraIndicacao;
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
public class FarmContraIndicacaoFacade extends AbstractFacade<FarmContraIndicacao>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmContraIndicacaoFacade()
   {
      super(FarmContraIndicacao.class);
   }
   
   public List<FarmContraIndicacao> findIndicacao(FarmContraIndicacao contraIndicacao)
   {
      String query = constroiQuery(contraIndicacao);

      TypedQuery<FarmContraIndicacao> t = em.createQuery(query, FarmContraIndicacao.class);

      if (contraIndicacao.getDescricao() != null && !(contraIndicacao.getDescricao().isEmpty()))
         t.setParameter("descricao", contraIndicacao.getDescricao() + "%");

      List<FarmContraIndicacao> resultado = t.getResultList();

      return resultado;
   }

   public String constroiQuery(FarmContraIndicacao contraIndicacao)
   {
      String query = "SELECT f FROM FarmContraIndicacao f WHERE f.pkIdContraIndicacao = f.pkIdContraIndicacao";

      if (contraIndicacao.getDescricao() != null && !(contraIndicacao.getDescricao().isEmpty()))
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }

   public boolean isContraIndicacaoTabelaEmpty()
   {
      List<FarmContraIndicacao> listContraIndicacoes = this.findAll();
      return (listContraIndicacoes == null || listContraIndicacoes.isEmpty());
   }

   public boolean existeRegisto(int pkIdContraIndicacao)
   {
      FarmContraIndicacao reg = this.find(pkIdContraIndicacao);
      return reg != null;
   }
}
