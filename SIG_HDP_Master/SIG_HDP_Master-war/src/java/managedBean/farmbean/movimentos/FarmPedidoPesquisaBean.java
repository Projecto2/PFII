/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.movimentos;

import entidade.FarmEstadoPedido;
import entidade.FarmLocalArmazenamento;
import entidade.FarmPedido;
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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.FarmPedidoFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmPedidoPesquisaBean implements Serializable
{
   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmPedidoFacade pedidoFacade;
   
   private FarmPedido pedidoPendentePesquisa, pedidoCanceladoPesquisa;
   private List<FarmPedido> listaPedidosPendentesPesquisados, listaPedidosCanceladosPesquisados;
   private Date dataInicio, dataFim;
   
   /**
    * Creates a new instance of FarmPedidoPesquisaBean
    */
   public FarmPedidoPesquisaBean()
   {
      pedidoPendentePesquisa = getInstanciaPedidoPendente();
      pedidoCanceladoPesquisa = getInstanciaPedidoCancelado();
   }
   
   public void pesquisarPedidosPendentes()
   {
      
      setListaPedidosPendentesPesquisados(pedidoFacade.findPedido(pedidoPendentePesquisa, dataInicio, dataFim));
      if (getListaPedidosPendentesPesquisados().isEmpty())
         Mensagem.warnMsg("Nenhum pedido pendente encontrado na pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getListaPedidosPendentesPesquisados().size() + " pedido(s) pendente(s) retornado(s).");
   }
   
   public void pesquisarPedidosCancelados()
   {
      
      setListaPedidosCanceladosPesquisados(pedidoFacade.findPedido(pedidoCanceladoPesquisa, dataInicio, dataFim));
      if (getListaPedidosCanceladosPesquisados().isEmpty())
         Mensagem.warnMsg("Nenhum pedido cancelado encontrado na pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getListaPedidosCanceladosPesquisados().size() + " pedido(s) cancelado(s) retornado(s).");
   }
   
   
   public FarmPedido getInstanciaPedidoCancelado()
   {
      setPedidoCanceladoPesquisa(new FarmPedido());
      
      getPedidoCanceladoPesquisa().setFkIdFuncionarioSolicitou(new RhFuncionario());
      getPedidoCanceladoPesquisa().setFkIdFuncionarioAtendeu(new RhFuncionario());
      getPedidoCanceladoPesquisa().setFkLocalDestinoPedido(new FarmLocalArmazenamento());
      getPedidoCanceladoPesquisa().setFkLocalOrigemPedido(new FarmLocalArmazenamento());
      getPedidoCanceladoPesquisa().setFkIdEstadoPedido(new FarmEstadoPedido(Constantes.FARM_ESTADO_PEDIDO_CANCELADO));
      return getPedidoCanceladoPesquisa();
   }
   
   public FarmPedido getInstanciaPedidoPendente()
   {
      setPedidoPendentePesquisa(new FarmPedido());
      
      getPedidoPendentePesquisa().setFkIdFuncionarioSolicitou(new RhFuncionario());
      getPedidoPendentePesquisa().setFkIdFuncionarioAtendeu(new RhFuncionario());
      getPedidoPendentePesquisa().setFkLocalDestinoPedido(new FarmLocalArmazenamento());
      getPedidoPendentePesquisa().setFkLocalOrigemPedido(new FarmLocalArmazenamento());
      getPedidoPendentePesquisa().setFkIdEstadoPedido(new FarmEstadoPedido(Constantes.FARM_ESTADO_PEDIDO_PENDENTE));
      return getPedidoPendentePesquisa();
   }

   public void cancelar()
   {
      Mensagem.sucessoMsg("");
      try
      {
          pedidoPendentePesquisa.setFkIdEstadoPedido(new FarmEstadoPedido(Constantes.FARM_ESTADO_PEDIDO_CANCELADO));
          System.out.println("motivo "+pedidoPendentePesquisa.getMotivoCancelamento());
          userTransaction.begin();
          pedidoFacade.edit(pedidoPendentePesquisa);
          userTransaction.commit();
          Mensagem.sucessoMsg("Pedido Cancelado com sucesso!");
          
          listaPedidosPendentesPesquisados = new ArrayList<>();
          pedidoPendentePesquisa = getInstanciaPedidoPendente();
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
    * @return the pedidoPendentePesquisa
    */
   public FarmPedido getPedidoPendentePesquisa()
   {
      return pedidoPendentePesquisa;
   }

   /**
    * @param pedidoPendentePesquisa the pedidoPendentePesquisa to set
    */
   public void setPedidoPendentePesquisa(FarmPedido pedidoPendentePesquisa)
   {
      this.pedidoPendentePesquisa = pedidoPendentePesquisa;
   }

   /**
    * @return the listaPedidosPendentesPesquisados
    */
   public List<FarmPedido> getListaPedidosPendentesPesquisados()
   {
      return listaPedidosPendentesPesquisados;
   }

   /**
    * @param listaPedidosPendentesPesquisados the listaPedidosPendentesPesquisados to set
    */
   public void setListaPedidosPendentesPesquisados(List<FarmPedido> listaPedidosPendentesPesquisados)
   {
      this.listaPedidosPendentesPesquisados = listaPedidosPendentesPesquisados;
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

   /**
    * @return the pedidoCanceladoPesquisa
    */
   public FarmPedido getPedidoCanceladoPesquisa()
   {
      return pedidoCanceladoPesquisa;
   }

   /**
    * @param pedidoCanceladoPesquisa the pedidoCanceladoPesquisa to set
    */
   public void setPedidoCanceladoPesquisa(FarmPedido pedidoCanceladoPesquisa)
   {
      this.pedidoCanceladoPesquisa = pedidoCanceladoPesquisa;
   }

   /**
    * @return the listaPedidosCanceladosPesquisados
    */
   public List<FarmPedido> getListaPedidosCanceladosPesquisados()
   {
      return listaPedidosCanceladosPesquisados;
   }

   /**
    * @param listaPedidosCanceladosPesquisados the listaPedidosCanceladosPesquisados to set
    */
   public void setListaPedidosCanceladosPesquisados(List<FarmPedido> listaPedidosCanceladosPesquisados)
   {
      this.listaPedidosCanceladosPesquisados = listaPedidosCanceladosPesquisados;
   }

}
