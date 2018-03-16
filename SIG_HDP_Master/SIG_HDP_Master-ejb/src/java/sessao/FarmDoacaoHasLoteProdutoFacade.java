/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmDoacaoHasLoteProduto;
import java.util.Date;
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
public class FarmDoacaoHasLoteProdutoFacade extends AbstractFacade<FarmDoacaoHasLoteProduto>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmDoacaoHasLoteProdutoFacade()
   {
      super(FarmDoacaoHasLoteProduto.class);
   }
   
   public List<FarmDoacaoHasLoteProduto> findDoacaoHasLoteProduto (FarmDoacaoHasLoteProduto item, Date dataInicio, Date dataFim)
   {      
      String query = constroiQuery(item, dataInicio, dataFim);

      TypedQuery<FarmDoacaoHasLoteProduto> t = em.createQuery(query, FarmDoacaoHasLoteProduto.class);
                  
      if(dataInicio != null && dataFim != null)
      {
         t.setParameter("dataInicio", dataInicio);
         t.setParameter("dataFim", dataFim);
      }
      
      if(dataInicio != null && dataFim == null)
      {
         t.setParameter("dataInicio", dataInicio);
      }
      
      if(dataInicio == null && dataFim != null)
      {
         t.setParameter("dataFim", dataFim);
      }
      
      List<FarmDoacaoHasLoteProduto> resultado = t.getResultList();

      return resultado;
   }
   
   
   public String constroiQuery (FarmDoacaoHasLoteProduto item, Date dataInicio, Date dataFim)
   {
      String query = "SELECT f FROM FarmDoacaoHasLoteProduto f WHERE f.pkIdDoacaoHasLoteProduto = f.pkIdDoacaoHasLoteProduto";
      
      if(dataInicio != null && dataFim != null)
      {
         query += " AND f.fkIdDoacao.dataCadastro >= :dataInicio and f.fkIdDoacao.dataCadastro <= :dataFim";
      }
      
      if(dataInicio != null && dataFim == null)
      {
         query += " AND f.fkIdDoacao.dataCadastro >= :dataInicio";
      }
      
      if(dataInicio == null && dataFim != null)
      {
         query += " AND f.fkIdDoacao.dataCadastro <= :dataFim";
      }
      
      query += " ORDER BY f.fkIdDoacao.dataCadastro DESC";

      return query;
   }
}
