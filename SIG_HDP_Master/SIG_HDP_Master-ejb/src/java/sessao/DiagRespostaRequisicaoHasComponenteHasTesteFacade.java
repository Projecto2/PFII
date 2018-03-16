/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagRespostaRequisicaoHasComponenteHasTeste;
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
public class DiagRespostaRequisicaoHasComponenteHasTesteFacade extends AbstractFacade<DiagRespostaRequisicaoHasComponenteHasTeste>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagRespostaRequisicaoHasComponenteHasTesteFacade()
    {
        super(DiagRespostaRequisicaoHasComponenteHasTeste.class);
    }
    
    public List<DiagRespostaRequisicaoHasComponenteHasTeste> findBolsas(int idRespostaRequisicaoComponente)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagRespostaRequisicaoHasComponenteHasTeste d WHERE d.fkIdRespostaRequisicaoHasComponente.fkIdRespostaRequisicaoComponente.pkIdRespostaRequisicaoComponenteSanguineo = :idRespostaRequisicaoComponente", DiagRespostaRequisicaoHasComponenteHasTeste.class).setParameter("idRespostaRequisicaoComponente", idRespostaRequisicaoComponente);
        return typedQuery.getResultList();
    }
}
