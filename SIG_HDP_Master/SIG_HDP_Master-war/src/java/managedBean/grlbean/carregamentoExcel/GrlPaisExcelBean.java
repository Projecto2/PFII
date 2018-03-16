/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlPais;
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
import sessao.GrlPaisFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlPaisExcelBean
{

    @EJB
    private GrlPaisFacade grlPaisFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlTipoDePaisExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlPaisExcelBean()
    {
    }

    public static GrlPaisExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlPaisExcelBean grlPaisExcelBean = 
            (GrlPaisExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlPaisExcelBean");
        
        return grlPaisExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarPaisTabela()
    {
//        Date dataPaisTabela = this.grlUpdatesFacade.dataPais();
//
//        Date dataPaisXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_PAIS_GRL);
//
//        if (this.grlPaisFacade.isPaisTabelaEmpty() || dataPaisTabela == null);
//        else if (!grlPaisFacade.isPaisTabelaEmpty() && (dataPaisXLSFile != null && dataPaisXLSFile.compareTo(dataPaisTabela) <= 0))
//        {
//            return;
//        }

        if (lerPaisTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoPaisTabela();
        }
    }

    public boolean lerPaisTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_PAIS_GRL);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("paises");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataPaisXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_PAIS_GRL);
            
            
            Date dataPaisTabela = this.grlUpdatesFacade.dataPais();
            
            
            if (dataPaisTabela == null);
            else if (dataPaisXLSFile.compareTo(dataPaisTabela) <= 0)
            {
                return false;
            }
            
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlPais reg = null;

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
                escreverPaisTabela(reg);
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

    public void escreverPaisTabela(GrlPais reg)
    {
        if (grlPaisFacade.existeRegisto(reg.getPkIdPais()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public GrlPais lerCampos(HSSFRow row)
    {
        int pk_id_pais;
        String nome_pais;
        String nacionalidade;
        
        final int PK_ID_PAIS = 0;
        final int NOME_PAIS = 1;
        final int NACIONALIDADE = 2;
        
        GrlPais reg = new GrlPais();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_PAIS:
                    pk_id_pais = (int)cell.getNumericCellValue();
                    reg.setPkIdPais(pk_id_pais);
                break;
                    
                case NOME_PAIS:
                    nome_pais = cell.getStringCellValue().trim();
                    reg.setNomePais(nome_pais);
                break;
                    
                case NACIONALIDADE:
                    nacionalidade = cell.getStringCellValue().trim();
                    reg.setNacionalidade(nacionalidade);
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlPais reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlPaisFacade.create(reg);
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

    public boolean editRegister(GrlPais reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlPaisFacade.edit(reg);
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
