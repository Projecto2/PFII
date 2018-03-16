/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlAreaInterna;
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
import sessao.AdmsUpdatesFacade;
import sessao.GrlAreaInternaFacade;
import sessao.GrlUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlAreaInternaExcelBean
{
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;

    @EJB
    private GrlAreaInternaFacade grlAreaInternaFacade;
    @EJB
    private AdmsUpdatesFacade admsUpdatesFacade;
    
    
    /**
     * Creates a new instance of GrlTipoDeServicoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlAreaInternaExcelBean()
    {
    }

    public static GrlAreaInternaExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlAreaInternaExcelBean grlAreaInternaExcelBean = 
            (GrlAreaInternaExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlAreaInternaExcelBean");
        
        return grlAreaInternaExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarAreaInternaTabela()
    {
//        Date dataAreaInternaTabela = this.grlUpdatesFacade.dataAreaInterna();
//
//        Date dataAreaInternaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_AREA_INTERNA_GRL);
//
//        if (this.grlAreaInternaFacade.isAreaInternaTabelaEmpty() || dataAreaInternaTabela == null);
//        else if (!grlAreaInternaFacade.isAreaInternaTabelaEmpty() && (dataAreaInternaXLSFile != null && dataAreaInternaXLSFile.compareTo(dataAreaInternaTabela) <= 0))
//        {
//            return;
//        }

        if (lerGruposServicoTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoAreaInternaTabela();
        }
    }

    public boolean lerGruposServicoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_AREA_INTERNA_GRL);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("areas_internas");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataAreaInternaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_AREA_INTERNA_GRL);
            
            
            Date dataAreaInternaTabela = this.grlUpdatesFacade.dataAreaInterna();
            
            
            if (dataAreaInternaTabela == null);
            else if (dataAreaInternaXLSFile.compareTo(dataAreaInternaTabela) <= 0)
            {
                return false;
            }
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlAreaInterna reg = null;

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
                
                escreverAreaInternaTabela(reg);
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

    public void escreverAreaInternaTabela(GrlAreaInterna reg)
    {
        if (grlAreaInternaFacade.existeRegisto(reg.getPkIdAreaInterna().intValue()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public GrlAreaInterna lerCampos(HSSFRow row)
    {
        int pk_id_area_interna;
        String descricao_area_interna;
        String codigo_area_interna;
        String observacoes;
        
        final int PK_ID_AREA_INTERNA = 0;
        final int DESCRICAO_AREA_INTERNA = 1;
        final int CODIGO_AREA_INTERNA = 2;
        final int OBSERVACOES = 3;
        
        GrlAreaInterna reg = new GrlAreaInterna();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_AREA_INTERNA:
                    pk_id_area_interna = (int)cell.getNumericCellValue();
                    reg.setPkIdAreaInterna(pk_id_area_interna);
                break;
                    
                case DESCRICAO_AREA_INTERNA:
                    descricao_area_interna = cell.getStringCellValue().trim();
                    reg.setDescricaoAreaInterna(descricao_area_interna);
                break;
                    
                case CODIGO_AREA_INTERNA:
                    if(cell == null)
                    {
                        reg.setCodigoAreaInterna("");
                    }
                    else
                    {
                        codigo_area_interna = cell.getStringCellValue().trim();
                        reg.setCodigoAreaInterna(codigo_area_interna); 
                    }
                break;
                    
                case OBSERVACOES:
                    if(cell == null)
                    {
                        reg.setObservacoes("");
                    }
                    else
                    {
                        observacoes = cell.getStringCellValue().trim();
                        reg.setObservacoes(observacoes); 
                    }
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlAreaInterna reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlAreaInternaFacade.create(reg);
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

    public boolean editRegister(GrlAreaInterna reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlAreaInternaFacade.edit(reg);
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
