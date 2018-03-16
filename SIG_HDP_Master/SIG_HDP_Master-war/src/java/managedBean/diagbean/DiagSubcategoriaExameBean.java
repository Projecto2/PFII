/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagCategoriaExame;
import entidade.DiagSubcategoriaExame;
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
import sessao.DiagSubcategoriaExameFacade;
import util.Mensagem;
import util.diag.Defs;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagSubcategoriaExameBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagSubcategoriaExameFacade diagSubcategoriaExameFacade;
    @EJB
    private DiagCategoriaExameFacade diagCategoriaExameFacade;

    private DiagCategoriaExame diagCategoriaExame, diagCategoriaExameEditar;
    private DiagSubcategoriaExame diagSubcategoriaExame, diagSubcategoriaExameEditar, diagSubcategoriaExameRemover, diagSubcategoriaExamePesquisar;

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    private boolean pesquisar;

    List<DiagSubcategoriaExame> itens;

    public static DiagSubcategoriaExameBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagSubcategoriaExameBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagSubcategoriaExameBean");
    }

    public static DiagSubcategoriaExame getInstancia()
    {
        DiagSubcategoriaExame diagSubcategoriaExame = new DiagSubcategoriaExame();
        diagSubcategoriaExame.setFkIdCategoria(DiagCategoriaExameBean.getInstancia());
        diagSubcategoriaExame.getFkIdCategoria().setPkIdCategoria(Defs.CATEGORIA_EXAME_LABORATORIO_DEFAULT_ID);

        return diagSubcategoriaExame;
    }

    public List<DiagSubcategoriaExame> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagSubcategoriaExame> itens)
    {
        this.itens = itens;
    }

    public DiagSubcategoriaExame getDiagSubcategoriaExame()
    {
        if (diagSubcategoriaExame == null)
        {
            diagSubcategoriaExame = getInstancia();
        }
        return diagSubcategoriaExame;
    }

    public void setDiagSubcategoriaExame(DiagSubcategoriaExame diagSubcategoriaExame)
    {
        this.diagSubcategoriaExame = diagSubcategoriaExame;
    }

    public DiagCategoriaExame getDiagCategoriaExame()
    {
        if (diagCategoriaExame == null)
        {
            diagCategoriaExame = DiagCategoriaExameBean.getInstancia();
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
            diagCategoriaExameEditar = DiagCategoriaExameBean.getInstancia();
        }
        return diagCategoriaExameEditar;
    }

    public void setDiagCategoriaExameEditar(DiagCategoriaExame diagCategoriaExameEditar)
    {
        this.diagCategoriaExameEditar = diagCategoriaExameEditar;
    }

    public DiagSubcategoriaExame getDiagSubcategoriaExameEditar()
    {
        if (diagSubcategoriaExameEditar == null)
        {
            diagSubcategoriaExameEditar = getInstancia();
        }
        return diagSubcategoriaExameEditar;
    }

    public void setDiagSubcategoriaExameEditar(DiagSubcategoriaExame diagSubcategoriaExameEditar)
    {
        this.diagSubcategoriaExameEditar = diagSubcategoriaExameEditar;
    }

    public DiagSubcategoriaExame getDiagSubcategoriaExameRemover()
    {
        if (diagSubcategoriaExameRemover == null)
        {
            diagSubcategoriaExameRemover = getInstancia();
        }
        return diagSubcategoriaExameRemover;
    }

    public void setDiagSubcategoriaExameRemover(DiagSubcategoriaExame diagSubcategoriaExameRemover)
    {
        this.diagSubcategoriaExameRemover = diagSubcategoriaExameRemover;
    }

    public DiagSubcategoriaExame getDiagSubcategoriaExamePesquisar()
    {
        if (diagSubcategoriaExamePesquisar == null)
        {
            diagSubcategoriaExamePesquisar = getInstancia();
        }
        return diagSubcategoriaExamePesquisar;
    }

    public void setDiagSubcategoriaExamePesquisar(DiagSubcategoriaExame diagSubcategoriaExamePesquisar)
    {
        this.diagSubcategoriaExamePesquisar = diagSubcategoriaExamePesquisar;
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

            diagSubcategoriaExame.setFkIdCategoria(diagCategoriaExame);
            diagSubcategoriaExameFacade.create(diagSubcategoriaExame);

            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Subcategoria de Exame salva com sucesso!";

            diagCategoriaExame = DiagCategoriaExameBean.getInstancia();
            diagSubcategoriaExame = getInstancia();
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

                mensagemPendente = e.toString();
            }
        }

        return "subcategoriaExame?faces-redirect=true";
    }

    public void edit()
    {
        try
        {
            userTransaction.begin();

            if (diagSubcategoriaExameEditar.getPkIdSubcategoriaExame() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagSubcategoriaExameEditar.setFkIdCategoria(diagCategoriaExameEditar);
            diagSubcategoriaExameFacade.edit(diagSubcategoriaExameEditar);

            userTransaction.commit();
            Mensagem.sucessoMsg("Subcategoria de Exame editada com sucesso!");

            diagCategoriaExameEditar = DiagCategoriaExameBean.getInstancia();
            diagSubcategoriaExameEditar = getInstancia();
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

            if (diagSubcategoriaExameRemover.getPkIdSubcategoriaExame() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagSubcategoriaExameFacade.remove(diagSubcategoriaExameRemover);
            userTransaction.commit();
            Mensagem.sucessoMsg("Subcategoria de exame removida com sucesso!");
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
        diagSubcategoriaExame = getInstancia();

        return "subcategoriaExame?faces-redirect=true";
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagSubcategoriaExamePesquisar = null;

        itens = new ArrayList<>();

        return "subcategoriaExame.xhtml?faces-redirect=true";
    }

    public void pesquisarSubcategoriasExames()
    {
        itens = diagSubcategoriaExameFacade.findPesquisa(diagSubcategoriaExamePesquisar);

        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }
    }

    public List<DiagSubcategoriaExame> findAllSubcategorias()
    {
        return diagSubcategoriaExameFacade.findAll();
    }

    public List<DiagCategoriaExame> findAllCategorias()
    {
        return diagCategoriaExameFacade.findAll();
    }
}
