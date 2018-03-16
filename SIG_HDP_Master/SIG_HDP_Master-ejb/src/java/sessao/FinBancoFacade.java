/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FinBanco;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gemix
 */
@Stateless
public class FinBancoFacade extends AbstractFacade<FinBanco>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinBancoFacade()
    {
        super(FinBanco.class);
    }
    
    
    
    public List<FinBanco> findBanco(FinBanco banco)
  {
      String query = constroiQuery(banco);

      Query qry = em.createQuery(query);

      qry.setParameter("pesquisar", true);
      
      if (banco.getNomeBanco()!= null && !banco.getNomeBanco().trim().isEmpty())
          qry.setParameter("nomeBanco", banco.getNomeBanco()+"%");
      
      List<FinBanco> bancos = qry.getResultList();

      return bancos;
  }
  
  
  public String constroiQuery(FinBanco banco)
  {        
      String query = "SELECT banco FROM FinBanco banco WHERE :pesquisar = :pesquisar";
      
      if (banco.getNomeBanco() != null && !banco.getNomeBanco().trim().isEmpty())
         query += " AND banco.nomeBanco LIKE :nomeBanco";

      query += " ORDER BY banco.nomeBanco";

      return query;
  }
    
}
