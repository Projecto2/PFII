/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmViaAdministracao;
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
import sessao.FarmUpdatesFacade;
import sessao.FarmViaAdministracaoFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmViaAdministracaoExcelBean implements Serializable
{
   @EJB
   private FarmViaAdministracaoFacade farmViaAdministracaoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmViaAdministracaoExcelBean
    */
   public FarmViaAdministracaoExcelBean()
   {
   }

   public static FarmViaAdministracaoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmViaAdministracaoExcelBean farmViaAdministracaoExcelBean
              = (FarmViaAdministracaoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmViaAdministracaoExcelBean");

      return farmViaAdministracaoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarViaAdministracaoTabela()
   {
      Date dataViaAdministracaoTabela = this.farmUpdatesFacade.dataViaAdministracao();

      Date dataViaAdministracaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_VIA_ADMINISTRACAO_FARM);

      if (this.farmViaAdministracaoFacade.isViaAdministracaoTabelaEmpty() || dataViaAdministracaoTabela == null);
      else if (!farmViaAdministracaoFacade.isViaAdministracaoTabelaEmpty() && (dataViaAdministracaoXLSFile != null && dataViaAdministracaoXLSFile.compareTo(dataViaAdministracaoTabela) <= 0))
         return;

      if (lerViaAdministracaoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoViaAdministracaoTabela();
      }
   }

   public boolean lerViaAdministracaoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_VIA_ADMINISTRACAO_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("viasDeAdministracao");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmViaAdministracao reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverViaAdministracaoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverViaAdministracaoTabela(FarmViaAdministracao reg)
   {
      if (farmViaAdministracaoFacade.existeRegisto(reg.getPkIdViaAdministracao()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmViaAdministracao lerCampos(HSSFRow row)
   {
      int pk_id_via_administracao;
      String descricao;

      final int PK_ID_VIA_ADMINISTRACAO = 0;
      final int DESCRICAO = 1;

      FarmViaAdministracao reg = new FarmViaAdministracao();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_VIA_ADMINISTRACAO:
               pk_id_via_administracao = (int) cell.getNumericCellValue();
               reg.setPkIdViaAdministracao(pk_id_via_administracao);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmViaAdministracao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmViaAdministracaoFacade.create(reg);
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

   public boolean editRegister(FarmViaAdministracao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmViaAdministracaoFacade.edit(reg);
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
