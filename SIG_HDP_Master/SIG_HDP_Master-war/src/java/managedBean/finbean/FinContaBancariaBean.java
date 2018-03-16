/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.finbean;

import entidade.FinBanco;
import entidade.FinContaBancaria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.FinContaBancariaFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class FinContaBancariaBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private FinContaBancariaFacade finContaBancariaFacade;

    /**
     *
     * Entidades
     */
    private FinContaBancaria finContaBancaria, finContaBancariaPesquisa, 
        finContaBancariaVisualizar, finContaBancariaEditar;
   
    private List<FinContaBancaria> finContaBancariaLista;
    
//    private boolean pesquisar = false;

    /**
     * Creates a new instance of ContaBancariaBean
     */
    public FinContaBancariaBean ()
    {
    }

    public FinContaBancaria getFinContaBancaria()
    {
        if (this.finContaBancaria == null)
        {
            this.finContaBancaria = getInstanciaContaBancaria();
        }

        return finContaBancaria;
    }

    public void setFinContaBancaria (FinContaBancaria finContaBancaria)
    {
        this.finContaBancaria = finContaBancaria;
    }

    public FinContaBancaria getFinContaBancariaEditar()
    {
        if (this.finContaBancariaEditar == null)
        {
            this.finContaBancariaEditar = getInstanciaContaBancaria();
        }
        return finContaBancariaEditar;
    }

    public void setFinContaBancariaEditar(FinContaBancaria finContaBancariaEditar)
    {
        this.finContaBancariaEditar = finContaBancariaEditar;
    }
    
    

    public FinContaBancaria getFinContaBancariaPesquisa()
    {
        if (this.finContaBancariaPesquisa == null)
        {
            this.finContaBancariaPesquisa = getInstanciaContaBancaria();
        }

        return finContaBancariaPesquisa;
    }

    public void setFinContaBancariaPesquisa(FinContaBancaria finContaBancariaPesquisa)
    {
        this.finContaBancariaPesquisa = finContaBancariaPesquisa;
    }

//    public boolean getPesquisar()
//    {
//        return pesquisar;
//    }
//
//    public void setPesquisar(boolean pesquisar)
//    {
//        this.pesquisar = pesquisar;
//    }
    
    public void pesquisar()
    {        
        if (finContaBancariaLista == null)
        {
            finContaBancariaLista = new ArrayList<>();
        }
        
        finContaBancariaLista = finContaBancariaFacade.findContaBancaria(finContaBancariaPesquisa);
        
        if(finContaBancariaLista.isEmpty())
            Mensagem.warnMsg("Nenhuma Conta Bancária Encontrada");
    }
    

    public FinContaBancaria getFinContaBancariaVisualizar()
    {
        return finContaBancariaVisualizar;
    }

    public void setFinContaBancariaVisualizar(FinContaBancaria finContaBancariaVisualizar)
    {
        this.finContaBancariaVisualizar = finContaBancariaVisualizar;
    }
    

    public FinContaBancaria getInstanciaContaBancaria()
    {
        FinContaBancaria finContaBancariaInstancia = new FinContaBancaria();
        finContaBancariaInstancia.setFkIdBanco(new FinBanco());
        return finContaBancariaInstancia;
    }
    

    public void gravarContaBancariaPesquisar()
    {
        create();
        pesquisar();
    }
    
    
    public void create()
    {
        try
        {
            userTransaction.begin();
            finContaBancariaFacade.create(finContaBancaria);
            userTransaction.commit();
            Mensagem.sucessoMsg("Conta Bancária Guardado Com Sucesso!");
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

        finContaBancaria = null;
    }

    
    public void editarContaBancariaPesquisar()
    {
        edit();
        pesquisar();
    }
    
    
    public void edit()
    {
        try
        {
            userTransaction.begin();
            if (finContaBancariaEditar.getPkIdContaBancaria() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            finContaBancariaFacade.edit(finContaBancariaEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Conta Bancária Editada Com Sucesso!");
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

    public List<FinContaBancaria> findAll()
    {
        return finContaBancariaFacade.findAll();
    }

    public void limpar()
    {
        finContaBancaria = null;
    }
    
//    public List<FinContaBancaria> pesquisarContaBancaria()
//    {
//        if(pesquisar)
//        {
//            return finContaBancariaFacade.findContaBancaria(finContaBancariaPesquisa);
//        }
//        pesquisar = false;
//        return null;
//    }
    public List<FinContaBancaria> getFinContaBancariaLista()
    {
        return finContaBancariaLista;
    }

    public void setFinContaBancariaLista(List<FinContaBancaria> finContaBancariaLista)
    {
        this.finContaBancariaLista = finContaBancariaLista;
    }
    
    
    
}
