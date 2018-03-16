/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsTipoServico;
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
import managedBean.admsbean.carregamentoExcel.AdmsTipoDeServicoExcelBean;
import sessao.AdmsTipoServicoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsTipoServicoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AdmsTipoServicoFacade tipoServicoFacade;

    private AdmsTipoServico tipoServico, tipoServicoPesquisa;
    
    private List<AdmsTipoServico> admsTipoServicoLista;
    
//    private boolean pesquisar = false;

    /**
     * Creates a new instance of TipoServicoBean
     */
    public AdmsTipoServicoBean ()
    {
    }
    
    public static AdmsTipoServicoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsTipoServicoBean admsTipoServicoBean = 
            (AdmsTipoServicoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsTipoServicoBean");
        
        return admsTipoServicoBean;
    }


    public AdmsTipoServico getTipoServico ()
    {
        if (this.tipoServico == null)
        {
            this.tipoServico = new AdmsTipoServico();
        }

        return tipoServico;
    }

    public void setTipoServico (AdmsTipoServico tipoServico)
    {
        this.tipoServico = tipoServico;
    }
    
    
//    public void gravarTipoServicoPesquisar()
//    {
//        create();
//        pesquisar();
//        if(admsTipoServicoLista.isEmpty())
//            Mensagem.warnMsg("Nenhum Grupo Encontrado");
//        else{
//            Mensagem.sucessoMsg("retornou");
//        }
//    }
    
    public void carregar()
    {
        AdmsTipoDeServicoExcelBean.getInstanciaBean().carregarTipoDeServicoTabela();
        
        Mensagem.sucessoMsg("Dados Carregados Com Sucesso");
                
        pesquisar();
        
//        if(admsTipoServicoLista.isEmpty())
//            Mensagem.warnMsg("Nenhum Tipo de Serviço Encontrado");
//        else{
//            Mensagem.sucessoMsg(""+admsTipoServicoLista.size()+" Registos Retornados Com Sucesso!");
//        }
    }

//    public void create ()
//    {
//        try
//        {
//            userTransaction.begin();
//            tipoServicoFacade.create(tipoServico);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("TipoServico guardado com sucesso!");
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
//        tipoServico = null;
//    }
    
    public AdmsTipoServico getTipoServicoPesquisa ()
    {
        if (this.tipoServicoPesquisa == null)
        {
            this.tipoServicoPesquisa = new AdmsTipoServico(0, "");
        }

        return tipoServicoPesquisa;
    }
    
    public void setTipoServicoPesquisa (AdmsTipoServico tipoServico)
    {
        tipoServicoPesquisa = tipoServico;
    }
    
//    public void editarTipoServicoPesquisar()
//    {
//        edit();
//        pesquisar();
//        
//        
//        if(admsTipoServicoLista.isEmpty())
//            Mensagem.warnMsg("Nenhum Grupo Encontrado");
//        else{
//            Mensagem.sucessoMsg("retornou");
//        }
//    }

//    public void edit()
//    {
//        try
//        {
//            userTransaction.begin();
//            if (tipoServico.getPkIdTipoServico() == null)
//            {
//                throw new NullPointerException("PK -> NULL");
//            }
//            tipoServicoFacade.edit(tipoServico);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("TipoServico editado com sucesso!");
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

    public List<AdmsTipoServico> findAll()
    {
        return tipoServicoFacade.findAll();
    }

    public String limpar()
    {
        tipoServico = null;
        return "tipoServicoAdms?faces-redirect=true";
    }
    
    
    public void pesquisar()
    {        
        if (admsTipoServicoLista == null)
        {
            admsTipoServicoLista = new ArrayList<>();
        }
        //888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
//        admsTipoServicoLista = tipoServicoFacade.findTiposServico(tipoServicoPesquisa);
        admsTipoServicoLista = tipoServicoFacade.findTiposServico(new AdmsTipoServico());
        //888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
        if(admsTipoServicoLista.isEmpty())
            Mensagem.warnMsg("Nenhum Tipo de Serviço Encontrado");
        else{
            Mensagem.sucessoMsg(""+admsTipoServicoLista.size()+" Registos Retornados Com Sucesso!");
        }
    }
    
    public void pegarDadosDaBaseDeDadaos()
    {        
        if (admsTipoServicoLista == null)
        {
            admsTipoServicoLista = new ArrayList<>();
        }
        //888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
//        admsTipoServicoLista = tipoServicoFacade.findTiposServico(tipoServicoPesquisa);
        admsTipoServicoLista = tipoServicoFacade.findAll();
        //888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
        
    }

    public List<AdmsTipoServico> getAdmsTipoServicoLista()
    {
        if(admsTipoServicoLista == null)
        {
            admsTipoServicoLista = new ArrayList<>();
        }
        return admsTipoServicoLista;
    }

    public void setAdmsTipoServicoLista(List<AdmsTipoServico> admsTipoServicoLista)
    {
        this.admsTipoServicoLista = admsTipoServicoLista;
    }
    
}
