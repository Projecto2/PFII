/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlReligiao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.GrlReligiaoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlReligiaoBean implements Serializable
{

     @Resource
     private UserTransaction userTransaction;

     @EJB
     private GrlReligiaoFacade religiaoFacade;

     /**
      *
      * Entidades
      */
     private GrlReligiao religiao,

    /**
     * Entidades
     */
    religiaoRemover;

     /**
      * Creates a new instance of ReligiaoBean
      */
     public GrlReligiaoBean ()
     {
     }

     public GrlReligiao getReligiao ()
     {
          if (this.religiao == null)
          {
               this.religiao = new GrlReligiao();
          }

          return religiao;
     }

     public void setReligiao (GrlReligiao religiao)
     {
          this.religiao = religiao;
     }

     public GrlReligiao getReligiaoRemover ()
     {
          return religiaoRemover;
     }

     public void setReligiaoRemover (GrlReligiao religiaoRemover)
     {
          this.religiaoRemover = religiaoRemover;
     }

     public String create ()
     {
          try
          {
               userTransaction.begin();
               religiaoFacade.create(religiao);
               userTransaction.commit();
               Mensagem.sucessoMsg("Religião guardada com sucesso!");
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

          religiao = null;

          return null;
     }

     public String edit ()
     {
          try
          {
               userTransaction.begin();
               if (religiao.getPkIdReligiao() == null)
               {
                    throw new NullPointerException("PK -> NULL");
               }
               religiaoFacade.edit(religiao);
               userTransaction.commit();
               Mensagem.sucessoMsg("Religião editada com sucesso!");
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

          religiao = null;

          return null;
     }

     public String remove ()
     {
          try
          {
               userTransaction.begin();

               religiaoFacade.remove(religiaoRemover);

               userTransaction.commit();

               Mensagem.sucessoMsg("Religião removida com sucesso!");
               religiaoRemover = null;
          }
          catch (Exception e)
          {
               try
               {
                    e.printStackTrace();
                    Mensagem.erroMsg("Esta religião possui registro de actividades, impossível remover !");
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

     public List<GrlReligiao> findAll ()
     {
          return religiaoFacade.findAll();
     }

     public String limpar ()
     {
          religiao = null;
          return "religiaoGrl?faces-redirect=true";
     }

}
