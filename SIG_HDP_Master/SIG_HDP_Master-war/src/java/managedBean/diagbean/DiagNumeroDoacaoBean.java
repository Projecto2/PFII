/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagNumeroDoacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagNumeroDoacaoFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagNumeroDoacaoBean implements Serializable
{

    @EJB
    private DiagNumeroDoacaoFacade diagNumeroDoacaoFacade;
    private DiagNumeroDoacao diagNumeroDoacaoPesquisar;
    private boolean pesquisar;
    List<DiagNumeroDoacao> itens;

    public static DiagNumeroDoacaoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagNumeroDoacaoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagNumeroDoacaoBean");
    }

    public static DiagNumeroDoacao getInstancia()
    {
        DiagNumeroDoacao diagNumeroDoacao = new DiagNumeroDoacao();

        return diagNumeroDoacao;
    }

    public DiagNumeroDoacao getDiagNumeroDoacaoPesquisar()
    {
        if (diagNumeroDoacaoPesquisar == null)
        {
            diagNumeroDoacaoPesquisar = getInstancia();
        }
        return diagNumeroDoacaoPesquisar;
    }

    public void setDiagNumeroDoacaoPesquisar(DiagNumeroDoacao diagNumeroDoacaoPesquisar)
    {
        this.diagNumeroDoacaoPesquisar = diagNumeroDoacaoPesquisar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagNumeroDoacao> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagNumeroDoacao> itens)
    {
        this.itens = itens;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagNumeroDoacaoPesquisar = getInstancia();
        itens = new ArrayList<>();

        return "numeroDoacao.xhtml?faces-redirect=true";
    }

    public void pesquisarNumeroDoacao()
    {
        itens = diagNumeroDoacaoFacade.findPesquisa(diagNumeroDoacaoPesquisar);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
    }

}
