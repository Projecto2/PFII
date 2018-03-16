/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagResultadoTesteCompatibilidade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagResultadoTesteCompatibilidadeFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagResultadoTesteCompatibilidadeBean implements Serializable
{

    @EJB
    private DiagResultadoTesteCompatibilidadeFacade diagResultadoTesteCompatibilidadeFacade;
    private DiagResultadoTesteCompatibilidade diagResultadoTesteCompatibilidadePesquisar;
    private boolean pesquisar;
    List<DiagResultadoTesteCompatibilidade> itens;

    public static DiagResultadoTesteCompatibilidadeBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagResultadoTesteCompatibilidadeBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagResultadoTesteCompatibilidadeBean");
    }

    public static DiagResultadoTesteCompatibilidade getInstancia()
    {
        DiagResultadoTesteCompatibilidade diagResultadoTesteCompatibilidade = new DiagResultadoTesteCompatibilidade();

        return diagResultadoTesteCompatibilidade;
    }

    public DiagResultadoTesteCompatibilidade getDiagResultadoTesteCompatibilidadePesquisar()
    {
        if (diagResultadoTesteCompatibilidadePesquisar == null)
        {
            diagResultadoTesteCompatibilidadePesquisar = getInstancia();
        }
        return diagResultadoTesteCompatibilidadePesquisar;
    }

    public void setDiagResultadoTesteCompatibilidadePesquisar(DiagResultadoTesteCompatibilidade diagResultadoTesteCompatibilidadePesquisar)
    {
        this.diagResultadoTesteCompatibilidadePesquisar = diagResultadoTesteCompatibilidadePesquisar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagResultadoTesteCompatibilidade> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagResultadoTesteCompatibilidade> itens)
    {
        this.itens = itens;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagResultadoTesteCompatibilidadePesquisar = getInstancia();
        itens = new ArrayList<>();

        return "resultadoTesteCompatibilidade.xhtml?faces-redirect=true";
    }

    public void pesquisarResultadoTesteCompatibilidade()
    {
        itens = diagResultadoTesteCompatibilidadeFacade.findPesquisa(diagResultadoTesteCompatibilidadePesquisar);

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
