/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.carregamentoExcel;

import entidade.InterParametroVital;
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
import managedBean.interbean.InterParametroVitalListarBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.InterParametroVitalFacade;
import sessao.InterUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterParametroVitalExcelBean implements Serializable
{
    @EJB
    private InterParametroVitalFacade interParametroVitalFacade;

    @EJB
    private InterUpdatesFacade interUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    
    /**
     * Creates a new instance of InterParametroVitalExcelBean
     */
    public InterParametroVitalExcelBean()
    {
    }
    
    public static InterParametroVitalExcelBean getInstanciaBean()
    {
        return (InterParametroVitalExcelBean) GeradorCodigo.getInstanciaBean("interParametroVitalExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarParametroVitalTabela()
    {
        Date dataParametroVitalTabela = this.interUpdatesFacade.dataParametroVitalTabela();

        Date dataParametroVitalXLSFile = util.DataUtils.dataModificacaoFicheiro(util.inter.Defs.FILE_PARAMETRO_VITAL_INTER);

        if (this.interParametroVitalFacade.isParametroVitalTabelaEmpty() || dataParametroVitalTabela == null);
        else if (!interParametroVitalFacade.isParametroVitalTabelaEmpty() && (dataParametroVitalXLSFile != null && dataParametroVitalXLSFile.compareTo(dataParametroVitalTabela) <= 0))
        {
            return;
        }

        if (lerParametroVitalTabela())
        {
            this.interUpdatesFacade.escreverDataActualizacaoParametroVitalTabela();
        }
        
        InterParametroVitalListarBean.getInstanciaBean().findByDescricao();
    }

    public boolean lerParametroVitalTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.inter.Defs.FILE_PARAMETRO_VITAL_INTER);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("parametroVital");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataParametroVitalXLSFile = FileManagerGeral.lerVersaoTabela(row, util.inter.Defs.FILE_PARAMETRO_VITAL_INTER);
            
            
            Date dataParametroVitalTabela = this.interUpdatesFacade.dataParametroVitalTabela();
            
            
            if (dataParametroVitalTabela == null);
            else if (dataParametroVitalXLSFile.compareTo(dataParametroVitalTabela) <= 0)
            {
                return false;
            }
            
            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            InterParametroVital reg = null;

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
                
                escreverParametroVitalTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverParametroVitalTabela(InterParametroVital reg)
    {

        if (interParametroVitalFacade.find(reg.getPkIdParametroVital()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }    
    
    public InterParametroVital lerCampos(HSSFRow row)
    {
        int pk_id_parametro_vital;
        String descricao;
        
        final int PK_ID_PARAMETRO_VITAL = 0;
        final int DESCRICAO = 1;
        
        InterParametroVital reg = new InterParametroVital();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_PARAMETRO_VITAL:
                    pk_id_parametro_vital = (int)cell.getNumericCellValue();
                    reg.setPkIdParametroVital(pk_id_parametro_vital);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                break;                
            }
        }
        
        return reg;
    }        
    
    public boolean createRegister(InterParametroVital reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interParametroVitalFacade.create(reg);
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

    public boolean editRegister(InterParametroVital reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interParametroVitalFacade.edit(reg);
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
