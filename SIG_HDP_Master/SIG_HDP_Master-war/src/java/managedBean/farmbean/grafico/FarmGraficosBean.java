/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.grafico;

import entidade.FarmLoteProduto;
import entidade.FarmProduto;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.farmbean.armazenamento.FarmLocalArmazenamentoListarBean;
import managedBean.farmbean.produto.FarmProdutoNovoBean;
import managedBean.farmbean.relatorio.FarmStockGeralBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.FarmProdutoFacade;
import util.Constantes;
import util.Data;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmGraficosBean implements Serializable
{

   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade farmLoteProdutoHasLocalArmazenamnetoFacade;
   @EJB
   private FarmProdutoFacade farmProdutoFacade;

   private List<FarmLoteProduto> lotesPesquisados, lotesLaranjas, lotesVermelhos;
   private List<ProdutoQuantidade> produtosPesquisados, produtosLaranjas, produtosVermelhos;

   private final String posicaoDaLegenda = "ne";
   private final String corLaranja = "FFA500",
           corPreta = "000000",
           corAmarela = "FFFF00",
           corVermelha = "A30303";

   private final int QUANT_MINIMA_CARACTERS = 5;
   private Date dataPesquisa;
   private String dataUltimaInicializacao;
   private final String dataHoje = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
   /**
    * Creates a new instance of FarmGraficosBean
    */
   public FarmGraficosBean()
   {
   }

   public static FarmGraficosBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmGraficosBean farmGraficosBean
              = (FarmGraficosBean) context.getELContext().getELResolver().getValue(context.getELContext(),
                      null, "farmGraficosBean");

      return farmGraficosBean;
   }

   /**
    * Pesquisa lote de produtos para preencher os gráficos de Validade
    */
   public void pesquisarLotes()
   {
      lotesPesquisados = farmLoteProdutoHasLocalArmazenamnetoFacade.findLoteExistentesNoStock();
      System.out.println("pesquisarLotes : "+lotesPesquisados.size());
   }

   /**
    * Pesquisa produtos para preencher os gráficos de Stock. Obtem um número de
    * regitos limitado a 5.
    */
   public void pesquisarProdutosComLimite()
   {
      produtosPesquisados = new ArrayList<>();
      List<FarmProduto> produtos = farmProdutoFacade.findProduto(FarmProdutoNovoBean.getInstancia());
      if (!produtos.isEmpty())
      {
         produtosPesquisados = ordenarProdutos(produtos);
      }

      while (produtosPesquisados.size() > 5)
      {
         produtosPesquisados.remove(produtosPesquisados.size() - 1);
      }
   }

   /**
    * Pesquisa produtos para preencher os gráficos de Stock. Sem um número de
    * regitos limitado.
    */
   public void pesquisarProdutosSemLimite()
   {
      produtosPesquisados = new ArrayList<>();
      List<FarmProduto> produtos = farmProdutoFacade.findProdutosComRegistoNoStock(FarmProdutoNovoBean.getInstancia(), FarmLocalArmazenamentoListarBean.getInstancia());
      if (!produtos.isEmpty())
      {
         produtosPesquisados = ordenarProdutos(produtos);
      }
      System.out.println("pesquisarProdutosSemLimite : "+produtosPesquisados.size());
   }

   public List<ProdutoQuantidade> ordenarProdutos(List<FarmProduto> produtos)
   {
      for (FarmProduto item : produtos)
      {
         int quantidadeTotal = FarmStockGeralBean.getInstanciaBean().getTotalGeral(item, FarmStockGeralBean.getInstanciaBean().getLocaisArmazenamento());
         int quantidadeMinima = FarmStockGeralBean.getInstanciaBean().getQuantidadeMinimaPermitidaGeral(item);

         produtosPesquisados.add(new ProdutoQuantidade(item, quantidadeTotal, quantidadeMinima));
      }
      List<ProdutoQuantidade> vectorOrdenado = new ArrayList<>();
      while (!produtosPesquisados.isEmpty())
      {
         vectorOrdenado.add(getMenorDaLista(produtosPesquisados));
      }

      return vectorOrdenado;
   }

   public ProdutoQuantidade getMenorDaLista(List<ProdutoQuantidade> lista)
   {
      ProdutoQuantidade menor = lista.get(0);
      for (int i = 1; i < lista.size(); i++)
      {
         System.out.println("i: " + i);
         System.out.println("tamanho da lista: " + lista.size());
         System.out.println("lista.get(i): " + lista.get(i));
         if (menor.quantidadeActual > lista.get(i).quantidadeActual)
         {
            menor = lista.get(i);
         }
      }
      lista.remove(menor);
      return menor;
   }

   public void organizarListasDeStock()
   {
      produtosVermelhos = new ArrayList<>();
      produtosLaranjas = new ArrayList<>();
      for (ProdutoQuantidade item : produtosPesquisados)
      {

         if (item.quantidadeActual <= item.quantidadeMinimaPermitida * .25)
         {
            produtosVermelhos.add(item);
         }
         else if (item.quantidadeActual <= item.quantidadeMinimaPermitida * .50)
         {
            produtosLaranjas.add(item);
         }
      }
   }

   public void organizarListasDeValidade()
   {
      lotesVermelhos = new ArrayList<>();
      lotesLaranjas = new ArrayList<>();
      for (FarmLoteProduto item : getLotesPesquisados())
      {
         Date dataHoje = new Date();

         GregorianCalendar addDate = new GregorianCalendar();
         addDate.add(Calendar.MONTH, Constantes.FARM_MESES_DE_ANTECEDENCIA);
         Date dateIntermediaria = addDate.getTime();

         if (item.getDataValidade().before(dataHoje))
         {
            getLotesVermelhos().add(item);
         }
         else if (item.getDataValidade().after(dataHoje) && item.getDataValidade().before(dateIntermediaria))
         {
            getLotesLaranjas().add(item);
         }
      }
   }

   public static int getDiferencaDeDias(Date dataInicial, Date dataFinal)
   {
      int dias = Data.getDiferencaDeDias(dataFinal, dataInicial);
      if (dias < 0)
      {
         return dias * (-1);
      }

      return dias;
   }

   public void actualizarGraficos()
   {
      pesquisarLotes();
      System.out.println("passou pesquisarLotes");
      pesquisarProdutosSemLimite();
      System.out.println("passou pesquisarProdutosSemLimite");
      organizarListasDeValidade();
      System.out.println("passou organizarListasDeValidade");
      organizarListasDeStock();
      System.out.println("passou organizarListasDeStock");
      setDataPesquisa(new Date());
      System.out.println("passou setDataPesquisa");
   }

   public void inicializarDados()
   {
      if (dataPesquisa == null)
         dataUltimaInicializacao = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
      else
         dataUltimaInicializacao = new SimpleDateFormat("dd-MM-yyyy").format(dataPesquisa);

      if (!dataHoje.equals(dataUltimaInicializacao))
      {
         actualizarGraficos();
      }
   }

   public void inicializarModelo(BarChartModel model, String titulo, String eixoAbssissas, String eixocoordenadas, String corSeries)
   {
      model.setTitle(titulo);
      model.setLegendPosition(posicaoDaLegenda);
      model.setSeriesColors(corSeries);
      model.setBarWidth(70);
      Axis eixoDasAbsissas = model.getAxis(AxisType.X);
      eixoDasAbsissas.setLabel(eixoAbssissas);

      Axis eixoDasCoordenadas = model.getAxis(AxisType.Y);
      eixoDasCoordenadas.setLabel(eixocoordenadas);
   }

   public BarChartModel graficoDeBarrasLotesExpirados()
   {
      System.out.println("graficoDeBarrasLotesExpirados: ");
      BarChartModel model = new BarChartModel();
      inicializarModelo(model, "Produtos Com Lote Expirado", "Nº de Lote", "Tempo de Expiração (Dias)", corPreta);
      ChartSeries barraVermelha = new ChartSeries();
      System.out.println("getLotesVermelhos.size(): "+getLotesVermelhos().size());
      for (int i = 0; i < 5 && i < getLotesVermelhos().size(); i++/*FarmLoteProduto item : lotesVermelhos*/)
      {
         System.out.println("for graficoDeBarrasLotesExpirados");
         barraVermelha.set(getLotesVermelhos().get(i).getNumeroLote(), getDiferencaDeDias(getLotesVermelhos().get(i).getDataValidade(), new Date()));
      }

      barraVermelha.setLabel("Lote Expirado");
      model.addSeries(barraVermelha);
      System.out.println("fim graficoDeBarrasLotesExpirados");
      return model;
   }

   public BarChartModel graficoDeBarrasLotesQuaseExpirados()
   {
      System.out.println("graficoDeBarrasLotesQuaseExpirados: ");
      BarChartModel model = new BarChartModel();
      inicializarModelo(model, "Produtos que Expiram Dentro de " + Constantes.FARM_MESES_DE_ANTECEDENCIA + " Meses.", "Nº de Lote", "Tempo de Validade Restante (Dias)", corLaranja);
      ChartSeries barraLaranja = new ChartSeries();
      System.out.println("getLotesLaranjas.size(): "+getLotesLaranjas().size());
      
      for (int i = 0; i < 5 && i < getLotesLaranjas().size(); i++/*FarmLoteProduto item : lotesLaranjas*/)
      {
         System.out.println("for graficoDeBarrasLotesQuaseExpirados");
         barraLaranja.set(getLotesLaranjas().get(i).getNumeroLote(), getDiferencaDeDias(getLotesLaranjas().get(i).getDataValidade(), new Date()));
      }

      barraLaranja.setLabel("Produtos que Expiram Dentro de " + Constantes.FARM_MESES_DE_ANTECEDENCIA + " Meses.");
      model.addSeries(barraLaranja);
      System.out.println("fim graficoDeBarrasLotesQuaseExpirados");
      return model;
   }

   public BarChartModel graficoDeBarrasSockGeralPosRuptura()
   {
      System.out.println("graficoDeBarrasSockGeralPosRuptura: ");
      BarChartModel model = new BarChartModel();
      inicializarModelo(model, "Produtos com Quantidade Inferior a 25% da Quantidade Mínima Permitida", "Descrição", "Quantidade Actual", corVermelha);
      ChartSeries barraVermelha = new ChartSeries();
      System.out.println("getProdutosVermelhos.size(): "+getProdutosVermelhos().size());
      for (int i = 0; i < 5 && i < produtosVermelhos.size(); i++/*FarmLoteProduto item : lotesVermelhos*/)
      {
         System.out.println("for graficoDeBarrasSockGeralPosRuptura");
         barraVermelha.set(reduzirDescricaoDoProduto(produtosVermelhos.get(i).getProduto().getDescricao()), produtosVermelhos.get(i).getQuantidadeActual());
      }

      barraVermelha.setLabel("Produto em Pós-Ruptura de Stock");
      model.addSeries(barraVermelha);
      System.out.println("fim graficoDeBarrasSockGeralPosRuptura");
      return model;
   }

   public BarChartModel graficoDeBarrasSockGeralPreRuptura()
   {
      System.out.println("graficoDeBarrasSockGeralPreRuptura: ");
      BarChartModel model = new BarChartModel();
      inicializarModelo(model, "Produtos com Quantidade entre 25% e 50% da Quantidade Mínima Permitida", "Descrição", "Quantidade Actual", corAmarela);
      ChartSeries barraVermelha = new ChartSeries();
      System.out.println("produtosLaranjas.size(): "+produtosLaranjas.size());
      for (int i = 0; i < 5 && i < produtosLaranjas.size(); i++/*FarmLoteProduto item : produtosLaranjas*/)
      {
         System.out.println("for graficoDeBarrasSockGeralPreRuptura");
         barraVermelha.set(reduzirDescricaoDoProduto(produtosLaranjas.get(i).getProduto().getDescricao()), produtosLaranjas.get(i).getQuantidadeActual());
      }

      barraVermelha.setLabel("Produto em Pré-Ruptura de Stock");
      model.addSeries(barraVermelha);
      System.out.println("fim graficoDeBarrasSockGeralPreRuptura");
      return model;
   }

   public String reduzirDescricaoDoProduto(String descricao)
   {
      String descricaoAux = "";
      for (int i = 0; i < QUANT_MINIMA_CARACTERS && i < descricao.length(); i++)
      {
         descricaoAux += descricao.charAt(i);
      }
      return descricaoAux + "...";
   }

   /**
    * @return the lotesPesquisados
    */
   public List<FarmLoteProduto> getLotesPesquisados()
   {
      return lotesPesquisados;
   }

   /**
    * @return the produtosLaranjas
    */
   public List<ProdutoQuantidade> getProdutosLaranjas()
   {
      if (produtosLaranjas == null)
      {
         produtosLaranjas = new ArrayList<>();
      }
      return produtosLaranjas;
   }

   /**
    * @param produtosLaranjas the produtosLaranjas to set
    */
   public void setProdutosLaranjas(List<ProdutoQuantidade> produtosLaranjas)
   {
      this.produtosLaranjas = produtosLaranjas;
   }

   /**
    * @return the produtosVermelhos
    */
   public List<ProdutoQuantidade> getProdutosVermelhos()
   {
      if (produtosVermelhos == null)
      {
         produtosVermelhos = new ArrayList<>();
      }
      return produtosVermelhos;
   }

   /**
    * @param produtosVermelhos the produtosVermelhos to set
    */
   public void setProdutosVermelhos(List<ProdutoQuantidade> produtosVermelhos)
   {
      this.produtosVermelhos = produtosVermelhos;
   }

   /**
    * @return the lotesLaranjas
    */
   public List<FarmLoteProduto> getLotesLaranjas()
   {
      if (lotesLaranjas == null)
      {
         lotesLaranjas = new ArrayList<>();
      }
      return lotesLaranjas;
   }

   /**
    * @return the lotesVermelhos
    */
   public List<FarmLoteProduto> getLotesVermelhos()
   {
      if (lotesVermelhos == null)
      {
         lotesVermelhos = new ArrayList<>();
      }
      return lotesVermelhos;
   }

   public boolean rendizarImagem()
   {
      return getLotesLaranjas().isEmpty() && getLotesVermelhos().isEmpty()
              && getProdutosVermelhos().isEmpty() && getProdutosLaranjas().isEmpty();
   }

   /**
    * @return the dataPesquisa
    */
   public Date getDataPesquisa()
   {
      return dataPesquisa;
   }

   /**
    * @param dataPesquisa the dataPesquisa to set
    */
   public void setDataPesquisa(Date dataPesquisa)
   {
      this.dataPesquisa = dataPesquisa;
   }

   private class ProdutoQuantidade
   {

      private FarmProduto produto;
      private int quantidadeActual, quantidadeMinimaPermitida;

      public ProdutoQuantidade(FarmProduto produto, int quantidadeActual, int quantidadeMinimaPermitida)
      {
         this.produto = produto;
         this.quantidadeActual = quantidadeActual;
         this.quantidadeMinimaPermitida = quantidadeMinimaPermitida;
      }

      public int getQuantidadeMinimaPermitida()
      {
         return quantidadeMinimaPermitida;
      }

      public void setQuantidadeMinimaPermitida(int quantidadeMinimaPermitida)
      {
         this.quantidadeMinimaPermitida = quantidadeMinimaPermitida;
      }

      public int getQuantidadeActual()
      {
         return quantidadeActual;
      }

      public void setQuantidadeActual(int quantidadeActual)
      {
         this.quantidadeActual = quantidadeActual;
      }

      public FarmProduto getProduto()
      {
         return produto;
      }

      public void setProduto(FarmProduto produto)
      {
         this.produto = produto;
      }

   }

}
