/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsEstadoPagamento;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.AdmsEstadoPagamentoFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsEstadoPagamentoBean implements Serializable
{
    @EJB
    private AdmsEstadoPagamentoFacade admsEstadoPagamentoFacade;

    /**
     * Creates a new instance of AdmsEstadoPagamentoBean
     */
    public AdmsEstadoPagamentoBean()
    {
    }
    
    public List<AdmsEstadoPagamento> findAll()
    {
        return admsEstadoPagamentoFacade.findAll();
    }
    
}
