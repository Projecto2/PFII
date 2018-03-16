/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AmbCidAgrupamentos;
import entidade.AmbCidCapitulos;
import entidade.AmbCidCategorias;
import entidade.AmbCidPerfis;
import entidade.AmbCidPerfisDoencas;
import entidade.AmbCidSubcategorias;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aires
 */
@Stateless
public class AmbCidPerfisDoencasFacade extends AbstractFacade<AmbCidPerfisDoencas>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCidPerfisDoencasFacade()
    {
        super(AmbCidPerfisDoencas.class);
    }

    public List<AmbCidSubcategorias> findAllAmbCidSubcategoriasFromPerfil(List<AmbCidSubcategorias> listAmbCidSubcategorias, AmbCidPerfis ambCidPerfis, int idDoencasPrioridadesPreferencial, String pkIdCategorias)
    {
        String pkIdNome = ambCidPerfis.getPkIdNome();
        Query q = em.createQuery("SELECT a.fkIdSubcategorias FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :pkIdNome AND a.fkIdPrioridades.pkIdDoencasPrioridades <= :idDoencasPrioridadesPreferencial AND a.fkIdSubcategorias.fkIdCategorias.pkIdCategorias = :pkIdCategorias");
        q.setParameter("pkIdNome", pkIdNome);
        q.setParameter("idDoencasPrioridadesPreferencial", idDoencasPrioridadesPreferencial);
        q.setParameter("pkIdCategorias", pkIdCategorias);
        List<AmbCidSubcategorias> listQuery = q.getResultList();
        if ((listQuery == null || listQuery.isEmpty()))
            return listAmbCidSubcategorias;

        for (AmbCidSubcategorias a : listQuery)
        {
            if (!listAmbCidSubcategorias.contains(a))
                listAmbCidSubcategorias.add(a);
        }
        return listAmbCidSubcategorias;
    }

    public List<AmbCidCategorias> findAllAmbCidCategoriasFromPerfil(List<AmbCidCategorias> listAmbCidCategorias, AmbCidPerfis ambCidPerfis, int idDoencasPrioridadesPreferencial, String pkIdAgrupamentos)
    {
        String pkIdNome = ambCidPerfis.getPkIdNome();
        Query q = em.createQuery("SELECT a.fkIdSubcategorias.fkIdCategorias FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :pkIdNome AND a.fkIdPrioridades.pkIdDoencasPrioridades <= :idDoencasPrioridadesPreferencial AND a.fkIdSubcategorias.fkIdCategorias.fkIdAgrupamentos.pkIdAgrupamentos = :pkIdAgrupamentos");
        q.setParameter("pkIdNome", pkIdNome);
        q.setParameter("idDoencasPrioridadesPreferencial", idDoencasPrioridadesPreferencial);
        q.setParameter("pkIdAgrupamentos", pkIdAgrupamentos);
        List<AmbCidCategorias> listQuery = q.getResultList();
        if ((listQuery == null || listQuery.isEmpty()))
            return listAmbCidCategorias;

        for (AmbCidCategorias a : listQuery)
        {
            if (!listAmbCidCategorias.contains(a))
                listAmbCidCategorias.add(a);
        }
        return listAmbCidCategorias;
    }

    public List<AmbCidAgrupamentos> findAllAmbCidAgrupamentosFromPerfil(List<AmbCidAgrupamentos> listAmbCidAgrupamentos, AmbCidPerfis ambCidPerfis, int idDoencasPrioridadesPreferencial, String pkIdCapitulos)
    {
        String pkIdNome = ambCidPerfis.getPkIdNome();
        Query q = em.createQuery("SELECT a.fkIdSubcategorias.fkIdCategorias.fkIdAgrupamentos FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :pkIdNome AND a.fkIdPrioridades.pkIdDoencasPrioridades <= :idDoencasPrioridadesPreferencial AND a.fkIdSubcategorias.fkIdCategorias.fkIdAgrupamentos.fkIdCapitulos.pkIdCapitulos = :pkIdCapitulos");
        q.setParameter("pkIdNome", pkIdNome);
        q.setParameter("idDoencasPrioridadesPreferencial", idDoencasPrioridadesPreferencial);
        q.setParameter("pkIdCapitulos", pkIdCapitulos);
        List<AmbCidAgrupamentos> listQuery = q.getResultList();
        if ((listQuery == null || listQuery.isEmpty()))
            return listAmbCidAgrupamentos;

        for (AmbCidAgrupamentos a : listQuery)
        {
            if (!listAmbCidAgrupamentos.contains(a))
                listAmbCidAgrupamentos.add(a);
        }
        return listAmbCidAgrupamentos;
    }

    public List<AmbCidCapitulos> findAllAmbCidCapitulosFromPerfil(List<AmbCidCapitulos> listAmbCidCapitulos, AmbCidPerfis ambCidPerfis, int idDoencasPrioridadesPreferencial)
    {
        String pkIdNome = ambCidPerfis.getPkIdNome();
        Query q = em.createQuery("SELECT a.fkIdSubcategorias.fkIdCategorias.fkIdAgrupamentos.fkIdCapitulos FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :pkIdNome AND a.fkIdPrioridades.pkIdDoencasPrioridades <= :idDoencasPrioridadesPreferencial");
        q.setParameter("pkIdNome", pkIdNome);
        q.setParameter("idDoencasPrioridadesPreferencial", idDoencasPrioridadesPreferencial);
        List<AmbCidCapitulos> listQuery = q.getResultList();
        if ((listQuery == null || listQuery.isEmpty()))
            return listAmbCidCapitulos;

        for (AmbCidCapitulos a : listQuery)
        {
            if (!listAmbCidCapitulos.contains(a))
                listAmbCidCapitulos.add(a);
        }
        return listAmbCidCapitulos;
    }

    public List<AmbCidPerfisDoencas> findAllByDoencasFromPerfil(String pkIdNome)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :pkIdNome ORDER BY a.fkIdPrioridades.pkIdDoencasPrioridades, a.fkIdSubcategorias.nome");
        q.setParameter("pkIdNome", pkIdNome);
        List<AmbCidPerfisDoencas> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list;
    }

    public List<AmbCidSubcategorias> findAllHighestPriorityOrderByNome(AmbCidPerfis ambCidPerfis, String pkIdCategorias)
    {
        List<AmbCidSubcategorias> listCurrent = findAllByAmbCidPerfisPkIdNome(ambCidPerfis, pkIdCategorias);
        if (listCurrent == null)
            listCurrent = new ArrayList();
        List<AmbCidSubcategorias> listPerfilPai = null;

        for (AmbCidPerfis perfil = ambCidPerfis.getFkIdPerfilPai(); perfil != null; perfil = perfil.getFkIdPerfilPai())
        {
            listPerfilPai = findAllHighestPriorityByAmbCidPerfisPkIdNome(perfil, pkIdCategorias);
            if ((listPerfilPai != null) && (listPerfilPai.isEmpty() == false))
                listCurrent.addAll(listPerfilPai);
        }
        return listCurrent;
    }

    public List<AmbCidSubcategorias> findAllByAmbCidPerfisPkIdNome(AmbCidPerfis ambCidPerfis, String pkIdCategorias)
    {
        String ambCidPerfisPkIdNome = ambCidPerfis.getPkIdNome();
        Query q = em.createQuery("SELECT a.fkIdSubcategorias FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :ambCidPerfisPkIdNome AND a.fkIdSubcategorias.fkIdCategorias.pkIdCategorias = :pkIdCategorias ORDER BY a.fkIdPrioridades.pkIdDoencasPrioridades, a.fkIdSubcategorias.nome");
        q.setParameter("ambCidPerfisPkIdNome", ambCidPerfisPkIdNome);
        q.setParameter("pkIdCategorias", pkIdCategorias);

        List<AmbCidSubcategorias> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list;
    }
    
    public List<AmbCidSubcategorias> findAllHighestPriorityByAmbCidPerfisPkIdNome(AmbCidPerfis ambCidPerfis, String pkIdCategorias)
    {
        String ambCidPerfisPkIdNome = ambCidPerfis.getPkIdNome();
        Query q = em.createQuery("SELECT a.fkIdSubcategorias FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :ambCidPerfisPkIdNome AND a.fkIdSubcategorias.fkIdCategorias.pkIdCategorias = :pkIdCategorias AND a.fkIdPrioridades.pkIdDoencasPrioridades = 1 ORDER BY a.fkIdPrioridades.pkIdDoencasPrioridades, a.fkIdSubcategorias.nome");
        q.setParameter("ambCidPerfisPkIdNome", ambCidPerfisPkIdNome);
        q.setParameter("pkIdCategorias", pkIdCategorias);

        List<AmbCidSubcategorias> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list;
    }

    public int findHigestPrioridade(AmbCidPerfis ambCidPerfis, String pkIdSubcategorias)
    {
        if (ambCidPerfis == null)
            return Utils.Defs.DOENCAS_PRIORIDADE_MINIMA;
        int prioridade = findHigestPrioridadeDeUmPerfil(ambCidPerfis, pkIdSubcategorias);
        if (prioridade < Utils.Defs.DOENCAS_PRIORIDADE_MINIMA)
            return prioridade;

        for (AmbCidPerfis perfil = ambCidPerfis.getFkIdPerfilPai(); perfil != null; perfil = perfil.getFkIdPerfilPai())
        {
            prioridade = findHigestPrioridadeDeUmPerfil(perfil, pkIdSubcategorias);
            if (prioridade < Utils.Defs.DOENCAS_PRIORIDADE_MINIMA)
                return prioridade;
        }
        return Utils.Defs.DOENCAS_PRIORIDADE_MINIMA;
    }

    public int findHigestPrioridadeDeUmPerfil(AmbCidPerfis ambCidPerfis, String pkIdSubcategorias)
    {
        String ambCidPerfisPkIdNome = ambCidPerfis.getPkIdNome();
        Query q = em.createQuery("SELECT a.fkIdPrioridades.pkIdDoencasPrioridades FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :ambCidPerfisPkIdNome AND a.fkIdSubcategorias.pkIdSubcategorias = :pkIdSubcategorias");
        q.setParameter("ambCidPerfisPkIdNome", ambCidPerfisPkIdNome);
        q.setParameter("pkIdSubcategorias", pkIdSubcategorias);

        List<Integer> list = q.getResultList();

        return (list == null || list.isEmpty()) ? 3 : list.get(0);
    }

    public AmbCidPerfisDoencas find(String perfilPkIdNome, String pkIdSubcategorias)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :perfilPkIdNome AND a.fkIdSubcategorias.pkIdSubcategorias = :pkIdSubcategorias");
        q.setParameter("perfilPkIdNome", perfilPkIdNome);
        q.setParameter("pkIdSubcategorias", pkIdSubcategorias);

        List<AmbCidPerfisDoencas> list = q.getResultList();

        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    public List<AmbCidPerfisDoencas> findByFk_id_perfil(String perfilPkIdNome)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :perfilPkIdNome");
        q.setParameter("perfilPkIdNome", perfilPkIdNome);

        List<AmbCidPerfisDoencas> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list;
    }

    public int obterHighestPrioritiesFromPerfil(AmbCidPerfis ambCidPerfis)
    {
        //System.err.println("0: AmbCidPerfisDoencasFacade.obterHighestPrioritiesFromPerfil()\tambCidPerfis: " + (ambCidPerfis == null ? "null" : ambCidPerfis.getPkIdNome()));
        String ambCidPerfisPkIdNome = ambCidPerfis.getPkIdNome();
        Query q = em.createQuery("SELECT a.fkIdPrioridades.pkIdDoencasPrioridades FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :ambCidPerfisPkIdNome");
        //System.err.println("1: AmbCidPerfisDoencasFacade.obterHighestPrioritiesFromPerfil()\tambCidPerfis: " + (ambCidPerfis == null ? "null" : ambCidPerfis.getPkIdNome()));
        q.setParameter("ambCidPerfisPkIdNome", ambCidPerfisPkIdNome);
        //System.err.println("2: AmbCidPerfisDoencasFacade.obterHighestPrioritiesFromPerfil()\tambCidPerfis: " + (ambCidPerfis == null ? "null" : ambCidPerfis.getPkIdNome()));
        List<Integer> list = q.getResultList();
        int highest = Utils.Defs.DOENCAS_PRIORIDADE_MINIMA;
        for (Integer value : list)
        {
            //System.err.println("3: AmbCidPerfisDoencasFacade.obterHighestPrioritiesFromPerfil()\tvalue: " + value);
            if (highest > value.intValue())
                highest = value;
        }
        //System.err.println("4: AmbCidPerfisDoencasFacade.obterHighestPrioritiesFromPerfil()\tambCidPerfis: " + (ambCidPerfis == null ? "null" : ambCidPerfis.getPkIdNome()));
        //System.err.println("5: AmbCidPerfisDoencasFacade.obterHighestPrioritiesFromPerfil()\tlist: " + ((list == null || list.isEmpty()) ? "null" : "not null"));
        //System.err.println("6: AmbCidPerfisDoencasFacade.obterHighestPrioritiesFromPerfil()\tlist.size(): " + list.size());
        return highest;
    }

    public boolean temDoencasRegistadas(String perfilNome)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :perfilNome");
