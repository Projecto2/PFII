/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagResultadoExameCandidato;
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
public class DiagResultadoExameCandidatoFacade extends AbstractFacade<DiagResultadoExameCandidato>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagResultadoExameCandidatoFacade()
    {
        super(DiagResultadoExameCandidato.class);
    }
    
    public boolean isResultadoExameCandidatoTabelaEmpty()
    {
        List<DiagResultadoExameCandidato> list = this.findAll();
        return (list == null || list.isEmpty());
    }
    
    public List<DiagResultadoExameCandidato> findPesquisa(DiagResultadoExameCandidato objectPesquisar)
    {
        String query = constroiQuery(objectPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (objectPesquisar.getPkIdResultadoExameCandidato() != null)
        {
            qry.setParameter("pkIdResultadoExameCandidato", objectPesquisar.getPkIdResultadoExameCandidato());
        }

        if (objectPesquisar.getDescricaoResultadoExameCandidato()!= null && !objectPesquisar.getDescricaoResultadoExameCandidato().trim().isEmpty())
        {
            qry.setParameter("descricaoResultadoExameCandidato", objectPesquisar.getDescricaoResultadoExameCandidato()+ "%");
        }

        List<DiagResultadoExameCandidato> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagResultadoExameCandidato objectPesquisar)
    {
        String query = "Select d FROM DiagResultadoExameCandidato d WHERE :pesquisar = :pesquisar";

        if (objectPesquisar.getPkIdResultadoExameCandidato() != null)
        {
            query += " AND d.pkIdResultadoExameCandidato = :pkIdResultadoExameCandidato";
        }

        if (objectPesquisar.getDescricaoResultadoExameCandidato()!= null && !objectPesquisar.getDescricaoResultadoExameCandidato().trim().isEmpty())
        {
            query += " AND d.descricaoResultadoExameCandidato LIKE :descricaoResultadoExameCandidato";
        }

        query += " ORDER BY d.descricaoResultadoExameCandidato";

        return query;
    }
    
}
