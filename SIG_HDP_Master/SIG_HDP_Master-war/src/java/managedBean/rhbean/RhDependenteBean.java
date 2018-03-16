/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhDependente;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhDependenteFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhDependenteBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhDependenteFacade dependenteFacade;

    private RhFuncionario funcionarioSelecionado;

    /**
     *
     * Entidades
     */
    private RhDependente dependente, dependenteRemover;

    /**
     * Creates a new instance of SeccaoBean
     */
    public RhDependenteBean ()
    {
    }

    public RhDependente getDependente ()
    {
        if (this.dependente == null)
        {
            dependente = new RhDependente();
            dependente.setFkIdFuncionario(funcionarioSelecionado);
        }

        return dependente;
    }

    public void setDependente (RhDependente dependente)
    {
        this.dependente = dependente;
    }

    public RhDependente getDependenteRemover ()
    {
        return dependenteRemover;
    }

    public void setDependenteRemover (RhDependente dependenteRemover)
    {
        this.dependenteRemover = dependenteRemover;
    }

    public RhFuncionario getFuncionarioSelecionado ()
    {
        return funcionarioSelecionado;
    }

    public String voltar ()
    {
        limpar();
        funcionarioSelecionado = null;

        return "funcionarioPesquisarRh.xhtml?faces-redirect=true";
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();
            dependenteFacade.create(dependente);
            userTransaction.commit();
            Mensagem.sucessoMsg("Dependente guardado com sucesso!");
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

        dependente = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            if (dependente.getPkIdDependente() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            userTransaction.begin();
            dependenteFacade.edit(dependente);
            userTransaction.commit();
            Mensagem.sucessoMsg("Dependente editado com sucesso!");
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

        dependente = null;

        return null;
    }

    public String remove (RhDependente depend)
    {
        try
        {
            userTransaction.begin();
            dependenteFacade.remove(depend);
            userTransaction.commit();
            Mensagem.sucessoMsg("Dependente removido com sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg("Não foi possível remover o dependente");
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        dependenteRemover = null;

        return null;
    }

    public List<RhDependente> pesquisaPorFuncionario ()
    {
        if (funcionarioSelecionado == null)
        {
            return null;
        }

        return dependenteFacade.pesquisaPorFuncionario(funcionarioSelecionado.getPkIdFuncionario());
    }

    public String limpar ()
    {
        dependente = null;
        return "dependenteRh?faces-redirect=true";
    }

    public String preparar (RhFuncionario funcionario)
    {
        this.funcionarioSelecionado = funcionario;
        return limpar();
    }
}
