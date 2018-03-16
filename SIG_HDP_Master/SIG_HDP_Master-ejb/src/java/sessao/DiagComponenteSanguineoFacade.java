/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagComponenteSanguineo;
import entidade.DiagExame;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagComponenteSanguineoFacade extends AbstractFacade<DiagComponenteSanguineo>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagComponenteSanguineoFacade()
    {
        super(DiagComponenteSanguineo.class);
    }
    
    public List<DiagComponenteSanguineo> findPesquisa(DiagComponenteSanguineo componenteSanguineoPesquisar)
    {
        String query = constroiQuery(componenteSanguineoPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (componenteSanguineoPesquisar.getPkIdComponenteSanguineo()!= null)
        {
            qry.setParameter("pkIdComponenteSanguineo", componenteSanguineoPesquisar.getPkIdComponenteSanguineo());
        }

        if (componenteSanguineoPesquisar.getDescricaoComponenteSanguineo()!= null && !componenteSanguineoPesquisar.getDescricaoComponenteSanguineo().trim().isEmpty())
        {
            qry.setParameter("descricaoComponenteSanguineo", componenteSanguineoPesquisar.getDescricaoComponenteSanguineo() + "%");
        }

        List<DiagComponenteSanguineo> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagComponenteSanguineo componenteSanguineoPesquisar)
    {
        String query = "Select d FROM DiagComponenteSanguineo d WHERE :pesquisar = :pesquisar";

        if (componenteSanguineoPesquisar.getPkIdComponenteSanguineo() != null)
        {
            query += " AND d.pkIdComponenteSanguineo = :pkIdComponenteSanguineo";
        }

        if (componenteSanguineoPesquisar.getDescricaoComponenteSanguineo() != null && !componenteSanguineoPesquisar.getDescricaoComponenteSanguineo().trim().isEmpty())
        {
            query += " AND d.descricaoComponenteSanguineo LIKE :descricaoComponenteSanguineo";
        }

        query += " ORDER BY d.descricaoComponenteSanguineo";

        return query;
    }
    
    public boolean isComponenteSanguineoTabelaEmpty()
    {
        List<DiagComponenteSanguineo> listTiposComponentes = this.findAll();
        return (listTiposComponentes == null || listTiposComponentes.isEmpty());
    }
}
