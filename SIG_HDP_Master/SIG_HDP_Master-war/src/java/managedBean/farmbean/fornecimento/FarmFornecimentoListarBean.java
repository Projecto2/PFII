/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.fornecimento;

import entidade.FarmFornecimento;
import entidade.FarmTipoFornecimento;
import entidade.GrlFornecedor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.FarmFornecimentoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmFornecimentoListarBean implements Serializable
{

   @EJB
   private FarmFornecimentoFacade fornecimentoFacade;

   private FarmFornecimento fornecimento;
   private Date dataInicio, dataFim;
   private List<FarmFornecimento> listaFornecimentos;

   /**
    * Creates a new instance of fornecimentoListarBean
    */
   public FarmFornecimentoListarBean()
   {
      limparCampos();
   }

   public FarmFornecimento getInstanciaFornecimento()
   {
      setFornecimento(new FarmFornecimento());
//      getFornecimento().setFkIdArmazemDestino(new FarmLocalArmazenamento());
      getFornecimento().setFkIdFornecedor(new GrlFornecedor());
      getFornecimento().setFkIdTipoFornecimento(new FarmTipoFornecimento());
      return getFornecimento();
   }

   public void pesquisarFornecimentos()
   {
      setListaFornecimentos(fornecimentoFacade.findFornecimento(getFornecimento(), getDataInicio(), getDataFim()));
      if (getListaFornecimentos().isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getListaFornecimentos().size() + " registo(s) retornado(s).");
   }

   public String novoFornecimento()
   {
      return "fornecimentoNovo.xhtml?faces-redirect=true";
   }

   public void limparCampos()
   {
      listaFornecimentos = new ArrayList<>();
      fornecimento = getInstanciaFornecimento();
      dataFim = null;
      dataInicio = null;
   }

   /**
    * @return the fornecimento
    */
   public FarmFornecimento getFornecimento()
   {
      return fornecimento;
   }

   /**
    * @param fornecimento the fornecimento to set
    */
   public void setFornecimento(FarmFornecimento fornecimento)
   {
      this.fornecimento = fornecimento;
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

   /**
    * @return the listaFornecimentos
    */
   public List<FarmFornecimento> getListaFornecimentos()
   {
      return listaFornecimentos;
   }

   /**
    * @param listaFornecimentos the listaFornecimentos to set
    */
   public void setListaFornecimentos(List<FarmFornecimento> listaFornecimentos)
   {
      this.listaFornecimentos = listaFornecimentos;
   }

   public Date getHoje()
   {
      return new Date();
   }
}
