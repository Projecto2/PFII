/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.GrlConvenio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class GrlConvenioFacade extends AbstractFacade<GrlConvenio>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public GrlConvenioFacade()
    {
        super(GrlConvenio.class);
    }

    public List<GrlConvenio> findPorConveniosAtivos()
    {

        Query query = em.createQuery("SELECT convenio from GrlConvenio convenio WHERE convenio.estadoConvenio = true");

        return query.getResultList();
    }

    public List<GrlConvenio> findConvenio(GrlConvenio convenio, int estadoConvenio)
    {
        String query = constroiQuery(convenio, estadoConvenio);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (convenio.getFkIdInstituicao().getPkIdInstituicao() != null)
        {
            qry.setParameter("fkIdInstituicao", convenio.getFkIdInstituicao().getPkIdInstituicao());
        }

        if (convenio.getNomeConvenio() != null && !convenio.getNomeConvenio().trim().isEmpty())
        {
            qry.setParameter("nomeConvenio", convenio.getNomeConvenio() + "%");
        }

        if (estadoConvenio == 1)
        {
            qry.setParameter("estadoConvenio", convenio.getEstadoConvenio());
        }

        List<GrlConvenio> convenios = qry.getResultList();

        return convenios;
    }

    public String constroiQuery(GrlConvenio convenio, int estadoConvenio)
    {
        String query = "SELECT convenio FROM GrlConvenio as convenio WHERE :pesquisar = :pesquisar";

        if (convenio.getFkIdInstituicao().getPkIdInstituicao() != null)
        {
            query += " AND convenio.fkIdInstituicao.pkIdInstituicao = :fkIdInstituicao";
        }

        if (convenio.getNomeConvenio() != null && !convenio.getNomeConvenio().trim().isEmpty())
        {
            query += " AND convenio.nomeConvenio LIKE :nomeConvenio";
        }

        if (estadoConvenio == 1)
        {
            query += " AND convenio.estadoConvenio = :estadoConvenio";
        }

        query += " ORDER BY convenio.nomeConvenio";

        return query;
    }

}
