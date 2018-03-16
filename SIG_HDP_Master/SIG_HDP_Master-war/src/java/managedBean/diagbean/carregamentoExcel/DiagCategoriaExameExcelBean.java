/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean.carregamentoExcel;

import entidade.DiagCategoriaExame;
import entidade.DiagSectorExame;
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
import sessao.DiagCategoriaExameFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagCategoriaExameExcelBean implements Serializable
{

    @EJB
    private DiagCategoriaExameFacade diagCategoriaExameFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of DiagCategoriaExameExcelBean
     */
    public DiagCategoriaExameExcelBean()
    {
    }

    public static DiagCategoriaExameExcelBean getInstanciaBean()
    {
        return (DiagCategoriaExameExcelBean) GeradorCodigo.getInstanciaBean("diagCategoriaExameExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarCategoriaExameTabela()
    {
        Date dataCategoriaExameTabela = this.diagUpdatesFacade.dataCategoriaExameTabela();

        Date dataCategoriaExameXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_CATEGORIA_EXAME_DIAG);

        if (this.diagCategoriaExameFacade.isCategoriaExameTabelaEmpty() || dataCategoriaExameTabela == null);
        else if (!diagCategoriaExameFacade.isCategoriaExameTabelaEmpty() && (dataCategoriaExameXLSFile != null && dataCategoriaExameXLSFile.compareTo(dataCategoriaExameTabela) <= 0))
        {
            return;
        }

        if (lerCategoriaExameTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoCategoriaExameTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerCategoriaExameTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_CATEGORIA_EXAME_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("categorias");

            HSSFRow row;
//            HSSFCell cell;

            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            DiagCategoriaExame reg = null;

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

                escreverCategoriaExameTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverCategoriaExameTabela(DiagCategoriaExame reg)
    {

        if (diagCategoriaExameFacade.find(reg.getPkIdCategoria()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagCategoriaExame lerCampos(HSSFRow row)
    {
        int pk_id_categoria;
        String descricao_categoria;
        int fk_id_sector;

        final int PK_ID_CATEGORIA = 0;
        final int DESCRICAO_CATEGORIA = 1;
        final int FK_ID_SECTOR = 2;

        DiagCategoriaExame reg = new DiagCategoriaExame();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_CATEGORIA:
                    pk_id_categoria = (int) cell.getNumericCellValue();
                    reg.setPkIdCategoria(pk_id_categoria);
                    break;

                case DESCRICAO_CATEGORIA:
                    descricao_categoria = cell.getStringCellValue().trim();
                    reg.setDescricaoCategoria(descricao_categoria);
                    break;

                case FK_ID_SECTOR:
                    if (cell == null)
                    {
                        reg.setFkIdSector(null);
                    }
                    else
                    {
                        fk_id_sector = (int) cell.getNumericCellValue();
                        reg.setFkIdSector(new DiagSectorExame(fk_id_sector));
                    }
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagCategoriaExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagCategoriaExameFacade.create(reg);
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

    public boolean editRegister(DiagCategoriaExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagCategoriaExameFacade.edit(reg);
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
