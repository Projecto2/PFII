/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.relatorio;

import entidade.FarmCategoriaMedicamento;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmProduto;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import managedBean.farmbean.produto.FarmProdutoNovoBean;
import sessao.FarmFichaStockFacade;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.FarmProdutoFacade;
import util.Constantes;
import util.Mensagem;
import util.relatorio.ConexaoPostgresSQL;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmInventarioBean implements Serializable
{
   @EJB
   private FarmProdutoFacade produtoFacade;
   @EJB
   private FarmFichaStockFacade fichaStockFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteProdutoHasLocalArmazenamentoFacade;
   @EJB
   private FarmLocalArmazenamentoFacade localArmazenamentoFacade;

   private FarmLocalArmazenamento farmLocalArmazenamentoPesquisa;
   
   private FarmProduto farmProdutoPesquisa;
   private List<FarmProduto> listaProdutos;
   private List<FarmLocalArmazenamento> listaLocais;

   private ConexaoPostgresSQL conexaoPostgresSQL;

   /**
    * Creates a new instance of FarmInventarioBean
    */
   public FarmInventarioBean()
   {

      this.conexaoPostgresSQL = new ConexaoPostgresSQL();
   }

   
   public void pesquisarProdutos()
   {
      listaProdutos = new ArrayList<>();
      if(farmProdutoPesquisa.getPkIdProduto() != null)
      {
         farmProdutoPesquisa = produtoFacade.find(farmProdutoPesquisa.getPkIdProduto());
//         listaProdutos.add(farmProdutoPesquisa);
         if(farmProdutoPesquisa.getFkIdCategoriaMedicamento() == null)
             farmProdutoPesquisa.setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento());
         listaProdutos = produtoFacade.findProdutosComRegistoNoStock(farmProdutoPesquisa, farmLocalArmazenamentoPesquisa);
         System.out.println("pesquisar produtos apenas um "+listaProdutos);
      }
      else
         listaProdutos = produtoFacade.findProdutosComRegistoNoStock(FarmProdutoNovoBean.getInstancia(), farmLocalArmazenamentoPesquisa);
      System.out.println("pesquisar produtos todos "+listaProdutos.size());
      
   }
   
   public void actualizar(ValueChangeEvent e)
   {
      listaProdutos = new ArrayList<>();
      listaLocais = new ArrayList<>();
   }

   public void pesquisarProdutosNoLocal()
   {
      pesquisarProdutos();
//      List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto = 
//              loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(farmLocalArmazenamentoPesquisa, "");
////      for (FarmLoteProdutoHasLocalArmazenamento aux : listaLotesPorProduto)
//      {
//         if (!listaProdutos.contains(aux.getFkIdLoteProduto().getFkIdProduto()))
//            listaProdutos.add(aux.getFkIdLoteProduto().getFkIdProduto());
//      }

      if (listaProdutos.isEmpty())
         Mensagem.warnMsg("Nenhum resultado encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. Encontrados registos para " + listaProdutos.size() + " produto(s).");

   }

//   public List<FarmFichaStock> getFichaStockDoProduto(FarmProduto produto)
//   {
//      return fichaStockFacade.findFichaStockComIntervaloDeData(farmLocalArmazenamentoPesquisa, produto, dataInicio, dataFim);
//   }

   public List<FarmLoteProdutoHasLocalArmazenamento> pesquisarLotesPorProduto(FarmProduto produto)
   {
      List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto = new ArrayList<>();
      for (FarmLoteProdutoHasLocalArmazenamento aux : loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(farmLocalArmazenamentoPesquisa, produto.getDescricao()))
      {
         if (aux.getFkIdLoteProduto().getFkIdProduto() == produto)
            listaLotesPorProduto.add(aux);
      }

      return listaLotesPorProduto;
   }

   public int getQuantidadeTotalItem(FarmProduto produto)
   {
      int qtd = 0;
      for (FarmLoteProdutoHasLocalArmazenamento aux : loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(farmLocalArmazenamentoPesquisa, produto))
      {
         qtd += aux.getQuantidadeStock();
      }

      return qtd;
   }

//   public int getTotalEntradasItem(FarmProduto produto)
//   {
//      int qtd = 0;
//      for (FarmFichaStock aux : getFichaStockDoProduto(produto))
//      {
//         qtd += aux.getEntradas();
//      }
//
//      return qtd;
//   }

