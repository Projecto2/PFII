/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbCor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbCorFacade extends AbstractFacade<AmbCor>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCorFacade()
    {
        super(AmbCor.class);
    }
    
    public boolean isCorTabelaEmpty()
    {
        List<AmbCor> listAmbCor = this.findAll();
        return (listAmbCor == null || listAmbCor.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdCor)
    {
        AmbCor reg = this.find(pkIdCor);
        return reg != null;
    }
}
