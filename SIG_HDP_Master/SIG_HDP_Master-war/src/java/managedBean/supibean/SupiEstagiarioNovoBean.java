/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.GeradorCodigo;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiEstagiarioNovoBean implements Serializable{

    /**
     * Creates a new instance of SupiEstagiarioNovoBean
     */
    public SupiEstagiarioNovoBean() {
        
    }
    
    public static SupiEstagiarioNovoBean getInstanciaBean()
    {
        return (SupiEstagiarioNovoBean) GeradorCodigo.getInstanciaBean("supiEstagiarioNovoBean");
    }
    
}
