/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiMedicoHasEscala;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author helga
 */
@Stateless
public class SupiMedicoHasEscalaFacade extends AbstractFacade<SupiMedicoHasEscala> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiMedicoHasEscalaFacade() {
        super(SupiMedicoHasEscala.class);
    }
    
      public List<SupiMedicoHasEscala> findMedicosDaEscala(Integer idEscala, Date dia) {
                
        TypedQuery<SupiMedicoHasEscala> query = em.createQuery("SELECT supHas FROM SupiMedicoHasEscala supHas "
                + "WHERE supHas.fkIdEscalaMedico.pkIdEscalaMedico = :idEscala AND supHas.data = :dia ORDER BY supHas.data", SupiMedicoHasEscala.class)
                .setParameter("idEscala", idEscala).setParameter("dia", dia);

        return query.getResultList();
    }
      
      public SupiMedicoHasEscala findConsultorioMedico(Integer idMedicoHasEscala)
    {
        Query query = em.createQuery("SELECT s FROM SupiMedicoHasEscala s "
                + "WHERE s.pkIdMedicoEscala = :idMedicoHasEscala", SupiMedicoHasEscala.class)
                .setParameter("idMedicoHasEscala", idMedicoHasEscala);
        
        return (SupiMedicoHasEscala) (query.getResultList().isEmpty() ? null : query.getResultList().get(0));
    }
    
}
