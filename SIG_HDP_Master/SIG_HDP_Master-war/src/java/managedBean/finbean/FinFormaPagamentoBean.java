/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.finbean;

import entidade.FinFormaPagamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.FinFormaPagamentoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class FinFormaPagamentoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private FinFormaPagamentoFacade formaPagamentoFacade;

    private FinFormaPagamento formaPagamento, formaPagamentoPesquisa, formaPagamentoEditar;
    
    private List<FinFormaPagamento> formaPagamentoLista;
    
//    private boolean pesquisar = false;

    /**
     * Creates a new instance of FormaPagamentoBean
     */
    public FinFormaPagamentoBean ()
    {
    }

    public FinFormaPagamento getFormaPagamento ()
    {
        if (this.formaPagamento == null)
        {
            this.formaPagamento = new FinFormaPagamento();
        }

        return formaPagamento;
    }

    public void setFormaPagamento (FinFormaPagamento formaPagamento)
    {
        this.formaPagamento = formaPagamento;
    }

    public FinFormaPagamento getFormaPagamentoEditar()
    {
        if (this.formaPagamentoEditar == null)
        {
            this.formaPagamentoEditar = new FinFormaPagamento();
        }

        return formaPagamentoEditar;
    }

    public void setFormaPagamentoEditar(FinFormaPagamento formaPagamentoEditar)
    {
        this.formaPagamentoEditar = formaPagamentoEditar;
    }
    
    
    public void gravarFormaPagamentoPesquisar()
    {
        create();
        pesquisar();
    }

    public void create()
    {
        try
        {
            userTransaction.begin();
            formaPagamentoFacade.create(formaPagamento);
            userTransaction.commit();
            Mensagem.sucessoMsg("FormaPagamento guardado com sucesso!");
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
        formaPagamento = null;
    }
    
    public FinFormaPagamento getFormaPagamentoPesquisa ()
    {
        if (this.formaPagamentoPesquisa == null)
        {
            this.formaPagamentoPesquisa = new FinFormaPagamento(0, "");
        }

        return formaPagamentoPesquisa;
    }
    
    public void setFormaPagamentoPesquisa (FinFormaPagamento formaPagamento)
    {
        formaPagamentoPesquisa = formaPagamento;
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
    
    public void pesquisar()
    {        
        if (formaPagamentoLista == null)
        {
            formaPagamentoLista = new ArrayList<>();
        }
        
        formaPagamentoLista = formaPagamentoFacade.findFormaPagamento(formaPagamentoPesquisa);
        
        if(formaPagamentoLista.isEmpty())
            Mensagem.warnMsg("Nenhuma Forma de Pagamento Encontrada");
    }
    
    
    public void editarFormaDePagamentoPesquisar()
    {
        edit();
        pesquisar();
    }

    public void edit()
    {
        try
        {
            userTransaction.begin();
            if (formaPagamentoEditar.getPkIdFormaPagamento()== null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            formaPagamentoFacade.edit(formaPagamentoEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("FormaPagamento editado com sucesso!");
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

    public List<FinFormaPagamento> findAll ()
    {
        return formaPagamentoFacade.findAll();
    }

    public String limpar()
    {
        formaPagamento = null;
        return "formaPagamentoFin?faces-redirect=true";
    }
    
//    public List<FinFormaPagamento> pesquisarFormaPagamento()
//    {
//        if(pesquisar)
//            return formaPagamentoFacade.findFormaPagamento(formaPagamentoPesquisa);
//        pesquisar = false;
//        return null;
//    }

    public List<FinFormaPagamento> getFormaPagamentoLista()
    {
        return formaPagamentoLista;
    }

    public void setFormaPagamentoLista(List<FinFormaPagamento> formaPagamentoLista)
    {
        this.formaPagamentoLista = formaPagamentoLista;
    }
    
    
    
}
