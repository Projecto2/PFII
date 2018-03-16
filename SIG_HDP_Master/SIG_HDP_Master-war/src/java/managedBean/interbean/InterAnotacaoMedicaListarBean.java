/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterAnotacaoMedica;
import entidade.InterRegistoInternamento;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.segbean.SegLoginBean;
import sessao.InterAnotacaoMedicaFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterAnotacaoMedicaListarBean implements Serializable
{

    @EJB
    private InterAnotacaoMedicaFacade interAnotacaoMedicaFacade;

    private InterRegistoInternamento registoInternamento;

    private String nomeMedico, sobreNomeMedico, tipoServico;

    private Date dataRegisto, dataRegistoPesq1, dataRegistoPesq2;

    private List<InterAnotacaoMedica> listaAnotacoes;

    /**
     * Creates a new instance of InterAnotacaoMedicaBean
     */
    public InterAnotacaoMedicaListarBean()
    {
    }

    public static InterAnotacaoMedicaListarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterAnotacaoMedicaListarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interAnotacaoMedicaListarBean");
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

    public String getNomeMedico()
    {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico)
    {
        this.nomeMedico = nomeMedico;
    }

    public String getSobreNomeMedico()
    {
        return sobreNomeMedico;
    }

    public void setSobreNomeMedico(String sobreNomeMedico)
    {
        this.sobreNomeMedico = sobreNomeMedico;
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

    public List<InterAnotacaoMedica> getListaAnotacoes()
    {
        return listaAnotacoes;
    }

    public void setListaAnotacoes(List<InterAnotacaoMedica> listaAnotacoes)
    {
        this.listaAnotacoes = listaAnotacoes;
    }

    public void pesquisar()
    {
        listaAnotacoes = interAnotacaoMedicaFacade.pesquisarRegisto(tipoServico, nomeMedico, sobreNomeMedico, dataRegistoPesq1, dataRegistoPesq2, registoInternamento.getPkIdRegistoInternamento());

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

        return "/faces/interVisao/interInternamento/internamentoListar/anotacaoMedicaListarInter.xhtml?faces-redirect=true";

    }

}
