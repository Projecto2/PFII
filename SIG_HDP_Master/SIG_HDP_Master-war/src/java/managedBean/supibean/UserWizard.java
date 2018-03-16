/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;


import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author helga
 */
@ManagedBean
@ViewScoped
public class UserWizard implements Serializable
{
    SupiAvaliacaoNovaBean supiAvaliacaoNovaBean = new SupiAvaliacaoNovaBean();

    private User user = new User();

    private boolean skip;
    
    public SupiAvaliacaoNovaBean getSupiAvaliacaoNovaBean()
    {
        return supiAvaliacaoNovaBean;
    }

    public void setSupiAvaliacaoNovaBean(SupiAvaliacaoNovaBean supiAvaliacaoNovaBean)
    {
        this.supiAvaliacaoNovaBean = supiAvaliacaoNovaBean;
    }
    
    

   /* public void save()
    {
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + supiAvaliacaoNovaBean.getEstagiario().getFkIdPessoa().getNome());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    */
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
    
    public boolean isSkip()
    {
        return skip;
    }

    public void setSkip(boolean skip)
    {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event)
    {
        if (skip)
        {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else
        {
            return event.getNewStep();
        }
    }
}
