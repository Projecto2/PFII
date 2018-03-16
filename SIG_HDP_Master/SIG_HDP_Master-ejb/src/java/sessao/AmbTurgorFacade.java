/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbTurgor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbTurgorFacade extends AbstractFacade<AmbTurgor>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbTurgorFacade()
    {
        super(AmbTurgor.class);
    }
    
    public boolean isTurgorTabelaEmpty()
    {
        List<AmbTurgor> listAmbTurgor = this.findAll();
        return (listAmbTurgor == null || listAmbTurgor.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdTurgor)
    {
        AmbTurgor reg = this.find(pkIdTurgor);
        return reg != null;
    }
}
