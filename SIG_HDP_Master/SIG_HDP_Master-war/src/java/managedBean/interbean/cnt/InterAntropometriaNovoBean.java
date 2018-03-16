/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.cnt;

import entidade.InterAntropometria;
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
public class InterAntropometriaNovoBean implements Serializable
{

    private InterAntropometria interAntropometria;
    
    /**
     * Creates a new instance of InterAntropometriaNovoBean
     */
    public InterAntropometriaNovoBean()
    {
    }
    
    public static InterAntropometriaNovoBean getInstanciaBean()
    {
        return (InterAntropometriaNovoBean) GeradorCodigo.getInstanciaBean("interAntropometriaNovoBean");
    }
    
    public InterAntropometria getInstancia()
    {
        InterAntropometria antropometria = new InterAntropometria();
        antropometria.setFkIdTratamentoMalnutricao(new InterTratamentoMalnutricao());

        return antropometria;
    }

    public InterAntropometria getInterAntropometria()
    {
        if (interAntropometria == null)
            interAntropometria = getInstancia();
        return interAntropometria;
    }

    public void setInterAntropometria(InterAntropometria interAntropometria)
    {
        this.interAntropometria = interAntropometria;
    }
}
