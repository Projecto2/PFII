/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlComuna;
import entidade.GrlMunicipio;
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
import sessao.GrlComunaFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlComunaExcelBean
{

    @EJB
    private GrlComunaFacade grlComunaFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlTipoDeComunaExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlComunaExcelBean()
    {
    }

    public static GrlComunaExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlComunaExcelBean grlComunaExcelBean = 
            (GrlComunaExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlComunaExcelBean");
        
        return grlComunaExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarComunaTabela()
    {
//        Date dataComunaTabela = this.grlUpdatesFacade.dataComuna();
//
//        Date dataComunaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_COMUNA_GRL);
//
//        if (this.grlComunaFacade.isComunaTabelaEmpty() || dataComunaTabela == null);
//        else if (!grlComunaFacade.isComunaTabelaEmpty() && (dataComunaXLSFile != null && dataComunaXLSFile.compareTo(dataComunaTabela) <= 0))
//        {
//            return;
//        }

        if (lerComunaTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoComunaTabela();
        }
    }

    public boolean lerComunaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_COMUNA_GRL);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("comunas");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataComunaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_COMUNA_GRL);
            
            
            Date dataComunaTabela = this.grlUpdatesFacade.dataComuna();
            
            
            if (dataComunaTabela == null);
            else if (dataComunaXLSFile.compareTo(dataComunaTabela) <= 0)
            {
                return false;
            }
            
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlComuna reg = null;

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
                
                escreverComunaTabela(reg);
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

    public void escreverComunaTabela(GrlComuna reg)
    {
        if (grlComunaFacade.existeRegisto(reg.getPkIdComuna()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public GrlComuna lerCampos(HSSFRow row)
    {
        int pk_id__comuna;
        String descricao_comuna;
        int fk_id_municipio;
        
        final int PK_ID_COMUNA = 0;
        final int DESCRICAO_COMUNA = 1;
        final int FK_ID_MUNICIPIO = 2;
        
        GrlComuna reg = new GrlComuna();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_COMUNA:
                    pk_id__comuna = (int)cell.getNumericCellValue();
                    reg.setPkIdComuna(pk_id__comuna);
                break;
                    
                case DESCRICAO_COMUNA:
                    descricao_comuna = cell.getStringCellValue().trim();
                    reg.setDescricaoComuna(descricao_comuna);
                break;
                    
                    
                case FK_ID_MUNICIPIO:
                    if(cell == null)
                    {
                        reg.setFkIdMunicipio(null);
                    }
                    else
                    {
                        fk_id_municipio = (int)cell.getNumericCellValue();
                        reg.setFkIdMunicipio(new GrlMunicipio(fk_id_municipio));
                    }
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlComuna reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlComunaFacade.create(reg);
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

    public boolean editRegister(GrlComuna reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlComunaFacade.edit(reg);
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
