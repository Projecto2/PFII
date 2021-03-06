/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterResultadoDoenca;
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
public class InterResultadoDoencaFacade extends AbstractFacade<InterResultadoDoenca>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterResultadoDoencaFacade()
    {
        super(InterResultadoDoenca.class);
    }
    
    public boolean isResultadoDoencaTabelaEmpty()
    {
        List<InterResultadoDoenca> listResultadoDoenca = this.findAll();
        return (listResultadoDoenca == null || listResultadoDoenca.isEmpty());
    }

    public List<InterResultadoDoenca> findByDescricao(String descricaoPesq)
    {
        Query qrs = em.createQuery("SELECT o FROM InterResultadoDoenca o WHERE o.descricao LIKE :valor ORDER BY o.descricao");
        qrs.setParameter("valor", "%"+descricaoPesq+"%");

        return qrs.getResultList();
    }
}
