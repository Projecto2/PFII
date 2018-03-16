/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.carregamentoExcel;

import entidade.TbFaseTratamento;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.TbFaseTratamentoFacade;
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
public class TbFaseTratamentoExcelBean implements Serializable
{
     @EJB
    private TbUpdatesFacade tbUpdatesFacade;

    @EJB
    private TbFaseTratamentoFacade tbFaseTratamentoFacade;

    @Resource
    private UserTransaction userTransaction;
    /**
     * Creates a new instance of TbFaseTratamentoExcelBean
     */
    public TbFaseTratamentoExcelBean()
    {
    }
    
    public static TbFaseTratamentoExcelBean getInstanciaBean()
    {
        return (TbFaseTratamentoExcelBean) GeradorCodigo.getInstanciaBean("tbFaseTratamentoExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarFaseTratamentoTabela()
    {
        if (lerFaseTratamentoTabela())
        {
            tbUpdatesFacade.escreverDataFaseTratamentoTabela();
            Mensagem.sucessoMsg(TbMensagens.CARREGAMENTO);
        }
    }
    
    @SuppressWarnings("empty-statement")
    public boolean lerFaseTratamentoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.tb.TbDefs.FILE_FASE_TRATAMENTO_TB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("fase_tratamento");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            row = (HSSFRow) rows.next();
            Date dataFaseTratamentoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.tb.TbDefs.FILE_FASE_TRATAMENTO_TB);
            
            Date dataFaseTratamentoTabela = this.tbUpdatesFacade.dataFaseTratamento();
            
            if (dataFaseTratamentoTabela == null);
            else if (dataFaseTratamentoXLSFile.compareTo(dataFaseTratamentoTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            TbFaseTratamento reg = null;
            
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
                
                escreverFaseTratamentoTabela(reg);
                
                nreg++;
                //System.err.println("12: AmbCidCapitulosBean.carregarCapitulosTabela()");
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverFaseTratamentoTabela(TbFaseTratamento reg)
    {
        if (tbFaseTratamentoFacade.existeRegisto(reg.getPkFaseTratamento()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public TbFaseTratamento lerCampos(HSSFRow row)
    {
        int pk_fase_tratamento = 0;
        String descricao = "a";
        final int PK_FASE_TRATAMENTO = 0;
        final int DESCRICAO = 1;

        TbFaseTratamento reg = new TbFaseTratamento();

        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_FASE_TRATAMENTO:
                    pk_fase_tratamento = (int) cell.getNumericCellValue();
                    reg.setPkFaseTratamento(pk_fase_tratamento);
                    break;

                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                    break;
            }
        }
        System.out.println("PK: "+pk_fase_tratamento+"descricao: "+descricao);
        return reg;
    }

    public boolean createRegister(TbFaseTratamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbFaseTratamentoFacade.create(reg);
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

    public boolean editRegister(TbFaseTratamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbFaseTratamentoFacade.edit(reg);
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
