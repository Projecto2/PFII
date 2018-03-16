/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmEfeitoSecundario;
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
public class FarmEfeitoSecundarioFacade extends AbstractFacade<FarmEfeitoSecundario>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FarmEfeitoSecundarioFacade()
    {
        super(FarmEfeitoSecundario.class);
    }
    
    public List<FarmEfeitoSecundario> findEfeitoSecundario(FarmEfeitoSecundario efeitoSecundario)
    {
       String query = constroiQuery(efeitoSecundario);

       
      TypedQuery<FarmEfeitoSecundario> t = em.createQuery(query, FarmEfeitoSecundario.class);
       
      if (efeitoSecundario.getDescricaoEfeitoSecundario()!= null && !(efeitoSecundario.getDescricaoEfeitoSecundario().isEmpty()))
         t.setParameter("descricao", efeitoSecundario.getDescricaoEfeitoSecundario()+"%");

      List<FarmEfeitoSecundario> resultado = t.getResultList();

      return resultado;
    }
    
    public String constroiQuery (FarmEfeitoSecundario efeitoSecundario)
   {
      String query = "SELECT f FROM FarmEfeitoSecundario f WHERE f.pkIdEfeitoSecundario = f.pkIdEfeitoSecundario";

      if (efeitoSecundario.getDescricaoEfeitoSecundario() != null && !(efeitoSecundario.getDescricaoEfeitoSecundario().isEmpty()))
           query += " AND f.descricaoEfeitoSecundario LIKE :descricao";

      query += " ORDER BY f.descricaoEfeitoSecundario";

      return query;
   }
    
   public boolean isEfeitoSecundarioTabelaEmpty()
   {
      List<FarmEfeitoSecundario> listEfeitosSecundarios = this.findAll();
      return (listEfeitosSecundarios == null || listEfeitosSecundarios.isEmpty());
   }

   public boolean existeRegisto(int pkIdEfeitoSecundario)
   {
      FarmEfeitoSecundario reg = this.find(pkIdEfeitoSecundario);
      return reg != null;
   }
}
