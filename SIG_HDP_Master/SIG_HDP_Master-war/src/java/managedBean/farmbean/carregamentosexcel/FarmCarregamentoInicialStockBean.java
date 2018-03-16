/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import entidade.FarmCategoriaMedicamento;
import entidade.FarmFichaStock;
import entidade.FarmFormaFarmaceutica;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmTipoUnidadeMedida;
import entidade.FarmUnidadeMedida;
import entidade.FarmViaAdministracao;
import entidade.GrlMarcaProduto;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.farmbean.produto.FarmProdutoGerirLotesBean;
import managedBean.farmbean.produto.FarmProdutoNovoBean;
import managedBean.segbean.SegLoginBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.FarmFichaStockFacade;
import sessao.FarmFormaFarmaceuticaFacade;
import sessao.FarmLoteProdutoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.FarmProdutoFacade;
import sessao.FarmUnidadeMedidaFacade;
import sessao.GrlMarcaProdutoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmCarregamentoInicialStockBean implements Serializable
{

   @EJB
   private FarmLoteProdutoFacade farmLoteProdutoFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade farmLoteProdutoHasLocalArmazenamentoFacade;
   @EJB
   private FarmUnidadeMedidaFacade farmUnidadeMedidaFacade;
   @EJB
   private GrlMarcaProdutoFacade grlMarcaProdutoFacade;
   @EJB
   private FarmProdutoFacade farmProdutoFacade;
   @EJB
   private FarmFichaStockFacade farmFichaStockFacade;
   @EJB
   private FarmFormaFarmaceuticaFacade farmFormaFarmaceuticaFacade;

   private final int localDeArmazenamento_ARM_1 = 4;
   private final int localDeArmazenamento_ARM_3 = 5;
   private final int localDeArmazenamento_ARM_4 = 6;
   private final int localDeArmazenamento_FARM_EXT = 7;
   private final int localDeArmazenamento_FARM_INF = 8;
   private final int localDeArmazenamento_FARM_INT = 3;
   private final String origem_movimento = "Carregamento Inicial";
   @Resource
   private UserTransaction userTransaction;

   /**
    * Creates a new instance of FarmCarregamentoInicialStock
    */
   public FarmCarregamentoInicialStockBean()
   {
   }

   public static FarmCarregamentoInicialStockBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmCarregamentoInicialStockBean farmCarregamentoInicialStockBean
              = (FarmCarregamentoInicialStockBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmCarregamentoInicialStockBean");

      return farmCarregamentoInicialStockBean;
   }

   @SuppressWarnings("empty-statement")
   public void carregarSockInicialTabela()
   {
      while (lerLoteProdutoHasLocalArmazenamentoTabelaARM_1());
      while (lerLoteProdutoHasLocalArmazenamentoTabelaARM_3());
      while (lerLoteProdutoHasLocalArmazenamentoTabelaARM_4());
      while (lerLoteProdutoHasLocalArmazenamentoTabelaFarmExt());
      while (lerLoteProdutoHasLocalArmazenamentoTabelaFarmInt());
      while (lerLoteProdutoHasLocalArmazenamentoTabelaFarmInf());

      Mensagem.sucessoMsg("Carregamento Inicial Efectuado com Sucesso.");
   }

   public boolean lerLoteProdutoHasLocalArmazenamentoTabelaARM_1()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_BALANCO_INICIAL_STOCK_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("Armazém 1");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmLoteProdutoHasLocalArmazenamento reg = new FarmLoteProdutoHasLocalArmazenamento();
         reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_1));

         FarmFichaStock fichaDeStock = new FarmFichaStock();
         fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_1));

         FarmLoteProduto loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
         loteProduto.setFkIdProduto(FarmProdutoNovoBean.getInstancia());
         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
//            reg = lerCamposARM_1(row);
            lerCampos(row, reg, loteProduto, fichaDeStock);
            System.out.println("num de lote depois de sair do lerCamposARM_1: " + loteProduto.getNumeroLote());
            escreverProdutoTabela(reg, loteProduto, fichaDeStock);
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

   public boolean lerLoteProdutoHasLocalArmazenamentoTabelaARM_3()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_BALANCO_INICIAL_STOCK_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("Armazém 3");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmLoteProdutoHasLocalArmazenamento reg = new FarmLoteProdutoHasLocalArmazenamento();
         reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_3));

         FarmFichaStock fichaDeStock = new FarmFichaStock();
         fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_3));

         FarmLoteProduto loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
         loteProduto.setFkIdProduto(FarmProdutoNovoBean.getInstancia());
         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
//            reg = lerCamposARM_1(row);
            lerCampos(row, reg, loteProduto, fichaDeStock);
            System.out.println("num de lote depois de sair do lerCamposARM_3: " + loteProduto.getNumeroLote());
            escreverProdutoTabela(reg, loteProduto, fichaDeStock);
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

   public boolean lerLoteProdutoHasLocalArmazenamentoTabelaARM_4()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_BALANCO_INICIAL_STOCK_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("Armazém 4");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmLoteProdutoHasLocalArmazenamento reg = new FarmLoteProdutoHasLocalArmazenamento();
         reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_4));

         FarmFichaStock fichaDeStock = new FarmFichaStock();
         fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_4));

         FarmLoteProduto loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
         loteProduto.setFkIdProduto(FarmProdutoNovoBean.getInstancia());
         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
//            reg = lerCamposARM_1(row);
            lerCampos(row, reg, loteProduto, fichaDeStock);
            System.out.println("num de lote depois de sair do lerCamposARM_4: " + loteProduto.getNumeroLote());
            escreverProdutoTabela(reg, loteProduto, fichaDeStock);
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

   public boolean lerLoteProdutoHasLocalArmazenamentoTabelaFarmExt()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_BALANCO_INICIAL_STOCK_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("Farmácia Externa");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmLoteProdutoHasLocalArmazenamento reg = new FarmLoteProdutoHasLocalArmazenamento();
         reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_EXT));

         FarmFichaStock fichaDeStock = new FarmFichaStock();
         fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_EXT));

         FarmLoteProduto loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
         loteProduto.setFkIdProduto(FarmProdutoNovoBean.getInstancia());
         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
