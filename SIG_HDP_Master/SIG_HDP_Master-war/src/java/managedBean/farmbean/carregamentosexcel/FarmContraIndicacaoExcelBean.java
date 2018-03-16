/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmContraIndicacao;
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
import sessao.FarmContraIndicacaoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmContraIndicacaoExcelBean implements Serializable
{

   @EJB
   private FarmContraIndicacaoFacade farmContraIndicacaoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmContraIndicacaoExcelBean
    */
   public FarmContraIndicacaoExcelBean()
   {
   }

   public static FarmContraIndicacaoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmContraIndicacaoExcelBean farmContraIndicacaoExcelBean
              = (FarmContraIndicacaoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmContraIndicacaoExcelBean");

      return farmContraIndicacaoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarContraIndicacaoTabela()
   {
      Date dataContraIndicacaoTabela = this.farmUpdatesFacade.dataContraIndicacao();

      Date dataContraIndicacaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_CONTRA_INDICACAO_FARM);

      if (this.farmContraIndicacaoFacade.isContraIndicacaoTabelaEmpty() || dataContraIndicacaoTabela == null);
      else if (!farmContraIndicacaoFacade.isContraIndicacaoTabelaEmpty() && (dataContraIndicacaoXLSFile != null && dataContraIndicacaoXLSFile.compareTo(dataContraIndicacaoTabela) <= 0))
         return;

      if (lerContraIndicacaoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoContraIndicacaoTabela();
      }
   }

   public boolean lerContraIndicacaoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_CONTRA_INDICACAO_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("contraIndicacoes");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmContraIndicacao reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverContraIndicacaoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverContraIndicacaoTabela(FarmContraIndicacao reg)
   {
      if (farmContraIndicacaoFacade.existeRegisto(reg.getPkIdContraIndicacao()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmContraIndicacao lerCampos(HSSFRow row)
   {
      int pk_id_contraIndicacao;
      String descricao;

      final int PK_ID_CONTRA_INDICACAO = 0;
      final int DESCRICAO = 1;

      FarmContraIndicacao reg = new FarmContraIndicacao();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_CONTRA_INDICACAO:
               pk_id_contraIndicacao = (int) cell.getNumericCellValue();
               reg.setPkIdContraIndicacao(pk_id_contraIndicacao);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmContraIndicacao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmContraIndicacaoFacade.create(reg);
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

   public boolean editRegister(FarmContraIndicacao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmContraIndicacaoFacade.edit(reg);
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
