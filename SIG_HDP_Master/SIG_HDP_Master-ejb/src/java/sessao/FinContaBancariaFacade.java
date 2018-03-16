/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FinContaBancaria;
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
public class FinContaBancariaFacade extends AbstractFacade<FinContaBancaria>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinContaBancariaFacade()
    {
        super(FinContaBancaria.class);
    }
    
    /**
     *
     * @param contaBancaria
     * @return
     */
    public List<FinContaBancaria> findContaBancaria(FinContaBancaria contaBancaria)
  {
      String query = constroiQuery(contaBancaria);

      Query qry = em.createQuery(query);

      qry.setParameter("pesquisar", true);
      
      if (contaBancaria.getFkIdBanco().getPkIdBanco() != null)
          qry.setParameter("fkIdBanco", contaBancaria.getFkIdBanco().getPkIdBanco());

      if (contaBancaria.getNumeroConta()!= null && !contaBancaria.getNumeroConta().trim().isEmpty())
          qry.setParameter("numeroConta", contaBancaria.getNumeroConta()+"%");
      
      
      List<FinContaBancaria> contaBancarias = qry.getResultList();

      return contaBancarias;
  }
  
  
  public String constroiQuery(FinContaBancaria contaBancaria)
  {        
      String query = "SELECT contaBancaria FROM FinContaBancaria as contaBancaria WHERE :pesquisar = :pesquisar";
      
      if (contaBancaria.getFkIdBanco().getPkIdBanco() != null)
         query += " AND contaBancaria.fkIdBanco.pkIdBanco = :fkIdBanco";

      if (contaBancaria.getNumeroConta() != null && !contaBancaria.getNumeroConta().trim().isEmpty())
         query += " AND contaBancaria.numeroConta LIKE :numeroConta";

      //query += " ORDER BY contaBancaria.fkIdBanco";
      
      //query += " ORDER BY contaBancaria.getFkIdPessoa().pkIdPessoa";

      return query;
  }

    public List<FinContaBancaria> pesquisaPorIdDoBanco(Integer idBanco)
    {

        Query query = em.createQuery("SELECT contaBancaria from FinContaBancaria contaBancaria WHERE contaBancaria.fkIdBanco.pkIdBanco = :codBanco");
    
        query.setParameter("codBanco", idBanco);
        return query.getResultList();
    }
    
}
