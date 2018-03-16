/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.quarentena;

import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmQuarentena;
import entidade.RhFuncionario;
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
import sessao.FarmQuarentenaFacade;
import util.Mensagem;
import util.RelatorioJasper;
import util.relatorio.ConexaoPostgresSQL;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmQuarentenaListarBean implements Serializable
{

   @EJB
   private FarmQuarentenaFacade quarentenaFacade;

   private List<FarmQuarentena> quarentenasPesquisadas;
   private FarmQuarentena quarentenaPesquisar;
   private Date dataInicio, dataFim;
   private ConexaoPostgresSQL conexaoPostgresSQL;
   /**
    * Creates a new instance of FarmQuarentenaListarBean
    */
   public FarmQuarentenaListarBean()
   {
   }

   public void pesquisarQuarentenas()
   {
      quarentenasPesquisadas = quarentenaFacade.findQuarentena(quarentenaPesquisar, dataInicio, dataFim);
      if (quarentenasPesquisadas.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. "
                 + quarentenasPesquisadas.size() + " registo(s) retornado(s).");
   }

   public FarmQuarentena getInstanciaQuarentena()
   {
      FarmQuarentena quarentenaAux = new FarmQuarentena();

      quarentenaAux.setFkIdFuncionario(new RhFuncionario());
      quarentenaAux.setFkIdLocalOrigem(new FarmLocalArmazenamento());
      quarentenaAux.setFkIdLoteProduto(new FarmLoteProduto());
      return quarentenaAux;
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

   public List<FarmQuarentena> getQuarentenasPesquisadas()
   {
      if (quarentenasPesquisadas == null)
         quarentenasPesquisadas = new ArrayList<>();
      return quarentenasPesquisadas;
   }

   public void setQuarentenasPesquisadas(List<FarmQuarentena> quarentenasPesquisadas)
   {
      this.quarentenasPesquisadas = quarentenasPesquisadas;
   }

   public FarmQuarentena getQuarentenaPesquisar()
   {
      if (quarentenaPesquisar == null)
         quarentenaPesquisar = getInstanciaQuarentena();
      return quarentenaPesquisar;
   }

   public void setQuarentenaPesquisar(FarmQuarentena quarentenaPesquisar)
   {
      this.quarentenaPesquisar = quarentenaPesquisar;
   }

   public Date getDataInicio()
   {
      return dataInicio;
   }

   public void setDataInicio(Date dataInicio)
   {
      this.dataInicio = dataInicio;
   }

   public Date getHoje()
   {
      return new Date();
   }

   public Date getMenorData()
   {
      Calendar calendar = Calendar.getInstance();
      calendar.set(0001, 01, 01);
      return calendar.getTime();
   }
   
  public void imprimirRelatorio()
   {
      conexaoPostgresSQL = new ConexaoPostgresSQL();
      Connection conn = conexaoPostgresSQL.getConnection();
      HashMap<String, Object> parametrosMap = new HashMap<>();
//      
//      if(dataInicio == null)
//         dataInicio = getMenorData();
      
      parametrosMap.put("REPORT_CONNECTION", conn);
//      parametrosMap.put("dataInicial", new SimpleDateFormat("dd-MM-yyyy").format(dataInicio));
//      parametrosMap.put("dataFinal",  new SimpleDateFormat("dd-MM-yyyy").format(dataFim));
//      
      RelatorioJasper.exportPDF("farm/quarentena.jasper", parametrosMap, quarentenasPesquisadas);
      dataInicio = null;
   }
}
