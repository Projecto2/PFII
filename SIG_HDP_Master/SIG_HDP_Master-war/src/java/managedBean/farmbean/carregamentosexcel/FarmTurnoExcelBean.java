/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmTurno;
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
import sessao.FarmTurnoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmTurnoExcelBean implements Serializable
{

   @EJB
   private FarmTurnoFacade farmTurnoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmTurnoExcelBean
    */
   public FarmTurnoExcelBean()
   {
   }

   public static FarmTurnoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmTurnoExcelBean farmTurnoExcelBean
              = (FarmTurnoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmTurnoExcelBean");

      return farmTurnoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarTurnoTabela()
   {
      Date dataTurnoTabela = this.farmUpdatesFacade.dataTurno();

      Date dataTurnoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_TURNO_FARM);

      if (this.farmTurnoFacade.isTurnoTabelaEmpty() || dataTurnoTabela == null);
      else if (!farmTurnoFacade.isTurnoTabelaEmpty() && (dataTurnoXLSFile != null && dataTurnoXLSFile.compareTo(dataTurnoTabela) <= 0))
         return;

      if (lerTurnoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoTurnoTabela();
      }
   }

   public boolean lerTurnoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_TURNO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("turnos");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmTurno reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverTurnoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverTurnoTabela(FarmTurno reg)
   {
      if (reg.getPkIdTurno() != null)
      {
         if (farmTurnoFacade.existeRegisto(reg.getPkIdTurno()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }
   }

   public FarmTurno lerCampos(HSSFRow row)
   {
      int pk_id_turno;
      String descricao;

      final int PK_ID_TURNO = 0;
      final int DESCRICAO = 1;

      FarmTurno reg = new FarmTurno();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_TURNO:
               pk_id_turno = (int) cell.getNumericCellValue();
               reg.setPkIdTurno(pk_id_turno);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmTurno reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTurnoFacade.create(reg);
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

   public boolean editRegister(FarmTurno reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTurnoFacade.edit(reg);
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
