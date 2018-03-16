/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbCidCapitulos;
import entidade.AmbConsultorioAtendimento;
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
public class AmbConsultorioAtendimentoFacade extends AbstractFacade<AmbConsultorioAtendimento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbConsultorioAtendimentoFacade()
    {
        super(AmbConsultorioAtendimento.class);
    }
    
    public String constroiQuery(AmbConsultorioAtendimento item, Date dataInicio, Date dataFim)
    {
        String query = "Select a FROM AmbConsultorioAtendimento a WHERE :pesquisar = :pesquisar";

        if (dataInicio != null)
        {
            query += " AND a.dataHoraCadastro >= :dataInicio";
        }

        if (dataFim != null)
        {
            query += " AND a.dataHoraCadastro <= :dataFim";
        }

        query += " ORDER BY a.dataHoraCadastro DESC";

        return query;
    }

    public List<AmbConsultorioAtendimento> findEncaminhamentos(AmbConsultorioAtendimento item, Date dataInicio, Date dataFim)
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

        List<AmbConsultorioAtendimento> resultado = t.getResultList();

        return resultado;
    } 
}
