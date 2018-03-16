/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhEspecialidadeCurso;
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
public class RhEspecialidadeCursoFacade extends AbstractFacade<RhEspecialidadeCurso>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhEspecialidadeCursoFacade ()
    {
        super(RhEspecialidadeCurso.class);
    }

    /**
     * Pesquisa todas as especialidades de um determinado curso
     * 
     * @param idCurso
     * @return
     */
    public List<RhEspecialidadeCurso> pesquisaPorCurso (Integer idCurso)
    {
        TypedQuery<RhEspecialidadeCurso> t = em.createQuery("SELECT e FROM RhEspecialidadeCurso e "
                                         + "WHERE e.fkIdCurso.pkIdCurso = :idCurso ORDER BY e.descricao", RhEspecialidadeCurso.class).setParameter("idCurso", idCurso);

        List<RhEspecialidadeCurso> resultado = t.getResultList();

        return resultado;
    }
    
    /**
     * Pesquisa todas os funcionários de acordo com todos os campos que 
     * tiverem dados no objecto funcionario, ou seja, procura de acordo os critérios
     * introduzidos
     *
     * @param especialidadeCurso
     * @return
     */
     public List<RhEspecialidadeCurso> findEspecialidadeCurso (RhEspecialidadeCurso especialidadeCurso)
     {
          String query = constroiQuery(especialidadeCurso);

          TypedQuery<RhEspecialidadeCurso> t = em.createQuery(query, RhEspecialidadeCurso.class);

          if (especialidadeCurso.getFkIdCurso().getPkIdCurso() != null)
          {
               t.setParameter("curso", especialidadeCurso.getFkIdCurso().getPkIdCurso());
          }

          if (especialidadeCurso.getDescricao() != null && !especialidadeCurso.getDescricao().trim().isEmpty())
          {
               t.setParameter("descricao", especialidadeCurso.getDescricao()+"%");
          }

          List<RhEspecialidadeCurso> resultado = t.getResultList();

          return resultado;
     }

    /**
     * Constrói uma query JPQL com todos os campos que 
     * tiverem dados no objecto especialidadeCurso
     * 
     * @param especialidadeCurso 
     * @return
     */
     public String constroiQuery (RhEspecialidadeCurso especialidadeCurso)
     {
          String query = "SELECT e FROM RhEspecialidadeCurso e WHERE e.pkIdEspecialidadeCurso = e.pkIdEspecialidadeCurso";

          if (especialidadeCurso.getFkIdCurso().getPkIdCurso() != null)
          {
               query += " AND e.fkIdCurso.pkIdCurso = :curso";
          }

          if (especialidadeCurso.getDescricao() != null && !especialidadeCurso.getDescricao().trim().isEmpty())
          {
               query += " AND e.descricao LIKE :descricao";
          }

          query += " ORDER BY e.fkIdCurso.descricao, e.descricao";

          return query;
     }
}
