/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagGrupoSanguineo;
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
import sessao.DiagGrupoSanguineoFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagGrupoSanguineoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagGrupoSanguineoFacade diagGrupoSanguineoFacade;
    private DiagGrupoSanguineo diagGrupoSanguineo, diagGrupoSanguineoRemover, diagGrupoSanguineoPesquisar;

    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    private boolean pesquisar;

    List<DiagGrupoSanguineo> itens;
    
    public static DiagGrupoSanguineoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagGrupoSanguineoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagGrupoSanguineoBean");
    }

    public static DiagGrupoSanguineo getInstancia()
    {
        DiagGrupoSanguineo diagGrupoSanguineo = new DiagGrupoSanguineo();

        return diagGrupoSanguineo;
    }

    public List<DiagGrupoSanguineo> getItens()
    {
        return itens;
    }

    public void setItens(List<DiagGrupoSanguineo> itens)
    {
        this.itens = itens;
    }
    
    public DiagGrupoSanguineo getDiagGrupoSanguineo()
    {
        if (diagGrupoSanguineo == null)
        {
            diagGrupoSanguineo = getInstancia();
        }
        return diagGrupoSanguineo;
    }

    public void setDiagGrupoSanguineo(DiagGrupoSanguineo diagGrupoSanguineo)
    {
        this.diagGrupoSanguineo = diagGrupoSanguineo;
    }

    public DiagGrupoSanguineo getDiagGrupoSanguineoRemover()
    {
        return diagGrupoSanguineoRemover;
    }

    public DiagGrupoSanguineo getDiagGrupoSanguineoPesquisar()
    {
        if (diagGrupoSanguineoPesquisar == null)
        {
            diagGrupoSanguineoPesquisar = getInstancia();
        }
        return diagGrupoSanguineoPesquisar;
    }

    public void setDiagGrupoSanguineoPesquisar(DiagGrupoSanguineo diagGrupoSanguineoPesquisar)
    {
        this.diagGrupoSanguineoPesquisar = diagGrupoSanguineoPesquisar;
    }

    public void setDiagGrupoSanguineoRemover(DiagGrupoSanguineo diagGrupoSanguineoRemover)
    {
        this.diagGrupoSanguineoRemover = diagGrupoSanguineoRemover;
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
            diagGrupoSanguineoFacade.create(diagGrupoSanguineo);
            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Grupo Sanguíneo salvo com sucesso!";

            diagGrupoSanguineo = getInstancia();
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

        return "grupoSanguineo?faces-redirect=true";
    }

    public void edit()
    {
        try
        {
            userTransaction.begin();

            if (diagGrupoSanguineo.getPkIdGrupoSanguineo() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagGrupoSanguineoFacade.edit(diagGrupoSanguineo);
            userTransaction.commit();

            Mensagem.sucessoMsg("Grupo Sanguíneo editado com sucesso!");

            diagGrupoSanguineo = getInstancia();
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

            if (diagGrupoSanguineoRemover.getPkIdGrupoSanguineo() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagGrupoSanguineoFacade.remove(diagGrupoSanguineoRemover);
            userTransaction.commit();

            Mensagem.sucessoMsg("Grupo Sanguíneo removido com sucesso!");
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
        diagGrupoSanguineo = getInstancia();
        return "grupoSanguineo?faces-redirect=true";
    }

    public String limparPesquisa()
    {
        pesquisar = false;
        diagGrupoSanguineoPesquisar = getInstancia();

        itens = new ArrayList<>();
        
        return "grupoSanguineo.xhtml?faces-redirect=true";
    }

    public void pesquisarGruposSanguineos()
    {
        itens = diagGrupoSanguineoFacade.findPesquisa(diagGrupoSanguineoPesquisar);
        
        if (itens.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + itens.size() + " registo(s) retornado(s).");
        }                 
    }

    public List<DiagGrupoSanguineo> findAll()
    {
        return diagGrupoSanguineoFacade.findAll();
    }
}
