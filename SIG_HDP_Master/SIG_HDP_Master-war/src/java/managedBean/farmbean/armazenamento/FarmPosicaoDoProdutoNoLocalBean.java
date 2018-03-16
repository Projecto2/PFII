/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.armazenamento;

import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmProduto;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
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
public class FarmPosicaoDoProdutoNoLocalBean
{

   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteProdutoHasLocalArmazenamentoFacade;
   private String posicao;

   /**
    * Creates a new instance of FarmPosicaoDoProdutoNoLocalBean
    */
   public FarmPosicaoDoProdutoNoLocalBean()
   {
   }

   public static FarmPosicaoDoProdutoNoLocalBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmPosicaoDoProdutoNoLocalBean farmPosicaoDoProdutoNoLocalBean
              = (FarmPosicaoDoProdutoNoLocalBean) context.getELContext().getELResolver().getValue(context.getELContext(),
                      null, "farmPosicaoDoProdutoNoLocalBean");

      return farmPosicaoDoProdutoNoLocalBean;
   }

   /**
    * Verifica se Uma dada posicao num dado local ja esta ocupada
    *
    * @param posicao
    * @param listaItemsNoLocal
    * @return Retorna True ou False
    */
   public boolean posicaoOcupada(String posicao, List<FarmLoteProdutoHasLocalArmazenamento> listaItemsNoLocal)
   {
      if(listaItemsNoLocal == null)
         return false;
      
      for (FarmLoteProdutoHasLocalArmazenamento item : listaItemsNoLocal)
      {
         if (item.getPosicao() != null && item.getPosicao().equals(posicao))
            return true;
      }
      return false;
   }

   public void editarPosicao()
   {
      System.out.println("editando a posicao");

      try
      {
 
         FarmVisualizarItensNoLocalBean farmVisualizarItensNoLocalBean
                 = FarmVisualizarItensNoLocalBean.getInstanciaBean();
         
         userTransaction.begin();
//         if (posicaoOcupada(posicao, loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(farmVisualizarItensNoLocalBean.getLocal(), "")))
//         {
//            Mensagem.warnMsg("Posição ocupada. Verifique.");
//         }
//         else
//         {
            farmVisualizarItensNoLocalBean.getLotesPorProduto();
            for (FarmLoteProdutoHasLocalArmazenamento aux : farmVisualizarItensNoLocalBean.getListaLotesPorProduto())
            {
               aux.setPosicao(posicao);

               loteProdutoHasLocalArmazenamentoFacade.edit(aux);
            }
            userTransaction.commit();
            Mensagem.sucessoMsg("Posição editada com sucesso.");
//         }

      }
      catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e)
      {
         try
         {
            userTransaction.rollback();
            Mensagem.warnMsg("Ocorreu um erro ao editar a posição");
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            System.out.println("Rollback: " + ex.toString());
         }
      }

   }

   public String getPosicao(FarmProduto produto, FarmLocalArmazenamento local)
   {
      System.out.println("produto pos: " + produto);
      System.out.println("local pos: " + local);
      for (FarmLoteProdutoHasLocalArmazenamento item : loteProdutoHasLocalArmazenamentoFacade.findAll())
      {
         System.out.println("analisando produto " + item.getFkIdLoteProduto().getFkIdProduto().getPkIdProduto() + " no local " + item.getFkIdLocalArmazenamento().getPkIdLocalArmazenamento());
         if (item.getFkIdLoteProduto().getFkIdProduto().getPkIdProduto() == produto.getPkIdProduto()
                 && item.getFkIdLocalArmazenamento().getPkIdLocalArmazenamento() == local.getPkIdLocalArmazenamento())

            return item.getPosicao();
      }
      System.out.println("return ....");
      return "";
   }

   /**
    * @return the posicao
    */
   public String getPosicao()
   {
      System.out.println("get pos: " + posicao);
      return posicao;
   }

   /**
    * @param posicao the posicao to set
    */
   public void setPosicao(String posicao)
   {
      System.out.println("set pos: " + posicao);
      this.posicao = posicao;
   }

}
