/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhSubsidio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhSubsidioFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhSubsidioBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhSubsidioFacade subsidioFacade;

    /**
     *
     * Entidades
     */
    private RhSubsidio subsidio, subsidioRemover;

    /**
     * Creates a new instance of SubsidioBean
     */
    public RhSubsidioBean ()
    {
    }

    public RhSubsidio getSubsidio ()
    {
        if (this.subsidio == null)
        {
            this.subsidio = new RhSubsidio();
        }

        return subsidio;
    }

    public void setSubsidio (RhSubsidio subsidio)
    {
        this.subsidio = subsidio;
    }

    public RhSubsidio getSubsidioRemover ()
    {
        return subsidioRemover;
    }

    public void setSubsidioRemover (RhSubsidio subsidioRemover)
    {
        this.subsidioRemover = subsidioRemover;
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();

            subsidioFacade.create(subsidio);
            userTransaction.commit();
            subsidio = null;
            Mensagem.sucessoMsg("Subsídio guardado com sucesso!");
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

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (subsidio.getPkIdSubsidio() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            subsidioFacade.edit(subsidio);
            userTransaction.commit();
            subsidio = null;
            Mensagem.sucessoMsg("Subsídio editado com sucesso!");
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

        return null;
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            subsidioFacade.remove(subsidioRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Subsídio removido com sucesso!");
            subsidioRemover = null;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Este subsídio possui registro de actividades, impossível remover !");
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

    public List<RhSubsidio> findAll ()
    {
        return subsidioFacade.findAll();
    }

    public String limpar ()
    {
        subsidio = null;
        return "subsidioRh?faces-redirect=true";
    }

}
