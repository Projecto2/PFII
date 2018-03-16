/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.rhbean.carregamentoExcel;

import entidade.RhPeriodoAulas;
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
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.RhUpdatesFacade;
import sessao.RhPeriodoAulasFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhPeriodoAulasExcelBean implements Serializable
{

    @EJB
    private RhPeriodoAulasFacade rhPeriodoAulasFacade;
    @EJB
    private RhUpdatesFacade rhUpdatesFacade;
    
    /**
     * Creates a new instance of RhPeriodoAulasExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public RhPeriodoAulasExcelBean()
    {
    }

    public static RhPeriodoAulasExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        RhPeriodoAulasExcelBean rhPeriodoAulasExcelBean = 
            (RhPeriodoAulasExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "rhPeriodoAulasExcelBean");
        
        return rhPeriodoAulasExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarPeriodoAulasTabela()
    {
        System.out.println("Esta carregando periodo_aulas");
        
        Date dataPeriodoAulasTabela = this.rhUpdatesFacade.dataPeriodoAulas();

        Date dataPeriodoAulasXLSFile = util.DataUtils.dataModificacaoFicheiro(util.rh.Defs.FILE_PERIODO_AULAS_RH);

        if (this.rhPeriodoAulasFacade.findAll().isEmpty() || dataPeriodoAulasTabela == null);
        else if (!rhPeriodoAulasFacade.findAll().isEmpty() && (dataPeriodoAulasXLSFile != null && dataPeriodoAulasXLSFile.compareTo(dataPeriodoAulasTabela) <= 0))
        {
            return;
        }

        if (lerPeriodoAulasTabela())
        {
            this.rhUpdatesFacade.escreverDataActualizacaoPeriodoAulasTabela();
        }
    }

    public boolean lerPeriodoAulasTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.rh.Defs.FILE_PERIODO_AULAS_RH);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("periodo_aulas");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            RhPeriodoAulas reg = null;

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
                
                escreverPeriodoAulasTabela(reg);
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

    public void escreverPeriodoAulasTabela(RhPeriodoAulas reg)
    {
        if (rhPeriodoAulasFacade.find(reg.getPkIdPeriodoAulas()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public RhPeriodoAulas lerCampos(HSSFRow row)
    {
        int pk_id_periodo_aulas;
        String descricao;
        
        final int PK_ID_PERIODO_AULAS = 0;
        final int DESCRICAO = 1;
        
        RhPeriodoAulas reg = new RhPeriodoAulas();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_PERIODO_AULAS:
                    pk_id_periodo_aulas = (int)cell.getNumericCellValue();
                    reg.setPkIdPeriodoAulas(pk_id_periodo_aulas);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(RhPeriodoAulas reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhPeriodoAulasFacade.create(reg);
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
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

    public boolean editRegister(RhPeriodoAulas reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhPeriodoAulasFacade.edit(reg);
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
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }
    
}
