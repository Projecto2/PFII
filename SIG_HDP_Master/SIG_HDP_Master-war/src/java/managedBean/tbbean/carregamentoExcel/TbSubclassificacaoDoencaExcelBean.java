/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.tbbean.carregamentoExcel;

import entidade.TbClassificacaoDoenca;
import entidade.TbSubclassificacaoDoenca;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.TbSubclassificacaoDoencaFacade;
import sessao.TbUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbSubclassificacaoDoencaExcelBean implements Serializable
{

    @EJB
    private TbUpdatesFacade tbUpdatesFacade;

    @EJB
    private TbSubclassificacaoDoencaFacade tbSubclassificacaoDoencaFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of TbSubclassificacaoDoencaExcelBean
     */
    public TbSubclassificacaoDoencaExcelBean()
    {
    }

    public static TbSubclassificacaoDoencaExcelBean getInstanciaBean()
    {
        return (TbSubclassificacaoDoencaExcelBean) GeradorCodigo.getInstanciaBean("tbSubclassificacaoDoencaExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarSubclassificacaoDoencaTabela()
    {
        if (lerSubclassificacaoDoencaTabela())
        {
            tbUpdatesFacade.escreverDataSubClassificacaoDoencaTabela();
            Mensagem.sucessoMsg(TbMensagens.CARREGAMENTO);
        }
    }

    @SuppressWarnings("empty-statement")
    public boolean lerSubclassificacaoDoencaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.tb.TbDefs.FILE_SUBCLASSIFICACAO_DOENCA_TB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("subClassificacao_doenca");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            row = (HSSFRow) rows.next();
            Date dataSubclassificacaoDoencaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.tb.TbDefs.FILE_SUBCLASSIFICACAO_DOENCA_TB);
            
            Date dataSubclassificacaoDoencaTabela = this.tbUpdatesFacade.dataSubClassificacaoDoenca();
            
            if (dataSubclassificacaoDoencaTabela == null);
            else if (dataSubclassificacaoDoencaXLSFile.compareTo(dataSubclassificacaoDoencaTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            TbSubclassificacaoDoenca reg = null;
            
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
                
                escreverSubclassificacaoDoencaTabela(reg);
                
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverSubclassificacaoDoencaTabela(TbSubclassificacaoDoenca reg)
    {
        if (tbSubclassificacaoDoencaFacade.existeRegisto(reg.getPkSubclassificacaoDoenca()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public TbSubclassificacaoDoenca lerCampos(HSSFRow row)
    {
        int pk_subclassificacaoDoenca = 0;
        int fk_classificacaoDoenca = 0;
        String localizacao_doenca = "a";

        final int PK_SUBCLASSIFICACAO_DOENCA = 0;
        final int FK_CLASSIFICACAO_DOENCA = 1;
        final int LOCALIZACAO_DOENCA = 2;

        TbSubclassificacaoDoenca reg = new TbSubclassificacaoDoenca();

        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_SUBCLASSIFICACAO_DOENCA:
                    pk_subclassificacaoDoenca = (int) cell.getNumericCellValue();
                    reg.setPkSubclassificacaoDoenca(pk_subclassificacaoDoenca);
                    break;

                case FK_CLASSIFICACAO_DOENCA:
                    fk_classificacaoDoenca = (int) cell.getNumericCellValue();
                    reg.setFkClassificacaoDoenca(new TbClassificacaoDoenca(fk_classificacaoDoenca));
                    break;

                case LOCALIZACAO_DOENCA:
                    localizacao_doenca = cell.getStringCellValue().trim();
                    reg.setDescricao(localizacao_doenca);
                    break;
            }
        }
        System.out.println("PK: " + pk_subclassificacaoDoenca + "FK: " + fk_classificacaoDoenca+ "Localizacao: " + localizacao_doenca);
        return reg;
    }

    public boolean createRegister(TbSubclassificacaoDoenca reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbSubclassificacaoDoencaFacade.create(reg);
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
                System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

    public boolean editRegister(TbSubclassificacaoDoenca reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbSubclassificacaoDoencaFacade.edit(reg);
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
                System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

}
