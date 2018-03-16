/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhSeccaoTrabalho;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhSeccaoTrabalhoFacade extends AbstractFacade<RhSeccaoTrabalho>
{

     @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager ()
     {
          return em;
     }

     public RhSeccaoTrabalhoFacade ()
     {
          super(RhSeccaoTrabalho.class);
     }

    /**
     * Pesquisa as secções de trabalho ordenadas pelo nome dos departamentos
     * e pelos nomes das secções
     * 
     * @return
     */
     public List<RhSeccaoTrabalho> findAllPorOrdemDeDepartamento ()
     {
          Query t = em.createQuery("SELECT s FROM RhSeccaoTrabalho s ORDER BY s.fkIdDepartamento.descricao, s.descricao", RhSeccaoTrabalho.class);

          List<RhSeccaoTrabalho> resultado = t.getResultList();

          return resultado;
     }

    /**
     * Pesquisa as secções de trabalho de um determinado depertamento
     * 
     * @param idDepartamento
     * @return
     */
     public List<RhSeccaoTrabalho> pesquisaPorDepartamento (Integer idDepartamento)
     {
          TypedQuery<RhSeccaoTrabalho> t = this.em.createQuery("SELECT s FROM RhSeccaoTrabalho s WHERE s.fkIdDepartamento.pkIdDepartamento = :idDepartamento ORDER BY s.descricao", RhSeccaoTrabalho.class)
                  .setParameter("idDepartamento", idDepartamento);

          List<RhSeccaoTrabalho> resultado = t.getResultList();

          return resultado;
     }

    /**
     * Pesquisa todas as secções de trabalho de acordo com todos os campos que 
     * tiverem dados no objecto seccao, ou seja, procura de acordo os critérios
     * introduzidos
     *
     * @param seccao
     * @return
     */
    public List<RhSeccaoTrabalho> findSeccaoTrabalho (RhSeccaoTrabalho seccao)
     {
          String query = constroiQuery(seccao);

          TypedQuery<RhSeccaoTrabalho> t = em.createQuery(query, RhSeccaoTrabalho.class);

          if (seccao.getFkIdDepartamento().getPkIdDepartamento() != null)
          {
               t.setParameter("departamento", seccao.getFkIdDepartamento().getPkIdDepartamento());
          }

          if (seccao.getDescricao() != null && !seccao.getDescricao().trim().isEmpty())
          {
               t.setParameter("descricao", seccao.getDescricao() + "%");
          }

          List<RhSeccaoTrabalho> resultado = t.getResultList();

          return resultado;
     }

    /**
     * Constrói uma query JPQL com todos os campos que 
     * tiverem dados no objecto seccao
     * 
     * @param seccao
     * @return
     */
    public String constroiQuery (RhSeccaoTrabalho seccao)
    {
          String query = "SELECT s FROM RhSeccaoTrabalho s WHERE s.pkIdSeccaoTrabalho = s.pkIdSeccaoTrabalho";

          if (seccao.getFkIdDepartamento().getPkIdDepartamento() != null)
          {
               query += " AND s.fkIdDepartamento.pkIdDepartamento = :departamento";
          }

          if (seccao.getDescricao() != null && !seccao.getDescricao().trim().isEmpty())
          {
               query += " AND s.descricao LIKE :descricao";
          }

          query += " ORDER BY s.fkIdDepartamento.descricao, s.descricao";

          return query;
     }

}
