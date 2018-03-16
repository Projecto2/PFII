/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagReagente;
import entidade.DiagTipoReagente;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
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
import sessao.DiagReagenteFacade;
import sessao.DiagTipoReagenteFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagReagenteBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagReagenteFacade diagReagenteFacade;
    @EJB
    private DiagTipoReagenteFacade diagTipoReagenteFacade;

    private DiagReagente diagReagente, diagReagenteEditar, diagReagenteRemover, diagReagentePesquisar;
    private DiagTipoReagente diagTipoReagente, diagTipoReagenteEditar;
    private int quantidadeAdicionar;

    private Date dataInicioValidade, dataFimValidade, dataInicioCadastro, dataFimCadastro;

    private boolean pesquisar;
    
    private boolean temMensagemPendente;

    private String mensagemPendente;

    private String tipoMensagemPendente;

    public static DiagReagenteBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagReagenteBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagReagenteBean");
    }

    public static DiagReagente getInstancia()
    {
        DiagReagente diagReagente = new DiagReagente();
        diagReagente.setFkIdTipoReagente(DiagTipoReagenteBean.getInstancia());

        return diagReagente;
    }
    
    public DiagReagente getDiagReagente()
    {
        if (diagReagente == null)
        {
            diagReagente = getInstancia();
        }
        return diagReagente;
    }

    public void setDiagReagente(DiagReagente diagReagente)
    {
        this.diagReagente = diagReagente;
    }

    public DiagTipoReagente getDiagTipoReagente()
    {
        if (diagTipoReagente == null)
        {
            diagTipoReagente = DiagTipoReagenteBean.getInstancia();
        }
        return diagTipoReagente;
    }

    public DiagReagente getDiagReagenteRemover()
    {
        if (diagReagenteRemover == null)
        {
            diagReagenteRemover = getInstancia();
        }
        return diagReagenteRemover;
    }

    public void setDiagReagenteRemover(DiagReagente diagReagenteRemover)
    {
        this.diagReagenteRemover = diagReagenteRemover;
    }

    public void setDiagTipoReagente(DiagTipoReagente diagTipoReagente)
    {
        this.diagTipoReagente = diagTipoReagente;
    }

    public DiagReagente getDiagReagenteEditar()
    {
        if (diagReagenteEditar == null)
        {
            diagReagenteEditar = getInstancia();
        }
        return diagReagenteEditar;
    }

    public void setDiagReagenteEditar(DiagReagente diagReagenteEditar)
    {
        this.diagReagenteEditar = diagReagenteEditar;
    }

    public DiagTipoReagente getDiagTipoReagenteEditar()
    {
        if (diagTipoReagenteEditar == null)
        {
            diagTipoReagenteEditar = DiagTipoReagenteBean.getInstancia();
        }
        return diagTipoReagenteEditar;
    }

    public void setDiagTipoReagenteEditar(DiagTipoReagente diagTipoReagenteEditar)
    {
        this.diagTipoReagenteEditar = diagTipoReagenteEditar;
    }

    public int getQuantidadeAdicionar()
    {
        return quantidadeAdicionar;
    }

    public void setQuantidadeAdicionar(int quantidadeAdicionar)
    {
        this.quantidadeAdicionar = quantidadeAdicionar;
    }

    public boolean isPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public Date getDataInicioValidade()
    {
        return dataInicioValidade;
    }

    public void setDataInicioValidade(Date dataInicioValidade)
    {
        this.dataInicioValidade = dataInicioValidade;
    }

    public Date getDataFimValidade()
    {
        return dataFimValidade;
    }

    public void setDataFimValidade(Date dataFimValidade)
    {
        this.dataFimValidade = dataFimValidade;
    }

    public Date getDataInicioCadastro()
    {
        return dataInicioCadastro;
    }

    public void setDataInicioCadastro(Date dataInicioCadastro)
    {
        this.dataInicioCadastro = dataInicioCadastro;
    }

    public Date getDataFimCadastro()
    {
        return dataFimCadastro;
    }

    public void setDataFimCadastro(Date dataFimCadastro)
    {
        this.dataFimCadastro = dataFimCadastro;
    }

    public DiagReagente getDiagReagentePesquisar()
    {
        if (diagReagentePesquisar == null)
        {
            diagReagentePesquisar = getInstancia();
        }
        return diagReagentePesquisar;
    }

    public void setDiagReagentePesquisar(DiagReagente diagReagentePesquisar)
    {
        this.diagReagentePesquisar = diagReagentePesquisar;
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
        GregorianCalendar data = new GregorianCalendar();

        try
        {
            userTransaction.begin();
            diagReagente.setDataCadastro(new Date(data.getTimeInMillis()));
            diagReagente.setFkIdTipoReagente(diagTipoReagente);
            diagReagenteFacade.create(diagReagente);
            userTransaction.commit();

            temMensagemPendente = true;

            tipoMensagemPendente = "Sucesso";

            mensagemPendente = "Reagente salvo com sucesso!";

            diagReagente = getInstancia();
            diagTipoReagente = DiagTipoReagenteBean.getInstancia();
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

        return "gestaoReagentes.xhtml?faces-redirect=true";
    }

    public void edit()
    {
        try
        {
            userTransaction.begin();

            if (diagReagenteEditar.getPkIdReagente() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagReagenteEditar.setFkIdTipoReagente(diagTipoReagenteEditar);
            diagReagenteFacade.edit(diagReagenteEditar);

            userTransaction.commit();
            Mensagem.sucessoMsg("Reagente editado com sucesso!");

            diagReagenteEditar = getInstancia();
            diagTipoReagenteEditar = DiagTipoReagenteBean.getInstancia();
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

    public void addQuantidade()
    {
        try
        {
            if (quantidadeAdicionar > 0)
            {

                userTransaction.begin();

                int quantidadeActual = diagReagente.getQuantidade();
                int quantidadeNova = quantidadeAdicionar + quantidadeActual;

                if (diagReagente.getPkIdReagente() == null)
                {
                    throw new NullPointerException("PK -> NULL");
                }

                diagReagente.setQuantidade(quantidadeNova);
                diagReagenteFacade.edit(diagReagente);

                userTransaction.commit();
                Mensagem.sucessoMsg("Quantidade do Reagente actualizada com suceso!");

                quantidadeAdicionar = 0;
            }
            else
            {
                Mensagem.erroMsg("Erro: Quantidade a adicionar inválida.");
            }
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

            if (diagReagenteRemover.getPkIdReagente() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            diagReagenteFacade.remove(diagReagenteRemover);
            userTransaction.commit();
            Mensagem.sucessoMsg("Reagente removido com sucesso!");

            diagReagenteRemover = getInstancia();
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
        diagReagente = diagReagentePesquisar = null;
        diagTipoReagente = null;
        return "gestaoReagentes.xhtml?faces-redirect=true";
    }

    public List<DiagTipoReagente> findAllTipoReagentes()
    {
        return diagTipoReagenteFacade.findAll();
    }

    public List<DiagReagente> findAll()
    {
        return diagReagenteFacade.findAll();
    }

    public List<DiagReagente> pesquisarReagentes()
    {
        if (pesquisar)
        {
            List<DiagReagente> pp;
            pp = diagReagenteFacade.findPesquisa(diagReagentePesquisar, dataInicioCadastro, dataFimCadastro, dataInicioValidade, dataFimValidade);
            if (pp.isEmpty() || pp == null)
            {
                Mensagem.erroMsg("Nenhum registo encontrado para esta pesquisa");
            }

            return pp;
        }

        return null;
    }
}
