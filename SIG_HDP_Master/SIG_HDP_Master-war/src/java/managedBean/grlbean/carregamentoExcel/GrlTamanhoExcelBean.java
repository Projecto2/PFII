/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlTamanho;
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
import sessao.GrlTamanhoFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class GrlTamanhoExcelBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private GrlTamanhoFacade grlTamanhoFacade;
    @EJB
    private AmbCeUpdatesFacade ambCeUpdatesFacade;
    
    /**
     * Creates a new instance of GrlTamanhoExcelBean
     */
    public GrlTamanhoExcelBean()
    {
    }
    
    public static GrlTamanhoExcelBean getInstanciaBean()
    {
        return (GrlTamanhoExcelBean) GeradorCodigo.getInstanciaBean("grlTamanhoExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarTamanhoTabela()
    {
        if (lerTamanhoTabela())
        {
            this.ambCeUpdatesFacade.escreverDataTamanhoTabela();
System.err.println("Conclusao da actualizacao da tabela \"grl_tamanho\" do GRL");
        }
    }
    
    public boolean lerTamanhoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_TAMANHO);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tamanho");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            row = (HSSFRow) rows.next();
            Date dataTamanhoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_TAMANHO);
            
            Date dataTamanhoTabela = this.ambCeUpdatesFacade.dataTamanho();
            
            if (dataTamanhoTabela == null);
            else if (dataTamanhoXLSFile.compareTo(dataTamanhoTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            GrlTamanho reg = null;
            
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
                
                escreverTamanhoTabela(reg);
                
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
    
    public GrlTamanho lerCampos(HSSFRow row)
    {
        int pk_id_tamanho;
        String descricao;
        
        final int PK_ID_TAMANHO = 0;
        final int DESCRICAO = 1;
        
        GrlTamanho reg = new GrlTamanho();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_TAMANHO:
                     pk_id_tamanho = (int)cell.getNumericCellValue();
                     reg.setPkIdTamanho(pk_id_tamanho);
                break;
                    
                case DESCRICAO:
                     descricao = cell.getStringCellValue().trim();;
                     reg.setDescricao(descricao);
                break;
            }
        }
        return reg;
    }
    
    public void escreverTamanhoTabela(GrlTamanho reg)
    {
        if(reg.getPkIdTamanho() == null) return;
        if (grlTamanhoFacade.existeRegisto(reg.getPkIdTamanho()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    public boolean createRegister(GrlTamanho reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlTamanhoFacade.create(reg);
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
    
    public boolean editRegister(GrlTamanho reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlTamanhoFacade.edit(reg);
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
