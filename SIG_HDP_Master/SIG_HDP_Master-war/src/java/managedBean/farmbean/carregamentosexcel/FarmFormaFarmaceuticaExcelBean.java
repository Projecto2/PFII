/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmFormaFarmaceutica;
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
import sessao.FarmFormaFarmaceuticaFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmFormaFarmaceuticaExcelBean implements Serializable
{

   @EJB
   private FarmFormaFarmaceuticaFacade farmFormaFarmaceuticaFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmFormaFarmaceuticaExcelBean
    */
   public FarmFormaFarmaceuticaExcelBean()
   {
   }

   public static FarmFormaFarmaceuticaExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmFormaFarmaceuticaExcelBean farmFormaFarmaceuticaExcelBean
              = (FarmFormaFarmaceuticaExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmFormaFarmaceuticaExcelBean");

      return farmFormaFarmaceuticaExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarFormaFarmaceuticaTabela()
   {
      Date dataFormaFarmaceuticaTabela = this.farmUpdatesFacade.dataFormaFarmaceutica();

      Date dataFormaFarmaceuticaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_FORMA_FARMACEUTICA_FARM);

      if (this.farmFormaFarmaceuticaFacade.isFormaFarmaceuticaTabelaEmpty() || dataFormaFarmaceuticaTabela == null);
      else if (!farmFormaFarmaceuticaFacade.isFormaFarmaceuticaTabelaEmpty() && (dataFormaFarmaceuticaXLSFile != null && dataFormaFarmaceuticaXLSFile.compareTo(dataFormaFarmaceuticaTabela) <= 0))
         return;

      if (lerFormaFarmaceuticaTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoFormaFarmaceuticaTabela();
      }
   }

   public boolean lerFormaFarmaceuticaTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_FORMA_FARMACEUTICA_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("formasFarmaceuticas");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmFormaFarmaceutica reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverFormaFarmaceuticaTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverFormaFarmaceuticaTabela(FarmFormaFarmaceutica reg)
   {
      if (farmFormaFarmaceuticaFacade.existeRegisto(reg.getPkIdFormaFarmaceutica()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmFormaFarmaceutica lerCampos(HSSFRow row)
   {
      int pk_id_forma_farmaceutica;
      String descricao;

      final int PK_ID_FORMA_FARMACEUTICA = 0;
      final int DESCRICAO = 1;

      FarmFormaFarmaceutica reg = new FarmFormaFarmaceutica();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_FORMA_FARMACEUTICA:
               pk_id_forma_farmaceutica = (int) cell.getNumericCellValue();
               reg.setPkIdFormaFarmaceutica(pk_id_forma_farmaceutica);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmFormaFarmaceutica reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmFormaFarmaceuticaFacade.create(reg);
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

   public boolean editRegister(FarmFormaFarmaceutica reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmFormaFarmaceuticaFacade.edit(reg);
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
