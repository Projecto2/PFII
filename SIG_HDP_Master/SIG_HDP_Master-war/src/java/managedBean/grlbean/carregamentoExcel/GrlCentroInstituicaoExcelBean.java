/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlContacto;
import entidade.GrlEndereco;
import entidade.GrlInstituicao;
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
import sessao.GrlUpdatesFacade;
import sessao.GrlInstituicaoFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlCentroInstituicaoExcelBean implements Serializable
{

    @EJB
    private GrlInstituicaoFacade grlInstituicaoFacade;
//    @EJB
//    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlInstituicaoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlCentroInstituicaoExcelBean()
    {
    }

    public static GrlCentroInstituicaoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlCentroInstituicaoExcelBean grlInstituicaoExcelBean = 
            (GrlCentroInstituicaoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlCentroInstituicaoExcelBean");
        
        return grlInstituicaoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarInstituicaoTabela()
    {
        
//        Date dataInstituicaoTabela = this.grlUpdatesFacade.dataInstituicao();

        Date dataInstituicaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_CENTRO_INSTITUICAO);

//        if (this.grlInstituicaoFacade.findAll().isEmpty() || dataInstituicaoTabela == null);
//        else if (!grlInstituicaoFacade.findAll().isEmpty() && (dataInstituicaoXLSFile != null && dataInstituicaoXLSFile.compareTo(dataInstituicaoTabela) <= 0))
//        {
//            return;
//        }

        if (lerInstituicaoTabela())
        {
            System.out.println("Esta carregando centro_instituicao");
//            this.grlUpdatesFacade.escreverDataActualizacaoInstituicaoTabela();
        }
    }

    public boolean lerInstituicaoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_CENTRO_INSTITUICAO);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("instituicao");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlInstituicao reg = null;

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
                
                escreverInstituicaoTabela(reg);
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

    public void escreverInstituicaoTabela(GrlInstituicao reg)
    {
        if (reg.getPkIdInstituicao() == null || grlInstituicaoFacade.find(reg.getPkIdInstituicao()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public GrlInstituicao lerCampos(HSSFRow row)
    {
//        int pk_id_instituicao;
        String codigo_instituicao;
        String descricao;
        long fk_id_telefone;
        long fk_id_endereco;
        
//        final int PK_ID_INSTITUICAO = 0;
        final int CODIGO_INSTITUICAO = 0;
        final int DESCRICAO = 1;
        final int FK_ID_TELEFONE = 2;
        final int FK_ID_ENDERECO = 3;
        
        GrlInstituicao reg = new GrlInstituicao();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {                    
                case CODIGO_INSTITUICAO:
                    codigo_instituicao = cell.getStringCellValue();
                    reg.setCodigoInstituicao(codigo_instituicao);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue();
                    reg.setDescricao(descricao);
                break;
                    
                case FK_ID_TELEFONE:
                    fk_id_telefone = (int)cell.getNumericCellValue();
                    reg.setFkIdContacto(new GrlContacto(fk_id_telefone));
                break;
                    
                case FK_ID_ENDERECO:
                    fk_id_endereco = (int)cell.getNumericCellValue();
                    reg.setFkIdEndereco(new GrlEndereco(fk_id_endereco));
                break;

            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlInstituicao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlInstituicaoFacade.create(reg);
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

    public boolean editRegister(GrlInstituicao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlInstituicaoFacade.edit(reg);
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
