/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.avaliacaodedesempenho;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhAvaliacaoDeDesempenhoHomeBean implements Serializable
{

    private final int PESQUISAR_AVALIACOES = 0;
    private final int NOVA_AVALIACAO = 1;
    private final int CRITERIOS_DE_AVALIACAO = 2;

    private int paginaActual;

    /**
     * Creates a new instance of AreaTrabalhoBean
     */
    public RhAvaliacaoDeDesempenhoHomeBean ()
    {
    }

    public static RhAvaliacaoDeDesempenhoHomeBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhAvaliacaoDeDesempenhoHomeBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhAvaliacaoDeDesempenhoHomeBean");
    }

    public String limpar ()
    {
        paginaActual = PESQUISAR_AVALIACOES;

        return "avaliacaoDeDesempenhoHomeRh?faces-redirect=true";
    }

    public String redirecionarParaHome()
    {
        return "avaliacaoDeDesempenhoHomeRh?faces-redirect=true";
    }
    
    public boolean renderizarCriteriosDeAvaliacao ()
    {
        return paginaActual == CRITERIOS_DE_AVALIACAO;
    }

    public boolean renderizarNovaAvaliacao ()
    {
        return paginaActual == NOVA_AVALIACAO;
    }

    public boolean renderizarPesquisarAvaliacoes ()
    {
        return paginaActual == PESQUISAR_AVALIACOES;
    }
    
    public String corCriteriosDeAvaliacao ()
    {
        return paginaActual == CRITERIOS_DE_AVALIACAO ? "green": null;
    }

    public String corNovaAvaliacao ()
    {
        return paginaActual == NOVA_AVALIACAO ? "green": null;
    }

    public String corPesquisarAvaliacoes ()
    {
        return paginaActual == PESQUISAR_AVALIACOES ? "green": null;
    }

    public String selecionarCriteriosDeAvaliacao ()
    {
        paginaActual = CRITERIOS_DE_AVALIACAO;
        return redirecionarParaHome();
    }

    public String selecionarNovaAvaliacao ()
    {
        paginaActual = NOVA_AVALIACAO;
        RhAvaliacaoDeDesempenhoNovaBean.getInstanciaBean().prepararAvaliacaoDeDesempenho();
        return redirecionarParaHome();
    }

    public String selecionarPesquisarAvaliacoes ()
    {
        paginaActual = PESQUISAR_AVALIACOES;
        return redirecionarParaHome();
    }

    public String getTituloDaPagina ()
    {
        switch (paginaActual)
        {
            case NOVA_AVALIACAO:
                return "Nova Avaliação de Desempenho";
            case CRITERIOS_DE_AVALIACAO:
                return "Critérios de Avaliação";
            default:
                return "Pesquisar Avaliações de Desempenho";
        }
    }
    
}