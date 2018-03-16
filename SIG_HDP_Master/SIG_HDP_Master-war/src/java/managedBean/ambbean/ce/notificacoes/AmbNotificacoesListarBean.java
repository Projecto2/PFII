/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.ce.notificacoes;

import entidade.AmbNotificacoes;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import managedBean.ambbean.ce.consultas.AmbConsultaListarBean;
import managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoListarBean;
import managedBean.ambbean.ce.triagem.AmbTriagemListarBean;
import sessao.AmbNotificacoesFacade;
import sessao.AmbTriagemFacade;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbNotificacoesListarBean implements Serializable
{

    @EJB
    private AmbNotificacoesFacade ambNotificacoesFacade;
    @EJB
    private AmbTriagemFacade ambTriagemFacade;

//    private AmbNotificacoes ambNotificacoes;
//    private AmbTriagem ambTriagem;
//    
    /**
     * Creates a new instance of AmbNotificacoesListarBean
     */
    public AmbNotificacoesListarBean()
    {
    }

    public static AmbConsultaListarBean getInstanciaAmbConsultaListarBean()
    {
        return (AmbConsultaListarBean) GeradorCodigo.getInstanciaBean("ambConsultaListarBean");
    }    

    public static AmbDiagnosticoListarBean getInstanciaAmbDiagnosticoListarBean()
    {
        return (AmbDiagnosticoListarBean) GeradorCodigo.getInstanciaBean("ambDiagnosticoListarBean");
    }      
    
    public static AmbNotificacoesListarBean getInstanciaBean()
    {
        return (AmbNotificacoesListarBean) getInstanciaGeradorCodigo().getInstanciaBean("ambNotificacoesListarBean");
    }

    public static AmbNotificacoesCriarBean getInstanciaAmbNotificacoesCriarBean()
    {
        return (AmbNotificacoesCriarBean) getInstanciaGeradorCodigo().getInstanciaBean("ambNotificacoesCriarBean");
    }    
    
    public static AmbTriagemListarBean getInstanciaAmbTriagemListarBean()
    {
        return (AmbTriagemListarBean) getInstanciaGeradorCodigo().getInstanciaBean("ambTriagemListarBean");
    }     
    
    public static GeradorCodigo getInstanciaGeradorCodigo()
    {
        return new GeradorCodigo();
    } 
    
    public List<AmbNotificacoes> findNotificacoes()
    {
        return ambNotificacoesFacade.findAll();
    }
    
    public String notificacoes()
    {
        getInstanciaAmbTriagemListarBean().findSolicitacoes();
        getInstanciaAmbConsultaListarBean().pesquisarPacientesEncaminhadosConsulta();
        getInstanciaAmbConsultaListarBean().pesquisarPacientesEncaminhadosReconsulta();
        getInstanciaAmbNotificacoesCriarBean().criarAmbNotificacaoPrimeira();
        getInstanciaAmbDiagnosticoListarBean().findConsultasExame();

        if (getInstanciaAmbTriagemListarBean().findSolicitacoes().isEmpty()) 
            if (getInstanciaAmbConsultaListarBean().pesquisarPacientesEncaminhadosConsulta().isEmpty())
                if (getInstanciaAmbConsultaListarBean().pesquisarPacientesEncaminhadosReconsulta().isEmpty())
                    if (getInstanciaAmbDiagnosticoListarBean().findConsultasExame().isEmpty())
                        return " WHITE";

       return "YELLOW";
    }
}
