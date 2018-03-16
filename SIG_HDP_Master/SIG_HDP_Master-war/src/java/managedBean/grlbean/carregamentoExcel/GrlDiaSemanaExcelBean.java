/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlDiaSemana;
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
import sessao.GrlDiaSemanaFacade;
import util.FileManagerGeral;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlDiaSemanaExcelBean implements Serializable
{

    @EJB
    private GrlDiaSemanaFacade grlDiaSemanaFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlDiaSemanaExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlDiaSemanaExcelBean()
    {
    }

    public static GrlDiaSemanaExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlDiaSemanaExcelBean grlDiaSemanaExcelBean = 
            (GrlDiaSemanaExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlDiaSemanaExcelBean");
        
        return grlDiaSemanaExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarDiaSemanaTabela()
    {
        System.out.println("Esta carregando dia_semana");
//        
//        Date dataDiaSemanaTabela = this.grlUpdatesFacade.dataDiaSemana();
//
//        Date dataDiaSemanaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_DIA_SEMANA);
//
//        if (this.grlDiaSemanaFacade.findAll().isEmpty() || dataDiaSemanaTabela == null);
//        else if (!grlDiaSemanaFacade.findAll().isEmpty() && (dataDiaSemanaXLSFile != null && dataDiaSemanaXLSFile.compareTo(dataDiaSemanaTabela) <= 0))
//        {
//            return;
//        }

        if (lerDiaSemanaTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoDiaSemanaTabela();
        }
    }

    public boolean lerDiaSemanaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_DIA_SEMANA);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("dia_semana");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataDiaSemanaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_DIA_SEMANA);
            
            
            Date dataDiaSemanaTabela = this.grlUpdatesFacade.dataDiaSemana();
            
            
            if (dataDiaSemanaTabela == null);
            else if (dataDiaSemanaXLSFile.compareTo(dataDiaSemanaTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlDiaSemana reg = null;

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
                
                escreverDiaSemanaTabela(reg);
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

    public void escreverDiaSemanaTabela(GrlDiaSemana reg)
    {
        if (grlDiaSemanaFacade.find(reg.getPkIdDiaSemana()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public GrlDiaSemana lerCampos(HSSFRow row)
    {
        int pk_iddia_semana;
        String codigo_dia_semana;
        String descricao;
        
        final int PK_ID_ESPECIALIDADE = 0;
        final int CODIGO_DIA_SEMANA = 1;
        final int DESCRICAO = 2;
        
        GrlDiaSemana reg = new GrlDiaSemana();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_ESPECIALIDADE:
                    pk_iddia_semana = (int)cell.getNumericCellValue();
                    reg.setPkIdDiaSemana(pk_iddia_semana);
                break;
                    
                case CODIGO_DIA_SEMANA:
                    codigo_dia_semana = cell.getStringCellValue();
                    reg.setCodigoDiaSemana(codigo_dia_semana);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
                    
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlDiaSemana reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlDiaSemanaFacade.create(reg);
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

    public boolean editRegister(GrlDiaSemana reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlDiaSemanaFacade.edit(reg);
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
