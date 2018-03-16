/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmTipoFornecimento;
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
import sessao.FarmTipoFornecimentoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmTipoFornecimentoExcelBean implements Serializable
{

   @EJB
   private FarmTipoFornecimentoFacade farmTipoFornecimentoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmTipoFornecimentoExcelBean
    */
   public FarmTipoFornecimentoExcelBean()
   {
   }

   public static FarmTipoFornecimentoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmTipoFornecimentoExcelBean farmTipoFornecimentoExcelBean
              = (FarmTipoFornecimentoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmTipoFornecimentoExcelBean");

      return farmTipoFornecimentoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarTipoFornecimentoTabela()
   {
      Date dataTipoFornecimentoTabela = this.farmUpdatesFacade.dataTipoUnidadeDeMedida();

      Date dataTipoFornecimentoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_TIPO_FORNECIMENTO_FARM);

      if (this.farmTipoFornecimentoFacade.isTipoFornecimentoTabelaEmpty() || dataTipoFornecimentoTabela == null);
      else if (!farmTipoFornecimentoFacade.isTipoFornecimentoTabelaEmpty() && (dataTipoFornecimentoXLSFile != null && dataTipoFornecimentoXLSFile.compareTo(dataTipoFornecimentoTabela) <= 0))
         return;

      if (lerTipoFornecimentoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoTipoFornecimentoTabela();
      }
   }

   public boolean lerTipoFornecimentoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_TIPO_FORNECIMENTO_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("tiposDeFornecimento");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmTipoFornecimento reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverTipoFornecimentoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverTipoFornecimentoTabela(FarmTipoFornecimento reg)
   {
      if (farmTipoFornecimentoFacade.existeRegisto(reg.getPkIdTipoFornecimento()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmTipoFornecimento lerCampos(HSSFRow row)
   {
      int pk_id_tipo_fornecimento;
      String descricao;

      final int PK_ID_TIPO_FORNECIMENTO = 0;
      final int DESCRICAO = 1;

      FarmTipoFornecimento reg = new FarmTipoFornecimento();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_TIPO_FORNECIMENTO:
               pk_id_tipo_fornecimento = (int) cell.getNumericCellValue();
               reg.setPkIdTipoFornecimento(pk_id_tipo_fornecimento);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricaoTipoFornecimento(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmTipoFornecimento reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoFornecimentoFacade.create(reg);
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

   public boolean editRegister(FarmTipoFornecimento reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoFornecimentoFacade.edit(reg);
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