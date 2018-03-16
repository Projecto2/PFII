/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidAgrupamentos;
import entidade.AmbCidCapitulos;
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
import sessao.AmbCidAgrupamentosFacade;
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
public class AmbCidAgrupamentosBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AmbCidCapitulosFacade ambCidCapitulosFacade;

    @EJB
    private AmbCidUpdatesFacade ambCidUpdatesFacade;

    @EJB
    private AmbCidAgrupamentosFacade ambCidAgrupamentosFacade;
    private AmbCidAgrupamentos ambCidAgrupamentos;

    /**
     * Creates a new instance of AmbCidAgrupamentoManagedBean
     */
    public AmbCidAgrupamentosBean()
    {
    }

    public static AmbCidAgrupamentosBean getInstanciaBean()
    {
        return (AmbCidAgrupamentosBean) GeradorCodigo.getInstanciaBean("ambCidAgrupamentosBean");
    }

    public static AmbCidAgrupamentos getInstancia()
    {
        AmbCidAgrupamentos agrupamentos = new AmbCidAgrupamentos();
        agrupamentos.setFkIdCapitulos(new AmbCidCapitulos());
        return agrupamentos;
    }

    public AmbCidAgrupamentos getAmbCidAgrupamentos()
    {
        return ambCidAgrupamentos;
    }

    public void setAmbCidAgrupamentos(AmbCidAgrupamentos ambCidAgrupamentos)
    {
        this.ambCidAgrupamentos = ambCidAgrupamentos;
    }

    public String init()
    {
        ambCidAgrupamentos = new AmbCidAgrupamentos();
        return util.amb.Defs.AGRUPAMENTOS_URL;
    }

    private boolean validarAgrupamentos(String agrupamento, String idCapitulos)
    {
        for (AmbCidAgrupamentos ambCidAgrupamentosAux : ambCidAgrupamentosFacade.findAll())
        {
            if (ambCidAgrupamentosAux.getNome().equalsIgnoreCase(agrupamento)
                && ambCidAgrupamentosAux.getFkIdCapitulos().getPkIdCapitulos().equals(idCapitulos))
            {
                return true;
            }
        }
        return false;
    }

    public void gravar()
    {
        if (!validarAgrupamentos(ambCidAgrupamentos.getNome(), ambCidAgrupamentos.getFkIdCapitulos().getPkIdCapitulos()))
        {
            this.ambCidAgrupamentosFacade.create(ambCidAgrupamentos);
            ambCidAgrupamentos = new AmbCidAgrupamentos();
        }
    }

    public List<AmbCidAgrupamentos> findAllOrderByNome()
    {
        return ambCidAgrupamentosFacade.findAllOrderByNome();
    }

    public List<AmbCidAgrupamentos> findAllOrderByNome(String idCapitulos)
    {
        return this.ambCidAgrupamentosFacade.findAllByPkIdCapitulosOrderByNome(idCapitulos);
    }

    public List<AmbCidAgrupamentos> findAllOrderById()
    {
        return ambCidAgrupamentosFacade.findAllOrderById();
    }

    public List<AmbCidAgrupamentos> findAllOrderByNomeFromPerfilPreferencial(String perfilPreferencial, int idDoencasPrioridadesPreferencial, String pkIdCapitulos)
    {
        return ambCidAgrupamentosFacade.findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, pkIdCapitulos);
    }

    public void apagar(String idAgrupamento)
    {
        ambCidAgrupamentosFacade.remove(new AmbCidAgrupamentos(idAgrupamento));
    }

    public void carregarAgrupamentosTabela()
    {
//System.err.println("0: AmbCidAgrupamentosBean.carregarAgrupamentosTabela()\tambCidUpdatesFacade: "
        //+ (ambCidUpdatesFacade == null ? "null" : "not null"));
        
        if (lerAgrupamentosTabela())
        {
            //System.err.println("13: AmbCidAgrupamentosBean.carregarAgrupamentosTabela()");
            this.ambCidUpdatesFacade.escreverDataActualizacaoAgrupamentosTabela();
            System.err.println("Conclusao da actualizacao da tabela \"amb_cid_agrupamentos\" do Cid-10");
        }
        //System.err.println("14: AmbCidAgrupamentosBean.carregarAgrupamentosTabela()");

    }

