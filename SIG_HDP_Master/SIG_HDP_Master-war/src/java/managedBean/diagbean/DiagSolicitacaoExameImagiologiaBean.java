/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsAgendamento;
import entidade.AdmsServico;
import entidade.AdmsServicoSolicitado;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.admsbean.servicosSolicitadosBean.AdmsAgendamentoBean;
import sessao.DiagExameFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagSolicitacaoExameImagiologiaBean implements Serializable
{

    @EJB
    private DiagExameFacade diagExameFacade;

    public static DiagSolicitacaoExameImagiologiaBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagSolicitacaoExameImagiologiaBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagSolicitacaoExameImagiologiaBean");
    }

    public List<AdmsServico> findExamesImagiologiaRadiografia()
    {
        return diagExameFacade.findServicosExamesImagiologiaRadiografia();
    }

    public List<AdmsServico> findExamesImagiologiaEcografia()
    {
        return diagExameFacade.findServicosExamesImagiologiaEcografia();
    }

    public List<AdmsAgendamento> getSolicitacoesRadiografia()
    {
        return diagExameFacade.findRadiografiasSolicitadas();
    }

    public List<AdmsAgendamento> getSolicitacoesEcografia()
    {
        return diagExameFacade.findEcografiasSolicitadas();
    }
}
