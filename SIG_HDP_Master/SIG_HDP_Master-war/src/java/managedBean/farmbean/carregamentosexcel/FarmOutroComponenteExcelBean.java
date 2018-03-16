/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmOutroComponente;
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
import sessao.FarmOutroComponenteFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmOutroComponenteExcelBean implements Serializable
{

   @EJB
   private FarmOutroComponenteFacade farmOutroComponenteFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmOutroComponenteExcelBean
    */
   public FarmOutroComponenteExcelBean()
   {
   }

   public static FarmOutroComponenteExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmOutroComponenteExcelBean farmOutroComponenteExcelBean
              = (FarmOutroComponenteExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmOutroComponenteExcelBean");

      return farmOutroComponenteExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarOutroComponenteTabela()
   {
      Date dataOutroComponenteTabela = this.farmUpdatesFacade.dataOutroComponente();

      Date dataOutroComponenteXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_OUTRO_COMPONENTE_FARM);

      if (this.farmOutroComponenteFacade.isOutroComponenteTabelaEmpty() || dataOutroComponenteTabela == null);
      else if (!farmOutroComponenteFacade.isOutroComponenteTabelaEmpty() && (dataOutroComponenteXLSFile != null && dataOutroComponenteXLSFile.compareTo(dataOutroComponenteTabela) <= 0))
         return;

      if (lerOutroComponenteTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoOutroComponenteTabela();
      }
   }

   public boolean lerOutroComponenteTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_OUTRO_COMPONENTE_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("outrosComponentes");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmOutroComponente reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverOutroComponenteTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverOutroComponenteTabela(FarmOutroComponente reg)
   {
      if (reg.getPkIdComponente() != null)
      {
         if (farmOutroComponenteFacade.existeRegisto(reg.getPkIdComponente()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }
   }

   public FarmOutroComponente lerCampos(HSSFRow row)
   {
      int pk_id_componente;
      String descricao;

      final int PK_ID_COMPONENTE = 0;
      final int DESCRICAO = 1;

      FarmOutroComponente reg = new FarmOutroComponente();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_COMPONENTE:
               pk_id_componente = (int) cell.getNumericCellValue();
               reg.setPkIdComponente(pk_id_componente);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricaoComponente(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmOutroComponente reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmOutroComponenteFacade.create(reg);
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

   public boolean editRegister(FarmOutroComponente reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmOutroComponenteFacade.edit(reg);
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
