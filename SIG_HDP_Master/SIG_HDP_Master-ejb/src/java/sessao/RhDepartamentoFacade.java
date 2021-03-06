/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhDepartamento;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhDepartamentoFacade extends AbstractFacade<RhDepartamento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhDepartamentoFacade ()
    {
        super(RhDepartamento.class);
    }
    
    @Override
    public List<RhDepartamento> findAll()
    {
          Query t = em.createQuery("SELECT d FROM RhDepartamento d ORDER BY d.descricao", RhDepartamento.class);

          return t.getResultList();
    }
    
}
