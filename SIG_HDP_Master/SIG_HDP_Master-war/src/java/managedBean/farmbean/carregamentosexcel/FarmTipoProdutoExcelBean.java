/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmTipoProduto;
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
import sessao.FarmTipoProdutoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmTipoProdutoExcelBean implements Serializable
{

   @EJB
   private FarmTipoProdutoFacade farmTipoProdutoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmTipoProdutoExcelBean
    */
   public FarmTipoProdutoExcelBean()
   {
   }

   public static FarmTipoProdutoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmTipoProdutoExcelBean farmTipoProdutoExcelBean
              = (FarmTipoProdutoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmTipoProdutoExcelBean");

      return farmTipoProdutoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarTipoProdutoTabela()
   {
      Date dataTipoProdutoTabela = this.farmUpdatesFacade.dataTipoProduto();

      Date dataTipoProdutoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_TIPO_PRODUTO_FARM);

      if (this.farmTipoProdutoFacade.isTipoProdutoTabelaEmpty() || dataTipoProdutoTabela == null);
      else if (!farmTipoProdutoFacade.isTipoProdutoTabelaEmpty() && (dataTipoProdutoXLSFile != null && dataTipoProdutoXLSFile.compareTo(dataTipoProdutoTabela) <= 0))
         return;

      if (lerTipoProdutoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoTipoProdutoTabela();
      }
   }

   public boolean lerTipoProdutoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_TIPO_PRODUTO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("tiposDeProduto");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmTipoProduto reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverTipoProdutoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverTipoProdutoTabela(FarmTipoProduto reg)
   {
      if (reg.getPkIdTipoProduto() != null)
      {
         if (farmTipoProdutoFacade.existeRegisto(reg.getPkIdTipoProduto()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }
   }

   public FarmTipoProduto lerCampos(HSSFRow row)
   {
      int pk_id_tipo_produto;
      String descricao;

      final int PK_ID_TIPO_PRODUTO = 0;
      final int DESCRICAO = 1;

      FarmTipoProduto reg = new FarmTipoProduto();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_TIPO_PRODUTO:
               pk_id_tipo_produto = (int) cell.getNumericCellValue();
               reg.setPkIdTipoProduto(pk_id_tipo_produto);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmTipoProduto reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoProdutoFacade.create(reg);
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

   public boolean editRegister(FarmTipoProduto reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoProdutoFacade.edit(reg);
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
