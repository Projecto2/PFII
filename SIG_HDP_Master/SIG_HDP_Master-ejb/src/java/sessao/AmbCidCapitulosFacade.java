/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AmbCidCapitulos;
import entidade.AmbCidPerfis;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbCidCapitulosFacade extends AbstractFacade<AmbCidCapitulos>
{
    @EJB
    private AmbCidPerfisDoencasFacade ambCidPerfisDoencasFacade;
    
    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;

    @EJB
    private AmbCidUpdatesFacade ambCidUpdatesFacade;

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCidCapitulosFacade()
    {
        super(AmbCidCapitulos.class);
    }

    public List<AmbCidCapitulos> findAllOrderByNome()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidCapitulos a ORDER BY a.nome");

        return q.getResultList();
    }

    public List<AmbCidCapitulos> findAllOrderById()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidCapitulos a ORDER BY a.pkIdCapitulos");

        return q.getResultList();
    }

    public List<AmbCidCapitulos> findAllOrderByNomeFromPerfilPreferencial(String perfilPreferencial, int idDoencasPrioridadesPreferencial)
    {
        if (perfilPreferencial == null || perfilPreferencial.equals(Utils.Defs.CID_10) || idDoencasPrioridadesPreferencial == Utils.Defs.DOENCAS_PRIORIDADE_MINIMA)
            return findAllOrderById();

        AmbCidPerfis ambCidPerfilPreferencial = this.ambCidPerfisFacade.find(perfilPreferencial);
        
        List<AmbCidCapitulos> listAmbCidCapitulos = new ArrayList();
        
        for (AmbCidPerfis ambCidPerfis = ambCidPerfilPreferencial; ambCidPerfis != null; ambCidPerfis = ambCidPerfis.getFkIdPerfilPai())
            listAmbCidCapitulos = this.ambCidPerfisDoencasFacade.findAllAmbCidCapitulosFromPerfil(listAmbCidCapitulos, ambCidPerfis, idDoencasPrioridadesPreferencial);
        
        return listAmbCidCapitulos;
    }
    
    public AmbCidCapitulos findByPkIdCapitulos(String pkIdCapitulos)
    {
        TypedQuery<AmbCidCapitulos> q = em.createQuery("SELECT p FROM AmbCidCapitulos p WHERE p.pkIdCapitulos = :pkIdCapitulos",
                AmbCidCapitulos.class).setParameter("pkIdCapitulos", pkIdCapitulos);
        List<AmbCidCapitulos> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
    
    /*
    public boolean isCapitulosTabelaEmpty()
    {
        List<AmbCidCapitulos> listCapitulos = this.findAll();
        return (listCapitulos == null || listCapitulos.isEmpty());
    }
    */

    public boolean existeRegisto(String pkIdCapitulos)
    {
//System.err.println("0: AmbCidCapitulosFacade.existeRegisto()\tpkIdCapitulos = " + 
   // pkIdCapitulos + "\npkIdCapitulos.length: " + pkIdCapitulos.length());
        //AmbCidCapitulos reg = this.find(pkIdCapitulos);
        AmbCidCapitulos reg = this.findByPkIdCapitulos(pkIdCapitulos);
//System.err.println("1: AmbCidCapitulosFacade.existeRegisto()\treg: " + 
     //           (reg == null ? "null" : "not null"));
        return reg != null;
    }

    public void apagarTodosRegistosTabelaAmbCidCapitulos()
    {
        List<AmbCidCapitulos> list = this.findAll();
        if (list == null || list.isEmpty())
        {
            return;
        }
        for (AmbCidCapitulos reg : list)
        {
            this.remove(reg);
        }
    }

    public String getCapituloFromAgrupamento(String pk_id_agrupamentos)
    {
        List<AmbCidCapitulos> list = this.findAll();
        
        for (AmbCidCapitulos capitulo : list)
        {
           if (capituloIncludes(capitulo, pk_id_agrupamentos))
               return capitulo.getPkIdCapitulos();
        }
        return null;
    }
    
    public static boolean capituloIncludes(AmbCidCapitulos capitulo, String pk_id_agrupamentos)
    {
        String pkIdCapitulos = capitulo.getPkIdCapitulos();
        
        String lowCategoriaFromCapitulo = getLowCategoria(pkIdCapitulos);
        String highCategoriaFromCapitulo = getHighCategoria(pkIdCapitulos);
        
        String lowCategoriaFromAgrupamento = getLowCategoria(pk_id_agrupamentos);
        String highCategoriaFromAgrupamento = getHighCategoria(pk_id_agrupamentos);
        
        return rangeOfCapitulosIncludes(lowCategoriaFromCapitulo, highCategoriaFromCapitulo, lowCategoriaFromAgrupamento) 
                        &
               rangeOfCapitulosIncludes(lowCategoriaFromCapitulo, highCategoriaFromCapitulo, highCategoriaFromAgrupamento);
    }
    
    public static String getLowCategoria(String range7)
    {
           return range7.substring(0, 3);
    }
    
    public static String getHighCategoria(String range7)
    {
            return range7.substring(4, 7);
    }
    
    public static boolean rangeOfCapitulosIncludes(String lowRange, String highRange, String target)
    {
 System.err.println("Resultado: " + (lowRange.compareTo(target) <= 0 && highRange.compareTo(target) >= 0));
        return lowRange.compareTo(target) <= 0 && highRange.compareTo(target) >= 0;
    }
    
}
