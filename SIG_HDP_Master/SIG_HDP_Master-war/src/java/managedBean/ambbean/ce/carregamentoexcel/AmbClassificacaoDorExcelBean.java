/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.carregamentoexcel;

import entidade.AmbClassificacaoDor;
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
import sessao.AmbCeUpdatesFacade;
import sessao.AmbClassificacaoDorFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbClassificacaoDorExcelBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AmbClassificacaoDorFacade ambClassificacaoDorFacade;
    @EJB
    private AmbCeUpdatesFacade ambCeUpdatesFacade;

    /**
     * Creates a new instance of AmbClassificacaoDorExcelBean
     */
    public AmbClassificacaoDorExcelBean()
    {
    }
    
    public static AmbClassificacaoDorExcelBean getInstanciaBean()
    {
        return (AmbClassificacaoDorExcelBean) GeradorCodigo.getInstanciaBean("ambClassificacaoDorExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarClassificacaoDorTabela()
    {
        if (lerClassificacaoDorTabela())
        {
            this.ambCeUpdatesFacade.escreverDataClassificacaoDorTabela();
System.err.println("Conclusao da actualizacao da tabela \"amb_classificacao_dor\" do AMB->CE");
        }
    }
    
    public boolean lerClassificacaoDorTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead     = new FileInputStream(util.amb.Defs.FILE_CLASSIFICACAO_DOR_AMB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb                 = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet                 = wb.getSheet("classificacao_dor");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows                    = sheet.rowIterator();
            
            row                              = (HSSFRow) rows.next();
            Date dataClassificacaoDorXLSFile = FileManagerGeral.lerVersaoTabela(row, util.amb.Defs.FILE_CLASSIFICACAO_DOR_AMB);
            Date dataClassificacaoDorTabela  = this.ambCeUpdatesFacade.dataClassificacaoDor();
            
            if (dataClassificacaoDorTabela == null);
            else if (dataClassificacaoDorXLSFile.compareTo(dataClassificacaoDorTabela) <= 0)
            {
                return false;
            }
            
            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow        = true;

            AmbClassificacaoDor reg = null;
//            boolean cabecalhoTabela = true;
            
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
                
                escreverClassificacaoDorTabela(reg);
                
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
    
    public AmbClassificacaoDor lerCampos(HSSFRow row)
    {
        int pk_id_classificacao_dor;
        String descricao;
        
        final int PK_ID_CLASSIFICACAO_DOR = 0;
        final int DESCRICAO               = 1;
        
        AmbClassificacaoDor reg = new AmbClassificacaoDor();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_CLASSIFICACAO_DOR:
                     pk_id_classificacao_dor = (int)cell.getNumericCellValue();
                     reg.setPkIdClassificacaoDor(pk_id_classificacao_dor);
                break;
                    
                case DESCRICAO:
                     descricao = cell.getStringCellValue().trim();
                     reg.setDescricao(descricao);
                break;
            }
        }
        return reg;
    }
    
    public void escreverClassificacaoDorTabela(AmbClassificacaoDor reg)
    {
        if(reg.getPkIdClassificacaoDor() == null) return;
        if (ambClassificacaoDorFacade.existeRegisto(reg.getPkIdClassificacaoDor()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    public boolean createRegister(AmbClassificacaoDor reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambClassificacaoDorFacade.create(reg);
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

    public boolean editRegister(AmbClassificacaoDor reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambClassificacaoDorFacade.edit(reg);
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
