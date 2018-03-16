/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagBolsaSangue;
import entidade.DiagComponenteSanguineo;
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
import sessao.DiagComponenteSanguineoFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagComponenteSanguineoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagComponenteSanguineoFacade diagComponenteSanguineoFacade;

    private DiagComponenteSanguineo diagComponenteSanguineo, diagComponenteSanguineoRemover, diagComponenteSanguineoPesquisar;

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    private boolean pesquisar;

    List<DiagComponenteSanguineo> itens;
    
    public static DiagComponenteSanguineoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagComponenteSanguineoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagComponenteSanguineoBean");
    }

    public static DiagComponenteSanguineo getInstancia()
    {
        DiagComponenteSanguineo diagComponenteSanguineo = new DiagComponenteSanguineo();

        return diagComponenteSanguineo;
    }

    public List<DiagComponenteSanguineo> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagComponenteSanguineo> itens)
    {
        this.itens = itens;
    }

    public DiagComponenteSanguineo getDiagComponenteSanguineo()
    {
        if (diagComponenteSanguineo == null)
        {
            diagComponenteSanguineo = getInstancia();
        }
        return diagComponenteSanguineo;
    }

    public void setDiagComponenteSanguineo(DiagComponenteSanguineo diagComponenteSanguineo)
    {
        this.diagComponenteSanguineo = diagComponenteSanguineo;
    }

    public DiagComponenteSanguineo getDiagComponenteSanguineoRemover()
    {
        return diagComponenteSanguineoRemover;
    }

    public void setDiagComponenteSanguineoRemover(DiagComponenteSanguineo diagComponenteSanguineoRemover)
    {
        this.diagComponenteSanguineoRemover = diagComponenteSanguineoRemover;
    }

    public DiagComponenteSanguineo getDiagComponenteSanguineoPesquisar()
    {
        if (diagComponenteSanguineoPesquisar == null)
        {
            diagComponenteSanguineoPesquisar = getInstancia();
        }
        return diagComponenteSanguineoPesquisar;
    }

    public void setDiagComponenteSanguineoPesquisar(DiagComponenteSanguineo diagComponenteSanguineoPesquisar)
    {
        this.diagComponenteSanguineoPesquisar = diagComponenteSanguineoPesquisar;
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
            diagComponenteSanguineoFacade.create(diagComponenteSanguineo);
            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Tipo de Componente Sanguíneo salvo com sucesso!";

            diagComponenteSanguineo = getInstancia();
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

        return "componentesSanguineos?faces-redirect=true";
    }

    public void edit()
    {
        try
        {
            userTransaction.begin();

            if (diagComponenteSanguineo.getPkIdComponenteSanguineo() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagComponenteSanguineoFacade.edit(diagComponenteSanguineo);
            userTransaction.commit();

            Mensagem.sucessoMsg("Tipo de Componente Sanguíneo editado com sucesso!");

            diagComponenteSanguineo = getInstancia();
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

            if (diagComponenteSanguineoRemover.getPkIdComponenteSanguineo() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagComponenteSanguineoFacade.remove(diagComponenteSanguineoRemover);
            userTransaction.commit();

            Mensagem.sucessoMsg("Tipo de Componente Sanguíneo removido com sucesso!");
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
        diagComponenteSanguineo = getInstancia();
        return "componentesSanguineos?faces-redirect=true";
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagComponenteSanguineoPesquisar = getInstancia();
        itens = new ArrayList<>();

        return "componentesSanguineos?faces-redirect=true";
    }

    public void pesquisarComponentesSanguineos()
    {
        itens = diagComponenteSanguineoFacade.findPesquisa(diagComponenteSanguineoPesquisar);
        
        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }         
    }

    public List<DiagComponenteSanguineo> findAll()
    {
        return diagComponenteSanguineoFacade.findAll();
    }
}
