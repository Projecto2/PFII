/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean;

import entidade.GrlEstadoCivil;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sessao.GrlEstadoCivilFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@RequestScoped
public class GrlEstadoCivilBean implements Serializable
{
    @EJB
    private GrlEstadoCivilFacade grlEstadoCivilFacade;

    /**
     * Creates a new instance of EstadoCivilBean
     */
    public GrlEstadoCivilBean()
    {
        grlEstadoCivilFacade = new GrlEstadoCivilFacade();
    }
    
    public List<GrlEstadoCivil> getEstadoCivis()
    {
        return grlEstadoCivilFacade.findAll();
    }
    
    public int getTotalEstadoCivils(){
        return grlEstadoCivilFacade.count();
    }
}
