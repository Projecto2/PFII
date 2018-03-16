/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagRespostasQuestoesRequisicaoComponentes;
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
public class DiagRespostasQuestoesRequisicaoComponentesFacade extends AbstractFacade<DiagRespostasQuestoesRequisicaoComponentes>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagRespostasQuestoesRequisicaoComponentesFacade()
    {
        super(DiagRespostasQuestoesRequisicaoComponentes.class);
    }
    
    public boolean isRespostasQuestoesRequisicaoComponentesTabelaEmpty()
    {
        List<DiagRespostasQuestoesRequisicaoComponentes> list = this.findAll();
        return (list == null || list.isEmpty());
    }
    
    public List<DiagRespostasQuestoesRequisicaoComponentes> findPesquisa(DiagRespostasQuestoesRequisicaoComponentes objectPesquisar)
    {
        String query = constroiQuery(objectPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (objectPesquisar.getPkIdRespostasQuestoesRequisicaoComponentes() != null)
        {
            qry.setParameter("pkIdRespostasQuestoesRequisicaoComponentes", objectPesquisar.getPkIdRespostasQuestoesRequisicaoComponentes());
        }

        if (objectPesquisar.getDescricao()!= null && !objectPesquisar.getDescricao().trim().isEmpty())
        {
            qry.setParameter("descricao", objectPesquisar.getDescricao()+ "%");
        }

        List<DiagRespostasQuestoesRequisicaoComponentes> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagRespostasQuestoesRequisicaoComponentes objectPesquisar)
    {
        String query = "Select d FROM DiagRespostasQuestoesRequisicaoComponentes d WHERE :pesquisar = :pesquisar";

        if (objectPesquisar.getPkIdRespostasQuestoesRequisicaoComponentes() != null)
        {
            query += " AND d.pkIdRespostasQuestoesRequisicaoComponentes = :pkIdRespostasQuestoesRequisicaoComponentes";
        }

        if (objectPesquisar.getDescricao()!= null && !objectPesquisar.getDescricao().trim().isEmpty())
        {
            query += " AND d.descricao LIKE :descricao";
        }

        query += " ORDER BY d.descricao";

        return query;
    }
}
