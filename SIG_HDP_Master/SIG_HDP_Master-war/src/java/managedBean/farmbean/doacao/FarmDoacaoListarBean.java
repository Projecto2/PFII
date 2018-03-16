/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.doacao;

import entidade.FarmDoacao;
import entidade.FarmDoacaoHasLoteProduto;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.GrlInstituicao;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.FarmDoacaoFacade;
import sessao.FarmDoacaoHasLoteProdutoFacade;
import util.Mensagem;
import util.RelatorioJasper;
import util.relatorio.ConexaoPostgresSQL;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmDoacaoListarBean implements Serializable
{

   @EJB
   private FarmDoacaoFacade doacaoFacade;
   @EJB
   private FarmDoacaoHasLoteProdutoFacade doacaoHasProdutoFacade;

   private FarmDoacao doacao;
   private FarmDoacaoHasLoteProduto doacaoHasProduto;
   private List<FarmDoacaoHasLoteProduto> listaItensDoados;

   private Date dataInicio, dataFim;

   /**
    * Creates a new instance of FarmDoacaoListarBean
    */
   public FarmDoacaoListarBean()
   {
   }

   public FarmDoacao getInstanciaDoacao()
   {
      FarmDoacao doacaoAux = new FarmDoacao();
      doacaoAux.setFkIdFuncionario(new RhFuncionario());
      doacaoAux.setFkIdInstituicao(new GrlInstituicao());
      doacaoAux.setFkIdLocalArmazenamento(new FarmLocalArmazenamento());
      return doacaoAux;
   }

   public FarmDoacaoHasLoteProduto getInstanciaDoacaoHasLoteProduto()
   {
      FarmDoacaoHasLoteProduto aux = new FarmDoacaoHasLoteProduto();
      aux.setFkIdDoacao(new FarmDoacao());
      aux.setFkIdLoteProduto(new FarmLoteProduto());
      aux.setQuantidade(1);
      return aux;
   }

   public void pesquisarDoacoes()
   {
      listaItensDoados = doacaoHasProdutoFacade.findDoacaoHasLoteProduto(doacaoHasProduto, dataInicio, dataFim);
      if (listaItensDoados.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. "
                 + listaItensDoados.size() + " registo(s) retornado(s).");

   }

   public FarmDoacao getDoacao()
   {
      if (doacao == null)
         doacao = getInstanciaDoacao();
      return doacao;
   }

   public void setDoacao(FarmDoacao doacao)
   {
      this.doacao = doacao;
   }

   public FarmDoacaoHasLoteProduto getDoacaoHasProduto()
   {
      if (doacaoHasProduto == null)
         doacaoHasProduto = getInstanciaDoacaoHasLoteProduto();
      return doacaoHasProduto;
   }

   public void setDoacaoHasProduto(FarmDoacaoHasLoteProduto doacaoHasProduto)
   {
      this.doacaoHasProduto = doacaoHasProduto;
   }

   public List<FarmDoacaoHasLoteProduto> getListaItensDoados()
   {
      return listaItensDoados;
   }

   public void setListaItensDoados(List<FarmDoacaoHasLoteProduto> ListaItensDoados)
   {
      this.listaItensDoados = ListaItensDoados;
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

   public Date getHoje()
   {
      return new Date();
   }
   
   public void imprimirRelatorio()
   {
      ConexaoPostgresSQL conexaoPostgresSQL = new ConexaoPostgresSQL();
      Connection conn = conexaoPostgresSQL.getConnection();
      HashMap<String, Object> parametrosMap = new HashMap<>();
      parametrosMap.put("REPORT_CONNECTION", conn);
      RelatorioJasper.exportPDF("farm/doacao.jasper", parametrosMap, listaItensDoados);
      dataInicio = null;
   }
}
