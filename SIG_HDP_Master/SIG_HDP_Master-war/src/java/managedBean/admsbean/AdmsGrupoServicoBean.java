/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsGrupoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.admsbean.carregamentoExcel.AdmsGrupoServicoExcelBean;
import sessao.AdmsGrupoServicoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
//@SessionScoped
@ViewScoped
public class AdmsGrupoServicoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AdmsGrupoServicoFacade grupoServicoFacade;

    /**
     *
     * Entidades
     */
    private AdmsGrupoServico grupoServico, grupoServicoPesquisa, grupoServicoVisualizar, grupoEditar;
    
    private List<AdmsGrupoServico> admsGrupoServicoLista;

    /**
     * Creates a new instance of GrupoServicoBean
     */
    public AdmsGrupoServicoBean ()
    {
    }
    
    public static AdmsGrupoServicoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsGrupoServicoBean admsGrupoServicoBean = 
            (AdmsGrupoServicoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsGrupoServicoBean");
        
        return admsGrupoServicoBean;
    }

    public AdmsGrupoServico getGrupoServico()
    {
        if (this.grupoServico == null)
        {
            this.grupoServico = getInstanciaGrupoServico();
        }

        return grupoServico;
    }

    public void setGrupoServico (AdmsGrupoServico grupoServico)
    {
        this.grupoServico = grupoServico;
    }

    public AdmsGrupoServico getGrupoServicoPesquisa()
    {
        if (this.grupoServicoPesquisa == null)
        {
            this.grupoServicoPesquisa = getInstanciaGrupoServico();
        }

        return grupoServicoPesquisa;
    }

    public void setGrupoServicoPesquisa(AdmsGrupoServico grupoServicoPesquisa)
    {
        this.grupoServicoPesquisa = grupoServicoPesquisa;
    }


    public AdmsGrupoServico getGrupoServicoVisualizar()
    {
        return grupoServicoVisualizar;
    }

    public void setGrupoServicoVisualizar(AdmsGrupoServico grupoServicoVisualizar)
    {
        this.grupoServicoVisualizar = grupoServicoVisualizar;
    }
    

    public AdmsGrupoServico getInstanciaGrupoServico()
    {
        AdmsGrupoServico grupoServicoInstancia = new AdmsGrupoServico();
        grupoServicoInstancia.setFkIdGrupoServicoPai(new AdmsGrupoServico());
        return grupoServicoInstancia;
    }
    
    
    public void carregar()
    {
        AdmsGrupoServicoExcelBean.getInstanciaBean().carregarGrupoServicoTabela();
        
//        pegarDadosDaBaseDeDadaos();
        
        Mensagem.sucessoMsg("Dados Carregados Com Sucesso!");
        
        AdmsGrupoServico servico = new AdmsGrupoServico();
        
        servico.setFkIdGrupoServicoPai(new AdmsGrupoServico());
        
        admsGrupoServicoLista = grupoServicoFacade.findGrupoServico(servico);
        
        
        
        if(admsGrupoServicoLista.isEmpty())
            Mensagem.warnMsg("Nenhum Grupo Encontrado");
        else{
            Mensagem.sucessoMsg(""+admsGrupoServicoLista.size()+" Registos Retornados Com Sucesso!");
        }
    }
    
    

//    public void create()
//    {
//        try
//        {
//            if(grupoServico.getFkIdGrupoServicoPai().getPkIdGrupoServico() == null)
//                grupoServico.setFkIdGrupoServicoPai(null);
//            userTransaction.begin();
//                grupoServicoFacade.create(grupoServico);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("Grupo De Serviços Guardado Com Sucesso!");
//            if(grupoServico.getFkIdGrupoServicoPai() == null)
//                grupoServico.setFkIdGrupoServicoPai(new AdmsGrupoServico());
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
//        grupoServico = null;
////        return null;
//    }
    
    
//    public void gravarGrupoServicoPesquisar()
//    {
//        create();
//        pesquisar();
//    }
    
//    public void editarGrupoServicoPesquisar()
//    {
//        edit();
//        pesquisar();
//    }
    

//    public void edit ()
//    {
//        try
//        {
//            userTransaction.begin();
//            if (grupoEditar.getPkIdGrupoServico() == null)
//            {
//                throw new NullPointerException("PK -> NULL");
//            }
//            if(grupoEditar.getFkIdGrupoServicoPai().getPkIdGrupoServico() == null)
//            {
//                grupoEditar.setFkIdGrupoServicoPai(null);
//            }
//            else if(grupoEditar.getPkIdGrupoServico() == grupoEditar.getFkIdGrupoServicoPai().getPkIdGrupoServico())
//            {
//                Mensagem.erroMsg("Um Grupo Não Pode Ser O Grupo Pai Dele Mesmo!");
//                return;
//            }
//            grupoServicoFacade.edit(grupoEditar);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("GrupoServico editado com sucesso!");
//            if(grupoEditar.getFkIdGrupoServicoPai() == null)
//            {
//                grupoEditar.setFkIdGrupoServicoPai(new AdmsGrupoServico());
//            }
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
//    }

    public List<AdmsGrupoServico> findAll()
    {
        return grupoServicoFacade.findAll();
    }

    public void limpar()
    {
        grupoServico = null;
    }
    
    public void pesquisar()
    {        
        if (admsGrupoServicoLista == null)
        {
            admsGrupoServicoLista = new ArrayList<>();
        }
        
        
        //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888
        admsGrupoServicoLista = grupoServicoFacade.findGrupoServico(grupoServicoPesquisa);
//        admsGrupoServicoLista = grupoServicoFacade.findAll();
        //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888
        
        if(admsGrupoServicoLista.isEmpty())
            Mensagem.warnMsg("Nenhum Grupo Encontrado");
        else{
            Mensagem.sucessoMsg(""+admsGrupoServicoLista.size()+" Registos Retornados Com Sucesso!");
        }
        
    
    }

//    public AdmsGrupoServico getGrupoEditar()
//    {
//        if(grupoEditar == null)
//        {
//            grupoEditar = getInstanciaGrupoServico();
//        }
//        return grupoEditar;
//    }
//
//    public void setGrupoEditar(AdmsGrupoServico grupoEditar)
//    {
////        System.out.println(""+grupoEditar.getPkIdGrupoServico());
//        
//        this.grupoEditar = grupoEditar;
//        if(grupoEditar.getFkIdGrupoServicoPai() == null)
//            this.grupoEditar.setFkIdGrupoServicoPai(new AdmsGrupoServico());
////        System.out.println("pai "+grupoEditar.getFkIdGrupoServicoPai().getPkIdGrupoServico());
//    }

    public List<AdmsGrupoServico> getAdmsGrupoServicoLista()
    {
        return admsGrupoServicoLista;
    }

    public void setAdmsGrupoServicoLista(List<AdmsGrupoServico> admsGrupoServicoLista)
    {
        this.admsGrupoServicoLista = admsGrupoServicoLista;
    }
}
