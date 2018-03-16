/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagCategoriaExame;
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
import sessao.DiagCategoriaExameFacade;
import util.Mensagem;
import util.diag.Defs;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagCategoriaExameBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagCategoriaExameFacade diagCategoriaExameFacade;
    private DiagCategoriaExame diagCategoriaExame, diagCategoriaExameEditar, diagCategoriaExameRemover, diagCategoriaExamePesquisar;
    private DiagSectorExame diagSectorExame, diagSectorExameEditar;

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    private boolean pesquisar;

    List<DiagCategoriaExame> itens;

    public static DiagCategoriaExameBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagCategoriaExameBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagCategoriaExameBean");
    }

    public static DiagCategoriaExame getInstancia()
    {
        DiagCategoriaExame diagCategoriaExame = new DiagCategoriaExame();

        diagCategoriaExame.setFkIdSector(DiagSectorExameBean.getInstancia());
        diagCategoriaExame.getFkIdSector().setPkIdSectorExame(Defs.SECTOR_EXAME_DEFAULT_ID);

        return diagCategoriaExame;
    }

    public List<DiagCategoriaExame> getItens()
    {
        if (itens == null)
        {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<DiagCategoriaExame> itens)
    {
        this.itens = itens;
    }

    public DiagCategoriaExame getDiagCategoriaExame()
    {
        if (diagCategoriaExame == null)
        {
            diagCategoriaExame = getInstancia();
        }
        return diagCategoriaExame;
    }

    public void setDiagCategoriaExame(DiagCategoriaExame diagCategoriaExame)
    {
        this.diagCategoriaExame = diagCategoriaExame;
    }

    public DiagCategoriaExame getDiagCategoriaExameEditar()
    {
        if (diagCategoriaExameEditar == null)
        {
            diagCategoriaExameEditar = getInstancia();
        }
        return diagCategoriaExameEditar;
    }

    public void setDiagCategoriaExameEditar(DiagCategoriaExame diagCategoriaExameEditar)
    {
        this.diagCategoriaExameEditar = diagCategoriaExameEditar;
    }

    public DiagCategoriaExame getDiagCategoriaExameRemover()
    {
        if (diagCategoriaExameRemover == null)
        {
            diagCategoriaExameRemover = getInstancia();
        }
        return diagCategoriaExameRemover;
    }

    public void setDiagCategoriaExameRemover(DiagCategoriaExame diagCategoriaExameRemover)
    {
        this.diagCategoriaExameRemover = diagCategoriaExameRemover;
    }

    public DiagCategoriaExame getDiagCategoriaExamePesquisar()
    {
        if (diagCategoriaExamePesquisar == null)
        {
            diagCategoriaExamePesquisar = getInstancia();
        }
        return diagCategoriaExamePesquisar;
    }

    public void setDiagCategoriaExamePesquisar(DiagCategoriaExame diagCategoriaExamePesquisar)
    {
        this.diagCategoriaExamePesquisar = diagCategoriaExamePesquisar;
    }

    public DiagSectorExame getDiagSectorExame()
    {
        if (diagSectorExame == null)
        {
            diagSectorExame = DiagSectorExameBean.getInstancia();
        }
        return diagSectorExame;
    }

    public void setDiagSectorExame(DiagSectorExame diagSectorExame)
    {
        this.diagSectorExame = diagSectorExame;
    }

    public DiagSectorExame getDiagSectorExameEditar()
    {
        if (diagSectorExameEditar == null)
        {
            diagSectorExameEditar = DiagSectorExameBean.getInstancia();
        }
        return diagSectorExameEditar;
    }

    public void setDiagSectorExameEditar(DiagSectorExame diagSectorExameEditar)
    {
        this.diagSectorExameEditar = diagSectorExameEditar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public boolean isTemMensagemPendente()
    {
        return temMensagemPendente;
    }

    public void setTemMensagemPendente(boolean temMensagemPendente)
    {
        this.temMensagemPendente = temMensagemPendente;
    }

    public String getMensagemPendente()
    {
        return mensagemPendente;
    }

    public void setMensagemPendente(String mensagemPendente)
    {
        this.mensagemPendente = mensagemPendente;
    }

    public String getTipoMensagemPendente()
    {
        return tipoMensagemPendente;
    }

    public void setTipoMensagemPendente(String tipoMensagemPendente)
    {
        this.tipoMensagemPendente = tipoMensagemPendente;
    }

    public void getMensagem()
    {
        if (tipoMensagemPendente == "Sucesso")
        {
            Mensagem.sucessoMsg(mensagemPendente);

            temMensagemPendente = false;
        }
        else
        {
            Mensagem.erroMsg(mensagemPendente);

            temMensagemPendente = false;
        }
    }

    public String create()
    {
        try
        {
            userTransaction.begin();

            diagCategoriaExame.setFkIdSector(diagSectorExame);
            diagCategoriaExameFacade.create(diagCategoriaExame);
            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Categoria de Exame salva com sucesso!";

            diagCategoriaExame = new DiagCategoriaExame();
            diagSectorExame = new DiagSectorExame();
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();

            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }

        return "categoriaExame?faces-redirect=true";
    }

    public void edit()
    {
        try
        {
            userTransaction.begin();

            if (diagCategoriaExameEditar.getPkIdCategoria() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagCategoriaExameEditar.setFkIdSector(diagSectorExameEditar);
            diagCategoriaExameFacade.edit(diagCategoriaExameEditar);

            userTransaction.commit();
            Mensagem.sucessoMsg("Categoria de Exame editada com sucesso!");

            diagCategoriaExameEditar = getInstancia();
            diagSectorExameEditar = DiagSectorExameBean.getInstancia();
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

            if (diagCategoriaExameRemover.getPkIdCategoria() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagCategoriaExameFacade.remove(diagCategoriaExameRemover);
            userTransaction.commit();
            Mensagem.sucessoMsg("Categoria de exame removida com sucesso!");
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
        diagCategoriaExame = DiagCategoriaExameBean.getInstancia();
        diagCategoriaExameEditar = DiagCategoriaExameBean.getInstancia();
        diagCategoriaExameRemover = DiagCategoriaExameBean.getInstancia();

        return "categoriaExame?faces-redirect=true";
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagCategoriaExamePesquisar = DiagCategoriaExameBean.getInstancia();
        itens = new ArrayList<>();

        return "categoriaExame.xhtml?faces-redirect=true";
    }

    public void pesquisarCategoriasExames()
    {
        itens = diagCategoriaExameFacade.findPesquisa(diagCategoriaExamePesquisar);
        
        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }        
    }

    public List<DiagCategoriaExame> findAll()
    {
        return diagCategoriaExameFacade.findAll();
    }
}
