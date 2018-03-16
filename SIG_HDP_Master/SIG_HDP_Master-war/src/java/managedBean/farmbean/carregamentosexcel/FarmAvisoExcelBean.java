/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmAviso;
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
import sessao.FarmAvisoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmAvisoExcelBean implements Serializable
{

   @EJB
   private FarmAvisoFacade farmAvisoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmAvisoExcelBean
    */
   public FarmAvisoExcelBean()
   {
   }

   public static FarmAvisoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmAvisoExcelBean farmAvisoExcelBean
              = (FarmAvisoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmAvisoExcelBean");

      return farmAvisoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarAvisoTabela()
   {
      Date dataAvisoTabela = this.farmUpdatesFacade.dataAviso();

      Date dataAvisoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_AVISO_FARM);

      if (this.farmAvisoFacade.isAvisoTabelaEmpty() || dataAvisoTabela == null);
      else if (!farmAvisoFacade.isAvisoTabelaEmpty() && (dataAvisoXLSFile != null && dataAvisoXLSFile.compareTo(dataAvisoTabela) <= 0))
         return;

      if (lerAvisoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoAvisoTabela();
      }
   }

   public boolean lerAvisoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_AVISO_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("avisos");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmAviso reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverAvisoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverAvisoTabela(FarmAviso reg)
   {
      if (farmAvisoFacade.existeRegisto(reg.getPkIdAviso()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmAviso lerCampos(HSSFRow row)
   {
      int pk_id_aviso;
      String descricao;

      final int PK_ID_AVISO = 0;
      final int DESCRICAO = 1;

      FarmAviso reg = new FarmAviso();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_AVISO:
               pk_id_aviso = (int) cell.getNumericCellValue();
               reg.setPkIdAviso(pk_id_aviso);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmAviso reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmAvisoFacade.create(reg);
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

   public boolean editRegister(FarmAviso reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmAvisoFacade.edit(reg);
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
