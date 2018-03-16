/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhDepartamento;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhDepartamentoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhDepartamentoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhDepartamentoFacade departamentoFacade;

    /**
     *
     * Entidades
     */
    private RhDepartamento departamento, departamentoRemover;

    /**
     * Creates a new instance of DepartamentoBean
     */
    public RhDepartamentoBean ()
    {
    }

    public RhDepartamento getDepartamento ()
    {
        if (this.departamento == null)
        {
            this.departamento = new RhDepartamento();
        }

        return departamento;
    }

    public void setDepartamento (RhDepartamento departamento)
    {
        this.departamento = departamento;
    }

    public RhDepartamento getDepartamentoRemover ()
    {
        return departamentoRemover;
    }

    public void setDepartamentoRemover (RhDepartamento departamentoRemover)
    {
        this.departamentoRemover = departamentoRemover;
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();
            departamentoFacade.create(departamento);
            userTransaction.commit();
            Mensagem.sucessoMsg("Departamento guardado com sucesso!");
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

        departamento = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (departamento.getPkIdDepartamento() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            departamentoFacade.edit(departamento);
            userTransaction.commit();
            Mensagem.sucessoMsg("Departamento editado com sucesso!");
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

        departamento = null;

        return null;
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            departamentoFacade.remove(departamentoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Departamento removido com sucesso!");
            departamentoRemover = null;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Este departamento possui registro de actividades, impossível remover !");
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

    public List<RhDepartamento> findAll ()
    {
        return departamentoFacade.findAll();
    }

    public String limpar ()
    {
        departamento = null;
        return "departamentoRh?faces-redirect=true";
    }

}
