/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsPaciente;
import entidade.AdmsSubprocesso;
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
public class AdmsSubprocessoFacade extends AbstractFacade<AdmsSubprocesso>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsSubprocessoFacade()
    {
        super(AdmsSubprocesso.class);
    }
    
    
    public int findUltimoNumeroSubprocessoPaciente(AdmsPaciente paciente)
    {
        Query query = em.createQuery("SELECT DISTINCT subprocesso.numeroSubprocesso FROM AdmsSubprocesso subprocesso WHERE subprocesso.fkIdPaciente.pkIdPaciente = :idPaciente ORDER BY subprocesso.numeroSubprocesso DESC");
        
        query.setParameter("idPaciente", paciente.getPkIdPaciente());
        
        query.setMaxResults(1);
        
        List<Integer> numeroSubprocesso = query.getResultList();
        if(numeroSubprocesso.isEmpty()) return 0;
        return Integer.parseInt(""+numeroSubprocesso.get(0)) ;
    }
    
    public boolean subProcessoExiste(Integer subProcesso)
    {
        Query query = em.createQuery("SELECT DISTINCT subprocesso.numeroSubprocesso FROM AdmsSubprocesso subprocesso WHERE subprocesso.numeroSubprocesso = :subProcesso ORDER BY subprocesso.numeroSubprocesso DESC");
        
        query.setParameter("subProcesso", subProcesso.longValue());
        
        query.setMaxResults(1);
        
        List<Integer> numeroSubprocesso = query.getResultList();
        if(numeroSubprocesso.isEmpty()) return false;
        return true;
    }
    
    
}
