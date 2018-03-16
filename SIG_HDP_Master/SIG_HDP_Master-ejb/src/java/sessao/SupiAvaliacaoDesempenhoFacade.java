/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiAvaliacaoDesempenho;
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
public class SupiAvaliacaoDesempenhoFacade extends AbstractFacade<SupiAvaliacaoDesempenho> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiAvaliacaoDesempenhoFacade() {
        super(SupiAvaliacaoDesempenho.class);
    }
    public List<SupiAvaliacaoDesempenho> findPorIdPessoa (Integer idPessoa)
     {
          TypedQuery<SupiAvaliacaoDesempenho> query = em.createQuery("SELECT e FROM SupiAvaliacaoDesempenho e WHERE e.fkIdEstagiario.fkIdPessoa.pkIdPessoa = :idPessoa", SupiAvaliacaoDesempenho.class).setParameter("idPessoa", idPessoa);

          return query.getResultList();
     }
     
     public List<SupiAvaliacaoDesempenho> findPorNumeroBI (String numBI)
     {
          TypedQuery<SupiAvaliacaoDesempenho> query = em.createQuery("SELECT e FROM SupiAvaliacaoDesempenho e WHERE e.fkIdEstagiario.numeroBi = :numBI", SupiAvaliacaoDesempenho.class).setParameter("numBI", numBI);

          return query.getResultList();
     }
    
    public List<SupiAvaliacaoDesempenho> findDesempenho (SupiAvaliacaoDesempenho desempenho)
     {
          String query = constroiQuery(desempenho);

          TypedQuery<SupiAvaliacaoDesempenho> t = em.createQuery(query, SupiAvaliacaoDesempenho.class);

          if (desempenho.getFkIdEstagiario().getFkIdEstadoEstagiario().getPkIdEstadoEstagiario() != null)
          {
               t.setParameter("estadoEstagiario", desempenho.getFkIdEstagiario().getFkIdEstadoEstagiario().getPkIdEstadoEstagiario());
          }

          if (desempenho.getFkIdEstagiario().getFkIdPessoa().getNome() != null && !desempenho.getFkIdEstagiario().getFkIdPessoa().getNome().trim().isEmpty())
          {
               t.setParameter("nome", "%"+desempenho.getFkIdEstagiario().getFkIdPessoa().getNome()+"%");
          }
          if (desempenho.getFkIdEstagiario().getFkIdEscolaUniversidade().getPkIdEscolaUniversidade() != null)
          {
               t.setParameter("escolaUniversidade", desempenho.getFkIdEstagiario().getFkIdEscolaUniversidade().getPkIdEscolaUniversidade());
          }

          if (desempenho.getFkIdEstagiario().getFkIdPessoa().getNomeDoMeio() != null && !desempenho.getFkIdEstagiario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
          {
               t.setParameter("nomeDoMeio", "%"+desempenho.getFkIdEstagiario().getFkIdPessoa().getNomeDoMeio()+"%");
          }

          if (desempenho.getFkIdEstagiario().getFkIdPessoa().getSobreNome() != null && !desempenho.getFkIdEstagiario().getFkIdPessoa().getSobreNome().trim().isEmpty())
          {
               t.setParameter("sobreNome", "%"+desempenho.getFkIdEstagiario().getFkIdPessoa().getSobreNome()+"%");
          }

          if (desempenho.getFkIdEstagiario().getNumeroBi() != null && !desempenho.getFkIdEstagiario().getNumeroBi().trim().isEmpty())
          {
               t.setParameter("numBi", desempenho.getFkIdEstagiario().getNumeroBi());
          }

          if (desempenho.getFkIdEstagiario().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
          {
               t.setParameter("sexo", desempenho.getFkIdEstagiario().getFkIdPessoa().getFkIdSexo().getPkIdSexo());
          }

          List<SupiAvaliacaoDesempenho> resultado = t.getResultList();

          return resultado;
     }
    
    public String constroiQuery (SupiAvaliacaoDesempenho desempenho)
     {
          String query = "SELECT e FROM SupiAvaliacaoDesempenho e WHERE e.pkIdAvaliacaoDesempenho = e.pkIdAvaliacaoDesempenho";

          if (desempenho.getFkIdEstagiario().getFkIdEstadoEstagiario().getPkIdEstadoEstagiario() != null)
          {
               query += " AND e.fkIdEstagiario.fkIdEstadoEstagiario.pkIdEstadoEstagiario = :estadoEstagiario";
          }
           if (desempenho.getFkIdEstagiario().getFkIdEscolaUniversidade().getPkIdEscolaUniversidade() != null)
          {
               query += " AND e.fkIdEstagiario.fkIdEscolaUniversidade.pkIdEscolaUniversidade = :escolaUniversidade";
          }

          if (desempenho.getFkIdEstagiario().getFkIdPessoa().getNome() != null && !desempenho.getFkIdEstagiario().getFkIdPessoa().getNome().trim().isEmpty())
          {
               query += " AND e.fkIdEstagiario.fkIdPessoa.nome LIKE :nome ORDER BY e.nome";
          }

          if (desempenho.getFkIdEstagiario().getFkIdPessoa().getNomeDoMeio() != null && !desempenho.getFkIdEstagiario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
          {
               query += " AND e.fkIdEstagiario.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
          }

          if (desempenho.getFkIdEstagiario().getFkIdPessoa().getSobreNome() != null && !desempenho.getFkIdEstagiario().getFkIdPessoa().getSobreNome().trim().isEmpty())
          {
               query += " AND e.fkIdEstagiario.fkIdPessoa.sobreNome LIKE :sobreNome";
          }

          if (desempenho.getFkIdEstagiario().getNumeroBi() != null && !desempenho.getFkIdEstagiario().getNumeroBi().trim().isEmpty())
          {
               query += " AND e.fkIdEstagiario.numeroBi LIKE :numBi";
          }

          if (desempenho.getFkIdEstagiario().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
          {
               query += " AND e.fkIdEstagiario.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
          }

          query += " ORDER BY e.fkIdEstagiario.fkIdPessoa.nome, e.fkIdEstagiario.fkIdPessoa.nomeDoMeio, e.fkIdEstagiario.fkIdPessoa.sobreNome";

          return query;
     }
    
}
