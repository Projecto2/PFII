/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlMarcaProduto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.GrlMarcaProdutoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class GrlMarcaProdutoListarBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   @EJB
   private GrlMarcaProdutoFacade marcaFacade;

   private GrlMarcaProduto marcaPesquisa, marcaEditar;
   private List<GrlMarcaProduto> marcasPesquisadas;

   /**
    * Creates a new instance of GrlMarcaProdutoFacade
    */
   public GrlMarcaProdutoListarBean()
   {
   }

   public GrlMarcaProduto getInstanciaMarca()
   {
      GrlMarcaProduto item = new GrlMarcaProduto();
      return item;
   }

   public void pesquisarMarcas()
   {
      marcasPesquisadas = marcaFacade.findMarcaProduto(new GrlMarcaProduto());
      if (marcasPesquisadas.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + marcasPesquisadas.size() + " registo(s) retornado(s).");
   }

   public void criar()
   {
      try
      {
         userTransaction.begin();
         marcaFacade.create(marcaEditar);
         userTransaction.commit();
         marcasPesquisadas = marcaFacade.findMarcaProduto(marcaPesquisa);
         Mensagem.sucessoMsg("Marca (Laboratório) cadastrada com sucesso.");
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

   public void editar()
   {
      try
      {
         userTransaction.begin();
         marcaFacade.edit(marcaEditar);
         userTransaction.commit();
         marcasPesquisadas = marcaFacade.findMarcaProduto(marcaPesquisa);
         Mensagem.sucessoMsg("Marca editada com sucesso.");
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

      setMarcaEditar(null);
   }

   public void eliminar()
   {
      try
      {
         userTransaction.begin();
         marcaFacade.remove(marcaEditar);
         userTransaction.commit();
         Mensagem.sucessoMsg("Marca eliminada com sucesso.");
         marcasPesquisadas = marcaFacade.findMarcaProduto(marcaPesquisa);
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

      setMarcaEditar(null);

   }

   public void limparCampos()
   {
      marcaEditar = getInstanciaMarca();
      marcaPesquisa = getInstanciaMarca();
      marcasPesquisadas = new ArrayList<>();
   }

   /**
    * @return the marcaPesquisa
    */
   public GrlMarcaProduto getMarcaPesquisa()
   {
      if (marcaPesquisa == null)
         marcaPesquisa = getInstanciaMarca();
      return marcaPesquisa;
   }

   /**
    * @param marcaPesquisa the marcaPesquisa to set
    */
   public void setMarcaPesquisa(GrlMarcaProduto marcaPesquisa)
   {
      this.marcaPesquisa = marcaPesquisa;
   }

   /**
    * @return the marcaEditar
    */
   public GrlMarcaProduto getMarcaEditar()
   {
      if (marcaEditar == null)
         marcaEditar = getInstanciaMarca();

      return marcaEditar;
   }

   /**
    * @param marcaEditar the marcaEditar to set
    */
   public void setMarcaEditar(GrlMarcaProduto marcaEditar)
   {
      this.marcaEditar = marcaEditar;
   }

   /**
    * @return the marcasPesquisadas
    */
   public List<GrlMarcaProduto> getMarcasPesquisadas()
   {
      pesquisarMarcas();
      if (marcasPesquisadas == null)
         marcasPesquisadas = new ArrayList<>();
      return marcasPesquisadas;
   }

   /**
    * @param marcasPesquisadas the marcasPesquisadas to set
    */
   public void setMarcasPesquisadas(List<GrlMarcaProduto> marcasPesquisadas)
   {
      this.marcasPesquisadas = marcasPesquisadas;
   }

}
