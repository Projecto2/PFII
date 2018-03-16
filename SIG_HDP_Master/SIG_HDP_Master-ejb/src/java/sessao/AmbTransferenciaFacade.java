/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbTransferencia;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbTransferenciaFacade extends AbstractFacade<AmbTransferencia>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbTransferenciaFacade()
    {
        super(AmbTransferencia.class);
    }
    
    public String constroiQuery(AmbTransferencia item, Date dataInicio, Date dataFim)
    {
        String query = "Select a FROM AmbTransferencia a WHERE :pesquisar = :pesquisar";

        if (dataInicio != null)
        {
            query += " AND a.dataTransferencia >= :dataInicio";
        }

        if (dataFim != null)
        {
            query += " AND a.dataTransferencia <= :dataFim";
        }

        query += " ORDER BY a.dataTransferencia DESC";

        return query;
    }

    public List<AmbTransferencia> findTransferencias(AmbTransferencia item, Date dataInicio, Date dataFim)
    {
        String query = constroiQuery(item, dataInicio, dataFim);

        Query t = em.createQuery(query);

        t.setParameter("pesquisar", true);

        if (dataInicio != null)
        {
            t.setParameter("dataInicio", dataInicio);
        }

        if (dataFim != null)
        {
            t.setParameter("dataFim", dataFim);
        }

        List<AmbTransferencia> resultado = t.getResultList();

        return resultado;
    }    
}
