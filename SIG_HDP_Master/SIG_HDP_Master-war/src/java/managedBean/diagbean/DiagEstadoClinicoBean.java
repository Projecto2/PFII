/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagEstadoClinico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagEstadoClinicoFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagEstadoClinicoBean implements Serializable
{

    @EJB
    private DiagEstadoClinicoFacade diagEstadoClinicoFacade;
    private DiagEstadoClinico diagEstadoClinicoPesquisar;
    private boolean pesquisar;
    List<DiagEstadoClinico> itens;

    public static DiagEstadoClinicoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagEstadoClinicoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagEstadoClinicoBean");
    }

    public static DiagEstadoClinico getInstancia()
    {
        DiagEstadoClinico diagEstadoClinico = new DiagEstadoClinico();

        return diagEstadoClinico;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public List<DiagEstadoClinico> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagEstadoClinico> itens)
    {
        this.itens = itens;
    }

    public DiagEstadoClinico getDiagEstadoClinicoPesquisar()
    {
        if (diagEstadoClinicoPesquisar == null)
        {
            diagEstadoClinicoPesquisar = new DiagEstadoClinico();
        }
        return diagEstadoClinicoPesquisar;
    }

    public void setDiagEstadoClinicoPesquisar(DiagEstadoClinico diagEstadoClinicoPesquisar)
    {
        this.diagEstadoClinicoPesquisar = diagEstadoClinicoPesquisar;
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagEstadoClinicoPesquisar = getInstancia();
        itens = new ArrayList<>();

        return "estadoClinico.xhtml?faces-redirect=true";
    }

    public void pesquisarEstadoClinico()
    {
        itens = diagEstadoClinicoFacade.findPesquisa(diagEstadoClinicoPesquisar);

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
