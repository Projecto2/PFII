/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.movimentos;

import entidade.FarmEstadoPedido;
import entidade.FarmLocalArmazenamento;
import entidade.FarmPedido;
import entidade.FarmPedidoHasProduto;
import entidade.FarmProduto;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.farmbean.configuracoes.FarmConfiguracoesBean;
import managedBean.farmbean.produto.FarmProdutoNovoBean;
import managedBean.segbean.SegLoginBean;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmLoteProdutoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.FarmPedidoFacade;
import sessao.FarmPedidoHasProdutoFacade;
import sessao.FarmProdutoFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmPedidoNovoBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;

   @EJB
   FarmProdutoFacade produtoFacade;
   @EJB
   private FarmLocalArmazenamentoFacade locaisArmazenamentoFacade;
   @EJB
   private FarmPedidoFacade pedidoFacade;
   @EJB
   private FarmPedidoHasProdutoFacade pedidoHasProdutoFacade;
   @EJB
   private FarmLoteProdutoFacade loteFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteNoLocalFacade;

   private ArrayList<SelectItem> listaLocaisOrigem;
   private ArrayList<SelectItem> listaLocaisDestino;

   private List<FarmProduto> produtosPesquisados;
   private ArrayList<FarmPedidoHasProduto> listaSeleccionados;
   private ArrayList<FarmPedidoHasProduto> produtosSemStock;

   private FarmProduto produtoPesquisa;
   private FarmProduto produtoAdicionar;
   private FarmPedido pedido;

   /**
    * Creates a new instance of PedidoNovoBean
    */
   public FarmPedidoNovoBean()
   {
   }

   public FarmPedido getInstanciaPedido()
   {
      setPedido(new FarmPedido());
      pedido.setFkLocalDestinoPedido(new FarmLocalArmazenamento(FarmConfiguracoesBean.DESTINO_PEDIDO_MODULO_FARMACIA));
      pedido.setFkLocalOrigemPedido(new FarmLocalArmazenamento(FarmConfiguracoesBean.ORIGEM_PEDIDO_MODULO_FARMACIA));
      pedido.setFkIdEstadoPedido(new FarmEstadoPedido(Constantes.FARM_ESTADO_PEDIDO_PENDENTE));
      pedido.setDataHoraPedido(new Date());
      pedido.setFkIdFuncionarioSolicitou(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
      return pedido;
   }

   public void pesquisarProdutos()
   {
      setProdutosPesquisados(produtoFacade.findProduto(getProdutoPesquisa()));

      if (getProdutosPesquisados().isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getProdutosPesquisados().size() + " registo(s) retornado(s).");

      eliminarDuplicados();
   }

   public void adicionarProduto()
   {
      FarmPedidoHasProduto itemAux = new FarmPedidoHasProduto();
      itemAux.setFkIdPedido(pedido);
      itemAux.setFkIdProduto(getProdutoAdicionar());
      itemAux.setQuantidade(1);
      listaSeleccionados.add(itemAux);

      pesquisarProdutos();
   }

   public void removerProduto()
   {
      FarmPedidoHasProduto itemAux = new FarmPedidoHasProduto();
      itemAux.setFkIdPedido(pedido);
      itemAux.setFkIdProduto(getProdutoAdicionar());
      itemAux.setQuantidade(1);
      listaSeleccionados.remove(itemAux);

      pesquisarProdutos();
   }

   public void eliminarDuplicados()
   {
      for (FarmPedidoHasProduto pedido_has_produto_aux : getListaSeleccionados())
      {
         if (getProdutosPesquisados().contains(pedido_has_produto_aux.getFkIdProduto()))
            getProdutosPesquisados().remove(pedido_has_produto_aux.getFkIdProduto());
      }
   }

//   public void avaliarStock()
//   {
//      produtosSemStock = listaSeleccionados;
//      System.out.println("avaliando produtos sem stock");
//      for (FarmPedidoHasProduto aux : produtosSemStock)
//      {
//         System.out.println("obtendo lotes do produto: "+aux.getFkIdProduto());
//         List<FarmLoteProduto> lotesAux = loteFacade.findLotePorProduto(new FarmLoteProduto(), aux.getFkIdProduto());
//         System.out.println("lotes encontrados: "+lotesAux);
//         
//         System.out.println("verificando se este produto existe na(o): "+localOrigemPedido);
//         List<FarmLoteProdutoHasLocalArmazenamento> lotesNoLocalAux = loteNoLocalFacade.findProdutoNoLocal(localOrigemPedido, aux.getFkIdProduto());
//         if(lotesNoLocalAux.isEmpty())
//            System.out.println("este produto ainda na existe la");
//         else
//         {
//            System.out.println("este produto ja existe la.vou remover da lista");
//            produtosSemStock.remove(aux);
//         }
//      }      
//   }
   public void limparCampos()
   {
      produtoPesquisa = FarmProdutoNovoBean.getInstancia();
      produtoAdicionar = FarmProdutoNovoBean.getInstancia();
      setPedido(getInstanciaPedido());
      listaSeleccionados = new ArrayList<>();
      produtosSemStock = new ArrayList<>();
   }

   public void criar()
   {
      if (pedido.getFkLocalOrigemPedido().getPkIdLocalArmazenamento() == pedido.getFkLocalDestinoPedido().getPkIdLocalArmazenamento())
         Mensagem.warnMsg("O Destino do pedido não pode ser o mesmo que a Origem. Verifique.");
      else if (listaSeleccionados.isEmpty())
         Mensagem.warnMsg("Não seleccionou nenhum Produto para o pedido. Verifique.");
      else
      {
         try
         {
            userTransaction.begin();
            for (FarmPedidoHasProduto pedido_has_produto_aux : getListaSeleccionados())
            {
               if (pedido_has_produto_aux.getQuantidade() > 0)
                  pedidoHasProdutoFacade.create(pedido_has_produto_aux);
               else
               {
                  Mensagem.warnMsg("A quantidade requisitada para " + pedido_has_produto_aux.getFkIdProduto().getDescricao() + " é inválida. Verifique.");
                  throw new IllegalStateException();
               }

            }
            pedido.setFkIdFuncionarioSolicitou(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
            pedidoFacade.create(pedido);

            userTransaction.commit();

            Mensagem.sucessoMsg("Pedido enviado com sucesso!");
            limparCampos();
         }
         catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
         {
            Mensagem.warnMsg("Ocorreu um erro ao processar o pedido. Tente novamente.");
            try
            {
               userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
               System.out.println(ex.toString());
            }
         }
      }
   }

   public ArrayList<SelectItem> getLocaisArmazenamentoOrigemPedido()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_FARMACIA));
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_AREA_INTERNA));
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_AREA_EXTERNA));
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_ARMAZEM));
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }
      return itens;
   }

   public ArrayList<SelectItem> getLocaisArmazenamentoDestinoPedido()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_FARMACIA));
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_AREA_INTERNA));
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_ARMAZEM));
      for (FarmLocalArmazenamento e : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }
      return itens;
   }
   
   public void definirLocalOrigemPedido(String localString)
   {
      getPedido();
      System.out.println("local a definir: " + localString);
      FarmLocalArmazenamento local = getInstanciaLocal();
      local.setDescricao(localString);

      if (!locaisArmazenamentoFacade.findLocalArmazenamento(local).isEmpty())
         pedido.setFkLocalOrigemPedido(locaisArmazenamentoFacade.findLocalArmazenamento(local).get(0));

      System.out.println("origem do pedido definida como: " + pedido.getFkLocalOrigemPedido());
   }

   public FarmLocalArmazenamento getInstanciaLocal()
   {
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento(FarmConfiguracoesBean.DESTINO_PEDIDO_MODULO_FARMACIA);
      aux.setFkIdInstituicao(new GrlInstituicao());
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      return aux;
   }

   public void eliminarDuplicadosDestino()
   {
      System.out.println("eliminando " + listaLocaisOrigem.get(0).getLabel() + "no destino");
      for (SelectItem aux : listaLocaisDestino)
      {
         System.out.println("analisando " + aux);
         if (listaLocaisOrigem.get(0).getValue() == aux.getValue())
            listaLocaisDestino.remove(aux);
      }
   }

   public void eliminarDuplicadosOrigem()
   {
      System.out.println("eliminando " + listaLocaisDestino.get(0) + "na origem");
      for (SelectItem aux : listaLocaisOrigem)
      {
         System.out.println("analisando " + aux);
         if (listaLocaisDestino.get(0).getValue() == aux.getValue())
            listaLocaisOrigem.remove(aux);
      }
   }

   /**
    * @return the produtosPesquisados
    */
   public List<FarmProduto> getProdutosPesquisados()
   {
      if (produtosPesquisados == null)
         produtosPesquisados = new ArrayList<>();
      return produtosPesquisados;
   }

   /**
    * @return the produtoPesquisa
    */
   public FarmProduto getProdutoPesquisa()
   {
      if (produtoPesquisa == null)
         produtoPesquisa = FarmProdutoNovoBean.getInstancia();
      return produtoPesquisa;
   }

   /**
    * @param produtoPesquisa the produtoPesquisa to set
    */
   public void setProdutoPesquisa(FarmProduto produtoPesquisa)
   {
      this.produtoPesquisa = produtoPesquisa;
   }

   /**
    * @param produtosPesquisados the produtosPesquisados to set
    */
   public void setProdutosPesquisados(List<FarmProduto> produtosPesquisados)
   {
      this.produtosPesquisados = produtosPesquisados;
   }

   /**
    * @return the listaSeleccionados
    */
   public List<FarmPedidoHasProduto> getListaSeleccionados()
   {
      if (listaSeleccionados == null)
         listaSeleccionados = new ArrayList<>();
      return listaSeleccionados;
   }

   /**
    * @return the produtoAdicionar
    */
   public FarmProduto getProdutoAdicionar()
   {
      if (produtoAdicionar == null)
         produtoAdicionar = FarmProdutoNovoBean.getInstancia();
      return produtoAdicionar;
   }

   /**
    * @param produtoAdicionar the produtoAdicionar to set
    */
   public void setProdutoAdicionar(FarmProduto produtoAdicionar)
   {
      this.produtoAdicionar = produtoAdicionar;
   }

   /**
    * @param listaSeleccionados the listaSeleccionados to set
    */
   public void setListaSeleccionados(ArrayList<FarmPedidoHasProduto> listaSeleccionados)
   {
      this.listaSeleccionados = listaSeleccionados;
   }

   /**
    * @return the produtosSemStock
    */
   public ArrayList<FarmPedidoHasProduto> getProdutosSemStock()
   {
      return produtosSemStock;
   }

   /**
    * @param produtosSemStock the produtosSemStock to set
    */
   public void setProdutosSemStock(ArrayList<FarmPedidoHasProduto> produtosSemStock)
   {
      this.produtosSemStock = produtosSemStock;
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
      this.pedido = pedido;
   }

}
