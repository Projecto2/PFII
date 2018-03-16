/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.movimentos;

import entidade.FarmLocalArmazenamento;
import entidade.FarmMovimento;
import entidade.FarmPedido;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.FarmMovimentoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmPedidosRealizadosVisualizarBean implements Serializable
{
   @EJB
   private FarmMovimentoFacade movimentoFacade;
   
   private FarmMovimento movimentoRealizadoPesquisa;
   private List<FarmMovimento> listaPedidosPesquisados;
   private Date dataInicio, dataFim;

   /**
    * Creates a new instance of FarmMovimentosVisualizarBean
    */
   public FarmPedidosRealizadosVisualizarBean()
   {
      movimentoRealizadoPesquisa = getInstanciaMovimentoRealizado();
   }
   
   public FarmMovimento getInstanciaMovimentoRealizado()
   {
      setMovimentoRealizadoPesquisa(new FarmMovimento());
      
      FarmPedido pedido = new FarmPedido();
      
      pedido.setFkIdFuncionarioSolicitou(new RhFuncionario());
      pedido.setFkIdFuncionarioAtendeu(new RhFuncionario());
      pedido.setFkLocalDestinoPedido(new FarmLocalArmazenamento());
      pedido.setFkLocalOrigemPedido(new FarmLocalArmazenamento());
      getMovimentoRealizadoPesquisa().setFkIdPedido(pedido);
      
      return getMovimentoRealizadoPesquisa();
   }
   
   public void pesquisarPedidosRealizados()
   {
      
      setListaPedidosPesquisados(movimentoFacade.findMovimento(movimentoRealizadoPesquisa, dataInicio, dataFim));
      if (getListaPedidosPesquisados().isEmpty())
         Mensagem.warnMsg("Nenhum pedido realizado encontrado na pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getListaPedidosPesquisados().size() + " registo(s) retornado(s).");
   }
   
   public String irParaVisualizarItensPedido()
   {
      return "pedidoRealizadoVisualizarItensFarm.xhtml?faces-redirect=true";
   }
   /**
    * @return the movimentoRealizadoPesquisa
    */
   public FarmMovimento getMovimentoRealizadoPesquisa()
   {
      return movimentoRealizadoPesquisa;
   }

   /**
    * @param movimentoRealizadoPesquisa the movimentoRealizadoPesquisa to set
    */
   public void setMovimentoRealizadoPesquisa(FarmMovimento movimentoRealizadoPesquisa)
   {
      this.movimentoRealizadoPesquisa = movimentoRealizadoPesquisa;
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
   
   /**
    * @return the listaPedidosPendentesPesquisados3
    */
   public List<FarmMovimento> getListaPedidosPesquisados()
   {
      return listaPedidosPesquisados;
   }

   /**
    * @param listaPedidosPesquisados the listaPedidosPendentesPesquisados3 to set
    */
   public void setListaPedidosPesquisados(List<FarmMovimento> listaPedidosPesquisados)
   {
      this.listaPedidosPesquisados = listaPedidosPesquisados;
   }
}
