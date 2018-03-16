/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.SegPrivilegio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.SegPrivilegioFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class SegPrivilegioListarBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   @EJB
   private SegPrivilegioFacade privilegioFacade;

   private SegPrivilegio privilegioPesquisa, privilegioEditar;
   private List<SegPrivilegio> privilegiosPesquisados;

   /**
    * Creates a new instance of SegPrivilegioFacade
    */
   public SegPrivilegioListarBean()
   {
   }

   public SegPrivilegio getInstanciaPrivilegio()
   {
      SegPrivilegio item = new SegPrivilegio();
      return item;
   }

   public void pesquisarPrivilegios()
   {
      privilegiosPesquisados = privilegioFacade.findPrivilegio(privilegioPesquisa);
      if (privilegiosPesquisados.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + privilegiosPesquisados.size() + " registo(s) retornado(s).");
   }

   public void criar()
   {
      try
      {
         userTransaction.begin();
         privilegioFacade.create(privilegioEditar);
         userTransaction.commit();
         privilegiosPesquisados = privilegioFacade.findPrivilegio(privilegioPesquisa);
         Mensagem.sucessoMsg("Privilegio cadastrado com sucesso.");
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
      setPrivilegioEditar(null);
   }

   public void editar()
   {
      try
      {
         userTransaction.begin();
         privilegioFacade.edit(privilegioEditar);
         userTransaction.commit();
         privilegiosPesquisados = privilegioFacade.findPrivilegio(privilegioPesquisa);
         Mensagem.sucessoMsg("Privilegio editado com sucesso.");
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

      setPrivilegioEditar(null);
   }

   public void eliminar()
   {
      try
      {
         userTransaction.begin();
         privilegioFacade.remove(privilegioEditar);
         userTransaction.commit();
         Mensagem.sucessoMsg("Privilegio eliminado com sucesso.");
         privilegiosPesquisados = privilegioFacade.findPrivilegio(privilegioPesquisa);
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

      setPrivilegioEditar(null);

   }

   public void limparCampos()
   {
      privilegioEditar = getInstanciaPrivilegio();
      privilegioPesquisa = getInstanciaPrivilegio();
      privilegiosPesquisados = new ArrayList<>();
   }

   /**
    * @return the privilegioPesquisa
    */
   public SegPrivilegio getPrivilegioPesquisa()
   {
      if (privilegioPesquisa == null)
         privilegioPesquisa = getInstanciaPrivilegio();
      return privilegioPesquisa;
   }

   /**
    * @param privilegioPesquisa the privilegioPesquisa to set
    */
   public void setPrivilegioPesquisa(SegPrivilegio privilegioPesquisa)
   {
      this.privilegioPesquisa = privilegioPesquisa;
   }

   /**
    * @return the privilegioEditar
    */
   public SegPrivilegio getPrivilegioEditar()
   {
      if (privilegioEditar == null)
         privilegioEditar = getInstanciaPrivilegio();

      return privilegioEditar;
   }

   /**
    * @param privilegioEditar the privilegioEditar to set
    */
   public void setPrivilegioEditar(SegPrivilegio privilegioEditar)
   {
      this.privilegioEditar = privilegioEditar;
   }

   /**
    * @return the privilegiosPesquisados
    */
   public List<SegPrivilegio> getPrivilegiosPesquisados()
   {
      if (privilegiosPesquisados == null)
         privilegiosPesquisados = new ArrayList<>();
      return privilegiosPesquisados;
   }

   /**
    * @param privilegiosPesquisados the privilegiosPesquisados to set
    */
   public void setPrivilegiosPesquisados(List<SegPrivilegio> privilegiosPesquisados)
   {
      this.privilegiosPesquisados = privilegiosPesquisados;
   }

}
