/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterRegistoInternamento;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterControloDiarioBean implements Serializable
{

    private InterRegistoInternamento registoInternamento;

    /**
     * Creates a new instance of InterControloDiarioBean
     */
    public InterControloDiarioBean()
    {
    }

    public static InterControloDiarioBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterControloDiarioBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interControloDiarioBean");
    }

    public InterRegistoInternamento getRegistoInternamento()
    {
        return registoInternamento;
    }

    public void setRegistoInternamento(InterRegistoInternamento registoInternamento)
    {
        this.registoInternamento = registoInternamento;
    }    

    public String ficha(InterRegistoInternamento registoInternamamento)
    {
        this.registoInternamento = registoInternamamento;
//        if (tipoServicoInter.equals("CNT"))
//            return "/faces/interVisao/interCNT/fichaTerapeuticaCNTInter.xhtml?faces-redirect=true";
        return "/faces/interVisao/interInternamento/internamentoListar/fichaControloTerapeuticaInter.xhtml?faces-redirect=true";
    }

    public String parametrosVitais(InterRegistoInternamento registoInternamamento)
    {
        this.registoInternamento = registoInternamamento;
        return "/faces/interVisao/interInternamento/internamentoListar/parametrosVitaisListarInter.xhtml?faces-redirect=true";
    }

    public String medicacao(InterRegistoInternamento registoInternamamento)
    {        
        this.registoInternamento = registoInternamamento;
        return "/faces/interVisao/interInternamento/internamentoListar/medicacaoListarInter.xhtml?faces-redirect=true";
    }
    
    public String resultadosExames(InterRegistoInternamento registoInternamamento)
    {        
        this.registoInternamento = registoInternamamento;
        return "/faces/interVisao/interLaboratorio/resultadosExamesInter.xhtml?faces-redirect=true";
    }

}
