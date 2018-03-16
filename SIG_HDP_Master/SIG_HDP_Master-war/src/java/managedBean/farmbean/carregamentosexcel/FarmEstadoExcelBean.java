
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmEstado;
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
import sessao.FarmEstadoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmEstadoExcelBean implements Serializable
{

   @EJB
   private FarmEstadoFacade farmEstadoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmEstadoExcelBean
    */
   public FarmEstadoExcelBean()
   {
   }

   public static FarmEstadoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmEstadoExcelBean farmEstadoExcelBean
              = (FarmEstadoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmEstadoExcelBean");

      return farmEstadoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarEstadoTabela()
   {
      Date dataEstadoTabela = this.farmUpdatesFacade.dataEstado();

      Date dataEstadoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_ESTADO_NOTIFICACAO_FARM);

      if (this.farmEstadoFacade.isEstadoTabelaEmpty() || dataEstadoTabela == null);
      else if (!farmEstadoFacade.isEstadoTabelaEmpty() && (dataEstadoXLSFile != null && dataEstadoXLSFile.compareTo(dataEstadoTabela) <= 0))
         return;

      if (lerEstadoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoEstadoTabela();
      }
   }

   public boolean lerEstadoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_ESTADO_NOTIFICACAO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("estadosDeNotificacao");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmEstado reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverEstadoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverEstadoTabela(FarmEstado reg)
   {
      if (reg.getPkIdEstado() != null)
      {
         if (farmEstadoFacade.existeRegisto(reg.getPkIdEstado()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }

   }

   public FarmEstado lerCampos(HSSFRow row)
   {
      int pk_id_estado;
      String descricao;

      final int PK_ID_ESTADO = 0;
      final int DESCRICAO = 1;

      FarmEstado reg = new FarmEstado();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_ESTADO:
               pk_id_estado = (int) cell.getNumericCellValue();
               reg.setPkIdEstado(pk_id_estado);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmEstado reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmEstadoFacade.create(reg);
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

   public boolean editRegister(FarmEstado reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmEstadoFacade.edit(reg);
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
