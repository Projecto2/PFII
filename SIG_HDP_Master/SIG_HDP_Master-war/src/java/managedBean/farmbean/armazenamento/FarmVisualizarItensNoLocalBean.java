/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.armazenamento;

import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmProduto;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.UserTransaction;
import managedBean.farmbean.produto.FarmProdutoNovoBean;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmVisualizarItensNoLocalBean implements Serializable
{
   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteProdutoHasLocalArmazenamentoFacade;
   @EJB
   private FarmLocalArmazenamentoFacade locaisArmazenamentoFacade;
   private FarmLocalArmazenamento local;

   private List<FarmLoteProdutoHasLocalArmazenamento> listaItens;
   private List<FarmProduto> listaProdutos;
   private List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto;
   private FarmProduto produtoPesquisa;
   private String descricao;

   /**
    * Creates a new instance of FarmVisualizarItensNoLocalBean
    */
   public FarmVisualizarItensNoLocalBean()
   {
      local = getInstanciaLocal();
      listaItens = new ArrayList<>();
      listaProdutos = new ArrayList<>();
      listaLotesPorProduto = new ArrayList<>();
      produtoPesquisa = FarmProdutoNovoBean.getInstancia();
      descricao = "";
   }

   public static FarmVisualizarItensNoLocalBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmVisualizarItensNoLocalBean farmVisualizarItensNoLocalBean = (FarmVisualizarItensNoLocalBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmVisualizarItensNoLocalBean");

      return farmVisualizarItensNoLocalBean;
   }

   public FarmLocalArmazenamento getInstanciaLocal()
   {
      local = new FarmLocalArmazenamento();
      local.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      local.setFkIdInstituicao(new GrlInstituicao());
      return local;
   }

   public FarmLoteProdutoHasLocalArmazenamento getReferenciaItem(FarmProduto produto)
   {
      return loteProdutoHasLocalArmazenamentoFacade.findReferenciaProdutoNoLocal(local, produto);
   }

   public int getQuantidadeItem(FarmProduto produto)
   {
      int qtd = 0;
      this.setProdutoPesquisa(produto);
      listaLotesPorProduto = getLotesPorProduto();
      for (FarmLoteProdutoHasLocalArmazenamento aux : listaLotesPorProduto)
      {
         qtd += aux.getQuantidadeStock();
      }
      return qtd;
   }

   public String getQuantidadeItemColor(FarmProduto produto)
   {
      String cor;
      int qtdActual = getQuantidadeItem(produto);
      int qtdMin = getQuantidadeMinimaPermitida(produto);
      //verde: > qtdMin 50%
      //laranja: > qtdMin 25% e < qtdMin 50%
      //vermelho: < qtdMin 25%

      if (qtdActual > qtdMin * .5)
         cor = "GREEN";
      else if (qtdActual > qtdMin * .25)
         cor = "ORANGE";
      else
         cor = "RED";

      return cor;
   }

   public int getQuantidadeMinimaPermitida(FarmProduto produto)
   {
      this.setProdutoPesquisa(produto);
      listaLotesPorProduto = getLotesPorProduto();
      if (listaLotesPorProduto.isEmpty() || listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida() == null)
         return 0;
      return listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida();
   }

   public List<FarmLoteProdutoHasLocalArmazenamento> getLotesPorProduto()
   {
      listaLotesPorProduto = new ArrayList<>();
      for (FarmLoteProdutoHasLocalArmazenamento aux : listaItens)
      {
         if (aux.getFkIdLoteProduto().getFkIdProduto() == produtoPesquisa)
            listaLotesPorProduto.add(aux);
      }

      return listaLotesPorProduto;
   }

   public void pesquisarItensNoLocal()
   {
      setListaItens(loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(local, getDescricao()));
      listaProdutos = getListaProdutos();
   }

   /**
    * @return the local
    */
   public FarmLocalArmazenamento getLocal()
   {
      return local;
   }

   public void definirLocalOrigemPedido(String localString)
   {
      System.out.println("local a definir: " + localString);
      FarmLocalArmazenamento localAux = getInstanciaLocal();
      localAux.setDescricao(localString);

      if (!locaisArmazenamentoFacade.findLocalArmazenamento(localAux).isEmpty())
         local = locaisArmazenamentoFacade.findLocalArmazenamento(local).get(0);

      pesquisarItensNoLocal();
      System.out.println("origem do pedido definida como: " + local);
   }

   /**
    * @param local the local to set
    */
   public void setLocal(FarmLocalArmazenamento local)
   {
      this.local = local;
      listaItens = getListaItens();
   }

   /**
    * @return the listaItens
    */
   public List<FarmLoteProdutoHasLocalArmazenamento> getListaItens()
   {
      pesquisarItensNoLocal();
      return listaItens;
   }

   /**
    * @param listaItens the listaItens to set
    */
   public void setListaItens(List<FarmLoteProdutoHasLocalArmazenamento> listaItens)
   {
      this.listaItens = listaItens;
   }

   /**
    * @return the listaProdutos
    */
   public List<FarmProduto> getListaProdutos()
   {
      listaProdutos = new ArrayList<>();
      for (FarmLoteProdutoHasLocalArmazenamento aux : listaItens)
      {
         if (!listaProdutos.contains(aux.getFkIdLoteProduto().getFkIdProduto()))
            if (descricao.isEmpty() || aux.getFkIdLoteProduto().getFkIdProduto().getDescricao().startsWith(descricao))
            {
               listaProdutos.add(aux.getFkIdLoteProduto().getFkIdProduto());
            }
      }
      return listaProdutos;
   }

     /**
    * @param listaProdutos the listaProdutos to set
    */
   public void setListaProdutos(List<FarmProduto> listaProdutos)
   {
      this.listaProdutos = listaProdutos;
   }

   /**
    * @return the listaLotesPorProduto
    */
   public List<FarmLoteProdutoHasLocalArmazenamento> getListaLotesPorProduto()
   {
      return listaLotesPorProduto;
   }

   /**
    * @param listaLotesPorProduto the listaLotesPorProduto to set
    */
   public void setListaLotesPorProduto(List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto)
   {
      this.listaLotesPorProduto = listaLotesPorProduto;
   }

   /**
    * @return the produtoPesquisa
    */
   public FarmProduto getProdutoPesquisa()
   {
      return produtoPesquisa;
   }

   /**
    * @param produtoPesquisa the produtoPesquisa to set
    */
   public void setProdutoPesquisa(FarmProduto produtoPesquisa)
   {
      System.out.println("fazendo set do produto: " + produtoPesquisa.getDescricao());
      this.produtoPesquisa = produtoPesquisa;
   }

   /**
    * @return the descricao
    */
   public String getDescricao()
   {
      return descricao;
   }

   /**
    * @param descricao the descricao to set
    */
   public void setDescricao(String descricao)
   {
      this.descricao = descricao;
   }

}
