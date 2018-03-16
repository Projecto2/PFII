/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.carregamentoExcel;

import entidade.AdmsGrupoServico;
import entidade.AdmsServico;
import entidade.AdmsTipoServico;
import entidade.GrlAreaInterna;
import entidade.GrlEspecialidade;
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
import sessao.AdmsServicoFacade;
import sessao.AdmsUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsServicoExcelBean
{

    @EJB
    private AdmsServicoFacade admsServicoFacade;
    @EJB
    private AdmsUpdatesFacade admsUpdatesFacade;
    
    /**
     * Creates a new instance of AdmsTipoDeServicoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;
    
    private Date dataServicoXLSFile = new Date();

    public AdmsServicoExcelBean()
    {
    }

    public static AdmsServicoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsServicoExcelBean admsServicoExcelBean = 
            (AdmsServicoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsServicoExcelBean");
        
        return admsServicoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarServicoTabela()
    {
//        Date dataServicoTabela = this.admsUpdatesFacade.dataServico();
//
//        Date dataServicoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.adms.Defs.FILE_SERVICO_ADMS);
//
//        if (this.admsServicoFacade.isServicoTabelaEmpty() || dataServicoTabela == null);
//        else if (!admsServicoFacade.isServicoTabelaEmpty() && (dataServicoXLSFile != null && dataServicoXLSFile.compareTo(dataServicoTabela) <= 0))
//        {
//            return;
//        }

        if (lerServicoTabela())
        {
            this.admsUpdatesFacade.escreverDataActualizacaoServicoTabela(dataServicoXLSFile);
        }
    }

    public boolean lerServicoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.adms.Defs.FILE_SERVICO_ADMS);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("servicos");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
        
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            /*Date */dataServicoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.adms.Defs.FILE_SERVICO_ADMS);
            
            
            Date dataServicoTabela = this.admsUpdatesFacade.dataServico();
            
            
            if (dataServicoTabela == null);
            else if (dataServicoXLSFile.compareTo(dataServicoTabela) <= 0)
            {
                return false;
            }
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            AdmsServico reg = null;

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
        return nreg != 0;
    }

    public void escreverServicoTabela(AdmsServico reg)
    {
        if (admsServicoFacade.existeRegisto(reg.getPkIdServico()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    
    
    public AdmsServico lerCampos(HSSFRow row)
    {
        int pk_id_servico;
        String nome_servico;
        String cod_servico;
        boolean pode_ter_medico;
        int fk_id_tipo_servico;
        int fk_id_grupo_servico;
        Integer fk_id_area;
        int fk_id_especialidade;
        
        final int PK_ID_SERVICO = 0;
        final int NOME_SERVICO = 1;
        final int COD_SERVICO = 2;
        final int PODE_TER_MEDICO = 3;
        final int FK_ID_TIPO_SERVICO = 4;
        final int FK_ID_GRUPO_SERVICO = 5;
        final int FK_ID_AREA = 6;
        final int FK_ID_ESPECIALIDADE = 7;
        
        AdmsServico reg = new AdmsServico();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_SERVICO:
                    pk_id_servico = (int)cell.getNumericCellValue();
                    reg.setPkIdServico(pk_id_servico);
                break;
                    
                case NOME_SERVICO:
                    nome_servico = cell.getStringCellValue().trim();
                    reg.setNomeServico(nome_servico);
                break;
                    
                case COD_SERVICO:
                    if(cell == null)
                    {
                        reg.setCodServico("");
                    }
                    else
                    {
                        cod_servico = cell.getStringCellValue().trim();
                        reg.setCodServico(cod_servico); 
                    }
                break;
                    
                case PODE_TER_MEDICO:
                    if(cell == null)
                    {
//                        reg.setPodeTerMedico(null);
                    }
                    else
                    {
                        String valor = cell.getStringCellValue().trim();
                        System.out.println(valor);
                        if(valor.equalsIgnoreCase("true"))
                           reg.setPodeTerMedico(true); 
                        else
                            reg.setPodeTerMedico(false); 
//                        pode_ter_medico = cell.getBooleanCellValue();
                        
                    }
                break;
                    
                case FK_ID_TIPO_SERVICO:
                    if(cell == null)
                    {
                        reg.setFkIdTipoServico(null);
                    }
                    else
                    {
                        fk_id_tipo_servico = (int)cell.getNumericCellValue();
                        reg.setFkIdTipoServico(new AdmsTipoServico(fk_id_tipo_servico));
                    }
                break;
                    
                case FK_ID_GRUPO_SERVICO:
                    if(cell == null)
                    {
                        reg.setFkIdGrupoServico(null);
                    }
                    else
                    {
                        fk_id_grupo_servico = (int)cell.getNumericCellValue();
                        reg.setFkIdGrupoServico(new AdmsGrupoServico(fk_id_grupo_servico));
                    }
                break;
                    
                case FK_ID_AREA:
                    if(cell == null)
                    {
                        reg.setFkIdArea(null);
                    }
                    else
                    {
                        fk_id_area = (int)cell.getNumericCellValue();
                        reg.setFkIdArea(new GrlAreaInterna(fk_id_area));
                    }
                break;
                    
                case FK_ID_ESPECIALIDADE:
                    if(cell == null)
                    {
                        reg.setFkIdEspecialidade(null);
                    }
                    else
                    {
                        fk_id_especialidade = (int)cell.getNumericCellValue();
                        reg.setFkIdEspecialidade(new GrlEspecialidade(fk_id_especialidade));
                    }
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(AdmsServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsServicoFacade.create(reg);
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

    public boolean editRegister(AdmsServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsServicoFacade.edit(reg);
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
