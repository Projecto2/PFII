/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmTipoQuantidade;
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
import sessao.FarmTipoQuantidadeFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmTipoQuantidadeExcelBean implements Serializable
{

   @EJB
   private FarmTipoQuantidadeFacade farmTipoQuantidadeFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmTipoQuantidadeExcelBean
    */
   public FarmTipoQuantidadeExcelBean()
   {
   }

   public static FarmTipoQuantidadeExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmTipoQuantidadeExcelBean farmTipoQuantidadeExcelBean
              = (FarmTipoQuantidadeExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmTipoQuantidadeExcelBean");

      return farmTipoQuantidadeExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarTipoQuantidadeTabela()
   {
      Date dataTipoQuantidadeTabela = this.farmUpdatesFacade.dataTipoQuantidade();

      Date dataTipoQuantidadeXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_TIPO_QUANTIDADE_FARM);

      if (this.farmTipoQuantidadeFacade.isTipoQuantidadeTabelaEmpty() || dataTipoQuantidadeTabela == null);
      else if (!farmTipoQuantidadeFacade.isTipoQuantidadeTabelaEmpty() && (dataTipoQuantidadeXLSFile != null && dataTipoQuantidadeXLSFile.compareTo(dataTipoQuantidadeTabela) <= 0))
         return;

      if (lerTipoQuantidadeTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoTipoQuantidadeTabela();
      }
   }

   public boolean lerTipoQuantidadeTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_TIPO_QUANTIDADE_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("tiposDeQuantidade");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmTipoQuantidade reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverTipoQuantidadeTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverTipoQuantidadeTabela(FarmTipoQuantidade reg)
   {
      if (reg.getPkIdTipoQuantidade() != null)
      {
         if (farmTipoQuantidadeFacade.existeRegisto(reg.getPkIdTipoQuantidade()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }
   }

   public FarmTipoQuantidade lerCampos(HSSFRow row)
   {
      int pk_id_tipo_quantidade;
      String descricao;

      final int PK_ID_TIPO_QUANTIDADE = 0;
      final int DESCRICAO = 1;

      FarmTipoQuantidade reg = new FarmTipoQuantidade();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_TIPO_QUANTIDADE:
               pk_id_tipo_quantidade = (int) cell.getNumericCellValue();
               reg.setPkIdTipoQuantidade(pk_id_tipo_quantidade);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmTipoQuantidade reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoQuantidadeFacade.create(reg);
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

   public boolean editRegister(FarmTipoQuantidade reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoQuantidadeFacade.edit(reg);
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
