/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbEspessura;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbEspessuraFacade extends AbstractFacade<AmbEspessura>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbEspessuraFacade()
    {
        super(AmbEspessura.class);
    }
    
    public boolean isEspessuraTabelaEmpty()
    {
        List<AmbEspessura> listAmbEspessura = this.findAll();
        return (listAmbEspessura == null || listAmbEspessura.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdEspessura)
    {
        AmbEspessura reg = this.find(pkIdEspessura);
        return reg != null;
    }
}
