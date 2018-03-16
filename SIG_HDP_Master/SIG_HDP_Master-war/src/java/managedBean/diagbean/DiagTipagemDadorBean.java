/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagCandidatoDador;
import entidade.DiagTipagemDador;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.DiagTipagemDadorFacade;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipagemDadorBean implements Serializable
{

    @EJB
    private DiagTipagemDadorFacade diagTipagemDadorFacade;

    public static DiagTipagemDadorBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTipagemDadorBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTipagemDadorBean");
    }

    public static DiagTipagemDador getInstancia()
    {
        DiagTipagemDador diagTipagemDador = new DiagTipagemDador();
        diagTipagemDador.setFkIdCandidatoDador(DiagCandidatoDadorBean.getInstancia());
        diagTipagemDador.setFkRealizadoPor(RhFuncionarioNovoBean.getInstancia());
        diagTipagemDador.setConclusao(DiagGrupoSanguineoBean.getInstancia());
        
        return diagTipagemDador;
    }
    
    public DiagTipagemDador findTipagemDador(DiagCandidatoDador candidatoDador)
    {
        return diagTipagemDadorFacade.findTipagemDador(candidatoDador);
    }

}
