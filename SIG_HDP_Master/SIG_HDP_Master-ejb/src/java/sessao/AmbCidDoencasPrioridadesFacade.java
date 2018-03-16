/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AmbCidDoencasPrioridades;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aires
 */
@Stateless
public class AmbCidDoencasPrioridadesFacade extends AbstractFacade<AmbCidDoencasPrioridades>
{

    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCidDoencasPrioridadesFacade()
    {
        super(AmbCidDoencasPrioridades.class);
    }

    public List<AmbCidDoencasPrioridades> findAllOrderBYpkIdDoencasPrioridades()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidDoencasPrioridades a ORDER BY a.pkIdDoencasPrioridades");
        return q.getResultList();
    }

    public List<AmbCidDoencasPrioridades> findAllOrderBYpkIdDoencasPrioridadesFromPerfilPreferencial(String perfilPreferencial)
    {
        Query q = null;
        if (perfilPreferencial == null)
        {
            int lowerPriority = Utils.Defs.DOENCAS_PRIORIDADE_MINIMA;
            q = em.createQuery("SELECT a FROM AmbCidDoencasPrioridades a WHERE a.pkIdDoencasPrioridades = :lowerPriority ORDER BY a.pkIdDoencasPrioridades");
            q.setParameter("lowerPriority", lowerPriority);
            return q.getResultList();
        }
        return this.ambCidPerfisFacade.obterHighestPrioritiesFromPerfil(perfilPreferencial);
    }

    public List<AmbCidDoencasPrioridades> obterHighestPriorities(int highestPriority)
    {
        List<AmbCidDoencasPrioridades> list = this.findAllOrderBYpkIdDoencasPrioridades();
        List<AmbCidDoencasPrioridades> resultList = new ArrayList();
        
        for (AmbCidDoencasPrioridades dp : list)
        {
            if (dp.getPkIdDoencasPrioridades() >= highestPriority)
                resultList.add(dp);
        }
//System.err.println("0: AmbCidDoencasPrioridadesFacade.obterHighestPriorities()\thighestPriority: " + highestPriority);
        return resultList;
    }

    public List<AmbCidDoencasPrioridades> findAll(int prioridade)
    {
//System.err.println("0: AmbCidDoencasPrioridadesFacade.findAll()\tprioridade: " + prioridade);
        prioridade = ((prioridade == 3) ? 2 : prioridade);
        Query q = em.createQuery("SELECT a FROM AmbCidDoencasPrioridades a WHERE a.pkIdDoencasPrioridades <= :prioridade ORDER BY a.pkIdDoencasPrioridades");
//System.err.println("1: AmbCidDoencasPrioridadesFacade.findAll()\tprioridade: " + prioridade);              
        q.setParameter("prioridade", prioridade);
//System.err.println("2: AmbCidDoencasPrioridadesFacade.findAll()\tprioridade: " + prioridade);              
        List<AmbCidDoencasPrioridades> doencasPrioridades = q.getResultList();
//System.err.println("3: AmbCidDoencasPrioridadesFacade.findAll()\tdoencasPrioridades: " + (doencasPrioridades == null ? "null" : "not null"));      
//if (doencasPrioridades != null)
    //System.err.println("4: AmbCidDoencasPrioridadesFacade.findAll()\tdoencasPrioridades.size: " + doencasPrioridades.size());      
        return doencasPrioridades;
    }
    
}
