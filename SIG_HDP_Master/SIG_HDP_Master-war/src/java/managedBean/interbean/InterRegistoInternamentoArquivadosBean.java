/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean;

import entidade.InterRegistoInternamento;
import entidade.SegConta;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.segbean.SegLoginBean;
import sessao.InterRegistoInternamentoFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterRegistoInternamentoArquivadosBean implements Serializable
{
    @EJB
    private InterRegistoInternamentoFacade interRegistoInternamentoFacade;

    private List<InterRegistoInternamento> listaRegistos;
    
    private String nomePacientePesq, nomeMeioPacientePesq, sobreNomePacientePesq, sexoPacientePesq;
    private String numeroInscricaoPesq, numeroPacientePesq, doencaPesq, tipoServico;
    
    private int cama, tituloAlta;    
    
    /**
     * Creates a new instance of InterRegistoInternamentoArquivadosBean
     */
    public InterRegistoInternamentoArquivadosBean()
    {
    }

    public static InterRegistoInternamentoArquivadosBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterRegistoInternamentoArquivadosBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interRegistoInternamentoArquivadosBean");
    }

    public void init()
    {
        inicializar();
    }

    public void inicializar()
    {
//        tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
    }
    
    public List<InterRegistoInternamento> getListaRegistos()
    {
        return listaRegistos;
    }

    public void setListaRegistos(List<InterRegistoInternamento> listaRegistos)
    {
        this.listaRegistos = listaRegistos;
    }

    public String getNomePacientePesq()
    {
        return nomePacientePesq;
    }

    public void setNomePacientePesq(String nomePacientePesq)
    {
        this.nomePacientePesq = nomePacientePesq;
    }

    public String getNomeMeioPacientePesq()
    {
        return nomeMeioPacientePesq;
    }

    public void setNomeMeioPacientePesq(String nomeMeioPacientePesq)
    {
        this.nomeMeioPacientePesq = nomeMeioPacientePesq;
    }

    public String getSobreNomePacientePesq()
    {
        return sobreNomePacientePesq;
    }

    public void setSobreNomePacientePesq(String sobreNomePacientePesq)
    {
        this.sobreNomePacientePesq = sobreNomePacientePesq;
    }

    public String getNumeroInscricaoPesq()
    {
        return numeroInscricaoPesq;
    }

    public void setNumeroInscricaoPesq(String numeroInscricaoPesq)
    {
        this.numeroInscricaoPesq = numeroInscricaoPesq;
    }

    public String getNumeroPacientePesq()
    {
        return numeroPacientePesq;
    }

    public void setNumeroPacientePesq(String numeroPacientePesq)
    {
        this.numeroPacientePesq = numeroPacientePesq;
    }

    public String getDoencaPesq()
    {
        return doencaPesq;
    }

    public void setDoencaPesq(String doencaPesq)
    {
        this.doencaPesq = doencaPesq;
    }

    public int getCama()
    {
        return cama;
    }

    public void setCama(int cama)
    {
        this.cama = cama;
    }

    public int getTituloAlta()
    {
        return tituloAlta;
    }

    public void setTituloAlta(int tituloAlta)
    {
        this.tituloAlta = tituloAlta;
    }

    public String getSexoPacientePesq()
    {
        return sexoPacientePesq;
    }

    public void setSexoPacientePesq(String sexoPacientePesq)
    {
        this.sexoPacientePesq = sexoPacientePesq;
    }
 
    
    
    public void pesquisarArquivados() 
    { 
         listaRegistos = interRegistoInternamentoFacade.pesquisarRegistoArquivados(null, tipoServico, numeroPacientePesq, numeroInscricaoPesq, nomePacientePesq, nomeMeioPacientePesq, sobreNomePacientePesq, sexoPacientePesq, InterCamaListarBean.getInstanciaBean().getFk_id_sala(), cama, null);
         
         if (listaRegistos.isEmpty())
             Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
         else
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaRegistos.size() + " registo(s) retornado(s).");
    }
    
}
