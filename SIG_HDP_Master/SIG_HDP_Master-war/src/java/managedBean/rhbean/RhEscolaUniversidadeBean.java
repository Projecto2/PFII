/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhEscolaUniversidade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhEscolaUniversidadeFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhEscolaUniversidadeBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhEscolaUniversidadeFacade instituicaoFacade;

    /**
     *
     * Entidades
     */
    private RhEscolaUniversidade instituicao, instituicaoRemover;

    /**
     * Creates a new instance of EscolaUniversidadeBean
     */
    public RhEscolaUniversidadeBean ()
    {
    }

    public RhEscolaUniversidade getEscolaUniversidade ()
    {
        if (this.instituicao == null)
        {
            this.instituicao = new RhEscolaUniversidade();
        }

        return instituicao;
    }

    public void setEscolaUniversidade (RhEscolaUniversidade instituicao)
    {
        this.instituicao = instituicao;
    }

    public RhEscolaUniversidade getEscolaUniversidadeRemover ()
    {
        return instituicaoRemover;
    }

    public void setEscolaUniversidadeRemover (RhEscolaUniversidade instituicaoRemover)
    {
        this.instituicaoRemover = instituicaoRemover;
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();

            instituicaoFacade.create(instituicao);
            userTransaction.commit();
            instituicao = null;
            Mensagem.sucessoMsg("Instituição guardada com sucesso!");
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
            if (instituicao.getPkIdEscolaUniversidade() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            instituicaoFacade.edit(instituicao);
            userTransaction.commit();
            instituicao = null;
            Mensagem.sucessoMsg("Instituição editada com sucesso!");
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

            instituicaoFacade.remove(instituicaoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Instituição removida com sucesso!");
            instituicaoRemover = null;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Esta instituição possui registro de actividades, impossível remover !");
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

    public List<RhEscolaUniversidade> findAll ()
    {
        return instituicaoFacade.findAll();
    }

    public String limpar ()
    {
        instituicao = null;
        return "instituicaoRh?faces-redirect=true";
    }

}
