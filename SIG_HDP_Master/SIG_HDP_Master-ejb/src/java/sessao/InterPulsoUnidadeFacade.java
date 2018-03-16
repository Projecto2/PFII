/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterPulsoUnidade;
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
public class InterPulsoUnidadeFacade extends AbstractFacade<InterPulsoUnidade>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterPulsoUnidadeFacade()
    {
        super(InterPulsoUnidade.class);
    }
    
    public boolean isPulsoUnidadeTabelaEmpty()
    {
        List<InterPulsoUnidade> listPulsoUnidade = this.findAll();
        return (listPulsoUnidade == null || listPulsoUnidade.isEmpty());
    }

    public List<InterPulsoUnidade> findByDescricao(String descricaoPesq, String cod)
    {
        Query qrs = em.createQuery("SELECT o FROM InterPulsoUnidade o WHERE o.codigo LIKE :valor ORDER BY o.descricao");
        qrs.setParameter("valor", "%"+cod+"%");

        return qrs.getResultList();    
    }
}
