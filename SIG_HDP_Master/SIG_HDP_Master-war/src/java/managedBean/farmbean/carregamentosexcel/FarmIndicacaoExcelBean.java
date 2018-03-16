/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmIndicacao;
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
import sessao.FarmIndicacaoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmIndicacaoExcelBean implements Serializable
{

   @EJB
   private FarmIndicacaoFacade farmIndicacaoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmIndicacaoExcelBean
    */
   public FarmIndicacaoExcelBean()
   {
   }

   public static FarmIndicacaoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmIndicacaoExcelBean farmIndicacaoExcelBean
              = (FarmIndicacaoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmIndicacaoExcelBean");

      return farmIndicacaoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarIndicacaoTabela()
   {
      Date dataIndicacaoTabela = this.farmUpdatesFacade.dataIndicacao();

      Date dataIndicacaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_INDICACAO_FARM);

      if (this.farmIndicacaoFacade.isIndicacaoTabelaEmpty() || dataIndicacaoTabela == null);
      else if (!farmIndicacaoFacade.isIndicacaoTabelaEmpty() && (dataIndicacaoXLSFile != null && dataIndicacaoXLSFile.compareTo(dataIndicacaoTabela) <= 0))
         return;

      if (lerIndicacaoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoIndicacaoTabela();
      }
   }

   public boolean lerIndicacaoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_INDICACAO_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("indicacoes");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmIndicacao reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverIndicacaoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverIndicacaoTabela(FarmIndicacao reg)
   {
      if (farmIndicacaoFacade.existeRegisto(reg.getPkIdIndicacao()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmIndicacao lerCampos(HSSFRow row)
   {
      int pk_id_indicacao;
      String descricao;

      final int PK_ID_INDICACAO = 0;
      final int DESCRICAO = 1;

      FarmIndicacao reg = new FarmIndicacao();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_INDICACAO:
               pk_id_indicacao = (int) cell.getNumericCellValue();
               reg.setPkIdIndicacao(pk_id_indicacao);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmIndicacao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmIndicacaoFacade.create(reg);
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

   public boolean editRegister(FarmIndicacao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmIndicacaoFacade.edit(reg);
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
