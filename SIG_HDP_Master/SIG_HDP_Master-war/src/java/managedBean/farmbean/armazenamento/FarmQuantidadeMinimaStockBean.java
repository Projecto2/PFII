/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.armazenamento;

import entidade.FarmLoteProdutoHasLocalArmazenamento;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmQuantidadeMinimaStockBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteProdutoHasLocalArmazenamentoFacade;
   private int qtdMinimaPermitida;

   /**
    * Creates a new instance of FarmQuantidadeMinimaStockBean
    */
   public FarmQuantidadeMinimaStockBean()
   {
   }

   public static FarmQuantidadeMinimaStockBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmQuantidadeMinimaStockBean farmQuantidadeMinimaStockBean = (FarmQuantidadeMinimaStockBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmQuantidadeMinimaStockBean");

      return farmQuantidadeMinimaStockBean;
   }
   
   public void editarQuantidadeMinimaPermitida()
   {
      if (qtdMinimaPermitida < 0)
         Mensagem.warnMsg("A quatidade definida não é válida. Verifique.");
      else
      {
         try
         {
            FarmVisualizarItensNoLocalBean farmVisualizarItensNoLocalBean = FarmVisualizarItensNoLocalBean.getInstanciaBean();
            userTransaction.begin();

            farmVisualizarItensNoLocalBean.getLotesPorProduto();
            for (FarmLoteProdutoHasLocalArmazenamento aux : farmVisualizarItensNoLocalBean.getListaLotesPorProduto())
            {
               aux.setQuantidadeMinimaPermitida(qtdMinimaPermitida);

               loteProdutoHasLocalArmazenamentoFacade.edit(aux);
            }
            userTransaction.commit();
//            FarmGraficosBean.getInstanciaBean().actualizarGraficos();
//            FarmNotificacaoBean.getInstanciaBean().pesquisarNotificacoes();
            Mensagem.sucessoMsg("Quantidade Mínima editada com sucesso.");
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
   }

   /**
    * @return the qtdMinimaPermitida
    */
   public int getQtdMinimaPermitida()
   {
      FarmVisualizarItensNoLocalBean farmVisualizarItensNoLocalBean = FarmVisualizarItensNoLocalBean.getInstanciaBean();

      farmVisualizarItensNoLocalBean.getLotesPorProduto();
      if(farmVisualizarItensNoLocalBean.getListaLotesPorProduto().isEmpty() || farmVisualizarItensNoLocalBean.getListaLotesPorProduto().get(0).getQuantidadeMinimaPermitida() == null)
         return 0;
              
      return farmVisualizarItensNoLocalBean.getListaLotesPorProduto().get(0).getQuantidadeMinimaPermitida();
   }

   /**
    * @param qtdMinimaPermitida the qtdMinimaPermitida to set
    */
   public void setQtdMinimaPermitida(int qtdMinimaPermitida)
   {
      this.qtdMinimaPermitida = qtdMinimaPermitida;
   }
}
