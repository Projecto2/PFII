/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.movimentos;

import entidade.FarmEstadoPedido;
import entidade.FarmLocalArmazenamento;
import entidade.FarmMovimento;
import entidade.FarmPedido;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmMovimentoFacade;
import sessao.FarmPedidoFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmMovimentoListarBean implements Serializable
{
   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmPedidoFacade pedidoFacade;
   @EJB
   private FarmMovimentoFacade movimentoFacade;
   @EJB
   private FarmLocalArmazenamentoFacade locaisArmazenamentoFacade;
   
   private FarmPedido pedido;
   private List<FarmPedido> listaPedidosPesquisados, listaPedidosCanceladosPesquisados;
   private Date dataInicio, dataFim;
   
   /**
    * Creates a new instance of FarmPedidoPesquisaBean
    */
   public FarmMovimentoListarBean()
   {
      
   }
   
   public static FarmMovimentoListarBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmMovimentoListarBean farmMovimentoListarBean
              = (FarmMovimentoListarBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmMovimentoListarBean");

      return farmMovimentoListarBean;
   }
   
   public FarmMovimento getInstaciaMovimento()
   {
      FarmMovimento farmMovimento = new FarmMovimento();
      farmMovimento.setFkIdPedido(pedido);
      farmMovimento.setDataMovimento(new Date());
      
      return farmMovimento;
   }
   
   public void inicializarParametros(String localOrigemString, String localDestinoString)
   {
      FarmLocalArmazenamento local = new FarmLocalArmazenamento();
      local.setDescricao(localOrigemString);
      if (!locaisArmazenamentoFacade.findLocalArmazenamento(local).isEmpty())
         pedido.setFkLocalOrigemPedido(locaisArmazenamentoFacade.findLocalArmazenamento(local).get(0));
      
      local.setDescricao(localDestinoString);
      if (!locaisArmazenamentoFacade.findLocalArmazenamento(local).isEmpty())
         pedido.setFkLocalDestinoPedido(locaisArmazenamentoFacade.findLocalArmazenamento(local).get(0));      
   }
   
   public void pesquisarPedidos()
   {
      
      setListaPedidosPesquisados(pedidoFacade.findPedido(getPedido(), dataInicio, dataFim));
      if (getListaPedidosPesquisados().isEmpty())
         Mensagem.warnMsg("Nenhum pedido encontrado na pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getListaPedidosPesquisados().size() + " registo(s) retornado(s).");
   }
      
   public void definirLocalOrigemPedido(String localString)
   {
      getPedido();
      System.out.println("local a definir: "+localString);
      FarmLocalArmazenamento local = getInstanciaLocal();
      local.setDescricao(localString);
      
      if (!locaisArmazenamentoFacade.findLocalArmazenamento(local).isEmpty())
         pedido.setFkLocalOrigemPedido(locaisArmazenamentoFacade.findLocalArmazenamento(local).get(0));
      
      System.out.println("origem do pedido definida como: "+pedido.getFkLocalOrigemPedido());
   }
   
   public FarmLocalArmazenamento getInstanciaLocal()
   {
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdInstituicao(new GrlInstituicao());
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      return aux;
   }
   
   public FarmPedido getInstanciaPedido()
   {
      pedido = new FarmPedido();
      
      pedido.setFkIdFuncionarioSolicitou(new RhFuncionario());
      pedido.setFkIdFuncionarioAtendeu(new RhFuncionario());
      pedido.setFkLocalDestinoPedido(new FarmLocalArmazenamento());
      pedido.setFkLocalOrigemPedido(new FarmLocalArmazenamento());
      pedido.setFkIdEstadoPedido(new FarmEstadoPedido());
      return pedido;
   }

   public void cancelar()
   {
      Mensagem.sucessoMsg("");
      try
      {
          pedido.setFkIdEstadoPedido(new FarmEstadoPedido(Constantes.FARM_ESTADO_PEDIDO_CANCELADO));
          System.out.println("motivo "+pedido.getMotivoCancelamento());
          userTransaction.begin();
          pedidoFacade.edit(pedido);
          userTransaction.commit();
          Mensagem.sucessoMsg("Pedido Cancelado com sucesso!");
          
          listaPedidosPesquisados = new ArrayList<>();
          pedido.setFkIdEstadoPedido(new FarmEstadoPedido());
          pesquisarPedidos();
      }
      catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
      {
         Mensagem.warnMsg("Ocorreu um erro ao alterar o estado o pedido. Tente novamente.");
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
   
   public String irParaVisualizarItensPedido()
   {
      return "visualizarItensPedidoFarm.xhtml?faces-redirect=true";
   }
   
   /**
    * @return the pedido
    */
   public FarmPedido getPedido()
   {
      if(pedido == null)
         pedido = getInstanciaPedido();
      return pedido;
   }

   /**
    * @param pedido the pedido to set
    */
   public void setPedido(FarmPedido pedido)
   {
      this.pedido = pedido;
   }

   /**
    * @return the listaPedidosPesquisados
    */
   public List<FarmPedido> getListaPedidosPesquisados()
   {
      return listaPedidosPesquisados;
   }

   /**
    * @param listaPedidosPesquisados the listaPedidosPesquisados to set
    */
   public void setListaPedidosPesquisados(List<FarmPedido> listaPedidosPesquisados)
   {
      this.listaPedidosPesquisados = listaPedidosPesquisados;
   }

   /**
    * @return the dataInicio
    */
   public Date getDataInicio()
   {
      return dataInicio;
   }

   /**
    * @param dataInicio the dataInicio to set
    */
   public void setDataInicio(Date dataInicio)
   {
      this.dataInicio = dataInicio;
   }

   /**
    * @return the dataFim
    */
   public Date getDataFim()
   {
      dataFim = new Date();
      dataFim = getEndOfDay(dataFim);
      return dataFim;
   }

   public void setDataFim(Date dataFim)
   {
      dataFim = getEndOfDay(dataFim);
      this.dataFim = dataFim;
   }

   public Date getEndOfDay(Date date)
   {
      if (date != null)
      {
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.set(Calendar.HOUR_OF_DAY, 23);
         calendar.set(Calendar.MINUTE, 59);
         calendar.set(Calendar.SECOND, 59);
         calendar.set(Calendar.MILLISECOND, 999);
         return calendar.getTime();
      }
      return new Date();
   }

      public FarmMovimento getMovimentoByPedido(FarmPedido pedido)
      {
         FarmMovimento movimento = getInstaciaMovimento();
         movimento.setFkIdPedido(pedido);
         return movimentoFacade.findMovimento(movimento, null, null).get(0);
      }
}
