/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterHoraMedicacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.InterHoraMedicacaoFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterHoraMedicacaoListarBean implements Serializable
{

    @EJB
    private InterHoraMedicacaoFacade interHoraMedicacaoFacade;

    private String descricaoPesq;
    
    private List<InterHoraMedicacao> listaHoraMedicacao;

    private InterHoraMedicacao interHoraMedicacao;
    
    /**
     * Creates a new instance of InterHoraMedicacaoListarBean
     */
    public InterHoraMedicacaoListarBean()
    {
    }

    public static InterHoraMedicacaoListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterHoraMedicacaoListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interHoraMedicacaoListarBean");
    }

    public List<InterHoraMedicacao> getListaHoraMedicacao()
    {
        return listaHoraMedicacao;
    }

    public void setListaHoraMedicacao(List<InterHoraMedicacao> listaHoraMedicacao)
    {
        this.listaHoraMedicacao = listaHoraMedicacao;
    }

    public InterHoraMedicacao getInterHoraMedicacao()
    {
        return interHoraMedicacao;
    }

    public void setInterHoraMedicacao(InterHoraMedicacao interHoraMedicacao)
    {
        this.interHoraMedicacao = interHoraMedicacao;
    }

    
    
    public String getDescricaoPesq()
    {
        return descricaoPesq;
    }

    public void setDescricaoPesq(String descricaoPesq)
    {
        this.descricaoPesq = descricaoPesq;
    }
    
    public List<InterHoraMedicacao> getListarTodas()
    {
        return interHoraMedicacaoFacade.findAll();
    }
    
    public void findByDescricao()
    {
        listaHoraMedicacao = interHoraMedicacaoFacade.findByDescricao(descricaoPesq);

        if (listaHoraMedicacao.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaHoraMedicacao.size() + " registo(s) retornado(s).");
        }
    }
}