//            reg = lerCamposARM_1(row);
            lerCampos(row, reg, loteProduto, fichaDeStock);
            System.out.println("num de lote depois de sair do lerCamposFarmExt: " + loteProduto.getNumeroLote());
            escreverProdutoTabela(reg, loteProduto, fichaDeStock);
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

   public boolean lerLoteProdutoHasLocalArmazenamentoTabelaFarmInt()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_BALANCO_INICIAL_STOCK_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("Farmácia Interna");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmLoteProdutoHasLocalArmazenamento reg = new FarmLoteProdutoHasLocalArmazenamento();
         reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_INT));

         FarmFichaStock fichaDeStock = new FarmFichaStock();
         fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_INT));

         FarmLoteProduto loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
         loteProduto.setFkIdProduto(FarmProdutoNovoBean.getInstancia());
         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
//            reg = lerCamposARM_1(row);
            lerCampos(row, reg, loteProduto, fichaDeStock);
            System.out.println("num de lote depois de sair do lerCamposFarmInt: " + loteProduto.getNumeroLote());
            escreverProdutoTabela(reg, loteProduto, fichaDeStock);
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

   public boolean lerLoteProdutoHasLocalArmazenamentoTabelaFarmInf()
   {
      int nreg = 0;
      try
      {
         InputStream ExcelFileToRead = new FileInputStream(util.farm.Defs.FILE_BALANCO_INICIAL_STOCK_FARM);

         HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

         HSSFSheet sheet = wb.getSheet("Farmácia de Infecciologia");

         HSSFRow row;

         Iterator rows = sheet.rowIterator();

         boolean firstRow = true;

         FarmLoteProdutoHasLocalArmazenamento reg = new FarmLoteProdutoHasLocalArmazenamento();
         reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_INF));

         FarmFichaStock fichaDeStock = new FarmFichaStock();
         fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_INF));

         FarmLoteProduto loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
         loteProduto.setFkIdProduto(FarmProdutoNovoBean.getInstancia());
         while (rows.hasNext())
         {
            row = (HSSFRow) rows.next();

            if (firstRow)
            {
               firstRow = false;
               continue;
            }
//            reg = lerCamposARM_1(row);
//            lerCamposARM_1(row, reg, loteProduto, fichaDeStock);
            lerCampos(row, reg, loteProduto, fichaDeStock);
            System.out.println("num de lote depois de sair do lerCamposFarmInf: " + loteProduto.getNumeroLote());
            escreverProdutoTabela(reg, loteProduto, fichaDeStock);
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

   public void escreverProdutoTabela(FarmLoteProdutoHasLocalArmazenamento reg,
           FarmLoteProduto loteProduto,
           FarmFichaStock fichaDeStock)
   {
      try
      {

         System.out.println("criando registo...");

         //obter o lote do produto
         //criar registo no local
         //preenceher ficha de stock
         System.out.println("dados do lote em escreverProdutoTabela: ");
         System.out.println("num de lote: " + loteProduto.getNumeroLote());

         List<FarmLoteProduto> listaLotes = farmLoteProdutoFacade.findLoteProduto(loteProduto, null, null);
         System.out.println("lotes pesquisados: " + listaLotes);
         if (listaLotes.isEmpty())
         {
            System.out.println("o lote nao foi encontrado. Vou cadastrar");
            loteProduto.setDataCadastro(new Date());
            System.out.println("pesquisando o produto para o lote: ");
            loteProduto.getFkIdProduto().setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento());
            loteProduto.getFkIdProduto().setFkIdViaAdministracao(new FarmViaAdministracao());
            loteProduto.getFkIdProduto().setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento());
            loteProduto.getFkIdProduto().setNomeGenerico(null);

            loteProduto.setFkIdProduto(farmProdutoFacade.findProduto(loteProduto.getFkIdProduto()).get(0));
            System.out.println("produto encontrado: " + loteProduto.getFkIdProduto());
            loteProduto.setFkIdFuncionarioCadastrou(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());

            System.out.println("dados a cadastrar: ");
            System.out.println("getDataCadastro: " + loteProduto.getDataCadastro());
            System.out.println("getDataFabrico: " + loteProduto.getDataFabrico());
            System.out.println("getDataValidade: " + loteProduto.getDataValidade());
            System.out.println("getNumeroLote: " + loteProduto.getNumeroLote());
            System.out.println("getNomeComercial: " + loteProduto.getNomeComercial());
            System.out.println("getFkIdProduto: " + loteProduto.getFkIdProduto());
            System.out.println("getFkIdFuncionarioCadastrou: " + loteProduto.getFkIdFuncionarioCadastrou());
            System.out.println("getFkIdMarca: " + loteProduto.getFkIdMarca());

            farmLoteProdutoFacade.create(loteProduto);
            System.out.println("lote criado com sucesso.");
         }
         else
         {
            System.out.println("lote encontrado. Vou supor que tem apenas 1.");
            loteProduto = listaLotes.get(0);

            System.out.println("dados encontrados: ");
            System.out.println("getDataCadastro: " + loteProduto.getDataCadastro());
            System.out.println("getDataFabrico: " + loteProduto.getDataFabrico());
            System.out.println("getDataValidade: " + loteProduto.getDataValidade());
            System.out.println("getNumeroLote: " + loteProduto.getNumeroLote());
            System.out.println("getNomeComercial: " + loteProduto.getNomeComercial());
            System.out.println("getFkIdProduto: " + loteProduto.getFkIdProduto());
            System.out.println("getFkIdFuncionarioCadastrou: " + loteProduto.getFkIdFuncionarioCadastrou());
            System.out.println("getFkIdMarca: " + loteProduto.getFkIdMarca());
         }

         System.out.println("inserindo no reg...");
         reg.setFkIdLoteProduto(loteProduto);

         System.out.println("dados do FarmLoteProdutoHasLocalArmazenamento: ");
         System.out.println("getFkIdLoteProduto: " + reg.getFkIdLoteProduto());
         System.out.println("getQuantidadeStock: " + reg.getQuantidadeStock());
         System.out.println("getFkIdLocalArmazenamento: " + reg.getFkIdLocalArmazenamento());
         System.out.println("getQuantidadeMinimaPermitida: " + reg.getQuantidadeMinimaPermitida());
         System.out.println("getPosicao: " + reg.getPosicao());
         farmLoteProdutoHasLocalArmazenamentoFacade.create(reg);
         System.out.println("FarmLoteProdutoHasLocalArmazenamento criado com sucesso!");

         fichaDeStock.setDataMovimento(new Date());
         fichaDeStock.setFkIdLoteProduto(loteProduto);
         fichaDeStock.setSaidas(0);
         fichaDeStock.setFkIdFuncionario(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
         fichaDeStock.setOrigemOuDestino(origem_movimento);

         System.out.println("dados da ficha de stock: ");
         System.out.println(": " + fichaDeStock.getDataMovimento());
         System.out.println(": " + fichaDeStock.getFkIdLoteProduto());
         System.out.println(": " + fichaDeStock.getFkIdLocalArmazenamento());
         System.out.println(": " + fichaDeStock.getEntradas());
         System.out.println(": " + fichaDeStock.getSaidas());
         System.out.println(": " + fichaDeStock.getQuantidadeRestante());
         System.out.println(": " + fichaDeStock.getFkIdFuncionario());
         System.out.println(": " + fichaDeStock.getOrigemOuDestino());

         farmFichaStockFacade.create(fichaDeStock);
         System.out.println("ficha de stock criada com sucesso!");
      }
      catch (Exception e)
      {
         try
         {
            this.userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            Mensagem.erroMsg("Ocorreu um erro ao fazer o carregamento. Por favor verifique os dados no ficheiro.");
         }
      }
   }

