/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.rhbean.carregamentoExcel;

import entidade.RhFormaPagamento;
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
import sessao.RhFormaPagamentoFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhFormaPagamentoExcelBean implements Serializable
{

    @EJB
    private RhFormaPagamentoFacade rhFormaPagamentoFacade;
    @EJB
    private RhUpdatesFacade rhUpdatesFacade;
    
    /**
     * Creates a new instance of RhFormaPagamentoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public RhFormaPagamentoExcelBean()
    {
    }

    public static RhFormaPagamentoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        RhFormaPagamentoExcelBean rhFormaPagamentoExcelBean = 
            (RhFormaPagamentoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "rhFormaPagamentoExcelBean");
        
        return rhFormaPagamentoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarFormaPagamentoTabela()
    {
        System.out.println("Esta carregando forma_de_pagamento");
        
        Date dataFormaPagamentoTabela = this.rhUpdatesFacade.dataFormaPagamento();

        Date dataFormaPagamentoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.rh.Defs.FILE_FORMA_PAGAMENTO_RH);

        if (this.rhFormaPagamentoFacade.findAll().isEmpty() || dataFormaPagamentoTabela == null);
        else if (!rhFormaPagamentoFacade.findAll().isEmpty() && (dataFormaPagamentoXLSFile != null && dataFormaPagamentoXLSFile.compareTo(dataFormaPagamentoTabela) <= 0))
        {
            return;
        }

        if (lerFormaPagamentoTabela())
        {
            this.rhUpdatesFacade.escreverDataActualizacaoFormaPagamentoTabela();
        }
    }

    public boolean lerFormaPagamentoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.rh.Defs.FILE_FORMA_PAGAMENTO_RH);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("forma_pagamento");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            RhFormaPagamento reg = null;

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
                
                escreverFormaPagamentoTabela(reg);
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

    public void escreverFormaPagamentoTabela(RhFormaPagamento reg)
    {
        if (rhFormaPagamentoFacade.find(reg.getPkIdFormaPagamento()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public RhFormaPagamento lerCampos(HSSFRow row)
    {
        int pk_id_forma_pagamento;
        String descricao;
        
        final int PK_ID_FORMA_DE_PAGAMENTO = 0;
        final int DESCRICAO = 1;
        
        RhFormaPagamento reg = new RhFormaPagamento();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_FORMA_DE_PAGAMENTO:
                    pk_id_forma_pagamento = (int)cell.getNumericCellValue();
                    reg.setPkIdFormaPagamento(pk_id_forma_pagamento);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(RhFormaPagamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhFormaPagamentoFacade.create(reg);
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

    public boolean editRegister(RhFormaPagamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhFormaPagamentoFacade.edit(reg);
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
