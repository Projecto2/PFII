/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean.carregamentoExcel;

import entidade.SegTipoFuncionalidade;
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
import sessao.SegTipoFuncionalidadeFacade;
import sessao.SegUpdateFacade;
import util.FileManagerGeral;

/**
 *
 * @author adalberto
 */
@ManagedBean
@SessionScoped
public class SegTipoFuncionalidadeExcelBean implements Serializable
{

    @EJB
    private SegTipoFuncionalidadeFacade funcionalidadeFacade;
    @EJB
    private SegUpdateFacade segUpdatesFacade;

    /**
     * Creates a new instance of SegTipoFuncionalidadeExcelBean
     */
    @Resource
    private UserTransaction userTransaction;

    public SegTipoFuncionalidadeExcelBean()
    {
    }

    public static SegTipoFuncionalidadeExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        SegTipoFuncionalidadeExcelBean segTipoFuncionalidadeExcelBean
            = (SegTipoFuncionalidadeExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
                getELContext(), null, "segTipoFuncionalidadeExcelBean");

        return segTipoFuncionalidadeExcelBean;
    }

    @SuppressWarnings("empty-statement")
    public void carregarTipoFuncionalidadeTabela()
    {
        if (lerTipoFuncionalidadeTabela())
        {
            this.segUpdatesFacade.escreverDataActualizacaoTipoFuncionalidadeTabela();
        }
    }

    @SuppressWarnings("empty-statement")
    public boolean lerTipoFuncionalidadeTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.seg.Defs.FILE_TIPO_FUNCIONALIDADE_SEG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipoFuncionalidade");

            HSSFRow row;
//            HSSFCell cell;

            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataFuncionalidadeXLSFile = FileManagerGeral.lerVersaoTabela(row, util.seg.Defs.FILE_TIPO_FUNCIONALIDADE_SEG);

            Date dataTipoFuncionalidadeTabela = this.segUpdatesFacade.dataFuncionalidade();

            if (dataTipoFuncionalidadeTabela == null);
            else if (dataFuncionalidadeXLSFile.compareTo(dataTipoFuncionalidadeTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            SegTipoFuncionalidade reg = null;

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

                escreverTipoFuncionalidadeTabela(reg);
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

    public void escreverTipoFuncionalidadeTabela(SegTipoFuncionalidade reg)
    {
//        if (funcionalidadeFacade.existeRegisto(reg.getPkIdFuncionalidade()) == false)
//        {
        this.createRegister(reg);
//        }
//        else
//        {
//            this.editRegister(reg);
//        }
    }

    public SegTipoFuncionalidade lerCampos(HSSFRow row)
    {
        String nome;

        final int NOME = 1;

        SegTipoFuncionalidade reg = new SegTipoFuncionalidade();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case NOME:
                    nome = cell.getStringCellValue().trim();
                    reg.setNome(nome);
                    break;
            }
        }

        return reg;
    }

    public boolean createRegister(SegTipoFuncionalidade reg)
    {
        try
        {
            this.userTransaction.begin();
            this.funcionalidadeFacade.create(reg);
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

    public boolean editRegister(SegTipoFuncionalidade reg)
    {
        try
        {
            this.userTransaction.begin();
            this.funcionalidadeFacade.edit(reg);
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
