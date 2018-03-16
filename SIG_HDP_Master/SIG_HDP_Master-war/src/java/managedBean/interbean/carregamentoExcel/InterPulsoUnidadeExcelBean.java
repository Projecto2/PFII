/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean.carregamentoExcel;

import entidade.InterPulsoUnidade;
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
import managedBean.interbean.InterPulsoUnidadeListarBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.InterPulsoUnidadeFacade;
import sessao.InterUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterPulsoUnidadeExcelBean implements Serializable
{

    @EJB
    private InterPulsoUnidadeFacade interPulsoUnidadeFacade;

    @EJB
    private InterUpdatesFacade interUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of InterPulsoUnidadeExcelBean
     */
    public InterPulsoUnidadeExcelBean()
    {
    }

    public static InterPulsoUnidadeExcelBean getInstanciaBean()
    {
        return (InterPulsoUnidadeExcelBean) GeradorCodigo.getInstanciaBean("interPulsoUnidadeExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarPulsoUnidadeTabela()
    {
        Date dataPulsoUnidadeTabela = this.interUpdatesFacade.dataPulsoUnidadeTabela();

        Date dataPulsoUnidadeXLSFile = util.DataUtils.dataModificacaoFicheiro(util.inter.Defs.FILE_PULSO_UNIDADE_INTER);

        if (this.interPulsoUnidadeFacade.isPulsoUnidadeTabelaEmpty() || dataPulsoUnidadeTabela == null);
        else if (!interPulsoUnidadeFacade.isPulsoUnidadeTabelaEmpty() && (dataPulsoUnidadeXLSFile != null && dataPulsoUnidadeXLSFile.compareTo(dataPulsoUnidadeTabela) <= 0))
        {
            return;
        }

        if (lerPulsoUnidadeTabela())
        {
            this.interUpdatesFacade.escreverDataActualizacaoPulsoUnidadeTabela();
        }

        InterPulsoUnidadeListarBean.getInstanciaBean().findByDescricao();
    }

    public boolean lerPulsoUnidadeTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.inter.Defs.FILE_PULSO_UNIDADE_INTER);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("pulsoUnidade");

            HSSFRow row;
//            HSSFCell cell;

            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataPulsoUnidadeXLSFile = FileManagerGeral.lerVersaoTabela(row, util.inter.Defs.FILE_PULSO_UNIDADE_INTER);

            Date dataPulsoUnidadeTabela = this.interUpdatesFacade.dataPulsoUnidadeTabela();

            if (dataPulsoUnidadeTabela == null);
            else if (dataPulsoUnidadeXLSFile.compareTo(dataPulsoUnidadeTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            InterPulsoUnidade reg = null;

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

                escreverPulsoUnidadeTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverPulsoUnidadeTabela(InterPulsoUnidade reg)
    {

        if (interPulsoUnidadeFacade.find(reg.getPkIdPulsoUnidade()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public InterPulsoUnidade lerCampos(HSSFRow row)
    {
        int pk_id_opcao_medicacao;
        String descricao, codigo;

        final int PK_ID_PULSO_UNIDADE = 0;
        final int CODIGO = 1;
        final int DESCRICAO = 2;


        InterPulsoUnidade reg = new InterPulsoUnidade();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_PULSO_UNIDADE:
                    pk_id_opcao_medicacao = (int) cell.getNumericCellValue();
                    reg.setPkIdPulsoUnidade(pk_id_opcao_medicacao);
                    break;

                case CODIGO:
                    codigo = cell.getStringCellValue().trim();
                    reg.setCodigo(codigo);     
                    
                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);   
                break;
            }
        }

        return reg;
    }

    public boolean createRegister(InterPulsoUnidade reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interPulsoUnidadeFacade.create(reg);
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

    public boolean editRegister(InterPulsoUnidade reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interPulsoUnidadeFacade.edit(reg);
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
