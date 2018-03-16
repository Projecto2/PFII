/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagSectorExame;
import java.io.Serializable;
import java.util.ArrayList;
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
import sessao.DiagSectorExameFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagSectorExameBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagSectorExameFacade diagSectorExameFacade;

    private DiagSectorExame diagSectorExame, diagSectorExameRemover, diagSectorExamePesquisar;

    private boolean pesquisar;
    List<DiagSectorExame> itens;

    public static DiagReagenteBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagReagenteBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagSectorExameBean");
    }

    public static DiagSectorExame getInstancia()
    {
        DiagSectorExame diagSectorExame = new DiagSectorExame();

        return diagSectorExame;
    }

    public List<DiagSectorExame> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagSectorExame> itens)
    {
        this.itens = itens;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public DiagSectorExame getDiagSectorExame()
    {
        return diagSectorExame;
    }

    public void setDiagSectorExame(DiagSectorExame diagSectorExame)
    {
        this.diagSectorExame = diagSectorExame;
    }

    public DiagSectorExame getDiagSectorExameRemover()
    {
        return diagSectorExameRemover;
    }

    public void setDiagSectorExameRemover(DiagSectorExame diagSectorExameRemover)
    {
        this.diagSectorExameRemover = diagSectorExameRemover;
    }

    public DiagSectorExame getDiagSectorExamePesquisar()
    {
        if (diagSectorExamePesquisar == null)
        {
            diagSectorExamePesquisar = new DiagSectorExame();
        }
        return diagSectorExamePesquisar;
    }

    public void setDiagSectorExamePesquisar(DiagSectorExame diagSectorExamePesquisar)
    {
        this.diagSectorExamePesquisar = diagSectorExamePesquisar;
    }

    public void create()
    {
        try
        {
            userTransaction.begin();
            diagSectorExameFacade.create(diagSectorExame);
            userTransaction.commit();
            Mensagem.sucessoMsg("Sector de Exame salvo com sucesso!");

            diagSectorExame = getInstancia();
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

            if (diagSectorExame.getPkIdSectorExame() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagSectorExameFacade.edit(diagSectorExame);
            userTransaction.commit();
            Mensagem.sucessoMsg("Sector de Exame editado com sucesso!");

            diagSectorExame = getInstancia();
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

            if (diagSectorExameRemover.getPkIdSectorExame() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagSectorExameFacade.remove(diagSectorExameRemover);
            userTransaction.commit();
            Mensagem.sucessoMsg("Sector de exame removido com sucesso!");
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
        diagSectorExame = null;
        return "sectorExame?faces-redirect=true";
    }

    public List<DiagSectorExame> findAll()
    {
        return diagSectorExameFacade.findAll();
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagSectorExamePesquisar = getInstancia();
        itens = new ArrayList<>();

        return "sectorExame.xhtml?faces-redirect=true";
    }

    public void pesquisarSectorExames()
    {
        itens = diagSectorExameFacade.findPesquisa(diagSectorExamePesquisar);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
    }
}
