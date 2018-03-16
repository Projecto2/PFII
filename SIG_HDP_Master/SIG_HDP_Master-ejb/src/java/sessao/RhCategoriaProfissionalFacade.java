/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template cile, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhCategoriaProfissional;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhCategoriaProfissionalFacade extends AbstractFacade<RhCategoriaProfissional>
{

     @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
     private EntityManager em;

     @Override
     protected EntityManager getEntityManager ()
     {
          return em;
     }

     public RhCategoriaProfissionalFacade ()
     {
          super(RhCategoriaProfissional.class);
     }

    @Override
    public List<RhCategoriaProfissional> findAll()
    {
//          Query t = em.createNativeQuery("SELECT * FROM rh_categoria_profissional ORDER BY descricao LIMIT 200", RhCategoriaProfissional.class);
//          
          Query t = em.createQuery("SELECT c FROM RhCategoriaProfissional c ORDER BY c.descricao", RhCategoriaProfissional.class);

          return t.getResultList();
    }
    
    public List<RhCategoriaProfissional> findAllContainsDescricao (String descricao)
    {
          
          Query t = em.createNativeQuery("SELECT * FROM rh_categoria_profissional WHERE descricao LIKE ?descricao ORDER BY descricao LIMIT 200", RhCategoriaProfissional.class).setParameter("descricao", "%"+descricao+"%");

          return t.getResultList();
    }

}
