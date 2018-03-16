/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AmbCidPerfilTipos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aires
 */
@Stateless
public class AmbCidPerfilTiposFacade extends AbstractFacade<AmbCidPerfilTipos>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCidPerfilTiposFacade()
    {
        super(AmbCidPerfilTipos.class);
    }

    public List<AmbCidPerfilTipos> findAllOrderByNome()
    {
        Query q = em.createQuery("SELECT a FROM AmbCidPerfilTipos a ORDER BY a.nome");

        return q.getResultList();
    }

    public AmbCidPerfilTipos findByNome(String nome)
    {
        Query q = em.createQuery("SELECT a FROM AmbCidPerfilTipos a WHERE a.nome = :nome");
        q.setParameter("nome", nome);
        List<AmbCidPerfilTipos> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
    
}
        
