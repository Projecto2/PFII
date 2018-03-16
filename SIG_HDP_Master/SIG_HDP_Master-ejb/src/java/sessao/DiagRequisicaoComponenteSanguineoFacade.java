/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.DiagRequisicaoComponenteSanguineo;
import entidade.DiagRequisicaoComponenteSanguineoHasComponente;
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
public class DiagRequisicaoComponenteSanguineoFacade extends AbstractFacade<DiagRequisicaoComponenteSanguineo>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagRequisicaoComponenteSanguineoFacade()
    {
        super(DiagRequisicaoComponenteSanguineo.class);
    }

    public List<DiagRequisicaoComponenteSanguineoHasComponente> findComponentesBySolicitacao(DiagRequisicaoComponenteSanguineo requisicaoComponenteSanguineoAux)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagRequisicaoComponenteSanguineoHasComponente d WHERE d.fkIdRequisicaoComponenteSanguineo.pkIdRequisicaoComponenteSanguineo = :idRequisicaoComponentes", DiagRequisicaoComponenteSanguineoHasComponente.class).setParameter("idRequisicaoComponentes", requisicaoComponenteSanguineoAux.getPkIdRequisicaoComponenteSanguineo());

        return typedQuery.getResultList();
    }

    public List<DiagRequisicaoComponenteSanguineo> findSolicitacoes()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagRequisicaoComponenteSanguineo d WHERE "
                + "d.pkIdRequisicaoComponenteSanguineo NOT IN (SELECT dd.fkIdRequisicaoComponenteSanguineo.pkIdRequisicaoComponenteSanguineo "
                + "FROM DiagRespostaRequisicaoComponenteSanguineo dd) ORDER BY d.data DESC", DiagSolicitacaoTipagemDoente.class);

        return typedQuery.getResultList();
    }
}
