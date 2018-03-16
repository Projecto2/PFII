/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.rhbean.carregamentoExcel;

import entidade.RhTipoFuncionario;
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
import sessao.RhTipoFuncionarioFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhTipoFuncionarioExcelBean implements Serializable
{

    @EJB
    private RhTipoFuncionarioFacade rhTipoFuncionarioFacade;
    @EJB
    private RhUpdatesFacade rhUpdatesFacade;
    
    /**
     * Creates a new instance of RhTipoServicoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public RhTipoFuncionarioExcelBean()
    {
    }

    public static RhTipoFuncionarioExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        RhTipoFuncionarioExcelBean rhTipoFuncionarioExcelBean = 
            (RhTipoFuncionarioExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "rhTipoFuncionarioExcelBean");
        
        return rhTipoFuncionarioExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarTipoFuncionarioTabela()
    {
        System.out.println("Esta carregando tipo_funcionario");
        
        Date dataTipoFuncionarioTabela = this.rhUpdatesFacade.dataTipoFuncionario();

        Date dataTipoFuncionarioXLSFile = util.DataUtils.dataModificacaoFicheiro(util.rh.Defs.FILE_TIPO_FUNCIONARIO_RH);

        if (this.rhTipoFuncionarioFacade.findAll().isEmpty() || dataTipoFuncionarioTabela == null);
        else if (!rhTipoFuncionarioFacade.findAll().isEmpty() && (dataTipoFuncionarioXLSFile != null && dataTipoFuncionarioXLSFile.compareTo(dataTipoFuncionarioTabela) <= 0))
        {
            return;
        }

        if (lerTipoFuncionarioTabela())
        {
            this.rhUpdatesFacade.escreverDataActualizacaoTipoFuncionarioTabela();
        }
    }

    public boolean lerTipoFuncionarioTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.rh.Defs.FILE_TIPO_FUNCIONARIO_RH);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipo_funcionario");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            RhTipoFuncionario reg = null;

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
                
                escreverTipoFuncionarioTabela(reg);
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

    public void escreverTipoFuncionarioTabela(RhTipoFuncionario reg)
    {
        if (rhTipoFuncionarioFacade.find(reg.getPkIdTipoFuncionario()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public RhTipoFuncionario lerCampos(HSSFRow row)
    {
        int pk_id_tipo_funcionario;
        String descricao;
        
        final int PK_ID_TIPO_FUNCIONARIO = 0;
        final int DESCRICAO = 1;
        
        RhTipoFuncionario reg = new RhTipoFuncionario();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_TIPO_FUNCIONARIO:
                    pk_id_tipo_funcionario = (int)cell.getNumericCellValue();
                    reg.setPkIdTipoFuncionario(pk_id_tipo_funcionario);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(RhTipoFuncionario reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhTipoFuncionarioFacade.create(reg);
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

    public boolean editRegister(RhTipoFuncionario reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhTipoFuncionarioFacade.edit(reg);
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
