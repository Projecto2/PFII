/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsServicoEfetuado;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.admsbean.servicosSolicitadosBean.AdmsAgendamentoBean;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class AdmsServicoEfetuadoBean implements Serializable
{
    
    public static AdmsServicoEfetuadoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsServicoEfetuadoBean admsServicoEfetuadoBean
                = (AdmsServicoEfetuadoBean) context.getELContext().
                getELResolver().getValue(FacesContext.getCurrentInstance().
                        getELContext(), null, "AdmsServicoEfetuadoBean");
        
        return admsServicoEfetuadoBean;
    }
    
    public static AdmsServicoEfetuado getInstancia()
    {
        AdmsServicoEfetuado admsServicoEfetuado = new AdmsServicoEfetuado();
        admsServicoEfetuado.setFkIdServicoSolicitado(AdmsAgendamentoBean.getInstanciaBean().getInstanciaServicoSolicitado());
        
        return admsServicoEfetuado;
    }
    
}
