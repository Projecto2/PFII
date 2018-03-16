/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterParametrosVitaisListarBean implements Serializable
{

    private String nomeEnfermeiro, nomeDoMeioEnfermeiro, sobreNomeEnfermeiro, tipoServico;

    private Date dataRegisto, dataRegisto1, dataRegisto2;

    /**
     * Creates a new instance of InterParametrosVitaisListarBean
     */
    public InterParametrosVitaisListarBean()
    {
    }

    public static InterParametrosVitaisListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterParametrosVitaisListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interParametrosVitaisListarBean");
    }

    public void init()
    {
        inicializar();
    }

    public void inicializar()
    {
        tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
    }

    public String getNomeEnfermeiro()
    {
        return nomeEnfermeiro;
    }

    public void setNomeEnfermeiro(String nomeEnfermeiro)
    {
        this.nomeEnfermeiro = nomeEnfermeiro;
    }

    public String getNomeDoMeioEnfermeiro()
    {
        return nomeDoMeioEnfermeiro;
    }

    public void setNomeDoMeioEnfermeiro(String nomeDoMeioEnfermeiro)
    {
        this.nomeDoMeioEnfermeiro = nomeDoMeioEnfermeiro;
    }

    public String getSobreNomeEnfermeiro()
    {
        return sobreNomeEnfermeiro;
    }

    public void setSobreNomeEnfermeiro(String sobreNomeEnfermeiro)
    {
        this.sobreNomeEnfermeiro = sobreNomeEnfermeiro;
    }

    public Date getDataRegisto()
    {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto)
    {
        this.dataRegisto = dataRegisto;
    }

    public Date getDataRegisto1()
    {
        return dataRegisto1;
    }

    public void setDataRegisto1(Date dataRegisto1)
    {
        this.dataRegisto1 = dataRegisto1;
    }

    public Date getDataRegisto2()
    {
        return dataRegisto2;
    }

    public void setDataRegisto2(Date dataRegisto2)
    {
        this.dataRegisto2 = dataRegisto2;
    }   
    
    public String novoParametrosVitais()
    {
//        return "/faces/interVisao/interInternamento/internamentoCadastrar/parametrosVitaisNovoInter.xhtml?faces-redirect=true";
        return "/faces/interVisao/interInternamento/internamentoCadastrar/controloParametrosVitaisNovoInter.xhtml?faces-redirect=true";
    }
}
