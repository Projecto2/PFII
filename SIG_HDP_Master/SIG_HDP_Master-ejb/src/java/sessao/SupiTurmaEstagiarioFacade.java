/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiAgendaAula;
import entidade.SupiTurmaEstagiario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author helga
 */
@Stateless
public class SupiTurmaEstagiarioFacade extends AbstractFacade<SupiTurmaEstagiario>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SupiTurmaEstagiarioFacade()
    {
        super(SupiTurmaEstagiario.class);
    }
    
    public List<SupiTurmaEstagiario> findEstagiariosPorTurma(Integer idTurma)
    {
        Query query = em.createQuery("SELECT turma FROM SupiTurmaEstagiario turma WHERE turma.fkIdCriacaoTurma.pkIdCriacaoTurma = :idTurma ORDER BY turma.fkIdEstagiario.fkIdPessoa.nome, turma.fkIdEstagiario.fkIdPessoa.nomeDoMeio, turma.fkIdEstagiario.fkIdPessoa.sobreNome", SupiTurmaEstagiario.class)
                .setParameter("idTurma", idTurma);

        return query.getResultList();
    }
    
}
