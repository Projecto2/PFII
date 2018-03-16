/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhTipoContrato;
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
public class RhTipoContratoFacade extends AbstractFacade<RhTipoContrato>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhTipoContratoFacade ()
    {
        super(RhTipoContrato.class);
    }
    
    @Override
    public List<RhTipoContrato> findAll()
    {
          Query t = em.createQuery("SELECT t FROM RhTipoContrato t ORDER BY t.descricao", RhTipoContrato.class);

          return t.getResultList();
    }
}
