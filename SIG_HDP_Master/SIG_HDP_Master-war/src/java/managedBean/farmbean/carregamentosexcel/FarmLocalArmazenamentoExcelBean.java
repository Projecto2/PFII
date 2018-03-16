/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmLocalArmazenamento;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
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
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmUpdatesFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmLocalArmazenamentoExcelBean implements Serializable
{

   @EJB
   private FarmLocalArmazenamentoFacade farmLocalArmazenamentoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmLocalArmazenamentoExcelBean
    */
   public FarmLocalArmazenamentoExcelBean()
   {
   }

   public static FarmLocalArmazenamentoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmLocalArmazenamentoExcelBean farmLocalArmazenamentoExcelBean
              = (FarmLocalArmazenamentoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmLocalArmazenamentoExcelBean");

      return farmLocalArmazenamentoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarLocalArmazenamentoTabela()
   {
      Date dataLocalArmazenamentoTabela = this.farmUpdatesFacade.dataLocalArmazenamento();

      Date dataLocalArmazenamentoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.farm.Defs.FILE_LOCAL_ARMAZENAMENTO_FARM);

      if (this.farmLocalArmazenamentoFacade.isLocalArmazenamentoTabelaEmpty() || dataLocalArmazenamentoTabela == null);
      else if (!farmLocalArmazenamentoFacade.isLocalArmazenamentoTabelaEmpty() && (dataLocalArmazenamentoXLSFile != null && dataLocalArmazenamentoXLSFile.compareTo(dataLocalArmazenamentoTabela) <= 0))
         return;

      if (lerLocalArmazenamentoTabela())
      {
         this.farmUpdatesFacade.escreverDataActualizacaoLocalArmazenamentoTabela();
      }
   }

   public boolean lerLocalArmazenamentoTabela()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_LOCAL_ARMAZENAMENTO_FARM);
         
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
         
         HSSFSheet sheet = wb.getSheet("locaisDeArmazenamento");
         
         HSSFRow row;
         
         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmLocalArmazenamento reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
            
            reg = lerCampos(row);

            escreverLocalArmazenamentoTabela(reg);
            nreg++;
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverLocalArmazenamentoTabela(FarmLocalArmazenamento reg)
   {
      if (farmLocalArmazenamentoFacade.existeRegisto(reg.getPkIdLocalArmazenamento()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public FarmLocalArmazenamento lerCampos(HSSFRow row)
   {
      int pk_id_local_armazenamento;
      String descricao;
      String abreviatura;
      int capacidade_alocacao;
      int fk_id_instituicao;
      int fk_tipo_local_armazenamento;

      final int PK_ID_LOCAL_ARMAZENAMENTO = 0;
      final int FK_ID_TIPO_LOCAL_ARMAZENAMENTO = 1;
      final int DESCRICAO = 2;
      final int ABREVIATURA = 3;
      final int CAPACIDADE_ALOCACAO = 4;
      final int FK_ID_INSTITUICAO = 5;

      FarmLocalArmazenamento reg = new FarmLocalArmazenamento();

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_LOCAL_ARMAZENAMENTO:
               pk_id_local_armazenamento = (int) cell.getNumericCellValue();
               reg.setPkIdLocalArmazenamento(pk_id_local_armazenamento);
               break;
               
            case FK_ID_TIPO_LOCAL_ARMAZENAMENTO:
               if (cell == null)
               {
                  reg.setFkIdTipoLocalArmazenamento(null);
               }
               else
               {
                  fk_tipo_local_armazenamento = (int) cell.getNumericCellValue();
                  reg.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(fk_tipo_local_armazenamento));
               }
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               break;

            case ABREVIATURA:
               abreviatura = cell.getStringCellValue().trim();
               reg.setAbreviatura(abreviatura);
               break;
               
            case CAPACIDADE_ALOCACAO:
               if (cell == null)
               {
                  reg.setCapacidadeAlocacao(null);
               }
               else
               {
                  capacidade_alocacao = (int) cell.getNumericCellValue();
                  reg.setCapacidadeAlocacao(capacidade_alocacao);
               }
               break;
                              
            case FK_ID_INSTITUICAO:
               if (cell == null)
               {
                  reg.setFkIdInstituicao(null);
               }
               else
               {
                  fk_id_instituicao = (int) cell.getNumericCellValue();
                  reg.setFkIdInstituicao(new GrlInstituicao(fk_id_instituicao));
               }
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmLocalArmazenamento reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmLocalArmazenamentoFacade.create(reg);
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

   public boolean editRegister(FarmLocalArmazenamento reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmLocalArmazenamentoFacade.edit(reg);
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
