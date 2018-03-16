/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.tbbean.carregamentoExcel;

import entidade.TbTipoNotificacao;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.TbTipoNotificacaoFacade;
import sessao.TbUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;
import util.Mensagem;
import util.tb.TbMensagens;

/**
 *
 * @author adelino
 */
@Named
@SessionScoped
public class TbTipoNotificacaoExcelBean implements Serializable
{

    @EJB
    private TbUpdatesFacade tbUpdatesFacade;

    @EJB
    private TbTipoNotificacaoFacade tbTipoNotificacaoFacade;

    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of TbTipoNotificacaoExcelBean
     */
    public TbTipoNotificacaoExcelBean()
    {
    }

    public static TbTipoNotificacaoExcelBean getInstanciaBean()
    {
        return (TbTipoNotificacaoExcelBean) GeradorCodigo.getInstanciaBean("tbTipoNotificacaoExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarTipoNotificacaoTabela()
    {
        if (lerTipoNotificacaoTabela())
        {
            tbUpdatesFacade.escreverDataTipoNotificacaoTabela();
            Mensagem.sucessoMsg(TbMensagens.CARREGAMENTO);
        }
    }

    @SuppressWarnings("empty-statement")
    public boolean lerTipoNotificacaoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.tb.TbDefs.FILE_TIPO_NOTIFICACAO_TB);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("tipo_notificacao");

            HSSFRow row;
            //HSSFCell cell;

            //Pega o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            row = (HSSFRow) rows.next();
            Date dataTipoNotificacaoXLSFile = FileManagerGeral.lerVersaoTabela(row, util.tb.TbDefs.FILE_TIPO_NOTIFICACAO_TB);

            Date dataTipoNotificacaoTabela = this.tbUpdatesFacade.dataTipoNotificacao();

            if (dataTipoNotificacaoTabela == null);
            else if (dataTipoNotificacaoXLSFile.compareTo(dataTipoNotificacaoTabela) <= 0)
            {
                return false;
            }

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            TbTipoNotificacao reg = null;

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

                escreverTipoNotificacaoTabela(reg);

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

    public void escreverTipoNotificacaoTabela(TbTipoNotificacao reg)
    {
        if (reg.getPkTipoNotificacao() == null ) return;
        if (tbTipoNotificacaoFacade.existeRegisto(reg.getPkTipoNotificacao()) == false)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public TbTipoNotificacao lerCampos(HSSFRow row)
    {
        int pk_tipoNotificacao = 0;
        String descricao = "a";
        
        final int PK_TIPO_NOTIFICACAO = 0;
        final int DESCRICAO = 1;

        TbTipoNotificacao reg = new TbTipoNotificacao();

        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_TIPO_NOTIFICACAO:
                    pk_tipoNotificacao = (int) cell.getNumericCellValue();
                    reg.setPkTipoNotificacao(pk_tipoNotificacao);
                    break;

                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricao(descricao);
                    break;
            }
        }
        System.out.println("PK: " + pk_tipoNotificacao + "descricao: " + descricao);
        return reg;
    }

    public boolean createRegister(TbTipoNotificacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbTipoNotificacaoFacade.create(reg);
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
                System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

    public boolean editRegister(TbTipoNotificacao reg)
    {
        try
        {
            this.userTransaction.begin();
            this.tbTipoNotificacaoFacade.edit(reg);
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
                System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }
    
}
