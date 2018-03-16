/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.SupiTipoEscala;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.transaction.UserTransaction;
import sessao.SupiTipoEscalaFacade;

/**
 *
 * @author helga
 */
@ManagedBean
@ViewScoped
public class SupiTipoEscalaBean
{

    @Resource
    private UserTransaction userTransaction;
    @EJB
    private SupiTipoEscalaFacade supiTipoEscalaFacade;

    private SupiTipoEscala supiTipoEscala;

    public SupiTipoEscalaBean()
    {
    }

    public List<SupiTipoEscala> getFindall()
    {
        return supiTipoEscalaFacade.findAll();
    }

}
