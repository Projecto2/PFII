/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean.carregamentoExcel;

import entidade.GrlComuna;
import entidade.GrlDistrito;
import entidade.GrlEndereco;
import entidade.GrlMunicipio;
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
import sessao.GrlEnderecoFacade;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlCentroEnderecoExcelBean implements Serializable
{

    @EJB
    private GrlEnderecoFacade grlEnderecoFacade;
//    @EJB
//    private GrlUpdatesFacade grlUpdatesFacade;

    /**
     * Creates a new instance of GrlEnderecoExcelBean
     */
    @Resource
    private UserTransaction userTransaction;

    public GrlCentroEnderecoExcelBean ()
    {
    }

    public static GrlCentroEnderecoExcelBean getInstanciaBean ()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        GrlCentroEnderecoExcelBean grlEnderecoExcelBean
                                   = (GrlCentroEnderecoExcelBean) context.getELContext().
                getELResolver().getValue(FacesContext.getCurrentInstance().
                        getELContext(), null, "grlCentroEnderecoExcelBean");

        return grlEnderecoExcelBean;
    }

    @SuppressWarnings("empty-statement")
    public void carregarEnderecoTabela ()
    {

//        Date dataEnderecoTabela = this.grlUpdatesFacade.dataEndereco();
        Date dataEnderecoXLSFile = util.DataUtils.dataModificacaoFicheiro(util.grl.Defs.FILE_CENTRO_ENDERECO);

//        if (this.grlEnderecoFacade.findAll().isEmpty() || dataEnderecoTabela == null);
//        else if (!grlEnderecoFacade.findAll().isEmpty() && (dataEnderecoXLSFile != null && dataEnderecoXLSFile.compareTo(dataEnderecoTabela) <= 0))
//        {
//            return;
//        }
        if (lerEnderecoTabela())
        {
            System.out.println("Esta carregando centro_endereco");
//            this.grlUpdatesFacade.escreverDataActualizacaoEnderecoTabela();
        }
    }

    public boolean lerEnderecoTabela ()
    {
        int nreg = 0;
        try
        {
            //Criar o Stream, caminho
            InputStream ExcelFileToRead = new FileInputStream(util.grl.Defs.FILE_CENTRO_ENDERECO);

            //Pega o Livro do Excel (O ficheiro Excel)
            HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

            //Pega a Pagina que desejamos ler
            HSSFSheet sheet = wb.getSheet("endereco");

            HSSFRow row;

            //Pega o o iterador que ira varrer todas as linhas desta pagina
            Iterator rows = sheet.rowIterator();

            //variavel que verifica se o iterador de linhas esta na primeira linha ou nao
            boolean firstRow = true;

            //como cada linha do registo ira representar um dado na bd, esses valores que
            //se encontram em cada linha tem que ser transformado num registo que pode ser
            //armazenado na base de dados, esntao criaremos com esta variavel, para depois 
            //gravar na bd
            GrlEndereco reg = null;

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

                escreverEnderecoTabela(reg);
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

    public void escreverEnderecoTabela (GrlEndereco reg)
    {
        if (reg.getPkIdEndereco() == null || grlEnderecoFacade.find(reg.getPkIdEndereco()) == null)
        {
            this.createRegister(reg);
        }
        else
        {
            this.editRegister(reg);
        }
    }

    public GrlEndereco lerCampos (HSSFRow row)
    {
//        long pk_id_endereco;
        String bairro;
        String numero_casa;
        String rua;
        int fk_id_comuna;
        String codigo_postal;
        int fk_id_distrito;
        int fk_id_municipio;

//        final int PK_ID_ENDERECO = 0;
        final int BAIRRO = 0;
        final int NUMERO_CASA = 1;
        final int RUA = 2;
        final int FK_ID_COMUNA = 3;
        final int CODIGO_POSTAL = 4;
        final int FK_ID_DISTRITO = 5;
        final int FK_ID_MUNICIPIO = 6;

        GrlEndereco reg = new GrlEndereco();

//        HSSFCell cell;
        for (int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);

            switch (cn)
            {
                case BAIRRO:
                    try
                    {
                        bairro = cell.getStringCellValue();
                        reg.setBairro(bairro);
                    }
                    catch (NullPointerException ex){}
                    break;

                case NUMERO_CASA:
                    try
                    {
                        numero_casa = cell.getStringCellValue();
                        reg.setNumeroCasa(numero_casa);
                    }
                    catch (NullPointerException ex){}
                    break;

                case RUA:
                    try
                    {
                        rua = cell.getStringCellValue();
                        reg.setRua(rua);
                    }
                    catch (NullPointerException ex){}
                    break;

                case FK_ID_COMUNA:
                    try
                    {
                        fk_id_comuna = (int) cell.getNumericCellValue();
                        reg.setFkIdComuna(new GrlComuna(fk_id_comuna));
                    }
                    catch (NullPointerException ex){}
                    break;

                case CODIGO_POSTAL:
                    try
                    {
                        codigo_postal = cell.getStringCellValue();
                        reg.setCodigoPostal(codigo_postal);
                    }
                    catch (NullPointerException ex){}
                    break;

                case FK_ID_DISTRITO:
                    try
                    {
                        fk_id_distrito = (int) cell.getNumericCellValue();
                        reg.setFkIdDistrito(new GrlDistrito(fk_id_distrito));
                    }
                    catch (NullPointerException ex){}
                    break;

                case FK_ID_MUNICIPIO:
                    fk_id_municipio = (int) cell.getNumericCellValue();
                    reg.setFkIdMunicipio(new GrlMunicipio(fk_id_municipio));
                    break;
            }
        }

        return reg;
    }

    public boolean createRegister (GrlEndereco reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlEnderecoFacade.create(reg);
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

    public boolean editRegister (GrlEndereco reg)
    {
        try
        {
            this.userTransaction.begin();
            this.grlEnderecoFacade.edit(reg);
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
