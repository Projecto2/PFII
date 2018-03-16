/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlPais;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class GrlPaisFacade extends AbstractFacade<GrlPais>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlPaisFacade ()
    {
        super(GrlPais.class);
    }
    
    
    public boolean isPaisTabelaEmpty()
    {
        List<GrlPais> listPais = this.findAll();
        return (listPais == null || listPais.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdPais)
    {
        GrlPais reg = this.find(pkIdPais);
        return reg != null;
    }
    
}
