/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.FarmCategoriaMedicamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmCategoriaMedicamentoFacade extends AbstractFacade<FarmCategoriaMedicamento>
{

   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmCategoriaMedicamentoFacade()
   {
      super(FarmCategoriaMedicamento.class);
   }

   /*  
    findCategoriasRaizesOrderByDescricao
    */
   public List<FarmCategoriaMedicamento> findCategoriasRaizesOrderByDescricao()
   {
      String query = constroiQueryParaRaizesOrderByDescricao();

      TypedQuery<FarmCategoriaMedicamento> t = em.createQuery(query, FarmCategoriaMedicamento.class);
      List<FarmCategoriaMedicamento> resultado = t.getResultList();

      return resultado;
   }
   /*  
    findCategoriasRaizesOrderByCapitulo
    */

   public List<FarmCategoriaMedicamento> findCategoriasRaizesOrderByCapitulo()
   {
      String query = constroiQueryParaRaizesOrderByCapitulo();

      TypedQuery<FarmCategoriaMedicamento> t = em.createQuery(query, FarmCategoriaMedicamento.class);
      List<FarmCategoriaMedicamento> resultado = t.getResultList();

      return resultado;
   }

   /*  
    findCategoriaOrderByDescricao
    */
   public List<FarmCategoriaMedicamento> findCategoriaOrderByDescricao(FarmCategoriaMedicamento item)
   {
      String query = constroiQueryOrderByDescricao(item);

      TypedQuery<FarmCategoriaMedicamento> t = em.createQuery(query, FarmCategoriaMedicamento.class);

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%" + item.getDescricao() + "%");

      if (item.getCapitulo() != null && !item.getCapitulo().isEmpty())
         t.setParameter("capitulo", "%" + item.getCapitulo() + "%");

      if (item.getFkIdCategoriaSuper().getPkIdCategoriaMedicamento() != null)
         t.setParameter("pai", item.getFkIdCategoriaSuper());

      List<FarmCategoriaMedicamento> resultado = t.getResultList();
      return resultado;
   }

   /*  
    findCategoriaOrderByCapitulo
    */
   public List<FarmCategoriaMedicamento> findCategoriaOrderByCapitulo(FarmCategoriaMedicamento item)
   {
      String query = constroiQueryOrderByCapitulo(item);

      TypedQuery<FarmCategoriaMedicamento> t = em.createQuery(query, FarmCategoriaMedicamento.class);

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         t.setParameter("descricao", "%" + item.getDescricao() + "%");

      if (item.getCapitulo() != null && !item.getCapitulo().isEmpty())
         t.setParameter("capitulo", "%" + item.getCapitulo() + "%");

      if (item.getFkIdCategoriaSuper().getPkIdCategoriaMedicamento() != null)
         t.setParameter("pai", item);

      List<FarmCategoriaMedicamento> resultado = t.getResultList();
      return resultado;
   }

   /*  
    findFilhosCategoriaOrderByCapitulo
    */
   public List<FarmCategoriaMedicamento> findFilhosCategoriaOrderByCapitulo(FarmCategoriaMedicamento item)
   {
      String query = constroiQueryFilhosOrderByCapitulo();

      TypedQuery<FarmCategoriaMedicamento> t = em.createQuery(query, FarmCategoriaMedicamento.class);

      t.setParameter("pai", item);

      List<FarmCategoriaMedicamento> resultado = t.getResultList();
      return resultado;
   }

   /*  
    findFilhosCategoriaOrderByDescricao
    */
   public List<FarmCategoriaMedicamento> findFilhosCategoriaOrderByDescricao(FarmCategoriaMedicamento item)
   {
      String query = constroiQueryFilhosOrderByDescricao();

      TypedQuery<FarmCategoriaMedicamento> t = em.createQuery(query, FarmCategoriaMedicamento.class);

      t.setParameter("pai", item);

      List<FarmCategoriaMedicamento> resultado = t.getResultList();
      return resultado;
   }

   public FarmCategoriaMedicamento findCategoriaByCapitulo(FarmCategoriaMedicamento item)
   {
      TypedQuery<FarmCategoriaMedicamento> t = em.createQuery("SELECT f FROM FarmCategoriaMedicamento f WHERE f.capitulo =:capitulo", FarmCategoriaMedicamento.class);
      t.setParameter("capitulo", item.getCapitulo());

      List<FarmCategoriaMedicamento> resultado = t.getResultList();
      if (!resultado.isEmpty())
         return resultado.get(0);

      return null;
   }
   /*  
    constroiQueryParaRaizesOrderByDescricao
    */

   public String constroiQueryParaRaizesOrderByDescricao()
   {
      String query = "SELECT f FROM FarmCategoriaMedicamento f WHERE f.pkIdCategoriaMedicamento = f.pkIdCategoriaMedicamento";

      query += " AND f.fkIdCategoriaSuper.pkIdCategoriaMedicamento = null ORDER BY f.descricao";
      return query;
   }

   /*  
    constroiQueryParaRaizesOrderByDescricao
    */
   public String constroiQueryParaRaizesOrderByCapitulo()
   {
      String query = "SELECT f FROM FarmCategoriaMedicamento f WHERE f.pkIdCategoriaMedicamento = f.pkIdCategoriaMedicamento";

      query += " AND f.fkIdCategoriaSuper.pkIdCategoriaMedicamento = null ORDER BY f.capitulo";
      return query;
   }

   /*  
    constroiQueryOrderByDescricao
    */
   public String constroiQueryOrderByDescricao(FarmCategoriaMedicamento item)
   {
      String query = "SELECT f FROM FarmCategoriaMedicamento f WHERE f.pkIdCategoriaMedicamento = f.pkIdCategoriaMedicamento";

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";

      if (item.getFkIdCategoriaSuper().getPkIdCategoriaMedicamento() != null)
         query += " AND f.fkIdCategoriaSuper = :pai";

      query += " ORDER BY f.descricao";
      return query;
   }

   /*  
    constroiQueryOrderByCapitulo
    */
   public String constroiQueryOrderByCapitulo(FarmCategoriaMedicamento item)
   {
      String query = "SELECT f FROM FarmCategoriaMedicamento f WHERE f.pkIdCategoriaMedicamento = f.pkIdCategoriaMedicamento";

      if (item.getDescricao() != null && !item.getDescricao().isEmpty())
         query += " AND f.descricao LIKE :descricao";

      if (item.getCapitulo() != null && !item.getCapitulo().isEmpty())
         query += " AND f.capitulo = :capitulo";

      if (item.getFkIdCategoriaSuper().getPkIdCategoriaMedicamento() != null)
         query += " AND f.fkIdCategoriaSuper = :pai";

      query += " ORDER BY f.capitulo";
      return query;
   }

   public String constroiQueryFilhosOrderByDescricao()
   {
      String query = "SELECT f FROM FarmCategoriaMedicamento f WHERE f.pkIdCategoriaMedicamento = f.pkIdCategoriaMedicamento";

      query += " AND f.fkIdCategoriaSuper = :pai ORDER BY f.descricao";
      return query;
   }

   public String constroiQueryFilhosOrderByCapitulo()
   {
      String query = "SELECT f FROM FarmCategoriaMedicamento f WHERE f.pkIdCategoriaMedicamento = f.pkIdCategoriaMedicamento";

      query += " AND f.fkIdCategoriaSuper = :pai ORDER BY f.capitulo";
      return query;
   }

   public boolean isCategoriaMedicamentoTabelaEmpty()
   {
      List<FarmCategoriaMedicamento> listCategoriasMedicamentos = this.findAll();
      return (listCategoriasMedicamentos == null || listCategoriasMedicamentos.isEmpty());
   }

   public boolean existeRegisto(int pkIdCategoriaMedicamento)
   {
      FarmCategoriaMedicamento reg = this.find(pkIdCategoriaMedicamento);
      return reg != null;
   }
}
