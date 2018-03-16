/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbConsultaHasTurgorPele;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbConsultaHasTurgorPeleFacade extends AbstractFacade<AmbConsultaHasTurgorPele>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbConsultaHasTurgorPeleFacade()
    {
        super(AmbConsultaHasTurgorPele.class);
    }
    
}
