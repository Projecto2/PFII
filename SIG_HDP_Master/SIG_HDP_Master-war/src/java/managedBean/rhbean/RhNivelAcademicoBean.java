/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhNivelAcademico;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhNivelAcademicoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhNivelAcademicoBean implements Serializable
{

     @Resource
     private UserTransaction userTransaction;

     @EJB
     private RhNivelAcademicoFacade nivelAcademicoFacade;

     /**
      *
      * Entidades
      */
     private RhNivelAcademico nivelAcademico, nivelAcademicoRemover;

     /**
      * Creates a new instance of NivelAcademicoBean
      */
     public RhNivelAcademicoBean ()
     {
     }

     public RhNivelAcademico getNivelAcademico ()
     {
          if (this.nivelAcademico == null)
          {
               this.nivelAcademico = new RhNivelAcademico();
          }

          return nivelAcademico;
     }

     public void setNivelAcademico (RhNivelAcademico nivelAcademico)
     {
          this.nivelAcademico = nivelAcademico;
     }

     public RhNivelAcademico getNivelAcademicoRemover ()
     {
          return nivelAcademicoRemover;
     }

     public void setNivelAcademicoRemover (RhNivelAcademico nivelAcademicoRemover)
     {
          this.nivelAcademicoRemover = nivelAcademicoRemover;
     }
     
     public String create ()
     {
          try
          {
               userTransaction.begin();

               nivelAcademicoFacade.create(nivelAcademico);
               userTransaction.commit();
               nivelAcademico = null;
               Mensagem.sucessoMsg("Nível acadêmico guardado com sucesso!");
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

          return null;
     }

     public String edit ()
     {
          try
          {
               userTransaction.begin();
               if (nivelAcademico.getPkIdNivelAcademico() == null)
               {
                    throw new NullPointerException("PK -> NULL");
               }

               nivelAcademicoFacade.edit(nivelAcademico);
               userTransaction.commit();
               nivelAcademico = null;
               Mensagem.sucessoMsg("Nível acadêmico editado com sucesso!");
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

          return null;
     }

     public String remove ()
     {
          try
          {
               userTransaction.begin();

               nivelAcademicoFacade.remove(nivelAcademicoRemover);

               userTransaction.commit();

               Mensagem.sucessoMsg("Nível acadêmico removido com sucesso!");
               nivelAcademicoRemover = null;
          }
          catch (Exception e)
          {
               try
               {
                    e.printStackTrace();
                    Mensagem.erroMsg("Este nível acadêmico possui registro de actividades, impossível remover !");
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
     
     public List<RhNivelAcademico> findAll ()
     {
          return nivelAcademicoFacade.findAll();
     }

     public String limpar ()
     {
          nivelAcademico = null;
          return "nivelAcademicoRh?faces-redirect=true";
     }

}
