/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.tabelasAuxiliares;

import entidade.FarmCategoriaMedicamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sessao.FarmCategoriaMedicamentoFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmConsultaCategoriaMedicamentoBean implements Serializable
{

   @EJB
   private FarmCategoriaMedicamentoFacade categoriaFacade;
   private TreeNode raiz;

   /**
    * Creates a new instance of FarmConsultaCategoriaMedicamentoBean
    */
   public FarmConsultaCategoriaMedicamentoBean()
   {
   }

   public static FarmConsultaCategoriaMedicamentoBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmConsultaCategoriaMedicamentoBean farmConsultaCategoriaMedicamentoBean = (FarmConsultaCategoriaMedicamentoBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmConsultaCategoriaMedicamentoBean");

      return farmConsultaCategoriaMedicamentoBean;
   }
   
   public void pesquisarCategorias()
   {
      List<FarmCategoriaMedicamento> categoriasRaizes = ordenarRaizesPorCapitulo(categoriaFacade.findCategoriasRaizesOrderByCapitulo());
      this.raiz = new DefaultTreeNode("Raiz", null);
      adicionarNos(categoriasRaizes, raiz);
   }

   public void adicionarNos(List<FarmCategoriaMedicamento> categorias, TreeNode pai)
   {
      for (FarmCategoriaMedicamento categoria : categorias)
      {
         TreeNode no = new DefaultTreeNode(categoria, pai);

         adicionarNos(categoriaFacade.findFilhosCategoriaOrderByCapitulo(categoria), no);
      }
   }

   public List<FarmCategoriaMedicamento> ordenarRaizesPorCapitulo(List<FarmCategoriaMedicamento> listaCategorias)
   {
      List<FarmCategoriaMedicamento> listaResultante = new ArrayList<>();
      while(!listaCategorias.isEmpty())
      {
         FarmCategoriaMedicamento item = obterMenorDaLista(listaCategorias);
         listaResultante.add(item);
         listaCategorias.remove(item);
      }
      
      return listaResultante;
   }
   
   public FarmCategoriaMedicamento obterMenorDaLista(List<FarmCategoriaMedicamento> listaCategorias)
   {
      FarmCategoriaMedicamento menor = listaCategorias.get(0);
      for (FarmCategoriaMedicamento categoria : listaCategorias)
      {
         if(Integer.parseInt(categoria.getCapitulo()) < Integer.parseInt(menor.getCapitulo()))
            menor = categoria;
      }
      return menor;
   }
   
   public TreeNode getRaiz()
   {
      return raiz;
   }

}
