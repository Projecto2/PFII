/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterParametrosVitaisNovoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    FacesContext ct = FacesContext.getCurrentInstance();

    private final Calendar dataCorrente = Calendar.getInstance();
    private Date data_hora;

    private Integer ta1, ta2;

    private int outro, diuresi;

    private boolean gravou = false;

    /**
     * Creates a new instance of InterParametrosVitaisBean
     */
    public InterParametrosVitaisNovoBean()
    {
    }

    public static InterParametrosVitaisNovoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterParametrosVitaisNovoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interParametrosVitaisNovoBean");
    }    

    public Integer getTa1()
    {
        return ta1;
    }

    public void setTa1(Integer ta1)
    {
        this.ta1 = ta1;
    }

    public Integer getTa2()
    {
        return ta2;
    }

    public void setTa2(Integer ta2)
    {
        this.ta2 = ta2;
    }

    public int getOutro()
    {
        return outro;
    }

    public void setOutro(int outro)
    {
        this.outro = outro;
    }

    public Date getData_hora()
    {
        return data_hora;
    }

    public void setData_hora(Date data_hora)
    {
        this.data_hora = data_hora;
    }

    public int getDiuresi()
    {
        return diuresi;
    }

    public void setDiuresi(int diuresi)
    {
        this.diuresi = diuresi;
    }
    
    public String voltar()
    {
        gravou = false;
        return "/faces/interVisao/interInternamento/internamentoListar/parametrosVitaisListarInter.xhtml?faces-redirect=true";
    }

}
