/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.carregamentoExcel;

import entidade.AdmsTipoServico;
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
import sessao.AdmsTipoServicoFacade;
import sessao.AdmsUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsTipoDeServicoExcelBean
{
    @EJB
    private AdmsTipoServicoFacade admsTipoServicoFacade;
    @EJB
    private AdmsUpdatesFacade admsUpdatesFacade;
    
    /**
     * Creates a new instance of AdmsTipoDeServicoExcelBean
     */
    
    private Date dataTiposDeServicoXLSFile = new Date();
    @Resource
    private UserTransaction userTransaction;

    public AdmsTipoDeServicoExcelBean()
    {
    }

    public static AdmsTipoDeServicoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsTipoDeServicoExcelBean admsTipoDeServicoExcelBean = 
            (AdmsTipoDeServicoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsTipoDeServicoExcelBean");
        
        return admsTipoDeServicoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarTipoDeServicoTabela()
    {
//        Date dataTipoServicoTabela = this.admsUpdatesFacade.dataTipoDeServico();
//
//        Date dataTipoServicoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.adms.Defs.FILE_TIPO_SERVICO_ADMS);
//
//        if (this.admsTipoServicoFacade.isTipoDeServicoTabelaEmpty() || dataTipoServicoTabela == null);
//        else if (!admsTipoServicoFacade.isTipoDeServicoTabelaEmpty() && (dataTipoServicoXLSFile != null && dataTipoServicoXLSFile.compareTo(dataTipoServicoTabela) <= 0))
//        {
//            return;
//        }

        if (lerTiposDeServicoTabela())
        {
            this.admsUpdatesFacade.escreverDataActualizacaoTipoServicoTabela(dataTiposDeServicoXLSFile);
        }
    }

    public boolean lerTiposDeServicoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.adms.Defs.FILE_TIPO_SERVICO_ADMS);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipos_de_servico");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            /*Date*/ dataTiposDeServicoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.adms.Defs.FILE_TIPO_SERVICO_ADMS);
            
            
            Date dataTiposDeServicoTabela = this.admsUpdatesFacade.dataTipoDeServico();
            
            
            if (dataTiposDeServicoTabela == null);
            else if (dataTiposDeServicoXLSFile.compareTo(dataTiposDeServicoTabela) <= 0)
            {
                return false;
            }
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            AdmsTipoServico reg = null;

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
                System.out.println("aqui");
                //caso nao seja a primeira linha, converto os dados dessa linha num registo que 
                //pode ser posto na base de dados
                reg = lerCampos(row);
                
                escreverTipoServicoTabela(reg);
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

    public void escreverTipoServicoTabela(AdmsTipoServico reg)
    {
        if(reg.getPkIdTipoServico() == null) return;
        if (admsTipoServicoFacade.existeRegisto(reg.getPkIdTipoServico()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public AdmsTipoServico lerCampos(HSSFRow row)
    {
        int pk_id_tipo_servico;
        String descricao_tipo_servico;
        
        final int PK_ID_TIPO_SERVICO = 0;
        final int DESCRICAO_TIPO_SERVICO = 1;
        
        AdmsTipoServico reg = new AdmsTipoServico();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_TIPO_SERVICO:
                    pk_id_tipo_servico = (int)cell.getNumericCellValue();
                    reg.setPkIdTipoServico(pk_id_tipo_servico);
                break;
                    
                case DESCRICAO_TIPO_SERVICO:
                    descricao_tipo_servico = cell.getStringCellValue().trim();
                    reg.setDescricaoTipoServico(descricao_tipo_servico);
                break;
            }
        }
        return reg;
    }
    
    
    public boolean createRegister(AdmsTipoServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsTipoServicoFacade.create(reg);
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

    public boolean editRegister(AdmsTipoServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsTipoServicoFacade.edit(reg);
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
