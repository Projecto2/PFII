/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhNotificacao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhNotificacaoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhNotificacaoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhNotificacaoFacade notificacaoFacade;

    /**
     *
     * Entidades
     */
    private String textoPesquisa;
    private Date dataPesquisa;
    private RhNotificacao notificacao, notificacaoRemover;
    private List<RhNotificacao> notificacoesPesquisadasList;

    /**
     * Creates a new instance of NotificacaoBean
     */
    public RhNotificacaoBean ()
    {
    }

    public String getTextoPesquisa ()
    {
        return textoPesquisa;
    }

    public void setTextoPesquisa (String textoPesquisa)
    {
        this.textoPesquisa = textoPesquisa;
    }

    public Date getDataPesquisa ()
    {
        return dataPesquisa;
    }

    public void setDataPesquisa (Date dataPesquisa)
    {
        this.dataPesquisa = dataPesquisa;
    }

    public RhNotificacao getInstanciaNotificacao ()
    {
        RhNotificacao notif = new RhNotificacao();

        return notif;
    }

    public RhNotificacao getNotificacao ()
    {
        if (this.notificacao == null)
        {
            notificacao = getInstanciaNotificacao();
        }

        return notificacao;
    }

    public void setNotificacao (RhNotificacao notificacao)
    {
        this.notificacao = notificacao;
    }

    public RhNotificacao getNotificacaoRemover ()
    {
        return notificacaoRemover;
    }

    public void setNotificacaoRemover (RhNotificacao notificacaoRemover)
    {
        this.notificacaoRemover = notificacaoRemover;
    }

    public List<RhNotificacao> getNotificacoesPesquisadasList ()
    {
        return notificacoesPesquisadasList;
    }

    public void pesquisarNotificacoes ()
    {
        notificacoesPesquisadasList = notificacaoFacade.findNotificacao(textoPesquisa, dataPesquisa);

        if (notificacoesPesquisadasList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + notificacoesPesquisadasList.size() + ")");
        }
    }

    public void verTodasNotificacoesNaoLidas ()
    {
        dataPesquisa = null;
        textoPesquisa = null;
        notificacao = notificacaoRemover = null;
        notificacoesPesquisadasList = notificacaoFacade.findAllNaoLidas();

        if (notificacoesPesquisadasList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + notificacoesPesquisadasList.size() + ")");
        }
    }

    public void verTodasNotificacoes ()
    {
        dataPesquisa = null;
        textoPesquisa = null;
        notificacao = notificacaoRemover = null;
        notificacoesPesquisadasList = notificacaoFacade.findAllOrdenadas();

        if (notificacoesPesquisadasList.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado");
        }
        else
        {
            Mensagem.sucessoMsg("Número de registros encontrados (" + notificacoesPesquisadasList.size() + ")");
        }
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            notificacaoFacade.remove(notificacaoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Notificacao removida com sucesso!");
            notificacaoRemover = null;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Ocorreu um erro, impossível remover !");
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

    public String marcarComoNaoLida ()
    {
        try
        {
            userTransaction.begin();

            notificacao.setLida(false);
            notificacaoFacade.edit(notificacao);

            userTransaction.commit();

            Mensagem.sucessoMsg("Notificação marcada como não lida !");

            notificacao = null;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Ocorreu um erro, impossível alterar !");
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

    public String prepararVisualizacao (RhNotificacao notif)
    {
        try
        {
            userTransaction.begin();

            notificacao = notif;
            notificacao.setLida(true);

            notificacaoFacade.edit(notificacao);

            userTransaction.commit();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
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

    public int numeroDeNotificacoesNaoLidas ()
    {
        return notificacaoFacade.findAllNaoLidas().size();
    }

    public String limpar ()
    {
        notificacao = null;
        return "notificacaoRh?faces-redirect=true";
    }

    public String getColorSize ()
    {
        if (notificacaoFacade.findAllNaoLidas().isEmpty())
        {
            return " WHITE";
        }
        return "YELLOW";
    }

}
