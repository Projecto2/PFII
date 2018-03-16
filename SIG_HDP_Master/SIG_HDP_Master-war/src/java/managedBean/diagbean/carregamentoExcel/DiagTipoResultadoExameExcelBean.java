/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.diagbean.carregamentoExcel;

import entidade.DiagTipoResultadoExame;
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
import sessao.DiagTipoResultadoExameFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipoResultadoExameExcelBean implements Serializable
{
@EJB
    private DiagTipoResultadoExameFacade diagTipoResultadoExameFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    /**
     * Creates a new instance of DiagTipoResultadoExameExcelBean
     */
    public DiagTipoResultadoExameExcelBean()
    {
    }
    
    public static DiagTipoResultadoExameExcelBean getInstanciaBean()
    {
        return (DiagTipoResultadoExameExcelBean) GeradorCodigo.getInstanciaBean("diagTipoResultadoExameExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarTipoResultadoExameTabela()
    {
        Date dataTipoResultadoExameTabela = this.diagUpdatesFacade.dataTipoResultadoExameTabela();

        Date dataTipoResultadoExameXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_TIPO_RESULTADO_EXAME_DIAG);

        if (this.diagTipoResultadoExameFacade.isTipoResultadoExameTabelaEmpty() || dataTipoResultadoExameTabela == null);
        else if (!diagTipoResultadoExameFacade.isTipoResultadoExameTabelaEmpty() && (dataTipoResultadoExameXLSFile != null && dataTipoResultadoExameXLSFile.compareTo(dataTipoResultadoExameTabela) <= 0))
        {
            return;
        }

        if (lerTipoResultadoExameTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoTipoResultadoExameTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerTipoResultadoExameTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_TIPO_RESULTADO_EXAME_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipoResultadoExame");

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
            DiagTipoResultadoExame reg = null;

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

                escreverTipoResultadoExameTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverTipoResultadoExameTabela(DiagTipoResultadoExame reg)
    {

        if (diagTipoResultadoExameFacade.find(reg.getPkIdTipoResultadoExame()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagTipoResultadoExame lerCampos(HSSFRow row)
    {
        int pk_id_tipo_resultado_exame;
        String descricao_tipo_resultado_exame;

        final int PK_ID_TIPO_RESULTADO_EXAME = 0;
        final int DESCRICAO_TIPO_RESULTADO_EXAME = 1;

        DiagTipoResultadoExame reg = new DiagTipoResultadoExame();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_TIPO_RESULTADO_EXAME:
                    pk_id_tipo_resultado_exame = (int) cell.getNumericCellValue();
                    reg.setPkIdTipoResultadoExame(pk_id_tipo_resultado_exame);
                    break;

                case DESCRICAO_TIPO_RESULTADO_EXAME:
                    descricao_tipo_resultado_exame = cell.getStringCellValue().trim();
                    reg.setDescricaoTipoResultadoExame(descricao_tipo_resultado_exame);
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagTipoResultadoExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagTipoResultadoExameFacade.create(reg);
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

    public boolean editRegister(DiagTipoResultadoExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagTipoResultadoExameFacade.edit(reg);
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
