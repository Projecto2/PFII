/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.tabelasAuxiliares;

import entidade.FarmViaAdministracao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import sessao.FarmViaAdministracaoFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@RequestScoped
public class FarmViaAdministracaoBean implements Serializable
{

    /**
     * Creates a new instance of FarmViaAdministracaoBean
     */
    @EJB
    private FarmViaAdministracaoFacade farmViaAdministracaoFacade;
    private FarmViaAdministracao via;
    
    public FarmViaAdministracaoBean()
    {
        farmViaAdministracaoFacade = new FarmViaAdministracaoFacade();
        via = new FarmViaAdministracao();
    }

    /**
     * @return the farmViaAdministracaoFacade
     */
    public FarmViaAdministracaoFacade getFarmViaAdministracaoFacade()
    {
        return farmViaAdministracaoFacade;
    }

    /**
     * @param farmViaAdministracaoFacade the farmViaAdministracaoFacade to set
     */
    public void setFarmViaAdministracaoFacade(FarmViaAdministracaoFacade farmViaAdministracaoFacade)
    {
        this.farmViaAdministracaoFacade = farmViaAdministracaoFacade;
    }

    /**
     * @return the via
     */
    public FarmViaAdministracao getVia()
    {
        return via;
    }

    /**
     * @param via the via to set
     */
    public void setVia(FarmViaAdministracao via)
    {
        this.via = via;
    }
    
    public List<FarmViaAdministracao> findAll()
    {
        return farmViaAdministracaoFacade.findAll();
    }
    
    public ArrayList<SelectItem> getTodasVias()
    {
        
        ArrayList<SelectItem> itens = new ArrayList<>();
        
        for (FarmViaAdministracao e : findAll())
        {
            itens.add(new SelectItem(e.getPkIdViaAdministracao(), e.getDescricao()));
        }
        return itens;
    }
    
    public void cadastrar()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        System.out.println("unidade de medida a cadastrar: " +getVia().getDescricao());
        farmViaAdministracaoFacade.create(via);
        getVia().setDescricao("");
        facesContext.addMessage(null, new FacesMessage("Unidade de Medida Cadastrada com sucesso."));
        
    }
}
