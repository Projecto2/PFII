/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsClassificacaoServicoSolicitado;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.AdmsClassificacaoServicoSolicitadoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsClassificacaoServicoSolicitadoBean implements Serializable
{


    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AdmsClassificacaoServicoSolicitadoFacade classificacaoServicoSolicitadoFacade;

    /**
     *
     * Entidades
     */
    private AdmsClassificacaoServicoSolicitado classificacaoServicoSolicitado;

    /**
     * Creates a new instance of ClassificacaoServicoSolicitadoBean
     */
    public AdmsClassificacaoServicoSolicitadoBean ()
    {
    }
    
    public static AdmsClassificacaoServicoSolicitadoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsClassificacaoServicoSolicitadoBean admsClassificacaoServicoSolicitadoBean = 
            (AdmsClassificacaoServicoSolicitadoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsClassificacaoServicoSolicitadoBean");
        
        return admsClassificacaoServicoSolicitadoBean;
    }

    public AdmsClassificacaoServicoSolicitado getClassificacaoServicoSolicitado ()
    {
        if (this.classificacaoServicoSolicitado == null)
        {
            this.classificacaoServicoSolicitado = new AdmsClassificacaoServicoSolicitado();
        }

        return classificacaoServicoSolicitado;
    }

    public void setClassificacaoServicoSolicitado (AdmsClassificacaoServicoSolicitado classificacaoServicoSolicitado)
    {
        this.classificacaoServicoSolicitado = classificacaoServicoSolicitado;
    }

    public String create () throws SystemException
    {
        try
        {
            userTransaction.begin();
            classificacaoServicoSolicitadoFacade.create(classificacaoServicoSolicitado);
            userTransaction.commit();
            Mensagem.sucessoMsg("ClassificacaoServicoSolicitado guardado com sucesso!");
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

        classificacaoServicoSolicitado = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (classificacaoServicoSolicitado.getPkIdClassificacaoServicoSolicitado() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            classificacaoServicoSolicitadoFacade.edit(classificacaoServicoSolicitado);
            userTransaction.commit();
            Mensagem.sucessoMsg("ClassificacaoServicoSolicitado editado com sucesso!");
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

        classificacaoServicoSolicitado = null;

        return null;
    }

    public List<AdmsClassificacaoServicoSolicitado> findAll ()
    {
        return classificacaoServicoSolicitadoFacade.findAll();
    }

    public String limpar()
    {
        classificacaoServicoSolicitado = null;
        return "classificacaoServicoSolicitadoAdms?faces-redirect=true";
    }
}