//System.err.println("0: AmbCidPerfisDoencasFacade.temDoencasRegistadas()\tperfilNome: " + perfilNome);
        q.setParameter("perfilNome", perfilNome);
//System.err.println("1: AmbCidPerfisDoencasFacade.temDoencasRegistadas()");
        List<Integer> list = q.getResultList();
//System.err.println("2: AmbCidPerfisDoencasFacade.temDoencasRegistadas()\tlist: " + (list == null ? "null" : "not null"));        
//System.err.println("3: AmbCidPerfisDoencasFacade.temDoencasRegistadas()\tlist.size: " + list.size());
        return (list == null || list.size() == 0) ? false : true;
    }
    
    
    public static int prioridadeMaxima(List<AmbCidPerfisDoencas> list)
    {
        int prioridadeMax = Utils.Defs.DOENCAS_PRIORIDADE_MINIMA;
        int prioridade;
        for (AmbCidPerfisDoencas p : list)
        {
            prioridade = p.getFkIdPrioridades().getPkIdDoencasPrioridades();
            if (prioridade < prioridadeMax)
                prioridadeMax = prioridade;
        }
        return prioridadeMax;
    }
    
    public int obterPrioridadeMaxima(String perfilNome)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidPerfisDoencas a WHERE a.fkIdPerfil.pkIdNome = :perfilNome");
