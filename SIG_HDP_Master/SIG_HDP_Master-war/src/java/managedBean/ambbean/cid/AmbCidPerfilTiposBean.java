/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidPerfilTipos;
import entidade.AmbCidPerfilTipos;
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
import sessao.AmbCidPerfilTiposFacade;
import sessao.AmbCidUpdatesFacade;
import util.GeradorCodigo;
import util.amb.FileManager;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidPerfilTiposBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AmbCidUpdatesFacade ambCidUpdatesFacade;

    @EJB
    private AmbCidPerfilTiposFacade ambCidPerfilTiposFacade;

    /**
     * Creates a new instance of AmbCidPerfilTiposBean
     */
    public AmbCidPerfilTiposBean()
    {
    }

    public static AmbCidPerfilTiposBean getInstanciaBean()
    {
        return (AmbCidPerfilTiposBean) GeradorCodigo.getInstanciaBean("ambCidPerfilTiposBean");
    }

    public List<AmbCidPerfilTipos> findAllOrderByNome()
    {
        return ambCidPerfilTiposFacade.findAllOrderByNome();
    }

    public void carregarPerfilTiposTabela()
    {
        //System.err.println("0: AmbCidPerfilTiposBean.carregarPerfilTiposTabela()\tambCidUpdatesFacade: "
        //  + (ambCidUpdatesFacade == null ? "null" : "not null"));

        if (lerPerfilTiposTabela())
        {
            //System.err.println("13: AmbCidPerfilTiposBean.carregarPerfilTiposTabela()");
            this.ambCidUpdatesFacade.escreverDataActualizacaoPerfilTiposTabela();
            //System.err.println("Conclusao da actualizacao da tabela \"amb_cid_perfil_tipos\" do Cid-10");
        }
        //System.err.println("14: AmbCidPerfilTiposBean.carregarPerfilTiposTabela()");
    }

    public boolean lerPerfilTiposTabela()
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
//System.err.println("0: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");
        int nreg = 0;
        //System.err.println("03: AmbCidPerfilTiposBean.carregarPerfilTiposTabela()");
        try
        {
            // HSSFWorkbook wb = FileManager.createHSSFWorkbook(util.amb.Defs.FILE_PERFIL_TIPOS_AMB);
//System.err.println("1: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");

           // HSSFSheet sheet = wb.getSheet("perfil_tipos");
            HSSFSheet sheet = FileManager.getSheetFromXLS_File(util.amb.Defs.FILE_PERFIL_TIPOS_AMB);
//System.err.println("2: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();

//System.err.println("3: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataPerfilTiposXLSFile = FileManager.lerVersaoTabela(row, util.amb.Defs.FILE_PERFIL_TIPOS_AMB, null);
//System.err.println("4: AmbCidPerfilTiposBean.lerPerfilTiposTabela()\tdataPerfilTiposXLSFile: "
            //    + util.DataUtils.toStringFull(dataPerfilTiposXLSFile));

            Date dataPerfilTiposTabela = this.ambCidUpdatesFacade.dataPerfilTiposTabela();

//System.err.println("5: AmbCidPerfilTiposBean.lerPerfilTiposTabela()\tdataPerfilTiposTabela: "
            //    + (dataPerfilTiposTabela == null ? "null"
               // : util.DataUtils.toStringFull(dataPerfilTiposTabela)));

            if (dataPerfilTiposTabela == null)
                ;
            else if (dataPerfilTiposXLSFile.compareTo(dataPerfilTiposTabela) <= 0)
            {
//System.err.println("6: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");
                return false;
            }

            AmbCidPerfilTipos reg = null;
            boolean cabecalhoTabela = true;
            while (rows.hasNext())
            {
//System.err.println("7: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");
                row = (HSSFRow) rows.next();
                if (cabecalhoTabela)
                {
                    // descartar linha de cabecalho da tabela
//System.err.println("8: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");
                    cabecalhoTabela = false;
                    continue;
                }
//System.err.println("9: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");
                reg = lerCampos(row);
//System.err.println("10: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");
                escreverPerfilTiposTabela(reg);
                nreg++;
                //System.err.println("12: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
//System.err.println("11: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");            
        }
//System.err.println("12: AmbCidPerfilTiposBean.lerPerfilTiposTabela()");        
        return nreg != 0;
    }

    public void escreverPerfilTiposTabela(AmbCidPerfilTipos reg)
    {
        //System.err.println("0: AmbCidPerfilTiposBean.escreverPerfilTiposTabela()\tpkIdPerfilTipos: " + reg.getPkIdPerfilTipos());
        //if (ambCidPerfilTiposFacade.existeRegisto(reg.getPkIdTipos()) == false)
        if (ambCidPerfilTiposFacade.find(reg.getPkIdTipos()) == null)
        {
            //System.err.println("1: AmbCidPerfilTiposBean.escreverPerfilTiposTabela()");
            this.createRegister(reg);
        }
        else
        {
            //System.err.println("2: AmbCidPerfilTiposBean.escreverPerfilTiposTabela()");
            this.editRegister(reg);
        }
        //System.err.println("3: AmbCidPerfilTiposBean.escreverPerfilTiposTabela()");
    }

    public AmbCidPerfilTipos lerCampos(HSSFRow row)
    {

        Iterator cells = row.cellIterator();
        HSSFCell cell;
        int coluna = 1;
        AmbCidPerfilTipos reg = new AmbCidPerfilTipos();

        //System.err.println("0: AmbCidPerfilTiposBean.lerCampos()");
        cell = (HSSFCell) cells.next();
        reg.setPkIdTipos((int) cell.getNumericCellValue());
        //System.err.print("\ncel[0]: " + cell.getNumericCellValue());

        cell = (HSSFCell) cells.next();
        //System.err.print("\ncel[1]: " + cell.getStringCellValue());
        reg.setNome(cell.getStringCellValue().trim());
        //System.err.println("2: AmbCidPerfilTiposBean.lerCampos()");
        return reg;
    }

    public boolean createRegister(AmbCidPerfilTipos reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidPerfilTiposFacade.create(reg);
            this.userTransaction.commit();
            //System.err.println("0: AmbCidPerfilTiposBean.createRegister()");
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
        //System.err.println("1: AmbCidPerfilTiposBean.createRegister()");
        return false;
    }

    public boolean editRegister(AmbCidPerfilTipos reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidPerfilTiposFacade.edit(reg);
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
