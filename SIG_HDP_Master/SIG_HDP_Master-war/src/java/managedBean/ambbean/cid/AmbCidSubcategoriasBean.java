/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.cid;

import entidade.AmbCidCategorias;
import entidade.AmbCidPerfis;
import entidade.AmbCidSubcategorias;
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
import sessao.AmbCidCategoriasFacade;
import sessao.AmbCidPerfisDoencasFacade;
import sessao.AmbCidSubcategoriasFacade;
import sessao.AmbCidUpdatesFacade;
import util.GeradorCodigo;
import util.amb.FileManager;

/**
 *
 * @author aires
 */
@ManagedBean
@SessionScoped
public class AmbCidSubcategoriasBean implements Serializable
{

    @EJB
    private AmbCidPerfisDoencasFacade ambCidPerfisDoencasFacade;

    @EJB
    private AmbCidCategoriasFacade ambCidCategoriasFacade;

    @EJB
    private AmbCidUpdatesFacade ambCidUpdatesFacade;

    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade;

    @Resource
    private UserTransaction userTransaction;

    private AmbCidSubcategorias ambCidSubcategorias;

    /**
     * Creates a new instance of AmbCidSubcategoriaManagedBean
     */
    public AmbCidSubcategoriasBean()
    {
    }

    public static AmbCidSubcategoriasBean getInstanciaBean()
    {
        return (AmbCidSubcategoriasBean) GeradorCodigo.getInstanciaBean("ambCidSubcategoriasBean");
    }

    public static AmbCidSubcategorias getInstancia()
    {
        AmbCidSubcategorias subcategorias = new AmbCidSubcategorias();
        subcategorias.setFkIdCategorias(AmbCidCategoriasBean.getInstancia());
        return subcategorias;
    }

    public AmbCidSubcategorias getAmbCidSubcategorias()
    {
        return ambCidSubcategorias;
    }

    public void setAmbCidSubcategorias(AmbCidSubcategorias ambCidSubcategorias)
    {
        this.ambCidSubcategorias = ambCidSubcategorias;
    }

    public String init()
    {
        ambCidSubcategorias = AmbCidSubcategoriasBean.getInstancia();
        return util.amb.Defs.SUBCATEGORIAS_URL;
    }

    public List<AmbCidSubcategorias> findAllOrderByNomeFromPerfilPreferencial(String perfilPreferencial, int idDoencasPrioridadesPreferencial, String pkIdCategorias)
    {
        return ambCidSubcategoriasFacade.findAllOrderByNomeFromPerfilPreferencial(perfilPreferencial, idDoencasPrioridadesPreferencial, pkIdCategorias);
    }

    public List<AmbCidSubcategorias> findAllExceptHighestPriorityOrderByNome(AmbCidPerfis ambCidPerfis, String pkIdCategorias)
    {
        if (pkIdCategorias == null || pkIdCategorias.isEmpty())
        {
            return null;
        }
        List<AmbCidSubcategorias> list = findAllByFkIdCategoriasOrderByNome(pkIdCategorias);
        List<AmbCidSubcategorias> listHighestPriority = findAllHighestPriorityOrderByNome(ambCidPerfis, pkIdCategorias);
        if (listHighestPriority != null)
        {
            list.removeAll(listHighestPriority);
        }
        return list;
    }

    public List<AmbCidSubcategorias> findAllHighestPriorityOrderByNome(AmbCidPerfis ambCidPerfis, String pkIdCategorias)
    {
        /*
         obter list de subcategorias(pkIdCategorias) com prioridade 1 do proprio perfil
         acrescentar recursivamente list(pkIdCategorias) com prioridade 1 do perfis ascendentes
         */
        return this.ambCidPerfisDoencasFacade.findAllHighestPriorityOrderByNome(ambCidPerfis, pkIdCategorias);
    }

    public List<AmbCidSubcategorias> findAllByFkIdCategoriasOrderByNome(String pkIdCategorias)
    {
        return this.ambCidSubcategoriasFacade.findAllByFkIdCategoriasOrderByNome(pkIdCategorias);
    }

