/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.segbean;

import entidade.SegPerfil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.GeradorCodigo;

/**
 *
 * @author adalberto
 */
@ManagedBean
@SessionScoped
public class SegPerfilBean implements Serializable
{

    /**
     * Creates a new instance of SegPerfilBean
     */
    public SegPerfilBean()
    {
    }
    
     public static SegPerfilBean getInstanciaBean()
    {
        return (SegPerfilBean) GeradorCodigo.getInstanciaBean("segPerfilBean");
    }

    public static SegPerfil getInstancia()
    {
        SegPerfil segPerfil = new SegPerfil();
        return segPerfil;
    }
    
}
