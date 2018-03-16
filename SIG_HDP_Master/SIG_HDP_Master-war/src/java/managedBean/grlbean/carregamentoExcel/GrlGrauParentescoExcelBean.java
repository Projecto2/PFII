/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlGrauParentesco;
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
import sessao.GrlGrauParentescoFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class GrlGrauParentescoExcelBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private GrlGrauParentescoFacade grlGrauParentescoFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;

    /**
     * Creates a new instance of GrlGrauParentescoExcelBean
     */
    public GrlGrauParentescoExcelBean()
    {
    }
    
    public static GrlGrauParentescoExcelBean getInstanciaBean()
    {
        return (GrlGrauParentescoExcelBean) GeradorCodigo.getInstanciaBean("grlGrauParentescoExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarGrauParentescoTabela()
    {
        if (lerGrauParentescoTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoGrauParentescoTabela();
System.err.println("Conclusao da actualizacao da tabela \"grl_grau_parentesco\" do GRL");
        }
    }
    
    public boolean lerGrauParentescoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_GRAU_PARENTESCO);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("grau_parentesco");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            row = (HSSFRow) rows.next();
            Date dataGrauParentescoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_GRAU_PARENTESCO);
            Date dataGrauParentescoTabela = this.grlUpdatesFacade.dataGrauParentesco();
            
            if (dataGrauParentescoTabela == null);
            else if (dataGrauParentescoXLSFile.compareTo(dataGrauParentescoTabela) <= 0)
            {
                return false;
            }
            
            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            GrlGrauParentesco reg = null;
            
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
                
                escreverGrauParentescoTabela(reg);
                
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
    
    public GrlGrauParentesco lerCampos(HSSFRow row)
    {
        int pk_id_grau_parentesco;
        String descricao;
        
        final int PK_ID_GRAU_PARENTESCO = 0;
        final int DESCRICAO = 1;
        
        GrlGrauParentesco reg = new GrlGrauParentesco();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_GRAU_PARENTESCO:
                     pk_id_grau_parentesco = (int)cell.getNumericCellValue();
                     reg.setPkIdGrauParentesco(pk_id_grau_parentesco);
                break;
                    
                case DESCRICAO:
                     descricao = cell.getStringCellValue().trim();
                     reg.setDescricaoGrauParentesco(descricao);
                break;
            }
        }
        return reg;
    }
    
    public void escreverGrauParentescoTabela(GrlGrauParentesco reg)
    {
        if(reg.getPkIdGrauParentesco() == null) return;
        if (grlGrauParentescoFacade.existeRegisto(reg.getPkIdGrauParentesco()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    public boolean createRegister(GrlGrauParentesco reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlGrauParentescoFacade.create(reg);
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
    
    public boolean editRegister(GrlGrauParentesco reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlGrauParentescoFacade.edit(reg);
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

