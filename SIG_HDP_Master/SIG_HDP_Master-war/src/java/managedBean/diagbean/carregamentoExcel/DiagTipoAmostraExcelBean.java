/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean.carregamentoExcel;

import entidade.DiagTipoAmostra;
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
import sessao.DiagTipoAmostraFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipoAmostraExcelBean implements Serializable
{

    @EJB
    private DiagTipoAmostraFacade diagTipoAmostraFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of DiagTipoAmostraExcelBean
     */
    public DiagTipoAmostraExcelBean()
    {
    }
    
    public static DiagTipoAmostraExcelBean getInstanciaBean()
    {
        return (DiagTipoAmostraExcelBean) GeradorCodigo.getInstanciaBean("diagTipoAmostraExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarTipoAmostraTabela()
    {
        Date dataTipoAmostraTabela = this.diagUpdatesFacade.dataTipoAmostraTabela();

        Date dataTipoAmostraXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_TIPO_AMOSTRA_DIAG);

        if (this.diagTipoAmostraFacade.isTipoAmostraTabelaEmpty() || dataTipoAmostraTabela == null);
        else if (!diagTipoAmostraFacade.isTipoAmostraTabelaEmpty() && (dataTipoAmostraXLSFile != null && dataTipoAmostraXLSFile.compareTo(dataTipoAmostraTabela) <= 0))
        {
            return;
        }

        if (lerTipoAmostraTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoTipoAmostraTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerTipoAmostraTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_TIPO_AMOSTRA_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipoAmostra");

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
            DiagTipoAmostra reg = null;

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

                escreverTipoAmostraTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverTipoAmostraTabela(DiagTipoAmostra reg)
    {

        if (diagTipoAmostraFacade.find(reg.getPkIdTipoAmostra()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagTipoAmostra lerCampos(HSSFRow row)
    {
        int pk_id_tipo_amostra;
        String descricao_tipo_amostra;

        final int PK_ID_TIPO_AMOSTRA = 0;
        final int DESCRICAO_TIPO_AMOSTRA = 1;

        DiagTipoAmostra reg = new DiagTipoAmostra();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_TIPO_AMOSTRA:
                    pk_id_tipo_amostra = (int) cell.getNumericCellValue();
                    reg.setPkIdTipoAmostra(pk_id_tipo_amostra);
                    break;

                case DESCRICAO_TIPO_AMOSTRA:
                    descricao_tipo_amostra = cell.getStringCellValue().trim();
                    reg.setDescricaoTipoAmostra(descricao_tipo_amostra);
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagTipoAmostra reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagTipoAmostraFacade.create(reg);
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

    public boolean editRegister(DiagTipoAmostra reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagTipoAmostraFacade.edit(reg);
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
