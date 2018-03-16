/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AmbCidDoencasPrioridades;
import entidade.AmbCidPerfis;
import entidade.SegConta;
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
public class AmbCidPerfisFacade extends AbstractFacade<AmbCidPerfis>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @EJB
    private AmbCidPerfisDoencasFacade ambCidPerfisDoencasFacade;

    @EJB
    private AmbCidDoencasPrioridadesFacade ambCidDoencasPrioridadesFacade;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCidPerfisFacade()
    {
        super(AmbCidPerfis.class);
    }

     public List<String> findAllOrderByPkIdNome(SegConta segConta)
     {
     
        Query q = em.createQuery("SELECT a FROM AmbCidPerfis a ORDER BY a.pkIdNome");
        List<AmbCidPerfis> perfis = q.getResultList();
        if (perfis == null || perfis.isEmpty())
        {
            return null;
        }
        List<String> list = filtrarPerfisPrivados(segConta, perfis);
        //List<String> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list;
     }
    
    public List<String> findAllOrderByPkIdNome(SegConta segConta, Integer pkIdEspecialidade)
    {
        if (pkIdEspecialidade == null || pkIdEspecialidade.intValue() == 0) 
        {
            return findAllOrderByPkIdNome(segConta);
        }
        
        Query q = em.createQuery("SELECT a FROM AmbCidPerfis a WHERE a.fkIdEspecialidades.pkIdEspecialidade = :pkIdEspecialidade ORDER BY a.pkIdNome");
        q.setParameter("pkIdEspecialidade", pkIdEspecialidade);
        List<AmbCidPerfis> perfis = q.getResultList();
        if (perfis == null || perfis.isEmpty())
        {
            return null;
        }
        List<String> list = filtrarPerfisPrivados(segConta, perfis);
        //List<String> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list;
    }

    public List<String> filtrarPerfisPrivados(SegConta segConta, List<AmbCidPerfis> perfis)
    {
        List<String> list = new ArrayList();
        for (AmbCidPerfis perfil : perfis)
        {
            if (perfil.getFkIdTipo().getNome().equals("Publico"))
            {
                list.add(perfil.getPkIdNome());
                continue;
            }
            if (perfil.getFkIdDono().getPkIdConta() == segConta.getPkIdConta())
            {
                list.add(perfil.getPkIdNome());
            }
        }
        return list;
    }

    public List<AmbCidPerfis> filtrarAmbCidPerfisPrivados(SegConta segConta, List<AmbCidPerfis> perfis)
    {
        List<AmbCidPerfis> list = new ArrayList();
        for (AmbCidPerfis perfil : perfis)
        {
            if (perfil.getFkIdTipo().getNome().equals(Utils.Defs.PERFIL_TIPO_PUBLICO))
            {
                list.add(perfil);
                continue;
            }
            if (perfil.getFkIdDono().getPkIdConta() == segConta.getPkIdConta())
            {
                list.add(perfil);
            }
        }
        return list;
    }

    public List<AmbCidPerfis> findAllAmbCidPerfisOrderByPkIdNome(SegConta segConta, Integer pkIdEspecialidade)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidPerfis a WHERE a.fkIdEspecialidades.pkIdEspecialidade = :pkIdEspecialidade ORDER BY a.pkIdNome");
        q.setParameter("pkIdEspecialidade", pkIdEspecialidade);
        List<AmbCidPerfis> perfis = q.getResultList();
        if (perfis == null || perfis.isEmpty())
        {
            return null;
        }
        List<AmbCidPerfis> list = filtrarAmbCidPerfisPrivados(segConta, perfis);
        //List<String> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list;
    }

    public List<AmbCidPerfis> findAllAmbCidPerfisDescendentes(String pkIdNome)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidPerfis a WHERE a.fkIdPerfilPai.pkIdNome = :pkIdNome ORDER BY a.pkIdNome");
        q.setParameter("pkIdNome", pkIdNome);
        List<AmbCidPerfis> list = q.getResultList();

        return (list == null || list.isEmpty()) ? null : list;
    }

    public boolean existemAmbCidPerfisDescendentes(String pkIdNome)
    {
        return this.findAllAmbCidPerfisDescendentes(pkIdNome) != null;
    }

    public List<AmbCidDoencasPrioridades> obterHighestPrioritiesFromPerfil(String perfilPreferencial)
    {
//System.err.println("0: AmbCidPerfisFacade.obterHighestPrioritiesFromPerfil()\tperfilPreferencial: " + perfilPreferencial);
        int tmpHigherPriority, highestPriority = Utils.Defs.DOENCAS_PRIORIDADE_MINIMA;

        AmbCidPerfis ambCidPerfis = this.find(perfilPreferencial);
//System.err.println("1: AmbCidPerfisFacade.obterHighestPrioritiesFromPerfil()\tambCidPerfis: " + (ambCidPerfis == null ? "null" : ambCidPerfis.getPkIdNome()));
        for (AmbCidPerfis tmpAmbCidPerfis = ambCidPerfis; tmpAmbCidPerfis != null; tmpAmbCidPerfis = tmpAmbCidPerfis.getFkIdPerfilPai())
        {
//System.err.println("2: AmbCidPerfisFacade.obterHighestPrioritiesFromPerfil()\ttmpAmbCidPerfis: " + tmpAmbCidPerfis.getPkIdNome());            
            tmpHigherPriority = ambCidPerfisDoencasFacade.obterHighestPrioritiesFromPerfil(tmpAmbCidPerfis);
//System.err.println("3: AmbCidPerfisFacade.obterHighestPrioritiesFromPerfil()\ttmpHigherPriority: " + tmpHigherPriority);
            if (highestPriority > tmpHigherPriority)
            {
                highestPriority = tmpHigherPriority;
            }
        }
//System.err.println("3: AmbCidPerfisFacade.obterHighestPrioritiesFromPerfil()\thighestPriority: " + highestPriority);
        return this.ambCidDoencasPrioridadesFacade.obterHighestPriorities(highestPriority);
    }
    
    public List<AmbCidPerfis> obterListaAmbCidPerfisFilhos(String perfilPai)
    {
        if (perfilPai.equals(Utils.Defs.CID_10) || perfilPai.equals("Root"))
        {
//System.err.println("0: AmbCidPerfisFacade.obterListaAmbCidPerfisFilhos()\tperfilPai: " + perfilPai);         
            perfilPai = null;
        }
//System.err.println("1: AmbCidPerfisFacade.obterListaAmbCidPerfisFilhos()\tperfilPai: " + perfilPai);  
        Query q = null;
        if (perfilPai != null)
        {
            q = em.createQuery("SELECT a FROM AmbCidPerfis a WHERE a.fkIdPerfilPai.pkIdNome = :perfilPai ORDER BY a.pkIdNome");
            q.setParameter("perfilPai", perfilPai);
        }
        else
            q = em.createQuery("SELECT a FROM AmbCidPerfis a WHERE a.fkIdPerfilPai = null ORDER BY a.pkIdNome");
               
        List<AmbCidPerfis> list = q.getResultList();
//System.err.println("1: AmbCidPerfisFacade.obterListaAmbCidPerfisFilhos()\tlist.size: " + list.size());                            
        return (list == null || list.isEmpty()) ? null : list;
    }

    
    public void persist(Object object)
    {
        em.persist(object);
    }
}
