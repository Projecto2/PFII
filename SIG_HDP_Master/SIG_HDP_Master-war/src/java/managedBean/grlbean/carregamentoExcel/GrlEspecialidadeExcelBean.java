/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlEspecialidade;
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
import sessao.GrlEspecialidadeFacade;
import util.FileManagerGeral;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlEspecialidadeExcelBean implements Serializable
{

    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlEspecialidadeExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlEspecialidadeExcelBean()
    {
    }

    public static GrlEspecialidadeExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlEspecialidadeExcelBean grlEspecialidadeExcelBean = 
            (GrlEspecialidadeExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlEspecialidadeExcelBean");
        
        return grlEspecialidadeExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarEspecialidadeTabela()
    {
        System.out.println("Esta carregando especialidade");
        
//        Date dataEspecialidadeTabela = this.grlUpdatesFacade.dataEspecialidade();
//
//        Date dataEspecialidadeXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_ESPECIALIDADE);
//
//        if (this.grlEspecialidadeFacade.findAll().isEmpty() || dataEspecialidadeTabela == null);
//        else if (!grlEspecialidadeFacade.findAll().isEmpty() && (dataEspecialidadeXLSFile != null && dataEspecialidadeXLSFile.compareTo(dataEspecialidadeTabela) <= 0))
//        {
//            return;
//        }

        if (lerEspecialidadeTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoEspecialidadeTabela();
        }
    }

    public boolean lerEspecialidadeTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_ESPECIALIDADE);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("especialidade");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataEspecialidadeXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_ESPECIALIDADE);
            
            
            Date dataEspecialidadeTabela = this.grlUpdatesFacade.dataEspecialidade();
            
            
            if (dataEspecialidadeTabela == null);
            else if (dataEspecialidadeXLSFile.compareTo(dataEspecialidadeTabela) <= 0)
            {
                return false;
            }
            
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlEspecialidade reg = null;

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
                
                escreverEspecialidadeTabela(reg);
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

    public void escreverEspecialidadeTabela(GrlEspecialidade reg)
    {
        if (grlEspecialidadeFacade.find(reg.getPkIdEspecialidade()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public GrlEspecialidade lerCampos(HSSFRow row)
    {
        int pk_id_especialidade;
        String descricao;
        int fk_id_profissao;
        
        final int PK_ID_ESPECIALIDADE = 0;
        final int DESCRICAO = 1;
        final int FK_ID_PROFISSAO = 2;
        
        GrlEspecialidade reg = new GrlEspecialidade();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_ESPECIALIDADE:
                    pk_id_especialidade = (int)cell.getNumericCellValue();
                    reg.setPkIdEspecialidade(pk_id_especialidade);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
                    
                case FK_ID_PROFISSAO:
                    fk_id_profissao = (int)cell.getNumericCellValue();
                    reg.setFkIdProfissao(new RhProfissao(fk_id_profissao));
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlEspecialidade reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlEspecialidadeFacade.create(reg);
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

    public boolean editRegister(GrlEspecialidade reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlEspecialidadeFacade.edit(reg);
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
