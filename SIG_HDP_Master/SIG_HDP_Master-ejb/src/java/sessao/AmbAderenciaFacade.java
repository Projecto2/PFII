/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbAderencia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbAderenciaFacade extends AbstractFacade<AmbAderencia>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbAderenciaFacade()
    {
        super(AmbAderencia.class);
    }
    
    public boolean isAderenciaTabelaEmpty()
    {
        List<AmbAderencia> listAmbAderencia = this.findAll();
        return (listAmbAderencia == null || listAmbAderencia.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdAderencia)
    {
        AmbAderencia reg = this.find(pkIdAderencia);
        return reg != null;
    }
}
