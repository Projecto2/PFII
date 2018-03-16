/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.SegConta;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.segbean.SegLoginBean;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterObjetosSessaoBean implements Serializable
{

    /**
     * Creates a new instance of ObjetosSessaoBean
     */
    
    private String servicoInter;

    public InterObjetosSessaoBean ()
    {
    }

    public static InterObjetosSessaoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterObjetosSessaoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interObjetosSessaoBean");
    }
    
    public String getServicoInter ()
    {  
        return servicoInter;
    }

    public void setServicoInter (String servicoInter)
    {
        this.servicoInter = servicoInter;
    }

    public String registarInternamento (String servico)
    {
        servicoInter = servico;

        return "listarSolicitacoesInternamento.xhtml";
    }
    
    public String getMenuTitulo ()
    {
        return servicoInter;
    }
}
