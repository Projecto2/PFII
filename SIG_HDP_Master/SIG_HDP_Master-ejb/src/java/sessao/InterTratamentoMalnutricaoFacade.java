/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterTratamentoMalnutricao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author armindo
 */
@Stateless
public class InterTratamentoMalnutricaoFacade extends AbstractFacade<InterTratamentoMalnutricao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterTratamentoMalnutricaoFacade()
    {
        super(InterTratamentoMalnutricao.class);
    }
    
}
