/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterTipoDoencaInternamento;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.InterTipoDoencaInternamentoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterTipoDoencaInternamentoListarBean implements Serializable
{

    @EJB
    private InterTipoDoencaInternamentoFacade interTipoDoencaInternamentoFacade;

    private InterTipoDoencaInternamento interTipoDoencaInternamento;

    private String descricaoPesq;

    private List<InterTipoDoencaInternamento> listaTiposDoenca;

    /**
     * Creates a new instance of TipoDoencaInternamentoInterBean
     */
    public InterTipoDoencaInternamentoListarBean()
    {
    }

    public static InterTipoDoencaInternamentoListarBean getInstanciaBean()
    {
        return (InterTipoDoencaInternamentoListarBean) GeradorCodigo.getInstanciaBean("interTipoDoencaInternamentoListarBean");
    }

    public InterTipoDoencaInternamento getInterTipoDoencaInternamento()
    {
        return interTipoDoencaInternamento;
    }

    public void setInterTipoDoencaInternamento(InterTipoDoencaInternamento interTipoDoencaInternamento)
    {
        this.interTipoDoencaInternamento = interTipoDoencaInternamento;
    }

    public String getDescricaoPesq()
    {
        return descricaoPesq;
    }

    public void setDescricaoPesq(String descricaoPesq)
    {
        this.descricaoPesq = descricaoPesq;
    }

    public List<InterTipoDoencaInternamento> getListaTiposDoenca()
    {
        return listaTiposDoenca;
    }

    public void setListaTiposDoenca(List<InterTipoDoencaInternamento> listaTiposDoenca)
    {
        this.listaTiposDoenca = listaTiposDoenca;
    }
    
    public List<InterTipoDoencaInternamento> findAll()
    {
        return interTipoDoencaInternamentoFacade.findAll();
    }

    public List<InterTipoDoencaInternamento> findByDescricao()
    {
        return interTipoDoencaInternamentoFacade.findByDescricao(descricaoPesq);
    }
    
    public void pesquisar()
    {
        listaTiposDoenca = interTipoDoencaInternamentoFacade.findByDescricao(descricaoPesq);
    
        if (listaTiposDoenca.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaTiposDoenca.size() + " registo(s) retornado(s).");
        }
    }
}
