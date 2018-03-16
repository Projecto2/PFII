/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagCaracterTransfusao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagCaracterTransfusaoFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagCaracterTransfusaoBean implements Serializable
{

    @EJB
    private DiagCaracterTransfusaoFacade diagCaracterTransfusaoFacade;
    private DiagCaracterTransfusao diagCaracterTransfusaoPesquisar;
    private boolean pesquisar;
    List<DiagCaracterTransfusao> itens;

    public static DiagCaracterTransfusaoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagCaracterTransfusaoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagCaracterTransfusaoBean");
    }

    public static DiagCaracterTransfusao getInstancia()
    {
        DiagCaracterTransfusao diagCaracterTransfusao = new DiagCaracterTransfusao();

        return diagCaracterTransfusao;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagCaracterTransfusao> getItens()
    {
         if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagCaracterTransfusao> itens)
    {
        this.itens = itens;
    }

    public DiagCaracterTransfusao getDiagCaracterTransfusaoPesquisar()
    {
        if (diagCaracterTransfusaoPesquisar == null)
        {
            diagCaracterTransfusaoPesquisar = new DiagCaracterTransfusao();
        }
        return diagCaracterTransfusaoPesquisar;
    }

    public void setDiagCaracterTransfusaoPesquisar(DiagCaracterTransfusao diagCaracterTransfusaoPesquisar)
    {
        this.diagCaracterTransfusaoPesquisar = diagCaracterTransfusaoPesquisar;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagCaracterTransfusaoPesquisar = getInstancia();
        itens = new ArrayList<>();

        return "caracterTransfusao.xhtml?faces-redirect=true";
    }

    public void pesquisarCaracterTransfusao()
    {
        itens = diagCaracterTransfusaoFacade.findPesquisa(diagCaracterTransfusaoPesquisar);

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
