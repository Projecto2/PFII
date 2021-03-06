/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlPais;
import entidade.GrlProvincia;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import sessao.GrlProvinciaFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlProvinciaExcelBean
{

    @EJB
    private GrlProvinciaFacade grlProvinciaFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlTipoDeProvinciaExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlProvinciaExcelBean()
    {
    }

    public static GrlProvinciaExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlProvinciaExcelBean grlProvinciaExcelBean = 
            (GrlProvinciaExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlProvinciaExcelBean");
        
        return grlProvinciaExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarProvinciaTabela()
    {
//        Date dataProvinciaTabela = this.grlUpdatesFacade.dataProvincia();
//
//        Date dataProvinciaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_PROVINCIA_GRL);
//
//        if (this.grlProvinciaFacade.isProvinciaTabelaEmpty() || dataProvinciaTabela == null);
//        else if (!grlProvinciaFacade.isProvinciaTabelaEmpty() && (dataProvinciaXLSFile != null && dataProvinciaXLSFile.compareTo(dataProvinciaTabela) <= 0))
//        {
//            return;
//        }

        if (lerProvinciaTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoProvinciaTabela();
        }
    }

    public boolean lerProvinciaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_PROVINCIA_GRL);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("provincias");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataProvinciaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_PROVINCIA_GRL);
            
            
            Date dataProvinciaTabela = this.grlUpdatesFacade.dataProvincia();
            
            
            if (dataProvinciaTabela == null);
            else if (dataProvinciaXLSFile.compareTo(dataProvinciaTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlProvincia reg = null;

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
                
                escreverProvinciaTabela(reg);
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

    public void escreverProvinciaTabela(GrlProvincia reg)
    {
        if (grlProvinciaFacade.existeRegisto(reg.getPkIdProvincia()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public GrlProvincia lerCampos(HSSFRow row)
    {
        int pk_id_provincia;
        String nome_provincia;
        int fk_id_pais;
        
        final int PK_ID_PROVINCIA = 0;
        final int NOME_PROVINCIA = 1;
        final int FK_ID_PAIS = 2;
        
        GrlProvincia reg = new GrlProvincia();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_PROVINCIA:
                    pk_id_provincia = (int)cell.getNumericCellValue();
                    reg.setPkIdProvincia(pk_id_provincia);
                break;
                    
                case NOME_PROVINCIA:
                    nome_provincia = cell.getStringCellValue().trim();
                    reg.setNomeProvincia(nome_provincia);
                break;
                   
                    
                case FK_ID_PAIS:
                    if(cell == null)
                    {
                        reg.setFkIdPais(null);
                    }
                    else
                    {
                        fk_id_pais = (int)cell.getNumericCellValue();
                        reg.setFkIdPais(new GrlPais(fk_id_pais));
                    }
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlProvincia reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlProvinciaFacade.create(reg);
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

    public boolean editRegister(GrlProvincia reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlProvinciaFacade.edit(reg);
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
