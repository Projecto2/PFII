/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.carregamentoexcel;

import entidade.AmbCor;
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
import sessao.AmbCorFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbCorExcelBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AmbCorFacade ambCorFacade;
    @EJB
    private AmbCeUpdatesFacade ambCeUpdatesFacade;
    
    /**
     * Creates a new instance of AmbCorExcelBean
     */
    public AmbCorExcelBean()
    {
    }
    
    public static AmbCorExcelBean getInstanciaBean()
    {
        return (AmbCorExcelBean) GeradorCodigo.getInstanciaBean("ambCorExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarCorTabela()
    {
        if (lerCorTabela())
        {
            this.ambCeUpdatesFacade.escreverDataCorTabela();
System.err.println("Conclusao da actualizacao da tabela \"amb_cor\" do AMB->CE");
        }
    }
    
    public boolean lerCorTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.amb.Defs.FILE_COR_AMB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb             = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet             = wb.getSheet("cor");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows               = sheet.rowIterator();
            
            row                         = (HSSFRow) rows.next();
            Date dataCorXLSFile         = FileManagerGeral.lerVersaoTabela(row, util.amb.Defs.FILE_COR_AMB);
            
            Date dataCorTabela          = this.ambCeUpdatesFacade.dataCor();
            
            if (dataCorTabela == null);
            else if (dataCorXLSFile.compareTo(dataCorTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            AmbCor reg       = null;
            
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
                
                escreverCorTabela(reg);
                
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
    
    public AmbCor lerCampos(HSSFRow row)
    {
        int pk_id_cor;
        String descricao;
        
        final int PK_ID_COR = 0;
        final int DESCRICAO = 1;
        
        AmbCor reg = new AmbCor();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_COR:
                     pk_id_cor = (int)cell.getNumericCellValue();
                     reg.setPkIdCor(pk_id_cor);
                break;
                    
                case DESCRICAO:
                     descricao = cell.getStringCellValue().trim();;
                     reg.setDescricao(descricao);
                break;
            }
        }
        return reg;
    }
    
    public void escreverCorTabela(AmbCor reg)
    {
        if(reg.getPkIdCor() == null) return;
        if (ambCorFacade.existeRegisto(reg.getPkIdCor()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    public boolean createRegister(AmbCor reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCorFacade.create(reg);
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
    
    public boolean editRegister(AmbCor reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCorFacade.edit(reg);
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
