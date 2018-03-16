/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmFarmaco;
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
import sessao.FarmFarmacoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmFarmacoExcelBean implements Serializable
{

   @EJB
   private FarmFarmacoFacade farmFarmacoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmFarmacoExcelBean
    */
   public FarmFarmacoExcelBean()
   {
   }

   public static FarmFarmacoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmFarmacoExcelBean farmFarmacoExcelBean
              = (FarmFarmacoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmFarmacoExcelBean");

      return farmFarmacoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarFarmacoTabela()
   {
      Date dataFarmacoTabela = this.farmUpdatesFacade.dataFarmaco();

      Date dataFarmacoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_FARMACO_FARM);

      if (this.farmFarmacoFacade.isFarmacoTabelaEmpty() || dataFarmacoTabela == null);
      else if (!farmFarmacoFacade.isFarmacoTabelaEmpty() && (dataFarmacoXLSFile != null && dataFarmacoXLSFile.compareTo(dataFarmacoTabela) <= 0))
         return;

      if (lerFarmacoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoFarmacoTabela();
      }
   }

   public boolean lerFarmacoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_FARMACO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("farmacos");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmFarmaco reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverFarmacoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverFarmacoTabela(FarmFarmaco reg)
   {
      if (reg.getPkIdFarmaco() != null)
      {
         if (farmFarmacoFacade.existeRegisto(reg.getPkIdFarmaco()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }
   }

   public FarmFarmaco lerCampos(HSSFRow row)
   {
      int pk_id_farmaco;
      String descricao;

      final int PK_ID_FARMACO = 0;
      final int DESCRICAO = 1;

      FarmFarmaco reg = new FarmFarmaco();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_FARMACO:
               pk_id_farmaco = (int) cell.getNumericCellValue();
               reg.setPkIdFarmaco(pk_id_farmaco);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmFarmaco reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmFarmacoFacade.create(reg);
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

   public boolean editRegister(FarmFarmaco reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmFarmacoFacade.edit(reg);
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
