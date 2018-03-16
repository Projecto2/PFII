/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.movimentos;

import entidade.FarmLocalArmazenamento;
import entidade.FarmPedido;
import entidade.FarmPedidoHasProduto;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.FarmPedidoHasProdutoFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmVisualizarItensPedidoBean implements Serializable
{
   @EJB
   private FarmPedidoHasProdutoFacade produtoPedidoFacade;
   
   private FarmPedido pedidoPesquisa;
   private List<FarmPedidoHasProduto> produtosPedido;

   /**
    * Creates a new instance of FarmVisualizarItensPedidoBean
    */
   public FarmVisualizarItensPedidoBean()
   {
      
   }
   
   public static FarmVisualizarItensPedidoBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      return (FarmVisualizarItensPedidoBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmVisualizarItensPedidoBean");
   }
   
   public FarmPedido getInstanciaPedido()
   {
      setPedidoPesquisa(new FarmPedido());
      
      getPedidoPesquisa().setFkIdFuncionarioSolicitou(new RhFuncionario());
      getPedidoPesquisa().setFkIdFuncionarioAtendeu(new RhFuncionario());
      getPedidoPesquisa().setFkLocalDestinoPedido(new FarmLocalArmazenamento());
      getPedidoPesquisa().setFkLocalOrigemPedido(new FarmLocalArmazenamento());
      return getPedidoPesquisa();
   }
   
   public void getItensPedido()
   {
      setProdutosPedido(produtoPedidoFacade.findProdutoPedido(pedidoPesquisa));
   }
   
    /**
    * @return the pedidoPesquisa
    */
   public FarmPedido getPedidoPesquisa()
   {
      if(pedidoPesquisa == null)
         pedidoPesquisa = getInstanciaPedido();
      return pedidoPesquisa;
   }

   /**
    * @param pedidoPesquisa the pedidoPesquisa to set
    */
   public void setPedidoPesquisa(FarmPedido pedidoPesquisa)
   {
      this.pedidoPesquisa = pedidoPesquisa;
   }

   /**
    * @return the produtosPedido
    */
   public List<FarmPedidoHasProduto> getProdutosPedido()
   {
      getItensPedido();
      return produtosPedido;
   }

   /**
    * @param produtosPedido the produtosPedido to set
    */
   public void setProdutosPedido(List<FarmPedidoHasProduto> produtosPedido)
   {
      this.produtosPedido = produtosPedido;
   }
}
