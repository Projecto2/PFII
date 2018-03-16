/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.tabelasAuxiliares;

import entidade.FarmCategoriaMedicamento;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.FarmCategoriaMedicamentoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmCategoriaMedicamentoListarBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmCategoriaMedicamentoFacade categoriaFacade;

   private FarmCategoriaMedicamento categoriaEditar, categoriaRemover;
   private List<FarmCategoriaMedicamento> categoriasPesquisadas;

   /**
    * Creates a new instance of FarmCategoriaMedicamentoListarBean
    */
   public FarmCategoriaMedicamentoListarBean()
   {
   }

   public void pesquisarCategorias()
   {
      categoriasPesquisadas = categoriaFacade.findCategoriaOrderByDescricao(getInstanciaCategoria());
   }

   public void definirCapitulo(FarmCategoriaMedicamento pai, FarmCategoriaMedicamento filho)
   {
      if (pai != null && pai.getPkIdCategoriaMedicamento() != null)
      {
         List<FarmCategoriaMedicamento> todasCategorias = categoriaFacade.findCategoriasRaizesOrderByDescricao();

         if (todasCategorias.isEmpty())
            filho.setCapitulo("1");
         else
         {
            String maiorCapitulo = 0 + "";
            for (FarmCategoriaMedicamento cat : todasCategorias)
            {
               if(Integer.parseInt(cat.getCapitulo()) > Integer.parseInt(maiorCapitulo) )
                  maiorCapitulo = cat.getCapitulo();
            }

            filho.setCapitulo(Integer.parseInt(maiorCapitulo + "") + 1 + "");
         }
      }
      else
      {
         pai = categoriaFacade.find(pai.getPkIdCategoriaMedicamento());
         List<FarmCategoriaMedicamento> filhos = categoriaFacade.findFilhosCategoriaOrderByCapitulo(pai);
         
         if (filhos.isEmpty())
         {
            filho.setCapitulo(pai.getCapitulo() + ".1");
         }
         else
         {
            String ultimoCaracter = "" + filhos.get(filhos.size() - 1).getCapitulo().charAt(filhos.get(filhos.size() - 1).getCapitulo().length() - 1);
            
            filho.setCapitulo(pai.getCapitulo() + "." + (Integer.parseInt(ultimoCaracter) + 1));
         }
      }
   }

   public void create()
   {
      try
      {
         userTransaction.begin();

         FarmCategoriaMedicamento catAux = getInstanciaCategoria();
         catAux.setDescricao(categoriaEditar.getDescricao());

         if (!categoriaFacade.findCategoriaOrderByDescricao(catAux).isEmpty())
            throw new IllegalStateException();
         else
         {
            if (categoriaEditar.getFkIdCategoriaSuper().getPkIdCategoriaMedicamento() == null)
               categoriaEditar.setFkIdCategoriaSuper(null);
            definirCapitulo(categoriaEditar.getFkIdCategoriaSuper(), categoriaEditar);
            categoriaFacade.create(categoriaEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Categoria guardada com sucesso!");
            pesquisarCategorias();
         }

      }
      catch (Exception e)
      {
         try
         {
            userTransaction.rollback();
            System.out.println(e.toString());
         }
         catch (SecurityException | SystemException ex)
         {
            System.out.println("Rollback: " + ex.toString());
         }
         catch (IllegalStateException ex)
         {
            Mensagem.warnMsg("Esta Categoria ja existe!");
         }
      }

      categoriaEditar = null;
   }

   public void edit()
   {
      try
      {
         userTransaction.begin();
         if (categoriaEditar.getPkIdCategoriaMedicamento() == null)
         {
            throw new NullPointerException("PK -> NULL");
         }
         if (categoriaEditar == categoriaEditar.getFkIdCategoriaSuper())
            Mensagem.warnMsg("Categoria pai inválida!");
         else
         {
            definirCapitulo(categoriaEditar.getFkIdCategoriaSuper(), categoriaEditar);
            int fk = categoriaEditar.getFkIdCategoriaSuper().getPkIdCategoriaMedicamento();
            categoriaEditar.setFkIdCategoriaSuper(new FarmCategoriaMedicamento(fk));
            categoriaFacade.edit(categoriaEditar);
            userTransaction.commit();

            Mensagem.sucessoMsg("Categoria editada com sucesso!");
            pesquisarCategorias();
         }
      }
      catch (Exception e)
      {
         try
         {
            userTransaction.rollback();
            System.out.println(e.toString());
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            System.out.println("Rollback: " + ex.toString());
         }
      }

      categoriaEditar = null;
   }

   public void remove()
   {
      try
      {
         userTransaction.begin();

         categoriaFacade.remove(categoriaRemover);

         userTransaction.commit();

         Mensagem.sucessoMsg("Categoria removida com sucesso!");
         pesquisarCategorias();
         categoriaRemover = null;
      }
      catch (Exception e)
      {
         try
         {
            e.printStackTrace();
            Mensagem.erroMsg("Esta Categoria possui registro de actividades, impossível remover !");
            System.out.println(e.toString());
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            e.printStackTrace();
            System.out.println("Rollback: " + ex.toString());
         }
      }
   }

   public FarmCategoriaMedicamento getCategoriaEditar()
   {
      if (categoriaEditar == null)
         categoriaEditar = getInstanciaCategoria();
      return categoriaEditar;
   }

   public void setCategoriaEditar(FarmCategoriaMedicamento categoriaEditar)
   {
      this.categoriaEditar = categoriaEditar;
      if (categoriaEditar != null && categoriaEditar.getFkIdCategoriaSuper() == null)
         categoriaEditar.setFkIdCategoriaSuper(getInstanciaCategoria());
   }

   public FarmCategoriaMedicamento getCategoriaRemover()
   {
      if (categoriaRemover == null)
         categoriaRemover = getInstanciaCategoria();
      return categoriaRemover;
   }

   public void setCategoriaRemover(FarmCategoriaMedicamento categoriaRemover)
   {
      this.categoriaRemover = categoriaRemover;
   }

   public List<FarmCategoriaMedicamento> getCategoriasPesquisadas()
   {
      if (categoriasPesquisadas == null)
         pesquisarCategorias();
      return categoriasPesquisadas;
   }

   public void setCategoriasPesquisadas(List<FarmCategoriaMedicamento> categoriasPesquisadas)
   {
      this.categoriasPesquisadas = categoriasPesquisadas;
   }

   public static FarmCategoriaMedicamento getInstanciaCategoria()
   {
      FarmCategoriaMedicamento item = new FarmCategoriaMedicamento();
      item.setFkIdCategoriaSuper(new FarmCategoriaMedicamento());
      return item;
   }

}
