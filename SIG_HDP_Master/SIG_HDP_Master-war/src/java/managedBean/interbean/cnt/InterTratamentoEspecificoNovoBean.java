/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.cnt;

import entidade.InterTratamentoEspecifico;
import entidade.InterTratamentoMalnutricao;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterTratamentoEspecificoNovoBean implements Serializable
{

    private InterTratamentoEspecifico interTratamentoEspecifico;
    
    /**
     * Creates a new instance of InterTratamentoEspecificoNovoBean
     */
    public InterTratamentoEspecificoNovoBean()
    {
    }
    
    public static InterTratamentoEspecificoNovoBean getInstanciaBean()
    {
        return (InterTratamentoEspecificoNovoBean) GeradorCodigo.getInstanciaBean("interTratamentoEspecificoNovoBean");
    }
    
    public InterTratamentoEspecifico getInstancia()
    {
        InterTratamentoEspecifico tratamento = new InterTratamentoEspecifico();
        tratamento.setFkIdTratamentoMalnutricao(new InterTratamentoMalnutricao());

        return tratamento;
    }

    public InterTratamentoEspecifico getInterTratamentoEspecifico()
    {
        if (interTratamentoEspecifico == null)
            interTratamentoEspecifico = getInstancia();
        return interTratamentoEspecifico;
    }

    public void setInterTratamentoEspecifico(InterTratamentoEspecifico interTratamentoEspecifico)
    {
        this.interTratamentoEspecifico = interTratamentoEspecifico;
    }
}
