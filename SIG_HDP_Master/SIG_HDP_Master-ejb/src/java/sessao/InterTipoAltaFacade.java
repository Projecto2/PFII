/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterTipoAlta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mauro
 */
@Stateless
public class InterTipoAltaFacade extends AbstractFacade<InterTipoAlta> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InterTipoAltaFacade() {
        super(InterTipoAlta.class);
    }
    
    public boolean isTipoAltaTabelaEmpty()
    {
        List<InterTipoAlta> listTipoAlta = this.findAll();
        return (listTipoAlta == null || listTipoAlta.isEmpty());
    }
    
    public List<InterTipoAlta> findByDescricao(String descricaoPesq)
    {
        Query qrs = em.createQuery("SELECT o FROM InterTipoAlta o WHERE o.descricao LIKE :valor ORDER BY o.descricao");
        qrs.setParameter("valor", "%"+descricaoPesq+"%");

        return qrs.getResultList();
    }
}
