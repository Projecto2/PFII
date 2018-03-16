/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.carregamentoexcel;

import entidade.AmbConsultorio;
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
import sessao.AmbConsultorioFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbConsultorioExcelBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AmbConsultorioFacade ambConsultorioFacade;
    @EJB
    private AmbCeUpdatesFacade ambCeUpdatesFacade;
    
    /**
     * Creates a new instance of AmbConsultorioExcelBean
     */
    public AmbConsultorioExcelBean()
    {
    }
    
    public static AmbConsultorioExcelBean getInstanciaBean()
    {
        return (AmbConsultorioExcelBean) GeradorCodigo.getInstanciaBean("ambConsultorioExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarConsultorioTabela()
    {
        if (lerConsultorioTabela())
        {
            this.ambCeUpdatesFacade.escreverDataConsultorioTabela();
System.err.println("Conclusao da actualizacao da tabela \"amb_consultorio\" do AMB->CE");
        }
    }
    
    public boolean lerConsultorioTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.amb.Defs.FILE_CONSULTORIO_AMB);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb             = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet             = wb.getSheet("consultorio");
            
            HSSFRow row;
            //HSSFCell cell;
            
            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows               = sheet.rowIterator();
            
            row                         = (HSSFRow) rows.next();
            Date dataConsultorioXLSFile = FileManagerGeral.lerVersaoTabela(row, util.amb.Defs.FILE_CONSULTORIO_AMB);
            
            Date dataConsultorioTabela  = this.ambCeUpdatesFacade.dataConsultorio();
            
            if (dataConsultorioTabela == null);
            else if (dataConsultorioXLSFile.compareTo(dataConsultorioTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow   = true;

            AmbConsultorio reg = null;
            
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
                
                escreverConsultorioTabela(reg);
                
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
    
    public AmbConsultorio lerCampos(HSSFRow row)
    {
        int pk_id_consultorio
          , nome;
        String especificacao;
        
        final int PK_ID_CONSULTORIO = 0;
        final int NOME              = 1;
        final int ESPECIFICACAO     = 2;
        
        AmbConsultorio reg = new AmbConsultorio();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_CONSULTORIO:
                     pk_id_consultorio = (int)cell.getNumericCellValue();
                     reg.setPkIdConsultorio(pk_id_consultorio);
                break;
                    
                case NOME:
                     nome = (int)cell.getNumericCellValue();
                     reg.setNome(nome);
                break;
                    
                case ESPECIFICACAO:
                     especificacao = cell.getStringCellValue().trim();
                     reg.setEspecificacao(especificacao);
                break;
            }
        }
        return reg;
    }
    
    public void escreverConsultorioTabela(AmbConsultorio reg)
    {
        if(reg.getPkIdConsultorio() == null) return;
        if (ambConsultorioFacade.existeRegisto(reg.getPkIdConsultorio()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
 
    public boolean createRegister(AmbConsultorio reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambConsultorioFacade.create(reg);
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

    public boolean editRegister(AmbConsultorio reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambConsultorioFacade.edit(reg);
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
