/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.carregamentoExcel;

import entidade.AdmsClassificacaoServicoSolicitado;
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
import sessao.AdmsClassificacaoServicoSolicitadoFacade;
import sessao.AdmsUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsClassificacaoServicoSolicitadoExcelBean
{

    @EJB
    private AdmsClassificacaoServicoSolicitadoFacade admsClassificacaoServicoSolicitadoFacade;
    @EJB
    private AdmsUpdatesFacade admsUpdatesFacade;
    
    /**
     * Creates a new instance of AdmsClassificacaoServicoSolicitadoExcelBean
     */
    
    private Date dataClassificacaoServicoXLSFile = new Date();
    
    @Resource
    private UserTransaction userTransaction;

    public AdmsClassificacaoServicoSolicitadoExcelBean()
    {
    }

    public static AdmsClassificacaoServicoSolicitadoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsClassificacaoServicoSolicitadoExcelBean admsClassificacaoServicoSolicitadoExcelBean = 
            (AdmsClassificacaoServicoSolicitadoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsClassificacaoServicoSolicitadoExcelBean");
        
        return admsClassificacaoServicoSolicitadoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarClassificacaoServicoSolicitadoTabela()
    {
//        Date dataClassificacaoServicoSolicitadoTabela = this.admsUpdatesFacade.dataClassificacaoServicoSolicitado();
//
//        Date dataClassificacaoServicoSolicitadoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.adms.Defs.FILE_CLASSIFICACAO_SERVICO_SOLICITADO_ADMS);
//
//        if (this.admsClassificacaoServicoSolicitadoFacade.isClassificacaoServicoSolicitadoTabelaEmpty() || dataClassificacaoServicoSolicitadoTabela == null);
//        else if (!admsClassificacaoServicoSolicitadoFacade.isClassificacaoServicoSolicitadoTabelaEmpty() && (dataClassificacaoServicoSolicitadoXLSFile != null && dataClassificacaoServicoSolicitadoXLSFile.compareTo(dataClassificacaoServicoSolicitadoTabela) <= 0))
//        {
//            return;
//        }

        if (lerClassificacaoDeServicoSolicitadoTabela())
        {
            this.admsUpdatesFacade.escreverDataClassificacaoServicoSolicitadoTabela(dataClassificacaoServicoXLSFile);
        }
    }

    public boolean lerClassificacaoDeServicoSolicitadoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.adms.Defs.FILE_CLASSIFICACAO_SERVICO_SOLICITADO_ADMS);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("classificacao_servico_solicitado");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            /*Date*/ dataClassificacaoServicoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.adms.Defs.FILE_CLASSIFICACAO_SERVICO_SOLICITADO_ADMS);
            
            
            Date dataClassificacaoServicoTabela = this.admsUpdatesFacade.dataClassificacaoServicoSolicitado();
            
            
            if (dataClassificacaoServicoTabela == null);
            else if (dataClassificacaoServicoXLSFile.compareTo(dataClassificacaoServicoTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            AdmsClassificacaoServicoSolicitado reg = null;

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
                
                escreverClassificacaoServicoSolicitadoTabela(reg);
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

    public void escreverClassificacaoServicoSolicitadoTabela(AdmsClassificacaoServicoSolicitado reg)
    {
        if(reg.getPkIdClassificacaoServicoSolicitado() == null) return;
        if (admsClassificacaoServicoSolicitadoFacade.existeRegisto(reg.getPkIdClassificacaoServicoSolicitado()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public AdmsClassificacaoServicoSolicitado lerCampos(HSSFRow row)
    {
        int pk_id_classificacao_servico_solicitado;
        String descricao_classificacao_servico_solicitado;
        
        final int PK_ID_CLASSIFICACAO_SERVICO_SOLICITADO = 0;
        final int DESCRICAO_CLASSIFICACAO_SERVICO_SOLICITADO = 1;
        
        AdmsClassificacaoServicoSolicitado reg = new AdmsClassificacaoServicoSolicitado();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_CLASSIFICACAO_SERVICO_SOLICITADO:
                    pk_id_classificacao_servico_solicitado = (int)cell.getNumericCellValue();
                    reg.setPkIdClassificacaoServicoSolicitado(pk_id_classificacao_servico_solicitado);
                break;
                    
                case DESCRICAO_CLASSIFICACAO_SERVICO_SOLICITADO:
                    descricao_classificacao_servico_solicitado = cell.getStringCellValue().trim();
                    reg.setDescricaoClassificacaoServicoSolicitado(descricao_classificacao_servico_solicitado);
                break;
            }
        }
        return reg;
    }
    
    
    public boolean createRegister(AdmsClassificacaoServicoSolicitado reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsClassificacaoServicoSolicitadoFacade.create(reg);
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

    public boolean editRegister(AdmsClassificacaoServicoSolicitado reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsClassificacaoServicoSolicitadoFacade.edit(reg);
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
