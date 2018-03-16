/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.rhbean.carregamentoExcel;

import entidade.RhSubsidio;
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
import sessao.RhSubsidioFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhSubsidioExcelBean implements Serializable
{

    @EJB
    private RhSubsidioFacade rhSubsidioFacade;
    @EJB
    private RhUpdatesFacade rhUpdatesFacade;
    
    /**
     * Creates a new instance of RhTipoServicoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public RhSubsidioExcelBean()
    {
    }

    public static RhSubsidioExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        RhSubsidioExcelBean rhSubsidioExcelBean = 
            (RhSubsidioExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "rhSubsidioExcelBean");
        
        return rhSubsidioExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarSubsidioTabela()
    {
        System.out.println("Esta carregando subsidio");
        
        Date dataSubsidioTabela = this.rhUpdatesFacade.dataSubsidio();

        Date dataSubsidioXLSFile = util.DataUtils.dataModificacaoFicheiro(util.rh.Defs.FILE_SUBSIDIO_RH);

        if (this.rhSubsidioFacade.findAll().isEmpty() || dataSubsidioTabela == null);
        else if (!rhSubsidioFacade.findAll().isEmpty() && (dataSubsidioXLSFile != null && dataSubsidioXLSFile.compareTo(dataSubsidioTabela) <= 0))
        {
            return;
        }

        if (lerSubsidioTabela())
        {
            this.rhUpdatesFacade.escreverDataActualizacaoSubsidioTabela();
        }
    }

    public boolean lerSubsidioTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.rh.Defs.FILE_SUBSIDIO_RH);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("subsidio");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            RhSubsidio reg = null;

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
                
                escreverSubsidioTabela(reg);
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

    public void escreverSubsidioTabela(RhSubsidio reg)
    {
        if (rhSubsidioFacade.find(reg.getPkIdSubsidio()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public RhSubsidio lerCampos(HSSFRow row)
    {
        int pk_id_subsidio;
        String descricao;
        double valor;
        boolean obrigatorio;
        
        final int PK_ID_SUBSIDIO = 0;
        final int DESCRICAO = 1;
        final int VALOR = 2;
        final int OBRIGATORIO = 3;
        
        RhSubsidio reg = new RhSubsidio();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_SUBSIDIO:
                    pk_id_subsidio = (int)cell.getNumericCellValue();
                    reg.setPkIdSubsidio(pk_id_subsidio);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricaoSubsidio(descricao);
                break;
                    
                case VALOR:
                    valor = cell.getNumericCellValue();
                    reg.setValor(valor);
                break;
                    
                case OBRIGATORIO:
                    obrigatorio = Boolean.parseBoolean(cell.getStringCellValue().trim());
                    reg.setObrigatorio(obrigatorio);
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(RhSubsidio reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhSubsidioFacade.create(reg);
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

    public boolean editRegister(RhSubsidio reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhSubsidioFacade.edit(reg);
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