//   public void lerCamposARM_1(
   public void lerCampos(
           HSSFRow row,
           FarmLoteProdutoHasLocalArmazenamento reg,
           FarmLoteProduto loteProduto,
           FarmFichaStock fichaDeStock)
   {
      String descricaoProduto, dosagem;
      String numeroLote, nomeComercial;
      Date dataFabrico, dataValidade;

      int quantidadeStock;
      int quantidade_minima_permitida;
      String posicao;

      final int DESCRICAO_PRODUTO = 0;
      final int DOSAGEM = 1;
      final int FK_ID_UNIDADE_MEDIDA = 2;
      final int FORMA_FARMACEUTICA = 3;
      final int FK_ID_LOTE_PRODUTO = 4;
      final int NOME_COMERCIAL = 5;
      final int MARCA_LABORATORIO = 6;
      final int DATA_FABRICO = 7;
      final int DATA_VALIDADE = 8;
      final int QUANTIDADE_STOCK = 9;
      final int POSICAO = 10;
      final int QUANTIDADE_MINIMA_PERMITIDA = 11;

      for (int cn = 0; cn < row.getLastCellNum(); cn++)
      {
         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

         switch (cn)
         {
            case DESCRICAO_PRODUTO:
               System.out.println("loteProduto.getFkIdProduto().getDescricao(): " + cell.getStringCellValue().trim());
               descricaoProduto = cell.getStringCellValue().trim();
               loteProduto.getFkIdProduto().setDescricao(descricaoProduto);

               break;

            case DOSAGEM:

               try
               {
                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + cell.getStringCellValue());
                  dosagem = cell.getStringCellValue().trim();
                  loteProduto.getFkIdProduto().setDosagem(dosagem);
               }
               catch (Exception e)
               {
                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + (int) cell.getNumericCellValue());
                  dosagem = (int) cell.getNumericCellValue() + "";
                  loteProduto.getFkIdProduto().setDosagem(dosagem);
               }
               break;

            case FK_ID_UNIDADE_MEDIDA:
               System.out.println("loteProduto.getFkIdProduto().lote: " + cell.getStringCellValue());
               FarmUnidadeMedida farmUnidadeMedida = new FarmUnidadeMedida();
               farmUnidadeMedida.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
               farmUnidadeMedida.setAbreviatura(cell.getStringCellValue().trim());
               farmUnidadeMedida = farmUnidadeMedidaFacade.findUnidadeMedida(farmUnidadeMedida).get(0);
               loteProduto.getFkIdProduto().setFkIdUnidadeMedida(farmUnidadeMedida);
               System.out.println("unidade de medida encontrada> " + loteProduto.getFkIdProduto().getFkIdUnidadeMedida());
               break;

            case FK_ID_LOTE_PRODUTO:
               try
               {
                  System.out.println("numero de lote string: " + cell.getStringCellValue().trim());
                  numeroLote = cell.getStringCellValue().trim();
                  loteProduto.setNumeroLote(numeroLote);
                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
               }
               catch (Exception ex)
               {
                  System.out.println("numero de lote inteiro: " + cell.getNumericCellValue());
                  numeroLote = (int) cell.getNumericCellValue() + "";
                  loteProduto.setNumeroLote(numeroLote);
                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
               }
               break;

            case FORMA_FARMACEUTICA:
               FarmFormaFarmaceutica farmFormaFarmaceutica = new FarmFormaFarmaceutica();
               farmFormaFarmaceutica.setDescricao(cell.getStringCellValue().trim());
               farmFormaFarmaceutica = farmFormaFarmaceuticaFacade.findFormaFarmaceutica(farmFormaFarmaceutica).get(0);
               loteProduto.getFkIdProduto().setFkIdFormaFarmaceutica(farmFormaFarmaceutica);
               break;

            case NOME_COMERCIAL:
               System.out.println("nome comercial: " + cell.getStringCellValue());
               nomeComercial = cell.getStringCellValue();
               loteProduto.setNomeComercial(nomeComercial);
               break;

            case MARCA_LABORATORIO:
               if (cell == null)
               {
                  System.out.println("no tem MARCA_LABORATORIO");
                  loteProduto.setFkIdMarca(null);
               }
               else
               {
                  System.out.println("marca: " + cell.getStringCellValue().trim());
                  GrlMarcaProduto grlMarcaProduto = new GrlMarcaProduto();
                  grlMarcaProduto.setDescricao(cell.getStringCellValue().trim());
                  grlMarcaProduto = grlMarcaProdutoFacade.findMarcaProduto(grlMarcaProduto).get(0);
                  loteProduto.setFkIdMarca(grlMarcaProduto);
                  break;
               }

            case DATA_FABRICO:
               if (cell == null)
               {
                  System.out.println("no tem DATA_FABRICO");
                  loteProduto.setDataFabrico(null);
               }
               else
               {
                  try
                  {
                     System.out.println("data fabrico: " + cell.getStringCellValue().trim());
                     dataFabrico = new SimpleDateFormat("yyyy-mm-dd").parse(cell.getStringCellValue().trim());
                     loteProduto.setDataFabrico(dataFabrico);
                  }
                  catch (ParseException ex)
                  {
                     System.out.println("Impossivel Data de Fabrico do Lote."+ex.getMessage());
                  }
               }

               break;

            case DATA_VALIDADE:
               try
               {
                  System.out.println("data validde: " + cell.getStringCellValue().trim());
                  dataValidade = new SimpleDateFormat("yyyy-mm-dd").parse(cell.getStringCellValue().trim());
                  loteProduto.setDataValidade(dataValidade);
               }
               catch (ParseException ex)
               {
                  System.out.println("Impossivel Data de Validade do Lote."+ex.getMessage());
               }
               break;

            case QUANTIDADE_STOCK:
               quantidadeStock = (int) cell.getNumericCellValue();
               reg.setQuantidadeStock(quantidadeStock);
               fichaDeStock.setEntradas(quantidadeStock);
               fichaDeStock.setQuantidadeRestante(quantidadeStock);
               break;

            case POSICAO:
               System.out.println("posicao: " + cell.getStringCellValue());
               posicao = cell.getStringCellValue();
               reg.setPosicao(posicao);
               break;

            case QUANTIDADE_MINIMA_PERMITIDA:
               System.out.println("quantidade_minima_permitida: " + (int) cell.getNumericCellValue());
               quantidade_minima_permitida = (int) cell.getNumericCellValue();
               reg.setQuantidadeMinimaPermitida(quantidade_minima_permitida);
               break;
         }
         System.out.println("num de lte antes de sair: " + loteProduto.getNumeroLote());
      }
   }
