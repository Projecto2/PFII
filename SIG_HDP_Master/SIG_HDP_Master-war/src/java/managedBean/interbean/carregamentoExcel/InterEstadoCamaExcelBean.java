/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.carregamentoExcel;

import entidade.InterEstadoCama;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.interbean.InterEstadoCamaListarBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.InterEstadoCamaFacade;
import sessao.InterUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterEstadoCamaExcelBean implements Serializable
{
    @EJB
    private InterEstadoCamaFacade interEstadoCamaFacade;
 
    @EJB
    private InterUpdatesFacade interUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    
    /**
     * Creates a new instance of InterEstadoCamaExcelBean
     */
    public InterEstadoCamaExcelBean()
    {
    }
    
    public static InterEstadoCamaExcelBean getInstanciaBean()
    {
        return (InterEstadoCamaExcelBean) GeradorCodigo.getInstanciaBean("interEstadoCamaExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarEstadoCamaTabela(int opcao)
    {
        Date dataEstadoCamaTabela = this.interUpdatesFacade.dataEstadoCamaTabela();

        Date dataEstadoCamaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.inter.Defs.FILE_ESTADO_CAMA_INTER);

        if (this.interEstadoCamaFacade.isEstadoCamaTabelaEmpty() || dataEstadoCamaTabela == null);
        else if (!interEstadoCamaFacade.isEstadoCamaTabelaEmpty() && (dataEstadoCamaXLSFile != null && dataEstadoCamaXLSFile.compareTo(dataEstadoCamaTabela) <= 0))
        {
            return;
        }

        if (lerEstadoCamaTabela())
        {
            this.interUpdatesFacade.escreverDataActualizacaoEstadoCamaTabela();
        }
        
        if (opcao == 1)        
            InterEstadoCamaListarBean.getInstanciaBean().findByDescricao();
    }

    public boolean lerEstadoCamaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.inter.Defs.FILE_ESTADO_CAMA_INTER);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("estadosCama");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataEstadoCamaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.inter.Defs.FILE_ESTADO_CAMA_INTER);
            
            
            Date dataEstadoCamaTabela = this.interUpdatesFacade.dataEstadoCamaTabela();
            
            
            if (dataEstadoCamaTabela == null);
            else if (dataEstadoCamaXLSFile.compareTo(dataEstadoCamaTabela) <= 0)
            {
                return false;
            }
            
            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            InterEstadoCama reg = null;

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
                
                escreverEstadoCamaTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverEstadoCamaTabela(InterEstadoCama reg)
    {

        if (interEstadoCamaFacade.find(reg.getPkIdEstadoCama()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }    
    
    public InterEstadoCama lerCampos(HSSFRow row)
    {
        int pk_id_estado_cama;
        String descricao;
        
        final int PK_ID_ESTADO_CAMA = 0;
        final int DESCRICAO = 1;
        
        InterEstadoCama reg = new InterEstadoCama();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_ESTADO_CAMA:
                    pk_id_estado_cama = (int)cell.getNumericCellValue();
                    reg.setPkIdEstadoCama(pk_id_estado_cama);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                break;                
            }
        }
        
        return reg;
    }        
    
    public boolean createRegister(InterEstadoCama reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interEstadoCamaFacade.create(reg);
            this.userTransaction.commit();
            return true;
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                System.out.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.out.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

    public boolean editRegister(InterEstadoCama reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interEstadoCamaFacade.edit(reg);
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
                System.out.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }
}
