/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhEstagiario;
import entidade.SupiControloPresenca;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author helga
 */
@Stateless
public class SupiControloPresencaFacade extends AbstractFacade<SupiControloPresenca>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SupiControloPresencaFacade()
    {
        super(SupiControloPresenca.class);
    }

    public SupiControloPresenca findPorEstagiarioDataAula(Integer pkIdEstagiario, Date data)
    {
        Query query;
        query = em.createQuery("SELECT e FROM RhEstagiario e WHERE e.dataFimEstagio <= :data "
                + "AND e.fkIdEstadoEstagiario.descricao = 'Activo'", SupiControloPresenca.class).setParameter("data", data);

        return (SupiControloPresenca) (query.getResultList().isEmpty() ? null : query.getResultList().get(0));
    }

    public List<SupiControloPresenca> findEstagiariosPorAgenda(Integer idAgenda)
    {
        Query query = em.createQuery("SELECT agenda FROM SupiControloPresenca agenda WHERE agenda.fkIdAgendaAula.pkIdAgendaAula = :idAgenda ORDER BY agenda.fkIdEstagiario.fkIdPessoa.nome, agenda.fkIdEstagiario.fkIdPessoa.nomeDoMeio, agenda.fkIdEstagiario.fkIdPessoa.sobreNome", SupiControloPresenca.class)
                .setParameter("idAgenda", idAgenda);

        return query.getResultList();
    }

}
