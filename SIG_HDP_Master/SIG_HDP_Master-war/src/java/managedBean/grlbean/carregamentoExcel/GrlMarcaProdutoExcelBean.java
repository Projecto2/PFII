/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean.carregamentoExcel;

import entidade.GrlMarcaProduto;
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
import sessao.GrlMarcaProdutoFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlMarcaProdutoExcelBean implements Serializable
{

   @EJB
   private GrlMarcaProdutoFacade grlMarcaProdutoFacade;
   @EJB
   private GrlUpdatesFacade grlUpdatesFacade;

   /**
    * Creates a new instance of GrlTipoDeMarcaProdutoExcelBean
    */
   @Resource
   private UserTransaction userTransaction;

   public GrlMarcaProdutoExcelBean()
   {
   }

   public static GrlMarcaProdutoExcelBean getInstanciaBean()
   {
      System.out.println("inicio do getInstanciaBean");
      FacesContext context = FacesContext.getCurrentInstance();
      GrlMarcaProdutoExcelBean grlMarcaProdutoExcelBean
              = (GrlMarcaProdutoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "grlMarcaProdutoExcelBean");

      System.out.println("fim do getInstanciaBean");

      return grlMarcaProdutoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarMarcaProdutoTabela()
   {
//        Date dataMarcaProdutoTabela = this.grlUpdatesFacade.dataMarcaProduto();
//
//        Date dataMarcaProdutoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_MARCA_PRODUTO_GRL);
//
//        if (this.grlMarcaProdutoFacade.isMarcaProdutoTabelaEmpty() || dataMarcaProdutoTabela == null);
//        else if (!grlMarcaProdutoFacade.isMarcaProdutoTabelaEmpty() && (dataMarcaProdutoXLSFile != null && dataMarcaProdutoXLSFile.compareTo(dataMarcaProdutoTabela) <= 0))
//        {
//            return;
//        }
      System.out.println("antes do lerMarcaProdutoTabela.");
      if (lerMarcaProdutoTabela())
      {
         System.out.println("escrevendo data de actualizacao do ficheiro.");
         this.grlUpdatesFacade.escreverDataActualizacaoMarcaProdutoTabela();
      }
   }

   public boolean lerMarcaProdutoTabela()
   {
      int nreg = 0;
      try
      {
         //Criar o Stream, caminho
         InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_MARCA_PRODUTO_GRL);

         //Pega o Livro do Excel (O ficheiro Excel)
         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         //Pega a Pagina que desejamos ler
         HSSFSheet sheet = wb.getSheet("marcasDeProduto(Laboratorios)");

         HSSFRow row;
//            HSSFCell cell;

         //Pega o o iterador que ira varrer todas as linhas desta pagina
         Iterator rows = sheet.rowIterator();

         // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
         row = (HSSFRow) rows.next();
         Date dataMarcaProdutoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_MARCA_PRODUTO_GRL);

         Date dataMarcaProdutoTabela = this.grlUpdatesFacade.dataMarcaProduto();

         if (dataMarcaProdutoTabela == null);
         else if (dataMarcaProdutoXLSFile.compareTo(dataMarcaProdutoTabela) <= 0)
         {
            return false;
         }

         //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
         boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
         //se encontram em cada linha tem que ser transformado num registo que pode ser
         //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
         //gravar na bd
         GrlMarcaProduto reg = null;

         //vai correr todas as linhas do ficheiro excel enquanto tiver dados na proxima linha
         while (rows.hasNext())
         {
            //pega linha
            row = (HSSFRow) rows.next();

            //se for a primeira linha, ira pular, pois a primeira linha sao apenas titulos
            if (firstRow)
            {
               firstRow = false;
               continue;
            }
                //caso nao seja a primeira linha, converto os dados dessa linha num registo que 
            //pode ser posto na base de dados
            reg = lerCampos(row);

            escreverMarcaProdutoTabela(reg);
            nreg++;
            //System.err.println("12: AmbCidCapitulosBean.carregarCapitulosTabela()");
         } // fim da leitura de cada linha 
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverMarcaProdutoTabela(GrlMarcaProduto reg)
   {
      if (grlMarcaProdutoFacade.existeRegisto(reg.getPkIdMarca()) == false)
      {
         this.createRegister(reg);
      }
      else
      {
         this.editRegister(reg);
      }
   }

   public GrlMarcaProduto lerCampos(HSSFRow row)
   {
      int pk_id_distrito;
      String descricao;

      final int PK_ID_DISTRITO = 0;
      final int DESCRICAO = 1;

      GrlMarcaProduto reg = new GrlMarcaProduto();

//        HSSFCell cell;
      System.out.println("lendo campos");
      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case PK_ID_DISTRITO:
               pk_id_distrito = (int) cell.getNumericCellValue();
               reg.setPkIdMarca(pk_id_distrito);
               System.out.println("leu id: " + pk_id_distrito);
               break;

            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               System.out.println("leu descricao: " + descricao);
               break;

         }
      }

      return reg;
   }

   public boolean createRegister(GrlMarcaProduto reg)
   {
      try
      {
         this.userTransaction.begin();
         this.grlMarcaProdutoFacade.create(reg);
         System.out.println("criou o registo");
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
            //System.err.println("Roolback: " + ex.toString());
         }
      }
      return false;
   }

   public boolean editRegister(GrlMarcaProduto reg)
   {
      try
      {
         this.userTransaction.begin();
         this.grlMarcaProdutoFacade.edit(reg);
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
            //System.err.println("Roolback: " + ex.toString());
         }
      }
      return false;
   }

}