//   public int getTotalSaidasItem(FarmProduto produto)
//   {
//      int qtd = 0;
//      for (FarmFichaStock aux : getFichaStockDoProduto(produto))
//      {
//         qtd += aux.getSaidas();
//      }
//
//      return qtd;
//   }

   public String getQuantidadeItemColor(FarmProduto produto)
   {
      String cor;
      int qtdActual = getQuantidadeTotalItem(produto);
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
      List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto = loteProdutoHasLocalArmazenamentoFacade.findProdutosNoLocal(farmLocalArmazenamentoPesquisa, produto.getDescricao());

      if (listaLotesPorProduto.isEmpty() || listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida() == null)
         return 0;

      return listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida();
   }

   public ArrayList<SelectItem> getLocaisArmazenamento()
   {

      ArrayList<SelectItem> itens = new ArrayList<>();
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_FARMACIA));
      for (FarmLocalArmazenamento e : localArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(new SelectItem(e.getPkIdLocalArmazenamento(), e.getDescricao()));
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_ARMAZEM));
      for (FarmLocalArmazenamento e : localArmazenamentoFacade.findLocalArmazenamento(aux))
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

   public FarmLocalArmazenamento getFarmLocalArmazenamentoPesquisa()
   {
      if (farmLocalArmazenamentoPesquisa == null)
         farmLocalArmazenamentoPesquisa = getInstanciaLocal();
//
//      if(farmLocalArmazenamentoPesquisa.getPkIdLocalArmazenamento() != null) 
//          farmLocalArmazenamentoPesquisa = localArmazenamentoFacade.find(farmLocalArmazenamentoPesquisa);
      
      return farmLocalArmazenamentoPesquisa;
   }

   public void setFarmLocalArmazenamentoPesquisa(FarmLocalArmazenamento farmLocalArmazenamentoPesquisa)
   {
      this.farmLocalArmazenamentoPesquisa = farmLocalArmazenamentoPesquisa;
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
//
//   public Date getDataInicio()
//   {
//      return dataInicio;
//   }
//
//   public void setDataInicio(Date dataInicio)
//   {
//      this.dataInicio = dataInicio;
//   }
//
//   public Date getDataFim()
//   {
//      dataFim = new Date();
//      dataFim = getEndOfDay(dataFim);
//      return dataFim;
//   }
//
//   public void setDataFim(Date dataFim)
//   {
//      dataFim = getEndOfDay(dataFim);
//      this.dataFim = dataFim;
//   }

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
//
//   public void exportPDF2(FarmProduto produto)
//   {
//      int totalEntradas, totalSaidas, quantActual;
//      totalEntradas = getTotalEntradasItem(produto);
//      totalSaidas = getTotalSaidasItem(produto);
//      quantActual = getQuantidadeTotalItem(produto);
//
//      HashMap<String, Object> parametrosMap = new HashMap<>();
//      parametrosMap.put("totalDeEntradas", totalEntradas);
//      parametrosMap.put("totalDeSaidas", totalSaidas);
//      parametrosMap.put("quantidadeActual", quantActual);
//
//      RelatorioJasper.exportPDF("farm/SubRelatorioInventario.jasper", parametrosMap, getFichaStockDoProduto(produto));
//   }
//
//   public void imprimirRelatorio()
//   {
//      conexaoPostgresSQL = new ConexaoPostgresSQL();
//      Connection conn = conexaoPostgresSQL.getConnection();
//      HashMap<String, Object> parametrosMap = new HashMap<>();
//
//      if(dataInicio == null)
//         dataInicio = getMenorData();
//      
//      parametrosMap.put("idLocal", farmLocalArmazenamentoPesquisa.getPkIdLocalArmazenamento());
//      parametrosMap.put("dataInicial", dataInicio);
//      parametrosMap.put("dataFinal",  getDataFim());
//      parametrosMap.put("REPORT_CONNECTION", conn);
//      parametrosMap.put("SUBREPORT_DIR", "/home/" + System.getProperty("user.name") + "/NetBeansProjects/2017_05_28/SIG_HDP_Master/SIG_HDP_Master-war/web/WEB-INF/relatorios/farm/");
//
//      RelatorioJasper.exportPDFSemLista("farm/inventarioMaster.jasper", parametrosMap);
//      dataInicio = null;
//   }
   
   public ArrayList<FarmLocalArmazenamento> getListaDeLocaisArmazenamento()
   {

      ArrayList<FarmLocalArmazenamento> itens = new ArrayList<>();
      FarmLocalArmazenamento aux = new FarmLocalArmazenamento();
      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_FARMACIA));
      for (FarmLocalArmazenamento local : localArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(local);
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_ARMAZEM));
      for (FarmLocalArmazenamento local : localArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(local);
      }

      aux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(Constantes.FARM_TIPO_LOCAL_AREA_INTERNA));
      for (FarmLocalArmazenamento local : localArmazenamentoFacade.findLocalArmazenamento(aux))
      {
         itens.add(local);
      }

      return itens;
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
}
