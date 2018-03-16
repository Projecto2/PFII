/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlCentroHospitalar;
import entidade.GrlComuna;
import entidade.GrlContacto;
import entidade.GrlDistrito;
import entidade.GrlEndereco;
import entidade.GrlInstituicao;
import entidade.GrlMunicipio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.grlbean.carregamentoExcel.GrlCentroContactoExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlCentroEnderecoExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlCentroHospitalarExcelBean;
import managedBean.grlbean.carregamentoExcel.GrlCentroInstituicaoExcelBean;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlContactoFacade;
import sessao.GrlEnderecoFacade;
import sessao.GrlInstituicaoFacade;
import sessao.GrlMunicipioFacade;
import util.ItensAjaxBean;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlCentroHospitalarBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlMunicipioFacade municipioFacade;
    @EJB
    private GrlEnderecoFacade enderecoFacade;
    @EJB
    private GrlContactoFacade contactoFacade;
    @EJB
    private GrlInstituicaoFacade instituicaoFacade;
    @EJB
    private GrlCentroHospitalarFacade centroHospitalarFacade;

    /**
     *
     * Entidades
     */
    private GrlCentroHospitalar centroHospitalar, centroHospitalarPesquisa, centroHospitalarRemover;
    private List<GrlCentroHospitalar> centrosHospitalaresPesquisadosList;

    /**
     * Creates a new instance of CentroHospitalarBean
     */
    public GrlCentroHospitalarBean ()
    {
    }

    public static GrlCentroHospitalarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (GrlCentroHospitalarBean) c.getELContext().getELResolver()
            .getValue(c.getELContext(), null, "grlCentroHospitalarBean");
    }
    
    public static GrlCentroHospitalar getInstancia ()
    {
        GrlCentroHospitalar centro = new GrlCentroHospitalar();

        centro.setFkIdInstituicao(new GrlInstituicao());
        centro.getFkIdInstituicao().setFkIdContacto(new GrlContacto());
        centro.getFkIdInstituicao().setFkIdEndereco(new GrlEndereco());
        centro.getFkIdInstituicao().getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio());
        centro.getFkIdInstituicao().getFkIdEndereco().setFkIdComuna(new GrlComuna());
        centro.getFkIdInstituicao().getFkIdEndereco().setFkIdDistrito(new GrlDistrito());

        return centro;
    }

    public GrlCentroHospitalar getCentroHospitalar ()
    {
        if (this.centroHospitalar == null)
        {
            centroHospitalar = getInstancia();
        }

        return centroHospitalar;
    }

    public void setCentroHospitalar (GrlCentroHospitalar centroHospitalar)
    {

        if (centroHospitalar != null)
        {
            if (centroHospitalar.getFkIdInstituicao().getFkIdEndereco().getFkIdComuna() == null)
            {
                centroHospitalar.getFkIdInstituicao().getFkIdEndereco().setFkIdComuna(new GrlComuna());
            }
            if (centroHospitalar.getFkIdInstituicao().getFkIdEndereco().getFkIdDistrito() == null)
            {
                centroHospitalar.getFkIdInstituicao().getFkIdEndereco().setFkIdDistrito(new GrlDistrito());
            }

            ItensAjaxBean itensAjaxBean = ItensAjaxBean.getInstanciaBean();

            GrlMunicipio mn = municipioFacade.find(centroHospitalar.getFkIdInstituicao().getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio());

            itensAjaxBean.setPais(mn.getFkIdProvincia().getFkIdPais().getPkIdPais());
            itensAjaxBean.setProvincia(mn.getFkIdProvincia().getPkIdProvincia());
            itensAjaxBean.setMunicipio(mn.getPkIdMunicipio());
        }

        this.centroHospitalar = centroHospitalar;

    }

    public GrlCentroHospitalar getCentroHospitalarPesquisa ()
    {
        if (centroHospitalarPesquisa == null)
        {
            centroHospitalarPesquisa = getInstancia();

        }
        return centroHospitalarPesquisa;
    }

    public void setCentroHospitalarPesquisa (GrlCentroHospitalar centroHospitalarPesquisa)
    {
        this.centroHospitalarPesquisa = centroHospitalarPesquisa;
    }

    public GrlCentroHospitalar getCentroHospitalarRemover ()
    {
        return centroHospitalarRemover;
    }

    public void setCentroHospitalarRemover (GrlCentroHospitalar centroHospitalarRemover)
    {
        this.centroHospitalarRemover = centroHospitalarRemover;
    }

    public List<GrlCentroHospitalar> getCentrosHospitalaresPesquisadosList ()
    {
        return centrosHospitalaresPesquisadosList;
    }

    public void pesquisarCentrosHospitalares ()
    {
        centrosHospitalaresPesquisadosList = centroHospitalarFacade.findCentroHospitalar(centroHospitalarPesquisa);

        if (centrosHospitalaresPesquisadosList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();

            Integer comu = centroHospitalar.getFkIdInstituicao().getFkIdEndereco().getFkIdComuna().getPkIdComuna();
            Integer dist = centroHospitalar.getFkIdInstituicao().getFkIdEndereco().getFkIdDistrito().getPkIdDistrito();

            if (comu == null)
            {
                centroHospitalar.getFkIdInstituicao().getFkIdEndereco().setFkIdComuna(null);
            }
            if (dist == null)
            {
                centroHospitalar.getFkIdInstituicao().getFkIdEndereco().setFkIdDistrito(null);
            }

            enderecoFacade.create(centroHospitalar.getFkIdInstituicao().getFkIdEndereco());
            contactoFacade.create(centroHospitalar.getFkIdInstituicao().getFkIdContacto());
            instituicaoFacade.create(centroHospitalar.getFkIdInstituicao());
            centroHospitalarFacade.create(centroHospitalar);
            
            userTransaction.commit();
            Mensagem.sucessoMsg("Centro Hospitalar guardado com sucesso!");
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

        centroHospitalar = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (centroHospitalar.getPkIdCentro() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            Integer muni = centroHospitalar.getFkIdInstituicao().getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio();
            Integer comu = centroHospitalar.getFkIdInstituicao().getFkIdEndereco().getFkIdComuna().getPkIdComuna();
            Integer dist = centroHospitalar.getFkIdInstituicao().getFkIdEndereco().getFkIdDistrito().getPkIdDistrito();

            centroHospitalar.getFkIdInstituicao().getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio(muni));
            centroHospitalar.getFkIdInstituicao().getFkIdEndereco().setFkIdComuna(new GrlComuna(comu));
            centroHospitalar.getFkIdInstituicao().getFkIdEndereco().setFkIdDistrito(new GrlDistrito(dist));

            if (comu == null)
            {
                centroHospitalar.getFkIdInstituicao().getFkIdEndereco().setFkIdComuna(null);
            }
            if (dist == null)
            {
                centroHospitalar.getFkIdInstituicao().getFkIdEndereco().setFkIdDistrito(null);
            }

            contactoFacade.edit(centroHospitalar.getFkIdInstituicao().getFkIdContacto());
            enderecoFacade.edit(centroHospitalar.getFkIdInstituicao().getFkIdEndereco());
            instituicaoFacade.edit(centroHospitalar.getFkIdInstituicao());

            userTransaction.commit();

            Mensagem.sucessoMsg("Centro Hospitalar editado com sucesso! ");

            pesquisarCentrosHospitalares();
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

        centroHospitalar = null;

        return null;
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            centroHospitalarFacade.remove(centroHospitalarRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Centro Hospitalar removido com sucesso!");
            centroHospitalarRemover = null;
            pesquisarCentrosHospitalares();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Este Centro Hospitalar possui registro de actividades, impossível remover !");
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

    
    public List<GrlCentroHospitalar> findAll()
    {
        return centroHospitalarFacade.findAll();
    }
    
    public String limpar ()
    {
        centroHospitalar = null;
        return "centroHospitalarGrl?faces-redirect=true";
    }

     public void carregarExcel ()
     {
         centroHospitalar = getInstancia();
         centroHospitalarPesquisa = getInstancia();
         centrosHospitalaresPesquisadosList = new ArrayList<>();
         
         GrlCentroContactoExcelBean.getInstanciaBean().carregarContactoTabela();
         GrlCentroEnderecoExcelBean.getInstanciaBean().carregarEnderecoTabela();
         GrlCentroInstituicaoExcelBean.getInstanciaBean().carregarInstituicaoTabela();
         GrlCentroHospitalarExcelBean.getInstanciaBean().carregarCentroHospitalarTabela();
     }
    
}
