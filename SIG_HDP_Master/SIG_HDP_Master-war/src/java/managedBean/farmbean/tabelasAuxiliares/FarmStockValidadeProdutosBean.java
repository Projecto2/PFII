/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.tabelasAuxiliares;

import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmProduto;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import entidade.GrlMarcaProduto;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.FarmLoteProdutoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmStockValidadeProdutosBean implements Serializable
{

   @EJB
   FarmLoteProdutoFacade loteFacade;
   @EJB
   FarmLoteProdutoHasLocalArmazenamentoFacade loteLocalFacade;

   private Date dataValidadeInicio;
   private Date dataValidadeFim;
   private FarmLoteProduto loteProdutoPesquisa;
   private List<FarmLoteProduto> lotesDeProdutosPesquisados;

   /**
    * Creates a new instance of stockValidadeProdutosBean
    */
   public FarmStockValidadeProdutosBean()
   {
   }

   public FarmLoteProduto getInstanciaLoteProdutoPesquisa()
   {
      loteProdutoPesquisa = new FarmLoteProduto();
      loteProdutoPesquisa.setFkIdProduto(new FarmProduto());
      loteProdutoPesquisa.setFkIdFuncionarioCadastrou(new RhFuncionario());
      loteProdutoPesquisa.setDataCadastro(new Date());
      loteProdutoPesquisa.setFkIdMarca(new GrlMarcaProduto());

      return loteProdutoPesquisa;
   }

   public void pesquisarLotesProdutos()
   {
      System.out.println("pesuisando lotes descricao: "+getLoteProdutoPesquisa().getFkIdProduto().getDescricao());
      System.out.println("pesuisando lotes lote: "+getLoteProdutoPesquisa().getNumeroLote());
      System.out.println("pesuisando lotes dataValidadeInicio: "+dataValidadeInicio);
      System.out.println("pesuisando lotes dataValidadeFim: "+dataValidadeFim);
      
      lotesDeProdutosPesquisados = loteFacade.findLoteProduto(getLoteProdutoPesquisa(), dataValidadeInicio, dataValidadeFim);
      if (lotesDeProdutosPesquisados.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + lotesDeProdutosPesquisados.size() + " registo(s) retornado(s).");
   }

   public static boolean loteExpirado(FarmLoteProduto item)
   {
      Date dataHoje = new Date();
      GregorianCalendar addDate = new GregorianCalendar();
      addDate.add(Calendar.MONTH, Constantes.FARM_MESES_DE_ANTECEDENCIA);

      return item.getDataValidade().before(dataHoje);
   }

   public static String getValidadeItemColor(FarmLoteProduto item)
   {
      String cor;
      Date dataHoje = new Date();

      GregorianCalendar addDate = new GregorianCalendar();
      addDate.add(Calendar.MONTH, Constantes.FARM_MESES_DE_ANTECEDENCIA);
      Date dateIntermediaria = addDate.getTime();

      //verde: > se a data de expiracao eh superior a um mes depois de hoje
      //laranja: >= se a data de expiracao eh superior a hoje mas inferior  a um mes depois de hoje
      //vermelho: >= se a data de expiracao eh menor que hoje
      if (item.getDataValidade().before(dataHoje))
         cor = "RED";
      else if (item.getDataValidade().after(dataHoje) && item.getDataValidade().before(dateIntermediaria/*um mes depois de hojedataHoje*//*um mes depois de hojedataHoje*/))
         cor = "ORANGE";
      else
         cor = "GREEN";

      return cor;
   }

   public FarmLocalArmazenamento getInstancialocal()
   {
      FarmLocalArmazenamento local = new FarmLocalArmazenamento();
      local.setFkIdInstituicao(new GrlInstituicao());
      local.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      return local;
   }

   public List<FarmLoteProdutoHasLocalArmazenamento> getQuantEmLocais(FarmLoteProduto item)
   {
      return loteLocalFacade.findLoteProdutoNoLocalComQtdPositiva(getInstancialocal(), item);
   }

   public FarmLoteProdutoFacade getLoteFacade()
   {
      return loteFacade;
   }

   public void setLoteFacade(FarmLoteProdutoFacade loteFacade)
   {
      this.loteFacade = loteFacade;
   }

   public Date getDataValidadeInicio()
   {
      return dataValidadeInicio;
   }

   public void setDataValidadeInicio(Date dataValidadeInicio)
   {
      this.dataValidadeInicio = dataValidadeInicio;
   }

   public Date getDataValidadeFim()
   {
      return dataValidadeFim;
   }

   public void setDataValidadeFim(Date dataValidadeFim)
   {
      dataValidadeFim = getEndOfDay(dataValidadeFim);
      this.dataValidadeFim = dataValidadeFim;
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
      return date;
   }

   public FarmLoteProduto getLoteProdutoPesquisa()
   {
      if (loteProdutoPesquisa == null)
         loteProdutoPesquisa = getInstanciaLoteProdutoPesquisa();
      return loteProdutoPesquisa;
   }

   public void setLoteProdutoPesquisa(FarmLoteProduto loteProdutoPesquisa)
   {
      this.loteProdutoPesquisa = loteProdutoPesquisa;
   }

   public List<FarmLoteProduto> getLotesDeProdutosPesquisados()
   {
      return lotesDeProdutosPesquisados;
   }

   public void setLotesDeProdutosPesquisados(List<FarmLoteProduto> lotesDeProdutosPesquisados)
   {
      this.lotesDeProdutosPesquisados = lotesDeProdutosPesquisados;
   }

}
