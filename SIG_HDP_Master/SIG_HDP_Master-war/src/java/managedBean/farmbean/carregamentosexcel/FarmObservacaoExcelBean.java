/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmObservacao;
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
import sessao.FarmObservacaoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmObservacaoExcelBean implements Serializable
{

   @EJB
   private FarmObservacaoFacade farmObservacaoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmObservacaoExcelBean
    */
   public FarmObservacaoExcelBean()
   {
   }

   public static FarmObservacaoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmObservacaoExcelBean farmObservacaoExcelBean
              = (FarmObservacaoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmObservacaoExcelBean");

      return farmObservacaoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarObservacaoTabela()
   {
      Date dataObservacaoTabela = this.farmUpdatesFacade.dataObservacao();

      Date dataObservacaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_OBSERVACAO_FARM);

      if (this.farmObservacaoFacade.isObservacaoTabelaEmpty() || dataObservacaoTabela == null);
      else if (!farmObservacaoFacade.isObservacaoTabelaEmpty() && (dataObservacaoXLSFile != null && dataObservacaoXLSFile.compareTo(dataObservacaoTabela) <= 0))
         return;

      if (lerObservacaoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoObservacaoTabela();
      }
   }

   public boolean lerObservacaoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_OBSERVACAO_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("observacoes");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmObservacao reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverObservacaoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverObservacaoTabela(FarmObservacao reg)
   {
      if (farmObservacaoFacade.existeRegisto(reg.getPkIdObservacao()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmObservacao lerCampos(HSSFRow row)
   {
      int pk_id_observacao;
      String descricao;

      final int PK_ID_OBSERVACAO = 0;
      final int DESCRICAO = 1;

      FarmObservacao reg = new FarmObservacao();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_OBSERVACAO:
               pk_id_observacao = (int) cell.getNumericCellValue();
               reg.setPkIdObservacao(pk_id_observacao);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmObservacao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmObservacaoFacade.create(reg);
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

   public boolean editRegister(FarmObservacao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmObservacaoFacade.edit(reg);
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
