/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.carregamentoExcel;

import entidade.AdmsCategoriaServico;
import entidade.AdmsServico;
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
import sessao.AdmsCategoriaServicoFacade;
import sessao.AdmsUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsCategoriaServicoExcelBean implements Serializable
{
    @EJB
    private AdmsCategoriaServicoFacade admsCategoriaServicoFacade;
    @EJB
    private AdmsUpdatesFacade admsUpdatesFacade;
    
    /**
     * Creates a new instance of AdmsTipoDeServicoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;
    
    private Date dataCategoriaServicoXLSFile = new Date();

    public AdmsCategoriaServicoExcelBean()
    {
    }

    public static AdmsCategoriaServicoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsCategoriaServicoExcelBean admsCategoriaServicoExcelBean = 
            (AdmsCategoriaServicoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsCategoriaServicoExcelBean");
        
        return admsCategoriaServicoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarCategoriaServicoTabela()
    {
        if (lerCategoriaServicoTabela())
        {
            this.admsUpdatesFacade.escreverDataActualizacaoCategoriaServicoTabela(dataCategoriaServicoXLSFile);
        }
    }

    public boolean lerCategoriaServicoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.adms.Defs.FILE_CATEGORIA_SERVICO_ADMS);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("categoriasServico");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            /*Date */dataCategoriaServicoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.adms.Defs.FILE_CATEGORIA_SERVICO_ADMS);
            
            
            Date dataCategoriaServicoTabela = this.admsUpdatesFacade.dataCategoriaServico();
            
            
            if (dataCategoriaServicoTabela == null);
            else if (dataCategoriaServicoXLSFile.compareTo(dataCategoriaServicoTabela) <= 0)
            {
                return false;
            }


            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            AdmsCategoriaServico reg = null;

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
                
                escreverServicoTabela(reg);
                nreg++;
                //System.err.println("12: AmbCidCapitulosBean.carregarCapitulosTabela()");
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        System.out.println("chegou ao fim");
        return nreg != 0;
    }

    public void escreverServicoTabela(AdmsCategoriaServico reg)
    {
        if (admsCategoriaServicoFacade.existeRegisto(reg.getPkIdCategoriaServico()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public AdmsCategoriaServico lerCampos(HSSFRow row)
    {
        int pk_id_categoria_servico;
        String descricao_categoria_servico;
        int fk_id_servico;
        
        final int PK_ID_CATEGORIA_SERVICO = 0;
        final int FK_SERVICO = 1;
        final int ESTADO_CATEGORIA_SERVICO = 2;
        final int DESCRICAO_CATEGORIA_SERVICO = 3;
        final int TIPO_UNICO = 4;
        
        AdmsCategoriaServico reg = new AdmsCategoriaServico();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_CATEGORIA_SERVICO:
                    pk_id_categoria_servico = (int)cell.getNumericCellValue();
                    reg.setPkIdCategoriaServico(pk_id_categoria_servico);
                break;
                    
                case FK_SERVICO:
                    fk_id_servico = (int)cell.getNumericCellValue();
                    reg.setFkIdServico(new AdmsServico(fk_id_servico)); 
                break;
                    
                case ESTADO_CATEGORIA_SERVICO:
                    String valor = cell.getStringCellValue().trim();
                    if(valor.equalsIgnoreCase("true"))
                       reg.setEstadoCategoriaServico(true); 
                    else
                        reg.setEstadoCategoriaServico(false); 
                break;
                    
                case DESCRICAO_CATEGORIA_SERVICO:
                    descricao_categoria_servico = cell.getStringCellValue().trim();
                    reg.setDescricaoCategoriaServico(descricao_categoria_servico);
                    
                break;
                    
                case TIPO_UNICO:
                    String valorUnico = cell.getStringCellValue().trim();
                    if(valorUnico.equalsIgnoreCase("true"))
                       reg.setTipoUnico(true); 
                    else
                        reg.setTipoUnico(false); 
                break;
                    
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(AdmsCategoriaServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsCategoriaServicoFacade.create(reg);
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

    public boolean editRegister(AdmsCategoriaServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsCategoriaServicoFacade.edit(reg);
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
