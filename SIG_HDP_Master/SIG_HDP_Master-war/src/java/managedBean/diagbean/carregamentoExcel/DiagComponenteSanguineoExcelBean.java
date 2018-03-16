/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean.carregamentoExcel;

import entidade.DiagComponenteSanguineo;
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
import sessao.DiagComponenteSanguineoFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagComponenteSanguineoExcelBean implements Serializable
{

    @EJB
    private DiagComponenteSanguineoFacade diagComponenteSanguineoFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of DiagComponenteSanguineoExcelBean
     */
    public DiagComponenteSanguineoExcelBean()
    {
    }

    public static DiagComponenteSanguineoExcelBean getInstanciaBean()
    {
        return (DiagComponenteSanguineoExcelBean) GeradorCodigo.getInstanciaBean("diagComponenteSanguineoExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarComponenteSanguineoTabela()
    {
        Date dataComponenteSanguineoTabela = this.diagUpdatesFacade.dataComponenteSanguineoTabela();

        Date dataComponenteSanguineoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_COMPONENTE_SANGUINEO_DIAG);

        if (this.diagComponenteSanguineoFacade.isComponenteSanguineoTabelaEmpty() || dataComponenteSanguineoTabela == null);
        else if (!diagComponenteSanguineoFacade.isComponenteSanguineoTabelaEmpty() && (dataComponenteSanguineoXLSFile != null && dataComponenteSanguineoXLSFile.compareTo(dataComponenteSanguineoTabela) <= 0))
        {
            return;
        }

        if (lerComponenteSanguineoTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoComponenteSanguineoTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerComponenteSanguineoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_COMPONENTE_SANGUINEO_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("componentesSanguineos");

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
            DiagComponenteSanguineo reg = null;

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

                escreverComponenteSanguineoTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverComponenteSanguineoTabela(DiagComponenteSanguineo reg)
    {

        if (diagComponenteSanguineoFacade.find(reg.getPkIdComponenteSanguineo()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagComponenteSanguineo lerCampos(HSSFRow row)
    {
        int pk_id_componente_sanguineo;
        String descricao_componente_sanguineo;

        final int PK_ID_COMPONENTE_SANGUINEO = 0;
        final int DESCRICAO_COMPONENTE_SANGUINEO = 1;

        DiagComponenteSanguineo reg = new DiagComponenteSanguineo();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_COMPONENTE_SANGUINEO:
                    pk_id_componente_sanguineo = (int) cell.getNumericCellValue();
                    reg.setPkIdComponenteSanguineo(pk_id_componente_sanguineo);
                    break;

                case DESCRICAO_COMPONENTE_SANGUINEO:
                    descricao_componente_sanguineo = cell.getStringCellValue().trim();
                    reg.setDescricaoComponenteSanguineo(descricao_componente_sanguineo);
                    break;
               
            }
        }

        return reg;
    }

    public boolean createRegister(DiagComponenteSanguineo reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagComponenteSanguineoFacade.create(reg);
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

    public boolean editRegister(DiagComponenteSanguineo reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagComponenteSanguineoFacade.edit(reg);
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
