/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhCurso;
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
public class RhCursoFacade extends AbstractFacade<RhCurso>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhCursoFacade ()
    {
        super(RhCurso.class);
    }

    @Override
    public List<RhCurso> findAll ()
    {
        return em.createQuery("SELECT r FROM RhCurso r ORDER BY r.descricao", RhCurso.class).getResultList();
    }
    
}
