/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean;

import entidade.GrlGrauParentesco;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessao.GrlGrauParentescoFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public class GrlGrauParentescoBean implements Serializable
{
    @EJB
    private GrlGrauParentescoFacade grlGrauParentescoFacade;

    /**
     * Creates a new instance of GrlGrauParentescoBean
     */
    public GrlGrauParentescoBean()
    {
    }
    
    
    public List<GrlGrauParentesco> findAll()
    {
        List<GrlGrauParentesco> lista = grlGrauParentescoFacade.findAll();
//        return grlGrauParentescoFacade.findAll();
        return lista;
    }
}
