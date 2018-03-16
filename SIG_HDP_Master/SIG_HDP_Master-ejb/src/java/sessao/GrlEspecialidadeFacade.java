/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.GrlEspecialidade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 * @author Garcia Paulo
 */
@Stateless
public class GrlEspecialidadeFacade extends AbstractFacade<GrlEspecialidade>
{

     @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager ()
     {
          return em;
     }

     public GrlEspecialidadeFacade ()
     {
          super(GrlEspecialidade.class);
     }

     public List<GrlEspecialidade> pesquisaPorProfissao (Integer idProfissao)
     {
          TypedQuery<GrlEspecialidade> t = this.em.createQuery("SELECT e FROM GrlEspecialidade e WHERE e.fkIdProfissao.pkIdProfissao = :idProfissao", GrlEspecialidade.class).setParameter("idProfissao", idProfissao);

          List<GrlEspecialidade> resultado = t.getResultList();

          return resultado;
     }

     /**
      * Gemix
      *
      * @return
      */
     public List<GrlEspecialidade> pesquisaPorEspecialidadeMedica ()
     {
          TypedQuery<GrlEspecialidade> t = this.em.createQuery("SELECT e FROM GrlEspecialidade e WHERE e.fkIdProfissao.descricao = :profissao", GrlEspecialidade.class).setParameter("profissao", "Médico");

          List<GrlEspecialidade> resultado = t.getResultList();

          return resultado;
     }

     public List<GrlEspecialidade> findEspecialidade (GrlEspecialidade especialidade)
     {
          String query = constroiQuery(especialidade);

          TypedQuery<GrlEspecialidade> t = em.createQuery(query, GrlEspecialidade.class);

          if (especialidade.getFkIdProfissao().getPkIdProfissao() != null)
          {
               t.setParameter("profissao", especialidade.getFkIdProfissao().getPkIdProfissao());
          }

          if (especialidade.getDescricao() != null && !especialidade.getDescricao().trim().isEmpty())
          {
               t.setParameter("descricao", especialidade.getDescricao()+"%");
          }

          List<GrlEspecialidade> resultado = t.getResultList();

          return resultado;
     }

     public String constroiQuery (GrlEspecialidade especialidade)
     {
          String query = "SELECT e FROM GrlEspecialidade e WHERE e.pkIdEspecialidade = e.pkIdEspecialidade";

          if (especialidade.getFkIdProfissao().getPkIdProfissao() != null)
          {
               query += " AND e.fkIdProfissao.pkIdProfissao = :profissao";
          }

          if (especialidade.getDescricao() != null && !especialidade.getDescricao().trim().isEmpty())
          {
               query += " AND e.descricao LIKE :descricao";
          }

          query += " ORDER BY e.fkIdProfissao.descricao";

          return query;
     }
     
    public List<GrlEspecialidade> findAllOrderByDescricao(Integer pkIdProfissao)
    {
        Query q = em.createQuery("SELECT a FROM GrlEspecialidade a WHERE a.fkIdProfissao.pkIdProfissao = :pkIdProfissao ORDER BY a.descricao");
        q.setParameter("pkIdProfissao", pkIdProfissao);
        return q.getResultList();
    }
}
