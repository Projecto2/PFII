/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.carregamentoExcel;

import entidade.AdmsEstadoAgendamento;
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
import sessao.AdmsEstadoAgendamentoFacade;
import sessao.AdmsUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsEstadoAgendamentoExcelBean
{

    @EJB
    private AdmsEstadoAgendamentoFacade admsEstadoAgendamentoFacade;
    @EJB
    private AdmsUpdatesFacade admsUpdatesFacade;
    
    /**
     * Creates a new instance of AdmsEstadoAgendamentoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;
    
    private Date dataEstadoAgendamentoXLSFile = new Date();

    public AdmsEstadoAgendamentoExcelBean()
    {
    }

    public static AdmsEstadoAgendamentoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsEstadoAgendamentoExcelBean admsEstadoAgendamentoExcelBean = 
            (AdmsEstadoAgendamentoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsEstadoAgendamentoExcelBean");
        
        return admsEstadoAgendamentoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarEstadoAgendamentoTabela()
    {
//        Date dataEstadoAgendamentoTabela = this.admsUpdatesFacade.dataEstadoAgendamento();
//
//        Date dataEstadoAgendamentoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.adms.Defs.FILE_ESTADO_AGENDAMENTO_ADMS);
//
//        if (this.admsEstadoAgendamentoFacade.isEstadoAgendamentoTabelaEmpty() || dataEstadoAgendamentoTabela == null);
//        else if (!admsEstadoAgendamentoFacade.isEstadoAgendamentoTabelaEmpty() && (dataEstadoAgendamentoXLSFile != null && dataEstadoAgendamentoXLSFile.compareTo(dataEstadoAgendamentoTabela) <= 0))
//        {
//            return;
//        }

        if (lerEstadoAgendamentoTabela())
        {
            this.admsUpdatesFacade.escreverDataEstadoAgendamentoTabela(dataEstadoAgendamentoXLSFile);
        }
    }

    public boolean lerEstadoAgendamentoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.adms.Defs.FILE_ESTADO_AGENDAMENTO_ADMS);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("estados_agendamento");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            /*Date*/ dataEstadoAgendamentoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.adms.Defs.FILE_ESTADO_AGENDAMENTO_ADMS);
            
            
            Date dataEstadoAgendamentoTabela = this.admsUpdatesFacade.dataEstadoAgendamento();
            
            
            if (dataEstadoAgendamentoTabela == null);
            else if (dataEstadoAgendamentoXLSFile.compareTo(dataEstadoAgendamentoTabela) <= 0)
            {
                return false;
            }
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            AdmsEstadoAgendamento reg = null;

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
                
                escreverEstadoAgendamentoTabela(reg);
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

    public void escreverEstadoAgendamentoTabela(AdmsEstadoAgendamento reg)
    {
        if(reg.getPkIdEstadoAgendamento() == null) return;
        if (admsEstadoAgendamentoFacade.existeRegisto(reg.getPkIdEstadoAgendamento()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public AdmsEstadoAgendamento lerCampos(HSSFRow row)
    {
        int pk_id_estado_agendamento;
        String descricao_estado_agendamento;
        
        final int PK_ID_ESTADO_AGENDAMENTO = 0;
        final int DESCRICAO_ESTADO_AGENDAMENTO = 1;
        
        AdmsEstadoAgendamento reg = new AdmsEstadoAgendamento();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_ESTADO_AGENDAMENTO:
                    pk_id_estado_agendamento = (int)cell.getNumericCellValue();
                    reg.setPkIdEstadoAgendamento(pk_id_estado_agendamento);
                break;
                    
                case DESCRICAO_ESTADO_AGENDAMENTO:
                    descricao_estado_agendamento = cell.getStringCellValue().trim();
                    reg.setDescricaoEstadoAgendamento(descricao_estado_agendamento);
                break;
            }
        }
        return reg;
    }
    
    
    public boolean createRegister(AdmsEstadoAgendamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsEstadoAgendamentoFacade.create(reg);
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

    public boolean editRegister(AdmsEstadoAgendamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsEstadoAgendamentoFacade.edit(reg);
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
