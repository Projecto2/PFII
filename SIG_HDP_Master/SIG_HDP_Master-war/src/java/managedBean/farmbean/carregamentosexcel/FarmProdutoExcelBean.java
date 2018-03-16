/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmCategoriaMedicamento;
import entidade.FarmFormaFarmaceutica;
import entidade.FarmProduto;
import entidade.FarmTipoProduto;
import entidade.FarmTipoUnidadeMedida;
import entidade.FarmUnidadeMedida;
import entidade.FarmViaAdministracao;
import entidade.RhFuncionario;
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
import managedBean.segbean.SegLoginBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.FarmCategoriaMedicamentoFacade;
import sessao.FarmFormaFarmaceuticaFacade;
import sessao.FarmProdutoFacade;
import sessao.FarmUnidadeMedidaFacade;
import sessao.FarmUpdatesFacade;
import sessao.FarmViaAdministracaoFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmProdutoExcelBean implements Serializable
{

   @EJB
   private FarmProdutoFacade farmProdutoFacade;
   @EJB
   private FarmUnidadeMedidaFacade farmUnidadeMedidaFacade;
   @EJB
   private FarmFormaFarmaceuticaFacade farmFormaFarmaceuticaFacade;
   @EJB
   private FarmViaAdministracaoFacade farmViaAdministracaoFacade;
   @EJB
   private FarmCategoriaMedicamentoFacade farmCategoriaMedicamentoFacade;
   @EJB
   private FarmUpdatesFacade farmUpdatesFacade;

   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmProdutoExcelBean
    */
   public FarmProdutoExcelBean()
   {
   }

   public static FarmProdutoExcelBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmProdutoExcelBean farmProdutoExcelBean
              = (FarmProdutoExcelBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmProdutoExcelBean");

      return farmProdutoExcelBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarProdutoTabela()
   {
      while (lerProdutoTabelaM());
      while (lerProdutoTabelaMG());
      while (lerProdutoTabelaMGV());
      Mensagem.sucessoMsg("Carregamento Inicial Efectuado com Sucesso.");
   }

   public boolean lerProdutoTabelaM()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_PRODUTO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("Medicamentos");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmProduto reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCamposM(row);

            escreverProdutoTabela(reg);
            nreg++;
         }
         return false;
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public boolean lerProdutoTabelaMG()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_PRODUTO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("Material Gastável");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmProduto reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCamposMG(row);

            escreverProdutoTabela(reg);
            nreg++;
         }
         return false;
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public boolean lerProdutoTabelaMGV()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_PRODUTO_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("Medicamento de Grande Volume");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmProduto reg = null;

         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }

            reg = lerCamposMGV(row);

            escreverProdutoTabela(reg);
            nreg++;
         }
         return false;
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return nreg != 0;
   }

   public void escreverProdutoTabela(FarmProduto reg)
   {
      this.createRegister(reg);
   }

   public FarmProduto lerCamposM(HSSFRow row)
   {
      String descricao;
      String dosagem;
      int fk_id_tipo_produto = Constantes.FARM_TIPO_PRODUTOMEDICAMENTO;
      int fk_id_funcionario_cadastrou = SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario().getPkIdFuncionario();
      Date data_hora_cadastro = new Date();

      final int DESCRICAO = 1;
      final int DOSAGEM = 2;
      final int FK_ID_UNIDADE_MEDIDA = 3;
      final int FK_ID_FORMA_FARMACEUTICA = 4;
      final int FK_ID_VIA_ADMINISTRACAO = 5;
      final int FK_ID_CATEGORIA_MEDICAMENTO = 6;

      FarmProduto reg = new FarmProduto();
      reg.setFkIdTipoProduto(new FarmTipoProduto(fk_id_tipo_produto));
      reg.setDataHoraCadastro(data_hora_cadastro);
      reg.setFkIdFuncionarioCadastrou(new RhFuncionario(fk_id_funcionario_cadastrou));

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               reg.setNomeGenerico(descricao);
               break;

            case DOSAGEM:
               try
               {
                  dosagem = cell.getStringCellValue().trim();
                  reg.setDosagem(dosagem);
               }
               catch (Exception e)
               {
                  dosagem = (int) cell.getNumericCellValue() + "";
                  reg.setDosagem(dosagem);
               }
               break;

            case FK_ID_UNIDADE_MEDIDA:
               
               FarmUnidadeMedida farmUnidadeMedida = new FarmUnidadeMedida();
               farmUnidadeMedida.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
               farmUnidadeMedida.setAbreviatura(cell.getStringCellValue().trim());
               farmUnidadeMedida = farmUnidadeMedidaFacade.findUnidadeMedida(farmUnidadeMedida).get(0);
               reg.setFkIdUnidadeMedida(farmUnidadeMedida);
               break;

            case FK_ID_FORMA_FARMACEUTICA:
               FarmFormaFarmaceutica farmFormaFarmaceutica = new FarmFormaFarmaceutica();
               farmFormaFarmaceutica.setDescricao(cell.getStringCellValue().trim());
               farmFormaFarmaceutica = farmFormaFarmaceuticaFacade.findFormaFarmaceutica(farmFormaFarmaceutica).get(0);
               reg.setFkIdFormaFarmaceutica(farmFormaFarmaceutica);
               break;

            case FK_ID_VIA_ADMINISTRACAO:
               FarmViaAdministracao farmViaAdministracao = new FarmViaAdministracao();
               farmViaAdministracao.setDescricao(cell.getStringCellValue().trim());
               farmViaAdministracao = farmViaAdministracaoFacade.findViaAdministracao(farmViaAdministracao).get(0);
               reg.setFkIdViaAdministracao(farmViaAdministracao);
               break;

            case FK_ID_CATEGORIA_MEDICAMENTO:
               FarmCategoriaMedicamento farmCategoriaMedicamento = new FarmCategoriaMedicamento();
               try
               {
                  farmCategoriaMedicamento.setCapitulo(cell.getStringCellValue().replace("Cap ", ""));
                  farmCategoriaMedicamento.setFkIdCategoriaSuper(new FarmCategoriaMedicamento());
                  farmCategoriaMedicamento = farmCategoriaMedicamentoFacade.findCategoriaByCapitulo(farmCategoriaMedicamento);
                  reg.setFkIdCategoriaMedicamento(farmCategoriaMedicamento);
               }
               catch (Exception e)
               {
//                  return null;
               }
               break;
         }
      }

      return reg;
   }

   public FarmProduto lerCamposMG(HSSFRow row)
   {
      String descricao;
      String dosagem;
      int fk_id_tipo_produto = Constantes.FARM_TIPO_PRODUTO_MATERIAL_GASTAVEL;
      int fk_id_funcionario_cadastrou = SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario().getPkIdFuncionario();
      Date data_hora_cadastro = new Date();

      final int DESCRICAO = 1;
      final int DOSAGEM = 2;
      final int FK_ID_UNIDADE_MEDIDA = 3;
      final int FK_ID_FORMA_FARMACEUTICA = 4;
      final int FK_ID_VIA_ADMINISTRACAO = 5;
      final int FK_ID_CATEGORIA_MEDICAMENTO = 6;

      FarmProduto reg = new FarmProduto();
      reg.setFkIdTipoProduto(new FarmTipoProduto(fk_id_tipo_produto));
      reg.setDataHoraCadastro(data_hora_cadastro);
      reg.setFkIdFuncionarioCadastrou(new RhFuncionario(fk_id_funcionario_cadastrou));
      reg.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica(Constantes.FARM_N_A));
      reg.setFkIdViaAdministracao(new FarmViaAdministracao(Constantes.FARM_N_A));
      reg.setFkIdCategoriaMedicamento(null);
      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               reg.setNomeGenerico(descricao);
               break;

            case DOSAGEM:
               try
               {
                  dosagem = cell.getStringCellValue().trim();
                  reg.setDosagem(dosagem);
               }
               catch (Exception e)
               {
                  dosagem = (int) cell.getNumericCellValue() + "";
                  reg.setDosagem(dosagem);
               }
               break;

            case FK_ID_UNIDADE_MEDIDA:
               
               FarmUnidadeMedida farmUnidadeMedida = new FarmUnidadeMedida();
               farmUnidadeMedida.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
               farmUnidadeMedida.setAbreviatura(cell.getStringCellValue().trim());
               farmUnidadeMedida = farmUnidadeMedidaFacade.findUnidadeMedida(farmUnidadeMedida).get(0);
               reg.setFkIdUnidadeMedida(farmUnidadeMedida);
               break;
         }
      }

      return reg;
   }

   public FarmProduto lerCamposMGV(HSSFRow row)
   {
      String descricao;
      String dosagem;
      int fk_id_tipo_produto = Constantes.FARM_TIPO_PRODUTO_MEDICAMENTO_GRANDE_VOLUME;
      int fk_id_funcionario_cadastrou = SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario().getPkIdFuncionario();
      Date data_hora_cadastro = new Date();

      final int DESCRICAO = 1;
      final int DOSAGEM = 2;
      final int FK_ID_UNIDADE_MEDIDA = 3;
      final int FK_ID_CATEGORIA_MEDICAMENTO = 5;

      FarmProduto reg = new FarmProduto();
      reg.setFkIdTipoProduto(new FarmTipoProduto(fk_id_tipo_produto));
      reg.setDataHoraCadastro(data_hora_cadastro);
      reg.setFkIdFuncionarioCadastrou(new RhFuncionario(fk_id_funcionario_cadastrou));
      reg.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica(Constantes.FARM_N_A));
      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case DESCRICAO:
               descricao = cell.getStringCellValue().trim();
               reg.setDescricao(descricao);
               reg.setNomeGenerico(descricao);
               break;

            case DOSAGEM:
               try
               {
                  dosagem = cell.getStringCellValue().trim();
                  reg.setDosagem(dosagem);
               }
               catch (Exception e)
               {
                  dosagem = (int) cell.getNumericCellValue() + "";
                  reg.setDosagem(dosagem);
               }
               break;

            case FK_ID_UNIDADE_MEDIDA:
               
               FarmUnidadeMedida farmUnidadeMedida = new FarmUnidadeMedida();
               farmUnidadeMedida.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
               farmUnidadeMedida.setAbreviatura(cell.getStringCellValue().trim());
               farmUnidadeMedida = farmUnidadeMedidaFacade.findUnidadeMedida(farmUnidadeMedida).get(0);
               reg.setFkIdUnidadeMedida(farmUnidadeMedida);
               break;

            case FK_ID_CATEGORIA_MEDICAMENTO:
               FarmCategoriaMedicamento farmCategoriaMedicamento = new FarmCategoriaMedicamento();
               try
               {
                  System.out.println("lendo a categoria...try");
                  farmCategoriaMedicamento.setCapitulo(cell.getStringCellValue().replace("Cap ", ""));
                  farmCategoriaMedicamento.setFkIdCategoriaSuper(new FarmCategoriaMedicamento());
                  farmCategoriaMedicamento = farmCategoriaMedicamentoFacade.findCategoriaByCapitulo(farmCategoriaMedicamento);
                  reg.setFkIdCategoriaMedicamento(farmCategoriaMedicamento);
                  System.out.println("categori obtida: "+reg.getFkIdCategoriaMedicamento());
               }
               catch (Exception e)
               {
                  System.out.println("lendo a categoria...Exception");
//                  return null;
               }
               break;
         }
      }

      return reg;
   }

   public boolean createRegister(FarmProduto reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmProdutoFacade.create(reg);
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

   public boolean editRegister(FarmProduto reg)
   {
      try
      {
         this.userTransaction.begin();
         this.farmProdutoFacade.edit(reg);
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
   
   public boolean naoExisteProdutos()
   {
      return farmProdutoFacade.findAll().isEmpty();
   }
}
