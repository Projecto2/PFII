/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.rhbean.carregamentoExcel;

import entidade.RhCategoriaProfissional;
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
import sessao.RhCategoriaProfissionalFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhCategoriaProfissionalExcelBean implements Serializable
{

    @EJB
    private RhCategoriaProfissionalFacade rhCategoriaProfissionalFacade;
    @EJB
    private RhUpdatesFacade rhUpdatesFacade;
    
    /**
     * Creates a new instance of RhTipoServicoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public RhCategoriaProfissionalExcelBean()
    {
    }

    public static RhCategoriaProfissionalExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        RhCategoriaProfissionalExcelBean rhCategoriaProfissionalExcelBean = 
            (RhCategoriaProfissionalExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "rhCategoriaProfissionalExcelBean");
        
        return rhCategoriaProfissionalExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarCategoriaProfissionalTabela()
    {
        System.out.println("Esta carregando categoria_profissional");
        
        Date dataCategoriaProfissionalTabela = this.rhUpdatesFacade.dataCategoriaProfissional();

        Date dataCategoriaProfissionalXLSFile = util.DataUtils.dataModificacaoFicheiro(util.rh.Defs.FILE_CATEGORIA_PROFISSIONAL_RH);

        if (this.rhCategoriaProfissionalFacade.findAll().isEmpty() || dataCategoriaProfissionalTabela == null);
        else if (!rhCategoriaProfissionalFacade.findAll().isEmpty() && (dataCategoriaProfissionalXLSFile != null && dataCategoriaProfissionalXLSFile.compareTo(dataCategoriaProfissionalTabela) <= 0))
        {
            return;
        }

        if (lerCategoriaProfissionalTabela())
        {
            this.rhUpdatesFacade.escreverDataActualizacaoCategoriaProfissionalTabela();
        }
    }

    public boolean lerCategoriaProfissionalTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.rh.Defs.FILE_CATEGORIA_PROFISSIONAL_RH);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("categoria_profissional");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            RhCategoriaProfissional reg = null;

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
                
                escreverCategoriaProfissionalTabela(reg);
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

    public void escreverCategoriaProfissionalTabela(RhCategoriaProfissional reg)
    {
        if (rhCategoriaProfissionalFacade.find(reg.getPkIdCategoriaProfissional()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public RhCategoriaProfissional lerCampos(HSSFRow row)
    {
        int pk_id_categoria_profissional;
        String descricao;
        int indice;
        double salario_base;
        Double despesas_de_representacao;
        Double remuneracao_total;
        
        final int PK_ID_CATEGORIA_PROFISSAO = 0;
        final int DESCRICAO = 1;
        final int INDICE = 2;
        final int SALARIO_BASE = 3;
        final int DESPESAS_DE_REPRESENTACAO = 4;
        final int REMUNERACAO_TOTAL = 5;
        
        RhCategoriaProfissional reg = new RhCategoriaProfissional();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_CATEGORIA_PROFISSAO:
                    pk_id_categoria_profissional = (int)cell.getNumericCellValue();
                    reg.setPkIdCategoriaProfissional(pk_id_categoria_profissional);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
                    
                case INDICE:
                    indice = (int)cell.getNumericCellValue();
                    reg.setIndice(indice);
                break;
                    
                case SALARIO_BASE:
                    salario_base = cell.getNumericCellValue();
                    reg.setSalarioBase(salario_base);
                break;
                    
                case DESPESAS_DE_REPRESENTACAO:
                    try
                    {
                        despesas_de_representacao = cell.getNumericCellValue();
                        reg.setDespesasDeRepresentacao(despesas_de_representacao);
                    }
                    catch(NullPointerException ex){}
                break;
                    
                case REMUNERACAO_TOTAL:
                    try
                    {
                        remuneracao_total = cell.getNumericCellValue();
                        reg.setRemuneracaoTotal(remuneracao_total);
                    }
                    catch(NullPointerException ex){}
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(RhCategoriaProfissional reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhCategoriaProfissionalFacade.create(reg);
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

    public boolean editRegister(RhCategoriaProfissional reg)
    {
        try
        {
            this.userTransaction.begin();
            this.rhCategoriaProfissionalFacade.edit(reg);
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
