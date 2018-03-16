/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagResultadoTriagem;
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
public class DiagResultadoTriagemFacade extends AbstractFacade<DiagResultadoTriagem>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagResultadoTriagemFacade()
    {
        super(DiagResultadoTriagem.class);
    }
 
    public boolean isResultadoTriagemTabelaEmpty()
    {
        List<DiagResultadoTriagem> list = this.findAll();
        return (list == null || list.isEmpty());
    }
    
    public List<DiagResultadoTriagem> findPesquisa(DiagResultadoTriagem objectPesquisar)
    {
        String query = constroiQuery(objectPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (objectPesquisar.getPkIdResultadoTriagem() != null)
        {
            qry.setParameter("pkIdResultadoTriagem", objectPesquisar.getPkIdResultadoTriagem());
        }

        if (objectPesquisar.getDescricaoResultadoTriagem()!= null && !objectPesquisar.getDescricaoResultadoTriagem().trim().isEmpty())
        {
            qry.setParameter("descricaoResultadoTriagem", objectPesquisar.getDescricaoResultadoTriagem()+ "%");
        }

        List<DiagResultadoTriagem> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagResultadoTriagem objectPesquisar)
    {
        String query = "Select d FROM DiagResultadoTriagem d WHERE :pesquisar = :pesquisar";

        if (objectPesquisar.getPkIdResultadoTriagem() != null)
        {
            query += " AND d.pkIdResultadoTriagem = :pkIdResultadoTriagem";
        }

        if (objectPesquisar.getDescricaoResultadoTriagem()!= null && !objectPesquisar.getDescricaoResultadoTriagem().trim().isEmpty())
        {
            query += " AND d.descricaoResultadoTriagem LIKE :descricaoResultadoTriagem";
        }

        query += " ORDER BY d.descricaoResultadoTriagem";

        return query;
    }
}
