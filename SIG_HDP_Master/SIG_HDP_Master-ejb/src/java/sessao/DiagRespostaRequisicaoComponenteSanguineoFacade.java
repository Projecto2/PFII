/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagRespostaRequisicaoComponenteSanguineo;
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
public class DiagRespostaRequisicaoComponenteSanguineoFacade extends AbstractFacade<DiagRespostaRequisicaoComponenteSanguineo>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagRespostaRequisicaoComponenteSanguineoFacade()
    {
        super(DiagRespostaRequisicaoComponenteSanguineo.class);
    }
    
    public List<DiagRespostaRequisicaoComponenteSanguineo> findSolicitacoesAprovadas()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagRespostaRequisicaoComponenteSanguineo d WHERE "
                + "d.pkIdRespostaRequisicaoComponenteSanguineo NOT IN (SELECT dd.fkIdRespostaRequisicaoComponenteSanguineo.pkIdRespostaRequisicaoComponenteSanguineo "
                + "FROM DiagTransfusao dd) ORDER BY d.dataResposta DESC", DiagRespostaRequisicaoComponenteSanguineo.class);

        return typedQuery.getResultList();
    }
}
