/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagResultadoTriagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagResultadoTriagemFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagResultadoTriagemBean implements Serializable
{

    @EJB
    private DiagResultadoTriagemFacade diagResultadoTriagemFacade;
    private DiagResultadoTriagem diagResultadoTriagemPesquisar;
    private boolean pesquisar;
    List<DiagResultadoTriagem> itens;

    public static DiagResultadoTriagemBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagResultadoTriagemBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagResultadoTriagemBean");
    }

    public static DiagResultadoTriagem getInstancia()
    {
        DiagResultadoTriagem diagResultadoTriagem = new DiagResultadoTriagem();

        return diagResultadoTriagem;
    }

    public DiagResultadoTriagem getDiagResultadoTriagemPesquisar()
    {
        if (diagResultadoTriagemPesquisar == null)
        {
            diagResultadoTriagemPesquisar = getInstancia();
        }
        return diagResultadoTriagemPesquisar;
    }

    public void setDiagResultadoTriagemPesquisar(DiagResultadoTriagem diagResultadoTriagemPesquisar)
    {
        this.diagResultadoTriagemPesquisar = diagResultadoTriagemPesquisar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagResultadoTriagem> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagResultadoTriagem> itens)
    {
        this.itens = itens;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagResultadoTriagemPesquisar = getInstancia();
        itens = new ArrayList<>();

        return "resultadoTriagem.xhtml?faces-redirect=true";
    }

    public void pesquisarResultadoTriagem()
    {
        itens = diagResultadoTriagemFacade.findPesquisa(diagResultadoTriagemPesquisar);

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
