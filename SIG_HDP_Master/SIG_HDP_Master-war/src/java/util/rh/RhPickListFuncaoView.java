//package org.primefaces.showcase.view.data;
package util.rh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import entidade.RhFuncao;
import sessao.RhFuncaoFacade;
import javax.ejb.EJB;
//import org.primefaces.showcase.domain.Theme;
//import org.primefaces.showcase.service.ThemeService;

/**
 * 
 * @author Desconhecido (Originalmente Tirada no Primeface Showcase)
 * @author Ornela F. Boaventura (Modificou o Código Original e Adaptou-o)
 */
@ManagedBean
public class RhPickListFuncaoView implements Serializable
{

//    @ManagedProperty("#{themeService}")
//    private ThemeService service;
    @EJB
    private RhFuncaoFacade funcaoFacade;

    private DualListModel<RhFuncao> funcoes;

    @PostConstruct
    public void init ()
    {

//        //Themes
//        List<Theme> themesSource = service.getThemes().subList(0, 5);
//        List<Theme> themesTarget = new ArrayList<Theme>();
        //Themes
        List<RhFuncao> funcaoSource = funcaoFacade.findAll();
        List<RhFuncao> funcaoTarget = new ArrayList<>();

        funcoes = new DualListModel<>(funcaoSource, funcaoTarget);

    }

    public RhFuncaoFacade getFuncaoFacade ()
    {
        return funcaoFacade;
    }

    public void setFuncaoFacade (RhFuncaoFacade funcaoFacade)
    {
        this.funcaoFacade = funcaoFacade;
    }

    public DualListModel<RhFuncao> getFuncoes ()
    {
        return funcoes;
    }

    public void setFuncoes (DualListModel<RhFuncao> funcoes)
    {
        this.funcoes = funcoes;
    }

    public void onTransfer (TransferEvent event)
    {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems())
        {
            builder.append(((RhFuncao) item).getDescricao()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onSelect (SelectEvent event)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void onUnselect (UnselectEvent event)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onReorder ()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }
}
