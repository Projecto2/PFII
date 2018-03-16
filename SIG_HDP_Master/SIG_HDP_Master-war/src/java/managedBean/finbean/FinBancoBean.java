/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.finbean;

import entidade.FinBanco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.FinBancoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class FinBancoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private FinBancoFacade bancoFacade;

    private FinBanco finBanco, finBancoEditar, bancoPesquisa;
    
    private List<FinBanco> finBancoLista;
    
//    private boolean pesquisar = false;

    /**
     * Creates a new instance of BancoBean
     */
    public FinBancoBean ()
    {
    }

//    public FinBanco getBanco ()
//    {
//        if (this.finBanco == null)
//        {
//            this.finBanco = new FinBanco();
//        }
//        return finBanco;
//    }
//
//    public void setBanco (FinBanco finBanco)
//    {
//        this.finBanco = finBanco;
//    }
    public FinBanco getFinBanco()
    {
        if (this.finBanco == null)
        {
            this.finBanco = new FinBanco();
        }
        return finBanco;
    }

    public void setFinBanco(FinBanco finBanco)
    {
        this.finBanco = finBanco;
    }
    
    public FinBanco getFinBancoEditar()
    {
        if (this.finBancoEditar == null)
        {
            this.finBancoEditar = new FinBanco();
        }
        return finBancoEditar;
    }

    public void setFinBancoEditar(FinBanco finBancoEditar)
    {
        this.finBancoEditar = finBancoEditar;
    }
    
    
    public void gravarBancoPesquisar()
    {
        create();
        pesquisar();
    }
    
    public void create()
    {
        try
        {
            userTransaction.begin();
            bancoFacade.create(finBanco);
            userTransaction.commit();
            Mensagem.sucessoMsg("Banco guardado com sucesso!");
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

        finBanco = null;
    }
    
    public FinBanco getBancoPesquisa ()
    {
        if (this.bancoPesquisa == null)
        {
            this.bancoPesquisa = new FinBanco(0, "");
        }

        return bancoPesquisa;
    }
    
    public void setBancoPesquisa (FinBanco banco)
    {
        bancoPesquisa = banco;
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
        if (finBancoLista == null)
        {
            finBancoLista = new ArrayList<>();
        }
        
        finBancoLista = bancoFacade.findBanco(bancoPesquisa);
        
        if(finBancoLista.isEmpty())
            Mensagem.warnMsg("Nenhum Serviço Encontrado");
    }

    
    public void editarBancoPesquisar()
    {
        edit();
        pesquisar();
    }
    
    public void edit()
    {
        try
        {
            userTransaction.begin();
            if (finBancoEditar.getPkIdBanco() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            bancoFacade.edit(finBancoEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Banco Editado Com Sucesso!");
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

    public List<FinBanco> findAll ()
    {
        return bancoFacade.findAll();
    }

    public String limpar()
    {
        finBanco = null;
        return "bancoFin?faces-redirect=true";
    }
    
//    public List<FinBanco> pesquisarBanco()
//    {
//        if(pesquisar)
//            return bancoFacade.findBanco(bancoPesquisa);
//        pesquisar = false;
//        return null;
//    }
    public List<FinBanco> getFinBancoLista()
    {
        return finBancoLista;
    }

    public void setFinBancoLista(List<FinBanco> finBancoLista)
    {
        this.finBancoLista = finBancoLista;
    }
    
    
    
}
