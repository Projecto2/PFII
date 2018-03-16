/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagNumeroDoacao;
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
public class DiagNumeroDoacaoFacade extends AbstractFacade<DiagNumeroDoacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagNumeroDoacaoFacade()
    {
        super(DiagNumeroDoacao.class);
    }
    
    public boolean isNumeroDoacaoTabelaEmpty()
    {
        List<DiagNumeroDoacao> list = this.findAll();
        return (list == null || list.isEmpty());
    }
    
     public List<DiagNumeroDoacao> findPesquisa(DiagNumeroDoacao estadoClinicoPesquisar)
    {
        String query = constroiQuery(estadoClinicoPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (estadoClinicoPesquisar.getPkIdNumeroDoacao() != null)
        {
            qry.setParameter("pkIdNumeroDoacao", estadoClinicoPesquisar.getPkIdNumeroDoacao());
        }

        if (estadoClinicoPesquisar.getDescricao()!= null && !estadoClinicoPesquisar.getDescricao().trim().isEmpty())
        {
            qry.setParameter("descricao", estadoClinicoPesquisar.getDescricao()+ "%");
        }

        List<DiagNumeroDoacao> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagNumeroDoacao estadoClinicoPesquisar)
    {
        String query = "Select d FROM DiagNumeroDoacao d WHERE :pesquisar = :pesquisar";

        if (estadoClinicoPesquisar.getPkIdNumeroDoacao() != null)
        {
            query += " AND d.pkIdNumeroDoacao = :pkIdNumeroDoacao";
        }

        if (estadoClinicoPesquisar.getDescricao()!= null && !estadoClinicoPesquisar.getDescricao().trim().isEmpty())
        {
            query += " AND d.descricao LIKE :descricao";
        }

        query += " ORDER BY d.descricao";

        return query;
    }
}
