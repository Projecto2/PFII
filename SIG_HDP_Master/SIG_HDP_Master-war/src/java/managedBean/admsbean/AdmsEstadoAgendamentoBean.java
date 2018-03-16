/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsEstadoAgendamento;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.AdmsEstadoAgendamentoFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsEstadoAgendamentoBean implements Serializable
{
    @EJB
    private AdmsEstadoAgendamentoFacade admsEstadoAgendamentoFacade;

    /**
     * Creates a new instance of AdmsEstadoAgendamentoBean
     */
    public AdmsEstadoAgendamentoBean()
    {
    }
    
    public List<AdmsEstadoAgendamento> findEstadosAgendamentoSemCancelado()
    {
        return admsEstadoAgendamentoFacade.findEstadosAgendamentoSemCancelado();
    }
}
