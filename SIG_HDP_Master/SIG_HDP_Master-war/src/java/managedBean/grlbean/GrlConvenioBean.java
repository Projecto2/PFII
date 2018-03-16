/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean;

import entidade.GrlConvenio;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.GrlConvenioFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
//@ViewScoped
public class GrlConvenioBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlConvenioFacade convenioFacade;
    
    private GrlConvenio grlConvenio, grlConvenioPesquisa, grlConvenioEditar, grlConvenioVisualizar;
    
    private List<GrlConvenio> grlConvenioLista;
    
//    private boolean pesquisar = false;
    
    int estado;

    /**
     * Creates a new instance of ConvenioBean
     */
    public GrlConvenioBean ()
    {
    }

    public GrlConvenio getGrlConvenio()
    {
        if (this.grlConvenio == null)
        {
            this.grlConvenio = getInstanciaConvenio();
        }

        return grlConvenio;
    }

    public void setGrlConvenio (GrlConvenio grlConvenio)
    {
        this.grlConvenio = grlConvenio;
    }

    public GrlConvenio getGrlConvenioEditar()
    {
        if (this.grlConvenioEditar == null)
        {
            this.grlConvenioEditar = getInstanciaConvenio();
        }
        return grlConvenioEditar;
    }

    public void setGrlConvenioEditar(GrlConvenio grlConvenioEditar)
    {
        this.grlConvenioEditar = grlConvenioEditar;
    }

    public GrlConvenio getGrlConvenioPesquisa()
    {
        if (this.grlConvenioPesquisa == null)
        {
            this.grlConvenioPesquisa = getInstanciaConvenio();
        }
        return grlConvenioPesquisa;
    }

    public void setGrlConvenioPesquisa(GrlConvenio convenioPesquisa)
    {
        this.grlConvenioPesquisa = convenioPesquisa;
    }

    public List<GrlConvenio> getGrlConvenioLista()
    {
        if(grlConvenioLista == null)
        {
            grlConvenioLista = new ArrayList<>();
        }
        return grlConvenioLista;
    }

    public void setGrlConvenioLista(List<GrlConvenio> grlConvenioLista)
    {
        this.grlConvenioLista = grlConvenioLista;
    }
    
    
//    public boolean getPesquisar()
//    {
//        return pesquisar;
//    }
//
//    public void setPesquisar(boolean pesquisar)
//    {
//        this.pesquisar = pesquisar;
//    }
    

    public GrlConvenio getGrlConvenioVisualizar()
    {
        return grlConvenioVisualizar;
    }

    public void setGrlConvenioVisualizar(GrlConvenio convenioVisualizar)
    {
        this.grlConvenioVisualizar = convenioVisualizar;
    }
    

    public GrlConvenio getInstanciaConvenio()
    {
        GrlConvenio convenioInstancia = new GrlConvenio();
        convenioInstancia.setFkIdInstituicao(new GrlInstituicao());
        return convenioInstancia;
    }
    
    public void gravarConvenioPesquisar()
    {
        create();
        pesquisar();
    }
    
    public void create()
    {
        try
        {
            userTransaction.begin();
            convenioFacade.create(grlConvenio);
            userTransaction.commit();
            Mensagem.sucessoMsg("Convenio guardado com sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        grlConvenio = null;
    }

    
    public void editarConvenioPesquisar()
    {
        edit();
        pesquisar();
    }
    
    public void edit ()
    {
        try
        {
            userTransaction.begin();
            if (grlConvenioEditar.getPkIdConvenio() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            convenioFacade.edit(grlConvenioEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Convénio Editado Com Sucesso!");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

    public List<GrlConvenio> findAll()
    {
        return convenioFacade.findAll();
    }

    public void limpar()
    {
        grlConvenio = null;
    }
    
//    public List<GrlConvenio> pesquisarConvenio()
//    {
//        int var = 0;
//        if(pesquisar)
//        {
//            if(estado == 1)
//            {
//                grlConvenioPesquisa.setEstadoConvenio(true);
//                var = 1;
//            }
//            else if(estado == 2)
//            {
//                grlConvenioPesquisa.setEstadoConvenio(false);
//                var = 1;
//            }
//            return convenioFacade.findConvenio(grlConvenioPesquisa, var);
//        }
//        pesquisar = false;
//        return null;
//    }
    
    public void pesquisar()
    {      
        int var = 0;
        
        if (grlConvenioLista == null)
        {
            grlConvenioLista = new ArrayList<>();
        }
        
        if(estado == 1)
        {
            grlConvenioPesquisa.setEstadoConvenio(true);
            var = 1;
        }
        else if(estado == 2)
        {
            grlConvenioPesquisa.setEstadoConvenio(false);
            var = 1;
        }
        
        grlConvenioLista = convenioFacade.findConvenio(grlConvenioPesquisa, var);
        
        if(grlConvenioLista.isEmpty())
            Mensagem.warnMsg("Nenhum Convénio Encontrado");
        else Mensagem.sucessoMsg("Tabela Atualizada. "+grlConvenioLista.size()+" registos!");
    }
    
    public void setEstado(int estado)
    {
        this.estado = estado;
    }
    
    public int getEstado()
    {
        return estado;
    }
    
    public String getEstadoDescricao(boolean estado)
    {
        if(estado) return "Ativo";
        return "Inativo";
    }
}
