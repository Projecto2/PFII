/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagGrupoSanguineo;
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
public class DiagGrupoSanguineoFacade extends AbstractFacade<DiagGrupoSanguineo>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagGrupoSanguineoFacade()
    {
        super(DiagGrupoSanguineo.class);
    }
    
    @Override
    public List<DiagGrupoSanguineo> findAll()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagGrupoSanguineo d ORDER BY d.descricaoGrupoSanguineo", DiagGrupoSanguineo.class);
        return typedQuery.getResultList();
    }
    
    public List<DiagGrupoSanguineo> findPesquisa(DiagGrupoSanguineo grupoSanguineo)
    {
        String query = constroiQuery(grupoSanguineo);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (grupoSanguineo.getPkIdGrupoSanguineo()!= null)
        {
            qry.setParameter("pkIdGrupoSanguineo", grupoSanguineo.getPkIdGrupoSanguineo());
        }

        if (grupoSanguineo.getDescricaoGrupoSanguineo()!= null && !grupoSanguineo.getDescricaoGrupoSanguineo().trim().isEmpty())
        {
            qry.setParameter("descricaoGrupoSanguineo", grupoSanguineo.getDescricaoGrupoSanguineo() + "%");
        }

        List<DiagGrupoSanguineo> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagGrupoSanguineo grupoSanguineo)
    {
        String query = "Select d FROM DiagGrupoSanguineo d WHERE :pesquisar = :pesquisar";

        if (grupoSanguineo.getPkIdGrupoSanguineo() != null)
        {
            query += " AND d.pkIdGrupoSanguineo = :pkIdGrupoSanguineo";
        }

        if (grupoSanguineo.getDescricaoGrupoSanguineo() != null && !grupoSanguineo.getDescricaoGrupoSanguineo().trim().isEmpty())
        {
            query += " AND d.descricaoGrupoSanguineo LIKE :descricaoGrupoSanguineo";
        }

        query += " ORDER BY d.descricaoGrupoSanguineo";

        return query;
    }
    
    public boolean isGrupoSanguineoTabelaEmpty()
    {
        List<DiagGrupoSanguineo> listGrupoSanguineo = this.findAll();
        return (listGrupoSanguineo == null || listGrupoSanguineo.isEmpty());
    }
}
