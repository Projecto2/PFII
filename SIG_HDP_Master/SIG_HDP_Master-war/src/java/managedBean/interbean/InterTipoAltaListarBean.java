/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean;

import entidade.InterTipoAlta;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.InterTipoAltaFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterTipoAltaListarBean implements Serializable
{
    @EJB
    private InterTipoAltaFacade interTipoAltaFacade;

    private InterTipoAlta interTipoAlta;

    private String descricaoPesq;

    private List<InterTipoAlta> listaTipoAlta;
    
    /**
     * Creates a new instance of InterTipoAltaBean
     */
    public InterTipoAltaListarBean()
    {
        
    }
    
    public static InterTipoAltaListarBean getInstanciaBean()
    {
        return (InterTipoAltaListarBean) GeradorCodigo.getInstanciaBean("interTipoAltaListarBean");
    }

    public InterTipoAltaFacade getInterTipoAltaFacade()
    {
        return interTipoAltaFacade;
    }

    public void setInterTipoAltaFacade(InterTipoAltaFacade interTipoAltaFacade)
    {
        this.interTipoAltaFacade = interTipoAltaFacade;
    }

    public InterTipoAlta getInterTipoAlta()
    {
        return interTipoAlta;
    }

    public void setInterTipoAlta(InterTipoAlta interTipoAlta)
    {
        this.interTipoAlta = interTipoAlta;
    }

    public String getDescricaoPesq()
    {
        return descricaoPesq;
    }

    public void setDescricaoPesq(String descricaoPesq)
    {
        this.descricaoPesq = descricaoPesq;
    }

    public List<InterTipoAlta> getListaTipoAlta()
    {
        return listaTipoAlta;
    }

    public void setListaTipoAlta(List<InterTipoAlta> listaTipoAlta)
    {
        this.listaTipoAlta = listaTipoAlta;
    }
    
    public void pesquisar()
    {
        listaTipoAlta = interTipoAltaFacade.findByDescricao(descricaoPesq);
    
        if (listaTipoAlta.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaTipoAlta.size() + " registo(s) retornado(s).");
        }
    }
    
    public List<InterTipoAlta> getListarTodos() 
    {
         return interTipoAltaFacade.findAll();
    }
}