    public boolean validarSubcategoria(String nomeSubcategorias, AmbCidCategorias ambCidCategorias)
    {
        for (AmbCidSubcategorias ambCidSubcategoriasAux : ambCidSubcategoriasFacade.findAll())
        {
            if (ambCidSubcategoriasAux.getNome().equalsIgnoreCase(nomeSubcategorias))
            {
                if (ambCidSubcategoriasAux.getFkIdCategorias().getPkIdCategorias().equals(ambCidCategorias.getPkIdCategorias()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void gravar()
    {
        if (!validarSubcategoria(this.ambCidSubcategorias.getNome(), this.ambCidSubcategorias.getFkIdCategorias()))
        {
            this.ambCidSubcategoriasFacade.create(ambCidSubcategorias);
            ambCidSubcategorias = new AmbCidSubcategorias();
        }
    }

    public List<AmbCidSubcategorias> findAllOrderByNome()
    {
        return ambCidSubcategoriasFacade.findAllOrderByNome();
    }

    public List<AmbCidSubcategorias> findAllOrderById()
    {
        return ambCidSubcategoriasFacade.findAllOrderById();
    }

    public void apagar(String idSubcategoria)
    {
        ambCidSubcategoriasFacade.remove(new AmbCidSubcategorias(idSubcategoria));
    }

    public void carregarSubcategoriasTabela()
    {
        // 88888888
        if (lerSubcategoriasTabela())
        {
            //System.err.println("13: AmbCidSubcategoriasBean.carregarSubcategoriasTabela()");
            this.ambCidUpdatesFacade.escreverDataActualizacaoSubcategoriasTabela();
            System.err.println("Conclusao da actualizacao da tabela \"amb_cid_subcategorias\" do Cid-10");
        }
        //System.err.println("14: AmbCidSubcategoriasBean.carregarSubcategoriasTabela()");

    }

    public boolean lerSubcategoriasTabela()
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
        //System.err.println("03: AmbCidSubcategoriasBean.lerSubcategoriasTabela()");

        int nreg = 0;
        try
        {
            HSSFSheet sheet = FileManager.getSheetFromXLS_File(util.amb.Defs.FILE_CID10_TABLES_AMB, util.amb.Defs.SHEET_SUBCATEGORIAS_AMB);
            //System.err.println("3: AmbCidSubcategoriasBean.lerSubcategoriasTabela()");
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();
            //System.err.println("4: AmbCidSubcategoriasBean.lerSubcategoriasTabela()");
            // ver na primeira linha da tabela, a versao do ficheiro (versao=aaaa-mm-dd hh:mm)
            row = (HSSFRow) rows.next();
            Date dataSubcategoriaXLSFile = FileManager.lerVersaoTabela(row, util.amb.Defs.FILE_CID10_TABLES_AMB, util.amb.Defs.SHEET_SUBCATEGORIAS_AMB);

            Date dataSubcategoriasTabela = this.ambCidUpdatesFacade.dataSubcategoriasTabela();

        //System.err.println("01: AmbCidSubategoriasBean.carregarCategoriasTabela()\tdataSubcategoriasTabela: "
            // + (dataSubcategoriasTabela == null ? "null"
            // : util.DataUtils.toStringFull(dataSubcategoriasTabela)));
            //System.err.println("02: AmbCidSubategoriasBean.carregarCategoriasTabela()\tdataSubcategoriaXLSFile: "
            //  + util.DataUtils.toStringFull(dataSubcategoriaXLSFile));
            if (dataSubcategoriasTabela == null)
                ;
            else if (dataSubcategoriaXLSFile.compareTo(dataSubcategoriasTabela) <= 0)
            {
                //System.err.println("03: AmbCidSubcategoriasBean.carregarSubcategoriasTabela()");
                return false;
            }
            //this.ambCidSubcategoriasFacade.apagarTodosRegistosTabelaAmbCidSubcategorias();
            boolean cabecalhoTabela = true;

            AmbCidSubcategorias reg = null;
            while (rows.hasNext())
            {
                //System.err.println("5: AmbCidSubcategoriasBean.lerSubcategoriasTabela()");
                row = (HSSFRow) rows.next();
                if (cabecalhoTabela)
                {
                    // descartar linha de cabecalho da tabela
                    //System.err.println("6: AmbCidSubcategoriasBean.lerSubcategoriasTabela()");
                    cabecalhoTabela = false;
                    continue;
                }
                //System.err.println("7: AmbCidSubcategoriasBean.lerSubcategoriasTabela()");
                reg = lerCampos(row);
                //System.err.println("11: AmbCidSubcategoriasBean.lerSubcategoriasTabela()");
                if (reg != null)
                {
                    if (nreg == 0)
                        System.err.println("Inicio da actualizacao da tabela \"amb_cid_subcategorias\" do Cid-10");
                    escreverSubcategoriasTabela(reg);
                    nreg++;
                }
                //System.err.println("12: AmbCidSubcategoriasBean.lerSubcategoriasTabela()");
            } // fim da leitura de cada linha 
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return nreg != 0;
    }

    public AmbCidSubcategorias lerCampos(HSSFRow row)
    {
        String pk_id_subcategorias
             , nome
             , fk_id_categorias;
        
        final int PK_ID_SUBCATEGORIAS = 0;
        final int NOME = 1;
        final int FK_ID_CATEGORIAS = 2;
        
        AmbCidSubcategorias reg = new AmbCidSubcategorias();
        
        for(int cn = 0; cn < row.getLastCellNum(); cn++)
        {
            HSSFCell cell = row.getCell(cn, row.RETURN_BLANK_AS_NULL);
            
            switch (cn) 
            {
                case PK_ID_SUBCATEGORIAS:
//                     pk_id_subcategorias = cell.getStringCellValue().trim();
                     pk_id_subcategorias = processarCodigo(cell.getStringCellValue().trim());
                     if (pk_id_subcategorias == null)
                     {
                         return null;
                     }
                     reg.setPkIdSubcategorias(pk_id_subcategorias);
                break;
                    
                case NOME:
                     nome = cell.getStringCellValue().trim();
                     reg.setNome(nome); 
                break;
                    
                case FK_ID_CATEGORIAS:
                     fk_id_categorias = cell.getStringCellValue().trim();
                     reg.setFkIdCategorias(new AmbCidCategorias(fk_id_categorias)); 
                break;
            }
        }
System.err.println("0: AmbCidSubcategorias.lerCampos()\tpk_id_subcategorias: " + reg.getPkIdSubcategorias() + "\nnome: " + reg.getNome() + "\nfk_id_categorias: " + reg.getFkIdCategorias().getPkIdCategorias());        
        return reg;
    }
    
    public String processarCodigo(String pk_id_subcategorias)
    {
        /*
         se tiver 3 caracteres
         retornar
         se tiver 4 caracteres
         inserir o caracter '.' depois do terceiro caracter
         retornar
         se tiver 5 caracteres
         verificar se tem o ponto na quarta posicao
         retornar
         senao
         retornar null
         */
        int length = pk_id_subcategorias.length();
        if (length == 3)
        {
            return pk_id_subcategorias;
        }
        if (length == 4)
        {
            return pk_id_subcategorias.substring(0, 3) + "." + pk_id_subcategorias.substring(3, 4);
        }
        if (length == 5)
        {
            if (pk_id_subcategorias.charAt(3) != '.')
            {
                return null;
            }
            return pk_id_subcategorias;
        }
        if (length == 7)
        {
            if (pk_id_subcategorias.charAt(5) != '/')
            {
                return null;
            }
            return pk_id_subcategorias;
        }
        return null;
    }

    public void escreverSubcategoriasTabela(AmbCidSubcategorias reg)
    {
        //System.err.println("0: AmbCidSubcategoriasBean.escreverSubcategoriasTabela()\tpkIdSubcategorias: " + reg.getPkIdSubcategorias());
        if (ambCidSubcategoriasFacade.existeRegisto(reg.getPkIdSubcategorias()) == false)
        {
            //System.err.println("1: AmbCidSubcategoriasBean.escreverSubcategoriasTabela()");
            this.createRegister(reg);
        }
        else
        {
            //System.err.println("2: AmbCidSubcategoriasBean.escreverSubcategoriasTabela()");
            this.editRegister(reg);
        }
        //System.err.println("3: AmbCidSubcategoriasBean.escreverSubcategoriasTabela()");
    }

    public boolean createRegister(AmbCidSubcategorias reg)
    {
        try
        {
            this.userTransaction.begin();
//System.err.println("0: AmbCidSubcategoriasBean.createRegister()\treg: " + reg.getPkIdSubcategorias() + " - " + reg.getNome());            
            this.ambCidSubcategoriasFacade.create(reg);
            this.userTransaction.commit();
            //System.err.println("0: AmbCidSubcategoriasBean.createRegister()");
            return true;
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                //System.err.println(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                //System.err.println("Roolback: " + ex.toString());
            }
        }
        //System.err.println("1: AmbCidSubcategoriasBean.createRegister()");
        return false;
    }

    public boolean editRegister(AmbCidSubcategorias reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambCidSubcategoriasFacade.edit(reg);
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
