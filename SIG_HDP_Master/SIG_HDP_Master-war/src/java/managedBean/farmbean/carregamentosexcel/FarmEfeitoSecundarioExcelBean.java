/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmEfeitoSecundario;
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
import sessao.FarmEfeitoSecundarioFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmEfeitoSecundarioExcelBean implements Serializable
{

   @EJB
   private FarmEfeitoSecundarioFacade farmEfeitoSecundarioFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmEfeitoSecundarioExcelBean
    */
   public FarmEfeitoSecundarioExcelBean()
   {
   }

   public static FarmEfeitoSecundarioExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmEfeitoSecundarioExcelBean farmEfeitoSecundarioExcelBean
              = (FarmEfeitoSecundarioExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmEfeitoSecundarioExcelBean");

      return farmEfeitoSecundarioExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarEfeitoSecundarioTabela()
   {
      Date dataEfeitoSecundarioTabela = this.farmUpdatesFacade.dataEfeitoSecundario();

      Date dataEfeitoSecundarioXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_EFEITO_SECUNDARIO_FARM);

      if (this.farmEfeitoSecundarioFacade.isEfeitoSecundarioTabelaEmpty() || dataEfeitoSecundarioTabela == null);
      else if (!farmEfeitoSecundarioFacade.isEfeitoSecundarioTabelaEmpty() && (dataEfeitoSecundarioXLSFile != null && dataEfeitoSecundarioXLSFile.compareTo(dataEfeitoSecundarioTabela) <= 0))
         return;

      if (lerEfeitoSecundarioTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoEfeitoSecundarioTabela();
      }
   }

   public boolean lerEfeitoSecundarioTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_EFEITO_SECUNDARIO_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("efeitosSecundarios");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmEfeitoSecundario reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverEfeitoSecundarioTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverEfeitoSecundarioTabela(FarmEfeitoSecundario reg)
   {
      if (farmEfeitoSecundarioFacade.existeRegisto(reg.getPkIdEfeitoSecundario()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmEfeitoSecundario lerCampos(HSSFRow row)
   {
      int pk_id_efeito_secundario;
      String descricao;

      final int PK_ID_EFEITO_SECUNDARIO = 0;
      final int DESCRICAO = 1;

      FarmEfeitoSecundario reg = new FarmEfeitoSecundario();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_EFEITO_SECUNDARIO:
               pk_id_efeito_secundario = (int) cell.getNumericCellValue();
               reg.setPkIdEfeitoSecundario(pk_id_efeito_secundario);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricaoEfeitoSecundario(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmEfeitoSecundario reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmEfeitoSecundarioFacade.create(reg);
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

   public boolean editRegister(FarmEfeitoSecundario reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmEfeitoSecundarioFacade.edit(reg);
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
