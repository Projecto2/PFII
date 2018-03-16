/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhFuncionarioHasRhSubsidio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhFuncionarioHasRhSubsidioFacade extends AbstractFacade<RhFuncionarioHasRhSubsidio>
{

     @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager ()
     {
          return em;
     }

     public RhFuncionarioHasRhSubsidioFacade ()
     {
          super(RhFuncionarioHasRhSubsidio.class);
     }

    /**
     * Retorna a lista de todos os subsídios de um determinado funcionário
     * 
     * @param idFuncionario
     * @return
     */
    public List<RhFuncionarioHasRhSubsidio> pesquisaPorFuncionario (Integer idFuncionario)
     {
          TypedQuery<RhFuncionarioHasRhSubsidio> t = em.createQuery("SELECT fhs FROM RhFuncionarioHasRhSubsidio fhs WHERE fhs.fkIdFuncionario.pkIdFuncionario = :idFuncionario", RhFuncionarioHasRhSubsidio.class)
                  .setParameter("idFuncionario", idFuncionario);

          List<RhFuncionarioHasRhSubsidio> resultado = t.getResultList();

          return resultado;
     }

    /**
     * Elimina todos os subsídios de um funcionário
     * 
     * @param idFuncionario
     * @return
     */
    public Integer eliminarPorFuncionario (Integer idFuncionario)
     {
          return em. createQuery("DELETE FROM RhFuncionarioHasRhSubsidio fhs WHERE fhs.fkIdFuncionario.pkIdFuncionario = :idFuncionario")
                  .setParameter("idFuncionario", idFuncionario).executeUpdate();
     }
}
