/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.DiagRequisicaoComponenteSanguineo;
import entidade.DiagRequisicaoComponenteSanguineoHasComponente;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagRequisicaoComponenteSanguineoHasComponenteFacade extends AbstractFacade<DiagRequisicaoComponenteSanguineoHasComponente>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagRequisicaoComponenteSanguineoHasComponenteFacade()
    {
        super(DiagRequisicaoComponenteSanguineoHasComponente.class);
    }

    public List<DiagRequisicaoComponenteSanguineoHasComponente> findComponentesByRequisicao(DiagRequisicaoComponenteSanguineo requisicao)
    {
        if (requisicao == null)
        {
            return new ArrayList<>();
        }
        
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagRequisicaoComponenteSanguineoHasComponente d WHERE d.fkIdRequisicaoComponenteSanguineo.pkIdRequisicaoComponenteSanguineo = :idRequisicao", DiagRequisicaoComponenteSanguineoHasComponente.class).setParameter("idRequisicao", requisicao.getPkIdRequisicaoComponenteSanguineo());

        return typedQuery.getResultList();
    }

}
