/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.AdmsAgendamento;
import entidade.AdmsServico;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.DiagExameFacade;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagSolicitacaoExameLaboratorioBean implements Serializable
{

    @EJB
    private DiagExameFacade diagExameFacade;

    public static DiagSolicitacaoExameLaboratorioBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagSolicitacaoExameLaboratorioBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagSolicitacaoExameLaboratorioBean");
    }

    public List<AdmsServico> findExamesLaboratorio()
    {
        return diagExameFacade.findServicosExamesLaboratorio();
    }

    public List<AdmsAgendamento> getSolicitacoesExame()
    {
        return diagExameFacade.findExamesSolicitados();
    }

    public List<AdmsAgendamento> getSolicitacoesExameUrgentes()
    {
        return diagExameFacade.findExamesUrgentesSolicitados();
    }
}
