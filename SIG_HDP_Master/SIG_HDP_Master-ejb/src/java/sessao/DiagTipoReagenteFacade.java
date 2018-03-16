/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagTipoReagente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagTipoReagenteFacade extends AbstractFacade<DiagTipoReagente>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTipoReagenteFacade()
    {
        super(DiagTipoReagente.class);
    }
    
    public DiagTipoReagente findTipoReagenteById(int idTipoReagente)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTipoReagente d WHERE d.pkIdTipoReagente = :idTipoReagente", DiagTipoReagente.class).setParameter("idTipoReagente", idTipoReagente);

        return (DiagTipoReagente) typedQuery.getResultList().get(0);
    }
}
