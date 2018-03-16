/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.carregamentoExcel;

import entidade.AdmsServico;
import entidade.InterEnfermaria;
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
import managedBean.interbean.InterEnfermariaListarBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.InterEnfermariaFacade;
import sessao.InterUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterEnfermariaExcelBean implements Serializable
{   
    @EJB
    private InterEnfermariaFacade interEnfermariaFacade;
    
    @EJB
    private InterUpdatesFacade interUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    
    /**
     * Creates a new instance of InterEnfermariaExcelBean
     */
    public InterEnfermariaExcelBean()
    {
    }
    
    public static InterEnfermariaExcelBean getInstanciaBean()
    {
        return (InterEnfermariaExcelBean) GeradorCodigo.getInstanciaBean("interEnfermariaExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarEnfermariaTabela()
    {
        Date dataEnfermariaTabela = this.interUpdatesFacade.dataEnfermariaTabela();

        Date dataEnfermariaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.inter.Defs.FILE_ENFERAMRIAS_INTER);

        if (this.interEnfermariaFacade.isEnfermariaTabelaEmpty() || dataEnfermariaTabela == null);
        else if (!interEnfermariaFacade.isEnfermariaTabelaEmpty() && (dataEnfermariaXLSFile != null && dataEnfermariaXLSFile.compareTo(dataEnfermariaTabela) <= 0))
        {
            return;
        }

        if (lerEnfermariaTabela())
        {
            this.interUpdatesFacade.escreverDataActualizacaoEnfermariaTabela();
        }
        
        InterEnfermariaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerEnfermariaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.inter.Defs.FILE_ENFERAMRIAS_INTER);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("enfermarias");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataEnfermariaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.inter.Defs.FILE_ENFERAMRIAS_INTER);
            
            
            Date dataEnfermariaTabela = this.interUpdatesFacade.dataEnfermariaTabela();
            
            
            if (dataEnfermariaTabela == null);
            else if (dataEnfermariaXLSFile.compareTo(dataEnfermariaTabela) <= 0)
            {
                return false;
            }
            
            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            InterEnfermaria reg = null;

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
                
                escreverEnfermariaTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverEnfermariaTabela(InterEnfermaria reg)
    {

        if (interEnfermariaFacade.find(reg.getPkIdEnfermaria()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }    
    
    public InterEnfermaria lerCampos(HSSFRow row)
    {
        int pk_id_enfermaria;
        String descricao;
        int fk_id_servico;
        String codigo_enfermaria;
        
        final int PK_ID_ENFERMARIA = 0;
        final int DESCRICAO = 1;
        final int FK_ID_SERVICO = 2;
        final int CODIGO_ENFERMARIA = 3;
        
        InterEnfermaria reg = new InterEnfermaria();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_ENFERMARIA:
                    pk_id_enfermaria = (int)cell.getNumericCellValue();
                    reg.setPkIdEnfermaria(pk_id_enfermaria);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                break;
                    
                case FK_ID_SERVICO:
                    if(cell == null)
                    {
                        reg.setFkIdServico(null);
                    }
                    else
                    {
                        fk_id_servico = (int)cell.getNumericCellValue();
                        reg.setFkIdServico(new AdmsServico(fk_id_servico)); 
                    }
                break;
                    
                case CODIGO_ENFERMARIA:
                    if(cell == null)
                    {
                        reg.setCodigoEnfermaria(null);
                    }
                    else
                    {
                        codigo_enfermaria = cell.getStringCellValue().trim();
                        reg.setCodigoEnfermaria(codigo_enfermaria);
                    }
                break;                  
            }
        }
        
        return reg;
    }        
    
    public boolean createRegister(InterEnfermaria reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interEnfermariaFacade.create(reg);
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

    public boolean editRegister(InterEnfermaria reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interEnfermariaFacade.edit(reg);
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
