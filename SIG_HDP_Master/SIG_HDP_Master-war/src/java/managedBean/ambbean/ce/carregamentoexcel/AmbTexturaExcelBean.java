/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.carregamentoexcel;

import entidade.AmbTextura;
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
import sessao.AmbTexturaFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbTexturaExcelBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
        
    @EJB
    private AmbTexturaFacade ambTexturaFacade;
    @EJB
    private AmbCeUpdatesFacade ambCeUpdatesFacade;
    
    /**
     * Creates a new instance of AmbTexturaExcelBean
     */
    public AmbTexturaExcelBean()
    {
    }
    
    public static AmbTexturaExcelBean getInstanciaBean()
    {
        return (AmbTexturaExcelBean) GeradorCodigo.getInstanciaBean("ambTexturaExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarTexturaTabela()
    {
        if (lerTexturaTabela())
        {
            this.ambCeUpdatesFacade.escreverDataTexturaTabela();
System.err.println("Conclusao da actualizacao da tabela \"amb_textura\" do AMB->CE");
        }
    }
    
    public boolean lerTexturaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.amb.Defs.FILE_TEXTURA_AMB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb             = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet             = wb.getSheet("textura");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows               = sheet.rowIterator();
            
            row                         = (HSSFRow) rows.next();
            Date dataTexturaXLSFile     = FileManagerGeral.lerVersaoTabela(row, util.amb.Defs.FILE_TEXTURA_AMB);
            
            Date dataTexturaTabela      = this.ambCeUpdatesFacade.dataTextura();
            
            if (dataTexturaTabela == null);
            else if (dataTexturaXLSFile.compareTo(dataTexturaTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            AmbTextura reg   = null;
            
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
                
                escreverTexturaTabela(reg);
                
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
    
    public AmbTextura lerCampos(HSSFRow row)
    {
        int pk_id_textura;
        String descricao;
        
        final int PK_ID_TEXTURA = 0;
        final int DESCRICAO     = 1;
        
        AmbTextura reg = new AmbTextura();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_TEXTURA:
                     pk_id_textura = (int)cell.getNumericCellValue();
                     reg.setPkIdTextura(pk_id_textura);
                break;
                    
                case DESCRICAO:
                     descricao = cell.getStringCellValue().trim();;
                     reg.setDescricao(descricao);
                break;
            }
        }
        return reg;
    }
    
    public void escreverTexturaTabela(AmbTextura reg)
    {
        if(reg.getPkIdTextura() == null) return;
        if (ambTexturaFacade.existeRegisto(reg.getPkIdTextura()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    public boolean createRegister(AmbTextura reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambTexturaFacade.create(reg);
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
    
    public boolean editRegister(AmbTextura reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambTexturaFacade.edit(reg);
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
