/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhFuncionario;
import entidade.SupiEscalaMedico;
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
public class SupiEscalaMedicoFacade extends AbstractFacade<SupiEscalaMedico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SupiEscalaMedicoFacade()
    {
        super(SupiEscalaMedico.class);
    }
    
      public List<SupiEscalaMedico> findEscalaMedico(SupiEscalaMedico escala)
    {
        String query = constroiQuery(escala);

        TypedQuery<SupiEscalaMedico> t = em.createQuery(query, SupiEscalaMedico.class);

        if (escala.getFkIdSeccaoTrabalho() != null)
        {
            if (escala.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho() != null)
            {
                t.setParameter("seccao", escala.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho());
            }
        }

        if (escala.getAno() != 0)
        {
            t.setParameter("ano", escala.getAno());
        }

        if (escala.getMes() != 0)
        {
            t.setParameter("mes", escala.getMes());
        }

        List<SupiEscalaMedico> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(SupiEscalaMedico escala)
    {
        String query = "SELECT e FROM SupiEscalaMedico e WHERE e.pkIdEscalaMedico = e.pkIdEscalaMedico";

        if (escala.getFkIdSeccaoTrabalho() != null)
        {
            if (escala.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho() != null)
            {
                query += " AND e.fkIdSeccao.pkIdSeccaoTrabalho = :seccao";
            }
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
    
}
