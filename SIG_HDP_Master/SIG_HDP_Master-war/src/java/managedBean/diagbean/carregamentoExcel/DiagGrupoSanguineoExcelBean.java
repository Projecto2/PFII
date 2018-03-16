/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.diagbean.carregamentoExcel;

import entidade.DiagGrupoSanguineo;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessao.DiagGrupoSanguineoFacade;
import sessao.DiagUpdatesFacade;
import util.GeradorCodigo;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagGrupoSanguineoExcelBean implements Serializable
{
@EJB
    private DiagGrupoSanguineoFacade diagGrupoSanguineoFacade;

    @EJB
    private DiagUpdatesFacade diagUpdatesFacade;

    @Resource
    private UserTransaction userTransaction;
    /**
     * Creates a new instance of DiagGrupoSanguineoExcelBean
     */
    public DiagGrupoSanguineoExcelBean()
    {
    }
    
    public static DiagGrupoSanguineoExcelBean getInstanciaBean()
    {
        return (DiagGrupoSanguineoExcelBean) GeradorCodigo.getInstanciaBean("diagGrupoSanguineoExcelBean");
    }

    @SuppressWarnings("empty-statement")
    public void carregarGrupoSanguineoTabela()
    {
        Date dataGrupoSanguineoTabela = this.diagUpdatesFacade.dataGrupoSanguineoTabela();

        Date dataGrupoSanguineoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.diag.Defs.FILE_GRUPO_SANGUINEO_DIAG);

        if (this.diagGrupoSanguineoFacade.isGrupoSanguineoTabelaEmpty() || dataGrupoSanguineoTabela == null);
        else if (!diagGrupoSanguineoFacade.isGrupoSanguineoTabelaEmpty() && (dataGrupoSanguineoXLSFile != null && dataGrupoSanguineoXLSFile.compareTo(dataGrupoSanguineoTabela) <= 0))
        {
            return;
        }

        if (lerGrupoSanguineoTabela())
        {
            this.diagUpdatesFacade.escreverDataActualizacaoGrupoSanguineoTabela();
        }

        //Depois de carregar, actualizar a lista e apresentar a mensagem de que a pesquisa foi efectuada com sucesso
//        InterCamaListarBean.getInstanciaBean().pesquisar();
    }

    public boolean lerGrupoSanguineoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.diag.Defs.FILE_GRUPO_SANGUINEO_DIAG);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("gruposSanguineos");

            HSSFRow row;
//            HSSFCell cell;

            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            DiagGrupoSanguineo reg = null;

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

                escreverGrupoSanguineoTabela(reg);
                nreg++;
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public void escreverGrupoSanguineoTabela(DiagGrupoSanguineo reg)
    {

        if (diagGrupoSanguineoFacade.find(reg.getPkIdGrupoSanguineo()) != null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public DiagGrupoSanguineo lerCampos(HSSFRow row)
    {
        int pk_id_grupo_sanguineo;
        String descricao_grupo_sanguineo;

        final int PK_ID_GRUPO_SANGUINEO = 0;
        final int DESCRICAO_GRUPO_SANGUINEO = 1;

        DiagGrupoSanguineo reg = new DiagGrupoSanguineo();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case PK_ID_GRUPO_SANGUINEO:
                    pk_id_grupo_sanguineo = (int) cell.getNumericCellValue();
                    reg.setPkIdGrupoSanguineo(pk_id_grupo_sanguineo);
                    break;

                case DESCRICAO_GRUPO_SANGUINEO:
                    descricao_grupo_sanguineo = cell.getStringCellValue().trim();
                    reg.setDescricaoGrupoSanguineo(descricao_grupo_sanguineo);
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(DiagGrupoSanguineo reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagGrupoSanguineoFacade.create(reg);
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

    public boolean editRegister(DiagGrupoSanguineo reg)
    {
        try
        {
            this.userTransaction.begin();
            this.diagGrupoSanguineoFacade.edit(reg);
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
