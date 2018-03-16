/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbTextura;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbTexturaFacade extends AbstractFacade<AmbTextura>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbTexturaFacade()
    {
        super(AmbTextura.class);
    }
    
    public boolean isTexturaTabelaEmpty()
    {
        List<AmbTextura> listAmbTextura = this.findAll();
        return (listAmbTextura == null || listAmbTextura.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdTextura)
    {
        AmbTextura reg = this.find(pkIdTextura);
        return reg != null;
    }
}
