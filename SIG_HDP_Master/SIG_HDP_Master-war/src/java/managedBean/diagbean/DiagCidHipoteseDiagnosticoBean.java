/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AmbCidAgrupamentos;
import entidade.AmbCidCapitulos;
import entidade.AmbCidCategorias;
import managedBean.interbean.*;
import entidade.AmbCidSubcategorias;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.ambbean.cid.AmbCidAgrupamentosBean;
import util.amb.AmbCidHipoteseDiagnosticoAbstract;
import sessao.AmbCidAgrupamentosFacade;
import sessao.AmbCidCapitulosFacade;
import sessao.AmbCidCategoriasFacade;
import sessao.AmbCidConfiguracoesFacade;
import sessao.AmbCidDoencasPrioridadesFacade;
import sessao.AmbCidPerfisFacade;
import sessao.AmbCidSubcategoriasFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.RhProfissaoFacade;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class DiagCidHipoteseDiagnosticoBean implements Serializable
{

    @EJB
    private AmbCidDoencasPrioridadesFacade ambCidDoencasPrioridadesFacade;

    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade;

    @EJB
    private AmbCidCategoriasFacade ambCidCategoriasFacade;

    @EJB
    private AmbCidAgrupamentosFacade ambCidAgrupamentosFacade;

    @EJB
    private AmbCidCapitulosFacade ambCidCapitulosFacade;

    @EJB
    private RhProfissaoFacade rhProfissaoFacade;

    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;

    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;

    @EJB
    private AmbCidConfiguracoesFacade ambCidConfiguracoesFacade;

    private String perfilPreferencial = util.amb.Defs.CID_10;

    private String prioridadesPreferencial = "Todas Doenças";

    private String pkIdCapitulo;
    private String pkIdAgrupamento;
    
    private String nomeCategoriaDoenca;

    private List<AmbCidAgrupamentos> listaAgrupamentos;
    
    private List<AmbCidCategorias> listaCategorias;

    /**
     * Creates a new instance of AmbCidHipoteseDiagnosticoBean
     */
    public DiagCidHipoteseDiagnosticoBean()
    {
    }

    public static InterCidHipoteseDiagnosticoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterCidHipoteseDiagnosticoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagCidHipoteseDiagnosticoBean");
    }

    public String getPerfilPreferencial()
    {
        return perfilPreferencial;
    }

    public void setPerfilPreferencial(String perfilPreferencial)
    {
        this.perfilPreferencial = perfilPreferencial;
    }

    public String getPrioridadesPreferencial()
    {
        return prioridadesPreferencial;
    }

    public void setPrioridadesPreferencial(String prioridadesPreferencial)
    {
        this.prioridadesPreferencial = prioridadesPreferencial;
    }

    public List<AmbCidCapitulos> getListaCapitulos()
    {
        return ambCidCapitulosFacade.findAllOrderByNome();
    }

    public String getPkIdCapitulo()
    {
        return pkIdCapitulo;
    }

    public void setPkIdCapitulo(String pkIdCapitulo)
    {
        this.pkIdCapitulo = pkIdCapitulo;
    }

    public String getPkIdAgrupamento()
    {
        return pkIdAgrupamento;
    }

    public void setPkIdAgrupamento(String pkIdAgrupamento)
    {
        this.pkIdAgrupamento = pkIdAgrupamento;
    }

    public String getNomeCategoriaDoenca()
    {
        return nomeCategoriaDoenca;
    }

    public void setNomeCategoriaDoenca(String nomeCategoriaDoenca)
    {
        this.nomeCategoriaDoenca = nomeCategoriaDoenca;
    }

    public List<AmbCidAgrupamentos> getListaAgrupamentos()
    {
        if (listaAgrupamentos == null)
        {
            listaAgrupamentos = new ArrayList<>();
        }
        return listaAgrupamentos;
    }

    public void setListaAgrupamentos(List<AmbCidAgrupamentos> listaAgrupamentos)
    {
        this.listaAgrupamentos = listaAgrupamentos;
    }

    public List<AmbCidCategorias> getListaCategorias()
    {
        if (listaCategorias == null)
        {
            listaCategorias = new ArrayList<>();
        }
        return listaCategorias;
    }

    public void setListaCategorias(List<AmbCidCategorias> listaCategorias)
    {
        this.listaCategorias = listaCategorias;
    }

    public void resetAmbiCidAgrupamentos()
    {
        listaAgrupamentos = ambCidAgrupamentosFacade.findAllByPkIdCapitulosOrderByNome(pkIdCapitulo);
    }
    
    public void resetAmbiCidCategorias()
    {
        listaCategorias = ambCidCategoriasFacade.findAllByPkIdAgrupamentosOrderByNome(pkIdAgrupamento);
    }
}
