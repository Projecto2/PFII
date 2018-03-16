/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagTipoAmostra;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagTipoAmostraFacade extends AbstractFacade<DiagTipoAmostra>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTipoAmostraFacade()
    {
        super(DiagTipoAmostra.class);
    }
    
    @Override
    public List<DiagTipoAmostra> findAll()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTipoAmostra d ORDER BY d.descricaoTipoAmostra", DiagTipoAmostra.class);
        return typedQuery.getResultList();
    }
    
    public List<DiagTipoAmostra> findPesquisa(DiagTipoAmostra tipoAmostra)
    {
        String query = constroiQuery(tipoAmostra);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (tipoAmostra.getPkIdTipoAmostra()!= null)
        {
            qry.setParameter("pkIdTipoAmostra", tipoAmostra.getPkIdTipoAmostra());
        }

        if (tipoAmostra.getDescricaoTipoAmostra()!= null && !tipoAmostra.getDescricaoTipoAmostra().trim().isEmpty())
        {
            qry.setParameter("descricaoTipoAmostra", tipoAmostra.getDescricaoTipoAmostra() + "%");
        }

        List<DiagTipoAmostra> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagTipoAmostra tipoAmostra)
    {
        String query = "Select d FROM DiagTipoAmostra d WHERE :pesquisar = :pesquisar";

        if (tipoAmostra.getPkIdTipoAmostra() != null)
        {
            query += " AND d.pkIdTipoAmostra = :pkIdTipoAmostra";
        }

        if (tipoAmostra.getDescricaoTipoAmostra() != null && !tipoAmostra.getDescricaoTipoAmostra().trim().isEmpty())
        {
            query += " AND d.descricaoTipoAmostra LIKE :descricaoTipoAmostra";
        }

        query += " ORDER BY d.descricaoTipoAmostra";

        return query;
    }
    
    public boolean isTipoAmostraTabelaEmpty()
    {
        List<DiagTipoAmostra> listTipoAmostra = this.findAll();
        return (listTipoAmostra == null || listTipoAmostra.isEmpty());
    }
}
