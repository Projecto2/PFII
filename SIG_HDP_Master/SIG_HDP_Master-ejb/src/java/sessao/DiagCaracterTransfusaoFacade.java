/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagCaracterTransfusao;
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
public class DiagCaracterTransfusaoFacade extends AbstractFacade<DiagCaracterTransfusao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagCaracterTransfusaoFacade()
    {
        super(DiagCaracterTransfusao.class);
    }
    
    public boolean isCaracterTransfusaoTabelaEmpty()
    {
        List<DiagCaracterTransfusao> listCaracterTransfusao = this.findAll();
        return (listCaracterTransfusao == null || listCaracterTransfusao.isEmpty());
    }
    
    public List<DiagCaracterTransfusao> findPesquisa(DiagCaracterTransfusao caracterTransfusaoPesquisar)
    {
        String query = constroiQuery(caracterTransfusaoPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (caracterTransfusaoPesquisar.getPkIdCaracterTransfusao() != null)
        {
            qry.setParameter("pkIdCaracterTransfusao", caracterTransfusaoPesquisar.getPkIdCaracterTransfusao());
        }

        if (caracterTransfusaoPesquisar.getDescricao() != null && !caracterTransfusaoPesquisar.getDescricao().trim().isEmpty())
        {
            qry.setParameter("descricao", caracterTransfusaoPesquisar.getDescricao() + "%");
        }

        List<DiagCaracterTransfusao> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagCaracterTransfusao caracterTransfusaoPesquisar)
    {
        String query = "Select d FROM DiagCaracterTransfusao d WHERE :pesquisar = :pesquisar";

        if (caracterTransfusaoPesquisar.getPkIdCaracterTransfusao() != null)
        {
            query += " AND d.pkIdCaracterTransfusao = :pkIdCaracterTransfusao";
        }

        if (caracterTransfusaoPesquisar.getDescricao() != null && !caracterTransfusaoPesquisar.getDescricao().trim().isEmpty())
        {
            query += " AND d.descricao LIKE :descricao";
        }

        query += " ORDER BY d.descricao";

        return query;
    }
}
