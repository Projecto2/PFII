/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean;

import entidade.GrlSexo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.GrlSexoFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlSexoBean implements Serializable
{
    @EJB
    private GrlSexoFacade grlSexoFacade;

    /**
     * Creates a new instance of SexoBean
     */
    public GrlSexoBean()
    {
    }
    
    public List<GrlSexo> findAll()
    {
        return grlSexoFacade.findAll();
    }
    
}
