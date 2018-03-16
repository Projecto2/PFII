/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmTipoUnidadeMedida;
import entidade.FarmUnidadeMedida;
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
import sessao.FarmUnidadeMedidaFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmUnidadeDeMedidaExcelBean implements Serializable
{

   @EJB
   private FarmUnidadeMedidaFacade farmUnidadeDeMedidaFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmUnidadeDeMedidaExcelBean
    */
   public FarmUnidadeDeMedidaExcelBean()
   {
   }

   public static FarmUnidadeDeMedidaExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmUnidadeDeMedidaExcelBean farmUnidadeDeMedidaExcelBean
              = (FarmUnidadeDeMedidaExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmUnidadeDeMedidaExcelBean");

      return farmUnidadeDeMedidaExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarUnidadeDeMedidaTabela()
   {
      Date dataUnidadeDeMedidaTabela = this.farmUpdatesFacade.dataUnidadeDeMedida();

      Date dataUnidadeDeMedidaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_UNIDADE_MEDIDA_FARM);

      if (this.farmUnidadeDeMedidaFacade.isUnidadeMedidaTabelaEmpty() || dataUnidadeDeMedidaTabela == null);
      else if (!farmUnidadeDeMedidaFacade.isUnidadeMedidaTabelaEmpty() && (dataUnidadeDeMedidaXLSFile != null && dataUnidadeDeMedidaXLSFile.compareTo(dataUnidadeDeMedidaTabela) <= 0))
         return;

      if (lerUnidadeDeMedidaTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoUnidadeDeMedidaTabela();
      }
   }

   public boolean lerUnidadeDeMedidaTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_UNIDADE_MEDIDA_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("UnidadesDeMedida");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmUnidadeMedida reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverUnidadeDeMedidaTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverUnidadeDeMedidaTabela(FarmUnidadeMedida reg)
   {
      if (reg.getPkIdUnidadeMedida() != null)
      {
         if (farmUnidadeDeMedidaFacade.existeRegisto(reg.getPkIdUnidadeMedida()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }
   }

   public FarmUnidadeMedida lerCampos(HSSFRow row)
   {
      int pk_id_UnidadeMedida;
      int fk_id_tipo_unidade_medida;
      String descricao;
      String abreviatura;

      final int PK_ID_UNIDADE_MEDIDA = 0;
      final int ABREVIATURA = 1;
      final int DESCRICAO = 2;
      final int FK_ID_TIPO_UNIDADE_MEDIDA = 3;

      FarmUnidadeMedida reg = new FarmUnidadeMedida();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_UNIDADE_MEDIDA:
               pk_id_UnidadeMedida = (int) cell.getNumericCellValue();
               reg.setPkIdUnidadeMedida(pk_id_UnidadeMedida);
               break;

            case ABREVIATURA:
               abreviatura = cell.getStringCellValue().trim();
               reg.setAbreviatura(abreviatura);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;

            case FK_ID_TIPO_UNIDADE_MEDIDA:
               if (cell == null)
               {
                  reg.setFkIdTipoUnidadeMedida(null);
               }
               else
               {
                  fk_id_tipo_unidade_medida = (int) cell.getNumericCellValue();
                  reg.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida(fk_id_tipo_unidade_medida));
               }
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmUnidadeMedida reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmUnidadeDeMedidaFacade.create(reg);
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

   public boolean editRegister(FarmUnidadeMedida reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmUnidadeDeMedidaFacade.edit(reg);
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
