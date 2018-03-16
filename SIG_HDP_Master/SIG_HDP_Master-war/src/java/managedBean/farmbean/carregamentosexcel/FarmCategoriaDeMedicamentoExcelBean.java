/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmCategoriaMedicamento;
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
import sessao.FarmCategoriaMedicamentoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmCategoriaDeMedicamentoExcelBean implements Serializable
{

   @EJB
   private FarmCategoriaMedicamentoFacade farmCategoriaDeMedicamentoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmCategoriaDeMedicamentoExcelBean
    */
   public FarmCategoriaDeMedicamentoExcelBean()
   {
   }

   public static FarmCategoriaDeMedicamentoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmCategoriaDeMedicamentoExcelBean farmCategoriaDeMedicamentoExcelBean
              = (FarmCategoriaDeMedicamentoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmCategoriaDeMedicamentoExcelBean");

      return farmCategoriaDeMedicamentoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarCategoriaDeMedicamentoTabela()
   {
      Date dataCategoriaDeMedicamentoTabela = this.farmUpdatesFacade.dataCategoriaDeMedicamento();

      Date dataCategoriaDeMedicamentoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_CATEGORIA_MEDICAMENTO_FARM);

      if (this.farmCategoriaDeMedicamentoFacade.isCategoriaMedicamentoTabelaEmpty() || dataCategoriaDeMedicamentoTabela == null);
      else if (!farmCategoriaDeMedicamentoFacade.isCategoriaMedicamentoTabelaEmpty() && (dataCategoriaDeMedicamentoXLSFile != null && dataCategoriaDeMedicamentoXLSFile.compareTo(dataCategoriaDeMedicamentoTabela) <= 0))
         return;

      if (lerCategoriaDeMedicamentoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoCategoriaDeMedicamentoTabela();
      }
   }

   public boolean lerCategoriaDeMedicamentoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_CATEGORIA_MEDICAMENTO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("CategoriasDeMedicamento");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmCategoriaMedicamento reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverCategoriaDeMedicamentoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverCategoriaDeMedicamentoTabela(FarmCategoriaMedicamento reg)
   {
      if (farmCategoriaDeMedicamentoFacade.existeRegisto(reg.getPkIdCategoriaMedicamento()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmCategoriaMedicamento lerCampos(HSSFRow row)
   {
      int pk_id_categoria_medicamento;
      int fk_id_categoria_super;
      String descricao;
      String capitulo;

      final int PK_ID_CATEGORIA_MEDICAMENTO = 0;
      final int DESCRICAO = 1;
      final int FK_ID_CATEGORIA_SUPER = 2;
      final int CAPITULO = 3;

      FarmCategoriaMedicamento reg = new FarmCategoriaMedicamento();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_CATEGORIA_MEDICAMENTO:
               pk_id_categoria_medicamento = (int) cell.getNumericCellValue();
               reg.setPkIdCategoriaMedicamento(pk_id_categoria_medicamento);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;

            case FK_ID_CATEGORIA_SUPER:
               if (cell == null)
               {
                  reg.setFkIdCategoriaSuper(null);
               }
               else
               {
                  fk_id_categoria_super = (int) cell.getNumericCellValue();
                  reg.setFkIdCategoriaSuper(new FarmCategoriaMedicamento(fk_id_categoria_super));
               }
               break;
               
            case CAPITULO:
               String cap = cell.getStringCellValue().trim();
               capitulo = cap.split(" ")[1];
               reg.setCapitulo(capitulo);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmCategoriaMedicamento reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmCategoriaDeMedicamentoFacade.create(reg);
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

   public boolean editRegister(FarmCategoriaMedicamento reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmCategoriaDeMedicamentoFacade.edit(reg);
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
