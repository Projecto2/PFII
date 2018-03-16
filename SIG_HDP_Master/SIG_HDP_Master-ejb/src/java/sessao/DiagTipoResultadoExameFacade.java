/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagTipoResultadoExame;
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
public class DiagTipoResultadoExameFacade extends AbstractFacade<DiagTipoResultadoExame>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTipoResultadoExameFacade()
    {
        super(DiagTipoResultadoExame.class);
    }
    
    public boolean isTipoResultadoExameTabelaEmpty()
    {
        List<DiagTipoResultadoExame> list = this.findAll();
        return (list == null || list.isEmpty());
    }
    
    public List<DiagTipoResultadoExame> findPesquisa(DiagTipoResultadoExame objectPesquisar)
    {
        String query = constroiQuery(objectPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (objectPesquisar.getPkIdTipoResultadoExame() != null)
        {
            qry.setParameter("pkIdTipoResultadoExame", objectPesquisar.getPkIdTipoResultadoExame());
        }

        if (objectPesquisar.getDescricaoTipoResultadoExame()!= null && !objectPesquisar.getDescricaoTipoResultadoExame().trim().isEmpty())
        {
            qry.setParameter("descricaoTipoResultadoExame", objectPesquisar.getDescricaoTipoResultadoExame()+ "%");
        }

        List<DiagTipoResultadoExame> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagTipoResultadoExame objectPesquisar)
    {
        String query = "Select d FROM DiagTipoResultadoExame d WHERE :pesquisar = :pesquisar";

        if (objectPesquisar.getPkIdTipoResultadoExame() != null)
        {
            query += " AND d.pkIdTipoResultadoExame = :pkIdTipoResultadoExame";
        }

        if (objectPesquisar.getDescricaoTipoResultadoExame()!= null && !objectPesquisar.getDescricaoTipoResultadoExame().trim().isEmpty())
        {
            query += " AND d.descricaoTipoResultadoExame LIKE :descricaoTipoResultadoExame";
        }

        query += " ORDER BY d.descricaoTipoResultadoExame";

        return query;
    }
}
