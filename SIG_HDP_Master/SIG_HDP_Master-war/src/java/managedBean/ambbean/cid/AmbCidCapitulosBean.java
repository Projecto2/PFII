/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidCapitulos;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import sessao.AmbCidCapitulosFacade;
import sessao.AmbCidUpdatesFacade;
import util.GeradorCodigo;
import util.amb.FileManager;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidCapitulosBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AmbCidUpdatesFacade ambCidUpdatesFacade;

    @EJB
    private AmbCidCapitulosFacade ambCidCapitulosFacade;

    private AmbCidCapitulos ambCidCapitulos;

    /**
     * Creates a new instance of AmbCidCapituloManagedBean
     */
    public AmbCidCapitulosBean()
    {
    }

    public static AmbCidCapitulosBean getInstanciaBean()
    {
        return (AmbCidCapitulosBean) GeradorCodigo.getInstanciaBean("ambCidCapitulosBean");
    }

    public static AmbCidCapitulos getInstancia()
    {
        return new AmbCidCapitulos();
    }

    public AmbCidCapitulos getAmbCidCapitulos()
    {
        return ambCidCapitulos;
    }

    public void setAmbCidCapitulos(AmbCidCapitulos ambCidCapitulos)
    {
        this.ambCidCapitulos = ambCidCapitulos;
    }

    public String init()
    {
        ambCidCapitulos = new AmbCidCapitulos();
        return util.amb.Defs.CAPITULOS_URL;
    }

    private boolean validarCapitulos(String capitulo)
    {
        for (AmbCidCapitulos capituloAux : ambCidCapitulosFacade.findAll())
        {
            if (capituloAux.getNome().equalsIgnoreCase(capitulo))
            {
                return true;
            }
        }
        return false;
    }

    public List<AmbCidCapitulos> findAll()
    {
        return ambCidCapitulosFacade.findAll();
    }

    public List<AmbCidCapitulos> findAllOrderByNome()
    {
        return ambCidCapitulosFacade.findAllOrderByNome();
    }

    public List<AmbCidCapitulos> findAllOrderByNomeFromPerfilPreferencial(String perfilPreferencial, int idDoencasPrioridadesPreferencial)
    {
        return ambCidCapitulosFacade.findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial);
    }

    public List<AmbCidCapitulos> findAllOrderById()
    {
        return ambCidCapitulosFacade.findAllOrderById();
    }

    public void gravar()
    {
        if (!validarCapitulos(ambCidCapitulos.getNome()))
        {
            this.ambCidCapitulosFacade.create(ambCidCapitulos);
            ambCidCapitulos = new AmbCidCapitulos();
        }
    }

    public void apagar(String idCapitulo)
    {
        ambCidCapitulosFacade.remove(new AmbCidCapitulos(idCapitulo));
    }

    public void actualizarCid10Tabelas()
    {
        //System.err.println("Inicio da actualizzacao da tabelas do Cid-10");
        carregarCapitulosTabela();
        carregarAgrupamentosTabela();
        carregarCategoriasTabela();
        carregarSubcategoriasTabela();
        carregarPerfilTiposTabela();
        carregarDoencasPrioridadesTabela();
        //System.err.println("Fim da actualizzacao da tabelas do Cid-10");
        //System.err.println("1: AmbCidCapitulosBean.actualizarCid10Tabelas()");
    }

    public void carregarPerfilTiposTabela()
    {
        AmbCidPerfilTiposBean ambCidPerfilTiposBean = AmbCidPerfilTiposBean.getInstanciaBean();
        ambCidPerfilTiposBean.carregarPerfilTiposTabela();
    }

    public void carregarDoencasPrioridadesTabela()
    {
        AmbCidDoencasPrioridadesBean ambCidDoencasPrioridadesBean = AmbCidDoencasPrioridadesBean.getInstanciaBean();
        ambCidDoencasPrioridadesBean.carregarDoencasPrioridadesTabela();
    }

    public void carregarSubcategoriasTabela()
    {
        AmbCidSubcategoriasBean ambCidSubCategoriasBean = AmbCidSubcategoriasBean.getInstanciaBean();
        ambCidSubCategoriasBean.carregarSubcategoriasTabela();
// 8888888888888888888888888888888888
    }

    public void carregarCategoriasTabela()
    {
        AmbCidCategoriasBean ambCidCategoriasBean = AmbCidCategoriasBean.getInstanciaBean();
        ambCidCategoriasBean.carregarCategoriasTabela();
// 8888888888888888888888888888888888
    }

    public void carregarAgrupamentosTabela()
    {
        AmbCidAgrupamentosBean ambCidAgrupamentosBean = AmbCidAgrupamentosBean.getInstanciaBean();
        ambCidAgrupamentosBean.carregarAgrupamentosTabela();
// 8888888888888888888888888888888888
    }

    @SuppressWarnings("empty-statement")
    public void carregarCapitulosTabela()
    {
        //System.err.println("0: AmbCidCapitulosBean.carregarCapitulosTabela()\tambCidUpdatesFacade: "
        //  + (ambCidUpdatesFacade == null ? "null" : "not null"));

        if (lerCapitulosTabela())
        {
            //System.err.println("13: AmbCidCapitulosBean.carregarCapitulosTabela()");
            this.ambCidUpdatesFacade.escreverDataActualizacaoCapitulosTabela();
            System.err.println("Conclusao da actualizacao da tabela \"amb_cid_capitulos\" do Cid-10");
        }
        //System.err.println("14: AmbCidCapitulosBean.carregarCapitulosTabela()");
    }

    public boolean lerCapitulosTabela()
    {
        /*
         abrir ficheiro
         abrir folha
            
         ler numeroRegistos
         apagar todos os registos da tabela
        
         para i = 1 ate numeroRegistos
         ler registo(i)
         gravar registo(i) na tabela
         */
//System.err.println("0: AmbCidCapitulosBean.lerCapitulosTabela()");
        int nreg = 0;
        //System.err.println("03: AmbCidCapitulosBean.carregarCapitulosTabela()");
        try
        {
            HSSFSheet sheet = FileManager.getSheetFromXLS_File(util.amb.Defs.FILE_CID10_TABLES_AMB, util.amb.Defs.SHEET_CAPITULOS_AMB);
            //HSSFSheet sheet = wb.getSheet("capitulos");
//System.err.println("2: AmbCidCapitulosBean.lerCapitulosTabela()");
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();

//System.err.println("3: AmbCidCapitulosBean.lerCapitulosTabela()");
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataCapituloXLSFile = FileManager.lerVersaoTabela(row, util.amb.Defs.FILE_CID10_TABLES_AMB, util.amb.Defs.SHEET_CAPITULOS_AMB);
//System.err.println("4: AmbCidCapitulosBean.lerCapitulosTabela()\tdataCapituloXLSFile: "
            //+ util.DataUtils.toStringFull(dataCapituloXLSFile));

            Date dataCapitulosTabela = this.ambCidUpdatesFacade.dataCapitulosTabela();

//System.err.println("5: AmbCidCapitulosBean.lerCapitulosTabela()\tdataCapitulosTabela: "
            //+ (dataCapitulosTabela == null ? "null"
            //: util.DataUtils.toStringFull(dataCapitulosTabela)));
            //if (this.ambCidCapitulosFacade.isCapitulosTabelaEmpty() || dataCapitulosTabela == null)
            if (dataCapitulosTabela == null)
                ;
            else if (dataCapituloXLSFile.compareTo(dataCapitulosTabela) <= 0)
            {
//System.err.println("6: AmbCidCapitulosBean.lerCapitulosTabela()");
                return false;
            }

            AmbCidCapitulos reg = null;
            boolean cabecalhoTabela = true;
            while (rows.hasNext())
            {
//System.err.println("5: AmbCidCapitulosBean.lerCapitulosTabela()");
                row = (HSSFRow) rows.next();
                if (cabecalhoTabela)
                {
                    // descartar linha de cabecalho da tabela
//System.err.println("6: AmbCidCapitulosBean.lerCapitulosTabela()");
                    cabecalhoTabela = false;
                    continue;
                }
//System.err.println("7: AmbCidCapitulosBean.lerCapitulosTabela()");
                reg = lerCampos(row);
                if (reg != null)
                {
                    if (nreg == 0)
                    {
                        System.err.println("Inicio da actualizacao da tabela \"amb_cid_capitulos\" do Cid-10");
                    }

//System.err.println("8: AmbCidCapitulosBean.lerCapitulosTabela()");
                    escreverCapitulosTabela(reg);
                    nreg++;
                }
                //System.err.println("12: AmbCidCapitulosBean.lerCapitulosTabela()");
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
//System.err.println("9: AmbCidCapitulosBean.lerCapitulosTabela()");            
        }
//System.err.println("10: AmbCidCapitulosBean.lerCapitulosTabela()");        
        return nreg != 0;
    }

    public void escreverCapitulosTabela(AmbCidCapitulos reg)
    {
        //System.err.println("0: AmbCidCapitulosBean.escreverCapitulosTabela()\tpkIdCapitulos: " + reg.getPkIdCapitulos());
        if (ambCidCapitulosFacade.existeRegisto(reg.getPkIdCapitulos()) == false)
        {
            //System.err.println("1: AmbCidCapitulosBean.escreverCapitulosTabela()");
            this.createRegister(reg);
        }
        else
        {
            //System.err.println("2: AmbCidCapitulosBean.escreverCapitulosTabela()");
            this.editRegister(reg);
        }
        //System.err.println("3: AmbCidCapitulosBean.escreverCapitulosTabela()");
    }

    public AmbCidCapitulos lerCampos(HSSFRow row)
    {
        final int PK_ID_CAPITULOS = 1;
        final int NOME = 2;

        Iterator cells = row.cellIterator();
        HSSFCell cell;
        int coluna = 1;
        AmbCidCapitulos reg = new AmbCidCapitulos();
//System.err.println("8: AmbCidCapitulosBean.carregarCapitulosTabela()");
        while (cells.hasNext())
        {
//System.err.println("9: AmbCidCapitulosBean.carregarCapitulosTabela()");
            cell = (HSSFCell) cells.next();

            if (coluna == PK_ID_CAPITULOS)
            {
                //System.err.print("\ncel[2, 1]: " + cell.getStringCellValue());
                reg.setPkIdCapitulos(cell.getStringCellValue().trim());
            }
            else if (coluna == NOME)
            {
                //System.err.print("\ncel[2, 2]: " + cell.getStringCellValue());
                reg.setNome(cell.getStringCellValue().trim());
            }
//System.err.println("10: AmbCidCapitulosBean.carregarCapitulosTabela()");
            coluna++;
        } // fim da leitura dos campos de uma linha
System.err.println("0: AmbCidCapitulosBean.lerCampos()\tpk_id_capitulos: " + reg.getPkIdCapitulos() + "\nnome: " + reg.getNome());
        return reg;
    }

    public boolean createRegister(AmbCidCapitulos reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidCapitulosFacade.create(reg);
            this.userTransaction.commit();
            //System.err.println("0: AmbCidCapitulosBean.createRegister()");
            return true;
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        //System.err.println("1: AmbCidCapitulosBean.createRegister()");
        return false;
    }

    public boolean editRegister(AmbCidCapitulos reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidCapitulosFacade.edit(reg);
            this.userTransaction.commit();
            return true;
        }
        catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }
}
