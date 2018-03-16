/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.movimentos;

import entidade.FarmEstadoPedido;
import entidade.FarmFichaStock;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmMovimento;
import entidade.FarmMovimentoHasProduto;
import entidade.FarmPedido;
import entidade.FarmPedidoHasProduto;
import entidade.FarmProduto;
import entidade.FarmTipoQuantidade;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import managedBean.segbean.SegLoginBean;
import sessao.FarmFichaStockFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.FarmMovimentoFacade;
import sessao.FarmMovimentoHasProdutoFacade;
import sessao.FarmPedidoFacade;
import sessao.FarmPedidoHasProdutoFacade;
import sessao.FarmTipoQuantidadeFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmPedidoAtenderBean implements Serializable
{
   
   @Resource
   private UserTransaction userTransaction;
   
   @EJB
   private FarmPedidoFacade pedidoFacade;
   @EJB
   private FarmPedidoHasProdutoFacade pedidoHasProdutoFacade;
   @EJB
   private FarmMovimentoFacade movimentoFacade;
   @EJB
   private FarmMovimentoHasProdutoFacade movimentoHasProdutoFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteHaslocalFacade;
   @EJB
   private FarmFichaStockFacade fichaStockFacade;
   @EJB
   private FarmTipoQuantidadeFacade tipoQuantidadeFacade;
   
   private FarmTipoQuantidade tipoQuantidade;
   
   private FarmPedido pedido;
   private FarmMovimento movimento;
   private FarmLoteProduto lote;
   private FarmMovimentoHasProduto movimentoHasProduto;
   
   private List<FarmPedidoHasProduto> itensPedido;
   private ArrayList<FarmMovimentoHasProduto> lotesProdutoMovimento;
   
   private boolean primeiraVez = true;

   /**
    * Creates a new instance of FarmPedidoAtenderBean
    */
   public FarmPedidoAtenderBean()
   {
   }
   
   public static FarmPedidoAtenderBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmPedidoAtenderBean farmPedidoAtenderBean
              = (FarmPedidoAtenderBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmPedidoAtenderBean");
      
      return farmPedidoAtenderBean;
   }
   
   public List<FarmLoteProdutoHasLocalArmazenamento> getLotesProdutoDisponiveisNoLocal(FarmProduto produto)
   {
      return loteHaslocalFacade.findProdutosDisponiveisNoLocal(pedido.getFkLocalDestinoPedido(), produto);
   }
   
   public FarmMovimentoHasProduto getInstanciaMovimentoHasProduto()
   {
      FarmMovimentoHasProduto FarmMovimentoHasProduto = new FarmMovimentoHasProduto();
      FarmMovimentoHasProduto.setFkIdMovimento(movimento);
      FarmMovimentoHasProduto.setFkIdLoteProduto(new FarmLoteProduto());
      FarmMovimentoHasProduto.setFkIdTipoQuantidade(new FarmTipoQuantidade(1));
      FarmMovimentoHasProduto.setQuantidadeMovimentada(1);
      return FarmMovimentoHasProduto;
   }
   
   public FarmPedido getInstanciaPedido()
   {
      FarmPedido FarmPedido = new FarmPedido();
      FarmPedido.setFkIdFuncionarioSolicitou(new RhFuncionario());
      FarmPedido.setFkLocalDestinoPedido(new FarmLocalArmazenamento());
      FarmPedido.setFkLocalOrigemPedido(new FarmLocalArmazenamento());
      FarmPedido.setDataHoraPedido(new Date());
      return FarmPedido;
   }
   
   public void removerProduto()
   {
      for (FarmMovimentoHasProduto aux : lotesProdutoMovimento)
      {
         if (aux.getFkIdLoteProduto().getPkIdLoteProduto() == lote.getPkIdLoteProduto())
         {
            lotesProdutoMovimento.remove(aux);
            break;
         }
      }
   }
   
   public void adicionarProduto()
   {
      movimentoHasProduto = getInstanciaMovimentoHasProduto();
      movimentoHasProduto.setFkIdLoteProduto(lote);
      movimentoHasProduto.setFkIdMovimento(movimento);
      boolean existe = false;
      for (FarmMovimentoHasProduto aux : lotesProdutoMovimento)
      {
         if (aux.getFkIdLoteProduto().getPkIdLoteProduto() == lote.getPkIdLoteProduto())
         {
            existe = true;
            break;
         }
      }
      if (existe)
         Mensagem.warnMsg("Este item já foi submetido");
      else
         lotesProdutoMovimento.add(movimentoHasProduto);
   }
   
   public void limparCampos()
   {
      lotesProdutoMovimento = null;
      itensPedido = new ArrayList<>();
      primeiraVez = false;
      pedido = getInstanciaPedido();
   }
   
   public boolean validarQuantidades()
   {
      
      for (FarmPedidoHasProduto aux : itensPedido)
      {
         
         int qtdProdutoDisponivel = getQtdTotalDisponivelStock(aux.getFkIdProduto());
         
         int qtdLoteConfirmado = 0;
         for (FarmMovimentoHasProduto aux2 : lotesProdutoMovimento)
         {
            if (aux2.getQuantidadeMovimentada() < 1)
            {
               Mensagem.warnMsg("A quantidade a ser entregue para " + aux.getFkIdProduto().getDescricao()
                       + " " + aux.getFkIdProduto().getDosagem() + " "
                       + aux.getFkIdProduto().getFkIdUnidadeMedida().getAbreviatura()
                       + " não é valida. Verifique.");
               return false;
            }
            if (aux2.getFkIdLoteProduto().getFkIdProduto().getPkIdProduto() == aux.getFkIdProduto().getPkIdProduto())
            {
               qtdLoteConfirmado += aux2.getQuantidadeMovimentada();
            }
         }
         
         if (qtdProdutoDisponivel < qtdLoteConfirmado)
         {
            Mensagem.warnMsg("A quantidade a ser entregue para " + aux.getFkIdProduto().getDescricao()
                    + " " + aux.getFkIdProduto().getDosagem() + " "
                    + aux.getFkIdProduto().getFkIdUnidadeMedida().getAbreviatura()
                    + " excede a quantidade disponível no stock. Verifique.");
            return false;
         }
         
      }
      return true;
   }
   
   public int getQtdTotalDisponivelStock(FarmProduto item)
   {
      List<FarmLoteProdutoHasLocalArmazenamento> lotes = getLotesProdutoDisponiveisNoLocal(item);
      int qtdTotal = 0;
      for (FarmLoteProdutoHasLocalArmazenamento aux : lotes)
      {
         qtdTotal += aux.getQuantidadeStock();
      }
      
      return qtdTotal;
   }

   //public boolean jaConfirmado(FarmLoteProduto item)
   public boolean jaConfirmado(FarmProduto item)
   {
      for (FarmMovimentoHasProduto aux : lotesProdutoMovimento)
      {
         if (aux.getFkIdLoteProduto().getFkIdProduto()/*.getPkIdLoteProduto()*/ == item/*.getPkIdLoteProduto()*/)
         {
            return true;
         }
      }
      return false;
   }
   
   public void inserNaoConfirmados()
   {
      for (FarmPedidoHasProduto aux : itensPedido)
      {
         for (FarmLoteProdutoHasLocalArmazenamento aux2 : getLotesProdutoDisponiveisNoLocal(aux.getFkIdProduto()))
         {
            if (!jaConfirmado(aux2.getFkIdLoteProduto().getFkIdProduto()))
            {
               movimentoHasProduto = new FarmMovimentoHasProduto();
               movimentoHasProduto.setFkIdLoteProduto(aux2.getFkIdLoteProduto());
               movimentoHasProduto.setFkIdMovimento(movimento);
               movimentoHasProduto.setQuantidadeMovimentada(0);
               
               lotesProdutoMovimento.add(movimentoHasProduto);
            }
         }
      }
   }
   
   public boolean pedidoJaFoiAtendido()
   {
      if(FarmMovimentoListarBean.getInstanciaBean().getMovimentoByPedido(pedido) != null)
         return true;
//      for (FarmMovimento mov : movimentoFacade.findAll())
//      {
//         if (mov.getFkIdPedido().getPkIdPedido() == pedido.getPkIdPedido())
//            return true;
//      }
      return false;
   }
   
   public String criar()
   {
      if (lotesProdutoMovimento.isEmpty())
         Mensagem.warnMsg("Este pedido já foi atendido.");
      else if (lotesProdutoMovimento.isEmpty())
         Mensagem.warnMsg("Não seleccionou nenhum item. Verifique.");
      else if (validarQuantidades())
      {
         try
         {
            userTransaction.begin();
            
            movimento = new FarmMovimento();
            movimento.setDataMovimento(new Date());
            System.out.println("movimento a criar:");
            System.out.println("pedido:  " + pedido);
            movimento.setFkIdPedido(pedido);
            System.out.println("getDataMovimento :" + movimento.getDataMovimento());
            movimentoFacade.create(movimento);
            
            for (FarmMovimentoHasProduto aux : lotesProdutoMovimento)
            {
               aux.setFkIdMovimento(movimento);
               
               movimentoHasProdutoFacade.create(aux);
               
               FarmLoteProdutoHasLocalArmazenamento itemNoLocal_OrigemPedido = loteHaslocalFacade.findLoteProdutoNoLocal(pedido.getFkLocalOrigemPedido(), aux.getFkIdLoteProduto());
               if (itemNoLocal_OrigemPedido != null)
               {
                  itemNoLocal_OrigemPedido.setQuantidadeStock(itemNoLocal_OrigemPedido.getQuantidadeStock() + aux.getQuantidadeMovimentada());
                  loteHaslocalFacade.edit(itemNoLocal_OrigemPedido);
               }
               else
               {
                  itemNoLocal_OrigemPedido = new FarmLoteProdutoHasLocalArmazenamento();
                  itemNoLocal_OrigemPedido.setFkIdLocalArmazenamento(pedido.getFkLocalOrigemPedido());
                  itemNoLocal_OrigemPedido.setFkIdLoteProduto(aux.getFkIdLoteProduto());
                  itemNoLocal_OrigemPedido.setQuantidadeStock(aux.getQuantidadeMovimentada());
                  loteHaslocalFacade.create(itemNoLocal_OrigemPedido);
               }
               
               FarmLoteProdutoHasLocalArmazenamento itemNoLocal_DestinoPedido = loteHaslocalFacade.findLoteProdutoNoLocal(pedido.getFkLocalDestinoPedido(), aux.getFkIdLoteProduto());
               itemNoLocal_DestinoPedido.setQuantidadeStock(itemNoLocal_DestinoPedido.getQuantidadeStock() - aux.getQuantidadeMovimentada());
               
               loteHaslocalFacade.edit(itemNoLocal_DestinoPedido);
               
               FarmFichaStock fichaParaOrigem = new FarmFichaStock();
               
               fichaParaOrigem.setDataMovimento(new Date());
               fichaParaOrigem.setOrigemOuDestino(pedido.getFkLocalDestinoPedido().getDescricao());
               fichaParaOrigem.setFkIdLoteProduto(itemNoLocal_OrigemPedido.getFkIdLoteProduto());
               fichaParaOrigem.setFkIdLocalArmazenamento(pedido.getFkLocalOrigemPedido());
               fichaParaOrigem.setEntradas(aux.getQuantidadeMovimentada());
               fichaParaOrigem.setSaidas(0);
               fichaParaOrigem.setQuantidadeRestante(itemNoLocal_OrigemPedido.getQuantidadeStock());
               fichaParaOrigem.setFkIdFuncionario(pedido.getFkIdFuncionarioSolicitou());
               fichaStockFacade.create(fichaParaOrigem);
               
               FarmFichaStock fichaParaDestino = new FarmFichaStock();
               
               fichaParaDestino.setDataMovimento(new Date());
               fichaParaDestino.setOrigemOuDestino(pedido.getFkLocalOrigemPedido().getDescricao());
               fichaParaDestino.setFkIdLoteProduto(itemNoLocal_DestinoPedido.getFkIdLoteProduto());
               fichaParaDestino.setFkIdLocalArmazenamento(pedido.getFkLocalDestinoPedido());
               fichaParaDestino.setSaidas(aux.getQuantidadeMovimentada());
               fichaParaDestino.setEntradas(0);
               fichaParaDestino.setQuantidadeRestante(itemNoLocal_DestinoPedido.getQuantidadeStock());
               fichaParaDestino.setFkIdFuncionario(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
               fichaStockFacade.create(fichaParaDestino);
               
            }
            pedido.setFkIdEstadoPedido(new FarmEstadoPedido(Constantes.FARM_ESTADO_PEDIDO_CONFIRMADO));
            pedido.setFkIdFuncionarioAtendeu(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
            pedidoFacade.edit(pedido);
//            FarmNotificacaoBean.getInstanciaBean().pesquisarNotificacoes();
//            FarmGraficosBean.getInstanciaBean().actualizarGraficos();
            Mensagem.sucessoMsg("Pedido confirmado com sucesso!");
            limparCampos();
            userTransaction.commit();
         }
         catch (Exception e)
         {
            Mensagem.warnMsg("Ocorreu um erro ao confirmar o pedido. Tente novamente."+e.toString());
            try
            {
               userTransaction.rollback();
//               Mensagem.erroMsg(e.toString());
            }
            catch (Exception ex)
            {
               Mensagem.erroMsg("Rollback: " + ex.toString());
            }
         }         
      }
      return null;
   }
   
   public String criarNovoTipoQuantidade()
   {
      try
      {
         userTransaction.begin();
         tipoQuantidadeFacade.create(getTipoQuantidade());
         userTransaction.commit();
         Mensagem.sucessoMsg("Tipo de Quantidade cadastrada com sucesso!");
      }
      catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
      {
         try
         {
            userTransaction.rollback();
            Mensagem.erroMsg(e.toString());
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            Mensagem.erroMsg("Ocorreu um erro ao processar a quantidade. Tente novamente mais tarde.");
         }
      }
      
      setTipoQuantidade(new FarmTipoQuantidade());
      
      return null;
   }

   /**
    * @return the pedido
    */
   public FarmPedido getPedido()
   {
      if (pedido == null)
         pedido = getInstanciaPedido();
      return pedido;
   }

   /**
    * @param pedido the pedido to set
    */
   public void setPedido(FarmPedido pedido)
   {
      primeiraVez = true;
      lotesProdutoMovimento = new ArrayList<>();
      this.pedido = pedido;
   }

   /**
    * @return the movimento
    */
   public FarmMovimento getMovimento()
   {
      return movimento;
   }

   /**
    * @param movimento the movimento to set
    */
   public void setMovimento(FarmMovimento movimento)
   {
      this.movimento = movimento;
   }

   /**
    * @return the movimentoHasProduto
    */
   public FarmMovimentoHasProduto getMovimentoHasProduto()
   {
      if (movimento == null)
         movimento = FarmMovimentoListarBean.getInstanciaBean().getInstaciaMovimento();
      return movimentoHasProduto;
   }

   /**
    * @param movimentoHasProduto the movimentoHasProduto to set
    */
   public void setMovimentoHasProduto(FarmMovimentoHasProduto movimentoHasProduto)
   {
      this.movimentoHasProduto = movimentoHasProduto;
   }

   /**
    * @return the itensPedido
    */
   public List<FarmPedidoHasProduto> getItensPedido()
   {
      if (primeiraVez)
         setItensPedido(pedidoHasProdutoFacade.findProdutoPedido(pedido));
      
      else
         itensPedido = new ArrayList<>();
      
      return itensPedido;
   }

   /**
    * @param itensPedido the itensPedido to set
    */
   public void setItensPedido(List<FarmPedidoHasProduto> itensPedido)
   {
      this.itensPedido = itensPedido;
   }

   /**
    * @return the lotesProdutoMovimento
    */
   public ArrayList<FarmMovimentoHasProduto> getLotesProdutoMovimento()
   {
      if (lotesProdutoMovimento == null)
         lotesProdutoMovimento = new ArrayList<>();
      
      return lotesProdutoMovimento;
   }

   /**
    * @param lotesProdutoMovimento the lotesProdutoMovimento to set
    */
   public void setLotesProdutoMovimento(ArrayList<FarmMovimentoHasProduto> lotesProdutoMovimento)
   {
      this.lotesProdutoMovimento = lotesProdutoMovimento;
   }

   /**
    * @return the lote
    */
   public FarmLoteProduto getLote()
   {
      return lote;
   }

   /**
    * @param lote the lote to set
    */
   public void setLote(FarmLoteProduto lote)
   {
      this.lote = lote;
   }

   /**
    * @return the tipoQuantidade
    */
   public FarmTipoQuantidade getTipoQuantidade()
   {
      if (tipoQuantidade == null)
         tipoQuantidade = new FarmTipoQuantidade();
      return tipoQuantidade;
   }

   /**
    * @param tipoQuantidade the tipoQuantidade to set
    */
   public void setTipoQuantidade(FarmTipoQuantidade tipoQuantidade)
   {
      this.tipoQuantidade = tipoQuantidade;
   }
   
}
