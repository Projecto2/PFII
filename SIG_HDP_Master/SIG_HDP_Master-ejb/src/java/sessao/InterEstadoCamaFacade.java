/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.InterEstadoCama;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author armindo
 */
@Stateless
public class InterEstadoCamaFacade extends AbstractFacade<InterEstadoCama>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterEstadoCamaFacade()
    {
        super(InterEstadoCama.class);
    }

    public Integer getStatusByNome(String descricao)
    {
        Query qrs = em.createQuery("SELECT o FROM InterEstadoCama o WHERE o.descricao = :nome");
        qrs.setParameter("nome", descricao);

        List<InterEstadoCama> results = qrs.getResultList();

        if (results.isEmpty())
        {
            return null;
        }

        return results.get(0).getPkIdEstadoCama();
    }

    public boolean isEstadoCamaTabelaEmpty()
    {
        List<InterEstadoCama> listEstadoCamas = this.findAll();
        return (listEstadoCamas == null || listEstadoCamas.isEmpty());
    }

    public List<InterEstadoCama> findByDescricao(String estadoPesq)
    {
        Query qrs = em.createQuery("SELECT o FROM InterEstadoCama o WHERE o.descricao LIKE :nome");
        qrs.setParameter("nome", "%"+estadoPesq+"%");

        return qrs.getResultList();
    }
    
    public List<InterEstadoCama> findAllLivre()
    {
        Query qrs = em.createQuery("SELECT o FROM InterEstadoCama o WHERE o.descricao != :nome1 and o.descricao != :nome2");
        qrs.setParameter("nome1", "Ocupada").setParameter("nome2", "Reservada");

        return qrs.getResultList();
    }
}
