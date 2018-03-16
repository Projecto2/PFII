/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean.carregamentoExcel;

import entidade.DiagCategoriaExame;
import entidade.DiagExame;
import entidade.DiagSectorExame;
import entidade.DiagSubcategoriaExame;
import entidade.DiagTipoResultadoExame;
import entidade.FarmUnidadeMedida;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.DiagExameFacade;
import sessao.DiagUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagExameExcelBean implements Serializable
{

    @EJB
    private DiagExameFacade diagExameFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of DiagExameExcelBean
     */
    public DiagExameExcelBean()
    {
    }

    public static DiagExameExcelBean getInstanciaBean()
    {
        return (DiagExameExcelBean) GeradorCodigo.getInstanciaBean("diagExameExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarExameTabela()
    {
        Date dataExameTabela = this.diagUpdatesFacade.dataExameTabela();

        Date dataExameXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_CATEGORIA_EXAME_DIAG);

        if (this.diagExameFacade.isExameTabelaEmpty() || dataExameTabela == null);
        else if (!diagExameFacade.isExameTabelaEmpty() && (dataExameXLSFile != null && dataExameXLSFile.compareTo(dataExameTabela) <= 0))
        {
            return;
        }

        if (lerExameTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoExameTabela();
        }
    }

    public boolean lerExameTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_EXAME_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("exames");

            HSSFRow row;
//            HSSFCell cell;

            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
//            row = (HSSFRow) rows.next();
//            Date dataExameXLSFile = FileManagerGeral.lerVersaoTabela(row, util.diag.Defs.FILE_EXAME_DIAG);
//
//            Date dataExameTabela = this.diagUpdatesFacade.dataExameTabela();
//
//            if (dataExameTabela == null);
//            else if (dataExameXLSFile.compareTo(dataExameTabela) <= 0)
//            {
//                return false;
//            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            DiagExame reg = null;

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

                escreverExameTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverExameTabela(DiagExame reg)
    {

        if (diagExameFacade.find(reg.getPkIdExame()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagExame lerCampos(HSSFRow row)
    {
        int pk_id_exame;
        String descricao_exame;
        float valor_referencia_minimo;
        float valor_referencia_maximo;
        int fk_id_categoria_exame;
        int fk_id_subcategoria_exame;
        int fk_id_unidade_medida;
        int fk_id_tipo_resultado;

        final int PK_ID_EXAME = 0;
        final int DESCRICAO_EXAME = 1;
        final int VALOR_REFERENCIA_MINIMO = 2;
        final int VALOR_REFERENCIA_MAXIMO = 3;
        final int FK_ID_CATEGORIA_EXAME = 4;
        final int FK_ID_SUBCATEGORIA_EXAME = 5;
        final int FK_ID_UNIDADE_MEDIDA = 6;
        final int FK_ID_TIPO_RESULTADO = 7;

        DiagExame reg = new DiagExame();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_EXAME:
                    pk_id_exame = (int) cell.getNumericCellValue();
                    reg.setPkIdExame(pk_id_exame);
                    break;

                case DESCRICAO_EXAME:
                    descricao_exame = cell.getStringCellValue().trim();
                    reg.setDescricaoExame(descricao_exame);
                    break;

                case VALOR_REFERENCIA_MINIMO:
                    if (cell != null)
                    {
                        valor_referencia_minimo = (float) cell.getNumericCellValue();
                        reg.setValorReferenciaMinimo(valor_referencia_minimo);
                    }
                    break;

                case VALOR_REFERENCIA_MAXIMO:
                    if (cell != null)
                    {
                        valor_referencia_maximo = (float) cell.getNumericCellValue();
                        reg.setValorReferenciaMaximo(valor_referencia_maximo);
                    }
                    break;

                case FK_ID_CATEGORIA_EXAME:
                    if (cell == null)
                    {
                        reg.setFkIdCategoriaExame(null);
                    }
                    else
                    {
                        fk_id_categoria_exame = (int) cell.getNumericCellValue();
                        reg.setFkIdCategoriaExame(new DiagCategoriaExame(fk_id_categoria_exame));
                    }
                    break;

                case FK_ID_SUBCATEGORIA_EXAME:
                    if (cell == null)
                    {
                        reg.setFkIdSubcategoriaExame(null);
                    }
                    else
                    {
                        fk_id_subcategoria_exame = (int) cell.getNumericCellValue();
                        reg.setFkIdSubcategoriaExame(new DiagSubcategoriaExame(fk_id_subcategoria_exame));
                    }
                    break;

                case FK_ID_UNIDADE_MEDIDA:
                    if (cell == null)
                    {
                        reg.setFkIdUnidadeMedida(null);
                    }
                    else
                    {
                        fk_id_unidade_medida = (int) cell.getNumericCellValue();
                        reg.setFkIdUnidadeMedida(new FarmUnidadeMedida(fk_id_unidade_medida));
                    }
                    break;

                case FK_ID_TIPO_RESULTADO:
                    if (cell == null)
                    {
                        reg.setFkIdTipoResultado(null);
                    }
                    else
                    {
                        fk_id_tipo_resultado = (int) cell.getNumericCellValue();
                        reg.setFkIdTipoResultado(new DiagTipoResultadoExame(fk_id_tipo_resultado));
                    }
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagExameFacade.create(reg);
            this.userTransaction.commit();
            return true;
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                System.out.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.out.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

    public boolean editRegister(DiagExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagExameFacade.edit(reg);
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
                System.out.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

}
