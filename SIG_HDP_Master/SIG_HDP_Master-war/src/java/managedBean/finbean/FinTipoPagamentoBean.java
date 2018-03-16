/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.finbean;

import entidade.FinTipoPagamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.FinTipoPagamentoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class FinTipoPagamentoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private FinTipoPagamentoFacade tipoPagamentoFacade;

    private FinTipoPagamento tipoPagamento, tipoPagamentoPesquisa, tipoPagamentoEditar;
    
    private List<FinTipoPagamento> finTipoPagamentoLista;
    
//    private boolean pesquisar = false;

    /**
     * Creates a new instance of TipoPagamentoBean
     */
    public FinTipoPagamentoBean ()
    {
    }

    public FinTipoPagamento getTipoPagamento ()
    {
        if (this.tipoPagamento == null)
        {
            this.tipoPagamento = new FinTipoPagamento();
        }

        return tipoPagamento;
    }

    public void setTipoPagamento (FinTipoPagamento tipoPagamento)
    {
        this.tipoPagamento = tipoPagamento;
    }

    public FinTipoPagamento getTipoPagamentoEditar()
    {
        if (this.tipoPagamentoEditar == null)
        {
            this.tipoPagamentoEditar = new FinTipoPagamento();
        }

        return tipoPagamentoEditar;
    }

    public void setTipoPagamentoEditar(FinTipoPagamento tipoPagamentoEditar)
    {
        this.tipoPagamentoEditar = tipoPagamentoEditar;
    }
    

    public void gravarTipoPagamentoPesquisar()
    {
        create();
        pesquisar();
    }

    public void create()
    {
        try
        {
            userTransaction.begin();
            tipoPagamentoFacade.create(tipoPagamento);
            userTransaction.commit();
            Mensagem.sucessoMsg("TipoPagamento guardado com sucesso!");
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

        tipoPagamento = null;
    }
    
    public FinTipoPagamento getTipoPagamentoPesquisa ()
    {
        if (this.tipoPagamentoPesquisa == null)
        {
            this.tipoPagamentoPesquisa = new FinTipoPagamento();
        }

        return tipoPagamentoPesquisa;
    }
    
    public void setTipoPagamentoPesquisa (FinTipoPagamento tipoPagamento)
    {
        tipoPagamentoPesquisa = tipoPagamento;
    }
    
//    public void setPesquisar(boolean pesquisar)
//    {
//        this.pesquisar = pesquisar;
//    }
//    
//    public boolean getPesquisar()
//    {
//        return this.pesquisar;
//    }
    
    public void editarTipoPagamentoPesquisar()
    {
        edit();
        pesquisar();
    }
    
    
    public void pesquisar()
    {        
        if (finTipoPagamentoLista == null)
        {
            finTipoPagamentoLista = new ArrayList<>();
        }
        
        finTipoPagamentoLista = tipoPagamentoFacade.findTipoPagamento(tipoPagamentoPesquisa);
        
        if(finTipoPagamentoLista.isEmpty())
            Mensagem.warnMsg("Nenhum Tipo de Pagamento Encontrado");
    }

    public void edit ()
    {
        try
        {
            userTransaction.begin();
            if (tipoPagamentoEditar.getPkIdTipoPagamento() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            tipoPagamentoFacade.edit(tipoPagamentoEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("TipoPagamento editado com sucesso!");
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
    }

    public List<FinTipoPagamento> findAll ()
    {
        return tipoPagamentoFacade.findAll();
    }

    public String limpar()
    {
        tipoPagamento = null;
        return "tipoPagamentoFin?faces-redirect=true";
    }
    
    public void limparEditar()
    {
        tipoPagamentoEditar = null;
    }
    
//    public List<FinTipoPagamento> pesquisarTipoPagamento()
//    {
//        if(pesquisar)
//            return tipoPagamentoFacade.findTipoPagamento(tipoPagamentoPesquisa);
//        pesquisar = false;
//        return null;
//    }

    public List<FinTipoPagamento> getFinTipoPagamentoLista()
    {
        return finTipoPagamentoLista;
    }

    public void setFinTipoPagamentoLista(List<FinTipoPagamento> finTipoPagamentoLista)
    {
        this.finTipoPagamentoLista = finTipoPagamentoLista;
    }
    
}
