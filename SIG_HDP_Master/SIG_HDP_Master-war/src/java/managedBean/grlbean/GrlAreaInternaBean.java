/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean;

import entidade.GrlAreaInterna;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.UserTransaction;
import managedBean.grlbean.carregamentoExcel.GrlAreaInternaExcelBean;
import sessao.GrlAreaInternaFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlAreaInternaBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlAreaInternaFacade areaInternaFacade;

    /**
     *
     * Entidades
     */
    private GrlAreaInterna areaInterna,

    /**
     * Entidades
     */
    areaInternaPesquisa,

    /**
     * Entidades
     */
    areaInternaVisualizar;
    
    private List<GrlAreaInterna> grlAreaInternaLista;
    
//    private boolean pesquisar = false;

    /**
     * Creates a new instance of AreaInternaBean
     */
    public GrlAreaInternaBean ()
    {
    }

    public GrlAreaInterna getAreaInterna()
    {
        if (this.areaInterna == null)
        {
            this.areaInterna = getInstanciaAreaInterna();
        }

        return areaInterna;
    }

    public void setAreaInterna (GrlAreaInterna areaInterna)
    {
        this.areaInterna = areaInterna;
    }

    public GrlAreaInterna getAreaInternaPesquisa()
    {
        if (this.areaInternaPesquisa == null)
        {
            this.areaInternaPesquisa = getInstanciaAreaInterna();
        }

        return areaInternaPesquisa;
    }

    public void setAreaInternaPesquisa(GrlAreaInterna areaInternaPesquisa)
    {
        this.areaInternaPesquisa = areaInternaPesquisa;
    }

    
    public void pesquisar()
    {        
        if (grlAreaInternaLista == null)
        {
            grlAreaInternaLista = new ArrayList<>();
        }
        
        grlAreaInternaLista = areaInternaFacade.findAreaInterna(areaInternaPesquisa);
        
        if(grlAreaInternaLista.isEmpty())
            Mensagem.warnMsg("Nenhuma Área Encontrada");
    }

    public GrlAreaInterna getAreaInternaVisualizar()
    {
        return areaInternaVisualizar;
    }

    public void setAreaInternaVisualizar(GrlAreaInterna areaInternaVisualizar)
    {
        this.areaInternaVisualizar = areaInternaVisualizar;
    }
    

    public GrlAreaInterna getInstanciaAreaInterna()
    {
        GrlAreaInterna areaInternaInstancia = new GrlAreaInterna();
        return areaInternaInstancia;
    }
    
    public void carregar()
    {
        GrlAreaInternaExcelBean.getInstanciaBean().carregarAreaInternaTabela();
        
        areaInternaPesquisa = null;
        
        grlAreaInternaLista = areaInternaFacade.findAll();
        if(grlAreaInternaLista.isEmpty())
            Mensagem.warnMsg("Nenhuma area Encontrado");
        else{
            Mensagem.sucessoMsg("retornou");
        }
    }
    
    
//    public void gravarAreaPesquisar()
//    {
//        create();
//        pesquisar();
//    }
    

//    public void create()
//    {
//        try
//        {
//            userTransaction.begin();
//            areaInternaFacade.create(areaInterna);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("AreaInterna guardado com sucesso!");
//        }
//        catch (Exception e)
//        {
//            try
//            {
//                userTransaction.rollback();
//                Mensagem.erroMsg(e.toString());
//            }
//            catch (IllegalStateException | SecurityException | SystemException ex)
//            {
//                Mensagem.erroMsg("Rollback: " + ex.toString());
//            }
//        }
//        areaInterna = null;
////        return null;
//    }
    
//    public void editarAreaPesquisar()
//    {
//        edit();
//        pesquisar();
//    }

//    public void edit()
//    {
//        try
//        {
//            userTransaction.begin();
//            if (areaInterna.getPkIdAreaInterna() == null)
//            {
//                throw new NullPointerException("PK -> NULL");
//            }
//            areaInternaFacade.edit(areaInterna);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("AreaInterna editado com sucesso!");
//        }
//        catch (Exception e)
//        {
//            try
//            {
//                userTransaction.rollback();
//                Mensagem.erroMsg(e.toString());
//            }
//            catch (IllegalStateException | SecurityException | SystemException ex)
//            {
//                Mensagem.erroMsg("Rollback: " + ex.toString());
//            }
//        }
//
////        areaInterna = null;
//
////        return null;
//    }

    public List<GrlAreaInterna> findAll()
    {
        return areaInternaFacade.findAll();
    }

    public void limpar()
    {
        areaInterna = null;
    }
    
//    public List<GrlAreaInterna> pesquisarAreaInterna()
//    {
//        if(pesquisar)
//        {
//            return areaInternaFacade.findAreaInterna(areaInternaPesquisa);
//        }
//        pesquisar = false;
//        return null;
//    }
    public List<GrlAreaInterna> getGrlAreaInternaLista()
    {
        return grlAreaInternaLista;
    }

    public void setGrlAreaInternaLista(List<GrlAreaInterna> grlAreaInternaLista)
    {
        this.grlAreaInternaLista = grlAreaInternaLista;
    }
    
    
    
}
