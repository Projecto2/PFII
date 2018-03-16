/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean;

import entidade.InterRegistoInternamento;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterRequisicaoComponenteSanguineoBean
{

    /**
     * Creates a new instance of InterRequisicaoComponenteSanguineoBean
     */
    
    private InterRegistoInternamento registoInternamento;
    
    public InterRequisicaoComponenteSanguineoBean()
    {
    }

    public static InterRequisicaoComponenteSanguineoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterRequisicaoComponenteSanguineoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interRequisicaoComponenteSanguineoBean");
    }
    
    public InterRegistoInternamento getRegistoInternamento()
    {
        return registoInternamento;
    }

    public void setRegistoInternamento(InterRegistoInternamento registoInternamento)
    {
        this.registoInternamento = registoInternamento;
    }    
}
