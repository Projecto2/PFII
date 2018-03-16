/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean.carregamentoExcel;

import entidade.InterCamaInternamento;
import entidade.InterEstadoCama;
import entidade.InterSalaInternamento;
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
import managedBean.interbean.InterCamaListarBean;
import managedBean.interbean.InterObjetosSessaoBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.InterCamaInternamentoFacade;
import sessao.InterUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterCamaExcelBean implements Serializable
{

    @EJB
    private InterCamaInternamentoFacade interCamaInternamentoFacade;
    
    @EJB
    private InterUpdatesFacade interUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;

    
    /**
     * Creates a new instance of InterCamaExcelBean
     */
    public InterCamaExcelBean()
    {
    }

    public static InterCamaExcelBean getInstanciaBean()
    {
        return (InterCamaExcelBean) GeradorCodigo.getInstanciaBean("interCamaExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarCamaInternamentoTabela()
    {
        InterEstadoCamaExcelBean.getInstanciaBean().carregarEstadoCamaTabela(2);
        
        Date dataCamaInternamentoTabela = this.interUpdatesFacade.dataCamaTabela();

        Date dataCamaInternamentoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.inter.Defs.FILE_CAMAS_INTER);

        if (this.interCamaInternamentoFacade.isCamaTabelaEmpty() || dataCamaInternamentoTabela == null);
        else if (!interCamaInternamentoFacade.isCamaTabelaEmpty() && (dataCamaInternamentoXLSFile != null && dataCamaInternamentoXLSFile.compareTo(dataCamaInternamentoTabela) <= 0))
        {
            return;
        }

        if (lerCamaInternamentoTabela())
        {
            this.interUpdatesFacade.escreverDataActualizacaoCamaTabela();
        }
        
        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerCamaInternamentoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.inter.Defs.FILE_CAMAS_INTER);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("camas");

            HSSFRow row;
//            HSSFCell cell;

            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();
            
             // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataCamaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.inter.Defs.FILE_CAMAS_INTER);
            
            
            Date dataCamaTabela = this.interUpdatesFacade.dataCamaTabela();
            
            
            if (dataCamaTabela == null);
            else if (dataCamaXLSFile.compareTo(dataCamaTabela) <= 0)
            {
                return false;
            }
            
            
            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            InterCamaInternamento reg = null;

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

                escreverCamaInternamentoTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverCamaInternamentoTabela(InterCamaInternamento reg)
    {

        if (interCamaInternamentoFacade.find(reg.getPkIdCamaInternamento()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public InterCamaInternamento lerCampos(HSSFRow row)
    {
        int pk_id_cama;
        String descricao;
        String codigo;
        int fk_id_sala;
        int fk_id_estado;

        final int PK_ID_CAMA = 0;
        final int DESCRICAO = 1;
        final int CODIGO = 2;
        final int FK_ID_SALA = 3;
        final int FK_ID_ESTADO = 4;

        InterCamaInternamento reg = new InterCamaInternamento();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_CAMA:
                    pk_id_cama = (int) cell.getNumericCellValue();
                    reg.setPkIdCamaInternamento(pk_id_cama);
                    break;

                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricaoCamaInternamento(descricao);
                    break;    
                    
                case CODIGO:
                    codigo = ""+((int) cell.getNumericCellValue());
                    reg.setCodigoCamaInternamento(codigo);
                    break;

                case FK_ID_SALA:
                    if (cell == null)
                    {
                        reg.setFkIdSalaInternamento(null);
                    }
                    else
                    {
                        fk_id_sala = (int) cell.getNumericCellValue();
                        reg.setFkIdSalaInternamento(new InterSalaInternamento(fk_id_sala));
                    }
                    break;
                    
                case FK_ID_ESTADO:
                    if (cell == null)
                    {
                        reg.setFkIdEstadoCama(null);
                    }
                    else
                    {
                        fk_id_estado = (int) cell.getNumericCellValue();
                        reg.setFkIdEstadoCama(new InterEstadoCama(fk_id_estado));
                    }
                    break;

                
            }
        }

        return reg;
    }

    public boolean createRegister(InterCamaInternamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interCamaInternamentoFacade.create(reg);
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

    public boolean editRegister(InterCamaInternamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interCamaInternamentoFacade.edit(reg);
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
