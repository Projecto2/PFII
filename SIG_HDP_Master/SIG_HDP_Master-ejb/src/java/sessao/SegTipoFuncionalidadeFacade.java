/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SegTipoFuncionalidade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author adalberto
 */
@Stateless
public class SegTipoFuncionalidadeFacade extends AbstractFacade<SegTipoFuncionalidade>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SegTipoFuncionalidadeFacade()
    {
        super(SegTipoFuncionalidade.class);
    }
    
    public List<SegTipoFuncionalidade> findAllOrderByNome()
    {
        Query q = em.createQuery("SELECT a FROM SegTipoFuncionalidade a ORDER BY a.nome");
        List<SegTipoFuncionalidade> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list;
    }
    
}
