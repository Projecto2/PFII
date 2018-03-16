/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlComuna;
import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlProvincia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.transaction.UserTransaction;
import managedBean.grlbean.carregamentoExcel.GrlComunaExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlMunicipioExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlPaisExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlProvinciaExcelBean;
import sessao.GrlComunaFacade;
import sessao.GrlMunicipioFacade;
import sessao.GrlProvinciaFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class GrlComunaBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlProvinciaFacade provinciaFacade;
    @EJB
    private GrlMunicipioFacade municipioFacade;
    @EJB
    private GrlComunaFacade comunaFacade;

    /**
     *
     * Entidades
     */
    private GrlComuna comunaPesquisa;
    private List<GrlComuna> comunasPesquisadasList;
    private List<GrlMunicipio> municipiosPorProvinciaList;
    private List<GrlProvincia> provinciasPorPaisList;

    /**
     * Creates a new instance of MunicipioBean
     */
    public GrlComunaBean ()
    {
    }

    public GrlComuna getInstancia ()
    {
        GrlComuna com = new GrlComuna();
        com.setFkIdMunicipio(new GrlMunicipio());
        com.getFkIdMunicipio().setFkIdProvincia(new GrlProvincia());
        com.getFkIdMunicipio().getFkIdProvincia().setFkIdPais(new GrlPais());

        return com;
    }

    public GrlComuna getComunaPesquisa ()
    {
        if (comunaPesquisa == null)
        {
            comunaPesquisa = getInstancia();
        }
        return comunaPesquisa;
    }

    public void setComunaPesquisa (GrlComuna comunaPesquisa)
    {
        this.comunaPesquisa = comunaPesquisa;
    }

    public List<GrlComuna> getComunasPesquisadasList ()
    {
        return comunasPesquisadasList;
    }

    public List<GrlProvincia> getProvinciasPorPaisList ()
    {
        return provinciasPorPaisList;
    }

    public List<GrlMunicipio> getMunicipiosPorProvinciaList ()
    {
        return municipiosPorProvinciaList;
    }

    public void pesquisarComunas ()
    {
        comunasPesquisadasList = comunaFacade.findComuna(comunaPesquisa);

        if (comunasPesquisadasList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
    }

    public void changePaisPesquisa (ValueChangeEvent e)
    {
        getComunaPesquisa().getFkIdMunicipio().getFkIdProvincia().getFkIdPais().setPkIdPais((Integer) e.getNewValue());

        provinciasPorPaisList = provinciaFacade.pesquisaPorPais(comunaPesquisa.getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais());
        comunaPesquisa.getFkIdMunicipio().getFkIdProvincia().setPkIdProvincia(null);
        municipiosPorProvinciaList = null;
    }

    public void changeProvinciaPesquisa (ValueChangeEvent e)
    {
        getComunaPesquisa().getFkIdMunicipio().getFkIdProvincia().setPkIdProvincia((Integer) e.getNewValue());

        municipiosPorProvinciaList = municipioFacade.pesquisaPorProvincia(comunaPesquisa.getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia());
        comunaPesquisa.getFkIdMunicipio().getFkIdProvincia().setPkIdProvincia(null);
    }

    public void carregarExcel ()
    {
        comunaPesquisa = getInstancia();
        comunasPesquisadasList = new ArrayList<>();

//        GrlPaisExcelBean.getInstanciaBean().carregarPaisTabela();
//        GrlProvinciaExcelBean.getInstanciaBean().carregarProvinciaTabela();
//        GrlMunicipioExcelBean.getInstanciaBean().carregarMunicipioTabela();
        GrlComunaExcelBean.getInstanciaBean().carregarComunaTabela();
    }
}
