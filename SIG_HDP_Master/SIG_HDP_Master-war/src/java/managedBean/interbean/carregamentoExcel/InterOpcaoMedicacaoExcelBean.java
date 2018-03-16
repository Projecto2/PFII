/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.carregamentoExcel;

import entidade.InterOpcaoMedicacao;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.interbean.InterOpcaoMedicacaoListarBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.InterOpcaoMedicacaoFacade;
import sessao.InterUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterOpcaoMedicacaoExcelBean implements Serializable
{
    @EJB
    private InterOpcaoMedicacaoFacade interOpcaoMedicacaoFacade;

    @EJB
    private InterUpdatesFacade interUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    
    /**
     * Creates a new instance of InterOpcaoMedicacaoExcelBean
     */
    public InterOpcaoMedicacaoExcelBean()
    {
    }
    
    public static InterOpcaoMedicacaoExcelBean getInstanciaBean()
    {
        return (InterOpcaoMedicacaoExcelBean) GeradorCodigo.getInstanciaBean("interOpcaoMedicacaoExcelBean");
    }
    
    @SuppressWarnings("empty-statement")
    public void carregarOpcaoMedicacaoTabela()
    {
        Date dataOpcaoMedicacaoTabela = this.interUpdatesFacade.dataOpcaoMedicacaoTabela();

        Date dataOpcaoMedicacaoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.inter.Defs.FILE_OPCAO_MEDICACAO_INTER);

        if (this.interOpcaoMedicacaoFacade.isOpcaoMedicacaoTabelaEmpty() || dataOpcaoMedicacaoTabela == null);
        else if (!interOpcaoMedicacaoFacade.isOpcaoMedicacaoTabelaEmpty() && (dataOpcaoMedicacaoXLSFile != null && dataOpcaoMedicacaoXLSFile.compareTo(dataOpcaoMedicacaoTabela) <= 0))
        {
            return;
        }

        if (lerOpcaoMedicacaoTabela())
        {
            this.interUpdatesFacade.escreverDataActualizacaoOpcaoMedicacaoTabela();
        }
        
        InterOpcaoMedicacaoListarBean.getInstanciaBean().findByDescricao();
    }

    public boolean lerOpcaoMedicacaoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.inter.Defs.FILE_OPCAO_MEDICACAO_INTER);
            
            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
            
            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("opcaoMedicacao");
            

            HSSFRow row;
//            HSSFCell cell;
            
            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataOpcaoMedicacaoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.inter.Defs.FILE_OPCAO_MEDICACAO_INTER);
            
            
            Date dataOpcaoMedicacaoTabela = this.interUpdatesFacade.dataOpcaoMedicacaoTabela();
            
            
            if (dataOpcaoMedicacaoTabela == null);
            else if (dataOpcaoMedicacaoXLSFile.compareTo(dataOpcaoMedicacaoTabela) <= 0)
            {
                return false;
            }
            
            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            InterOpcaoMedicacao reg = null;

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
                
                escreverOpcaoMedicacaoTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverOpcaoMedicacaoTabela(InterOpcaoMedicacao reg)
    {

        if (interOpcaoMedicacaoFacade.find(reg.getPkIdOpcaoMedicacao()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }    
    
    public InterOpcaoMedicacao lerCampos(HSSFRow row)
    {
        int pk_id_opcao_medicacao;
        String descricao, codigo;
        
        final int PK_ID_OPCAO_MEDICACAO = 0;
        final int DESCRICAO = 1;
        final int CODIGO = 2;
        
        InterOpcaoMedicacao reg = new InterOpcaoMedicacao();
        
//        HSSFCell cell;
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) {
                case PK_ID_OPCAO_MEDICACAO:
                    pk_id_opcao_medicacao = (int)cell.getNumericCellValue();
                    reg.setPkIdOpcaoMedicacao(pk_id_opcao_medicacao);
                break;
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                    
                case CODIGO:
                    codigo = cell.getStringCellValue().trim();
                    reg.setCodigo(codigo);
                break;                
            }
        }
        
        return reg;
    }        
    
    public boolean createRegister(InterOpcaoMedicacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interOpcaoMedicacaoFacade.create(reg);
            this.userTransaction.commit();
            return true;
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                System.out.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.out.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

    public boolean editRegister(InterOpcaoMedicacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interOpcaoMedicacaoFacade.edit(reg);
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
                System.out.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }
}
