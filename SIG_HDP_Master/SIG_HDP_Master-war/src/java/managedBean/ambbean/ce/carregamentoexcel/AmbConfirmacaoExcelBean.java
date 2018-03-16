/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.carregamentoexcel;

import entidade.AmbConfirmacao;
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
import sessao.AmbConfirmacaoFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbConfirmacaoExcelBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AmbCeUpdatesFacade ambCeUpdatesFacade;
    @EJB
    private AmbConfirmacaoFacade ambConfirmacaoFacade;    
    
    
    /**
     * Creates a new instance of AmbConfirmacaoExcelBean
     */
    public AmbConfirmacaoExcelBean()
    {
    }
    
    public static AmbConfirmacaoExcelBean getInstanciaBean()
    {
        return (AmbConfirmacaoExcelBean) GeradorCodigo.getInstanciaBean("ambConfirmacaoExcelBean");
    } 
    
    @SuppressWarnings("empty-statement")
    public void carregarConfirmacaoTabela()
    {
        if (lerConfirmacaoTabela())
        {
            this.ambCeUpdatesFacade.escreverDataConfirmacaoTabela();
System.err.println("Conclusao da actualizacao da tabela \"amb_confirmacao\" do AMB->CE");
        }
    }
    
    public boolean lerConfirmacaoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead       = new FileInputStream(util.amb.Defs.FILE_CONFIRMACAO_AMB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb                   = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet                   = wb.getSheet("confirmacao");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows                     = sheet.rowIterator();
            
            row                               = (HSSFRow) rows.next();
            Date dataConfirmacaoTabelaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.amb.Defs.FILE_CONFIRMACAO_AMB);
            
            Date dataConfirmacaoTabela        = this.ambCeUpdatesFacade.dataConfirmacao();
            
            if (dataConfirmacaoTabela == null);
            else if (dataConfirmacaoTabelaXLSFile.compareTo(dataConfirmacaoTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow   = true;

            AmbConfirmacao reg = null;
            
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
                
                escreverConfirmacaoTabela(reg);
                
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
    
    public AmbConfirmacao lerCampos(HSSFRow row)
    {
        int pk_id_confirmacao;
        String descricao;
        
        final int PK_ID_CONFIRMACAO = 0;
        final int DESCRICAO         = 1;
        
        AmbConfirmacao reg = new AmbConfirmacao();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_CONFIRMACAO:
                     pk_id_confirmacao = (int)cell.getNumericCellValue();
                     reg.setPkIdConfirmacao(pk_id_confirmacao);
                break;
                    
                case DESCRICAO:
                     descricao = cell.getStringCellValue().trim();;
                     reg.setDescricao(descricao);
                break;
            }
        }
        return reg;
    } 
    
    
    public void escreverConfirmacaoTabela(AmbConfirmacao reg)
    {
        if(reg.getPkIdConfirmacao() == null) return;
        if (ambConfirmacaoFacade.existeRegisto(reg.getPkIdConfirmacao()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }  
    
    public boolean createRegister(AmbConfirmacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambConfirmacaoFacade.create(reg);
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
    
    public boolean editRegister(AmbConfirmacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambConfirmacaoFacade.edit(reg);
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
