/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsTipoSolicitacaoServico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.AdmsTipoSolicitacaoServicoFacade;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsTipoSolicitacaoBean implements Serializable
{
    @EJB
    private AdmsTipoSolicitacaoServicoFacade admsTipoSolicitacaoServicoFacade;

    /**
     * Creates a new instance of AdmsTipoSolicitacaoBean
     */
    public AdmsTipoSolicitacaoBean()
    {
    }
    
    
    public List<AdmsTipoSolicitacaoServico> findAll()
    {
        return admsTipoSolicitacaoServicoFacade.findAll();
    }
    
}
