/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean.cnt;

import entidade.InterTabelaVigilancia;
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
public class InterTabelaVigilanciaNovoBean implements Serializable
{

    private InterTabelaVigilancia interTabelaVigilancia;
    
    /**
     * Creates a new instance of InterTabelaVigilanciaNovoBean
     */
    public InterTabelaVigilanciaNovoBean()
    {
    }
    
    public static InterTabelaVigilanciaNovoBean getInstanciaBean()
    {
        return (InterTabelaVigilanciaNovoBean) GeradorCodigo.getInstanciaBean("interTabelaVigilanciaNovoBean");
    }
    
    public InterTabelaVigilancia getInstancia()
    {
        InterTabelaVigilancia tabela = new InterTabelaVigilancia();
        tabela.setFkIdTratamentoMalnutricao(new InterTratamentoMalnutricao());

        return tabela;
    }

    public InterTabelaVigilancia getInterTabelaVigilancia()
    {
        if (interTabelaVigilancia == null)
            interTabelaVigilancia = getInstancia();
        return interTabelaVigilancia;
    }

    public void setInterTabelaVigilancia(InterTabelaVigilancia interTabelaVigilancia)
    {
        this.interTabelaVigilancia = interTabelaVigilancia;
    }
}
