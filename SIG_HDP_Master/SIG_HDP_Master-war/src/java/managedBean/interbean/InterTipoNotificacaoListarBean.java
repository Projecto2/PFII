/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean;

import entidade.InterTipoNotificacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.InterTipoNotificacaoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterTipoNotificacaoListarBean implements Serializable
{
    @EJB
    private InterTipoNotificacaoFacade interTipoNotificacaoFacade;
    
    private InterTipoNotificacao interTipoNotificacao;

    private String descricaoPesq;

    private List<InterTipoNotificacao> listaTipoNotificacao;
    
    /**
     * Creates a new instance of InterTipoNotificacaoListarBean
     */
    public InterTipoNotificacaoListarBean()
    {
    }
    
    public static InterTipoNotificacaoListarBean getInstanciaBean()
    {
        return (InterTipoNotificacaoListarBean) GeradorCodigo.getInstanciaBean("interTipoNotificacaoListarBean");
    }

    public InterTipoNotificacao getInterTipoNotificacao()
    {
        return interTipoNotificacao;
    }

    public void setInterTipoNotificacao(InterTipoNotificacao interTipoNotificacao)
    {
        this.interTipoNotificacao = interTipoNotificacao;
    }

    public String getDescricaoPesq()
    {
        return descricaoPesq;
    }

    public void setDescricaoPesq(String descricaoPesq)
    {
        this.descricaoPesq = descricaoPesq;
    }

    public List<InterTipoNotificacao> getListaTipoNotificacao()
    {
        return listaTipoNotificacao;
    }

    public void setListaTipoNotificacao(List<InterTipoNotificacao> listaTipoNotificacao)
    {
        this.listaTipoNotificacao = listaTipoNotificacao;
    }

    public List<InterTipoNotificacao> findAll()
    {
        return interTipoNotificacaoFacade.findAll();
    }

    public List<InterTipoNotificacao> findByDescricao()
    {
        return interTipoNotificacaoFacade.findByDescricao(descricaoPesq);
    }
    
    public void pesquisar()
    {
        listaTipoNotificacao = interTipoNotificacaoFacade.findByDescricao(descricaoPesq);
    
        if (listaTipoNotificacao.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaTipoNotificacao.size() + " registo(s) retornado(s).");
        }
    }
}
