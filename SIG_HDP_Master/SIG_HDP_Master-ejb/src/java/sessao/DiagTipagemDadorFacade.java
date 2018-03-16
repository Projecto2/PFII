/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagCandidatoDador;
import entidade.DiagTipagemDador;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagTipagemDadorFacade extends AbstractFacade<DiagTipagemDador>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTipagemDadorFacade()
    {
        super(DiagTipagemDador.class);
    }
    
    public DiagTipagemDador findTipagemDador(DiagCandidatoDador candidatoDador)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTipagemDador d WHERE d.fkIdCandidatoDador.pkIdCandidatoDador = :idCandidatoDador", DiagTipagemDador.class).setParameter("idCandidatoDador", candidatoDador.getPkIdCandidatoDador());

        if (typedQuery.getResultList().isEmpty())
        {
            return null;
        }

        return (DiagTipagemDador) typedQuery.getResultList().get(0);
    }
}
