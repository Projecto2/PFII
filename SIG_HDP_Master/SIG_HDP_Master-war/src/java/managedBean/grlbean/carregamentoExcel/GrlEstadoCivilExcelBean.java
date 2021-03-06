/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlEstadoCivil;
import entidade.RhProfissao;
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
import sessao.GrlUpdatesFacade;
import sessao.GrlEstadoCivilFacade;
import util.FileManagerGeral;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlEstadoCivilExcelBean implements Serializable
{

    @EJB
    private GrlEstadoCivilFacade grlEstadoCivilFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlEstadoCivilExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlEstadoCivilExcelBean()
    {
    }

    public static GrlEstadoCivilExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlEstadoCivilExcelBean grlEstadoCivilExcelBean = 
            (GrlEstadoCivilExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlEstadoCivilExcelBean");
        
        return grlEstadoCivilExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarEstadoCivilTabela()
    {
        System.out.println("Esta carregando estado_civil");
        
//        Date dataEstadoCivilTabela = this.grlUpdatesFacade.dataEstadoCivil();
//
//        Date dataEstadoCivilXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_ESTADO_CIVIL);
//
//        if (this.grlEstadoCivilFacade.findAll().isEmpty() || dataEstadoCivilTabela == null);
//        else if (!grlEstadoCivilFacade.findAll().isEmpty() && (dataEstadoCivilXLSFile != null && dataEstadoCivilXLSFile.compareTo(dataEstadoCivilTabela) <= 0))
//        {
//            return;
//        }

        if (lerEstadoCivilTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoEstadoCivilTabela();
        }
    }

    public boolean lerEstadoCivilTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_ESTADO_CIVIL);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("estado_civil");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataEstadoCivilXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_ESTADO_CIVIL);
            
            
            Date dataEstadoCivilTabela = this.grlUpdatesFacade.dataEstadoCivil();
            
            
            if (dataEstadoCivilTabela == null);
            else if (dataEstadoCivilXLSFile.compareTo(dataEstadoCivilTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlEstadoCivil reg = null;

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
                
                escreverEstadoCivilTabela(reg);
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

    public void escreverEstadoCivilTabela(GrlEstadoCivil reg)
    {
        if (grlEstadoCivilFacade.find(reg.getPkIdEstadoCivil()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public GrlEstadoCivil lerCampos(HSSFRow row)
    {
        int pk_id_estado_civil;
        String codigo_estado_civil;
        String descricao;
        
        final int PK_ID_ESTADO_CIVIL = 0;
        final int CODIGO_ESTADO_CIVIL = 1;
        final int DESCRICAO = 2;
        
        GrlEstadoCivil reg = new GrlEstadoCivil();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_ESTADO_CIVIL:
                    pk_id_estado_civil = (int)cell.getNumericCellValue();
                    reg.setPkIdEstadoCivil(pk_id_estado_civil);
                break;
                    
                case CODIGO_ESTADO_CIVIL:
                    codigo_estado_civil = cell.getStringCellValue();
                    reg.setCodigoEstadoCivil(codigo_estado_civil);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
                    
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlEstadoCivil reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlEstadoCivilFacade.create(reg);
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

    public boolean editRegister(GrlEstadoCivil reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlEstadoCivilFacade.edit(reg);
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
