/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.relatorio;

import entidade.FarmFichaStock;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmProduto;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import sessao.FarmFichaStockFacade;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import util.Constantes;
import util.Mensagem;
import util.RelatorioJasper;
import util.relatorio.ConexaoPostgresSQL;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmFichaDeStockNoLocalBean implements Serializable
{

   @EJB
   private FarmFichaStockFacade fichaStockFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteProdutoHasLocalArmazenamentoFacade;
   @EJB
   private FarmLocalArmazenamentoFacade locaisArmazenamentoFacade;

   private FarmLocalArmazenamento local;
   private List<FarmProduto> listaProdutos;
   private Date dataInicio, dataFim;

   private ConexaoPostgresSQL conexaoPostgresSQL;

   /**
    * Creates a new instance of FarmFichaDeStockNoLocalBean
    */
   public FarmFichaDeStockNoLocalBean()
   {

      this.conexaoPostgresSQL = new ConexaoPostgresSQL();
   }

   public void validarLocal(ValueChangeEvent e)
   {
      listaProdutos = new ArrayList<>();
   }

   public void pesquisarProdutosNoLocal()
   {
      listaProdutos = new ArrayList<>();
      List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto = loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(local, "");
      for (FarmLoteProdutoHasLocalArmazenamento aux : listaLotesPorProduto)
      {
         if (!listaProdutos.contains(aux.getFkIdLoteProduto().getFkIdProduto()))
            listaProdutos.add(aux.getFkIdLoteProduto().getFkIdProduto());
      }

      if (listaProdutos.isEmpty())
         Mensagem.warnMsg("Nenhum resultado encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. Encontrados registos para " + listaProdutos.size() + " produto(s).");

   }

   public List<FarmFichaStock> getFichaStockDoProduto(FarmProduto produto)
   {
      return fichaStockFacade.findFichaStockComIntervaloDeData(local, produto, dataInicio, dataFim);
   }

   public List<FarmLoteProdutoHasLocalArmazenamento> pesquisarLotesPorProduto(FarmProduto produto)
   {
      List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto = new ArrayList<>();
      for (FarmLoteProdutoHasLocalArmazenamento aux : loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(local, produto.getDescricao()))
      {
         if (aux.getFkIdLoteProduto().getFkIdProduto() == produto)
            listaLotesPorProduto.add(aux);
      }

      return listaLotesPorProduto;
   }

   public int getQuantidadeTotalItem(FarmProduto produto)
   {
      System.out.println("getQuantidadeTotalItem  no inventario");
      System.out.println("produto: " + produto.getDescricao() + " " + produto.getDosagem());
      System.out.println("no local: " + local);
      int qtd = 0;
      for (FarmLoteProdutoHasLocalArmazenamento aux : loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(local, produto))
      {
         System.out.println("obtido lote " + aux.getFkIdLoteProduto() + " com qtd = " + aux.getQuantidadeStock());
         qtd += aux.getQuantidadeStock();
         System.out.println("ja somou: "+qtd);
      }

      System.out.println("qtd> " + qtd);
      return qtd;
   }

   public int getTotalEntradasItem(FarmProduto produto)
   {
      int qtd = 0;
      for (FarmFichaStock aux : getFichaStockDoProduto(produto))
      {
         qtd += aux.getEntradas();
      }

      return qtd;
   }

   public int getTotalSaidasItem(FarmProduto produto)
   {
      int qtd = 0;
      for (FarmFichaStock aux : getFichaStockDoProduto(produto))
      {
         qtd += aux.getSaidas();
      }

      return qtd;
   }

   public String getQuantidadeItemColor(FarmProduto produto)
   {
      System.out.println("obtendo a cor do "+produto.getDescricao());
      String cor;
      int qtdActual = getQuantidadeTotalItem(produto);
      int qtdMin = getQuantidadeMinimaPermitida(produto);
      //verde: > qtdMin 50%
      //laranja: > qtdMin 25% e < qtdMin 50%
      //vermelho: < qtdMin 25%

      if (qtdActual > qtdMin * .50)
         cor = "GREEN";
      else if (qtdActual > qtdMin * .25)
         cor = "ORANGE";
      else
         cor = "RED";
      System.out.println("cor: "+cor);
      return cor;
   }

   public int getQuantidadeMinimaPermitida(FarmProduto produto)
   {
      System.out.println("quant min do item: "+produto.getDescricao());
      List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto = loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(local, produto);

      if (listaLotesPorProduto.isEmpty() || listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida() == null)
         return 0;
      System.out.println("encontrou || 0: "+listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida());
      return listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida();
   }

   public ArrayList<SelectItem> getLocaisArmazenamento()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_FARMACIA));
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

   public FarmLocalArmazenamento getInstanciaLocal()
   {
      FarmLocalArmazenamento localAux = new FarmLocalArmazenamento();
      localAux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      localAux.setFkIdInstituicao(new GrlInstituicao());

      return localAux;
   }

   public FarmLocalArmazenamento getLocal()
   {
      if (local == null)
         local = getInstanciaLocal();

      return local;
   }

   public void setLocal(FarmLocalArmazenamento local)
   {
      this.local = local;
   }

   public List<FarmProduto> getListaProdutos()
   {
      if (listaProdutos == null)
         listaProdutos = new ArrayList<>();

      return listaProdutos;
   }

   public void setListaProdutos(List<FarmProduto> listaProdutos)
   {
      this.listaProdutos = listaProdutos;
   }

   public Date getDataInicio()
   {
      return dataInicio;
   }

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

   public Date getMenorData()
   {
      Calendar calendar = Calendar.getInstance();
      calendar.set(0001, 01, 01);
      return calendar.getTime();
   }

   public Date getHoje()
   {
      return new Date();
   }

   public void exportPDF2(FarmProduto produto)
   {
      int totalEntradas, totalSaidas, quantActual;
      totalEntradas = getTotalEntradasItem(produto);
      totalSaidas = getTotalSaidasItem(produto);
      quantActual = getQuantidadeTotalItem(produto);

      HashMap<String, Object> parametrosMap = new HashMap<>();
      parametrosMap.put("totalDeEntradas", totalEntradas);
      parametrosMap.put("totalDeSaidas", totalSaidas);
      parametrosMap.put("quantidadeActual", quantActual);

      RelatorioJasper.exportPDF("farm/SubRelatorioInventario.jasper", parametrosMap, getFichaStockDoProduto(produto));
   }

   public void imprimirRelatorio()
   {
      conexaoPostgresSQL = new ConexaoPostgresSQL();
      Connection conn = conexaoPostgresSQL.getConnection();
      HashMap<String, Object> parametrosMap = new HashMap<>();

      if(dataInicio == null)
         dataInicio = getMenorData();
      
      parametrosMap.put("idLocal", local.getPkIdLocalArmazenamento());
      parametrosMap.put("dataInicial", dataInicio);
      parametrosMap.put("dataFinal",  getDataFim());
      parametrosMap.put("REPORT_CONNECTION", conn);
      parametrosMap.put("SUBREPORT_DIR", "/home/" + System.getProperty("user.name") + Constantes.FARM_CAMINHO_DO_SUBREPORT);

      RelatorioJasper.exportPDFSemLista("farm/inventarioMaster.jasper", parametrosMap);
      dataInicio = null;
   }
}
