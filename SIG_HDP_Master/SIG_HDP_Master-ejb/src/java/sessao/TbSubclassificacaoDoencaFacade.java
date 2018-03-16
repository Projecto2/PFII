/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.TbSubclassificacaoDoenca;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author adelino
 */
@Stateless
public class TbSubclassificacaoDoencaFacade extends AbstractFacade<TbSubclassificacaoDoenca>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TbSubclassificacaoDoencaFacade()
    {
        super(TbSubclassificacaoDoenca.class);
    }

    public boolean isSubClassificacaoDoencaTabelaEmpty()
    {
        return (this.findAll() == null || this.findAll().isEmpty());
    }

    public boolean existeRegisto(int pkSubClassificacaoDoenca)
    {
        TbSubclassificacaoDoenca reg = this.find(pkSubClassificacaoDoenca);
        return reg != null;
    }

    public List<TbSubclassificacaoDoenca> findSubclassificacaoDoenca(TbSubclassificacaoDoenca subclassificacaoDoenca)
    {
        String query = constroiQuery(subclassificacaoDoenca);

        TypedQuery<TbSubclassificacaoDoenca> t = em.createQuery(query, TbSubclassificacaoDoenca.class);

        if (subclassificacaoDoenca.getDescricao() != null && !(subclassificacaoDoenca.getDescricao().isEmpty()))
        {
            t.setParameter("descricao", subclassificacaoDoenca.getDescricao() + "%");
        }

        List<TbSubclassificacaoDoenca> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(TbSubclassificacaoDoenca subclassificacaoDoenca)
    {
        String query = "SELECT a FROM TbSubclassificacaoDoenca a WHERE a.pkSubclassificacaoDoenca = a.pkSubclassificacaoDoenca";

        if (subclassificacaoDoenca.getDescricao() != null && !(subclassificacaoDoenca.getDescricao().isEmpty()))
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }

    public String constroiQueryOrdenar()
    {
        String query = "SELECT t FROM TbSubclassificacaoDoenca t ";
        query += " ORDER BY t.fkClassificacaoDoenca ASC, t.descricao ASC";
        return query;
    }

    public List<TbSubclassificacaoDoenca> ordenarSubClassificacaoDoenca()
    {
        String query = constroiQueryOrdenar();

        TypedQuery<TbSubclassificacaoDoenca> t = em.createQuery(query, TbSubclassificacaoDoenca.class);
        List<TbSubclassificacaoDoenca> lista = t.getResultList();

        return lista;
    }

}
