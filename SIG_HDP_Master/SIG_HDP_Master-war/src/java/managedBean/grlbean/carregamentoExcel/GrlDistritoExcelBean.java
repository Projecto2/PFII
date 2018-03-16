/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlDistrito;
import entidade.GrlMunicipio;
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
import sessao.GrlDistritoFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlDistritoExcelBean
{

    @EJB
    private GrlDistritoFacade grlDistritoFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlTipoDeDistritoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlDistritoExcelBean()
    {
    }

    public static GrlDistritoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlDistritoExcelBean grlDistritoExcelBean = 
            (GrlDistritoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlDistritoExcelBean");
        
        return grlDistritoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarDistritoTabela()
    {
//        Date dataDistritoTabela = this.grlUpdatesFacade.dataDistrito();
//
//        Date dataDistritoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_DISTRITO_GRL);
//
//        if (this.grlDistritoFacade.isDistritoTabelaEmpty() || dataDistritoTabela == null);
//        else if (!grlDistritoFacade.isDistritoTabelaEmpty() && (dataDistritoXLSFile != null && dataDistritoXLSFile.compareTo(dataDistritoTabela) <= 0))
//        {
//            return;
//        }

        if (lerDistritoTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoDistritoTabela();
        }
    }

    public boolean lerDistritoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_DISTRITO_GRL);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("distritos");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            

            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataDistritoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_DISTRITO_GRL);
            
            
            Date dataDistritoTabela = this.grlUpdatesFacade.dataDistrito();
            
            
            if (dataDistritoTabela == null);
            else if (dataDistritoXLSFile.compareTo(dataDistritoTabela) <= 0)
            {
                return false;
            }
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlDistrito reg = null;

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
                
                escreverDistritoTabela(reg);
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

    public void escreverDistritoTabela(GrlDistrito reg)
    {
        if (grlDistritoFacade.existeRegisto(reg.getPkIdDistrito()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public GrlDistrito lerCampos(HSSFRow row)
    {
        int pk_id_distrito;
        String descricao;
        int fk_id_municipio;
        
        final int PK_ID_DISTRITO = 0;
        final int DESCRICAO = 1;
        final int FK_ID_MUNICIPIO = 2;
        
        GrlDistrito reg = new GrlDistrito();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_DISTRITO:
                    pk_id_distrito = (int)cell.getNumericCellValue();
                    reg.setPkIdDistrito(pk_id_distrito);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                break;
                    
                case FK_ID_MUNICIPIO:
                    if(cell == null)
                    {
                        reg.setFkIdMunicipio(null);
                    }
                    else
                    {
                        fk_id_municipio = (int)cell.getNumericCellValue();
                        reg.setFkIdMunicipio(new GrlMunicipio(fk_id_municipio));
                    }
                break;
                    

            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlDistrito reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlDistritoFacade.create(reg);
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

    public boolean editRegister(GrlDistrito reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlDistritoFacade.edit(reg);
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
