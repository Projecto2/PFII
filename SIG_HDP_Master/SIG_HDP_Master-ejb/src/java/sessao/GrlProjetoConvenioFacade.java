/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlConvenio;
import entidade.GrlProjetoConvenio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gemix
 */
@Stateless
public class GrlProjetoConvenioFacade extends AbstractFacade<GrlProjetoConvenio>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public GrlProjetoConvenioFacade()
    {
        super(GrlProjetoConvenio.class);
    }
    
    public List<GrlProjetoConvenio> findPorProjetosAtivos(int idConvenio)
    {

        Query query = em.createQuery("SELECT projetoConvenio from GrlProjetoConvenio projetoConvenio WHERE projetoConvenio.fkIdConvenio.pkIdConvenio = :idConvenio AND projetoConvenio.estadoProjetoConvenio = true");
    
        query.setParameter("idConvenio", idConvenio);
        
        return query.getResultList();
    }
    
    public List<GrlProjetoConvenio> findProjetoConvenio(GrlProjetoConvenio projetoConvenio, int estadoProjetoConvenio)
    {
        String query = constroiQuery(projetoConvenio, estadoProjetoConvenio);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (projetoConvenio.getFkIdConvenio().getPkIdConvenio() != null)
        {
            qry.setParameter("fkIdConvenio", projetoConvenio.getFkIdConvenio().getPkIdConvenio());
        }

        if (projetoConvenio.getDescricaoProjeto()!= null && !projetoConvenio.getDescricaoProjeto().trim().isEmpty())
        {
            qry.setParameter("descricaoProjeto", projetoConvenio.getDescricaoProjeto() + "%");
        }

        if (estadoProjetoConvenio == 1)
        {
            qry.setParameter("estadoProjetoConvenio", projetoConvenio.getEstadoProjetoConvenio());
        }
        List<GrlProjetoConvenio> projetosConvenio = qry.getResultList();
        return projetosConvenio;
    }

    public String constroiQuery(GrlProjetoConvenio projetoConvenio, int estadoProjetoConvenio)
    {
        String query = "SELECT projetoConvenio FROM GrlProjetoConvenio as projetoConvenio WHERE :pesquisar = :pesquisar";

        if (projetoConvenio.getFkIdConvenio().getPkIdConvenio()!= null)
        {
            query += " AND projetoConvenio.fkIdConvenio.pkIdConvenio = :fkIdConvenio";
        }

        if (projetoConvenio.getDescricaoProjeto()!= null && !projetoConvenio.getDescricaoProjeto().trim().isEmpty())
        {
            query += " AND projetoConvenio.descricaoProjeto LIKE :descricaoProjeto";
        }

        if (estadoProjetoConvenio == 1)
        {
            query += " AND projetoConvenio.estadoProjetoConvenio = :estadoProjetoConvenio";
        }

        query += " ORDER BY projetoConvenio.descricaoProjeto";

        return query;
    }
    
}