System.err.println("0: AmbCidPerfisDoencasFacade.obterPrioridadeMaxima()\tperfilNome: " + perfilNome);
        q.setParameter("perfilNome", perfilNome);
System.err.println("1: AmbCidPerfisDoencasFacade.obterPrioridadeMaxima()");
        List<AmbCidPerfisDoencas> list = q.getResultList();
System.err.println("2: AmbCidPerfisDoencasFacade.obterPrioridadeMaxima()\tlist: " + (list == null ? "null" : "not null"));        
        if (list == null || list.size() == 0)
        {
System.err.println("2: AmbCidPerfisDoencasFacade.obterPrioridadeMaxima()\tlist: " + (list == null ? "null" : "not null"));            
            return Utils.Defs.DOENCAS_PRIORIDADE_MINIMA;
        }
System.err.println("3: AmbCidPerfisDoencasFacade.obterPrioridadeMaxima()\tlist.size: " + list.size());                    
        return prioridadeMaxima(list);
        /*
if (list != null)
{
System.err.println("3: AmbCidPerfisDoencasFacade.obterPrioridadeMaxima()\tlist.size: " + list.size());
System.err.println("4: AmbCidPerfisDoencasFacade.obterPrioridadeMaxima()\tlist(0): " + list.get(0).intValue());
}
        return (list == null || list.size() == 0) ? utils.Defs.DOENCAS_PRIORIDADE_MINIMA : list.get(0);
            */
    }

}
