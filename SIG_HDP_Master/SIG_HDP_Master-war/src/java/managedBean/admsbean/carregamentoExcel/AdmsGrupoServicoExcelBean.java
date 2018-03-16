/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.carregamentoExcel;

import entidade.AdmsGrupoServico;
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
import sessao.AdmsGrupoServicoFacade;
import sessao.AdmsUpdatesFacade;
import util.FileManagerGeral;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsGrupoServicoExcelBean
{

    @EJB
    private AdmsGrupoServicoFacade admsGrupoServicoFacade;
    @EJB
    private AdmsUpdatesFacade admsUpdatesFacade;
    
    /**
     * Creates a new instance of AdmsTipoDeServicoExcelBean
     */
    
    @Resource
    private UserTransaction userTransaction;
    
    private Date dataGruposServicoXLSFile = new Date();

    public AdmsGrupoServicoExcelBean()
    {
    }

    public static AdmsGrupoServicoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsGrupoServicoExcelBean admsGrupoServicoExcelBean = 
            (AdmsGrupoServicoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsGrupoServicoExcelBean");
        
        return admsGrupoServicoExcelBean;
    }
    
    
    @SuppressWarnings("empty-statement")
    public void carregarGrupoServicoTabela()
    {
//        Date dataGrupoServicoTabela = this.admsUpdatesFacade.dataGrupoServico();
//
//        Date dataGrupoServicoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.adms.Defs.FILE_GRUPO_SERVICO_ADMS);
//
//        if (this.admsGrupoServicoFacade.isGrupoServicoTabelaEmpty() || dataGrupoServicoTabela == null);
//        else if (!admsGrupoServicoFacade.isGrupoServicoTabelaEmpty() && (dataGrupoServicoXLSFile != null && dataGrupoServicoXLSFile.compareTo(dataGrupoServicoTabela) <= 0))
//        {
//            System.out.println("retornou sem nada");
//            return;
//        }

        if (lerGruposServicoTabela())
        {
            System.out.println("leu");
            this.admsUpdatesFacade.escreverDataActualizacaoGrupoServicoTabela(dataGruposServicoXLSFile);
        }
    }

    public boolean lerGruposServicoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.adms.Defs.FILE_GRUPO_SERVICO_ADMS);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("grupos_de_servico");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
            
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            /*Date*/ dataGruposServicoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.adms.Defs.FILE_GRUPO_SERVICO_ADMS);
            
            
            Date dataGruposServicoTabela = this.admsUpdatesFacade.dataGrupoServico();
            
            
            if (dataGruposServicoTabela == null);
            else if (dataGruposServicoXLSFile.compareTo(dataGruposServicoTabela) <= 0)
            {
                return false;
            }
            

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            AdmsGrupoServico reg = null;

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
                
                escreverGrupoServicoTabela(reg);
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

    public void escreverGrupoServicoTabela(AdmsGrupoServico reg)
    {
        if (admsGrupoServicoFacade.existeRegisto(reg.getPkIdGrupoServico()) == false)
        {
            System.out.println("criou");
            this.createRegister(reg);
        }
        else
        {
            System.out.println("editou");
            this.editRegister(reg);
        }
    }

    
    
    public AdmsGrupoServico lerCampos(HSSFRow row)
    {
        int pk_id_grupo_servico;
        String descricao_grupo_servico;
        String observacao_grupo_servico;
        int fk_id_grupo_servico_pai;
        
        final int PK_ID_GRUPO_SERVICO = 0;
        final int DESCRICAO_GRUPO_SERVICO = 1;
        final int OBSERVACAO_GRUPO_SERVICO = 2;
        final int FK_ID_GRUPO_SERVICO_PAI = 3;
        
        AdmsGrupoServico reg = new AdmsGrupoServico();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_GRUPO_SERVICO:
                    pk_id_grupo_servico = (int)cell.getNumericCellValue();
                    reg.setPkIdGrupoServico(pk_id_grupo_servico);
                break;
                    
                case DESCRICAO_GRUPO_SERVICO:
                    descricao_grupo_servico = cell.getStringCellValue().trim();
                    reg.setDescricaoGrupoServico(descricao_grupo_servico);
                break;
                    
                case OBSERVACAO_GRUPO_SERVICO:
                    if(cell == null)
                    {
                        reg.setObservacaoGrupoServico("");
                    }
                    else
                    {
                        observacao_grupo_servico = cell.getStringCellValue().trim();
                        reg.setObservacaoGrupoServico(observacao_grupo_servico); 
                    }
                break;
                    
                case FK_ID_GRUPO_SERVICO_PAI:
                    if(cell == null)
                    {
                        reg.setFkIdGrupoServicoPai(null);
                    }
                    else
                    {
                        fk_id_grupo_servico_pai = (int)cell.getNumericCellValue();
                        reg.setFkIdGrupoServicoPai(new AdmsGrupoServico(fk_id_grupo_servico_pai));
                    }
                break;
            }
        }
        
        return reg;
    }
    
    
    
    public boolean createRegister(AdmsGrupoServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsGrupoServicoFacade.create(reg);
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

    public boolean editRegister(AdmsGrupoServico reg)
    {
        try
        {
            this.userTransaction.begin();
            this.admsGrupoServicoFacade.edit(reg);
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
