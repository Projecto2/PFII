/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagTipoReagente;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.DiagTipoReagenteFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipoReagenteBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagTipoReagenteFacade diagTipoReagenteFacade;
    private DiagTipoReagente diagTipoReagente, diagTipoReagenteRemover;

    public static DiagTipoAmostraBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTipoAmostraBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTipoAmostraBean");
    }

    public static DiagTipoReagente getInstancia()
    {
        DiagTipoReagente diagTipoReagente = new DiagTipoReagente();

        return diagTipoReagente;
    }
    
    public DiagTipoReagente getDiagTipoReagente()
    {
        if (diagTipoReagente == null)
        {
            diagTipoReagente = getInstancia();
        }
        return diagTipoReagente;
    }

    public void setDiagTipoReagente(DiagTipoReagente diagTipoReagente)
    {
        this.diagTipoReagente = diagTipoReagente;
    }

    public DiagTipoReagente getDiagTipoReagenteRemover()
    {
        if (diagTipoReagenteRemover == null)
        {
            diagTipoReagenteRemover = getInstancia();
        }
        return diagTipoReagenteRemover;
    }

    public void setDiagTipoReagenteRemover(DiagTipoReagente diagTipoReagenteRemover)
    {
        this.diagTipoReagenteRemover = diagTipoReagenteRemover;
    }

    public void create()
    {
        try
        {
            userTransaction.begin();
            diagTipoReagenteFacade.create(diagTipoReagente);
            userTransaction.commit();
            
            Mensagem.sucessoMsg("Tipo de Reagente salvo com sucesso!");

            diagTipoReagente = getInstancia();
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
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
    }

    public void edit()
    {
        try
        {
            userTransaction.begin();

            if (diagTipoReagente.getPkIdTipoReagente() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagTipoReagenteFacade.edit(diagTipoReagente);
            userTransaction.commit();
            
            Mensagem.sucessoMsg("Tipo de Reagente editado com sucesso!");

            diagTipoReagente = getInstancia();
        }
        catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
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
    }

    public void remove()
    {
        try
        {
            userTransaction.begin();

            if (diagTipoReagenteRemover.getPkIdTipoReagente() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagTipoReagenteFacade.remove(diagTipoReagenteRemover);
            userTransaction.commit();
            
            Mensagem.sucessoMsg("Tipo de reagente removido com sucesso!");
        }
        catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
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
    }

    public String limpar()
    {
        diagTipoReagente = null;
        return "tipoReagenteSanguineo?faces-redirect=true";
    }

    public List<DiagTipoReagente> findAll()
    {
        return diagTipoReagenteFacade.findAll();
    }
}
