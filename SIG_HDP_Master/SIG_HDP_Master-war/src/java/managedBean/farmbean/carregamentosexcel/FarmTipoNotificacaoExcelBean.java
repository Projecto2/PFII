/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmTipoNotificacao;
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
import sessao.FarmTipoNotificacaoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmTipoNotificacaoExcelBean implements Serializable
{

   @EJB
   private FarmTipoNotificacaoFacade farmTipoNotificacaoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmTipoNotificacaoExcelBean
    */
   public FarmTipoNotificacaoExcelBean()
   {
   }

   public static FarmTipoNotificacaoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmTipoNotificacaoExcelBean farmTipoNotificacaoExcelBean
              = (FarmTipoNotificacaoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmTipoNotificacaoExcelBean");

      return farmTipoNotificacaoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarTipoNotificacaoTabela()
   {
      Date dataTipoNotificacaoTabela = this.farmUpdatesFacade.dataTipoNotificacao();

      Date dataTipoNotificacaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_TIPO_NOTIFICACAO_FARM);

      if (this.farmTipoNotificacaoFacade.isTipoNotificacaoTabelaEmpty() || dataTipoNotificacaoTabela == null);
      else if (!farmTipoNotificacaoFacade.isTipoNotificacaoTabelaEmpty() && (dataTipoNotificacaoXLSFile != null && dataTipoNotificacaoXLSFile.compareTo(dataTipoNotificacaoTabela) <= 0))
         return;

      if (lerTipoNotificacaoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoTipoNotificacaoTabela();
      }
   }

   public boolean lerTipoNotificacaoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_TIPO_NOTIFICACAO_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("tiposDeNotificacao");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmTipoNotificacao reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverTipoNotificacaoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverTipoNotificacaoTabela(FarmTipoNotificacao reg)
   {
      if (farmTipoNotificacaoFacade.existeRegisto(reg.getPkIdTipoNotificacao()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmTipoNotificacao lerCampos(HSSFRow row)
   {
      int pk_id_tipo_notificacao;
      String descricao;

      final int PK_ID_TIPO_NOTIFICACAO = 0;
      final int DESCRICAO = 1;

      FarmTipoNotificacao reg = new FarmTipoNotificacao();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_TIPO_NOTIFICACAO:
               pk_id_tipo_notificacao = (int) cell.getNumericCellValue();
               reg.setPkIdTipoNotificacao(pk_id_tipo_notificacao);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmTipoNotificacao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoNotificacaoFacade.create(reg);
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

   public boolean editRegister(FarmTipoNotificacao reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmTipoNotificacaoFacade.edit(reg);
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
