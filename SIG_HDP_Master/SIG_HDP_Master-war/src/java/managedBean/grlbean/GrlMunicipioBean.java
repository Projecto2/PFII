/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlProvincia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.grlbean.carregamentoExcel.GrlMunicipioExcelBean;
import sessao.GrlMunicipioFacade;
import sessao.GrlProvinciaFacade;
import util.ItensAjaxBean;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlMunicipioBean implements Serializable
{
    
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private GrlProvinciaFacade provinciaFacade;
    @EJB
    private GrlMunicipioFacade municipioFacade;

    /**
     *
     * Entidades
     */
    private GrlMunicipio municipio,

    /**
     * Entidades
     */
    municipioPesquisa,

    /**
     * Entidades
     */
    municipioRemover;
    private List<GrlMunicipio> municipiosPesquisadosList;
    private List<GrlProvincia> provinciasPorPaisList;

    /**
     * Creates a new instance of MunicipioBean
     */
    public GrlMunicipioBean ()
    {
    }
    
    public GrlMunicipio getInstanciaMunicipio ()
    {
        GrlMunicipio munic = new GrlMunicipio();
        munic.setFkIdProvincia(new GrlProvincia());
        
        return munic;
    }
    
    public GrlMunicipio getMunicipio ()
    {
        if (this.municipio == null)
        {
            municipio = getInstanciaMunicipio();
        }
        
        return municipio;
    }
    
    public void setMunicipio (GrlMunicipio municipio)
    {
        this.municipio = municipio;
        
        if (municipio != null)
        {
            ItensAjaxBean itensAjaxBean = obterItensAjaxBean();
            itensAjaxBean.setPais(municipio.getFkIdProvincia().getFkIdPais().getPkIdPais());
            itensAjaxBean.setProvincia(municipio.getFkIdProvincia().getPkIdProvincia());
        }
    }
    
    public GrlMunicipio getMunicipioPesquisa ()
    {
        if (municipioPesquisa == null)
        {
            municipioPesquisa = getInstanciaMunicipio();
            municipioPesquisa.getFkIdProvincia().setFkIdPais(new GrlPais());
        }
        return municipioPesquisa;
    }
    
    public void setMunicipioPesquisa (GrlMunicipio municipioPesquisa)
    {
        this.municipioPesquisa = municipioPesquisa;
    }
    
    public GrlMunicipio getMunicipioRemover ()
    {
        return municipioRemover;
    }
    
    public void setMunicipioRemover (GrlMunicipio municipioRemover)
    {
        this.municipioRemover = municipioRemover;
    }
    
    public List<GrlMunicipio> getMunicipiosPesquisadosList ()
    {
        return municipiosPesquisadosList;
    }
    
    public List<GrlProvincia> getProvinciasPorPaisList ()
    {
        return provinciasPorPaisList;
    }
    
    public void pesquisarMunicipios ()
    {
        if (municipiosPesquisadosList == null)
        {
            municipiosPesquisadosList = new ArrayList<>();
        }
        
        municipiosPesquisadosList = municipioFacade.findMunicipio(municipioPesquisa);
        
        if (municipiosPesquisadosList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else{
            Mensagem.sucessoMsg(""+municipiosPesquisadosList.size()+" Registos Retornados Com Sucesso!");
        }
    }
    
    
    
    public void changePaisPesquisa (ValueChangeEvent e)
    {
        getMunicipioPesquisa().getFkIdProvincia().setPkIdProvincia((Integer) e.getNewValue());
        
        provinciasPorPaisList = provinciaFacade.pesquisaPorPais(municipioPesquisa.getFkIdProvincia().getPkIdProvincia());
        
        municipioPesquisa.getFkIdProvincia().setPkIdProvincia(null);
    }
    
//    public String create ()
//    {
//        try
//        {
//            userTransaction.begin();
//            municipioFacade.create(municipio);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("Município guardado com sucesso!");
//        }
//        catch (Exception e)
//        {
//            try
//            {
//                Mensagem.erroMsg(e.toString());
//                userTransaction.rollback();
//            }
//            catch (IllegalStateException | SecurityException | SystemException ex)
//            {
//                Mensagem.erroMsg("Rollback: " + ex.toString());
//            }
//        }
//        
//        municipio = null;
//        
//        return null;
//    }
    
//    public String edit ()
//    {
//        try
//        {
//            userTransaction.begin();
//            if (municipio.getPkIdMunicipio() == null)
//            {
//                throw new NullPointerException("PK -> NULL");
//            }
//            int prov = municipio.getFkIdProvincia().getPkIdProvincia();
//            municipio.setFkIdProvincia(new GrlProvincia(prov));
//            municipioFacade.edit(municipio);
//            userTransaction.commit();
//            
//            Mensagem.sucessoMsg("Município editado com sucesso! ");
//            pesquisarMunicipios();
//        }
//        catch (Exception e)
//        {
//            try
//            {
//                userTransaction.rollback();
//                Mensagem.erroMsg(e.toString());
//            }
//            catch (IllegalStateException | SecurityException | SystemException ex)
//            {
//                Mensagem.erroMsg("Rollback: " + ex.toString());
//            }
//        }
//        
//        municipio = null;
//        
//        return null;
//    }
    
//    public String remove ()
//    {
//        try
//        {
//            userTransaction.begin();
//            
//            municipioFacade.remove(municipioRemover);
//            
//            userTransaction.commit();
//            
//            Mensagem.sucessoMsg("Município removido com sucesso!");
//            municipioRemover = null;
//            pesquisarMunicipios();
//        }
//        catch (Exception e)
//        {
//            try
//            {
//                e.printStackTrace();
//                Mensagem.erroMsg("Este município possui registro de actividades, impossível remover !");
//                Mensagem.erroMsg(e.toString());
//                userTransaction.rollback();
//            }
//            catch (IllegalStateException | SecurityException | SystemException ex)
//            {
//                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
//            }
//        }
//        
//        return null;
//    }
    
    public String limpar ()
    {
        municipio = null;
        return "municipioGrl?faces-redirect=true";
    }
    
    private ItensAjaxBean obterItensAjaxBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (ItensAjaxBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "itensAjaxBean");
    }
    
    
    public void carregar()
    {
        GrlMunicipioExcelBean.getInstanciaBean().carregarMunicipioTabela();
        
        Mensagem.sucessoMsg("Dados Carregados Com Sucesso!");
        
        
        pesquisarMunicipios();
    }
}
