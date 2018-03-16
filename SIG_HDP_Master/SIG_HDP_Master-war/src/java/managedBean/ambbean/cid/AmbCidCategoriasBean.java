/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidAgrupamentos;
import entidade.AmbCidCategorias;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import sessao.AmbCidAgrupamentosFacade;
import sessao.AmbCidCategoriasFacade;
import sessao.AmbCidUpdatesFacade;
import util.GeradorCodigo;
import util.amb.FileManager;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidCategoriasBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AmbCidAgrupamentosFacade ambCidAgrupamentosFacade;

    @EJB
    private AmbCidUpdatesFacade ambCidUpdatesFacade;

    @EJB
    private AmbCidCategoriasFacade ambCidCategoriasFacade;

    private AmbCidCategorias ambCidCategorias;

    /**
     * Creates a new instance of AmbCidCategoriaManagedBean
     */
    public AmbCidCategoriasBean()
    {
    }

    public static AmbCidCategoriasBean getInstanciaBean()
    {
        return (AmbCidCategoriasBean) GeradorCodigo.getInstanciaBean("ambCidCategoriasBean");
    }

    public static AmbCidCategorias getInstancia()
    {
        AmbCidCategorias categorias = new AmbCidCategorias();
        categorias.setFkIdAgrupamentos(AmbCidAgrupamentosBean.getInstancia());
        return categorias;
    }

    public String init()
    {
        ambCidCategorias = new AmbCidCategorias();
        return util.amb.Defs.CATEGORIAS_URL;
    }

    public AmbCidCategorias getAmbCidCategorias()
    {
        return ambCidCategorias;
    }

    public void setAmbCidCategorias(AmbCidCategorias ambCidCategorias)
    {
        this.ambCidCategorias = ambCidCategorias;
    }

    public boolean validarCategorias(String nomeCategorias, AmbCidAgrupamentos ambCidAgrupamentos)
    {
        for (AmbCidCategorias ambCidCategoriasAux : ambCidCategoriasFacade.findAll())
        {
            if (ambCidCategoriasAux.getNome().equalsIgnoreCase(nomeCategorias))
            {
                if (ambCidCategoriasAux.getFkIdAgrupamentos().getPkIdAgrupamentos().equals(ambCidAgrupamentos.getPkIdAgrupamentos()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void gravar()
    {
        if (!validarCategorias(this.ambCidCategorias.getNome(), this.ambCidCategorias.getFkIdAgrupamentos()))
        {
            this.ambCidCategoriasFacade.create(ambCidCategorias);
            ambCidCategorias = new AmbCidCategorias();
        }
    }

    public List<AmbCidCategorias> findAllOrderByNomeFromPerfilPreferencial(String perfilPreferencial, int idDoencasPrioridadesPreferencial, String pkIdAgrupamentos)
    {
        return ambCidCategoriasFacade.findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, pkIdAgrupamentos);
    }

    public List<AmbCidCategorias> findAllByPkIdAgrupamentosOrderByNome(String idAgrupamentos)
    {
        return ambCidCategoriasFacade.findAllByPkIdAgrupamentosOrderByNome(idAgrupamentos);
    }

    public List<AmbCidCategorias> findAllOrderByNome()
    {
        return ambCidCategoriasFacade.findAllOrderByNome();
    }

    public List<AmbCidCategorias> findAllOrderByPkIdCategorias()
    {
        return ambCidCategoriasFacade.findAllOrderByPkIdCategorias();
    }

    public void apagar(String idCategorias)
    {
        ambCidCategoriasFacade.remove(new AmbCidCategorias(idCategorias));
    }

    public void carregarCategoriasTabela()
    {
//System.err.println("0: AmbCidCategoriasBean.carregarCategoriasTabela()\tambCidUpdatesFacade: "
 
        if (lerCategoriasTabela())
        {
            //System.err.println("13: AmbCidCategoriasBean.carregarCategoriasTabela()");
            this.ambCidUpdatesFacade.escreverDataActualizacaoCategoriasTabela();
            System.err.println("Conclusao da actualizacao da tabela \"amb_cid_categorias\" do Cid-10");
        }
        //System.err.println("14: AmbCidCategoriasBean.carregarCategoriasTabela()");

    }

    public boolean lerCategoriasTabela()
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
        //System.err.println("03: AmbCidCategoriasBean.lerCategoriasTabela()");

        int nreg = 0;
        try
        {
            HSSFSheet sheet = FileManager.getSheetFromXLS_File(util.amb.Defs.FILE_CID10_TABLES_AMB, util.amb.Defs.SHEET_CATEGORIAS_AMB);
            //System.err.println("3: AmbCidCategoriasBean.lerCategoriasTabela()");
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();
//System.err.println("4: AmbCidCategoriasBean.lerCategoriasTabela()");
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataCategoriaXLSFile = FileManager.lerVersaoTabela(row, util.amb.Defs.FILE_CID10_TABLES_AMB, util.amb.Defs.SHEET_CATEGORIAS_AMB);

            Date dataCategoriasTabela = this.ambCidUpdatesFacade.dataCategoriasTabela();
        //System.err.println("02: AmbCidCategoriasBean.carregarCategoriasTabela()\tdataCategoriaXLSFile: "
            //   + util.DataUtils.toStringFull(dataCategoriaXLSFile));
            if (dataCategoriasTabela == null)
            ;
            else if (dataCategoriaXLSFile.compareTo(dataCategoriasTabela) <= 0)
            {
                //System.err.println("03: AmbCidCategoriasBean.carregarCategoriasTabela()");
                return false;
            }
            //this.ambCidCategoriasFacade.apagarTodosRegistosTabelaAmbCidCategorias();
            boolean cabecalhoTabela = true;

            AmbCidCategorias reg = null;
            while (rows.hasNext())
            {
//System.err.println("5: AmbCidCategoriasBean.lerCategoriasTabela()");
                row = (HSSFRow) rows.next();
                if (cabecalhoTabela)
                {
                    // descartar linha de cabecalho da tabela
//System.err.println("6: AmbCidCategoriasBean.lerCategoriasTabela()");
                    cabecalhoTabela = false;
                    continue;
                }
//System.err.println("7: AmbCidCategoriasBean.lerCategoriasTabela()");
                reg = lerCampos(row);
                //System.err.println("11: AmbCidCategoriasBean.lerCategoriasTabela()");
                if (reg != null)
                {
                    if (nreg == 0)
                        System.err.println("Inicio da actualizacao da tabela \"amb_cid_categorias\" do Cid-10");
                    escreverCategoriasTabela(reg);
                    nreg++;
                }
                //System.err.println("12: AmbCidCategoriasBean.lerCategoriasTabela()");
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public AmbCidCategorias lerCampos(HSSFRow row)
    {
        String pk_id_categorias
             , nome
             , fk_id_agrupamentos;
        
        final int PK_ID_CATEGORIAS = 0;
        final int NOME = 1;
        final int FK_ID_AGRUPAMENTOS = 2;
        
        AmbCidCategorias reg = new AmbCidCategorias();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_CATEGORIAS:
                     pk_id_categorias = cell.getStringCellValue().trim();
                     reg.setPkIdCategorias(pk_id_categorias);
                break;
                    
                case NOME:
                     nome = cell.getStringCellValue().trim();
                     reg.setNome(nome); 
                break;
                    
                case FK_ID_AGRUPAMENTOS:
                     fk_id_agrupamentos = cell.getStringCellValue().trim();
                     if (fk_id_agrupamentos == null)
                     {
                         return null;
                     }
                     reg.setFkIdAgrupamentos(new AmbCidAgrupamentos(fk_id_agrupamentos)); 
                break;
            }
        }
System.err.println("0: AmbCidCategoriasBean.lerCampos()\tpk_id_categorias: " + reg.getPkIdCategorias() + "\nnome: " + reg.getNome() + "\nfk_id_agrupamentos: " + reg.getFkIdAgrupamentos().getPkIdAgrupamentos());        
        return reg;
    }
    
    public void escreverCategoriasTabela(AmbCidCategorias reg)
    {
        System.err.println("0: AmbCidCategoriasBean.escreverCategoriasTabela()\tpkIdCategorias: " + reg.getPkIdCategorias());
        if (ambCidCategoriasFacade.existeRegisto(reg.getPkIdCategorias()) == false)
        {
            System.err.println("1: AmbCidCategoriasBean.escreverCategoriasTabela()");
            this.createRegister(reg);
        }
        else
        {
            System.err.println("2: AmbCidCategoriasBean.escreverCategoriasTabela()");
            this.editRegister(reg);
        }
        System.err.println("3: AmbCidCategoriasBean.escreverCategoriasTabela()");
    }

    public boolean createRegister(AmbCidCategorias reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidCategoriasFacade.create(reg);
            this.userTransaction.commit();
            System.err.println("0: AmbCidCategoriasBean.createRegister()");
            return true;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                userTransaction.rollback();
                System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                ex.printStackTrace();
                System.err.println("Roolback: " + ex.toString());
            }
        }
        System.err.println("1: AmbCidCategoriasBean.createRegister()");
        return false;
    }

    public boolean editRegister(AmbCidCategorias reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidCategoriasFacade.edit(reg);
            this.userTransaction.commit();
            return true;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                this.userTransaction.rollback();
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                ex.printStackTrace();
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        return false;
    }

}
