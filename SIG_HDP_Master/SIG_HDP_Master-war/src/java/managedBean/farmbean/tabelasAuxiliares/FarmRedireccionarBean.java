/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.tabelasAuxiliares;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmRedireccionarBean implements Serializable
{
   /**
    * Creates a new instance of FarmRedireccionarBean
    */
   public FarmRedireccionarBean()
   {
   }
   
   public String red_VisualizarItensPedido()
   {
      return "visualizarItensPedidoFarm.xhtml?faces-redirect=true";
   }
   
   public String red_PedidosPendentes()
   {
      return "pedidosPendentesFarm.xhtml?faces-redirect=true";
   }

   public String red_AtenderPedidos()
   {
      return "atenderPedidoFarm.xhtml?faces-redirect=true";
   }

   public String red_VisualizarItensNoLocal()
   {
      return "localArmazenamentoVerItensFarm.xhtml?faces-redirect=true";
   }

   public String red_LocalArmazenamentoListar()
   {
      return "localArmazenamentoListarFarm.xhtml?faces-redirect=true";
   }

   public String red_VisualizarFichaDeStock()
   {
      return "visualizarFichaDeStockFarm.xhtml?faces-redirect=true";
   }
   
   public String red_VisualizarIntesFornecimento()
   {
      return "fornecimentoVisualizarItensFarm?faces-redirect=true";
   }
   
   public String red_NovoIntesFornecedor()
   {
      return "fornecedorNovoFarm?faces-redirect=true";
   }
}
