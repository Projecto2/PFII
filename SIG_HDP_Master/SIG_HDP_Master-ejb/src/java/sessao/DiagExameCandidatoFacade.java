/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagCandidatoDador;
import entidade.DiagExameCandidato;
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
public class DiagExameCandidatoFacade extends AbstractFacade<DiagExameCandidato>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagExameCandidatoFacade()
    {
        super(DiagExameCandidato.class);
    }
    
    public DiagExameCandidato findExameCandidatoPorCodigo(DiagExameCandidato exameCandidato)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagExameCandidato d WHERE d.pkIdExameDador = :idExameDador", DiagExameCandidato.class).setParameter("idExameDador", exameCandidato.getPkIdExameDador());

        return (DiagExameCandidato) typedQuery.getSingleResult();
    }

    public List<DiagExameCandidato> findExamesCandidatoDador(DiagCandidatoDador candidatoDador)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagExameCandidato d WHERE d.fkIdCandidatoDador.pkIdCandidatoDador = :idCandidatoDador", DiagExameCandidato.class).setParameter("idCandidatoDador", candidatoDador.getPkIdCandidatoDador());

        return typedQuery.getResultList();
    }

    public List<DiagExameCandidato> findExamesCandidatoPorCodigoPessoa(GrlPessoa pessoa)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagExameCandidato d WHERE d.fkIdCandidatoDador.fkIdPessoa.pkIdPessoa = :idPessoa", DiagExameCandidato.class).setParameter("idPessoa", pessoa.getPkIdPessoa());

        return typedQuery.getResultList();
    }

    public Date findDataUltimoExame(DiagCandidatoDador candidatoDador)
    {
        TypedQuery typedQuery = em.createQuery("SELECT MAX(d.dataExame) FROM DiagExameCandidato d WHERE d.fkIdCandidatoDador.pkIdCandidatoDador = :idCandidatoDador", Date.class).setParameter("idCandidatoDador", candidatoDador.getPkIdCandidatoDador());

        return (Date) typedQuery.getSingleResult();
    }

    public DiagExameCandidato findUltimoExame(DiagCandidatoDador candidatoDador)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagExameCandidato d WHERE d.fkIdCandidatoDador.pkIdCandidatoDador = :idCandidatoDador ORDER BY d.dataExame DESC", Date.class).setParameter("idCandidatoDador", candidatoDador.getPkIdCandidatoDador());
        typedQuery.setMaxResults(1);

        if (typedQuery.getResultList().isEmpty())
        {
            return null;
        }
        return (DiagExameCandidato) typedQuery.getSingleResult();
    }
}
