
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmEstadoPedido;
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
import sessao.FarmEstadoPedidoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmEstadoDePedidoExcelBean implements Serializable
{

   @EJB
   private FarmEstadoPedidoFacade farmEstadoDePedidoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmEstadoPedidoExcelBean
    */
   public FarmEstadoDePedidoExcelBean()
   {
   }

   public static FarmEstadoDePedidoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmEstadoDePedidoExcelBean farmEstadoDePedidoExcelBean
              = (FarmEstadoDePedidoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmEstadoDePedidoExcelBean");

      return farmEstadoDePedidoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarEstadoDePedidoTabela()
   {
      Date dataEstadoDePedidoTabela = this.farmUpdatesFacade.dataEstadoPedido();

      Date dataEstadoDePedidoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_ESTADO_PEDIDO_FARM);

      if (this.farmEstadoDePedidoFacade.isEstadoPedidoTabelaEmpty() || dataEstadoDePedidoTabela == null);
      else if (!farmEstadoDePedidoFacade.isEstadoPedidoTabelaEmpty() && (dataEstadoDePedidoXLSFile != null && dataEstadoDePedidoXLSFile.compareTo(dataEstadoDePedidoTabela) <= 0))
         return;

      if (lerEstadoDePedidoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoEstadoDePedidoTabela();
      }
   }

   public boolean lerEstadoDePedidoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_ESTADO_PEDIDO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("estadosDePedido");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmEstadoPedido reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCampos(row);

            escreverEstadoDePedidoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverEstadoDePedidoTabela(FarmEstadoPedido reg)
   {
      if (reg.getPkIdEstadoPedido() != null)
      {
         if (farmEstadoDePedidoFacade.existeRegisto(reg.getPkIdEstadoPedido()) == false)
         {
            this.createRegister(reg);
         }
         else
         {
            this.editRegister(reg);
         }
      }
   }

   public FarmEstadoPedido lerCampos(HSSFRow row)
   {
      int pk_id_farmaco;
      String descricao;

      final int PK_ID_ESTADO_PEDIDO = 0;
      final int DESCRICAO = 1;

      FarmEstadoPedido reg = new FarmEstadoPedido();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_ESTADO_PEDIDO:
               pk_id_farmaco = (int) cell.getNumericCellValue();
               reg.setPkIdEstadoPedido(pk_id_farmaco);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmEstadoPedido reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmEstadoDePedidoFacade.create(reg);
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

   public boolean editRegister(FarmEstadoPedido reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmEstadoDePedidoFacade.edit(reg);
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
