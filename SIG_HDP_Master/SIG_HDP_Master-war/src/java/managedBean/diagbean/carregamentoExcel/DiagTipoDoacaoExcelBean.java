/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.diagbean.carregamentoExcel;

import entidade.DiagTipoDoacao;
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
import sessao.DiagTipoDoacaoFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipoDoacaoExcelBean implements Serializable
{
@EJB
    private DiagTipoDoacaoFacade diagTipoDoacaoFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    /**
     * Creates a new instance of DiagTipoDoacaoExcelBean
     */
    public DiagTipoDoacaoExcelBean()
    {
    }
    
    public static DiagTipoDoacaoExcelBean getInstanciaBean()
    {
        return (DiagTipoDoacaoExcelBean) GeradorCodigo.getInstanciaBean("diagTipoDoacaoExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarTipoDoacaoTabela()
    {
        Date dataTipoDoacaoTabela = this.diagUpdatesFacade.dataTipoDoacaoTabela();

        Date dataTipoDoacaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_TIPO_DOACAO_DIAG);

        if (this.diagTipoDoacaoFacade.isTipoDoacaoTabelaEmpty() || dataTipoDoacaoTabela == null);
        else if (!diagTipoDoacaoFacade.isTipoDoacaoTabelaEmpty() && (dataTipoDoacaoXLSFile != null && dataTipoDoacaoXLSFile.compareTo(dataTipoDoacaoTabela) <= 0))
        {
            return;
        }

        if (lerTipoDoacaoTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoTipoDoacaoTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerTipoDoacaoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_TIPO_DOACAO_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipoDoacao");

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
            DiagTipoDoacao reg = null;

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

                escreverTipoDoacaoTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverTipoDoacaoTabela(DiagTipoDoacao reg)
    {

        if (diagTipoDoacaoFacade.find(reg.getPkIdTipoDoacao()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagTipoDoacao lerCampos(HSSFRow row)
    {
        int pk_id_tipo_doacao;
        String descricao_tipo_doacao;

        final int PK_ID_TIPO_DOACAO = 0;
        final int DESCRICAO_TIPO_DOACAO = 1;

        DiagTipoDoacao reg = new DiagTipoDoacao();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_TIPO_DOACAO:
                    pk_id_tipo_doacao = (int) cell.getNumericCellValue();
                    reg.setPkIdTipoDoacao(pk_id_tipo_doacao);
                    break;

                case DESCRICAO_TIPO_DOACAO:
                    descricao_tipo_doacao = cell.getStringCellValue().trim();
                    reg.setDescricaoTipoDoacao(descricao_tipo_doacao);
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagTipoDoacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagTipoDoacaoFacade.create(reg);
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

    public boolean editRegister(DiagTipoDoacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagTipoDoacaoFacade.edit(reg);
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
