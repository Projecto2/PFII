/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterAnotacaoEnfermagem;
import entidade.InterRegistoInternamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.InterAnotacaoEnfermagemFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterAnotacaoEnfermagemListarBean implements Serializable
{

    @EJB
    private InterAnotacaoEnfermagemFacade interAnotacaoEnfermagemFacade;

    private InterRegistoInternamento registoInternamento;

    private String nomeEnfermeiro, sobreNomeEnfermeiro, tipoServico;
    
    private Date dataRegisto, dataRegistoPesq1, dataRegistoPesq2;

    private List<InterAnotacaoEnfermagem> listaAnotacoes;

    /**
     * Creates a new instance of InterAnotacaoEnfermagemBean
     */
    public InterAnotacaoEnfermagemListarBean()
    {
    }

    public static InterAnotacaoEnfermagemListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterAnotacaoEnfermagemListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interAnotacaoEnfermagemListarBean");
    }

    public void init()
    {
        inicializar();
    }

    public void inicializar()
    {
        tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
    }

    public InterRegistoInternamento getRegistoInternamento()
    {
        return registoInternamento;
    }

    public void setRegistoInternamento(InterRegistoInternamento registoInternamento)
    {
        this.registoInternamento = registoInternamento;
    }

    public String getNomeEnfermeiro()
    {
        return nomeEnfermeiro;
    }

    public void setNomeEnfermeiro(String nomeEnfermeiro)
    {
        this.nomeEnfermeiro = nomeEnfermeiro;
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

    public Date getDataRegistoPesq1()
    {
        return dataRegistoPesq1;
    }

    public void setDataRegistoPesq1(Date dataRegistoPesq1)
    {
        this.dataRegistoPesq1 = dataRegistoPesq1;
    }

    public Date getDataRegistoPesq2()
    {
        return dataRegistoPesq2;
    }

    public void setDataRegistoPesq2(Date dataRegistoPesq2)
    {
        this.dataRegistoPesq2 = dataRegistoPesq2;
    }

    public List<InterAnotacaoEnfermagem> getListaAnotacoes()
    {
        return listaAnotacoes;
    }

    public void setListaAnotacoes(List<InterAnotacaoEnfermagem> listaAnotacoes)
    {
        this.listaAnotacoes = listaAnotacoes;
    }

    public void pesquisar()
    {
        listaAnotacoes = interAnotacaoEnfermagemFacade.pesquisarRegisto(tipoServico, nomeEnfermeiro, sobreNomeEnfermeiro, dataRegistoPesq1, dataRegistoPesq2, registoInternamento.getPkIdRegistoInternamento());

        if (listaAnotacoes.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaAnotacoes.size() + " registo(s) retornado(s).");
        }
    }

    public String anotacao(InterRegistoInternamento registoInternamamento)
    {
        listaAnotacoes = new ArrayList();

        this.registoInternamento = registoInternamamento;

        return "/faces/interVisao/interInternamento/internamentoListar/anotacaoEnfermagemListarInter.xhtml?faces-redirect=true";
    }
}
