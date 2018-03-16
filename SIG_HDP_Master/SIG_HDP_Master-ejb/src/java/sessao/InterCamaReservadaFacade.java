/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.InterCamaReservada;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author armindo
 */
@Stateless
public class InterCamaReservadaFacade extends AbstractFacade<InterCamaReservada>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterCamaReservadaFacade()
    {
        super(InterCamaReservada.class);
    }

    public List<InterCamaReservada> findCamaReservadaByPacienteId(Long pkIdPaciente)
    {
        Query qrs = em.createQuery("SELECT o FROM InterCamaReservada o WHERE o.fkIdPaciente.pkIdPaciente=:fkIdPaciente");
        qrs.setParameter("fkIdPaciente", pkIdPaciente);

        return qrs.getResultList();
    }

}
