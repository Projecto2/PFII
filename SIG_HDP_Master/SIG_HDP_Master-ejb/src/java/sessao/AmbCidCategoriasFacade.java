/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbCidCategorias;
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
public class AmbCidCategoriasFacade extends AbstractFacade<AmbCidCategorias>
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

    public AmbCidCategoriasFacade()
    {
        super(AmbCidCategorias.class);
    }

    public List<AmbCidCategorias> findAllOrderByNomeFromPerfilPreferencial(String perfilPreferencial, int idDoencasPrioridadesPreferencial, String pkIdAgrupamentos)
    {
        if (perfilPreferencial == null || perfilPreferencial.equals(Utils.Defs.CID_10) || idDoencasPrioridadesPreferencial == Utils.Defs.DOENCAS_PRIORIDADE_MINIMA)
            return findAllByPkIdAgrupamentosOrderByNome(pkIdAgrupamentos);

        AmbCidPerfis ambCidPerfilPreferencial = this.ambCidPerfisFacade.find(perfilPreferencial);
        
        List<AmbCidCategorias> listAmbCidCategorias = new ArrayList();
        
        for (AmbCidPerfis ambCidPerfis = ambCidPerfilPreferencial; ambCidPerfis != null; ambCidPerfis = ambCidPerfis.getFkIdPerfilPai())
            listAmbCidCategorias = this.ambCidPerfisDoencasFacade.findAllAmbCidCategoriasFromPerfil(listAmbCidCategorias, ambCidPerfis, idDoencasPrioridadesPreferencial, pkIdAgrupamentos);
        
        return listAmbCidCategorias;

    }
    
    public List<AmbCidCategorias> findAllByPkIdAgrupamentosOrderByNome(String pkIdAgrupamentos)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidCategorias a WHERE a.fkIdAgrupamentos.pkIdAgrupamentos = :pkIdAgrupamentos ORDER BY a.fkIdAgrupamentos.fkIdCapitulos.nome, a.fkIdAgrupamentos.nome, a.nome");
        q.setParameter("pkIdAgrupamentos", pkIdAgrupamentos);
        return q.getResultList();
    }
    
    public List<AmbCidCategorias> findAllOrderByNome()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidCategorias a ORDER BY a.fkIdAgrupamentos.fkIdCapitulos.nome, a.fkIdAgrupamentos.nome, a.nome");
        return q.getResultList();
    }
    
    public List<AmbCidCategorias> findAllOrderByPkIdCategorias()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidCategorias a ORDER BY a.pkIdCategorias");
        return q.getResultList();
    }
    
    /*
    public boolean isCategoriasTabelaEmpty()
    {
        List<AmbCidCategorias> listCategorias = this.findAll();
        return (listCategorias == null || listCategorias.isEmpty());
    }
    */
    
    public boolean existeRegisto(String pkIdCategorias)
    {
//System.err.println("0: AmbCidCategoriasFacade.existeRegisto()\tpkIdCategorias = " + 
   // pkIdCategorias + "\npkIdCategorias.length: " + pkIdCategorias.length());
        //AmbCidCategorias reg = this.find(pkIdCategorias);
        AmbCidCategorias reg = this.find(pkIdCategorias);
//System.err.println("1: AmbCidCategoriasFacade.existeRegisto()\treg: " + 
     //           (reg == null ? "null" : "not null"));
        return reg != null;
    }
    
    public String getCategoriaFromSubcategoria(String pk_id_subcategorias)
    {
        List<AmbCidCategorias> list = this.findAll();

        for (AmbCidCategorias categoria : list)
        {
            if (categoriaIncludes(categoria, pk_id_subcategorias))
            {
                return categoria.getPkIdCategorias();
            }
        }
        return null;
    }

    public static boolean categoriaIncludes(AmbCidCategorias categoria, String pk_id_subcategorias)
    {
        String pk_id_categorias = categoria.getPkIdCategorias();
        return pk_id_subcategorias.startsWith(pk_id_categorias);
    }
}
