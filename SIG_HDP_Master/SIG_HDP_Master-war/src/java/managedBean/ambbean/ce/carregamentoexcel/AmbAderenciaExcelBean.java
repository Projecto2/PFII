/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.carregamentoexcel;

import entidade.AmbAderencia;
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
import sessao.AmbAderenciaFacade;
import sessao.AmbCeUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbAderenciaExcelBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AmbAderenciaFacade ambAderenciaFacade;
    @EJB
    private AmbCeUpdatesFacade ambCeUpdatesFacade;
    
    /**
     * Creates a new instance of AmbAderenciaExcelBean
     */
    public AmbAderenciaExcelBean()
    {
    }
    
    public static AmbAderenciaExcelBean getInstanciaBean()
    {
        return (AmbAderenciaExcelBean) GeradorCodigo.getInstanciaBean("ambAderenciaExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarAderenciaTabela()
    {
        if (lerAderenciaTabela())
        {
            this.ambCeUpdatesFacade.escreverDataAderenciaTabela();
System.err.println("Conclusao da actualizacao da tabela \"amb_aderencia\" do AMB->CE");
        }
    }
    
    public boolean lerAderenciaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.amb.Defs.FILE_ADERENCIA_AMB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb             = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet             = wb.getSheet("aderencia");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows               = sheet.rowIterator();
            
            row                         = (HSSFRow) rows.next();
            Date dataAderenciaXLSFile   = FileManagerGeral.lerVersaoTabela(row, util.amb.Defs.FILE_ADERENCIA_AMB);
            
            Date dataAderenciaTabela    = this.ambCeUpdatesFacade.dataAderencia();
            
            if (dataAderenciaTabela == null);
            else if (dataAderenciaXLSFile.compareTo(dataAderenciaTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            AmbAderencia reg = null;
            
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
                
                escreverAderenciaTabela(reg);
                
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
    
    public AmbAderencia lerCampos(HSSFRow row)
    {
        int pk_id_aderencia;
        String descricao;
        
        final int PK_ID_ADERENCIA = 0;
        final int DESCRICAO       = 1;
        
        AmbAderencia reg = new AmbAderencia();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_ADERENCIA:
                     pk_id_aderencia = (int)cell.getNumericCellValue();
                     reg.setPkIdAderencia(pk_id_aderencia);
                break;
                    
                case DESCRICAO:
                     descricao = cell.getStringCellValue().trim();;
                     reg.setDescricao(descricao);
                break;
            }
        }
        return reg;
    }
    
    public void escreverAderenciaTabela(AmbAderencia reg)
    {
        if(reg.getPkIdAderencia() == null) return;
        if (ambAderenciaFacade.existeRegisto(reg.getPkIdAderencia()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    public boolean createRegister(AmbAderencia reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambAderenciaFacade.create(reg);
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
    
    public boolean editRegister(AmbAderencia reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambAderenciaFacade.edit(reg);
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
