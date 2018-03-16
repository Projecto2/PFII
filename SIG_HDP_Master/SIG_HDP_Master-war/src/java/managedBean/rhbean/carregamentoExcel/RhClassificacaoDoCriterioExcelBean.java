/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.rhbean.carregamentoExcel;

import entidade.RhClassificacaoDoCriterio;
import entidade.RhCriterioDeAvaliacao;
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
import sessao.RhClassificacaoDoCriterioFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhClassificacaoDoCriterioExcelBean implements Serializable
{

    @EJB
    private RhClassificacaoDoCriterioFacade rhClassificacaoDoCriterioFacade;
    @EJB
    private RhUpdatesFacade rhUpdatesFacade;
    
    /**
     * Creates a new instance of RhClassificacaoDoCriterioExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public RhClassificacaoDoCriterioExcelBean()
    {
    }

    public static RhClassificacaoDoCriterioExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        RhClassificacaoDoCriterioExcelBean rhClassificacaoDoCriterioExcelBean = 
            (RhClassificacaoDoCriterioExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "rhClassificacaoDoCriterioExcelBean");
        
        return rhClassificacaoDoCriterioExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarClassificacaoDoCriterioTabela()
    {
        System.out.println("Esta carregando classificacao_do_criterio");
        
        Date dataClassificacaoDoCriterioTabela = this.rhUpdatesFacade.dataClassificacaoDoCriterio();

        Date dataClassificacaoDoCriterioXLSFile = util.DataUtils.dataModificacaoFicheiro(util.rh.Defs.FILE_CLASSIFICACAO_DO_CRITERIO_RH);

        if (this.rhClassificacaoDoCriterioFacade.findAll().isEmpty() || dataClassificacaoDoCriterioTabela == null);
        else if (!rhClassificacaoDoCriterioFacade.findAll().isEmpty() && (dataClassificacaoDoCriterioXLSFile != null && dataClassificacaoDoCriterioXLSFile.compareTo(dataClassificacaoDoCriterioTabela) <= 0))
        {
            return;
        }

        if (lerClassificacaoDoCriterioTabela())
        {
            this.rhUpdatesFacade.escreverDataActualizacaoClassificacaoDoCriterioTabela();
        }
    }

    public boolean lerClassificacaoDoCriterioTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.rh.Defs.FILE_CLASSIFICACAO_DO_CRITERIO_RH);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("classificacao_do_criterio");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            RhClassificacaoDoCriterio reg = null;

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
                
                escreverClassificacaoDoCriterioTabela(reg);
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

    public void escreverClassificacaoDoCriterioTabela(RhClassificacaoDoCriterio reg)
    {
        if (rhClassificacaoDoCriterioFacade.find(reg.getPkIdClassificacaoDoCriterio()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public RhClassificacaoDoCriterio lerCampos(HSSFRow row)
    {
        int pk_id_classificacao_do_criterio;
        String descricao;
        int classificacao;
        int fk_id_criterio_de_avaliacao;
        
        final int PK_ID_CLASSIFICACAO_DO_CRITERIO = 0;
        final int DESCRICAO = 1;
        final int CLASSIFICACAO = 2;
        final int FK_ID_CRITERIO_DE_AVALIACAO = 3;
        
        RhClassificacaoDoCriterio reg = new RhClassificacaoDoCriterio();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_CLASSIFICACAO_DO_CRITERIO:
                    pk_id_classificacao_do_criterio = (int)cell.getNumericCellValue();
                    reg.setPkIdClassificacaoDoCriterio(pk_id_classificacao_do_criterio);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
                    
                case CLASSIFICACAO:
                    classificacao = (int)cell.getNumericCellValue();
                    reg.setClassificacao(classificacao);
                break;
                    
                case FK_ID_CRITERIO_DE_AVALIACAO:
                    fk_id_criterio_de_avaliacao = (int)cell.getNumericCellValue();
                    reg.setFkIdCriterioDeAvaliacao(new RhCriterioDeAvaliacao(fk_id_criterio_de_avaliacao));
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(RhClassificacaoDoCriterio reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhClassificacaoDoCriterioFacade.create(reg);
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

    public boolean editRegister(RhClassificacaoDoCriterio reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhClassificacaoDoCriterioFacade.edit(reg);
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
