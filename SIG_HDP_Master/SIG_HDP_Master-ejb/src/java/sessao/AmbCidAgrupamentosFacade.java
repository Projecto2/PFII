/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AmbCidAgrupamentos;
import entidade.AmbCidPerfis;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbCidAgrupamentosFacade extends AbstractFacade<AmbCidAgrupamentos>
{
    @EJB
    private AmbCidPerfisDoencasFacade ambCidPerfisDoencasFacade;
    
    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    
    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCidAgrupamentosFacade()
    {
        super(AmbCidAgrupamentos.class);
    }
    
    public List<AmbCidAgrupamentos> findAllOrderByNomeFromPerfilPreferencial(String perfilPreferencial, int idDoencasPrioridadesPreferencial, String pkIdCapitulos)
    {
        if (perfilPreferencial == null || perfilPreferencial.equals(Utils.Defs.CID_10) || idDoencasPrioridadesPreferencial == Utils.Defs.DOENCAS_PRIORIDADE_MINIMA)
            return findAllByPkIdCapitulosOrderByNome(pkIdCapitulos);

        AmbCidPerfis ambCidPerfilPreferencial = this.ambCidPerfisFacade.find(perfilPreferencial);
        
        List<AmbCidAgrupamentos> listAmbCidAgrupamentos = new ArrayList();
        
        for (AmbCidPerfis ambCidPerfis = ambCidPerfilPreferencial; ambCidPerfis != null; ambCidPerfis = ambCidPerfis.getFkIdPerfilPai())
            listAmbCidAgrupamentos = this.ambCidPerfisDoencasFacade.findAllAmbCidAgrupamentosFromPerfil(listAmbCidAgrupamentos, ambCidPerfis, idDoencasPrioridadesPreferencial, pkIdCapitulos);
        
        return listAmbCidAgrupamentos;

    }
    
    public List<AmbCidAgrupamentos> findAllByPkIdCapitulosOrderByNome(String pkIdCapitulos)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidAgrupamentos a WHERE a.fkIdCapitulos.pkIdCapitulos = :pkIdCapitulos ORDER BY a.nome");
        q.setParameter("pkIdCapitulos", pkIdCapitulos);
        return q.getResultList();
    }

    public List<AmbCidAgrupamentos> findAllOrderByNome()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidAgrupamentos a ORDER BY a.fkIdCapitulos.nome, a.nome");
        return q.getResultList();
    }

    public List<AmbCidAgrupamentos> findAllOrderById()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidAgrupamentos a ORDER BY a.pkIdAgrupamentos");
        return q.getResultList();
    }

    /*
    public boolean isAgrupamentosTabelaEmpty()
    {
        List<AmbCidAgrupamentos> listAgrupamentos = this.findAll();
        return (listAgrupamentos == null || listAgrupamentos.isEmpty());
    }
    */

    public boolean existeRegisto(String pkIdAgrupamentos)
    {
        //System.err.println("0: AmbCidAgrupamentosFacade.existeRegisto()\tpkIdAgrupamentos = "
            //+ pkIdAgrupamentos + "\npkIdAgrupamentos.length: " + pkIdAgrupamentos.length());
        //AmbCidAgrupamentos reg = this.find(pkIdAgrupamentos);
        AmbCidAgrupamentos reg = this.find(pkIdAgrupamentos);
        //System.err.println("1: AmbCidAgrupamentosFacade.existeRegisto()\treg: "
            //+ (reg == null ? "null" : "not null"));
        return reg != null;
    }

    public String getAgrupamentoFromCategoria(String pk_id_categorias)
    {
        List<AmbCidAgrupamentos> list = this.findAll();

        for (AmbCidAgrupamentos agrupamento : list)
        {
            if (agrupamentoIncludes(agrupamento, pk_id_categorias))
            {
                return agrupamento.getPkIdAgrupamentos();
            }
        }
        return null;
    }

    public static boolean agrupamentoIncludes(AmbCidAgrupamentos agrupamento, String pk_id_categorias)
    {
        String pkIdAgrupamentos = agrupamento.getPkIdAgrupamentos();

        String lowCategoriaFromAgrupamento = getLowCategoria(pkIdAgrupamentos);
        String highCategoriaFromAgrupamento = getHighCategoria(pkIdAgrupamentos);

        return rangeOfCapitulosIncludes(lowCategoriaFromAgrupamento, highCategoriaFromAgrupamento, pk_id_categorias);
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
        return lowRange.compareTo(target) <= 0 && highRange.compareTo(target) >= 0;
    }

}
