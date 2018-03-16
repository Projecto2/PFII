/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.diagbean;

import entidade.DiagAmostra;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagAmostraBean implements Serializable
{

    public static DiagAmostraBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagAmostraBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagAmostraBean");
    }

    public static DiagAmostra getInstancia()
    {
        DiagAmostra diagAmostra = new DiagAmostra();
        diagAmostra.setFkIdColecta(DiagColectaBean.getInstancia());
        diagAmostra.setFkIdTipoAmostra(DiagTipoAmostraBean.getInstancia());

        return diagAmostra;
    }
    
}
