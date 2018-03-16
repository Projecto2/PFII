/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhNivelAcademico;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhNivelAcademicoFacade extends AbstractFacade<RhNivelAcademico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhNivelAcademicoFacade ()
    {
        super(RhNivelAcademico.class);
    }
    
}
