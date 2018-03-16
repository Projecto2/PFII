/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhProfissao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhProfissaoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhProfissaoBean implements Serializable
{

     @Resource
     private UserTransaction userTransaction;

     @EJB
     private RhProfissaoFacade profissaoFacade;

     /**
      *
      * Entidades
      */
     private RhProfissao profissao, profissaoRemover;

     /**
      * Creates a new instance of ProfissaoBean
      */
     public RhProfissaoBean ()
     {
     }

     public RhProfissao getProfissao ()
     {
          if (this.profissao == null)
          {
               this.profissao = new RhProfissao();
          }

          return profissao;
     }

     public void setProfissao (RhProfissao profissao)
     {
          this.profissao = profissao;
     }

     public RhProfissao getProfissaoRemover ()
     {
          return profissaoRemover;
     }

     public void setProfissaoRemover (RhProfissao profissaoRemover)
     {
          this.profissaoRemover = profissaoRemover;
     }

     public String create ()
     {
          try
          {
               userTransaction.begin();
               profissaoFacade.create(profissao);
               userTransaction.commit();
               Mensagem.sucessoMsg("Profissão guardada com sucesso!");
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

          profissao = null;

          return null;
     }

     public String edit ()
     {
          try
          {
               userTransaction.begin();
               if (profissao.getPkIdProfissao() == null)
               {
                    throw new NullPointerException("PK -> NULL");
               }
               profissaoFacade.edit(profissao);
               userTransaction.commit();
               Mensagem.sucessoMsg("Profissão editada com sucesso!");
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

          profissao = null;

          return null;
     }

     public String remove ()
     {
          try
          {
               userTransaction.begin();

               if (profissaoRemover.getDescricao().equals("Médico") || profissaoRemover.getDescricao().equals("Enfermeiro"))
               {
                    Mensagem.erroMsg("Impossível remover esta profissão");
                    profissaoRemover = null;
                    return null;
               }

               profissaoFacade.remove(profissaoRemover);

               userTransaction.commit();

               Mensagem.sucessoMsg("Profissão removida com sucesso!");
               profissaoRemover = null;
          }
          catch (Exception e)
          {
               try
               {
                    e.printStackTrace();
                    Mensagem.erroMsg("Esta profissão possui registro de actividades, impossível remover !");
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

     public List<RhProfissao> findAll ()
     {
          return profissaoFacade.findAll();
     }

     public String limpar ()
     {
          profissao = null;
          return "profissaoRh?faces-redirect=true";
     }

}
