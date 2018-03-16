/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean.carregamentoExcel;

import entidade.InterEnfermaria;
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
import managedBean.interbean.InterObjetosSessaoBean;
import managedBean.interbean.InterSalaListarBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.InterSalaInternamentoFacade;
import sessao.InterUpdatesFacade;
import util.FileManagerGeral;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterSalaExcelBean implements Serializable
{

    @EJB
    private InterSalaInternamentoFacade interSalaInternamentoFacade;
    
    @EJB
    private InterUpdatesFacade interUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    
    
    /**
     * Creates a new instance of InterSalaExcelBean
     */
    public InterSalaExcelBean()
    {
    }

    public static InterSalaExcelBean getInstanciaBean()
    {
        return (InterSalaExcelBean) GeradorCodigo.getInstanciaBean("interSalaExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarSalaTabela()
    {
        Date dataSalaTabela = this.interUpdatesFacade.dataSalaTabela();

        Date dataSalaXLSFile = util.DataUtils.dataModificacaoFicheiro(util.inter.Defs.FILE_SALAS_INTER);

        if (this.interSalaInternamentoFacade.isSalaTabelaEmpty() || dataSalaTabela == null);
        else if (!interSalaInternamentoFacade.isSalaTabelaEmpty() && (dataSalaXLSFile != null && dataSalaXLSFile.compareTo(dataSalaTabela) <= 0))
        {
            return;
        }

        if (lerSalaTabela())
        {
            this.interUpdatesFacade.escreverDataActualizacaoSalaTabela();
        }
        
        InterSalaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerSalaTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.inter.Defs.FILE_SALAS_INTER);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("salas");

            HSSFRow row;
//            HSSFCell cell;

            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

             // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataSalaXLSFile = FileManagerGeral.lerVersaoTabela(row, util.inter.Defs.FILE_SALAS_INTER);
            
            
            Date dataSalaTabela = this.interUpdatesFacade.dataSalaTabela();
            
            
            if (dataSalaTabela == null);
            else if (dataSalaXLSFile.compareTo(dataSalaTabela) <= 0)
            {
                return false;
            }
            
            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            InterSalaInternamento reg = null;

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

                escreverSalaTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverSalaTabela(InterSalaInternamento reg)
    {

        if (interSalaInternamentoFacade.find(reg.getPkIdSalaInternamento()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public InterSalaInternamento lerCampos(HSSFRow row)
    {
        int pk_id_sala;
        String codigo_sala;
        String nome_sala;
        int fk_id_enfermaria;
        String descricao;

        final int PK_ID_SALA = 0;
        final int CODIGO = 1;
        final int NOME = 2;
        final int FK_ID_ENFERMARIA = 3;
        final int DESCRICAO = 4;

        InterSalaInternamento reg = new InterSalaInternamento();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_SALA:
                    pk_id_sala = (int) cell.getNumericCellValue();
                    reg.setPkIdSalaInternamento(pk_id_sala);
                    break;

                case CODIGO:
                    if(cell == null)
                    {
                        reg.setCodigoSalaInternamento(null);
                    }
                    else
                    {
                        codigo_sala = ""+((int) cell.getNumericCellValue());
                        reg.setCodigoSalaInternamento(codigo_sala);
                    }
                break;                   

                case NOME:
                    nome_sala = cell.getStringCellValue().trim();
                    reg.setNomeSala(nome_sala);
                    break;

                case FK_ID_ENFERMARIA:
                    if (cell == null)
                    {
                        reg.setFkIdEnfermaria(null);
                    }
                    else
                    {
                        fk_id_enfermaria = (int) cell.getNumericCellValue();
                        reg.setFkIdEnfermaria(new InterEnfermaria(fk_id_enfermaria));
                    }
                    break;

                case DESCRICAO:
                    descricao = cell.getStringCellValue().trim();
                    reg.setDescricaoSalaInternamento(descricao);
                    break;
            }
        }

        return reg;
    }

    public boolean createRegister(InterSalaInternamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interSalaInternamentoFacade.create(reg);
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

    public boolean editRegister(InterSalaInternamento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.interSalaInternamentoFacade.edit(reg);
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
