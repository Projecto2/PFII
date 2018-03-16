/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean;

import entidade.GrlDiaSemana;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessao.GrlDiaSemanaFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public class GrlDiaSemanaBean implements Serializable
{
    @EJB
    private GrlDiaSemanaFacade grlDiaSemanaFacade;

    /**
     * Creates a new instance of GrlDiaSemanaBean
     */
    public GrlDiaSemanaBean()
    {
    }
    
    public List<GrlDiaSemana> findAll()
    {
        return grlDiaSemanaFacade.findAll();
    }
    
}
