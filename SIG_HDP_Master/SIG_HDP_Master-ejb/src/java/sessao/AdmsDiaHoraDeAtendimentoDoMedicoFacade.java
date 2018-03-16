/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsDiaHoraDeAtendimentoDoMedico;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gemix
 */
@Stateless
public class AdmsDiaHoraDeAtendimentoDoMedicoFacade extends AbstractFacade<AdmsDiaHoraDeAtendimentoDoMedico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsDiaHoraDeAtendimentoDoMedicoFacade()
    {
        super(AdmsDiaHoraDeAtendimentoDoMedico.class);
    }
    
    
    public List<AdmsDiaHoraDeAtendimentoDoMedico> findHorariosByMedico(Integer idMedico)
    {
        Query query = em.createQuery("SELECT horarios from AdmsDiaHoraDeAtendimentoDoMedico horarios WHERE horarios.fkIdMedico.pkIdFuncionario = :idMedico");

        query.setParameter("idMedico", idMedico.intValue());

        List<AdmsDiaHoraDeAtendimentoDoMedico> horarios = query.getResultList();
        
        return horarios;
    }
    
    
}
