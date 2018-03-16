/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.carregamentoExcel;

import entidade.AdmsTipoSolicitacaoServico;
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
import sessao.AdmsTipoSolicitacaoServicoFacade;
import sessao.AdmsUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsTipoSolicitacaoServicoExcelBean
{

    @EJB
    private AdmsTipoSolicitacaoServicoFacade admsTipoSolicitacaoServicoFacade;
    @EJB
    private AdmsUpdatesFacade admsUpdatesFacade;
    
    /**
     * Creates a new instance of AdmsTipoDeServicoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;
    
    private Date dataTipoSolicitacaoServicoXLSFile = new Date();

    public AdmsTipoSolicitacaoServicoExcelBean()
    {
    }

    public static AdmsTipoSolicitacaoServicoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsTipoSolicitacaoServicoExcelBean admsTipoSolicitacaoServicoExcelBean = 
            (AdmsTipoSolicitacaoServicoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsTipoSolicitacaoServicoExcelBean");
        
        return admsTipoSolicitacaoServicoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarTipoSolicitacaoServicoTabela()
    {
//        Date dataTipoSolicitacaoServicoTabela = this.admsUpdatesFacade.dataTipoSolicitacaoServico();
//
//        Date dataTipoSolicitacaoServicoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.adms.Defs.FILE_TIPO_SOLICITACAO_SERVICO_ADMS);
//
//        if (this.admsTipoSolicitacaoServicoFacade.isTipoSolicitacaoServicoTabelaEmpty() || dataTipoSolicitacaoServicoTabela == null);
//        else if (!admsTipoSolicitacaoServicoFacade.isTipoSolicitacaoServicoTabelaEmpty() && (dataTipoSolicitacaoServicoXLSFile != null && dataTipoSolicitacaoServicoXLSFile.compareTo(dataTipoSolicitacaoServicoTabela) <= 0))
//        {
//            return;
//        }

        if (lerTipoSolicitacaoServicoTabela())
        {
            this.admsUpdatesFacade.escreverDataTipoSolicitacaoServicoTabela(dataTipoSolicitacaoServicoXLSFile);
        }
    }

    public boolean lerTipoSolicitacaoServicoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.adms.Defs.FILE_TIPO_SOLICITACAO_SERVICO_ADMS);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipos_solicitacao");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            /*Date*/ dataTipoSolicitacaoServicoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.adms.Defs.FILE_TIPO_SOLICITACAO_SERVICO_ADMS);
            
            
            Date dataTipoSolicitacaoServicoTabela = this.admsUpdatesFacade.dataTipoSolicitacaoServico();
            
            
            if (dataTipoSolicitacaoServicoTabela == null);
            else if (dataTipoSolicitacaoServicoXLSFile.compareTo(dataTipoSolicitacaoServicoTabela) <= 0)
            {
                return false;
            }
            
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            AdmsTipoSolicitacaoServico reg = null;

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
                
                escreverTipoSolicitacaoServicoTabela(reg);
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

    public void escreverTipoSolicitacaoServicoTabela(AdmsTipoSolicitacaoServico reg)
    {
        if (admsTipoSolicitacaoServicoFacade.existeRegisto(reg.getPkIdTipoSolicitacao()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public AdmsTipoSolicitacaoServico lerCampos(HSSFRow row)
    {
        int pk_id_tipo_solicitacao;
        String descricao_tipo_solicitacao_servico;
        
        final int PK_ID_TIPO_SOLICITACAO = 0;
        final int DESCRICAO_TIPO_SOLICITACAO_SERVICO = 1;
        
        AdmsTipoSolicitacaoServico reg = new AdmsTipoSolicitacaoServico();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_TIPO_SOLICITACAO:
                    pk_id_tipo_solicitacao = (int)cell.getNumericCellValue();
                    reg.setPkIdTipoSolicitacao(pk_id_tipo_solicitacao);
                break;
                    
                case DESCRICAO_TIPO_SOLICITACAO_SERVICO:
                    descricao_tipo_solicitacao_servico = cell.getStringCellValue().trim();
                    reg.setDescricaoTipoSolicitacaoServico(descricao_tipo_solicitacao_servico);
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(AdmsTipoSolicitacaoServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsTipoSolicitacaoServicoFacade.create(reg);
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

    public boolean editRegister(AdmsTipoSolicitacaoServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsTipoSolicitacaoServicoFacade.edit(reg);
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
