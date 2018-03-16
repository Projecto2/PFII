/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.diagbean.carregamentoExcel;

import entidade.DiagResultadoExameCandidato;
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
import sessao.DiagResultadoExameCandidatoFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagResultadoExameCandidatoExcelBean implements Serializable
{
@EJB
    private DiagResultadoExameCandidatoFacade diagResultadoExameCandidatoFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    /**
     * Creates a new instance of DiagResultadoExameCandidatoExcelBean
     */
    public DiagResultadoExameCandidatoExcelBean()
    {
    }
    
    public static DiagResultadoExameCandidatoExcelBean getInstanciaBean()
    {
        return (DiagResultadoExameCandidatoExcelBean) GeradorCodigo.getInstanciaBean("diagResultadoExameCandidatoExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarResultadoExameCandidatoTabela()
    {
        Date dataResultadoExameCandidatoTabela = this.diagUpdatesFacade.dataResultadoExameCandidatoTabela();

        Date dataResultadoExameCandidatoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_RESULTADO_EXAME_CANDIDATO_DIAG);

        if (this.diagResultadoExameCandidatoFacade.isResultadoExameCandidatoTabelaEmpty() || dataResultadoExameCandidatoTabela == null);
        else if (!diagResultadoExameCandidatoFacade.isResultadoExameCandidatoTabelaEmpty() && (dataResultadoExameCandidatoXLSFile != null && dataResultadoExameCandidatoXLSFile.compareTo(dataResultadoExameCandidatoTabela) <= 0))
        {
            return;
        }

        if (lerResultadoExameCandidatoTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoResultadoExameCandidatoTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerResultadoExameCandidatoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_RESULTADO_EXAME_CANDIDATO_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("resultadoExameCandidato");

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
            DiagResultadoExameCandidato reg = null;

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

                escreverResultadoExameCandidatoTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverResultadoExameCandidatoTabela(DiagResultadoExameCandidato reg)
    {

        if (diagResultadoExameCandidatoFacade.find(reg.getPkIdResultadoExameCandidato()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagResultadoExameCandidato lerCampos(HSSFRow row)
    {
        int pk_id_resultado_exame_candidato;
        String descricao_resultado_exame_candidato;

        final int PK_ID_RESULTADO_EXAME_CANDIDATO = 0;
        final int DESCRICAO_RESULTADO_EXAME_CANDIDATO = 1;

        DiagResultadoExameCandidato reg = new DiagResultadoExameCandidato();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_RESULTADO_EXAME_CANDIDATO:
                    pk_id_resultado_exame_candidato = (int) cell.getNumericCellValue();
                    reg.setPkIdResultadoExameCandidato(pk_id_resultado_exame_candidato);
                    break;

                case DESCRICAO_RESULTADO_EXAME_CANDIDATO:
                    descricao_resultado_exame_candidato = cell.getStringCellValue().trim();
                    reg.setDescricaoResultadoExameCandidato(descricao_resultado_exame_candidato);
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagResultadoExameCandidato reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagResultadoExameCandidatoFacade.create(reg);
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

    public boolean editRegister(DiagResultadoExameCandidato reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagResultadoExameCandidatoFacade.edit(reg);
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
