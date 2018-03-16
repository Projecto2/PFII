/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsGrupoServico;
import entidade.AdmsServico;
import entidade.AdmsTipoServico;
import entidade.GrlAreaInterna;
import entidade.GrlEspecialidade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.admsbean.carregamentoExcel.AdmsCarregarTabelasBean;
import managedBean.admsbean.carregamentoExcel.AdmsCategoriaServicoExcelBean;
import managedBean.admsbean.carregamentoExcel.AdmsServicoExcelBean;
import sessao.AdmsCategoriaServicoFacade;
import sessao.AdmsServicoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsServicoBean implements Serializable
{
    @EJB
    private AdmsCategoriaServicoFacade admsCategoriaServicoFacade;
    
    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AdmsServicoFacade servicoFacade;
    

    /**
     *
     * Entidades
     */
    private AdmsServico servico, servicoPesquisa, servicoEditar;
    
    private List<AdmsServico> admsServicoLista;
    
    private boolean carregou = false;

    public AdmsServicoBean ()
    {
    }
    
    public static AdmsServicoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsServicoBean admsServicoBean = 
            (AdmsServicoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsServicoBean");
        
        return admsServicoBean;
    }

    public AdmsServico getServico ()
    {
        if (this.servico == null)
        {
            this.servico = getInstancia();
        }
        if(servico.getFkIdGrupoServico() == null)
        {
            servico.setFkIdGrupoServico(new AdmsGrupoServico());
        }
        return servico;
    }

    public void setServico (AdmsServico servico)
    {
        try{
            if(servico.getFkIdEspecialidade().getPkIdEspecialidade() == null)
                servico.getFkIdEspecialidade().setPkIdEspecialidade(0);
        }
        catch(Exception ex)
        {
            servico.setFkIdEspecialidade(new GrlEspecialidade(0));
        }
        
        try{
            if(servico.getFkIdArea().getPkIdAreaInterna() == null)
                servico.getFkIdArea().setPkIdAreaInterna(0);
        }
        catch(Exception ex)
        {
            servico.setFkIdArea(new GrlAreaInterna(0));
        }
        
        this.servico = servico;
    }
    
    public AdmsServico getServicoPesquisa()
    {
        if (this.servicoPesquisa == null)
            this.servicoPesquisa = getInstancia();
        return servicoPesquisa;
    }

    public void setServicoPesquisa(AdmsServico servicoPesquisa)
    {
        this.servicoPesquisa = servicoPesquisa;
    }
    
    public AdmsServico getInstancia()
    {
        AdmsServico servicoInstancia = new AdmsServico();
        servicoInstancia.setFkIdArea(new GrlAreaInterna());
        servicoInstancia.setFkIdEspecialidade(new GrlEspecialidade());
        servicoInstancia.setFkIdTipoServico(new AdmsTipoServico());
        servicoInstancia.setFkIdGrupoServico(new AdmsGrupoServico());
        return servicoInstancia;
    }
    
//    public boolean isPesquisar()
//    {
//        return pesquisar;
//    }
//
//    public void setPesquisar(boolean pesquisar)
//    {
//        this.pesquisar = pesquisar;
//    }
    
    public void pesquisar()
    {        
        if (admsServicoLista == null)
        {
            admsServicoLista = new ArrayList<>();
        }
        
        admsServicoLista = servicoFacade.findServico(servicoPesquisa, false);
        
        if(admsServicoLista.isEmpty())
            Mensagem.warnMsg("Nenhuma Serviço Encontrado!");
        else{
            Mensagem.sucessoMsg(""+admsServicoLista.size()+" Registos Retornados Com Sucesso!");
        }
    }
    
    
    public void carregar()
    {
        AdmsCarregarTabelasBean.getInstanciaBean().carregarTabelasQueServicoDepende();
        AdmsServicoExcelBean.getInstanciaBean().carregarServicoTabela();
        AdmsCategoriaServicoExcelBean.getInstanciaBean().carregarCategoriaServicoTabela();
        
        Mensagem.sucessoMsg("Dados Carregados Com Sucesso!");
        
        
        pesquisar();
    }
    
//    public void gravarServicoPesquisar()
//    {
//        create();
//        pesquisar();
//    }

//    public void create()
//    {
//        try
//        {
//            if(servico.getFkIdGrupoServico().getPkIdGrupoServico() == null) servico.setFkIdGrupoServico(null);
//            if(servico.getFkIdEspecialidade().getPkIdEspecialidade() == null) servico.setFkIdEspecialidade(null);
//            if(servico.getFkIdArea().getPkIdAreaInterna() == null) servico.setFkIdArea(null);
//            if(servico.getFkIdTipoServico().getPkIdTipoServico() == null) servico.setFkIdTipoServico(null);
//            userTransaction.begin();
//            servicoFacade.create(servico);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("Servico guardado com sucesso!");
//        }
//        catch (Exception e)
//        {
//            try
//            {
//                Mensagem.erroMsg(e.toString());
//                userTransaction.rollback();
//                
//            }
//            catch (IllegalStateException | SecurityException | SystemException ex)
//            {
//                Mensagem.erroMsg("Rollback: " + ex.toString());
//            }
//        }
//        servico = null;
////        return null;
//    }

//    public void editarPesquisar()
//    {
//        edit();
//        pesquisar();
//    }
    
//    public void edit ()
//    {
//        try
//        {
//            userTransaction.begin();
//            if (servicoEditar.getPkIdServico() == null)
//            {
//                throw new NullPointerException("PK -> NULL");
//            }
//            if(servicoEditar.getFkIdGrupoServico().getPkIdGrupoServico() == null) servicoEditar.setFkIdGrupoServico(null);
//            if(servicoEditar.getFkIdArea().getPkIdAreaInterna() == null) servicoEditar.setFkIdArea(null);
//            if(servicoEditar.getFkIdEspecialidade().getPkIdEspecialidade() == null) servicoEditar.setFkIdEspecialidade(null);
//            if(servicoEditar.getFkIdTipoServico().getPkIdTipoServico() == null) servicoEditar.setFkIdTipoServico(null);
//            servicoFacade.edit(servicoEditar);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("Servico Editado Com Sucesso!");
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
////        servico = null;
////        return null;
//    }

    public List<AdmsServico> findAll ()
    {
        return servicoFacade.findAll();
    }
    
//    public List<AdmsServico> findServicoComEssesDados()
//    {
//        if(pesquisar)
//            return servicoFacade.findServico(servicoPesquisa, false);
//        pesquisar = false;
//        return null;
//    }
    
    
//    public AdmsServico getServicoEditar()
//    {
//        if (this.servicoEditar == null)
//        {
//            this.servicoEditar = getInstancia();
//        }
//        if(servicoEditar.getFkIdGrupoServico() == null)
//        {
//            servicoEditar.setFkIdGrupoServico(new AdmsGrupoServico());
//        }
//        if(servicoEditar.getFkIdEspecialidade()== null)
//        {
//            servicoEditar.setFkIdEspecialidade(new GrlEspecialidade());
//        }
//        return servicoEditar;
//    }

//    public void setServicoEditar(AdmsServico servicoEditar)
//    {
//        try{
//            if(servicoEditar.getFkIdEspecialidade().getPkIdEspecialidade() == null)
//                servicoEditar.getFkIdEspecialidade().setPkIdEspecialidade(0);
//        }
//        catch(Exception ex)
//        {
//            servicoEditar.setFkIdEspecialidade(new GrlEspecialidade(0));
//        }
//        
//        try{
//            if(servicoEditar.getFkIdArea().getPkIdAreaInterna() == null)
//                servicoEditar.getFkIdArea().setPkIdAreaInterna(0);
//        }
//        catch(Exception ex)
//        {
//            servicoEditar.setFkIdArea(new GrlAreaInterna(0));
//        }
//        
//        this.servicoEditar = servicoEditar;
//    }
    
    
    
    public void limpar()
    {
        servico = null;
    }
    
    public String podeTerMedicoOuNao(boolean pode)
    {
        if(pode) return "Sim";
        return "Não";
    }
    
    public String verificarCategoria(AdmsServico servico)
    {
        return admsCategoriaServicoFacade.estadoServicoCategoriaServico(servico);
    }

    public List<AdmsServico> getAdmsServicoLista()
    {
        return admsServicoLista;
    }

    public void setAdmsServicoLista(List<AdmsServico> admsServicoLista)
    {
        this.admsServicoLista = admsServicoLista;
    }
    
    public List<AdmsServico> findServicosInternamento()
    {
        return servicoFacade.findServicosInternamento();
    }
    
}
