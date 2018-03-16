/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.diagbean;

import entidade.DiagColecta;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagColectaBean implements Serializable
{

    public static DiagColectaBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagColectaBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagColectaBean");
    }

    public static DiagColecta getInstancia()
    {
        DiagColecta diagColecta = new DiagColecta();
        diagColecta.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return diagColecta;
    }
    
}
