/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.carregamentoExcel;

import entidade.AdmsEstadoPagamento;
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
import sessao.AdmsEstadoPagamentoFacade;
import sessao.AdmsUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsEstadoPagamentoExcelBean
{

    @EJB
    private AdmsEstadoPagamentoFacade admsEstadoPagamentoFacade;
    @EJB
    private AdmsUpdatesFacade admsUpdatesFacade;
    
    /**
     * Creates a new instance of AdmsEstadoPagamentoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;
    
    private Date dataEstadoPagamentoXLSFile = new Date();

    public AdmsEstadoPagamentoExcelBean()
    {
    }

    public static AdmsEstadoPagamentoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsEstadoPagamentoExcelBean admsEstadoPagamentoExcelBean = 
            (AdmsEstadoPagamentoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsEstadoPagamentoExcelBean");
        
        return admsEstadoPagamentoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarEstadoPagamentoTabela()
    {
//        Date dataEstadoPagamentoTabela = this.admsUpdatesFacade.dataEstadoPagamento();
//
//        Date dataEstadoPagamentoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.adms.Defs.FILE_ESTADO_PAGAMENTO_ADMS);
//
//        if (this.admsEstadoPagamentoFacade.isEstadoPagamentoTabelaEmpty() || dataEstadoPagamentoTabela == null);
//        else if (!admsEstadoPagamentoFacade.isEstadoPagamentoTabelaEmpty() && (dataEstadoPagamentoXLSFile != null && dataEstadoPagamentoXLSFile.compareTo(dataEstadoPagamentoTabela) <= 0))
//        {
//            return;
//        }

        if (lerDataEstadoPagamentoTabelaTabela())
        {
            this.admsUpdatesFacade.escreverDataEstadoPagamentoTabela(dataEstadoPagamentoXLSFile);
        }
    }

    public boolean lerDataEstadoPagamentoTabelaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.adms.Defs.FILE_ESTADO_PAGAMENTO_ADMS);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("estados_pagamento");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            /*Date*/ dataEstadoPagamentoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.adms.Defs.FILE_ESTADO_PAGAMENTO_ADMS);
            
            
            Date dataEstadoPagamentoTabela = this.admsUpdatesFacade.dataEstadoPagamento();
            
            
            if (dataEstadoPagamentoTabela == null);
            else if (dataEstadoPagamentoXLSFile.compareTo(dataEstadoPagamentoTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            AdmsEstadoPagamento reg = null;

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
                
                escreverEstadoPagamentoTabela(reg);
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

    public void escreverEstadoPagamentoTabela(AdmsEstadoPagamento reg)
    {
        if(reg.getPkIdEstadoPagamento() == null) return;
        if (admsEstadoPagamentoFacade.existeRegisto(reg.getPkIdEstadoPagamento()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public AdmsEstadoPagamento lerCampos(HSSFRow row)
    {
        int pk_id_estado_pagamento;
        String descricao_estado_pagamento;
        
        final int PK_ID_ESTADO_PAGAMENTO = 0;
        final int DESCRICAO_ESTADO_PAGAMENTO = 1;
        
        AdmsEstadoPagamento reg = new AdmsEstadoPagamento();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_ESTADO_PAGAMENTO:
                    pk_id_estado_pagamento = (int)cell.getNumericCellValue();
                    reg.setPkIdEstadoPagamento(pk_id_estado_pagamento);
                break;
                    
                case DESCRICAO_ESTADO_PAGAMENTO:
                    descricao_estado_pagamento = cell.getStringCellValue().trim();
                    reg.setDescricaoEstadoPagamento(descricao_estado_pagamento);
                break;
            }
        }
        return reg;
    }
    
    
    public boolean createRegister(AdmsEstadoPagamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsEstadoPagamentoFacade.create(reg);
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

    public boolean editRegister(AdmsEstadoPagamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsEstadoPagamentoFacade.edit(reg);
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