//
//   public void lerCamposARM_3(
//           HSSFRow row,
//           FarmLoteProdutoHasLocalArmazenamento reg,
//           FarmLoteProduto loteProduto,
//           FarmFichaStock fichaDeStock)
//   {
//      String descricaoProduto, dosagem;
//      String numeroLote, nomeComercial;
//      Date dataFabrico, dataValidade;
//
//      int quantidadeStock;
//      int quantidade_minima_permitida;
//      String posicao;
//
//      final int DESCRICAO_PRODUTO = 0;
//      final int DOSAGEM = 1;
//      final int FK_ID_UNIDADE_MEDIDA = 2;
//      final int FORMA_FARMACEUTICA = 3;
//      final int FK_ID_LOTE_PRODUTO = 4;
//      final int NOME_COMERCIAL = 5;
//      final int MARCA_LABORATORIO = 6;
//      final int DATA_FABRICO = 7;
//      final int DATA_VALIDADE = 8;
//      final int QUANTIDADE_STOCK = 9;
//      final int POSICAO = 10;
//      final int QUANTIDADE_MINIMA_PERMITIDA = 11;
//
//      reg = new FarmLoteProdutoHasLocalArmazenamento();
//      reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_3));
//      fichaDeStock = new FarmFichaStock();
//      fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_3));
//      loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
//
//      for (int cn = 0; cn < row.getLastCellNum(); cn++)
//      {
//         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
//
//         switch (cn)
//         {
//            case DESCRICAO_PRODUTO:
//               System.out.println("loteProduto.getFkIdProduto().getDescricao(): " + cell.getStringCellValue().trim());
//               descricaoProduto = cell.getStringCellValue().trim();
//               loteProduto.getFkIdProduto().setDescricao(descricaoProduto);
//
//               break;
//
//            case DOSAGEM:
//
//               try
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + cell.getStringCellValue());
//                  dosagem = cell.getStringCellValue().trim();
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               catch (Exception e)
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + (int) cell.getNumericCellValue());
//                  dosagem = (int) cell.getNumericCellValue() + "";
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               break;
//
//            case FK_ID_UNIDADE_MEDIDA:
//               System.out.println("loteProduto.getFkIdProduto().lote: " + cell.getStringCellValue());
//               FarmUnidadeMedida farmUnidadeMedida = new FarmUnidadeMedida();
//               farmUnidadeMedida.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
//               farmUnidadeMedida.setAbreviatura(cell.getStringCellValue().trim());
//               farmUnidadeMedida = farmUnidadeMedidaFacade.findUnidadeMedida(farmUnidadeMedida).get(0);
//               loteProduto.getFkIdProduto().setFkIdUnidadeMedida(farmUnidadeMedida);
//               break;
//
//            case FK_ID_LOTE_PRODUTO:
//               try
//               {
//                  System.out.println("numero de lote string: " + cell.getStringCellValue().trim());
//                  numeroLote = cell.getStringCellValue().trim();
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               catch (Exception ex)
//               {
//                  System.out.println("numero de lote inteiro: " + cell.getNumericCellValue());
//                  numeroLote = (int) cell.getNumericCellValue() + "";
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               break;
//
//            case FORMA_FARMACEUTICA:
//               System.out.println("forma: " + cell.getStringCellValue().trim());
//               FarmFormaFarmaceutica farmFormaFarmaceutica = new FarmFormaFarmaceutica();
//               farmFormaFarmaceutica.setDescricao(cell.getStringCellValue().trim());
//               farmFormaFarmaceutica = farmFormaFarmaceuticaFacade.findFormaFarmaceutica(farmFormaFarmaceutica).get(0);
//               loteProduto.getFkIdProduto().setFkIdFormaFarmaceutica(farmFormaFarmaceutica);
//               break;
//
//            case NOME_COMERCIAL:
//               System.out.println("nome comercial: " + cell.getStringCellValue());
//               nomeComercial = cell.getStringCellValue();
//               loteProduto.setNomeComercial(nomeComercial);
//               break;
//
//            case MARCA_LABORATORIO:
//               System.out.println("marca: " + cell.getStringCellValue().trim());
//               GrlMarcaProduto grlMarcaProduto = new GrlMarcaProduto();
//               grlMarcaProduto.setDescricao(cell.getStringCellValue().trim());
//               grlMarcaProduto = grlMarcaProdutoFacade.findMarcaProduto(grlMarcaProduto).get(0);
//               loteProduto.setFkIdMarca(grlMarcaProduto);
//               break;
//
//            case DATA_FABRICO:
//               try
//               {
//                  System.out.println("data fabrico: " + cell.getStringCellValue().trim());
//                  dataFabrico = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataFabrico(dataFabrico);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Fabrico do Lote.");
//               }
//
//               break;
//
//            case DATA_VALIDADE:
//               try
//               {
//                  dataValidade = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataValidade(dataValidade);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Validade do Lote.");
//               }
//               break;
//
//            case QUANTIDADE_STOCK:
//               quantidadeStock = (int) cell.getNumericCellValue();
//               reg.setQuantidadeStock(quantidadeStock);
//               fichaDeStock.setEntradas(quantidadeStock);
//               fichaDeStock.setQuantidadeRestante(quantidadeStock);
//               break;
//
//            case POSICAO:
//               System.out.println("posicao: " + cell.getStringCellValue());
//               posicao = cell.getStringCellValue();
//               reg.setPosicao(posicao);
//               break;
//
//            case QUANTIDADE_MINIMA_PERMITIDA:
//               System.out.println("quantidade_minima_permitida: " + cell.getNumericCellValue());
//               quantidade_minima_permitida = (int) cell.getNumericCellValue();
//               reg.setQuantidadeMinimaPermitida(quantidade_minima_permitida);
//               break;
//         }
//      }
//   }
//
//   public void lerCamposARM_4(
//           HSSFRow row,
//           FarmLoteProdutoHasLocalArmazenamento reg,
//           FarmLoteProduto loteProduto,
//           FarmFichaStock fichaDeStock)
//   {
//      String descricaoProduto, dosagem;
//      String numeroLote, nomeComercial;
//      Date dataFabrico, dataValidade;
//
//      int quantidadeStock;
//      int quantidade_minima_permitida;
//      String posicao;
//
//      final int DESCRICAO_PRODUTO = 0;
//      final int DOSAGEM = 1;
//      final int FK_ID_UNIDADE_MEDIDA = 2;
//      final int FORMA_FARMACEUTICA = 3;
//      final int FK_ID_LOTE_PRODUTO = 4;
//      final int NOME_COMERCIAL = 5;
//      final int MARCA_LABORATORIO = 6;
//      final int DATA_FABRICO = 7;
//      final int DATA_VALIDADE = 8;
//      final int QUANTIDADE_STOCK = 9;
//      final int POSICAO = 10;
//      final int QUANTIDADE_MINIMA_PERMITIDA = 11;
//
//      reg = new FarmLoteProdutoHasLocalArmazenamento();
//      /*
//       4;"Armazém 1"
//       5;"Armazém 3"
//       6;"Armazém 4"
//       */
//
//      reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_4));
//      fichaDeStock = new FarmFichaStock();
//      fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_ARM_4));
//      loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
//
//      for (int cn = 0; cn < row.getLastCellNum(); cn++)
//      {
//         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
//
//         switch (cn)
//         {
//            case DESCRICAO_PRODUTO:
//               System.out.println("loteProduto.getFkIdProduto().getDescricao(): " + cell.getStringCellValue().trim());
//               descricaoProduto = cell.getStringCellValue().trim();
//               loteProduto.getFkIdProduto().setDescricao(descricaoProduto);
//
//               break;
//
//            case DOSAGEM:
//
//               try
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + cell.getStringCellValue());
//                  dosagem = cell.getStringCellValue().trim();
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               catch (Exception e)
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + (int) cell.getNumericCellValue());
//                  dosagem = (int) cell.getNumericCellValue() + "";
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               break;
//
//            case FK_ID_UNIDADE_MEDIDA:
//               System.out.println("loteProduto.getFkIdProduto().lote: " + cell.getStringCellValue());
//               FarmUnidadeMedida farmUnidadeMedida = new FarmUnidadeMedida();
//               farmUnidadeMedida.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
//               farmUnidadeMedida.setAbreviatura(cell.getStringCellValue().trim());
//               farmUnidadeMedida = farmUnidadeMedidaFacade.findUnidadeMedida(farmUnidadeMedida).get(0);
//               loteProduto.getFkIdProduto().setFkIdUnidadeMedida(farmUnidadeMedida);
//               break;
//
//            case FK_ID_LOTE_PRODUTO:
//               try
//               {
//                  System.out.println("numero de lote string: " + cell.getStringCellValue().trim());
//                  numeroLote = cell.getStringCellValue().trim();
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               catch (Exception ex)
//               {
//                  System.out.println("numero de lote inteiro: " + cell.getNumericCellValue());
//                  numeroLote = (int) cell.getNumericCellValue() + "";
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               break;
//
//            case FORMA_FARMACEUTICA:
//               FarmFormaFarmaceutica farmFormaFarmaceutica = new FarmFormaFarmaceutica();
//               farmFormaFarmaceutica.setDescricao(cell.getStringCellValue().trim());
//               farmFormaFarmaceutica = farmFormaFarmaceuticaFacade.findFormaFarmaceutica(farmFormaFarmaceutica).get(0);
//               loteProduto.getFkIdProduto().setFkIdFormaFarmaceutica(farmFormaFarmaceutica);
//               break;
//
//            case NOME_COMERCIAL:
//               System.out.println("nome comercial: " + cell.getStringCellValue());
//               nomeComercial = cell.getStringCellValue();
//               loteProduto.setNomeComercial(nomeComercial);
//               break;
//
//            case MARCA_LABORATORIO:
//               System.out.println("marca: " + cell.getStringCellValue().trim());
//               GrlMarcaProduto grlMarcaProduto = new GrlMarcaProduto();
//               grlMarcaProduto.setDescricao(cell.getStringCellValue().trim());
//               grlMarcaProduto = grlMarcaProdutoFacade.findMarcaProduto(grlMarcaProduto).get(0);
//               loteProduto.setFkIdMarca(grlMarcaProduto);
//               break;
//
//            case DATA_FABRICO:
//               try
//               {
//                  System.out.println("data fabrico: " + cell.getStringCellValue().trim());
//                  dataFabrico = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataFabrico(dataFabrico);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Fabrico do Lote.");
//               }
//
//               break;
//
//            case DATA_VALIDADE:
//               try
//               {
//                  dataValidade = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataValidade(dataValidade);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Validade do Lote.");
//               }
//               break;
//
//            case QUANTIDADE_STOCK:
//               System.out.println("qtd stock : " + (int) cell.getNumericCellValue());
//               quantidadeStock = (int) cell.getNumericCellValue();
//               reg.setQuantidadeStock(quantidadeStock);
//               fichaDeStock.setEntradas(quantidadeStock);
//               fichaDeStock.setQuantidadeRestante(quantidadeStock);
//               break;
//
//            case POSICAO:
//               System.out.println("posicao: " + cell.getStringCellValue());
//               posicao = cell.getStringCellValue();
//               reg.setPosicao(posicao);
//               break;
//
//            case QUANTIDADE_MINIMA_PERMITIDA:
//               System.out.println("quantidade_minima_permitida: " + cell.getNumericCellValue());
//               quantidade_minima_permitida = (int) cell.getNumericCellValue();
//               reg.setQuantidadeMinimaPermitida(quantidade_minima_permitida);
//               break;
//         }
//      }
//   }
//
//   public void lerCamposFarmInt(
//           HSSFRow row,
//           FarmLoteProdutoHasLocalArmazenamento reg,
//           FarmLoteProduto loteProduto,
//           FarmFichaStock fichaDeStock)
//   {
//      String descricaoProduto, dosagem;
//      String numeroLote, nomeComercial;
//      Date dataFabrico, dataValidade;
//
//      int quantidadeStock;
//      int quantidade_minima_permitida;
//      String posicao;
//
//      final int DESCRICAO_PRODUTO = 0;
//      final int DOSAGEM = 1;
//      final int FK_ID_UNIDADE_MEDIDA = 2;
//      final int FORMA_FARMACEUTICA = 3;
//      final int FK_ID_LOTE_PRODUTO = 4;
//      final int NOME_COMERCIAL = 5;
//      final int MARCA_LABORATORIO = 6;
//      final int DATA_FABRICO = 7;
//      final int DATA_VALIDADE = 8;
//      final int QUANTIDADE_STOCK = 9;
//      final int POSICAO = 10;
//      final int QUANTIDADE_MINIMA_PERMITIDA = 11;
//
//      reg = new FarmLoteProdutoHasLocalArmazenamento();
//      /*
//       4;"Armazém 1"
//       5;"Armazém 3"
//       6;"Armazém 4"
//       */
//
//      reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_INT));
//      fichaDeStock = new FarmFichaStock();
//      fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_INT));
//      loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
//
//      for (int cn = 0; cn < row.getLastCellNum(); cn++)
//      {
//         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
//
//         switch (cn)
//         {
//            case DESCRICAO_PRODUTO:
//               System.out.println("loteProduto.getFkIdProduto().getDescricao(): " + cell.getStringCellValue().trim());
//               descricaoProduto = cell.getStringCellValue().trim();
//               loteProduto.getFkIdProduto().setDescricao(descricaoProduto);
//
//               break;
//
//            case DOSAGEM:
//
//               try
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + cell.getStringCellValue());
//                  dosagem = cell.getStringCellValue().trim();
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               catch (Exception e)
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + (int) cell.getNumericCellValue());
//                  dosagem = (int) cell.getNumericCellValue() + "";
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               break;
//
//            case FK_ID_UNIDADE_MEDIDA:
//               System.out.println("loteProduto.getFkIdProduto().lote: " + cell.getStringCellValue());
//               FarmUnidadeMedida farmUnidadeMedida = new FarmUnidadeMedida();
//               farmUnidadeMedida.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
//               farmUnidadeMedida.setAbreviatura(cell.getStringCellValue().trim());
//               farmUnidadeMedida = farmUnidadeMedidaFacade.findUnidadeMedida(farmUnidadeMedida).get(0);
//               loteProduto.getFkIdProduto().setFkIdUnidadeMedida(farmUnidadeMedida);
//               break;
//
//            case FK_ID_LOTE_PRODUTO:
//               try
//               {
//                  System.out.println("numero de lote string: " + cell.getStringCellValue().trim());
//                  numeroLote = cell.getStringCellValue().trim();
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               catch (Exception ex)
//               {
//                  System.out.println("numero de lote inteiro: " + cell.getNumericCellValue());
//                  numeroLote = (int) cell.getNumericCellValue() + "";
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               break;
//
//            case FORMA_FARMACEUTICA:
//               FarmFormaFarmaceutica farmFormaFarmaceutica = new FarmFormaFarmaceutica();
//               farmFormaFarmaceutica.setDescricao(cell.getStringCellValue().trim());
//               farmFormaFarmaceutica = farmFormaFarmaceuticaFacade.findFormaFarmaceutica(farmFormaFarmaceutica).get(0);
//               loteProduto.getFkIdProduto().setFkIdFormaFarmaceutica(farmFormaFarmaceutica);
//               break;
//
//            case NOME_COMERCIAL:
//               System.out.println("nome comercial: " + cell.getStringCellValue());
//               nomeComercial = cell.getStringCellValue();
//               loteProduto.setNomeComercial(nomeComercial);
//               break;
//
//            case MARCA_LABORATORIO:
//               System.out.println("marca: " + cell.getStringCellValue().trim());
//               GrlMarcaProduto grlMarcaProduto = new GrlMarcaProduto();
//               grlMarcaProduto.setDescricao(cell.getStringCellValue().trim());
//               grlMarcaProduto = grlMarcaProdutoFacade.findMarcaProduto(grlMarcaProduto).get(0);
//               loteProduto.setFkIdMarca(grlMarcaProduto);
//               break;
//
//            case DATA_FABRICO:
//               try
//               {
//                  System.out.println("data fabrico: " + cell.getStringCellValue().trim());
//                  dataFabrico = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataFabrico(dataFabrico);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Fabrico do Lote.");
//               }
//
//               break;
//
//            case DATA_VALIDADE:
//               try
//               {
//                  dataValidade = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataValidade(dataValidade);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Validade do Lote.");
//               }
//               break;
//
//            case QUANTIDADE_STOCK:
//               System.out.println("qtd stock: " + cell.getNumericCellValue());
//               quantidadeStock = (int) cell.getNumericCellValue();
//               reg.setQuantidadeStock(quantidadeStock);
//               fichaDeStock.setEntradas(quantidadeStock);
//               fichaDeStock.setQuantidadeRestante(quantidadeStock);
//               break;
//
//            case POSICAO:
//               System.out.println("posicao: " + cell.getStringCellValue());
//               posicao = cell.getStringCellValue();
//               reg.setPosicao(posicao);
//               break;
//
//            case QUANTIDADE_MINIMA_PERMITIDA:
//               System.out.println("quantidade_minima_permitida: " + cell.getNumericCellValue());
//               quantidade_minima_permitida = (int) cell.getNumericCellValue();
//               reg.setQuantidadeMinimaPermitida(quantidade_minima_permitida);
//               break;
//         }
//      }
//   }
//
//   public void lerCamposFarmExt(
//           HSSFRow row,
//           FarmLoteProdutoHasLocalArmazenamento reg,
//           FarmLoteProduto loteProduto,
//           FarmFichaStock fichaDeStock)
//   {
//      String descricaoProduto, dosagem;
//      String numeroLote, nomeComercial;
//      Date dataFabrico, dataValidade;
//
//      int quantidadeStock;
//      int quantidade_minima_permitida;
//      String posicao;
//
//      final int DESCRICAO_PRODUTO = 0;
//      final int DOSAGEM = 1;
//      final int FK_ID_UNIDADE_MEDIDA = 2;
//      final int FORMA_FARMACEUTICA = 3;
//      final int FK_ID_LOTE_PRODUTO = 4;
//      final int NOME_COMERCIAL = 5;
//      final int MARCA_LABORATORIO = 6;
//      final int DATA_FABRICO = 7;
//      final int DATA_VALIDADE = 8;
//      final int QUANTIDADE_STOCK = 9;
//      final int POSICAO = 10;
//      final int QUANTIDADE_MINIMA_PERMITIDA = 11;
//
//      reg = new FarmLoteProdutoHasLocalArmazenamento();
//      reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_EXT));
//      fichaDeStock = new FarmFichaStock();
//      fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_EXT));
//      loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
//
//      for (int cn = 0; cn < row.getLastCellNum(); cn++)
//      {
//         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
//
//         switch (cn)
//         {
//            case DESCRICAO_PRODUTO:
//               System.out.println("loteProduto.getFkIdProduto().getDescricao(): " + cell.getStringCellValue().trim());
//               descricaoProduto = cell.getStringCellValue().trim();
//               loteProduto.getFkIdProduto().setDescricao(descricaoProduto);
//
//               break;
//
//            case DOSAGEM:
//
//               try
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + cell.getStringCellValue());
//                  dosagem = cell.getStringCellValue().trim();
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               catch (Exception e)
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + (int) cell.getNumericCellValue());
//                  dosagem = (int) cell.getNumericCellValue() + "";
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               break;
//
//            case FK_ID_UNIDADE_MEDIDA:
//               System.out.println("loteProduto.getFkIdProduto().lote: " + cell.getStringCellValue());
//               FarmUnidadeMedida farmUnidadeMedida = new FarmUnidadeMedida();
//               farmUnidadeMedida.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
//               farmUnidadeMedida.setAbreviatura(cell.getStringCellValue().trim());
//               farmUnidadeMedida = farmUnidadeMedidaFacade.findUnidadeMedida(farmUnidadeMedida).get(0);
//               loteProduto.getFkIdProduto().setFkIdUnidadeMedida(farmUnidadeMedida);
//               break;
//
//            case FK_ID_LOTE_PRODUTO:
//               try
//               {
//                  System.out.println("numero de lote string: " + cell.getStringCellValue().trim());
//                  numeroLote = cell.getStringCellValue().trim();
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               catch (Exception ex)
//               {
//                  System.out.println("numero de lote inteiro: " + cell.getNumericCellValue());
//                  numeroLote = (int) cell.getNumericCellValue() + "";
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               break;
//
//            case FORMA_FARMACEUTICA:
//               FarmFormaFarmaceutica farmFormaFarmaceutica = new FarmFormaFarmaceutica();
//               farmFormaFarmaceutica.setDescricao(cell.getStringCellValue().trim());
//               farmFormaFarmaceutica = farmFormaFarmaceuticaFacade.findFormaFarmaceutica(farmFormaFarmaceutica).get(0);
//               loteProduto.getFkIdProduto().setFkIdFormaFarmaceutica(farmFormaFarmaceutica);
//               break;
//
//            case NOME_COMERCIAL:
//               System.out.println("nome comercial: " + cell.getStringCellValue());
//               nomeComercial = cell.getStringCellValue();
//               loteProduto.setNomeComercial(nomeComercial);
//               break;
//
//            case MARCA_LABORATORIO:
//               System.out.println("marca: " + cell.getStringCellValue().trim());
//               GrlMarcaProduto grlMarcaProduto = new GrlMarcaProduto();
//               grlMarcaProduto.setDescricao(cell.getStringCellValue().trim());
//               grlMarcaProduto = grlMarcaProdutoFacade.findMarcaProduto(grlMarcaProduto).get(0);
//               loteProduto.setFkIdMarca(grlMarcaProduto);
//               break;
//
//            case DATA_FABRICO:
//               try
//               {
//                  System.out.println("data fabrico: " + cell.getStringCellValue().trim());
//                  dataFabrico = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataFabrico(dataFabrico);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Fabrico do Lote.");
//               }
//
//               break;
//
//            case DATA_VALIDADE:
//               try
//               {
//                  dataValidade = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataValidade(dataValidade);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Validade do Lote.");
//               }
//               break;
//
//            case QUANTIDADE_STOCK:
//               quantidadeStock = (int) cell.getNumericCellValue();
//               reg.setQuantidadeStock(quantidadeStock);
//               fichaDeStock.setEntradas(quantidadeStock);
//               fichaDeStock.setQuantidadeRestante(quantidadeStock);
//               break;
//
//            case POSICAO:
//               System.out.println("posicao: " + cell.getStringCellValue());
//               posicao = cell.getStringCellValue();
//               reg.setPosicao(posicao);
//               break;
//
//            case QUANTIDADE_MINIMA_PERMITIDA:
//               System.out.println("quantidade_minima_permitida: " + cell.getNumericCellValue());
//               quantidade_minima_permitida = (int) cell.getNumericCellValue();
//               reg.setQuantidadeMinimaPermitida(quantidade_minima_permitida);
//               break;
//         }
//      }
//   }
//
//   public void lerCamposFarmInf(
//           HSSFRow row,
//           FarmLoteProdutoHasLocalArmazenamento reg,
//           FarmLoteProduto loteProduto,
//           FarmFichaStock fichaDeStock)
//   {
//      String descricaoProduto, dosagem;
//      String numeroLote, nomeComercial;
//      Date dataFabrico, dataValidade;
//
//      int quantidadeStock;
//      int quantidade_minima_permitida;
//      String posicao;
//
//      final int DESCRICAO_PRODUTO = 0;
//      final int DOSAGEM = 1;
//      final int FK_ID_UNIDADE_MEDIDA = 2;
//      final int FORMA_FARMACEUTICA = 3;
//      final int FK_ID_LOTE_PRODUTO = 4;
//      final int NOME_COMERCIAL = 5;
//      final int MARCA_LABORATORIO = 6;
//      final int DATA_FABRICO = 7;
//      final int DATA_VALIDADE = 8;
//      final int QUANTIDADE_STOCK = 9;
//      final int POSICAO = 10;
//      final int QUANTIDADE_MINIMA_PERMITIDA = 11;
//
//      reg = new FarmLoteProdutoHasLocalArmazenamento();
//      reg.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_INF));
//      fichaDeStock = new FarmFichaStock();
//      fichaDeStock.setFkIdLocalArmazenamento(new FarmLocalArmazenamento(localDeArmazenamento_FARM_INF));
//      loteProduto = new FarmProdutoGerirLotesBean().getInstanciaLoteProduto();
//
//      for (int cn = 0; cn < row.getLastCellNum(); cn++)
//      {
//         HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
//
//         switch (cn)
//         {
//            case DESCRICAO_PRODUTO:
//               System.out.println("loteProduto.getFkIdProduto().getDescricao(): " + cell.getStringCellValue().trim());
//               descricaoProduto = cell.getStringCellValue().trim();
//               loteProduto.getFkIdProduto().setDescricao(descricaoProduto);
//
//               break;
//
//            case DOSAGEM:
//
//               try
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + cell.getStringCellValue());
//                  dosagem = cell.getStringCellValue().trim();
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               catch (Exception e)
//               {
//                  System.out.println("loteProduto.getFkIdProduto().getDosagem(): " + (int) cell.getNumericCellValue());
//                  dosagem = (int) cell.getNumericCellValue() + "";
//                  loteProduto.getFkIdProduto().setDosagem(dosagem);
//               }
//               break;
//
//            case FK_ID_UNIDADE_MEDIDA:
//               System.out.println("loteProduto.getFkIdProduto().lote: " + cell.getStringCellValue());
//               FarmUnidadeMedida farmUnidadeMedida = new FarmUnidadeMedida();
//               farmUnidadeMedida.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
//               farmUnidadeMedida.setAbreviatura(cell.getStringCellValue().trim());
//               farmUnidadeMedida = farmUnidadeMedidaFacade.findUnidadeMedida(farmUnidadeMedida).get(0);
//               loteProduto.getFkIdProduto().setFkIdUnidadeMedida(farmUnidadeMedida);
//               break;
//
//            case FK_ID_LOTE_PRODUTO:
//               try
//               {
//                  System.out.println("numero de lote string: " + cell.getStringCellValue().trim());
//                  numeroLote = cell.getStringCellValue().trim();
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               catch (Exception ex)
//               {
//                  System.out.println("numero de lote inteiro: " + cell.getNumericCellValue());
//                  numeroLote = (int) cell.getNumericCellValue() + "";
//                  loteProduto.setNumeroLote(numeroLote);
//                  System.out.println("num de lte: " + loteProduto.getNumeroLote());
//               }
//               break;
//
//            case FORMA_FARMACEUTICA:
//               FarmFormaFarmaceutica farmFormaFarmaceutica = new FarmFormaFarmaceutica();
//               farmFormaFarmaceutica.setDescricao(cell.getStringCellValue().trim());
//               farmFormaFarmaceutica = farmFormaFarmaceuticaFacade.findFormaFarmaceutica(farmFormaFarmaceutica).get(0);
//               loteProduto.getFkIdProduto().setFkIdFormaFarmaceutica(farmFormaFarmaceutica);
//               break;
//
//            case NOME_COMERCIAL:
//               System.out.println("nome comercial: " + cell.getStringCellValue());
//               nomeComercial = cell.getStringCellValue();
//               loteProduto.setNomeComercial(nomeComercial);
//               break;
//
//            case MARCA_LABORATORIO:
//               System.out.println("marca: " + cell.getStringCellValue().trim());
//               GrlMarcaProduto grlMarcaProduto = new GrlMarcaProduto();
//               grlMarcaProduto.setDescricao(cell.getStringCellValue().trim());
//               grlMarcaProduto = grlMarcaProdutoFacade.findMarcaProduto(grlMarcaProduto).get(0);
//               loteProduto.setFkIdMarca(grlMarcaProduto);
//               break;
//
//            case DATA_FABRICO:
//               try
//               {
//                  System.out.println("data fabrico: " + cell.getStringCellValue().trim());
//                  dataFabrico = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataFabrico(dataFabrico);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Fabrico do Lote.");
//               }
//
//               break;
//
//            case DATA_VALIDADE:
//               try
//               {
//                  dataValidade = new SimpleDateFormat("yyyy/mm/dd").parse(cell.getStringCellValue().trim());
//                  loteProduto.setDataValidade(dataValidade);
//               }
//               catch (ParseException ex)
//               {
//                  System.out.println("Impossivel Data de Validade do Lote.");
//               }
//               break;
//
//            case QUANTIDADE_STOCK:
//               quantidadeStock = (int) cell.getNumericCellValue();
//               reg.setQuantidadeStock(quantidadeStock);
//               fichaDeStock.setEntradas(quantidadeStock);
//               fichaDeStock.setQuantidadeRestante(quantidadeStock);
//               break;
//
//            case POSICAO:
//               System.out.println("posicao: " + cell.getStringCellValue());
//               posicao = cell.getStringCellValue();
//               reg.setPosicao(posicao);
//               break;
//
//            case QUANTIDADE_MINIMA_PERMITIDA:
//               System.out.println("quantidade_minima_permitida: " + cell.getNumericCellValue());
//               quantidade_minima_permitida = (int) cell.getNumericCellValue();
//               reg.setQuantidadeMinimaPermitida(quantidade_minima_permitida);
//               break;
//         }
//      }
//   }

   public boolean naoExisteStock()
   {
      return farmLoteProdutoHasLocalArmazenamentoFacade.findAll().isEmpty();
   }

}
