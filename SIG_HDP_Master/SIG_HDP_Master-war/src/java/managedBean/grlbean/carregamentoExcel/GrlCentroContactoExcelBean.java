/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean.carregamentoExcel;

import entidade.GrlContacto;
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
import sessao.GrlUpdatesFacade;
import sessao.GrlContactoFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlCentroContactoExcelBean implements Serializable
{

    @EJB
    private GrlContactoFacade grlContactoFacade;
//    @EJB
//    private GrlUpdatesFacade grlUpdatesFacade;

    /**
     * Creates a new instance of GrlContactoExcelBean
     */
    @Resource
    private UserTransaction userTransaction;

    public GrlCentroContactoExcelBean()
    {
    }

    public static GrlCentroContactoExcelBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlCentroContactoExcelBean grlContactoExcelBean
            = (GrlCentroContactoExcelBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
                getELContext(), null, "grlCentroContactoExcelBean");

        return grlContactoExcelBean;
    }

    @SuppressWarnings("empty-statement")
    public void carregarContactoTabela()
    {

//        Date dataContactoTabela = this.grlUpdatesFacade.dataContacto();
        Date dataContactoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_CENTRO_CONTACTO);

//        if (this.grlContactoFacade.findAll().isEmpty() || dataContactoTabela == null);
//        else if (!grlContactoFacade.findAll().isEmpty() && (dataContactoXLSFile != null && dataContactoXLSFile.compareTo(dataContactoTabela) <= 0))
//        {
//            return;
//        }
        if (lerContactoTabela())
        {
            System.out.println("Esta carregando centro_contacto");
//            this.grlUpdatesFacade.escreverDataActualizacaoContactoTabela();
        }
    }

    public boolean lerContactoTabela()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_CENTRO_CONTACTO);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("contacto");

            HSSFRow row;

            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlContacto reg = null;

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

                escreverContactoTabela(reg);
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

    public void escreverContactoTabela(GrlContacto reg)
    {
        if (reg.getPkIdContacto() == null || grlContactoFacade.find(reg.getPkIdContacto()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public GrlContacto lerCampos(HSSFRow row)
    {
//        long pk_id_contacto;
        String telefone1;
        String telefone2;
        String email1;
        String email2;

//        final int PK_ID_CONTACTO = 0;
        final int TELEFONE1 = 0;
        final int TELEFONE2 = 1;
        final int EMAIL1 = 2;
        final int EMAIL2 = 3;

        GrlContacto reg = new GrlContacto();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case TELEFONE1:
                    telefone1 = cell.getStringCellValue();
                    reg.setTelefone1(telefone1);
                    break;

                case TELEFONE2:
                    if (cell.getStringCellValue() != null)
                    {
                        telefone2 = cell.getStringCellValue();
                        reg.setTelefone2(telefone2);
                    }
                    break;

                case EMAIL1:
                    if (cell.getStringCellValue() != null)
                    {
                        email1 = cell.getStringCellValue();
                        reg.setEmail1(email1);
                    }
                    break;

                case EMAIL2:
                    if (cell.getStringCellValue() != null)
                    {
                        email2 = cell.getStringCellValue();
                        reg.setEmail2(email2);
                    }
                    break;

            }
        }

        return reg;
    }

    public boolean createRegister(GrlContacto reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlContactoFacade.create(reg);
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

    public boolean editRegister(GrlContacto reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlContactoFacade.edit(reg);
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
