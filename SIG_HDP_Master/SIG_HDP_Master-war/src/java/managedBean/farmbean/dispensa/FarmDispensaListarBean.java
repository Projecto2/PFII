/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.dispensa;

import entidade.AdmsPaciente;
import entidade.FarmDispensa;
import entidade.FarmLocalArmazenamento;
import entidade.FarmTurno;
import entidade.FarmTurnoDispensa;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import managedBean.segbean.SegcontroloSessaoBean;
import sessao.FarmDispensaFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmDispensaListarBean implements Serializable
{

   @EJB
   private FarmDispensaFacade dispensaFacade;

   private FarmDispensa dispensaPesquisa;
   private List<FarmDispensa> dispensasPesquisadas;
   private Date dataInicio, dataFim;

   /**
    * Creates a new instance of FarmDispensaListarBean
    */
   public FarmDispensaListarBean()
   {
   }

   public FarmDispensa getInstanciaDispensa()
   {
      new SegcontroloSessaoBean().validarSessao();
      FarmDispensa dispensa = new FarmDispensa();
      dispensa.setFkIdPaciente(new AdmsPaciente());

      FarmTurnoDispensa turnoDispensa = new FarmTurnoDispensa();
      turnoDispensa.setFkIdTurno(new FarmTurno());
      turnoDispensa.setFkIdLocalDeAtendimento(new FarmLocalArmazenamento());

      dispensa.setFkIdTurnoDispensa(turnoDispensa);
      dispensa.setFkIdFuncionario(new RhFuncionario());
      return dispensa;
   }

   public void pesquisarDispensa()
   {
      System.out.println("pesquisar");
      dispensasPesquisadas = dispensaFacade.findDispensa(dispensaPesquisa, dataInicio, dataFim);
      if (dispensasPesquisadas.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + dispensasPesquisadas.size() + " registo(s) retornado(s).");
   }

   /**
    * @return the dispensa
    */
   public FarmDispensa getDispensa()
   {
      if (dispensaPesquisa == null)
         dispensaPesquisa = getInstanciaDispensa();
      return dispensaPesquisa;
   }

   /**
    * @param dispensa the dispensa to set
    */
   public void setDispensa(FarmDispensa dispensa)
   {
      this.dispensaPesquisa = dispensa;
   }

   /**
    * @return the dispensasPesquisadas
    */
   public List<FarmDispensa> getDispensasPesquisadas()
   {
      if (dispensasPesquisadas == null)
         dispensasPesquisadas = new ArrayList<>();

      return dispensasPesquisadas;
   }

   /**
    * @param dispensasPesquisadas the dispensasPesquisadas to set
    */
   public void setDispensasPesquisadas(List<FarmDispensa> dispensasPesquisadas)
   {
      this.dispensasPesquisadas = dispensasPesquisadas;
   }

   /**
    * @return the dataInicio
    */
   public Date getDataInicio()
   {
      return dataInicio;
   }

   /**
    * @param dataInicio the dataInicio to set
    */
   public void setDataInicio(Date dataInicio)
   {
      this.dataInicio = dataInicio;
   }

   /**
    * @return the dataFim
    */
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
}
