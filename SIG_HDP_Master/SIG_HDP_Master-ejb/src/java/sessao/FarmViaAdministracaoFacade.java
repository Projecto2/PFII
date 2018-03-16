/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.FarmViaAdministracao;
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
public class FarmViaAdministracaoFacade extends AbstractFacade<FarmViaAdministracao>
{

   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmViaAdministracaoFacade()
   {
      super(FarmViaAdministracao.class);
   }

   public List<FarmViaAdministracao> findViaAdministracao(FarmViaAdministracao item)
   {
      String query = constroiQuery(item);

      TypedQuery<FarmViaAdministracao> t = em.createQuery(query, FarmViaAdministracao.class);

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%" + item.getDescricao() + "%");

      List<FarmViaAdministracao> resultado = t.getResultList();

      return resultado;
   }
   
   public String constroiQuery(FarmViaAdministracao item)
   {
      String query = "SELECT f FROM FarmViaAdministracao f WHERE f.pkIdViaAdministracao = f.pkIdViaAdministracao";

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";

      query += " ORDER BY f.descricao";

      return query;
   }
   
   public boolean isViaAdministracaoTabelaEmpty()
   {
      List<FarmViaAdministracao> listViasAdministracao = this.findAll();
      return (listViasAdministracao == null || listViasAdministracao.isEmpty());
   }

   public boolean existeRegisto(int pkIdViaAdministracao)
   {
      FarmViaAdministracao reg = this.find(pkIdViaAdministracao);
      return reg != null;
   }
}
