/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.diagbean.carregamentoExcel;

import entidade.DiagEstadoClinico;
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
import sessao.DiagEstadoClinicoFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagEstadoClinicoExcelBean implements Serializable
{
@EJB
    private DiagEstadoClinicoFacade diagEstadoClinicoFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    /**
     * Creates a new instance of DiagEstadoClinicoExcelBean
     */
    public DiagEstadoClinicoExcelBean()
    {
    }
    
    public static DiagEstadoClinicoExcelBean getInstanciaBean()
    {
        return (DiagEstadoClinicoExcelBean) GeradorCodigo.getInstanciaBean("diagEstadoClinicoExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarEstadoClinicoTabela()
    {
        Date dataEstadoClinicoTabela = this.diagUpdatesFacade.dataEstadoClinicoTabela();

        Date dataEstadoClinicoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_ESTADO_CLINICO_DIAG);

        if (this.diagEstadoClinicoFacade.isEstadoClinicoTabelaEmpty() || dataEstadoClinicoTabela == null);
        else if (!diagEstadoClinicoFacade.isEstadoClinicoTabelaEmpty() && (dataEstadoClinicoXLSFile != null && dataEstadoClinicoXLSFile.compareTo(dataEstadoClinicoTabela) <= 0))
        {
            return;
        }

        if (lerEstadoClinicoTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoEstadoClinicoTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerEstadoClinicoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_ESTADO_CLINICO_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("estadoClinico");

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
            DiagEstadoClinico reg = null;

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

                escreverEstadoClinicoTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverEstadoClinicoTabela(DiagEstadoClinico reg)
    {

        if (diagEstadoClinicoFacade.find(reg.getPkIdEstadoClinico()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagEstadoClinico lerCampos(HSSFRow row)
    {
        int pk_id_estado_clinico;
        String descricao_estado_clinico;

        final int DIAG_ESTADO_CLINICO = 0;
        final int DESCRICAO_ESTADO_CLINICO = 1;

        DiagEstadoClinico reg = new DiagEstadoClinico();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case DIAG_ESTADO_CLINICO:
                    pk_id_estado_clinico = (int) cell.getNumericCellValue();
                    reg.setPkIdEstadoClinico(pk_id_estado_clinico);
                    break;

                case DESCRICAO_ESTADO_CLINICO:
                    descricao_estado_clinico = cell.getStringCellValue().trim();
                    reg.setDescricaoEstadoClinico(descricao_estado_clinico);
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagEstadoClinico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagEstadoClinicoFacade.create(reg);
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

    public boolean editRegister(DiagEstadoClinico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagEstadoClinicoFacade.edit(reg);
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
