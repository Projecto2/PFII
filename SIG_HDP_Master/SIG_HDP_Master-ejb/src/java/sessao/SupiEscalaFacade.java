/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhFuncionario;
import entidade.SupiEscala;
import entidade.SupiSupervisorHasEscala;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author helga
 */
@Stateless
public class SupiEscalaFacade extends AbstractFacade<SupiEscala> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiEscalaFacade() {
        super(SupiEscala.class);
    }
    
    public List<SupiEscala> findEscala(SupiEscala escala)
    {
        String query = constroiQuery(escala);

        TypedQuery<SupiEscala> t = em.createQuery(query, SupiEscala.class);

        if (escala.getFkIdSeccao() != null)
        {
            if (escala.getFkIdSeccao().getPkIdSeccao() != null)
            {
                t.setParameter("seccao", escala.getFkIdSeccao().getPkIdSeccao());
            }
        }

        if (escala.getFkIdTipoEscala().getPkIdTipoEscala() != null)
        {
            t.setParameter("tipoEscala", escala.getFkIdTipoEscala().getPkIdTipoEscala());
        }

        if (escala.getAno() != 0)
        {
            t.setParameter("ano", escala.getAno());
        }

        if (escala.getMes() != 0)
        {
            t.setParameter("mes", escala.getMes());
        }

        List<SupiEscala> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(SupiEscala escala)
    {
        String query = "SELECT e FROM SupiEscala e WHERE e.pkIdEscala = e.pkIdEscala";

        if (escala.getFkIdSeccao() != null)
        {
            if (escala.getFkIdSeccao().getPkIdSeccao() != null)
            {
                query += " AND e.fkIdSeccao.pkIdSeccao = :seccao";
            }
        }

        if (escala.getFkIdTipoEscala().getPkIdTipoEscala() != null)
        {
            query += " AND e.fkIdTipoEscala.pkIdTipoEscala = :tipoEscala";
        }

        if (escala.getAno() != 0)
        {
            query += " AND e.ano = :ano";
        }
        if (escala.getMes() != 0)
        {
            query += " AND e.mes = :mes";
        }

        query += " ORDER BY e.ano, e.mes";

        return query;
    }
    
     public List<RhFuncionario> findMedicos()
    {

        TypedQuery<RhFuncionario> t = em.createQuery("SELECT medico FROM RhFuncionario medico"
                + " WHERE medico.fkIdProfissao.descricao = :profissao", RhFuncionario.class)
                .setParameter("profissao", "Médico");

        return t.getResultList();
    }
    
    public List<RhFuncionario> findMedicoPorEscala(Integer idEscala)
    {
        TypedQuery<RhFuncionario> query = em.createQuery("SELECT DISTINCT medico FROM RhFuncionario medico, SupiMedicoHasEscala medicoHas "
                + "WHERE medico.pkIdFuncionario = medicoHas.fkIdMedico.pkIdFuncionario AND medicoHas.fkIdEscala.pkIdEscala = :idEscala ORDER BY medico.fkIdPessoa.nome", RhFuncionario.class)
                .setParameter("idEscala", idEscala);

        return query.getResultList();
    }
    
    public List<RhFuncionario> findMedicoSemEscala(int ano, int mes)
    {
        TypedQuery<RhFuncionario> query = em.createQuery("SELECT DISTINCT medico FROM RhFuncionario medico, SupiMedicoHasEscala medicoHas "
                + "WHERE medico.pkIdFuncionario = medicoHas.fkIdMedico.pkIdFuncionario AND medico.fkIdProfissao.descricao = :profissao ORDER BY medico.fkIdPessoa.nome AND medicoHas.fkIdEscala.ano <> :ano AND medicoHas.fkIdEscala.mes <> :mes", RhFuncionario.class)
                .setParameter("mes", mes).setParameter("ano", ano).setParameter("profissao", "Médico");

        return query.getResultList();
    }
    
   
    public List<RhFuncionario> findEnfermeiros()
    {

        TypedQuery<RhFuncionario> t = em.createQuery("SELECT supervisor FROM RhFuncionario supervisor"
                + " WHERE supervisor.fkIdProfissao.descricao = :profissao", RhFuncionario.class)
                .setParameter("profissao", "Enfermeiro");

        return t.getResultList();
    }

    public List<RhFuncionario> findSupervisoresPorEscala(Integer idEscala)
    {
        TypedQuery<RhFuncionario> query = em.createQuery("SELECT DISTINCT superv FROM RhFuncionario superv, SupiSupervisorHasEscala supHas "
                + "WHERE superv.pkIdFuncionario = supHas.fkIdFuncionario.pkIdFuncionario AND supHas.fkIdEscala.pkIdEscala = :idEscala ORDER BY superv.fkIdPessoa.nome", RhFuncionario.class)
                .setParameter("idEscala", idEscala);

        return query.getResultList();
    }
    
    //listar os supervisores sem escala de um determinado mes selecionado do ano corrente
    public List<RhFuncionario> findSupervisoresSemEscala(int ano, int mes)
    {
        TypedQuery<RhFuncionario> query = em.createQuery("SELECT DISTINCT superv FROM RhFuncionario superv, SupiSupervisorHasEscala supHas "
                + "WHERE superv.pkIdFuncionario = supHas.fkIdFuncionario.pkIdFuncionario AND superv.fkIdProfissao.descricao = :profissao ORDER BY superv.fkIdPessoa.nome AND supHas.fkIdEscala.ano <> :ano AND supHas.fkIdEscala.mes <> :mes", RhFuncionario.class)
                .setParameter("mes", mes).setParameter("ano", ano).setParameter("profissao", "Enfermeiro");

        return query.getResultList();
    }
}
