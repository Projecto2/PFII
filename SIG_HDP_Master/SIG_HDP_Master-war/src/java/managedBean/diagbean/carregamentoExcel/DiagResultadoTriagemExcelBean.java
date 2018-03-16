/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.diagbean.carregamentoExcel;

import entidade.DiagResultadoTriagem;
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
import sessao.DiagResultadoTriagemFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagResultadoTriagemExcelBean implements Serializable
{
@EJB
    private DiagResultadoTriagemFacade diagResultadoTriagemFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    /**
     * Creates a new instance of DiagResultadoTriagemExcelBean
     */
    public DiagResultadoTriagemExcelBean()
    {
    }
    
    public static DiagResultadoTriagemExcelBean getInstanciaBean()
    {
        return (DiagResultadoTriagemExcelBean) GeradorCodigo.getInstanciaBean("diagResultadoTriagemExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarResultadoTriagemTabela()
    {
        Date dataResultadoTriagemTabela = this.diagUpdatesFacade.dataResultadoTriagemTabela();

        Date dataResultadoTriagemXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_RESULTADO_TRIAGEM_DIAG);

        if (this.diagResultadoTriagemFacade.isResultadoTriagemTabelaEmpty() || dataResultadoTriagemTabela == null);
        else if (!diagResultadoTriagemFacade.isResultadoTriagemTabelaEmpty() && (dataResultadoTriagemXLSFile != null && dataResultadoTriagemXLSFile.compareTo(dataResultadoTriagemTabela) <= 0))
        {
            return;
        }

        if (lerResultadoTriagemTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoResultadoTriagemTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerResultadoTriagemTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_RESULTADO_TRIAGEM_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("resultadoTriagem");

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
            DiagResultadoTriagem reg = null;

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

                escreverResultadoTriagemTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverResultadoTriagemTabela(DiagResultadoTriagem reg)
    {

        if (diagResultadoTriagemFacade.find(reg.getPkIdResultadoTriagem()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagResultadoTriagem lerCampos(HSSFRow row)
    {
        int pk_id_resultado_triagem;
        String descricao_resultado_triagem;

        final int PK_ID_RESULTADO_TRIAGEM = 0;
        final int DESCRICAO_RESULTADO_TRIAGEM = 1;

        DiagResultadoTriagem reg = new DiagResultadoTriagem();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_RESULTADO_TRIAGEM:
                    pk_id_resultado_triagem = (int) cell.getNumericCellValue();
                    reg.setPkIdResultadoTriagem(pk_id_resultado_triagem);
                    break;

                case DESCRICAO_RESULTADO_TRIAGEM:
                    descricao_resultado_triagem = cell.getStringCellValue().trim();
                    reg.setDescricaoResultadoTriagem(descricao_resultado_triagem);
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagResultadoTriagem reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagResultadoTriagemFacade.create(reg);
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

    public boolean editRegister(DiagResultadoTriagem reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagResultadoTriagemFacade.edit(reg);
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
