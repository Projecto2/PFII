/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.tabelasAuxiliares;

import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmProduto;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmStockRupturaBean implements Serializable
{
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteProdutoHasLocalArmazenamentoFacade;
   
   private List<FarmLoteProdutoHasLocalArmazenamento> listaItens;
   private List<FarmProduto> listaProdutos;
   private List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto;
   
   private FarmProduto produtoPesquisa;
   private FarmLocalArmazenamento local;
   private String descricao = "";
   
   /**
    * Creates a new instance of FarmStockRupturaBean
    */
   public FarmStockRupturaBean()
   {
   }
   
   public void pesquisarItensNoLocal()
   {
      listaItens = loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(getLocal(), descricao);
      listaProdutos = getListaProdutos();
      if(listaProdutos.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
      else Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. "+ listaProdutos.size() +" registo(s) retornado(s).");
   }
   
   public List<FarmProduto> getListaProdutos()
   {
      listaProdutos = new ArrayList<>();
      for( FarmLoteProdutoHasLocalArmazenamento aux :getListaItens())
      {
         if(! listaProdutos.contains(aux.getFkIdLoteProduto().getFkIdProduto()))
            listaProdutos.add(aux.getFkIdLoteProduto().getFkIdProduto());
      }
      return listaProdutos;
   }
   
   public int getQuantidadeItem(FarmProduto produto)
   {
      int qtd = 0;
      produtoPesquisa = produto;
      listaLotesPorProduto = getLotesPorProduto();
      for(FarmLoteProdutoHasLocalArmazenamento aux : listaLotesPorProduto)
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
      
      if(qtdActual > qtdMin * .5)
         cor = "GREEN";
      else if(qtdActual > qtdMin * .25 )
         cor = "ORANGE";
      else
         cor = "RED";
         
      return cor;
   }
   
   public int getQuantidadeMinimaPermitida(FarmProduto produto)
   {
      produtoPesquisa = produto;
      listaLotesPorProduto = getLotesPorProduto();
      if(listaLotesPorProduto.isEmpty() || listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida() == null)
         return 0;
      return listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida();
   }
   
   public List<FarmLoteProdutoHasLocalArmazenamento> getLotesPorProduto()
   {
      listaLotesPorProduto = new ArrayList<>();
      for( FarmLoteProdutoHasLocalArmazenamento aux :listaItens)
      {
         if(aux.getFkIdLoteProduto().getFkIdProduto() == produtoPesquisa)
            listaLotesPorProduto.add(aux);
      }
      
      return listaLotesPorProduto;
   }

   public boolean mostrarNoficacoes()
   {
      descricao = "";
      pesquisarItensNoLocal();
      System.out.println("pesquisou");
      for(FarmLoteProdutoHasLocalArmazenamento aux : listaItens)
      {
         int qtdMin = 0;
         if(aux.getQuantidadeMinimaPermitida() != null)            
            qtdMin = aux.getQuantidadeMinimaPermitida();
         
         if(aux.getQuantidadeStock() <=  qtdMin * .25)
         {
            System.out.println("vai imprimir");
            System.out.println("Alguns Produtos como " 
                    + aux.getFkIdLoteProduto().getFkIdProduto().getDescricao() 
                    + " " + aux.getFkIdLoteProduto().getFkIdProduto().getDosagem() 
                    + " " + aux.getFkIdLoteProduto().getFkIdProduto().getFkIdUnidadeMedida().getAbreviatura() 
                    + " estão abaixo da quantidade mínima permitida. Clique para analisar.");
            return true;
         }
      }
      return false;
   }
   
   public void notificarSock()
   {
      descricao = "";
      pesquisarItensNoLocal();
      for(FarmLoteProdutoHasLocalArmazenamento aux : listaItens)
      {
         int qtdMin = 0;
         if(aux.getQuantidadeMinimaPermitida() != null)            
            qtdMin = aux.getQuantidadeMinimaPermitida();
         
         if(aux.getQuantidadeStock() <=  qtdMin * .25)
         {
            Mensagem.warnMsg("Alguns Produtos como " 
                    + aux.getFkIdLoteProduto().getFkIdProduto().getDescricao() 
                    + " " + aux.getFkIdLoteProduto().getFkIdProduto().getDosagem() 
                    + " " + aux.getFkIdLoteProduto().getFkIdProduto().getFkIdUnidadeMedida().getAbreviatura() 
                    + " estão em ruptura de stock no/a "+ aux.getFkIdLocalArmazenamento().getAbreviatura()
                    + ". Clique para analisar.");
            break;
         }
      }
   }
   
   public List<FarmLoteProdutoHasLocalArmazenamento> getListaItens()
   {
      if(listaItens == null)
         listaItens = new ArrayList<>();
      return listaItens;
   }

   public void setListaItens(List<FarmLoteProdutoHasLocalArmazenamento> listaItens)
   {
      this.listaItens = listaItens;
   }

   public List<FarmLoteProdutoHasLocalArmazenamento> getListaLotesPorProduto()
   {
      return listaLotesPorProduto;
   }

   public void setListaLotesPorProduto(List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto)
   {
      this.listaLotesPorProduto = listaLotesPorProduto;
   }

   public FarmProduto getProdutoPesquisa()
   {
      return produtoPesquisa;
   }

   public void setProdutoPesquisa(FarmProduto produtoPesquisa)
   {
      this.produtoPesquisa = produtoPesquisa;
   }

   public FarmLocalArmazenamento getInstanciaLocal()
   {
      local = new FarmLocalArmazenamento();
      local.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      local.setFkIdInstituicao(new GrlInstituicao());
      return local;
   }
   
   public FarmLocalArmazenamento getLocal()
   {
      if(local == null)
         local = getInstanciaLocal();
      return local;
   }

   public void setLocal(FarmLocalArmazenamento local)
   {
      System.out.println("definindo o local de armazenamento");
      this.local = local;
   }

   public String getDescricao()
   {
      return descricao;
   }

   public void setDescricao(String descricao)
   {
      this.descricao = descricao;
   }
}
