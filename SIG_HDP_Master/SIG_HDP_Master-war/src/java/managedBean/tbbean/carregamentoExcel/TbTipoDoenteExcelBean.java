/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.carregamentoExcel;

import entidade.TbTipoDoente;
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
import sessao.TbTipoDoenteFacade;
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
public class TbTipoDoenteExcelBean implements Serializable
{
    @EJB
    private TbUpdatesFacade tbUpdatesFacade;

    @EJB
    private TbTipoDoenteFacade tbTipoDoenteFacade;

    @Resource
    private UserTransaction userTransaction;
    /**
     * Creates a new instance of TbTipoDoenteExcelBean
     */
    public TbTipoDoenteExcelBean()
    {
    }
    
    public static TbTipoDoenteExcelBean getInstanciaBean()
    {
        return (TbTipoDoenteExcelBean) GeradorCodigo.getInstanciaBean("tbTipoDoenteExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarTipoDoenteTabela()
    {
        if (lerTipoDoenteTabela())
        {
            tbUpdatesFacade.escreverDataTipoDoenteTabela();
            Mensagem.sucessoMsg(TbMensagens.CARREGAMENTO);
        }
    }

    @SuppressWarnings("empty-statement")
    public boolean lerTipoDoenteTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.tb.TbDefs.FILE_TIPO_DOENTE_TB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipo_doente");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            row = (HSSFRow) rows.next();
            Date dataTipoDoenteXLSFile = FileManagerGeral.lerVersaoTabela(row, util.tb.TbDefs.FILE_TIPO_DOENTE_TB);
            
            Date dataTipoDoenteTabela = this.tbUpdatesFacade.dataTipoDoente();
            
            if (dataTipoDoenteTabela == null);
            else if (dataTipoDoenteXLSFile.compareTo(dataTipoDoenteTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            TbTipoDoente reg = null;
            
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
                
                escreverTipoDoenteTabela(reg);
                
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

    public void escreverTipoDoenteTabela(TbTipoDoente reg)
    {
        if (tbTipoDoenteFacade.existeRegisto(reg.getPkTipoDoente()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public TbTipoDoente lerCampos(HSSFRow row)
    {
        int pk_tipoDoente = 0;
        String descricao = "a";
        final int PK_TIPO_DOENTE = 0;
        final int DESCRICAO = 1;

        TbTipoDoente reg = new TbTipoDoente();

        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_TIPO_DOENTE:
                    pk_tipoDoente = (int) cell.getNumericCellValue();
                    reg.setPkTipoDoente(pk_tipoDoente);
                    break;

                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                    break;
            }
        }
        System.out.println("PK: "+pk_tipoDoente+"descricao: "+descricao);
        return reg;
    }

    public boolean createRegister(TbTipoDoente reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbTipoDoenteFacade.create(reg);
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

    public boolean editRegister(TbTipoDoente reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbTipoDoenteFacade.edit(reg);
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
