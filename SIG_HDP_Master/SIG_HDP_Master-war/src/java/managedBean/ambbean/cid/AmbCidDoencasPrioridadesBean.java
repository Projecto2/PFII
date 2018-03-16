/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;


import entidade.AmbCidDoencasPrioridades;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import sessao.AmbCidDoencasPrioridadesFacade;
import sessao.AmbCidUpdatesFacade;
import util.GeradorCodigo;
import util.amb.FileManager;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidDoencasPrioridadesBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AmbCidUpdatesFacade ambCidUpdatesFacade;

    @EJB
    private AmbCidDoencasPrioridadesFacade ambCidDoencasPrioridadesFacade;

    /**
     * Creates a new instance of AmbCidPerfilPrioridadesBean
     */
    public AmbCidDoencasPrioridadesBean()
    {
    }

    public static AmbCidDoencasPrioridadesBean getInstanciaBean()
    {
        return (AmbCidDoencasPrioridadesBean) GeradorCodigo.getInstanciaBean("ambCidDoencasPrioridadesBean");
    }
    
    public static AmbCidDoencasPrioridades getInstancia()
    {
        return new AmbCidDoencasPrioridades();
    }

    public List<AmbCidDoencasPrioridades> findAll()
    {
        //System.err.println("0: AmbCidPerfilDoencasBean.findAllByDoencas()");
        return this.ambCidDoencasPrioridadesFacade.findAllOrderBYpkIdDoencasPrioridades();
    }

    public List<AmbCidDoencasPrioridades> findAllFromPerfilPreferencial(String perfilPreferencial)
    {
        //System.err.println("0: AmbCidPerfilDoencasBean.findAllByDoencas()");
        return this.ambCidDoencasPrioridadesFacade.findAllOrderBYpkIdDoencasPrioridadesFromPerfilPreferencial(perfilPreferencial);
    }
    
    public void carregarDoencasPrioridadesTabela()
    {
        //System.err.println("0: AmbCidDoencasPrioridadesBean.carregarDoencasPrioridadesTabela()\tambCidUpdatesFacade: "
        //  + (ambCidUpdatesFacade == null ? "null" : "not null"));

        if (lerDoencasPrioridadesTabela())
        {
            //System.err.println("13: AmbCidDoencasPrioridadesBean.carregarDoencasPrioridadesTabela()");
            this.ambCidUpdatesFacade.escreverDataActualizacaoDoencasPrioridadesTabela();
            //System.err.println("Conclusao da actualizacao da tabela \"amb_cid_doencas_prioridades\" do Cid-10");
        }
        //System.err.println("14: AmbCidDoencasPrioridadesBean.carregarDoencasPrioridadesTabela()");
    }

    public boolean lerDoencasPrioridadesTabela()
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
//System.err.println("0: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");
        int nreg = 0;
        //System.err.println("03: AmbCidDoencasPrioridadesBean.carregarDoencasPrioridadesTabela()");
        try
        {
           // HSSFWorkbook wb = FileManager.createHSSFWorkbook(util.amb.Defs.FILE_DOENCAS_PRIORIDADES_AMB);
//System.err.println("1: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");

            //HSSFSheet sheet = wb.getSheet("doencas_prioridades");
            HSSFSheet sheet = FileManager.getSheetFromXLS_File(util.amb.Defs.FILE_DOENCAS_PRIORIDADES_AMB);
//System.err.println("2: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();

//System.err.println("3: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataDoencasPrioridadesXLSFile = FileManager.lerVersaoTabela(row, util.amb.Defs.FILE_DOENCAS_PRIORIDADES_AMB, null);
//System.err.println("4: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()\tdataDoencasPrioridadesXLSFile: "
              //  + util.DataUtils.toStringFull(dataDoencasPrioridadesXLSFile));

            Date dataDoencasPrioridadesTabela = this.ambCidUpdatesFacade.dataDoencasPrioridadesTabela();

//System.err.println("5: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()\tdataDoencasPrioridadesTabela: "
                //+ (dataDoencasPrioridadesTabela == null ? "null"
                //: util.DataUtils.toStringFull(dataDoencasPrioridadesTabela)));

            if (dataDoencasPrioridadesTabela == null)
                ;
            else if (dataDoencasPrioridadesXLSFile.compareTo(dataDoencasPrioridadesTabela) <= 0)
            {
//System.err.println("6: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");
                return false;
            }

            AmbCidDoencasPrioridades reg = null;
            boolean cabecalhoTabela = true;
            while (rows.hasNext())
            {
//System.err.println("7: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");
                row = (HSSFRow) rows.next();
                if (cabecalhoTabela)
                {
                    // descartar linha de cabecalho da tabela
//System.err.println("8: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");
                    cabecalhoTabela = false;
                    continue;
                }
//System.err.println("9: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");
                reg = lerCampos(row);
//System.err.println("10: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");
                escreverDoencasPrioridadesTabela(reg);
                nreg++;
                //System.err.println("12: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
//System.err.println("11: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");            
        }
//System.err.println("12: AmbCidDoencasPrioridadesBean.lerDoencasPrioridadesTabela()");        
        return nreg != 0;
    }

    public void escreverDoencasPrioridadesTabela(AmbCidDoencasPrioridades reg)
    {
        //System.err.println("0: AmbCidDoencasPrioridadesBean.escreverDoencasPrioridadesTabela()\tpkIdDoencasPrioridades: " + reg.getPkIdDoencasPrioridades());
        //if (ambCidDoencasPrioridadesFacade.existeRegisto(reg.getPkIdTipos()) == false)
        if (ambCidDoencasPrioridadesFacade.find(reg.getPkIdDoencasPrioridades()) == null)
        {
            //System.err.println("1: AmbCidDoencasPrioridadesBean.escreverDoencasPrioridadesTabela()");
            this.createRegister(reg);
        }
        else
        {
            //System.err.println("2: AmbCidDoencasPrioridadesBean.escreverDoencasPrioridadesTabela()");
            this.editRegister(reg);
        }
        //System.err.println("3: AmbCidDoencasPrioridadesBean.escreverDoencasPrioridadesTabela()");
    }

    public AmbCidDoencasPrioridades lerCampos(HSSFRow row)
    {

        Iterator cells = row.cellIterator();
        HSSFCell cell;
        int coluna = 1;
        AmbCidDoencasPrioridades reg = new AmbCidDoencasPrioridades();

        //System.err.println("0: AmbCidDoencasPrioridadesBean.lerCampos()");
        cell = (HSSFCell) cells.next();
        reg.setPkIdDoencasPrioridades((int) cell.getNumericCellValue());
        //System.err.print("\ncel[0]: " + cell.getNumericCellValue());

        cell = (HSSFCell) cells.next();
        //System.err.print("\ncel[1]: " + cell.getStringCellValue());
        reg.setDescricao(cell.getStringCellValue().trim());
        //System.err.println("2: AmbCidDoencasPrioridadesBean.lerCampos()");
        return reg;
    }

    public boolean createRegister(AmbCidDoencasPrioridades reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidDoencasPrioridadesFacade.create(reg);
            this.userTransaction.commit();
            //System.err.println("0: AmbCidDoencasPrioridadesBean.createRegister()");
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
        //System.err.println("1: AmbCidDoencasPrioridadesBean.createRegister()");
        return false;
    }

    public boolean editRegister(AmbCidDoencasPrioridades reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidDoencasPrioridadesFacade.edit(reg);
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
