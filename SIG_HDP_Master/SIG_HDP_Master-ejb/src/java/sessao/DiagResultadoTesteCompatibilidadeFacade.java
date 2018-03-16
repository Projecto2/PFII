/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagResultadoTesteCompatibilidade;
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
public class DiagResultadoTesteCompatibilidadeFacade extends AbstractFacade<DiagResultadoTesteCompatibilidade>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagResultadoTesteCompatibilidadeFacade()
    {
        super(DiagResultadoTesteCompatibilidade.class);
    }
    
    public boolean isResultadoTesteCompatibilidadeTabelaEmpty()
    {
        List<DiagResultadoTesteCompatibilidade> list = this.findAll();
        return (list == null || list.isEmpty());
    }
    
    public List<DiagResultadoTesteCompatibilidade> findPesquisa(DiagResultadoTesteCompatibilidade objectPesquisar)
    {
        String query = constroiQuery(objectPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (objectPesquisar.getPkIdResultadoTesteCompatibilidade() != null)
        {
            qry.setParameter("pkIdResultadoTesteCompatibilidade", objectPesquisar.getPkIdResultadoTesteCompatibilidade());
        }

        if (objectPesquisar.getDescricao()!= null && !objectPesquisar.getDescricao().trim().isEmpty())
        {
            qry.setParameter("descricao", objectPesquisar.getDescricao()+ "%");
        }

        List<DiagResultadoTesteCompatibilidade> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagResultadoTesteCompatibilidade objectPesquisar)
    {
        String query = "Select d FROM DiagResultadoTesteCompatibilidade d WHERE :pesquisar = :pesquisar";

        if (objectPesquisar.getPkIdResultadoTesteCompatibilidade() != null)
        {
            query += " AND d.pkIdResultadoTesteCompatibilidade = :pkIdResultadoTesteCompatibilidade";
        }

        if (objectPesquisar.getDescricao()!= null && !objectPesquisar.getDescricao().trim().isEmpty())
        {
            query += " AND d.descricao LIKE :descricao";
        }

        query += " ORDER BY d.descricao";

        return query;
    }
}
