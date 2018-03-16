/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.finbean;

import entidade.FinTipoEncargo;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessao.FinTipoEncargoFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@ViewScoped
public class FinTipoEncargoBean
{
    @EJB
    private FinTipoEncargoFacade finTipoEncargoFacade;

    /**
     * Creates a new instance of FinTipoEncargoBean
     */
    public FinTipoEncargoBean()
    {
    }
    
    public List<FinTipoEncargo> findAll()
    {
        return finTipoEncargoFacade.findAll();
    }
    
}
