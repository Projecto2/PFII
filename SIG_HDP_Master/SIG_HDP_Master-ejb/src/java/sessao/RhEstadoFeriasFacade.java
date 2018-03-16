/*
 * To change this license header, choose License Headers in Projeft Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhEstadoFerias;
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
public class RhEstadoFeriasFacade extends AbstractFacade<RhEstadoFerias>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhEstadoFeriasFacade ()
    {
        super(RhEstadoFerias.class);
    }
    
    @Override
    public List<RhEstadoFerias> findAll()
    {
          Query t = em.createQuery("SELECT e FROM RhEstadoFerias e ORDER BY e.descricao", RhEstadoFerias.class);

          return t.getResultList();
    }
    
    /**
     * Pesquisa o estado de férias com uma descrição<br/>
     * Não retorna as férias, mas sim o estado
     * 
     * @param descricao
     * @return
     */
    public List<RhEstadoFerias> pesquisaPorDescricao(String descricao)
    {
        TypedQuery<RhEstadoFerias> t = em.createQuery("SELECT ef FROM RhEstadoFerias ef "
                + "WHERE ef.descricao = :descricao", RhEstadoFerias.class).setParameter("descricao", descricao);

        List<RhEstadoFerias> resultado = t.getResultList();

        return resultado;
    }
    
}
