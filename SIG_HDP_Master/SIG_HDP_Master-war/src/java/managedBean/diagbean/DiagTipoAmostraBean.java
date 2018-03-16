/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagTipoAmostra;
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
import sessao.DiagTipoAmostraFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipoAmostraBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagTipoAmostraFacade diagTipoAmostraFacade;

    private DiagTipoAmostra diagTipoAmostra, diagTipoAmostraRemover, diagTipoAmostraPesquisar;

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    private boolean pesquisar;

    List<DiagTipoAmostra> itens;
    
    public static DiagTipoAmostraBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTipoAmostraBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTipoAmostraBean");
    }

    public static DiagTipoAmostra getInstancia()
    {
        DiagTipoAmostra diagTipoAmostra = new DiagTipoAmostra();

        return diagTipoAmostra;
    }

    public List<DiagTipoAmostra> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagTipoAmostra> itens)
    {
        this.itens = itens;
    }
    
    public DiagTipoAmostraBean()
    {
        diagTipoAmostra = new DiagTipoAmostra();
    }

    public DiagTipoAmostra getDiagTipoAmostra()
    {
        if (diagTipoAmostra == null)
        {
            diagTipoAmostra = getInstancia();
        }
        return diagTipoAmostra;
    }

    public void setDiagTipoAmostra(DiagTipoAmostra diagTipoAmostra)
    {
        this.diagTipoAmostra = diagTipoAmostra;
    }

    public DiagTipoAmostra getDiagTipoAmostraRemover()
    {
        if (diagTipoAmostraRemover == null)
        {
            diagTipoAmostraRemover = getInstancia();
        }
        return diagTipoAmostraRemover;
    }

    public void setDiagTipoAmostraRemover(DiagTipoAmostra diagTipoAmostraRemover)
    {
        this.diagTipoAmostraRemover = diagTipoAmostraRemover;
    }

    public DiagTipoAmostra getDiagTipoAmostraPesquisar()
    {
        if (diagTipoAmostraPesquisar == null)
        {
            diagTipoAmostraPesquisar = getInstancia();
        }
        return diagTipoAmostraPesquisar;
    }

    public void setDiagTipoAmostraPesquisar(DiagTipoAmostra diagTipoAmostraPesquisar)
    {
        this.diagTipoAmostraPesquisar = diagTipoAmostraPesquisar;
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
        } else
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
            diagTipoAmostraFacade.create(diagTipoAmostra);
            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Tipo de Amostra de Exame salvo com sucesso!";

            diagTipoAmostra = getInstancia();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();

                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = e.toString();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                temMensagemPendente = true;

                tipoMensagemPendente = "Erro";

                mensagemPendente = "Rollback: " + ex.toString();
            }
        }

        return "tipoAmostraExame?faces-redirect=true";
    }

    public void edit()
    {
        try
        {
            userTransaction.begin();

            if (diagTipoAmostra.getPkIdTipoAmostra() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagTipoAmostraFacade.edit(diagTipoAmostra);
            userTransaction.commit();

            Mensagem.sucessoMsg("Tipo de Amostra de Exame editado com sucesso!");

            diagTipoAmostra = getInstancia();
        } catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            } catch (IllegalStateException | SecurityException | SystemException ex)
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

            if (diagTipoAmostraRemover.getPkIdTipoAmostra() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagTipoAmostraFacade.remove(diagTipoAmostraRemover);
            userTransaction.commit();

            Mensagem.sucessoMsg("Tipo de amostra removido com sucesso!");
        } catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

    public String limpar()
    {
        diagTipoAmostra = null;
        return "tipoAmostraExame?faces-redirect=true";
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagTipoAmostraPesquisar = null;
        
        itens = new ArrayList<>();

        return "tipoAmostraExame.xhtml?faces-redirect=true";
    }

    public void pesquisarTiposAmostra()
    {        
        itens = diagTipoAmostraFacade.findPesquisa(diagTipoAmostraPesquisar);
        
        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }        
    }

    public List<DiagTipoAmostra> findAll()
    {
        return diagTipoAmostraFacade.findAll();
    }

}
