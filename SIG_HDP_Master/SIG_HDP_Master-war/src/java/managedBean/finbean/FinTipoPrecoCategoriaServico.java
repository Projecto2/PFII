/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.finbean;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.FinTipoPrecoCategoriaServicoFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class FinTipoPrecoCategoriaServico implements Serializable
{
    @EJB
    private FinTipoPrecoCategoriaServicoFacade finTipoPrecoCategoriaServicoFacade;
    
    
    

    /**
     * Creates a new instance of AdmsTipoValorPrecoServico
     */
    public FinTipoPrecoCategoriaServico()
    {
    }
    
    
    public List<entidade.FinTipoPrecoCategoriaServico> findAll()
    {
        return finTipoPrecoCategoriaServicoFacade.findAll();
    }
    
}
