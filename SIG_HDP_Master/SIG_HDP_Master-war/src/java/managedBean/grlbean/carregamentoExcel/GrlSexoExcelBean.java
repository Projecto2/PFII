/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlSexo;
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
import sessao.GrlSexoFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlSexoExcelBean implements Serializable
{

    @EJB
    private GrlSexoFacade grlSexoFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlSexoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlSexoExcelBean()
    {
    }

    public static GrlSexoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlSexoExcelBean grlSexoExcelBean = 
            (GrlSexoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlSexoExcelBean");
        
        return grlSexoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarSexoTabela()
    {
        System.out.println("Esta carregando sexo");
//        
//        Date dataSexoTabela = this.grlUpdatesFacade.dataSexo();
//
//        Date dataSexoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_SEXO);
//
//        if (this.grlSexoFacade.findAll().isEmpty() || dataSexoTabela == null);
//        else if (!grlSexoFacade.findAll().isEmpty() && (dataSexoXLSFile != null && dataSexoXLSFile.compareTo(dataSexoTabela) <= 0))
//        {
//            return;
//        }

        if (lerSexoTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoSexoTabela();
        }
    }

    public boolean lerSexoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_SEXO);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("sexo");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataSexoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_SEXO);
            
            
            Date dataSexoTabela = this.grlUpdatesFacade.dataSexo();
            
            
            if (dataSexoTabela == null);
            else if (dataSexoXLSFile.compareTo(dataSexoTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlSexo reg = null;

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
                
                escreverSexoTabela(reg);
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

    public void escreverSexoTabela(GrlSexo reg)
    {
        if (grlSexoFacade.find(reg.getPkIdSexo()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public GrlSexo lerCampos(HSSFRow row)
    {
        int pk_id_sexo;
        String codigo_sexo;
        String descricao;
        
        final int PK_ID_SEXO = 0;
        final int CODIGO_SEXO = 1;
        final int DESCRICAO = 2;
        
        GrlSexo reg = new GrlSexo();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_SEXO:
                    pk_id_sexo = (int)cell.getNumericCellValue();
                    reg.setPkIdSexo(pk_id_sexo);
                break;
                    
                case CODIGO_SEXO:
                    codigo_sexo = cell.getStringCellValue();
                    reg.setCodigoSexo(codigo_sexo);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
                    
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlSexo reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlSexoFacade.create(reg);
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

    public boolean editRegister(GrlSexo reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlSexoFacade.edit(reg);
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
