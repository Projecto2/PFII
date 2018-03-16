/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.tbbean.carregamentoExcel;

import entidade.TbTipoExame;
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
import sessao.TbTipoExameFacade;
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
public class TbTipoExameExcelBean implements Serializable
{
    @EJB
    private TbUpdatesFacade tbUpdatesFacade;

    @EJB
    private TbTipoExameFacade tbTipoExameFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of TbTipoExameExcelBean
     */
    public TbTipoExameExcelBean()
    {
    }

    public static TbTipoExameExcelBean getInstanciaBean()
    {
        return (TbTipoExameExcelBean) GeradorCodigo.getInstanciaBean("tbTipoExameExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarTipoExameTabela()
    {
        if (lerTipoExameTabela())
        {
            this.tbUpdatesFacade.dataTipoExame();
            Mensagem.sucessoMsg(TbMensagens.CARREGAMENTO);
        }
    }
    
    @SuppressWarnings("empty-statement")
    public boolean lerTipoExameTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.tb.TbDefs.FILE_TIPO_EXAME_TB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipo_exame");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            row = (HSSFRow) rows.next();
            Date dataTipoExameXLSFile = FileManagerGeral.lerVersaoTabela(row, util.tb.TbDefs.FILE_TIPO_EXAME_TB);
            
            Date dataTipoExameTabela = this.tbUpdatesFacade.dataTipoExame();
            
            if (dataTipoExameTabela == null);
            else if (dataTipoExameXLSFile.compareTo(dataTipoExameTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            TbTipoExame reg = null;
            
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
                
                escreverTipoExameTabela(reg);
                
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
    
    public TbTipoExame lerCampos(HSSFRow row)
    {
        int pk_id_aderencia;
        String descricao;
        
        final int PK_ID_ADERENCIA = 0;
        final int DESCRICAO = 1;
        
        TbTipoExame reg = new TbTipoExame();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_ADERENCIA:
                     pk_id_aderencia = (int)cell.getNumericCellValue();
                     reg.setPkTipoExame(pk_id_aderencia);
                break;
                    
                case DESCRICAO:
                     descricao = cell.getStringCellValue().trim();;
                     reg.setDescricao(descricao);
                break;
            }
        }
        return reg;
    }
    
    public void escreverTipoExameTabela(TbTipoExame reg)
    {
        if(reg.getPkTipoExame()== null) return;
        if (tbTipoExameFacade.existeRegisto(reg.getPkTipoExame()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    public boolean createRegister(TbTipoExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbTipoExameFacade.create(reg);
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
    
    public boolean editRegister(TbTipoExame reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbTipoExameFacade.edit(reg);
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
