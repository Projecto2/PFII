/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlReligiao;
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
import sessao.GrlReligiaoFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlReligiaoExcelBean implements Serializable
{

    @EJB
    private GrlReligiaoFacade grlReligiaoFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlReligiaoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlReligiaoExcelBean()
    {
    }

    public static GrlReligiaoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlReligiaoExcelBean grlReligiaoExcelBean = 
            (GrlReligiaoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlReligiaoExcelBean");
        
        return grlReligiaoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarReligiaoTabela()
    {
        System.out.println("Esta carregando religiao");
//        
//        Date dataReligiaoTabela = this.grlUpdatesFacade.dataReligiao();
//
//        Date dataReligiaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_RELIGIAO);
//
//        if (this.grlReligiaoFacade.findAll().isEmpty() || dataReligiaoTabela == null);
//        else if (!grlReligiaoFacade.findAll().isEmpty() && (dataReligiaoXLSFile != null && dataReligiaoXLSFile.compareTo(dataReligiaoTabela) <= 0))
//        {
//            return;
//        }

        if (lerReligiaoTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoReligiaoTabela();
        }
    }

    public boolean lerReligiaoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_RELIGIAO);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("religiao");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataReligiaoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_RELIGIAO);
            
            
            Date dataReligiaoTabela = this.grlUpdatesFacade.dataReligiao();
            
            
            if (dataReligiaoTabela == null);
            else if (dataReligiaoXLSFile.compareTo(dataReligiaoTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlReligiao reg = null;

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
                
                escreverReligiaoTabela(reg);
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

    public void escreverReligiaoTabela(GrlReligiao reg)
    {
        if (grlReligiaoFacade.find(reg.getPkIdReligiao()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public GrlReligiao lerCampos(HSSFRow row)
    {
        int pk_id_religiao;
        String descricao;
        
        final int PK_ID_RELIGIAO = 0;
        final int DESCRICAO = 1;
        
        GrlReligiao reg = new GrlReligiao();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_RELIGIAO:
                    pk_id_religiao = (int)cell.getNumericCellValue();
                    reg.setPkIdReligiao(pk_id_religiao);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
                    
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlReligiao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlReligiaoFacade.create(reg);
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

    public boolean editRegister(GrlReligiao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlReligiaoFacade.edit(reg);
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
