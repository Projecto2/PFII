/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlPais;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.grlbean.carregamentoExcel.GrlPaisExcelBean;
import sessao.GrlPaisFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlPaisBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;

   @EJB
   private GrlPaisFacade paisFacade;

   /**
    *
    * Entidades
    */
   private GrlPais pais,

   /**
    * Entidades
    */
   paisRemover;

   /**
    * Creates a new instance of PaisBean
    */
   public GrlPaisBean()
   {
   }

   public GrlPais getPais()
   {
      if (this.pais == null)
      {
         this.pais = new GrlPais();
      }

      return pais;
   }

   public void setPais(GrlPais pais)
   {
      this.pais = pais;
   }

   public GrlPais getPaisRemover()
   {
      return paisRemover;
   }

   public void setPaisRemover(GrlPais paisRemover)
   {
      this.paisRemover = paisRemover;
   }

   public String create()
   {
      try
      {
         userTransaction.begin();
         paisFacade.create(pais);
         userTransaction.commit();
         Mensagem.sucessoMsg("País guardada com sucesso!");
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

      pais = null;

      return null;
   }

   public String edit()
   {
      try
      {
         userTransaction.begin();
         if (pais.getPkIdPais() == null)
         {
            throw new NullPointerException("PK -> NULL");
         }
         paisFacade.edit(pais);
         userTransaction.commit();
         Mensagem.sucessoMsg("País editado com sucesso!");
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

      pais = null;

      return null;
   }

   public String remove()
   {
      try
      {
         userTransaction.begin();

         paisFacade.remove(paisRemover);

         userTransaction.commit();

         Mensagem.sucessoMsg("País removido com sucesso!");
         paisRemover = null;
      }
      catch (Exception e)
      {
         try
         {
            e.printStackTrace();
            Mensagem.erroMsg("Este país possui registro de actividades, impossível remover !");
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

   public List<GrlPais> findAll()
   {
      return paisFacade.findAll();
   }

   public String limpar()
   {
      pais = null;
      return "paisGrl?faces-redirect=true";
   }

   public void carregar()
   {
      GrlPaisExcelBean.getInstanciaBean().carregarPaisTabela();

      Mensagem.sucessoMsg("Dados Carregados Com Sucesso!");

      findAll();
      Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + findAll().size() + " registo(s) retornado(s).");
   }
}
