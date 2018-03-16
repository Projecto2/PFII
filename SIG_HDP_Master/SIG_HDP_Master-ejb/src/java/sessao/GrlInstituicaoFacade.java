/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlInstituicao;
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
public class GrlInstituicaoFacade extends AbstractFacade<GrlInstituicao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlInstituicaoFacade ()
    {
        super(GrlInstituicao.class);
    }
    
    public List<GrlInstituicao> findInstituicao (GrlInstituicao item)
    {
        String query = constroiQuery(item);

        TypedQuery<GrlInstituicao> t = em.createQuery(query, GrlInstituicao.class);


        if (item.getCodigoInstituicao() != null && !item.getCodigoInstituicao().trim().isEmpty())
        {
            t.setParameter("sigla", item.getCodigoInstituicao() + "%");
        }

        if (item.getDescricao() != null && !item.getDescricao().trim().isEmpty())
        {
            t.setParameter("descricao", item.getCodigoInstituicao() + "%");
        }

        List<GrlInstituicao> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery (GrlInstituicao item)
    {
        String query = "SELECT c FROM GrlInstituicao c WHERE c.pkIdInstituicao = c.pkIdInstituicao";

        if (item.getCodigoInstituicao() != null && !item.getCodigoInstituicao().trim().isEmpty())
        {
            query += " AND c.codigoInstituicao.descricao LIKE :sigla";
        }

        if (item.getDescricao() != null && !item.getDescricao().trim().isEmpty())
        {
            query += " AND c.descricao LIKE :descricao";
        }

        query += " ORDER BY c.descricao";

        return query;
    }
}
