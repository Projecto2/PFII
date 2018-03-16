/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean.carregamentoExcel;

import entidade.DiagCategoriaExame;
import entidade.DiagSubcategoriaExame;
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
import sessao.DiagSubcategoriaExameFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagSubcategoriaExameExcelBean implements Serializable
{

    @EJB
    private DiagSubcategoriaExameFacade diagSubcategoriaExameFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of DiagSubcategoriaExameExcelBean
     */
    public DiagSubcategoriaExameExcelBean()
    {
    }

    public static DiagSubcategoriaExameExcelBean getInstanciaBean()
    {
        return (DiagSubcategoriaExameExcelBean) GeradorCodigo.getInstanciaBean("diagSubcategoriaExameExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarSubcategoriaExameTabela()
    {
        Date dataSubcategoriaExameTabela = this.diagUpdatesFacade.dataSubcategoriaExameTabela();

        Date dataSubcategoriaExameXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_SUBCATEGORIA_EXAME_DIAG);

        if (this.diagSubcategoriaExameFacade.isSubcategoriaExameTabelaEmpty() || dataSubcategoriaExameTabela == null);
        else if (!diagSubcategoriaExameFacade.isSubcategoriaExameTabelaEmpty() && (dataSubcategoriaExameXLSFile != null && dataSubcategoriaExameXLSFile.compareTo(dataSubcategoriaExameTabela) <= 0))
        {
            return;
        }

        if (lerSubcategoriaExameTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoSubcategoriaExameTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerSubcategoriaExameTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_SUBCATEGORIA_EXAME_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("subcategorias");

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
            DiagSubcategoriaExame reg = null;

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

                escreverSubcategoriaExameTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverSubcategoriaExameTabela(DiagSubcategoriaExame reg)
    {

        if (diagSubcategoriaExameFacade.find(reg.getPkIdSubcategoriaExame()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagSubcategoriaExame lerCampos(HSSFRow row)
    {
        int pk_id_subcategoria_exame;
        String descricao_subcategoria;
        int fk_id_categoria;

        final int PK_ID_SUBCATEGORIA_EXAME = 0;
        final int DESCRICAO_SUBCATEGORIA = 1;
        final int FK_ID_CATEGORIA = 2;

        DiagSubcategoriaExame reg = new DiagSubcategoriaExame();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_SUBCATEGORIA_EXAME:
                    pk_id_subcategoria_exame = (int) cell.getNumericCellValue();
                    reg.setPkIdSubcategoriaExame(pk_id_subcategoria_exame);
                    break;

                case DESCRICAO_SUBCATEGORIA:
                    descricao_subcategoria = cell.getStringCellValue().trim();
                    reg.setDescricaoSubcategoria(descricao_subcategoria);
                    break;

                case FK_ID_CATEGORIA:
                    if (cell == null)
                    {
                        reg.setFkIdCategoria(null);
                    }
                    else
                    {
                        fk_id_categoria = (int) cell.getNumericCellValue();
                        reg.setFkIdCategoria(new DiagCategoriaExame(fk_id_categoria));
                    }
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagSubcategoriaExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagSubcategoriaExameFacade.create(reg);
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

    public boolean editRegister(DiagSubcategoriaExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagSubcategoriaExameFacade.edit(reg);
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
