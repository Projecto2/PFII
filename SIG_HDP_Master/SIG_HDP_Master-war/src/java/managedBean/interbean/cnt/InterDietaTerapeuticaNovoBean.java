/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.cnt;

import entidade.InterDietaTerapeutica;
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
public class InterDietaTerapeuticaNovoBean implements Serializable
{

    private InterDietaTerapeutica interDietaTerapeutica;
    
    /**
     * Creates a new instance of InterDietaTerapeuticaNovoBean
     */
    public InterDietaTerapeuticaNovoBean()
    {
    }
    
    public static InterDietaTerapeuticaNovoBean getInstanciaBean()
    {
        return (InterDietaTerapeuticaNovoBean) GeradorCodigo.getInstanciaBean("interDietaTerapeuticaNovoBean");
    }
    
    public InterDietaTerapeutica getInstancia()
    {
        InterDietaTerapeutica dieta = new InterDietaTerapeutica();
        dieta.setFkIdTratamentoMalnutricao(new InterTratamentoMalnutricao());

        return dieta;
    }

    public InterDietaTerapeutica getInterDietaTerapeutica()
    {
        if (interDietaTerapeutica == null)
            interDietaTerapeutica = getInstancia();
        return interDietaTerapeutica;
    }

    public void setInterDietaTerapeutica(InterDietaTerapeutica interDietaTerapeutica)
    {
        this.interDietaTerapeutica = interDietaTerapeutica;
    }
    
    
}
