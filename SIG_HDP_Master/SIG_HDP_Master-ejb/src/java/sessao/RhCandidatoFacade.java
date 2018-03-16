/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhCandidato;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhCandidatoFacade extends AbstractFacade<RhCandidato>
{

     @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager ()
     {
          return em;
     }

     public RhCandidatoFacade ()
     {
          super(RhCandidato.class);
     }

    /**
     * Encontra um candidato de acordo o seu id de pessoa
     * 
     * @param idPessoa
     * @return
     */
     public List<RhCandidato> findPorIdPessoa (Integer idPessoa)
     {
          TypedQuery<RhCandidato> query = em.createQuery("SELECT c FROM RhCandidato c WHERE c.fkIdPessoa.pkIdPessoa = :idPessoa", RhCandidato.class).setParameter("idPessoa", idPessoa);

          return query.getResultList();
     }
     
    /**
     * Encontra um candidato de acordo o seu número do BI
     * 
     * @param numBI 
     * @return
     */
     public List<RhCandidato> findPorNumeroBI (String numBI)
     {
          TypedQuery<RhCandidato> query = em.createQuery("SELECT c FROM RhCandidato c WHERE c.numeroBi = :numBI", RhCandidato.class).setParameter("numBI", numBI);

          return query.getResultList();
     }

    /**
     * Retorna a lista de todos os candidatos aprovados
     * 
     * @return
     */
    public List<RhCandidato> findCandidatosAprovados ()
     {
          TypedQuery<RhCandidato> t = em.createQuery("SELECT c FROM RhCandidato c WHERE c.fkIdEstadoCandidato.descricao = 'Aprovado'", RhCandidato.class);

          List<RhCandidato> resultado = t.getResultList();

          return resultado;
     }

    /**
     * Pesquisa todos os candidatos de acordo com todos os campos que 
     * tiverem dados no objecto candidato, ou seja, procura de acordo os critérios
     * introduzidos
     *
     * @param candidato
     * @return
     */
     public List<RhCandidato> findCandidato (RhCandidato candidato)
     {
          String query = constroiQuery(candidato);

          TypedQuery<RhCandidato> t = em.createQuery(query, RhCandidato.class);

          if (candidato.getFkIdEstadoCandidato().getPkIdEstadoCandidato() != null)
          {
               t.setParameter("estadoCandidato", candidato.getFkIdEstadoCandidato().getPkIdEstadoCandidato());
          }

          if (candidato.getFkIdPessoa().getNome() != null && !candidato.getFkIdPessoa().getNome().trim().isEmpty())
          {
               t.setParameter("nome", candidato.getFkIdPessoa().getNome() + "%");
          }

          if (candidato.getFkIdPessoa().getNomeDoMeio() != null && !candidato.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
          {
               t.setParameter("nomeDoMeio", candidato.getFkIdPessoa().getNomeDoMeio() + "%");
          }

          if (candidato.getFkIdPessoa().getSobreNome() != null && !candidato.getFkIdPessoa().getSobreNome().trim().isEmpty())
          {
               t.setParameter("sobreNome", candidato.getFkIdPessoa().getSobreNome() + "%");
          }

          if (candidato.getNumeroBi() != null && !candidato.getNumeroBi().trim().isEmpty())
          {
               t.setParameter("numBi", candidato.getNumeroBi());
          }

          if (candidato.getFkIdCategoriaPretendida().getPkIdCategoriaProfissional() != null)
          {
               t.setParameter("categoriaPretendida", candidato.getFkIdCategoriaPretendida().getPkIdCategoriaProfissional());
          }

         t.setMaxResults(100);

          List<RhCandidato> resultado = t.getResultList();

          return resultado;
     }

    /**
     * Constrói uma query JPQL com todos os campos que 
     * tiverem dados no objecto candidato
     * 
     * @param candidato
     * @return
     */
     public String constroiQuery (RhCandidato candidato)
     {
          String query = "SELECT c FROM RhCandidato c WHERE c.pkIdCandidato = c.pkIdCandidato";

          if (candidato.getFkIdEstadoCandidato().getPkIdEstadoCandidato() != null)
          {
               query += " AND c.fkIdEstadoCandidato.pkIdEstadoCandidato = :estadoCandidato";
          }

          if (candidato.getFkIdPessoa().getNome() != null && !candidato.getFkIdPessoa().getNome().trim().isEmpty())
          {
               query += " AND LOWER(c.fkIdPessoa.nome) LIKE LOWER(:nome)";
          }

          if (candidato.getFkIdPessoa().getNomeDoMeio() != null && !candidato.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
          {
               query += " AND LOWER(c.fkIdPessoa.nomeDoMeio) LIKE LOWER(:nomeDoMeio)";
          }

          if (candidato.getFkIdPessoa().getSobreNome() != null && !candidato.getFkIdPessoa().getSobreNome().trim().isEmpty())
          {
               query += " AND LOWER(c.fkIdPessoa.sobreNome) LIKE LOWER(:sobreNome)";
          }

          if (candidato.getNumeroBi() != null && !candidato.getNumeroBi().trim().isEmpty())
          {
               query += " AND c.numeroBi LIKE :numBi";
          }

          if (candidato.getFkIdCategoriaPretendida().getPkIdCategoriaProfissional() != null)
          {
               query += " AND c.fkIdCategoriaPretendida.pkIdCategoriaProfissional = :categoriaPretendida";
          }

          query += " ORDER BY c.fkIdPessoa.nome, c.fkIdPessoa.nomeDoMeio, c.fkIdPessoa.sobreNome";

          return query;
     }

}
