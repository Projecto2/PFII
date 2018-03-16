/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsTipoSolicitacaoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.admsbean.carregamentoExcel.AdmsTipoSolicitacaoServicoExcelBean;
import sessao.AdmsTipoSolicitacaoServicoFacade;
import util.Mensagem;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsTipoSolicitacaoServicoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AdmsTipoSolicitacaoServicoFacade tipoSolicitacaoServicoFacade;

    private AdmsTipoSolicitacaoServico tipoSolicitacaoServico, tipoSolicitacaoServicoPesquisa;
    
    private List<AdmsTipoSolicitacaoServico> admsTipoSolicitacaoLista;
    
    private boolean pesquisar = false;

    /**
     * Creates a new instance of TipoSolicitacaoServicoBean
     */
    public AdmsTipoSolicitacaoServicoBean ()
    {
    }

    public AdmsTipoSolicitacaoServico getTipoSolicitacaoServico ()
    {
        if (this.tipoSolicitacaoServico == null)
        {
            this.tipoSolicitacaoServico = new AdmsTipoSolicitacaoServico();
        }

        return tipoSolicitacaoServico;
    }

    public void setTipoSolicitacaoServico (AdmsTipoSolicitacaoServico tipoSolicitacaoServico)
    {
        this.tipoSolicitacaoServico = tipoSolicitacaoServico;
    }
    
    
    public void carregar()
    {
        AdmsTipoSolicitacaoServicoExcelBean.getInstanciaBean().carregarTipoSolicitacaoServicoTabela();
        
        Mensagem.sucessoMsg("Dados Carregados Com Sucesso!");
        
        pesquisar();
        
    }
    
//    public void gravarTipoPesquisar()
//    {
//        create();
//        pesquisar();
//    }

//    public void create()
//    {
//        try
//        {
//            userTransaction.begin();
//            tipoSolicitacaoServicoFacade.create(tipoSolicitacaoServico);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("TipoSolicitacaoServico guardado com sucesso!");
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
//        tipoSolicitacaoServico = null;
//    }
    
    public AdmsTipoSolicitacaoServico getTipoSolicitacaoServicoPesquisa ()
    {
        if (this.tipoSolicitacaoServicoPesquisa == null)
        {
            this.tipoSolicitacaoServicoPesquisa = new AdmsTipoSolicitacaoServico(0, "");
        }

        return tipoSolicitacaoServicoPesquisa;
    }
    
    public void setTipoSolicitacaoServicoPesquisa (AdmsTipoSolicitacaoServico tipoSolicitacaoServico)
    {
        tipoSolicitacaoServicoPesquisa = tipoSolicitacaoServico;
    }
    
//    public void setPesquisar(boolean pesquisar)
//    {
//        this.pesquisar = pesquisar;
//    }
//    
//    public boolean getPesquisar()
//    {
//        return this.pesquisar;
//    }
    
    public void pesquisar()
    {        
        if (admsTipoSolicitacaoLista == null)
        {
            admsTipoSolicitacaoLista = new ArrayList<>();
        }
        
//        admsTipoSolicitacaoLista = tipoSolicitacaoServicoFacade.findTipoSolicitacaoServico(tipoSolicitacaoServicoPesquisa);
        admsTipoSolicitacaoLista = tipoSolicitacaoServicoFacade.findTipoSolicitacaoServico(new AdmsTipoSolicitacaoServico());
        
//        if(admsTipoSolicitacaoLista.isEmpty())
//            Mensagem.warnMsg("Nenhum Tipo De Solicitação Encontrado");
        
        if(admsTipoSolicitacaoLista.isEmpty())
            Mensagem.warnMsg("Nenhum Tipo de Solicitação Encontrada");
        else{
            Mensagem.sucessoMsg(""+admsTipoSolicitacaoLista.size()+" Registos Retornados Com Sucesso!");
        }
    }
    
    

//    public String edit()
//    {
//        try
//        {
//            userTransaction.begin();
//            if (tipoSolicitacaoServico.getPkIdTipoSolicitacao()== null)
//            {
//                throw new NullPointerException("PK -> NULL");
//            }
//            tipoSolicitacaoServicoFacade.edit(tipoSolicitacaoServico);
//            userTransaction.commit();
//            Mensagem.sucessoMsg("TipoSolicitacaoServico editado com sucesso!");
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
//        tipoSolicitacaoServico = null;
//
//        return null;
//    }

    public List<AdmsTipoSolicitacaoServico> findAll ()
    {
        return tipoSolicitacaoServicoFacade.findAll();
    }

    public String limpar()
    {
        tipoSolicitacaoServico = null;
        return "tipoSolicitacaoServicoAdms?faces-redirect=true";
    }
    
//    public List<AdmsTipoSolicitacaoServico> pesquisarTipoSolicitacaoServico()
//    {
//        if(pesquisar)
//            return tipoSolicitacaoServicoFacade.findTipoSolicitacaoServico(tipoSolicitacaoServicoPesquisa);
//        pesquisar = false;
//        return null;
//    }

    public List<AdmsTipoSolicitacaoServico> getAdmsTipoSolicitacaoLista()
    {
        return admsTipoSolicitacaoLista;
    }

    public void setAdmsTipoSolicitacaoLista(List<AdmsTipoSolicitacaoServico> admsTipoSolicitacaoLista)
    {
        this.admsTipoSolicitacaoLista = admsTipoSolicitacaoLista;
    }
    
    
    
    
}
