/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.diagbean;

import entidade.DiagSolicitacaoTipagemDoente;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.interbean.InterRegistoInternamentoBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagSolicitacaoTipagemDoenteBean implements Serializable
{
    
    public static DiagSolicitacaoTipagemDoenteBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagSolicitacaoTipagemDoenteBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagSolicitacaoTipagemDoenteBean");
    }
    
    public static DiagSolicitacaoTipagemDoente getInstancia()
    {
        DiagSolicitacaoTipagemDoente diagSolicitacaoTipagemDoente = new DiagSolicitacaoTipagemDoente();
        diagSolicitacaoTipagemDoente.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());
        diagSolicitacaoTipagemDoente.setFkIdRegistoInternamento(InterRegistoInternamentoBean.getInstanciaBean().getInstancia());
        
        return diagSolicitacaoTipagemDoente;
    }
    
}
