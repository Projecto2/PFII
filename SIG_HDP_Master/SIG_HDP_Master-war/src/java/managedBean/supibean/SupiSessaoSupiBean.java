/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

import entidade.SupiSeccao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.SupiSeccaoFacade;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiSessaoSupiBean implements Serializable{
    @Resource
    private UserTransaction userTransaction;
    @EJB
    private SupiSeccaoFacade supiSeccaoFacade;
    
    
    private SupiSeccao supiSeccao;
    
    
    /**
     * Creates a new instance of SessaoSupiBean
     */
    
    
    
    
    public SupiSeccao getSupiSeccao(){
        if(supiSeccao == null){
            supiSeccao = new SupiSeccao();
        
        }
        return supiSeccao;
    
    }

    public void setSupiSeccao(SupiSeccao supiSeccao)
    {
        this.supiSeccao = supiSeccao;
    }
    
    
   public String create () 
     {
          try
          {
               userTransaction.begin();
               supiSeccaoFacade.create(supiSeccao);
               userTransaction.commit();
               Mensagem.sucessoMsg("Secção guardada com sucesso!");
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

          supiSeccao = null;

          return null;
     }
   
    public String edit ()
     {
          try
          {
               userTransaction.begin();
               if (supiSeccao.getPkIdSeccao() == null)
               {
                    throw new NullPointerException("PK -> NULL");
               }
               supiSeccaoFacade.edit(supiSeccao);
               userTransaction.commit();
               Mensagem.sucessoMsg("Secção editada com sucesso!");
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

          supiSeccao = null;

          return null;
     }
    
     public void eliminar(int idSeccao)
    {

        FacesContext fc = FacesContext.getCurrentInstance();

        try
        {

            supiSeccao = supiSeccaoFacade.find(idSeccao);

            if (supiSeccao.getSupiEscalaList() != null)
            {

                supiSeccaoFacade.remove(supiSeccao);

                fc.addMessage(null, new FacesMessage("Secção Removida com sucesso!"));

                supiSeccao = new SupiSeccao();

            } else
            {
                fc.addMessage(null, new FacesMessage("Erro ao Eliminar a Secção " + ""));

            }

        } catch (Exception ex)
        {
            fc.addMessage(null, new FacesMessage("Erro :" + "" + ex.getMessage()));
        }
    }
//    public List<SupiSeccao> getFindall(){
//      return supiSeccaoFacade.findAll();
//    }
    
    public List<SupiSeccao> findall()
    {
        return supiSeccaoFacade.findAll();
    }
    
}
