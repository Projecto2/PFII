/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.cnt;

import entidade.InterTratamentoMalnutricao;
import entidade.InterTratamentoSistematico;
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
public class InterTratamentoSistematicoNovoBean implements Serializable
{

    private InterTratamentoSistematico interTratamentoSistematico;
    
    /**
     * Creates a new instance of InterTratamentoSistematicoNovoBean
     */
    public InterTratamentoSistematicoNovoBean()
    {
    }
    
    public static InterTratamentoSistematicoNovoBean getInstanciaBean()
    {
        return (InterTratamentoSistematicoNovoBean) GeradorCodigo.getInstanciaBean("interTratamentoSistematicoNovoBean");
    }
    
    public InterTratamentoSistematico getInstancia()
    {
        InterTratamentoSistematico tratamento = new InterTratamentoSistematico();
        tratamento.setFkIdTratamentoMalnutricao(new InterTratamentoMalnutricao());

        return tratamento;
    }

    public InterTratamentoSistematico getInterTratamentoSistematico()
    {
        if (interTratamentoSistematico == null)
            interTratamentoSistematico = getInstancia();
        return interTratamentoSistematico;
    }

    public void setInterTratamentoSistematico(InterTratamentoSistematico interTratamentoSistematico)
    {
        this.interTratamentoSistematico = interTratamentoSistematico;
    }
    
}
