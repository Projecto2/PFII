/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean;

import entidade.InterPulsoUnidade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.InterPulsoUnidadeFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterPulsoUnidadeListarBean implements Serializable
{
    @EJB
    private InterPulsoUnidadeFacade interPulsoUnidadeFacade;

    private String descricaoPesq, codPesq;
    
    private List<InterPulsoUnidade> listaPulsoUnidade;

    private InterPulsoUnidade interPulsoUnidade;
    
    /**
     * Creates a new instance of InterPulsoUnidadeListarBean
     */
    public InterPulsoUnidadeListarBean()
    {
    }
    
    public static InterPulsoUnidadeListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterPulsoUnidadeListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interPulsoUnidadeListarBean");
    }
    
    public List<InterPulsoUnidade> getLerTodos() 
    {
         return interPulsoUnidadeFacade.findAll();
    }

    public String getDescricaoPesq()
    {
        return descricaoPesq;
    }

    public void setDescricaoPesq(String descricaoPesq)
    {
        this.descricaoPesq = descricaoPesq;
    }

    public String getCodPesq()
    {
        return codPesq;
    }

    public void setCodPesq(String codPesq)
    {
        this.codPesq = codPesq;
    }

    public List<InterPulsoUnidade> getListaPulsoUnidade()
    {
        return listaPulsoUnidade;
    }

    public void setListaPulsoUnidade(List<InterPulsoUnidade> listaPulsoUnidade)
    {
        this.listaPulsoUnidade = listaPulsoUnidade;
    }    

    public InterPulsoUnidade getInterPulsoUnidade()
    {
        return interPulsoUnidade;
    }

    public void setInterPulsoUnidade(InterPulsoUnidade interPulsoUnidade)
    {
        this.interPulsoUnidade = interPulsoUnidade;
    }
    
    public void findByDescricao()
    {
        listaPulsoUnidade = interPulsoUnidadeFacade.findByDescricao(descricaoPesq, codPesq);

        if (listaPulsoUnidade.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaPulsoUnidade.size() + " registo(s) retornado(s).");
        }
    }
}
