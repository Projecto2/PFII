/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbCidConfiguracoes;
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
public class AmbCidConfiguracoesFacade extends AbstractFacade<AmbCidConfiguracoes>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCidConfiguracoesFacade()
    {
        super(AmbCidConfiguracoes.class);
    }
    
    public AmbCidConfiguracoes findAllByIdConta(int idConta)
      {
        Query q = em.createQuery("SELECT a FROM AmbCidConfiguracoes a WHERE a.idConta = :idConta");
        q.setParameter("idConta", idConta);
        List<AmbCidConfiguracoes> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
    
    public AmbCidConfiguracoes loadAmbCidConfiguracoes(int idConta)
    {
        return this.findAllByIdConta(idConta);
    }
    
 }
