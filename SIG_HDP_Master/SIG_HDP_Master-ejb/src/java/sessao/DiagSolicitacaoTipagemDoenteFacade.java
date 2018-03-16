/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.DiagSolicitacaoTipagemDoente;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagSolicitacaoTipagemDoenteFacade extends AbstractFacade<DiagSolicitacaoTipagemDoente>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagSolicitacaoTipagemDoenteFacade()
    {
        super(DiagSolicitacaoTipagemDoente.class);
    }

    public List<DiagSolicitacaoTipagemDoente> findTipagensSolicitadas()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagSolicitacaoTipagemDoente d WHERE "
                + "d.pkIdSolicitacaoTipagemDoente NOT IN (SELECT dd.fkIdSolicitacaoTipagemDoente.pkIdSolicitacaoTipagemDoente "
                + "FROM DiagTipagemDoente dd) ORDER BY d.dataSolicitacao DESC", DiagSolicitacaoTipagemDoente.class);

        return typedQuery.getResultList();
    }
}
