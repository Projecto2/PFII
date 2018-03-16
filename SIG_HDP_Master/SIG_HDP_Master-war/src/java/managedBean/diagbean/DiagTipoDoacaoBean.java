/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagTipoDoacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import static managedBean.diagbean.DiagTipoDoacaoBean.getInstancia;
import sessao.DiagTipoDoacaoFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipoDoacaoBean implements Serializable
{

    @EJB
    private DiagTipoDoacaoFacade diagTipoDoacaoFacade;
    private DiagTipoDoacao diagTipoDoacaoPesquisar;
    private boolean pesquisar;
    List<DiagTipoDoacao> itens;

    public static DiagTipoDoacaoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTipoDoacaoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTipoDoacaoBean");
    }

    public static DiagTipoDoacao getInstancia()
    {
        DiagTipoDoacao diagTipoDoacao = new DiagTipoDoacao();

        return diagTipoDoacao;
    }

    public DiagTipoDoacao getDiagTipoDoacaoPesquisar()
    {
        if (diagTipoDoacaoPesquisar == null)
        {
            diagTipoDoacaoPesquisar = getInstancia();
        }
        return diagTipoDoacaoPesquisar;
    }

    public void setDiagTipoDoacaoPesquisar(DiagTipoDoacao diagTipoDoacaoPesquisar)
    {
        this.diagTipoDoacaoPesquisar = diagTipoDoacaoPesquisar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagTipoDoacao> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagTipoDoacao> itens)
    {
        this.itens = itens;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagTipoDoacaoPesquisar = getInstancia();
        itens = new ArrayList<>();

        return "tipoDoacao.xhtml?faces-redirect=true";
    }

    public void pesquisarTipoDoacao()
    {
        itens = diagTipoDoacaoFacade.findPesquisa(diagTipoDoacaoPesquisar);

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
