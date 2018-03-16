/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagResultadoExameCandidato;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagResultadoExameCandidatoFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagResultadoExameCandidatoBean implements Serializable
{

    @EJB
    private DiagResultadoExameCandidatoFacade diagResultadoExameCandidatoFacade;
    private DiagResultadoExameCandidato diagResultadoExameCandidatoPesquisar;
    private boolean pesquisar;
    List<DiagResultadoExameCandidato> itens;

    public static DiagResultadoExameCandidatoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagResultadoExameCandidatoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagResultadoExameCandidatoBean");
    }

    public static DiagResultadoExameCandidato getInstancia()
    {
        DiagResultadoExameCandidato diagResultadoExameCandidato = new DiagResultadoExameCandidato();

        return diagResultadoExameCandidato;
    }

    public DiagResultadoExameCandidato getDiagResultadoExameCandidatoPesquisar()
    {
        if (diagResultadoExameCandidatoPesquisar == null)
        {
            diagResultadoExameCandidatoPesquisar = getInstancia();
        }
        return diagResultadoExameCandidatoPesquisar;
    }

    public void setDiagResultadoExameCandidatoPesquisar(DiagResultadoExameCandidato diagResultadoExameCandidatoPesquisar)
    {
        this.diagResultadoExameCandidatoPesquisar = diagResultadoExameCandidatoPesquisar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagResultadoExameCandidato> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagResultadoExameCandidato> itens)
    {
        this.itens = itens;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagResultadoExameCandidatoPesquisar = getInstancia();
        itens = new ArrayList<>();

        return "resultadoExameCandidato.xhtml?faces-redirect=true";
    }

    public void pesquisarResultadoExameCandidato()
    {
        itens = diagResultadoExameCandidatoFacade.findPesquisa(diagResultadoExameCandidatoPesquisar);

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
