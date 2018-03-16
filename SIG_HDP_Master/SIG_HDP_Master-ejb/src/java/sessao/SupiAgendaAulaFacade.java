/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;


import entidade.RhEstagiario;
import entidade.SupiAgendaAula;
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
public class SupiAgendaAulaFacade extends AbstractFacade<SupiAgendaAula> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiAgendaAulaFacade() {
        super(SupiAgendaAula.class);
    }
    
    public List<SupiAgendaAula> findAgendaAulaPorTurma(Integer idTurma)
    {
        Query query = em.createQuery("SELECT agenda FROM SupiAgendaAula agenda WHERE agenda.fkIdCriacaoTurma.pkIdCriacaoTurma = :idTurma ORDER BY agenda.data", SupiAgendaAula.class)
                .setParameter("idTurma", idTurma);

        return query.getResultList();
    }
    
     public List<SupiAgendaAula> findAgendaAula(SupiAgendaAula supiAgendaAula, Date dataInicio, Date dataFim)
    {
          String query = constroiQuery(supiAgendaAula,dataInicio, dataFim);

        TypedQuery<SupiAgendaAula> t = em.createQuery(query, SupiAgendaAula.class);

        if (supiAgendaAula.getFkIdCriacaoTurma().getPkIdCriacaoTurma() != null)
        {
            t.setParameter("criacaoTurma", supiAgendaAula.getFkIdCriacaoTurma().getPkIdCriacaoTurma());
        }

        if (supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            t.setParameter("nome", supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() != null && !supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            t.setParameter("nomeDoMeio", supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if (supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            t.setParameter("sobreNome", supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
        }
        
        if(dataInicio != null && dataFim != null)
      {
         t.setParameter("dataInicio", dataInicio);
         t.setParameter("dataFim", dataFim);
      }
      
      if(dataInicio != null && dataFim == null)
      {
         t.setParameter("dataInicio", dataInicio);
      }
      
      if(dataInicio == null && dataFim != null)
      {
         t.setParameter("dataFim", dataFim);
      }

        if (supiAgendaAula.getTema() != null && !supiAgendaAula.getTema().trim().isEmpty())
        {
            t.setParameter("numBi", supiAgendaAula.getTema());
        }


        t.setMaxResults(200);

        List<SupiAgendaAula> resultado = t.getResultList();

        return resultado;
    }
     
     public String constroiQuery (SupiAgendaAula supiAgendaAula, Date dataInicio, Date dataFim)
    {
        String query = "SELECT e FROM SupiAgendaAula e WHERE e.pkIdAgendaAula = e.pkIdAgendaAula";

        if (supiAgendaAula.getFkIdCriacaoTurma().getPkIdCriacaoTurma() != null)
        {
            query += " AND e.fkIdCriacaoTurma.pkIdCriacaoTurma = :criacaoTurma";
        }

        if (supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND e.fkIdFuncionario.fkIdPessoa.nome LIKE :nome";
        }

        if (supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() != null && !supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            query += " AND e.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if (supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !supiAgendaAula.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND e.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if (supiAgendaAula.getTema() != null && !supiAgendaAula.getTema().trim().isEmpty())
        {
            query += " AND e.tema LIKE :tema";
        }

        query += " ORDER BY e.fkIdFuncionario.fkIdPessoa.nome, e.fkIdFuncionario.fkIdPessoa.nomeDoMeio, e.fkIdFuncionario.fkIdPessoa.sobreNome";
        
        if(dataInicio != null && dataFim != null)
      {
         query += " AND f.data >= :dataInicio and f.data <= :dataFim";
      }
      
      if(dataInicio != null && dataFim == null)
      {
         query += " AND f.data >= :dataInicio";
      }
      
      if(dataInicio == null && dataFim != null)
      {
         query += " AND f.data <= :dataFim";
      }
      
      query += " ORDER BY f.data DESC";

        return query;
    }
 
}
