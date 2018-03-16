/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhEstadoFuncionario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhEstadoFuncionarioFacade extends AbstractFacade<RhEstadoFuncionario>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhEstadoFuncionarioFacade ()
    {
        super(RhEstadoFuncionario.class);
    }
    
    @Override
    public List<RhEstadoFuncionario> findAll()
    {
          Query t = em.createQuery("SELECT e FROM RhEstadoFuncionario e ORDER BY e.descricao", RhEstadoFuncionario.class);

          return t.getResultList();
    }
    
    /**
     * Pesquisa um estado de funcionário com uma determinada descrição
     * 
     * @param descricao
     * @return
     */
    public List<RhEstadoFuncionario> pesquisaPorDescricao(String descricao)
    {
        TypedQuery<RhEstadoFuncionario> t = this.em.createQuery("SELECT ef FROM RhEstadoFuncionario ef "
                + "WHERE ef.descricao = :descricao", RhEstadoFuncionario.class).setParameter("descricao", descricao);

        List<RhEstadoFuncionario> resultado = t.getResultList();

        return resultado;
    }
}
