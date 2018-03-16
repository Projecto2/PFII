/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.SegFuncionalidade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author aires
 */
@Stateless
public class SegFuncionalidadeFacade extends AbstractFacade<SegFuncionalidade>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SegFuncionalidadeFacade()
    {
        super(SegFuncionalidade.class);
    }

    public List<SegFuncionalidade> obterListaSegFuncionalidadeFilhos(int pkIdFuncionalidade)
    {
        System.err.println("1: SegFuncionalidadeFacade.obterListaSegFuncionalidadeFilhos()\tpkIdFuncionalidade: " + pkIdFuncionalidade);
        Query q = null;
        if (pkIdFuncionalidade != 0)
        {
            q = em.createQuery("SELECT a FROM SegFuncionalidade a WHERE a.fkIdFuncionalidadePai.pkIdFuncionalidade = :pkIdFuncionalidade ORDER BY a.nome");
            q.setParameter("pkIdFuncionalidade", pkIdFuncionalidade);
        }
        else
        {
            q = em.createQuery("SELECT a FROM SegFuncionalidade a WHERE a.fkIdFuncionalidadePai = null ORDER BY a.nome");
        }

        List<SegFuncionalidade> list = q.getResultList();
//System.err.println("1: SegFuncionalidadeFacade.obterListaSegFuncionalidadeFilhos()\tlist.size: " + list.size());                            
        return (list == null || list.isEmpty()) ? null : list;
    }

    public SegFuncionalidade findByNomeByPai(String nome, SegFuncionalidade segFuncionalidadePai)
    {
        Query q;
        int pkIdFuncionalidadePai;
        if (segFuncionalidadePai != null)
        {
            pkIdFuncionalidadePai = segFuncionalidadePai.getPkIdFuncionalidade();
            q = em.createQuery("SELECT a FROM SegFuncionalidade a WHERE a.fkIdFuncionalidadePai.pkIdFuncionalidade = :pkIdFuncionalidadePai AND a.nome = :nome");
            q.setParameter("pkIdFuncionalidadePai", pkIdFuncionalidadePai);
        }
        else
        {
            q = em.createQuery("SELECT a FROM SegFuncionalidade a WHERE a.fkIdFuncionalidadePai = null AND a.nome = :nome");
        }

        q.setParameter("nome", nome);
        List<SegFuncionalidade> list = q.getResultList();
//System.err.println("1: SegFuncionalidadeFacade.obterListaSegFuncionalidadeFilhos()\tlist.size: " + list.size());                            
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    public List<SegFuncionalidade> funcionalidadesPai()
    {
        Query result = em.createQuery("SELECT a FROM SegFuncionalidade a WHERE a.fkIdFuncionalidadePai = null");

        List<SegFuncionalidade> resultado = (List<SegFuncionalidade>) result.getResultList();

        if (resultado.isEmpty())
        {
            return null;
        }
        else
        {
            return resultado;
        }

    }

    public List<SegFuncionalidade> getFuncionalidadeMenu()
    {

        Query result = em.createQuery("SELECT a FROM SegFuncionalidade a WHERE a.fkIdFuncionalidadePai.pkIdFuncionalidade != null");

        List<SegFuncionalidade> resultado = (List<SegFuncionalidade>) result.getResultList();

        if (resultado.isEmpty())
        {
            return null;
        }
        else
        {
            return resultado;
        }
    }
    
     public List<SegFuncionalidade> getPermissasByRecursos(Integer idFuncionalidadePermissao) {
        TypedQuery<SegFuncionalidade> query;
        query = em.createQuery("SELECT p FROM SegFuncionalidade p WHERE p.fkIdFuncionalidadePai.pkIdFuncionalidade = :idFuncionalidadePermissao", SegFuncionalidade.class);
        //query = em.createQuery("SELECT p.nome FROM SegFuncionalidade p WHERE p.fkIdTipoFuncionalidade = 2 :idFuncionalidadePermissao", SegFuncionalidade.class);

        query.setParameter("idFuncionalidadePermissao", idFuncionalidadePermissao);

        List<SegFuncionalidade> results = query.getResultList();

        if (results.isEmpty()) {
            return new ArrayList<SegFuncionalidade>();
        } else {
            return results;
        }

    }
     
      public boolean existeRegisto(int pkIdFuncionalidade)
    {
        SegFuncionalidade reg = this.find(pkIdFuncionalidade);
        return reg != null;
    }

}
