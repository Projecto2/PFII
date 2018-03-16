/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.GrlFornecedor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class GrlFornecedorFacade extends AbstractFacade<GrlFornecedor>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlFornecedorFacade ()
    {
        super(GrlFornecedor.class);
    }

    public List<GrlFornecedor> findFornecedor (GrlFornecedor item)
    {
        String query = constroiQuery(item);

        TypedQuery<GrlFornecedor> t = em.createQuery(query, GrlFornecedor.class);

        if (item.getNumeroContribuinte() != null && !item.getNumeroContribuinte().isEmpty())
        {
            t.setParameter("numeroContribuinte", "%" + item.getNumeroContribuinte() + "%");
        }

        if (item.getFkIdInstituicao().getCodigoInstituicao() != null && !item.getFkIdInstituicao().getCodigoInstituicao().isEmpty())
        {
            t.setParameter("codigoFornecedor", "%" + item.getFkIdInstituicao().getCodigoInstituicao() + "%");
        }

        if (item.getFkIdInstituicao().getDescricao() != null && !item.getFkIdInstituicao().getDescricao().isEmpty())
        {
            t.setParameter("descricao", "%" + item.getFkIdInstituicao().getDescricao() + "%");
        }

        List<GrlFornecedor> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery (GrlFornecedor item)
    {
        String query = "SELECT f FROM GrlFornecedor f WHERE f.pkIdFornecedor = f.pkIdFornecedor ";

        if (item.getNumeroContribuinte() != null && !item.getNumeroContribuinte().isEmpty())
        {
            query += "AND f.numeroContribuinte LIKE :numeroContribuinte";
        }

        if (item.getFkIdInstituicao().getCodigoInstituicao()!= null && !item.getFkIdInstituicao().getCodigoInstituicao().isEmpty())
        {
            query += "AND f.fkIdInstituicao.codigoInstituicao LIKE :codigoFornecedor";
        }

        if (item.getFkIdInstituicao().getDescricao() != null && !item.getFkIdInstituicao().getDescricao().isEmpty())
        {
            query += "AND f.fkIdInstituicao.descricao LIKE :descricao";
        }

        query += " ORDER BY f.fkIdInstituicao.descricao";

        return query;
    }
}
