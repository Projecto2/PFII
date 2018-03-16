/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagCandidatoDador;
import entidade.DiagTriagem;
import entidade.GrlPessoa;
import java.util.Date;
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
public class DiagTriagemFacade extends AbstractFacade<DiagTriagem>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTriagemFacade()
    {
        super(DiagTriagem.class);
    }
    
    public List<DiagTriagem> findTriagensPorCodigoPessoa(GrlPessoa pessoa)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTriagem d WHERE d.fkIdCandidatoDador.fkIdPessoa.pkIdPessoa = :idPessoa", DiagTriagem.class).setParameter("idPessoa", pessoa.getPkIdPessoa());

        return typedQuery.getResultList();
    }
    
    public DiagTriagem findTriagemPorCodigo(DiagTriagem triagem)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTriagem d WHERE d.pkIdTriagem = :idTriagem", DiagTriagem.class).setParameter("idTriagem", triagem.getPkIdTriagem());

        return (DiagTriagem) typedQuery.getSingleResult();
    }
    
    public Date findDataUltimaTriagem(DiagCandidatoDador candidatoDador)
    {
        TypedQuery typedQuery = em.createQuery("SELECT MAX(d.dataTriagem) FROM DiagTriagem d WHERE d.fkIdCandidatoDador.pkIdCandidatoDador = :idCandidatoDador", Date.class).setParameter("idCandidatoDador", candidatoDador.getPkIdCandidatoDador());

        return (Date) typedQuery.getSingleResult();
    }
    
    public DiagTriagem findUltimaTriagem(DiagCandidatoDador candidatoDador)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTriagem d WHERE d.fkIdCandidatoDador.pkIdCandidatoDador = :idCandidatoDador ORDER BY d.dataTriagem DESC", Date.class).setParameter("idCandidatoDador", candidatoDador.getPkIdCandidatoDador());
        typedQuery.setMaxResults(1);
        
        if (typedQuery.getResultList().isEmpty())
        {
            return null;
        }
        
        return (DiagTriagem) typedQuery.getSingleResult();
    }
}
