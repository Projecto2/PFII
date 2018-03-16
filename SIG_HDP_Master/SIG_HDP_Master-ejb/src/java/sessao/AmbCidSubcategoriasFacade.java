/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AmbCidPerfis;
import entidade.AmbCidSubcategorias;
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
public class AmbCidSubcategoriasFacade extends AbstractFacade<AmbCidSubcategorias>
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

    public AmbCidSubcategoriasFacade()
    {
        super(AmbCidSubcategorias.class);
    }

    public List<AmbCidSubcategorias> findAllOrderByNomeFromPerfilPreferencial(String perfilPreferencial, int idDoencasPrioridadesPreferencial, String pkIdCategorias)
    {
        if (perfilPreferencial == null || perfilPreferencial.equals(Utils.Defs.CID_10) || idDoencasPrioridadesPreferencial == Utils.Defs.DOENCAS_PRIORIDADE_MINIMA)
        {
            return findAllByFkIdCategoriasOrderByNome(pkIdCategorias);
        }

        AmbCidPerfis ambCidPerfilPreferencial = this.ambCidPerfisFacade.find(perfilPreferencial);

        List<AmbCidSubcategorias> listAmbCidSubcategorias = new ArrayList();

        for (AmbCidPerfis ambCidPerfis = ambCidPerfilPreferencial; ambCidPerfis != null; ambCidPerfis = ambCidPerfis.getFkIdPerfilPai())
        {
            listAmbCidSubcategorias = this.ambCidPerfisDoencasFacade.findAllAmbCidSubcategoriasFromPerfil(listAmbCidSubcategorias, ambCidPerfis, idDoencasPrioridadesPreferencial, pkIdCategorias);
        }

        return listAmbCidSubcategorias;

    }

    public List<AmbCidSubcategorias> findAllOrderByNome()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidSubcategorias a ORDER BY a.fkIdCategorias.fkIdAgrupamentos.fkIdCapitulos.nome, a.fkIdCategorias.fkIdAgrupamentos.nome, a.fkIdCategorias.nome, a.nome");
        return q.getResultList();
    }

    public List<AmbCidSubcategorias> findAllOrderById()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidSubcategorias a ORDER BY a.pkIdSubcategorias");
        return q.getResultList();
    }

    public List<AmbCidSubcategorias> findAllByFkIdCategoriasOrderByNome(String pkIdCategorias)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidSubcategorias a WHERE a.fkIdCategorias.pkIdCategorias = :pkIdCategorias ORDER BY a.nome");
        q.setParameter("pkIdCategorias", pkIdCategorias);
        return q.getResultList();
    }

    public AmbCidSubcategorias getAmbCidSubcategoriasFromNomeDoenca(String doenca)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidSubcategorias a WHERE a.nome = :doenca");
        q.setParameter("doenca", doenca);
        List<AmbCidSubcategorias> lista = q.getResultList();
        return (lista == null || lista.isEmpty()) ? null : lista.get(0);
    }

    public List<AmbCidSubcategorias> getListaAmbCidSubcategoriasDasDoencasSeleccionadas(List<String> listaNomeDoencasSeleccionadas)
    {
        List<AmbCidSubcategorias> lista = new ArrayList();
        AmbCidSubcategorias ambCidSubcategorias = null;
        
        for (String doenca : listaNomeDoencasSeleccionadas)
        {
            ambCidSubcategorias = getAmbCidSubcategoriasFromNomeDoenca(doenca);
            if (! lista.contains(ambCidSubcategorias))
                lista.add(ambCidSubcategorias);
        }
        return lista;
    }

    /*
    public boolean isSubcategoriasTabelaEmpty()
    {
        List<AmbCidSubcategorias> listSubcategorias = this.findAll();
        return (listSubcategorias == null || listSubcategorias.isEmpty());
    }
    */

    public boolean existeRegisto(String pkIdSubcategorias)
    {
//System.err.println("0: AmbCidSubcategoriasFacade.existeRegisto()\tpkIdCategorias = " + 
        //pkIdSubcategorias + "\npkIdCategorias.length: " + pkIdSubcategorias.length());
        AmbCidSubcategorias reg = this.find(pkIdSubcategorias);
//System.err.println("1: AmbCidSubcategoriasFacade.existeRegisto()\treg: " + 
        //     (reg == null ? "null" : "not null"));
        return reg != null;
    }

    public List<String> obterListaAmbCidSubcategoriasNomes(List<AmbCidSubcategorias> listAmbCidSubcategorias)
    {
        List<String> lista = new ArrayList();
        for (AmbCidSubcategorias ambCidSubcategorias : listAmbCidSubcategorias)
        {
            lista.add(ambCidSubcategorias.getNome());
        }
        return lista.isEmpty() ? null : lista;
    }
}
