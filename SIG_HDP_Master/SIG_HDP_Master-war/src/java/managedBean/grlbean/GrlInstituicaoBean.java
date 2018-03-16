/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlComuna;
import entidade.GrlContacto;
import entidade.GrlDistrito;
import entidade.GrlEndereco;
import entidade.GrlInstituicao;
import entidade.GrlMunicipio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
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
public class GrlInstituicaoBean implements Serializable
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

    /**
     *
     * Entidades
     */
    private GrlInstituicao instituicao, instituicaoPesquisa, instituicaoRemover;
    private List<GrlInstituicao> InstituicoesPesquisadasList;

    /**
     * Creates a new instance of InstituicaoBean
     */
    public GrlInstituicaoBean ()
    {
    }

    public static GrlInstituicao getInstanciaInstituicao ()
    {
        GrlInstituicao item = new GrlInstituicao();

        item.setFkIdContacto(new GrlContacto());
        item.setFkIdEndereco(new GrlEndereco());
        item.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio());
        item.getFkIdEndereco().setFkIdComuna(new GrlComuna());
        item.getFkIdEndereco().setFkIdDistrito(new GrlDistrito());

        return item;
    }

    public GrlInstituicao getInstituicao ()
    {
        if (this.instituicao == null)
        {
            instituicao = getInstanciaInstituicao();
        }

        return instituicao;
    }

    public void setInstituicao (GrlInstituicao instituicao)
    {

        if (instituicao != null)
        {
            if (instituicao.getFkIdEndereco().getFkIdComuna() == null)
            {
                instituicao.getFkIdEndereco().setFkIdComuna(new GrlComuna());
            }
            if (instituicao.getFkIdEndereco().getFkIdDistrito() == null)
            {
                instituicao.getFkIdEndereco().setFkIdDistrito(new GrlDistrito());
            }

            ItensAjaxBean itensAjaxBean = obterItensAjaxBean();

            GrlMunicipio mn = municipioFacade.find(instituicao.getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio());

            itensAjaxBean.setPais(mn.getFkIdProvincia().getFkIdPais().getPkIdPais());
            itensAjaxBean.setProvincia(mn.getFkIdProvincia().getPkIdProvincia());
            itensAjaxBean.setMunicipio(mn.getPkIdMunicipio());
        }

        this.instituicao = instituicao;

    }

    public GrlInstituicao getInstituicaoPesquisa ()
    {
        if (instituicaoPesquisa == null)
        {
            instituicaoPesquisa = getInstanciaInstituicao();

        }
        return instituicaoPesquisa;
    }

    public void setInstituicaoPesquisa (GrlInstituicao instituicaoPesquisa)
    {
        this.instituicaoPesquisa = instituicaoPesquisa;
    }

    public GrlInstituicao getInstituicaoRemover ()
    {
        return instituicaoRemover;
    }

    public void setInstituicaoRemover (GrlInstituicao instituicaoRemover)
    {
        this.instituicaoRemover = instituicaoRemover;
    }

    public List<GrlInstituicao> getInstituicoesPesquisadasList ()
    {
        return InstituicoesPesquisadasList;
    }

    public void pesquisarinstituicoes ()
    {
        InstituicoesPesquisadasList = instituicaoFacade.findInstituicao(instituicaoPesquisa);

        if (InstituicoesPesquisadasList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
           Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. "+InstituicoesPesquisadasList.size()+" registos retornado(s).");
    }

    public String create ()
    {
       System.out.println("inst");
        try
        {
            userTransaction.begin();

            Integer comu = instituicao.getFkIdEndereco().getFkIdComuna().getPkIdComuna();
            Integer dist = instituicao.getFkIdEndereco().getFkIdDistrito().getPkIdDistrito();

            if (comu == null)
            {
                instituicao.getFkIdEndereco().setFkIdComuna(null);
            }
            if (dist == null)
            {
                instituicao.getFkIdEndereco().setFkIdDistrito(null);
            }

            enderecoFacade.create(instituicao.getFkIdEndereco());
            contactoFacade.create(instituicao.getFkIdContacto());
            instituicaoFacade.create(instituicao);
            
            userTransaction.commit();
            Mensagem.sucessoMsg("Instituição guardada com sucesso!");
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

        instituicao = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (instituicao.getPkIdInstituicao() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            Integer muni = instituicao.getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio();
            Integer comu = instituicao.getFkIdEndereco().getFkIdComuna().getPkIdComuna();
            Integer dist = instituicao.getFkIdEndereco().getFkIdDistrito().getPkIdDistrito();

            instituicao.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio(muni));
            instituicao.getFkIdEndereco().setFkIdComuna(new GrlComuna(comu));
            instituicao.getFkIdEndereco().setFkIdDistrito(new GrlDistrito(dist));

            if (comu == null)
            {
                instituicao.getFkIdEndereco().setFkIdComuna(null);
            }
            if (dist == null)
            {
                instituicao.getFkIdEndereco().setFkIdDistrito(null);
            }

            contactoFacade.edit(instituicao.getFkIdContacto());
            enderecoFacade.edit(instituicao.getFkIdEndereco());

            userTransaction.commit();

            Mensagem.sucessoMsg("Instituição editada com sucesso! ");

            pesquisarinstituicoes();
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

        instituicao = null;

        return null;
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            instituicaoFacade.remove(instituicaoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Instituição removida com sucesso!");
            instituicaoRemover = null;
            pesquisarinstituicoes();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Esta Instituição possui registro de actividades, impossível remover !");
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

    
    public List<GrlInstituicao> findAll()
    {
        return instituicaoFacade.findAll();
    }
    
    public String limpar ()
    {
        instituicao = null;
        return "instituicaoGrl?faces-redirect=true";
    }

    private ItensAjaxBean obterItensAjaxBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (ItensAjaxBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "itensAjaxBean");
    }
}
