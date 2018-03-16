/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmTipoLocalArmazenamento;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.FarmTipoLocalArmazenamentoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmTipoLocalArmazenamentoExcelBean implements Serializable
{

   @EJB
   private FarmTipoLocalArmazenamentoFacade farmTipoLocalArmazenamentoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmTipoLocalArmazenamentoExcelBean
    */
   public FarmTipoLocalArmazenamentoExcelBean()
   {
   }

   public static FarmTipoLocalArmazenamentoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmTipoLocalArmazenamentoExcelBean farmTipoLocalArmazenamentoExcelBean
              = (FarmTipoLocalArmazenamentoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmTipoLocalArmazenamentoExcelBean");

      return farmTipoLocalArmazenamentoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarTipoLocalArmazenamentoTabela()
   {
      Date dataTipoLocalArmazenamentoTabela = this.farmUpdatesFacade.dataTipoUnidadeDeMedida();

      Date dataTipoLocalArmazenamentoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_TIPO_LOCAL_ARMAZENAMENTO_FARM);

      if (this.farmTipoLocalArmazenamentoFacade.isTipoLocalArmazenamentoTabelaEmpty() || dataTipoLocalArmazenamentoTabela == null);
      else if (!farmTipoLocalArmazenamentoFacade.isTipoLocalArmazenamentoTabelaEmpty() && (dataTipoLocalArmazenamentoXLSFile != null && dataTipoLocalArmazenamentoXLSFile.compareTo(dataTipoLocalArmazenamentoTabela) <= 0))
         return;

      if (lerTipoLocalArmazenamentoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoTipoLocalArmazenamentoTabela();
      }
   }

   public boolean lerTipoLocalArmazenamentoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_TIPO_LOCAL_ARMAZENAMENTO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("tiposDeLocalDeArmazenamento");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmTipoLocalArmazenamento reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverTipoLocalArmazenamentoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverTipoLocalArmazenamentoTabela(FarmTipoLocalArmazenamento reg)
   {
      if (reg.getPkIdTipoLocalArmazenamento() != null)
      {
         if (farmTipoLocalArmazenamentoFacade.existeRegisto(reg.getPkIdTipoLocalArmazenamento()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }
   }

   public FarmTipoLocalArmazenamento lerCampos(HSSFRow row)
   {
      int pk_id_tipo_local_armazenamento;
      String descricao;

      final int PK_ID_TIPO_LOCAL_ARMAZENAMENTO = 0;
      final int DESCRICAO = 1;

      FarmTipoLocalArmazenamento reg = new FarmTipoLocalArmazenamento();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_TIPO_LOCAL_ARMAZENAMENTO:
               pk_id_tipo_local_armazenamento = (int) cell.getNumericCellValue();
               reg.setPkIdTipoLocalArmazenamento(pk_id_tipo_local_armazenamento);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmTipoLocalArmazenamento reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoLocalArmazenamentoFacade.create(reg);
         this.userTransaction.commit();
         return true;
      }
      catch (Exception e)
      {
         try
         {
            this.userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            System.err.println("Roolback: " + ex.toString());
         }
      }
      return false;
   }

   public boolean editRegister(FarmTipoLocalArmazenamento reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoLocalArmazenamentoFacade.edit(reg);
         this.userTransaction.commit();
         return true;
      }
      catch (Exception e)
      {
         try
         {
            this.userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            System.err.println("Roolback: " + ex.toString());
         }
      }
      return false;
   }
}
