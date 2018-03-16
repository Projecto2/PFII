/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean;

import entidade.GrlConvenio;
import entidade.GrlProjetoConvenio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.GrlProjetoConvenioFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class GrlProjetoConvenioBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private GrlProjetoConvenioFacade projetoConvenioFacade;

    /**
     *
     * Entidades
     */
    private GrlProjetoConvenio projetoConvenio, projetoConvenioPesquisa, projetoConvenioVisualizar;
    
    private boolean pesquisar = false;
    
    private int estado;
    
    private List<GrlProjetoConvenio> grlListaProjetoConvenio;

    /**
     * Creates a new instance of ProjetoConvenioBean
     */
    public GrlProjetoConvenioBean ()
    {
    }

    public GrlProjetoConvenio getProjetoConvenio()
    {
        if (this.projetoConvenio == null)
        {
            this.projetoConvenio = getInstanciaProjetoConvenio();
        }

        return projetoConvenio;
    }

    public void setProjetoConvenio (GrlProjetoConvenio projetoConvenio)
    {
        this.projetoConvenio = projetoConvenio;
    }

    public GrlProjetoConvenio getProjetoConvenioPesquisa()
    {
        if (this.projetoConvenioPesquisa == null)
        {
            this.projetoConvenioPesquisa = getInstanciaProjetoConvenio();
        }

        return projetoConvenioPesquisa;
    }

    public void setProjetoConvenioPesquisa(GrlProjetoConvenio projetoConvenioPesquisa)
    {
        this.projetoConvenioPesquisa = projetoConvenioPesquisa;
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

    public GrlProjetoConvenio getProjetoConvenioVisualizar()
    {
        return projetoConvenioVisualizar;
    }

    public List<GrlProjetoConvenio> getGrlListaProjetoConvenio()
    {
        if(grlListaProjetoConvenio == null)
        {
            grlListaProjetoConvenio = new ArrayList<>();
        }
        return grlListaProjetoConvenio;
    }

    public void setGrlListaProjetoConvenio(List<GrlProjetoConvenio> grlListaConvenio)
    {
        this.grlListaProjetoConvenio = grlListaConvenio;
    }

    public void setProjetoConvenioVisualizar(GrlProjetoConvenio projetoConvenioVisualizar)
    {
        this.projetoConvenioVisualizar = projetoConvenioVisualizar;
    }
    

    public GrlProjetoConvenio getInstanciaProjetoConvenio()
    {
        GrlProjetoConvenio projetoConvenioInstancia = new GrlProjetoConvenio();
        projetoConvenioInstancia.setFkIdConvenio(new GrlConvenio());
        return projetoConvenioInstancia;
    }
    

    public String create () throws SystemException
    {
        try
        {
            userTransaction.begin();
            projetoConvenioFacade.create(projetoConvenio);
            userTransaction.commit();
            Mensagem.sucessoMsg("ProjetoConvenio guardado com sucesso!");
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

        projetoConvenio = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            if (projetoConvenio.getPkIdProjetoConvenio() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            projetoConvenioFacade.edit(projetoConvenio);
            userTransaction.commit();
            Mensagem.sucessoMsg("ProjetoConvenio editado com sucesso!");
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

        projetoConvenio = null;

        return null;
    }

    public List<GrlProjetoConvenio> findAll()
    {
        return projetoConvenioFacade.findAll();
    }

    public void limpar()
    {
        projetoConvenio = null;
    }
    
    
    public void pesquisar()
    {
        int var = 0;
        
        if (grlListaProjetoConvenio == null)
        {
            grlListaProjetoConvenio = new ArrayList<>();
        }
        
        if(estado == 1)
        {
            projetoConvenioPesquisa.setEstadoProjetoConvenio(true);
            var = 1;
//                return projetoConvenioFacade.findProjetoConvenio(projetoConvenioPesquisa, 1);
        }
        else if(estado == 2)
        {
            projetoConvenioPesquisa.setEstadoProjetoConvenio(false);
            var = 1;
        }
        grlListaProjetoConvenio = projetoConvenioFacade.findProjetoConvenio(projetoConvenioPesquisa, var);
        
        if(grlListaProjetoConvenio.isEmpty())
            Mensagem.warnMsg("Nenhum Projeto Convénio Encontrado");
        else Mensagem.sucessoMsg("Tabela Atualizada. "+grlListaProjetoConvenio.size()+" registos!");
    }
    
    
    
    
    
    
    
    
    public List<GrlProjetoConvenio> pesquisarProjetoConvenio()
    {
//        if(pesquisar)
//        {
//            return /*projetoConvenioFacade.findProjetoConvenio(projetoConvenioPesquisa)*/null;
//        }
//        pesquisar = false;
//        return null;
        
        int var = 0;
        if(pesquisar)
        {
            if(estado == 1)
            {
                projetoConvenioPesquisa.setEstadoProjetoConvenio(true);
                var = 1;
//                return projetoConvenioFacade.findProjetoConvenio(projetoConvenioPesquisa, 1);
            }
            else if(estado == 2)
            {
                projetoConvenioPesquisa.setEstadoProjetoConvenio(false);
                var = 1;
            }
            return projetoConvenioFacade.findProjetoConvenio(projetoConvenioPesquisa, var);
        }
        pesquisar = false;
        return null;
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
