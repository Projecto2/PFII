/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbSinal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbSinalFacade extends AbstractFacade<AmbSinal>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbSinalFacade()
    {
        super(AmbSinal.class);
    }
    
    public boolean isSinalTabelaEmpty()
    {
        List<AmbSinal> listAmbSinal = this.findAll();
        return (listAmbSinal == null || listAmbSinal.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdSinal)
    {
        AmbSinal reg = this.find(pkIdSinal);
        return reg != null;
    }
}
