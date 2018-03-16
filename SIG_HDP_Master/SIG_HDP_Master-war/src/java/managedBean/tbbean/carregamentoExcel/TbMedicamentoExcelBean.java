/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.tbbean.carregamentoExcel;

import entidade.TbMedicamento;
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
import sessao.TbMedicamentoFacade;
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
public class TbMedicamentoExcelBean implements Serializable
{
    @EJB
    private TbUpdatesFacade tbUpdatesFacade;

    @EJB
    private TbMedicamentoFacade tbMedicamentoFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of TbMedicamentoExcelBean
     */
    public TbMedicamentoExcelBean()
    {
    }

    public static TbMedicamentoExcelBean getInstanciaBean()
    {
        return (TbMedicamentoExcelBean) GeradorCodigo.getInstanciaBean("tbMedicamentoExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarMedicamentoTabela()
    {
        if (lerMedicamentoTabela())
        {
            tbUpdatesFacade.escreverDataMedicamentoTabela();
            Mensagem.sucessoMsg(TbMensagens.CARREGAMENTO);
        }
    }

    @SuppressWarnings("empty-statement")
    public boolean lerMedicamentoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.tb.TbDefs.FILE_MEDICAMENTO_TB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("medicamento");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            row = (HSSFRow) rows.next();
            Date dataMedicamentoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.tb.TbDefs.FILE_MEDICAMENTO_TB);
            
            Date dataMedicamentoTabela = this.tbUpdatesFacade.dataMedicamento();
            
            if (dataMedicamentoTabela == null);
            else if (dataMedicamentoXLSFile.compareTo(dataMedicamentoTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            TbMedicamento reg = null;
            
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
                
                escreverMedicamentoTabela(reg);
                
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

    public void escreverMedicamentoTabela(TbMedicamento reg)
    {
        if (tbMedicamentoFacade.existeRegisto(reg.getPkMedicamento()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public TbMedicamento lerCampos(HSSFRow row)
    {
        int pk_medicamento = 0;
        String descricao = "d";
        String abreviatura = "a";
        final int PK_MEDICAMENTO = 0;
        final int DESCRICAO = 1;
        final int ABREVIATURA = 2;

        TbMedicamento reg = new TbMedicamento();

        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_MEDICAMENTO:
                    pk_medicamento = (int) cell.getNumericCellValue();
                    reg.setPkMedicamento(pk_medicamento);
                    break;

                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                    break;

                case ABREVIATURA:
                    abreviatura = cell.getStringCellValue().trim();
                    reg.setAbreviatura(abreviatura);
                    break;
            }
        }
        System.out.println("PK: " + pk_medicamento + "Abreviatura: " + abreviatura + "descricao: " + descricao);
        return reg;
    }

    public boolean createRegister(TbMedicamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbMedicamentoFacade.create(reg);
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

    public boolean editRegister(TbMedicamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbMedicamentoFacade.edit(reg);
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
