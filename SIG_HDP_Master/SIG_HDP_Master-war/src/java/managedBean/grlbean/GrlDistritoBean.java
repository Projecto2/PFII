/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlDistrito;
import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlProvincia;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.grlbean.carregamentoExcel.GrlDistritoExcelBean;
import sessao.GrlDistritoFacade;
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
public class GrlDistritoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlProvinciaFacade provinciaFacade;
    @EJB
    private GrlMunicipioFacade municipioFacade;
    @EJB
    private GrlDistritoFacade distritoFacade;

    /**
     *
     * Entidades
     */
    private GrlDistrito distrito,

    /**
     * Entidades
     */
    distritoPesquisa,

    /**
     * Entidades
     */
    distritoRemover;
    private List<GrlDistrito> distritosPesquisadosList;
    private List<GrlProvincia> provinciasPorPaisList;
    private List<GrlMunicipio> municipiosPorProvinciasList;

    /**
     * Creates a new instance of DistritoBean
     */
    public GrlDistritoBean ()
    {
    }

    public GrlDistrito getInstanciaDistrito ()
    {
        GrlDistrito com = new GrlDistrito();
        com.setFkIdMunicipio(new GrlMunicipio());

        return com;
    }

    public GrlDistrito getDistrito ()
    {
        if (this.distrito == null)
        {
            distrito = getInstanciaDistrito();
        }

        return distrito;
    }

    public void setDistrito (GrlDistrito distrito)
    {
        this.distrito = distrito;

        if (distrito != null)
        {
            ItensAjaxBean itensAjaxBean = obterItensAjaxBean();
            itensAjaxBean.setPais(distrito.getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais());
            itensAjaxBean.setProvincia(distrito.getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia());
        }
    }

    public GrlDistrito getDistritoPesquisa ()
    {
        if (distritoPesquisa == null)
        {
            distritoPesquisa = getInstanciaDistrito();
            distritoPesquisa.getFkIdMunicipio().setFkIdProvincia(new GrlProvincia());
            distritoPesquisa.getFkIdMunicipio().getFkIdProvincia().setFkIdPais(new GrlPais());
            
        }
        return distritoPesquisa;
    }

    public void setDistritoPesquisa (GrlDistrito distritoPesquisa)
    {
        this.distritoPesquisa = distritoPesquisa;
    }

    public GrlDistrito getDistritoRemover ()
    {
        return distritoRemover;
    }

    public void setDistritoRemover (GrlDistrito distritoRemover)
    {
        this.distritoRemover = distritoRemover;
    }

    public List<GrlDistrito> getDistritosPesquisadosList ()
    {
        return distritosPesquisadosList;
    }

    public List<GrlProvincia> getProvinciasPorPaisList ()
    {
        return provinciasPorPaisList;
    }

    public List<GrlMunicipio> getMunicipiosPorProvinciasList ()
    {
        return municipiosPorProvinciasList;
    }
    
    public void pesquisarDistritos ()
    {
        distritosPesquisadosList = distritoFacade.findDistrito(distritoPesquisa);

        if (distritosPesquisadosList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
    }

    public void changePaisPesquisa (ValueChangeEvent e)
    {
        getDistritoPesquisa().getFkIdMunicipio().getFkIdProvincia().getFkIdPais().setPkIdPais((Integer) e.getNewValue());

        provinciasPorPaisList = provinciaFacade.pesquisaPorPais(distritoPesquisa.getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais());
        
        distritoPesquisa.getFkIdMunicipio().getFkIdProvincia().setPkIdProvincia(null);
        municipiosPorProvinciasList = null;
    }

    public void changeProvinciaPesquisa (ValueChangeEvent e)
    {
        getDistritoPesquisa().getFkIdMunicipio().getFkIdProvincia().setPkIdProvincia((Integer) e.getNewValue());
        
        municipiosPorProvinciasList = municipioFacade.pesquisaPorProvincia(distritoPesquisa.getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia());

        distritoPesquisa.getFkIdMunicipio().setPkIdMunicipio(null);
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();
            distritoFacade.create(distrito);
            userTransaction.commit();
            Mensagem.sucessoMsg("Distrito guardado com sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        distrito = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (distrito.getPkIdDistrito() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            int muni = distrito.getFkIdMunicipio().getPkIdMunicipio();
            distrito.setFkIdMunicipio(new GrlMunicipio(muni));
            distritoFacade.edit(distrito);
            userTransaction.commit();

            Mensagem.sucessoMsg("Distrito editado com sucesso! ");
            pesquisarDistritos();
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        distrito = null;

        return null;
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            distritoFacade.remove(distritoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Distrito removido com sucesso!");
            distritoRemover = null;
            pesquisarDistritos();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Este distrito possui registro de actividades, impossível remover !");
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return null;
    }

    public String limpar ()
    {
        distrito = null;
        return "distritoGrl?faces-redirect=true";
    }

    private ItensAjaxBean obterItensAjaxBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (ItensAjaxBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "itensAjaxBean");
    }
    
    public void carregar()
    {
        GrlDistritoExcelBean.getInstanciaBean().carregarDistritoTabela();
        
        Mensagem.sucessoMsg("Dados Carregados Com Sucesso!");
        
        pesquisarDistritos();
    }
}
