/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.InterParametroVital;
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
public class InterParametroVitalFacade extends AbstractFacade<InterParametroVital>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterParametroVitalFacade()
    {
        super(InterParametroVital.class);
    }

    public List<InterParametroVital> listarTodos()
    {
        Query qrs = em.createQuery("SELECT o FROM InterParametroVital o ORDER BY o.descricao");
        
        return qrs.getResultList();
    }
    
    public InterParametroVital listarPorNome(String nomeParametro)
    {
        Query qrs = em.createQuery("SELECT o FROM InterParametroVital o WHERE o.descricao =:nomeParametro");
        qrs.setParameter("nomeParametro", nomeParametro);
        
        List<InterParametroVital> lista = qrs.getResultList();
        
        return lista.get(0);
    }

    public boolean isParametroVitalTabelaEmpty()
    {
        List<InterParametroVital> listParametroVital = this.findAll();
        return (listParametroVital == null || listParametroVital.isEmpty());
    }

    public List<InterParametroVital> findByDescricao(String descricaoPesq)
    {
        Query qrs = em.createQuery("SELECT o FROM InterParametroVital o WHERE o.descricao LIKE :valor ORDER BY o.descricao");
        qrs.setParameter("valor", "%"+descricaoPesq+"%");

        return qrs.getResultList();
    }
}
