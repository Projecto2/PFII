/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagTipoDoacao;
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
public class DiagTipoDoacaoFacade extends AbstractFacade<DiagTipoDoacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTipoDoacaoFacade()
    {
        super(DiagTipoDoacao.class);
    }
    
    public boolean isTipoDoacaoTabelaEmpty()
    {
        List<DiagTipoDoacao> list = this.findAll();
        return (list == null || list.isEmpty());
    }
    
    public List<DiagTipoDoacao> findPesquisa(DiagTipoDoacao objectPesquisar)
    {
        String query = constroiQuery(objectPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (objectPesquisar.getPkIdTipoDoacao() != null)
        {
            qry.setParameter("pkIdTipoDoacao", objectPesquisar.getPkIdTipoDoacao());
        }

        if (objectPesquisar.getDescricaoTipoDoacao()!= null && !objectPesquisar.getDescricaoTipoDoacao().trim().isEmpty())
        {
            qry.setParameter("descricaoTipoDoacao", objectPesquisar.getDescricaoTipoDoacao()+ "%");
        }

        List<DiagTipoDoacao> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagTipoDoacao objectPesquisar)
    {
        String query = "Select d FROM DiagTipoDoacao d WHERE :pesquisar = :pesquisar";

        if (objectPesquisar.getPkIdTipoDoacao() != null)
        {
            query += " AND d.pkIdTipoDoacao = :pkIdTipoDoacao";
        }

        if (objectPesquisar.getDescricaoTipoDoacao()!= null && !objectPesquisar.getDescricaoTipoDoacao().trim().isEmpty())
        {
            query += " AND d.descricaoTipoDoacao LIKE :descricaoTipoDoacao";
        }

        query += " ORDER BY d.descricaoTipoDoacao";

        return query;
    }
}
