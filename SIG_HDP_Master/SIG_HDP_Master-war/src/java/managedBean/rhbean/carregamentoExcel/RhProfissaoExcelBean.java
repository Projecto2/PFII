/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.rhbean.carregamentoExcel;

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
import sessao.RhUpdatesFacade;
import sessao.RhProfissaoFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhProfissaoExcelBean implements Serializable
{

    @EJB
    private RhProfissaoFacade rhProfissaoFacade;
    @EJB
    private RhUpdatesFacade rhUpdatesFacade;
    
    /**
     * Creates a new instance of RhProfissaoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public RhProfissaoExcelBean()
    {
    }

    public static RhProfissaoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        RhProfissaoExcelBean rhProfissaoExcelBean = 
            (RhProfissaoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "rhProfissaoExcelBean");
        
        return rhProfissaoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarProfissaoTabela()
    {
        System.out.println("Esta carregando profissao");
        
        Date dataProfissaoTabela = this.rhUpdatesFacade.dataProfissao();

        Date dataProfissaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.rh.Defs.FILE_PROFISSAO_RH);

        if (this.rhProfissaoFacade.findAll().isEmpty() || dataProfissaoTabela == null);
        else if (!rhProfissaoFacade.findAll().isEmpty() && (dataProfissaoXLSFile != null && dataProfissaoXLSFile.compareTo(dataProfissaoTabela) <= 0))
        {
            return;
        }

        if (lerProfissaoTabela())
        {
            this.rhUpdatesFacade.escreverDataActualizacaoProfissaoTabela();
        }
    }

    public boolean lerProfissaoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.rh.Defs.FILE_PROFISSAO_RH);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("profissao");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            RhProfissao reg = null;

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
                
                escreverProfissaoTabela(reg);
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

    public void escreverProfissaoTabela(RhProfissao reg)
    {
        if (rhProfissaoFacade.find(reg.getPkIdProfissao()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public RhProfissao lerCampos(HSSFRow row)
    {
        int pk_id_profissao;
        String descricao;
        
        final int PK_ID_PROFISSAO = 0;
        final int DESCRICAO = 1;
        
        RhProfissao reg = new RhProfissao();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_PROFISSAO:
                    pk_id_profissao = (int)cell.getNumericCellValue();
                    reg.setPkIdProfissao(pk_id_profissao);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(RhProfissao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhProfissaoFacade.create(reg);
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

    public boolean editRegister(RhProfissao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhProfissaoFacade.edit(reg);
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
