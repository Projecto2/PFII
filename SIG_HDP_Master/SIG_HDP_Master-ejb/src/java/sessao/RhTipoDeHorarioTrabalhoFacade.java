/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhTipoDeHorarioTrabalho;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhTipoDeHorarioTrabalhoFacade extends AbstractFacade<RhTipoDeHorarioTrabalho>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhTipoDeHorarioTrabalhoFacade ()
    {
        super(RhTipoDeHorarioTrabalho.class);
    }

    @Override
    public List<RhTipoDeHorarioTrabalho> findAll ()
    {
        return em.createQuery("SELECT t FROM RhTipoDeHorarioTrabalho t ORDER BY t.descricao", RhTipoDeHorarioTrabalho.class).getResultList();
    }

}
