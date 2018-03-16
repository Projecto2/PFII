/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlTipoDocumentoIdentificacao;
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
import sessao.GrlTipoDocumentoIdentificacaoFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlTipoDocumentoIdentificacaoExcelBean
{

    @EJB
    private GrlTipoDocumentoIdentificacaoFacade grlTipoDocumentoIdentificacaoFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlTipoDeTipoDocumentoIdentificacaoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlTipoDocumentoIdentificacaoExcelBean()
    {
    }

    public static GrlTipoDocumentoIdentificacaoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlTipoDocumentoIdentificacaoExcelBean grlTipoDocumentoIdentificacaoExcelBean = 
            (GrlTipoDocumentoIdentificacaoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlTipoDocumentoIdentificacaoExcelBean");
        
        return grlTipoDocumentoIdentificacaoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarTipoDocumentoIdentificacaoTabela()
    {
//        Date dataTipoDocumentoIdentificacaoTabela = this.grlUpdatesFacade.dataTipoDocumentoIdentificacao();
//
//        Date dataTipoDocumentoIdentificacaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_TIPO_DOCUMENTO_IDENTIFICACAO_GRL);
//
//        if (this.grlTipoDocumentoIdentificacaoFacade.isTipoDocumentoIdentificacaoTabelaEmpty() || dataTipoDocumentoIdentificacaoTabela == null);
//        else if (!grlTipoDocumentoIdentificacaoFacade.isTipoDocumentoIdentificacaoTabelaEmpty() && (dataTipoDocumentoIdentificacaoXLSFile != null && dataTipoDocumentoIdentificacaoXLSFile.compareTo(dataTipoDocumentoIdentificacaoTabela) <= 0))
//        {
//            return;
//        }

        if (lerTipoDocumentoIdentificacaoTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoTipoDocumentoIdentificacaoTabela();
        }
    }

    public boolean lerTipoDocumentoIdentificacaoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_TIPO_DOCUMENTO_IDENTIFICACAO_GRL);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipos_documento_identificacao");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataTipoDocXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_TIPO_DOCUMENTO_IDENTIFICACAO_GRL);
            
            
            Date dataTipoDocTabela = this.grlUpdatesFacade.dataTipoDocumentoIdentificacao();
            
            
            if (dataTipoDocTabela == null);
            else if (dataTipoDocXLSFile.compareTo(dataTipoDocTabela) <= 0)
            {
                return false;
            }
            
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlTipoDocumentoIdentificacao reg = null;

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
                
                escreverTipoDocumentoIdentificacaoTabela(reg);
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

    public void escreverTipoDocumentoIdentificacaoTabela(GrlTipoDocumentoIdentificacao reg)
    {
        if (grlTipoDocumentoIdentificacaoFacade.existeRegisto(reg.getPkTipoDocumentoIdentificacao()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public GrlTipoDocumentoIdentificacao lerCampos(HSSFRow row)
    {
        int pk_tipo_documento_identificacao;
        String descricao;
        
        final int PK_ID_TIPO_DOCUMENTO_IDENTIFICACAO = 0;
        final int DESCRICAO = 1;
        
        GrlTipoDocumentoIdentificacao reg = new GrlTipoDocumentoIdentificacao();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_TIPO_DOCUMENTO_IDENTIFICACAO:
                    pk_tipo_documento_identificacao = (int)cell.getNumericCellValue();
                    reg.setPkTipoDocumentoIdentificacao(pk_tipo_documento_identificacao);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlTipoDocumentoIdentificacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlTipoDocumentoIdentificacaoFacade.create(reg);
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

    public boolean editRegister(GrlTipoDocumentoIdentificacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlTipoDocumentoIdentificacaoFacade.edit(reg);
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
