/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhEstadoContrato;
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
public class RhEstadoContratoFacade extends AbstractFacade<RhEstadoContrato>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhEstadoContratoFacade ()
    {
        super(RhEstadoContrato.class);
    }
    
    @Override
    public List<RhEstadoContrato> findAll()
    {
          Query t = em.createQuery("SELECT e FROM RhEstadoContrato e ORDER BY e.descricao", RhEstadoContrato.class);

          return t.getResultList();
    }
    
    /**
     * Pesquisa um estado de contrato com uma determinada descrição
     * 
     * @param descricao
     * @return
     */
    public List<RhEstadoContrato> pesquisaPorDescricao(String descricao)
    {
        TypedQuery<RhEstadoContrato> t = em.createQuery("SELECT ec FROM RhEstadoContrato ec "
                + "WHERE ec.descricao = :descricao", RhEstadoContrato.class).setParameter("descricao", descricao);

        List<RhEstadoContrato> resultado = t.getResultList();

        return resultado;
    }
    
}
