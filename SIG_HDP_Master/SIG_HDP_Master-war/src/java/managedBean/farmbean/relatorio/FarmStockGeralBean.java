/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.relatorio;

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
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import managedBean.farmbean.armazenamento.FarmLocalArmazenamentoListarBean;
import managedBean.farmbean.produto.FarmProdutoNovoBean;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.FarmProdutoFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmStockGeralBean implements Serializable
{

   @EJB
   private FarmLocalArmazenamentoFacade locaisArmazenamentoFacade;
   @EJB
   private FarmProdutoFacade produtoFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteProdutoHasLocalArmazenamentoFacade;

   private FarmLocalArmazenamento farmLocalArmazenamentoPesquisa;
   private FarmProduto farmProdutoPesquisa;
   private List<FarmProduto> listaProdutos;
   private List<FarmLocalArmazenamento> listaLocais;

   /**
    * Creates a new instance of FarmStockGeralBean
    */
   public FarmStockGeralBean()
   {
   }

   public static FarmStockGeralBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmStockGeralBean farmStockGeralBean
              = (FarmStockGeralBean) context.getELContext().getELResolver().getValue(context.getELContext(),
                      null, "farmStockGeralBean");

      return farmStockGeralBean;
   }
   
   public FarmLocalArmazenamento getInstanciaLocal()
   {
      FarmLocalArmazenamento localAux = new FarmLocalArmazenamento();
      localAux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      localAux.setFkIdInstituicao(new GrlInstituicao());

      return localAux;
   }

   public void pesquisarProdutos()
   {
      listaProdutos = new ArrayList<>();
      getFarmProdutoPesquisa();
      if (farmProdutoPesquisa.getPkIdProduto() != null)
      {
         farmProdutoPesquisa = produtoFacade.find(farmProdutoPesquisa.getPkIdProduto());
         listaProdutos.add(farmProdutoPesquisa);
      }
      else
         listaProdutos = produtoFacade.findProduto(FarmProdutoNovoBean.getInstancia());

   }

   public void pesquisarLocais()
   {
      listaLocais = new ArrayList<>();
      getFarmLocalArmazenamentoPesquisa();
      if (farmLocalArmazenamentoPesquisa.getPkIdLocalArmazenamento() != null)
      {
         farmLocalArmazenamentoPesquisa = locaisArmazenamentoFacade.find(farmLocalArmazenamentoPesquisa.getPkIdLocalArmazenamento());
         listaLocais.add(farmLocalArmazenamentoPesquisa);
      }
      else
         listaLocais = getLocaisArmazenamento();
   }

   public void pesquisar()
   {
      pesquisarLocais();
      pesquisarProdutos();
      if (listaProdutos.isEmpty())
         Mensagem.warnMsg("Nenhum resultado encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso para " + listaProdutos.size() + " produto(s).");
   }

   public ArrayList<SelectItem> getProdutos()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmProduto e : produtoFacade.findProduto(FarmProdutoNovoBean.getInstancia()))
      {
         itens.add(new SelectItem(
                 e.getPkIdProduto(), e.getDescricao()
                 + " " + e.getDosagem()
                 + " " + e.getFkIdUnidadeMedida().getAbreviatura()
                 + " (" + e.getFkIdFormaFarmaceutica().getDescricao()
                 + ")"));
      }

      return itens;
   }

   public void actualizar(ValueChangeEvent e)
   {
      listaProdutos = new ArrayList<>();
      listaLocais = new ArrayList<>();
   }

   /**
    * @return the farmLocalArmazenamentoPesquisa
    */
   public FarmLocalArmazenamento getFarmLocalArmazenamentoPesquisa()
   {
      if (farmLocalArmazenamentoPesquisa == null)
         farmLocalArmazenamentoPesquisa = FarmLocalArmazenamentoListarBean.getInstancia();
      return farmLocalArmazenamentoPesquisa;
   }

   /**
    * @return the farmProdutoPesquisa
    */
   public FarmProduto getFarmProdutoPesquisa()
   {
      if (farmProdutoPesquisa == null)
         farmProdutoPesquisa = FarmProdutoNovoBean.getInstancia();

      return farmProdutoPesquisa;
   }

   /**
    * @return the listaProdutos
    */
   public List<FarmProduto> getListaProdutos()
   {
      if (listaProdutos == null)
         listaProdutos = new ArrayList<>();

      return listaProdutos;
   }

   /**
    * @return the listaLocais
    */
   public List<FarmLocalArmazenamento> getListaLocais()
   {
      if (listaLocais == null)
         listaLocais = new ArrayList<>();

      return listaLocais;
   }

   /**
    *
    * @param produto O produto que se deseja saber a quantidade armazenada
    * @param local O local no qual o produto se encontra
    * @return A quantidade total do produto neste local
    */
   public int getTotalParcial(FarmProduto produto, FarmLocalArmazenamento local)
   {
      int totalParcial = 0;
      for (FarmLoteProdutoHasLocalArmazenamento aux : getLotesPorProdutoNoLocal(produto, local))
      {
         totalParcial += aux.getQuantidadeStock();
      }
      return totalParcial;
   }

   /**
    *
    * @param produto O produto que se deseja saber o totalArmazenado
    * @param locais Os locais nos quais o produto se encontra
    * @return A soma quantidade total do produto nestes locais
    */
   public int getTotalGeral(FarmProduto produto, List<FarmLocalArmazenamento> locais)
   {
      int totalGeral = 0;
      for (FarmLocalArmazenamento local : locais)
      {
         totalGeral += getTotalParcial(produto, local);
      }
      return totalGeral;
   }
   
   /**
    *
    * @param produto O produto que se deseja saber o totalArmazenado no hospital
    * @return A soma quantidade total do produto nestes locais
    */
   public int getTotalGeral(FarmProduto produto)
   {
      System.out.println("soma dco produto: "+produto);
      int totalGeral = 0;
      System.out.println("antes: "+totalGeral);
      for (FarmLocalArmazenamento local : locaisArmazenamentoFacade.findAll())
      {
         System.out.println("total + no local "+local + ": "+totalGeral);
         totalGeral += getTotalParcial(produto, local);
      }
      System.out.println("a retornar: "+totalGeral);
      return totalGeral;
   }
   
   /**
    * Calcula a cor que a referência do item deve tomar de acordo com a quantidade mínima permitida do produto no local.
    * @param produto
    * @param local
    * @return a cor como String
    */
   public String getQuantidadeItemColor(FarmProduto produto, FarmLocalArmazenamento local)
   {
      String cor;
      int qtdActual = getTotalParcial(produto, local);
      int qtdMin = getQuantidadeMinimaPermitida(produto, local);
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

   /**
    * 
    * @param produto
    * @param local
    * @return a quantidade mínima permitida do produto no local.
    */
   public int getQuantidadeMinimaPermitida(FarmProduto produto, FarmLocalArmazenamento local)
   {
      List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto = getLotesPorProdutoNoLocal(produto, local);
      if (listaLotesPorProduto.isEmpty() || listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida() == null)
         return 0;
      return listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida();
   }
   
   /**
    * 
    * @param produto
    * @return a quantidade mínima permitida do produto no hospital em geral.
    */
   public int getQuantidadeMinimaPermitidaGeral(FarmProduto produto)
   {
      List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto = loteProdutoHasLocalArmazenamentoFacade.findProdutoEmTodosOsLocais(produto);
      int quantidadeMinimaPermitidaGeral = 0;
      for (FarmLoteProdutoHasLocalArmazenamento item : listaLotesPorProduto)
      {
         if(item.getQuantidadeMinimaPermitida() != null)
            quantidadeMinimaPermitidaGeral += item.getQuantidadeMinimaPermitida();
      }
      return quantidadeMinimaPermitidaGeral;
   }

   public List<FarmLoteProdutoHasLocalArmazenamento> getLotesPorProdutoNoLocal(FarmProduto produto, FarmLocalArmazenamento local)
   {
      List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto = new ArrayList<>();
      for (FarmLoteProdutoHasLocalArmazenamento aux : loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(local, produto))
      {
         listaLotesPorProduto.add(aux);
      }

      return listaLotesPorProduto;
   }

   public ArrayList<FarmLocalArmazenamento> getLocaisArmazenamento()
   {

      ArrayList<FarmLocalArmazenamento> itens = new ArrayList<>();
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_FARMACIA));
      for (FarmLocalArmazenamento local : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(local);
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_ARMAZEM));
      for (FarmLocalArmazenamento local : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(local);
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_AREA_INTERNA));
      for (FarmLocalArmazenamento local : locaisArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(local);
      }

      return itens;
   }
}