public boolean lerAgrupamentosTabela()
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
//System.err.println("03: AmbCidAgrupamentosBean.lerAgrupamentosTabela()");
        int nreg = 0;
        try
        {
            //HSSFSheet sheet = FileManager.getSheetFromXLS_File(util.amb.Defs.FILE_AGRUPAMENTOS_AMB);
            HSSFSheet sheet = FileManager.getSheetFromXLS_File(util.amb.Defs.FILE_CID10_TABLES_AMB, util.amb.Defs.SHEET_AGRUPAMENTOS_AMB);
//System.err.println("3: AmbCidAgrupamentosBean.lerAgrupamentosTabela()\tsheet: " + (sheet == null ? "null" : "not null"));
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();
//System.err.println("4: AmbCidAgrupamentosBean.lerAgrupamentosTabela()");
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataAgrupamentoXLSFile = FileManager.lerVersaoTabela(row, util.amb.Defs.FILE_CID10_TABLES_AMB, util.amb.Defs.SHEET_AGRUPAMENTOS_AMB);

            Date dataAgrupamentosTabela = this.ambCidUpdatesFacade.dataAgrupamentosTabela();

            //System.err.println("01: AmbCidAgrupamentosBean.carregarAgrupamentosTabela()\tdataAgrupamentosTabela: "
            //+ (dataAgrupamentosTabela == null ? "null"
            //: util.DataUtils.toStringFull(dataAgrupamentosTabela)));
            //System.err.println("02: AmbCidAgrupamentosBean.carregarAgrupamentosTabela()\tdataAgrupamentoXLSFile: "
            //+ util.DataUtils.toStringFull(dataAgrupamentoXLSFile));
            if (dataAgrupamentosTabela == null)
                ;
            else if (dataAgrupamentoXLSFile.compareTo(dataAgrupamentosTabela) <= 0)
            {
                //System.err.println("03: AmbCidAgrupamentosBean.carregarAgrupamentosTabela()");
                return false;
            }

            boolean cabecalhoTabela = true;

            AmbCidAgrupamentos reg = null;
            while (rows.hasNext())
            {
  //System.err.println("5: AmbCidAgrupamentosBean.lerAgrupamentosTabela()");
                row = (HSSFRow) rows.next();
                if (cabecalhoTabela)
                {
                    // descartar linha de cabecalho da tabela
//System.err.println("6: AmbCidAgrupamentosBean.lerAgrupamentosTabela()");
                    cabecalhoTabela = false;
                    continue;
                }
//System.err.println("7: AmbCidAgrupamentosBean.lerAgrupamentosTabela()");
                reg = lerCampos(row);
                //System.err.println("11: AmbCidAgrupamentosBean.lerAgrupamentosTabela()");
                if (reg != null)
                {
                    if (nreg == 0)
                        System.err.println("Inicio da actualizacao da tabela \"amb_cid_agrupamentos\" do Cid-10");
                    escreverAgrupamentosTabela(reg);
                    nreg++;
                }
                //System.err.println("12: AmbCidAgrupamentosBean.lerAgrupamentosTabela()");
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public AmbCidAgrupamentos lerCampos(HSSFRow row)
    {
        String pk_id_agrupamentos
             , nome
             , fk_id_capitulos;
        
        final int PK_ID_AGRUPAMENTOS = 0;
        final int NOME = 1;
        final int FK_ID_CAPITULOS = 2;
        
        AmbCidAgrupamentos reg = new AmbCidAgrupamentos();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_AGRUPAMENTOS:
                     pk_id_agrupamentos = cell.getStringCellValue().trim();
                     reg.setPkIdAgrupamentos(pk_id_agrupamentos);
                break;
                    
                case NOME:
                     nome = cell.getStringCellValue().trim();
                     reg.setNome(nome); 
                break;
                    
                case FK_ID_CAPITULOS:
                     fk_id_capitulos = cell.getStringCellValue().trim();
                     reg.setFkIdCapitulos(new AmbCidCapitulos(fk_id_capitulos)); 
                break;
            }
        }
System.err.println("0: AmbCidAgrupamentosBean.lerCampos()\tpk_id_agrupamentos: " + reg.getPkIdAgrupamentos() + "\nnome: " + reg.getNome() + "\nfk_id_capitulos: " + reg.getFkIdCapitulos().getPkIdCapitulos());        
        return reg;
    }

    public void escreverAgrupamentosTabela(AmbCidAgrupamentos reg)
    {
        //System.err.println("0: AmbCidAgrupamentosBean.escreverAgrupamentosTabela()\tpkIdAgrupamentos: " + reg.getPkIdAgrupamentos());
        if (ambCidAgrupamentosFacade.existeRegisto(reg.getPkIdAgrupamentos()) == false)
        {
            //System.err.println("1: AmbCidAgrupamentosBean.escreverAgrupamentosTabela()");
            this.createRegister(reg);
        }
        else
        {
            //System.err.println("2: AmbCidAgrupamentosBean.escreverAgrupamentosTabela()");
            this.editRegister(reg);
        }
        //System.err.println("3: AmbCidAgrupamentosBean.escreverAgrupamentosTabela()");
    }

    public boolean createRegister(AmbCidAgrupamentos reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidAgrupamentosFacade.create(reg);
            this.userTransaction.commit();
            //System.err.println("0: AmbCidAgrupamentosBean.createRegister()");
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
        //System.err.println("1: AmbCidAgrupamentosBean.createRegister()");
        return false;
    }

    public boolean editRegister(AmbCidAgrupamentos reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidAgrupamentosFacade.edit(reg);
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
