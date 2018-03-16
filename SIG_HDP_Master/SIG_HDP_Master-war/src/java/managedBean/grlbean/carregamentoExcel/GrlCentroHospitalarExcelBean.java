/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean.carregamentoExcel;

import entidade.GrlCentroHospitalar;
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
import sessao.GrlCentroHospitalarFacade;
import util.FileManagerGeral;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlCentroHospitalarExcelBean implements Serializable
{

    @EJB
    private GrlCentroHospitalarFacade grlCentroHospitalarFacade;
    @EJB
    private GrlUpdatesFacade grlUpdatesFacade;
    
    /**
     * Creates a new instance of GrlCentroHospitalarExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;

    public GrlCentroHospitalarExcelBean()
    {
    }

    public static GrlCentroHospitalarExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlCentroHospitalarExcelBean grlCentroHospitalarExcelBean = 
            (GrlCentroHospitalarExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "grlCentroHospitalarExcelBean");
        
        return grlCentroHospitalarExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarCentroHospitalarTabela()
    {
//        System.out.println("ola");
        System.out.println("Esta carregando centro_hospitalar");
        
//        Date dataCentroHospitalarTabela = this.grlUpdatesFacade.dataCentroHospitalar();
//
//        Date dataCentroHospitalarXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_CENTRO_HOSPITALAR);
//
//        if (this.grlCentroHospitalarFacade.findAll().isEmpty() || dataCentroHospitalarTabela == null);
//        else if (!grlCentroHospitalarFacade.findAll().isEmpty() && (dataCentroHospitalarXLSFile != null && dataCentroHospitalarXLSFile.compareTo(dataCentroHospitalarTabela) <= 0))
//        {
//            return;
//        }

        if (lerCentroHospitalarTabela())
        {
            this.grlUpdatesFacade.escreverDataActualizacaoCentroHospitalarTabela();
        }
    }

    public boolean lerCentroHospitalarTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_CENTRO_HOSPITALAR);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("centro_hospitalar");
            

            HSSFRow row;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataCentroXLSFile = FileManagerGeral.lerVersaoTabela(row, util.grl.Defs.FILE_CENTRO_HOSPITALAR);
            
            
            Date dataCentroTabela = this.grlUpdatesFacade.dataCentroHospitalar();
            
            
            if (dataCentroTabela == null);
            else if (dataCentroXLSFile.compareTo(dataCentroTabela) <= 0)
            {
                return false;
            }
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlCentroHospitalar reg = null;

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
                
                escreverCentroHospitalarTabela(reg);
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

    public void escreverCentroHospitalarTabela(GrlCentroHospitalar reg)
    {
        if (grlCentroHospitalarFacade.find(reg.getPkIdCentro()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }
    
    
    public GrlCentroHospitalar lerCampos(HSSFRow row)
    {
        int pk_id_centro;
        String codigo_centro;
        int fk_id_instituicao;
        
        final int PK_ID_CENTRO = 0;
        final int CODIGO_CENTRO = 1;
        final int FK_ID_INSTITUICAO = 2;
        
        GrlCentroHospitalar reg = new GrlCentroHospitalar();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_CENTRO:
                    pk_id_centro = (int)cell.getNumericCellValue();
                    reg.setPkIdCentro(pk_id_centro);
                break;
                    
                case CODIGO_CENTRO:
                    codigo_centro = cell.getStringCellValue();
                    reg.setCodigoCentro(codigo_centro);
                break;
                    
                case FK_ID_INSTITUICAO:
                    fk_id_instituicao = (int)cell.getNumericCellValue();
                    reg.setFkIdInstituicao(new GrlInstituicao(fk_id_instituicao));
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(GrlCentroHospitalar reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlCentroHospitalarFacade.create(reg);
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

    public boolean editRegister(GrlCentroHospitalar reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlCentroHospitalarFacade.edit(reg);
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
