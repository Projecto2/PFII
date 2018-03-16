/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmTipoUnidadeMedida;
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
import sessao.FarmTipoUnidadeMedidaFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmTipoUnidadeMedidaExcelBean implements Serializable
{

   @EJB
   private FarmTipoUnidadeMedidaFacade farmTipoUnidadeMedidaFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmTipoUnidadeMedidaExcelBean
    */
   public FarmTipoUnidadeMedidaExcelBean()
   {
   }

   public static FarmTipoUnidadeMedidaExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmTipoUnidadeMedidaExcelBean farmTipoUnidadeMedidaExcelBean
              = (FarmTipoUnidadeMedidaExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmTipoUnidadeMedidaExcelBean");

      return farmTipoUnidadeMedidaExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarTipoUnidadeMedidaTabela()
   {
      Date dataTipoUnidadeMedidaTabela = this.farmUpdatesFacade.dataTipoUnidadeDeMedida();

      Date dataTipoUnidadeMedidaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_TIPO_UNIDADE_MEDIDA_FARM);

      if (this.farmTipoUnidadeMedidaFacade.isTipoUnidadeMedidaTabelaEmpty() || dataTipoUnidadeMedidaTabela == null);
      else if (!farmTipoUnidadeMedidaFacade.isTipoUnidadeMedidaTabelaEmpty() && (dataTipoUnidadeMedidaXLSFile != null && dataTipoUnidadeMedidaXLSFile.compareTo(dataTipoUnidadeMedidaTabela) <= 0))
         return;

      if (lerTipoUnidadeMedidaTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoTipoUnidadeDeMedidaTabela();
      }
   }

   public boolean lerTipoUnidadeMedidaTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_TIPO_UNIDADE_MEDIDA_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("tiposDeUnidadesDeMedida");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmTipoUnidadeMedida reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverTipoUnidadeMedidaTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverTipoUnidadeMedidaTabela(FarmTipoUnidadeMedida reg)
   {
      if (reg.getPkIdTipoUnidadeMedida() != null)
      {
         if (farmTipoUnidadeMedidaFacade.existeRegisto(reg.getPkIdTipoUnidadeMedida()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }
   }

   public FarmTipoUnidadeMedida lerCampos(HSSFRow row)
   {
      int pk_id_tipo_unidade_medida;
      String descricao;

      final int PK_ID_TIPO_UNIDADE_MEDIDA = 0;
      final int DESCRICAO = 1;

      FarmTipoUnidadeMedida reg = new FarmTipoUnidadeMedida();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_TIPO_UNIDADE_MEDIDA:
               pk_id_tipo_unidade_medida = (int) cell.getNumericCellValue();
               reg.setPkIdTipoUnidadeMedida(pk_id_tipo_unidade_medida);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmTipoUnidadeMedida reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoUnidadeMedidaFacade.create(reg);
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

   public boolean editRegister(FarmTipoUnidadeMedida reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoUnidadeMedidaFacade.edit(reg);
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
