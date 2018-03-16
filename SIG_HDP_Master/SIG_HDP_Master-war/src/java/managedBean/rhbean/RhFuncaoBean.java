/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhFuncao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhFuncaoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhFuncaoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhFuncaoFacade funcaoFacade;

    /**
     *
     * Entidades
     */
    private RhFuncao funcao, funcaoPesquisa, funcaoRemover;

    /**
     * Creates a new instance of FuncaoBean
     */
    public RhFuncaoBean ()
    {
    }

    public static RhFuncaoBean getInstanciaBean ()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        return (RhFuncaoBean) context.getELContext().getELResolver().
                getValue(FacesContext.getCurrentInstance().
                        getELContext(), null, "rhFuncaoBean");
    }

    public RhFuncao getInstanciaFuncao ()
    {
        RhFuncao func = new RhFuncao();

        return func;
    }

    public RhFuncao getFuncao ()
    {
        if (this.funcao == null)
        {
            funcao = getInstanciaFuncao();
        }

        return funcao;
    }

    public void setFuncao (RhFuncao funcao)
    {
        this.funcao = funcao;
    }

    public RhFuncaoFacade getFuncaoFacade ()
    {
        return funcaoFacade;
    }
    
    public RhFuncao getFuncaoPesquisa ()
    {
        if (funcaoPesquisa == null)
        {
            funcaoPesquisa = getInstanciaFuncao();
        }
        return funcaoPesquisa;
    }

    public void setFuncaoPesquisa (RhFuncao funcaoPesquisa)
    {
        this.funcaoPesquisa = funcaoPesquisa;
    }

    public RhFuncao getFuncaoRemover ()
    {
        return funcaoRemover;
    }

    public void setFuncaoRemover (RhFuncao funcaoRemover)
    {
        this.funcaoRemover = funcaoRemover;
    }

    public List<RhFuncao> findAll ()
    {
        return funcaoFacade.findAll();
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();
            funcaoFacade.create(funcao);
            userTransaction.commit();
            Mensagem.sucessoMsg("Função guardada com sucesso!");
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

        funcao = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (funcao.getPkIdFuncao() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            funcaoFacade.edit(funcao);

            userTransaction.commit();

            Mensagem.sucessoMsg("Função editada com sucesso! ");
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

        funcao = null;

        return null;
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            funcaoFacade.remove(funcaoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Função removida com sucesso!");
            funcaoRemover = null;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Esta função possui registro de actividades, impossível remover !");
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
        funcao = null;
        return "funcaoRh?faces-redirect=true";
    }
}
