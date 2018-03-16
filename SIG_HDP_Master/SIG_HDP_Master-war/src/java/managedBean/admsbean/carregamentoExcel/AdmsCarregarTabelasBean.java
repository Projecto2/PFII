/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean.carregamentoExcel;

//import managedBean.grlbean.carregamentoExcel.GrlAreaInternaExcelBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsCarregarTabelasBean
{

    /**
     * Creates a new instance of AdmsCarregarTabelasBean
     */
    public AdmsCarregarTabelasBean()
    {
    }
    
    
    public static AdmsCarregarTabelasBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        AdmsCarregarTabelasBean admsCarregarTabelasBean = 
            (AdmsCarregarTabelasBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "admsCarregarTabelasBean");
        
        return admsCarregarTabelasBean;
    }

    
    public void actualizarAdmissoesTabelas()
    {
        AdmsClassificacaoServicoSolicitadoExcelBean.getInstanciaBean().carregarClassificacaoServicoSolicitadoTabela();
        AdmsTipoSolicitacaoServicoExcelBean.getInstanciaBean().carregarTipoSolicitacaoServicoTabela();
        AdmsEstadoAgendamentoExcelBean.getInstanciaBean().carregarEstadoAgendamentoTabela();
        AdmsEstadoPagamentoExcelBean.getInstanciaBean().carregarEstadoPagamentoTabela();
//        AdmsTipoDeServicoExcelBean.getInstanciaBean().carregarTipoDeServicoTabela();
//        AdmsGrupoServicoExcelBean.getInstanciaBean().carregarGrupoServicoTabela();
//        AdmsServicoExcelBean.getInstanciaBean().carregarServicoTabela();
//        AdmsCategoriaServicoExcelBean.getInstanciaBean().carregarCategoriaServicoTabela();
    }
    
    public void carregarTabelasQueServicoDepende()
    {
        AdmsGrupoServicoExcelBean.getInstanciaBean().carregarGrupoServicoTabela();
        AdmsTipoDeServicoExcelBean.getInstanciaBean().carregarTipoDeServicoTabela();
//        
//        AdmsServicoExcelBean.getInstanciaBean().carregarServicoTabela();
//        AdmsCategoriaServicoExcelBean.getInstanciaBean().carregarCategoriaServicoTabela();
    }
    
    
}
